package ua.foxminded.lezhenin.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.util.Reader;

@Component
public class CoursesGeneratorImpl implements Generator<List<Course>, String> {
	private final Reader reader;

	@Autowired
	public CoursesGeneratorImpl(Reader reader) {
		this.reader = reader;
	}

	@Override
	public List<Course> generate(String coursesNamesPath) {
		List<String> coursesNames = reader.getListFromFile(coursesNamesPath);
		List<Course> courses = new ArrayList<>();
		for (String courseName : coursesNames) {
			courses.add(new Course(courseName));
		}
		return courses;
	}

	@Override
	public List<Course> generate() {
		throw new UnsupportedOperationException();
	}
}
