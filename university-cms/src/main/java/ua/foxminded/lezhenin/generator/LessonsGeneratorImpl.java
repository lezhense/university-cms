package ua.foxminded.lezhenin.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.foxminded.lezhenin.model.Course;
import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.model.Lesson;
import ua.foxminded.lezhenin.model.ScheduleForDay;
import ua.foxminded.lezhenin.model.Teacher;
import ua.foxminded.lezhenin.service.impl.CourseServiceImpl;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.ScheduleForDayServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@Component
public class LessonsGeneratorImpl implements Generator<List<Lesson>, String> {
	private final CourseServiceImpl courseService;
	private final GroupServiceImpl groupService;
	private final TeacherServiceImpl teacherService;
	private final ScheduleForDayServiceImpl scheduleForDayService;

	@Autowired
	public LessonsGeneratorImpl(CourseServiceImpl courseService, GroupServiceImpl groupService,
			TeacherServiceImpl teacherService, ScheduleForDayServiceImpl scheduleForDayService) {
		this.courseService = courseService;
		this.groupService = groupService;
		this.teacherService = teacherService;
		this.scheduleForDayService = scheduleForDayService;
	}

	@Override
	public List<Lesson> generate() {
		List<Course> courses = courseService.getAll();
		List<Group> groups = groupService.getAll();
		List<Teacher> teachers = teacherService.getAll();
		Teacher firstTeacher = teachers.get(0);
		Teacher secondTeacher = teachers.get(1);
		List<ScheduleForDay> schedulesForDay = scheduleForDayService.getAll();
		Set<Group> setOfGroups1 = new HashSet<>();
		for (int i = 0; i < groups.size() / 2; i++) {
			setOfGroups1.add(groups.get(i));
		}
		Set<Group> setOfGroups2 = new HashSet<>();
		for (int i = groups.size() / 2; i < groups.size(); i++) {
			setOfGroups2.add(groups.get(i));
		}
		List<Lesson> lessons = new ArrayList<>();
		for (int i = 0; i < courses.size() / 2; i++) {
			Lesson lesson = new Lesson();
			lesson.setCourse(courses.get(i));
			lesson.setGroups(setOfGroups1);
			lesson.setTeacher(firstTeacher);
			lesson.setScheduleForDay(schedulesForDay.get(i));
			lessons.add(lesson);
		}
		for (int i = courses.size() / 2; i < courses.size(); i++) {
			Lesson lesson = new Lesson();
			lesson.setCourse(courses.get(i));
			lesson.setGroups(setOfGroups2);
			lesson.setTeacher(secondTeacher);
			lesson.setScheduleForDay(schedulesForDay.get(i));
			lessons.add(lesson);
		}
		return lessons;
	}

	@Override
	public List<Lesson> generate(String e) {
		throw new UnsupportedOperationException();
	}

}
