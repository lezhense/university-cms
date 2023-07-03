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

import ua.foxminded.lezhenin.generator.CoursesGeneratorImpl;
import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
	@InjectMocks
	CourseServiceImpl courseServiceImpl;
	@Mock
	CourseRepository courseRepository;
	@Mock
	CoursesGeneratorImpl coursesGeneratorImpl;

	@Test
	void testCreateCourses_shouldCreateCourses() {
		List<Course> courses = new ArrayList<>(Arrays.asList(new Course("Sport"), new Course("Law")));
		when(coursesGeneratorImpl.generate(anyString())).thenReturn(courses);
		courseServiceImpl.createCourses();
		verify(courseRepository, times(2)).save(any());
	}

}
