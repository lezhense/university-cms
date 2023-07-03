package ua.foxminded.lezhenin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.dto.UserDto;
import ua.foxminded.lezhenin.exeptions.UserAlreadyExistException;
import ua.foxminded.lezhenin.generator.TeachersGeneratorImpl;
import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.repository.TeacherRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class TeacherServiceImpl implements EntityService<Teacher> {
	private final TeacherRepository teacherRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleServiceImpl roleService;
	private final TeachersGeneratorImpl teachersGeneratorImpl;
	private static final String TEACHERS_NAMES_PATH = "src/main/resources/TeachersNames.txt";
	private static final String TEACHER_ROLE_NAME = "TEACHER";

	@Autowired
	public TeacherServiceImpl(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder,
			RoleServiceImpl roleService, TeachersGeneratorImpl teachersGeneratorImpl) {
		this.teacherRepository = teacherRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.teachersGeneratorImpl = teachersGeneratorImpl;
	}

	@Override
	public List<Teacher> getAll() {
		List<Teacher> teachers = teacherRepository.findAll();
		log.info("List of all teachers was found");
		return teachers;
	}

	@Override
	public Teacher get(Integer id) {
		Optional<Teacher> teacherOpt = teacherRepository.findById(id);
		Teacher teacher = new Teacher();
		if (teacherOpt.isPresent()) {
			teacher = teacherOpt.get();
			log.info("Teacher with id {} was found", id);
		} else {
			log.info("Teacher with id {} was not found", id);
		}
		return teacher;
	}

	@Override
	@Transactional
	public Teacher save(Teacher entity) {
		Teacher teacher = entity;
		teacher.setPassword(passwordEncoder.encode(entity.getPassword()));
		log.info("Teacher with firstName {} was saved", entity.getFirstName());
		return teacherRepository.save(teacher);
	}
	
	@Override
	public void delete(Integer id) {
		teacherRepository.deleteById(id);
		log.info("Teacher with id {} was deleted", id);
	}

	@Transactional
	public void createTeachers() {
		List<Teacher> teachers = teachersGeneratorImpl.generate(TEACHERS_NAMES_PATH);
		for (Teacher teacher : teachers) {
			teacherRepository.save(teacher);
		}
		log.info("Teachers were created");
	}

	@Transactional
	public void register(UserDto userDto) throws UserAlreadyExistException {
		if (checkIfUserExist(userDto.getUsername())) {
			throw new UserAlreadyExistException("User already exists for this username");
		}
		Teacher teacher = new Teacher();
		BeanUtils.copyProperties(userDto, teacher);
		teacher.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.getByName(TEACHER_ROLE_NAME));
		teacher.setRoles(roles);
		teacherRepository.save(teacher);
	}

	private boolean checkIfUserExist(String username) {
		return teacherRepository.findByUsername(username).isPresent();
	}

	@Override
	public Teacher getByName(String username) {
		Optional<Teacher> teacherOpt = teacherRepository.findByUsername(username);
		Teacher teacher = new Teacher();
		if (teacherOpt.isPresent()) {
			teacher = teacherOpt.get();
			log.info("Teacher with username {} was found", username);
		} else {
			log.info("Teacher with username {} was not found", username);
		}
		return teacher;
	}

}
