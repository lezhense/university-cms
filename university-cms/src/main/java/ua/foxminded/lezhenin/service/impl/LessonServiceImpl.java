package ua.foxminded.lezhenin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.generator.LessonsGeneratorImpl;
import ua.foxminded.lezhenin.model.Lesson;
import ua.foxminded.lezhenin.repository.LessonRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class LessonServiceImpl implements EntityService<Lesson> {
	private final LessonRepository lessonRepository;
	private final LessonsGeneratorImpl lessonsGenerator;

	@Autowired
	public LessonServiceImpl(LessonRepository lessonRepository, LessonsGeneratorImpl lessonsGenerator) {
		this.lessonRepository = lessonRepository;
		this.lessonsGenerator = lessonsGenerator;
	}

	@Override
	public List<Lesson> getAll() {
		List<Lesson> lessons = lessonRepository.findAll();
		log.info("List of all lessons was found");
		return lessons;
	}

	@Override
	public Lesson get(Integer id) {
		Optional<Lesson> lessonOpt = lessonRepository.findById(id);
		Lesson lesson = new Lesson();
		if (lessonOpt.isPresent()) {
			lesson = lessonOpt.get();
			log.info("Lesson with id {} was found", id);
		} else {
			log.info("Lesson with id {} was not found", id);
		}
		return lesson;
	}

	@Override
	public Lesson save(Lesson entity) {
		Lesson lesson = lessonRepository.save(entity);
		log.info("Lesson with id {} was saved", entity.getId());
		return lesson;
	}

	@Override
	public void delete(Integer id) {
		lessonRepository.deleteById(id);
		log.info("Lesson with id {} was deleted", id);
	}

	@Transactional
	public void createLessons() {
		List<Lesson> lessons = lessonsGenerator.generate();
		for (Lesson lesson : lessons) {
			lessonRepository.save(lesson);
		}
		log.info("Lessons were created");
	}

	@Override
	public Lesson getByName(String name) {
		throw new UnsupportedOperationException();
	}

}
