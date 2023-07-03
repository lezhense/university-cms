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

import ua.foxminded.lezhenin.generator.TeachersGeneratorImpl;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
	@InjectMocks
	TeacherServiceImpl teacherServiceImpl;
	@Mock
	TeacherRepository teacherRepository;
	@Mock
	TeachersGeneratorImpl teachersGeneratorImpl;

	@Test
	void testCreateTeachers_shouldCreateTeachers() {
		List<Teacher> teachers = new ArrayList<>(
				Arrays.asList(new Teacher("Papa1", "Roach1", "Papa1Roach1", "123"),
						new Teacher("Papa2", "Roach2", "Papa2Roach2", "123")));
		when(teachersGeneratorImpl.generate(anyString())).thenReturn(teachers);
		teacherServiceImpl.createTeachers();
		verify(teacherRepository, times(2)).save(any());
	}

}
