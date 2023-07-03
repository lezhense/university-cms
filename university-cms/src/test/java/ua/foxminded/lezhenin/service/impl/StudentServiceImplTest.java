package ua.foxminded.lezhenin.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.lezhenin.generator.StudentGeneratorImpl;
import ua.foxminded.lezhenin.model.Student;
import ua.foxminded.lezhenin.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
	@InjectMocks
	StudentServiceImpl studentServiceImpl;
	@Mock
	StudentRepository studentRepository;
	@Mock
	StudentGeneratorImpl studentGeneratorImpl;

	@Test
	void testCreateStudents_shouldCreateStudents() {
		List<Student> students = new ArrayList<>(
				Arrays.asList(new Student("Papa1", "Roach1", "Papa1Roach1", "123"),
						new Student("Papa2", "Roach2", "Papa2Roach2", "123")));
		when(studentGeneratorImpl.generate(anyString())).thenReturn(students);
		studentServiceImpl.createStudents();
		verify(studentRepository, times(2)).save(any());
	}

}
