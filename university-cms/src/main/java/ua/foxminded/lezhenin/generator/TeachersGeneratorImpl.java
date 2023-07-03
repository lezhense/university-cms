package ua.foxminded.lezhenin.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.util.Reader;

@Component
public class TeachersGeneratorImpl implements Generator<List<Teacher>, String> {
	private final Reader reader;
	private final PasswordEncoder passwordEncoder;
	private final RoleServiceImpl roleService;
	private static final String ADMIN_ROLE_NAME = "ADMIN";
	private static final String TEACHER_ROLE_NAME = "TEACHER";

	@Autowired
	public TeachersGeneratorImpl(Reader reader, PasswordEncoder passwordEncoder, RoleServiceImpl roleService) {
		this.reader = reader;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
	}

	@Override
	public List<Teacher> generate(String teachersNamesPath) {
		List<String> teacherNames = reader.getListFromFile(teachersNamesPath);
		List<Teacher> teachers = new ArrayList<>();
		Set<Role> teacherRole = new HashSet<>();
		Set<Role> teacherRoleWithAdmin = new HashSet<>();
		Role roleTeacher = roleService.getByName(TEACHER_ROLE_NAME);
		Role roleAdmin = roleService.getByName(ADMIN_ROLE_NAME);
		teacherRole.add(roleTeacher);
		teacherRoleWithAdmin.add(roleTeacher);
		teacherRoleWithAdmin.add(roleAdmin);
		String password = passwordEncoder.encode("123");
		for (int i = 0; i < teacherNames.size(); i++) {
			List<String> teacherFullName = Stream.of(teacherNames.get(i).split(" ")).toList();
			String firstName = teacherFullName.get(0);
			String lastName = teacherFullName.get(1);
			String username = firstName + lastName;
			Teacher teacher = new Teacher(firstName, lastName, username, password);
			teacher.setRoles(teacherRole);
			teachers.add(teacher);
		}
		Teacher lastTeacher = teachers.get(teachers.size() - 1);
		lastTeacher.setRoles(teacherRoleWithAdmin);
		return teachers;
	}

	@Override
	public List<Teacher> generate() {
		throw new UnsupportedOperationException();
	}
}
