package ua.foxminded.lezhenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.service.TablesCleanerService;
import ua.foxminded.lezhenin.service.impl.CourseServiceImpl;
import ua.foxminded.lezhenin.service.impl.GroupServiceImpl;
import ua.foxminded.lezhenin.service.impl.LessonServiceImpl;
import ua.foxminded.lezhenin.service.impl.RoleServiceImpl;
import ua.foxminded.lezhenin.service.impl.ScheduleForDayServiceImpl;
import ua.foxminded.lezhenin.service.impl.StudentServiceImpl;
import ua.foxminded.lezhenin.service.impl.TeacherServiceImpl;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
	private CourseServiceImpl courseServiceImpl;
	private RoleServiceImpl roleServiceImpl;
	private GroupServiceImpl groupServiceImpl;
	private StudentServiceImpl studentServiceImpl;
	private TeacherServiceImpl teacherServiceImpl;
	private ScheduleForDayServiceImpl scheduleForDayServiceImpl;
	private LessonServiceImpl lessonServiceImpl;
	private TablesCleanerService tablesCleanerService;

	@Autowired
	public Runner(CourseServiceImpl courseServiceImpl, GroupServiceImpl groupServiceImpl,
			StudentServiceImpl studentServiceImpl, TeacherServiceImpl teacherServiceImpl,
			ScheduleForDayServiceImpl scheduleForDayServiceImpl, LessonServiceImpl lessonServiceImpl,
			TablesCleanerService tablesCleanerService, RoleServiceImpl roleServiceImpl) {
		this.courseServiceImpl = courseServiceImpl;
		this.groupServiceImpl = groupServiceImpl;
		this.studentServiceImpl = studentServiceImpl;
		this.teacherServiceImpl = teacherServiceImpl;
		this.scheduleForDayServiceImpl = scheduleForDayServiceImpl;
		this.lessonServiceImpl = lessonServiceImpl;
		this.tablesCleanerService = tablesCleanerService;
		this.roleServiceImpl = roleServiceImpl;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Runner called");
		tablesCleanerService.cleanTables();
		courseServiceImpl.createCourses();
		groupServiceImpl.createGroups();
		roleServiceImpl.createRoles();
		studentServiceImpl.createStudents();
		teacherServiceImpl.createTeachers();
		scheduleForDayServiceImpl.createSchedulesForDay();
		lessonServiceImpl.createLessons();
		
	}
}
