package ua.foxminded.lezhenin.service.impl;

import static org.mockito.ArgumentMatchers.any;
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

import ua.foxminded.lezhenin.generator.LessonsGeneratorImpl;
import ua.foxminded.lezhenin.model.Lesson;
import ua.foxminded.lezhenin.repository.LessonRepository;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
	@InjectMocks
	LessonServiceImpl lessonServiceImpl;
	@Mock
	LessonRepository lessonRepository;
	@Mock
	LessonsGeneratorImpl lessonsGenerator;

	@Test
	void testCreateLessons_shouldCreateLessons() {
		List<Lesson> lessons = new ArrayList<>(Arrays.asList(new Lesson()));
		when(lessonsGenerator.generate()).thenReturn(lessons);
		lessonServiceImpl.createLessons();
		verify(lessonRepository, times(1)).save(any());
	}

}
