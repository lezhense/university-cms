package ua.foxminded.lezhenin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.dto.CourseDto;
import ua.foxminded.lezhenin.exeptions.CourseAlreadyExistException;
import ua.foxminded.lezhenin.generator.CoursesGeneratorImpl;
import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.repository.CourseRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class CourseServiceImpl implements EntityService<Course> {
	private final CourseRepository courseRepository;
	private final CoursesGeneratorImpl coursesGeneratorImpl;
	private static final String COURSES_NAMES_PATH = "src/main/resources/CoursesNames.txt";

	@Autowired
	public CourseServiceImpl(CourseRepository courseRepository, CoursesGeneratorImpl coursesGeneratorImpl) {
		this.courseRepository = courseRepository;
		this.coursesGeneratorImpl = coursesGeneratorImpl;
	}

	@Override
	public List<Course> getAll() {
		List<Course> courses = courseRepository.findAll();
		log.info("List of all courses was found");
		return courses;

	}

	@Override
	public Course get(Integer id) {
		Optional<Course> courseOpt = courseRepository.findById(id);
		Course course = new Course();
		if (courseOpt.isPresent()) {
			course = courseOpt.get();
			log.info("Course with id {} was found", id);
		} else {
			log.info("Course with id {} was not found", id);
		}
		return course;
	}

	@Override
	public Course save(Course entity) {
		Course course = courseRepository.save(entity);
		log.info("Course with name {} was saved", entity.getName());
		return course;
	}

	@Override
	public void delete(Integer id) {
		courseRepository.deleteById(id);
		log.info("Course with id {} was deleted", id);
	}

	@Transactional
	public void createCourses() {
		List<Course> courses = coursesGeneratorImpl.generate(COURSES_NAMES_PATH);
		for (Course course : courses) {
			courseRepository.save(course);
		}
		log.info("Courses were created");
	}

	@Override
	public Course getByName(String name) {
		Optional<Course> courseOpt = courseRepository.findByName(name);
		Course course = new Course();
		if (courseOpt.isPresent()) {
			course = courseOpt.get();
			log.info("Course with name {} was found", name);
		} else {
			log.info("Course with name {} was not found", name);
		}
		return course;
	}

	@Transactional
	public void createCourse(CourseDto courseDto) throws CourseAlreadyExistException {
		if (checkIfCourseExist(courseDto.getName())) {
			throw new CourseAlreadyExistException("Course already exists for this name");
		}
		Course course = new Course();
		BeanUtils.copyProperties(courseDto, course);
		courseRepository.save(course);
	}

	private boolean checkIfCourseExist(String name) {
		return courseRepository.findByName(name).isPresent();
	}
	
}
