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
import ua.foxminded.lezhenin.generator.StudentGeneratorImpl;
import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.Student;
import ua.foxminded.lezhenin.repository.StudentRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class StudentServiceImpl implements EntityService<Student> {
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleServiceImpl roleService;
	private final StudentGeneratorImpl studentGeneratorImpl;
	private static final String STUDENT_NAMES_PATH = "src/main/resources/StudentsNames.txt";
	private static final String STUDENT_ROLE_NAME = "STUDENT";

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder,
			RoleServiceImpl roleService, StudentGeneratorImpl studentGeneratorImpl) {
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.studentGeneratorImpl = studentGeneratorImpl;
	}

	@Override
	public List<Student> getAll() {
		List<Student> students = studentRepository.findAll();
		log.info("List of all students was found");
		return students;
	}

	@Override
	public Student get(Integer id) {
		Optional<Student> studentOpt = studentRepository.findById(id);
		Student student = new Student();
		if (studentOpt.isPresent()) {
			student = studentOpt.get();
			log.info("Student with id {} was found", id);
		} else {
			log.info("Student with id {} was not found", id);
		}
		return student;
	}

	@Override
	@Transactional
	public Student save(Student entity) {
		Student student = entity;
		student.setPassword(passwordEncoder.encode(entity.getPassword()));
		log.info("Student with firstName {} was saved", entity.getFirstName());
		return studentRepository.save(student);
	}

	@Override
	public void delete(Integer id) {
		studentRepository.deleteById(id);
		log.info("Student with id {} was deleted", id);
	}

	@Transactional
	public void createStudents() {
		List<Student> students = studentGeneratorImpl.generate(STUDENT_NAMES_PATH);
		for (Student student : students) {
			studentRepository.save(student);
		}
		log.info("Students were created");
	}

	@Transactional
	public void register(UserDto userDto) throws UserAlreadyExistException {
		if (checkIfUserExist(userDto.getUsername())) {
			throw new UserAlreadyExistException("User already exists for this username");
		}
		Student student = new Student();
		BeanUtils.copyProperties(userDto, student);
		student.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.getByName(STUDENT_ROLE_NAME));
		student.setRoles(roles);
		studentRepository.save(student);
	}

	private boolean checkIfUserExist(String username) {
		return studentRepository.findByUsername(username).isPresent();
	}

	@Override
	public Student getByName(String username) {
		Optional<Student> studentOpt = studentRepository.findByUsername(username);
		Student student = new Student();
		if (studentOpt.isPresent()) {
			student = studentOpt.get();
			log.info("Student with username {} was found", username);
		} else {
			log.info("Student with username {} was not found", username);
		}
		return student;
	}

	public List<Student> getAllByGroup(Group group) {
		List<Student> studentsInGroup = studentRepository.findByGroup(group);
		log.info("List of all students in group {} was found", group.getName());
		return studentsInGroup;
	}

}
