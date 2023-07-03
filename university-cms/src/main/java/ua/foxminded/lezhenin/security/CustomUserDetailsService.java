package ua.foxminded.lezhenin.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.foxminded.lezhenin.model.Student;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.repository.StudentRepository;
import ua.foxminded.lezhenin.repository.TeacherRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;

	@Autowired
	public CustomUserDetailsService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Student> student = studentRepository.findByUsername(username);
		if (student.isPresent()) {
			return new StudentDetails(student.get());
		}
		Optional<Teacher> teacher = teacherRepository.findByUsername(username);
		if (teacher.isPresent()) {
			return new TeacherDetails(teacher.get());
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
