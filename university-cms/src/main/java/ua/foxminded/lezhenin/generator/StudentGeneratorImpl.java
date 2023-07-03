package ua.foxminded.lezhenin.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.Student;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.util.Reader;

@Component
public class StudentGeneratorImpl implements Generator<List<Student>, String> {
	private final Reader reader;
	private final GroupServiceImpl groupService;
	private final RoleServiceImpl roleService;
	private final PasswordEncoder passwordEncoder;
	private static final String STUDENT_ROLE_NAME = "STUDENT";

	@Autowired
	public StudentGeneratorImpl(Reader reader, GroupServiceImpl groupService, PasswordEncoder passwordEncoder,
			RoleServiceImpl roleService) {
		this.reader = reader;
		this.groupService = groupService;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
	}

	@Override
	public List<Student> generate(String studentsNamesPath) {
		List<String> studentNames = reader.getListFromFile(studentsNamesPath);
		List<Group> groups = groupService.getAll();
		Group firstGroup = groups.get(0);
		Group secondGroup = groups.get(1);
		List<Student> students = new ArrayList<>();
		Set<Role> studentRoles = new HashSet<>();
		studentRoles.add(roleService.getByName(STUDENT_ROLE_NAME));
		String password = passwordEncoder.encode("123");
		for (int i = 0; i < studentNames.size() / 2; i++) {
			List<String> name = Stream.of(studentNames.get(i).split(" ")).toList();
			String firstName = name.get(0);
			String lastName = name.get(1);
			String username = firstName + lastName;
			Student student = new Student(firstName, lastName, username, password);
			student.setRoles(studentRoles);
			student.setGroup(firstGroup);
			students.add(student);
		}
		for (int i = studentNames.size() / 2; i < studentNames.size(); i++) {
			List<String> studentFullName = Stream.of(studentNames.get(i).split(" ")).toList();
			String firstName = studentFullName.get(0);
			String lastName = studentFullName.get(1);
			String username = firstName + lastName;
			Student student = new Student(firstName, lastName, username, password);
			student.setRoles(studentRoles);
			student.setGroup(secondGroup);
			students.add(student);
		}
		return students;
	}

	@Override
	public List<Student> generate() {
		throw new UnsupportedOperationException();
	}
}
