package ua.foxminded.lezhenin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.repository.CourseRepository;
import ua.foxminded.lezhenin.repository.GroupRepository;
import ua.foxminded.lezhenin.repository.LessonRepository;
import ua.foxminded.lezhenin.repository.RoleRepository;
import ua.foxminded.lezhenin.repository.ScheduleForDayRepository;
import ua.foxminded.lezhenin.repository.StudentRepository;
import ua.foxminded.lezhenin.repository.TeacherRepository;

@Service
@Slf4j
public class TablesCleanerService {
	private final StudentRepository studentRepository;
	private final GroupRepository groupRepository;
	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;
	private final LessonRepository lessonRepository;
	private final ScheduleForDayRepository scheduleForDayRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public TablesCleanerService(StudentRepository studentRepository, GroupRepository groupRepository,
			CourseRepository courseRepository, TeacherRepository teacherRepository, LessonRepository lessonRepository,
			ScheduleForDayRepository scheduleForDayRepository, RoleRepository roleRepository) {
		this.studentRepository = studentRepository;
		this.groupRepository = groupRepository;
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
		this.lessonRepository = lessonRepository;
		this.scheduleForDayRepository = scheduleForDayRepository;
		this.roleRepository = roleRepository;
	}

	@Transactional
	public void cleanTables() {
		lessonRepository.deleteAll();
		lessonRepository.clearTable();
		lessonRepository.restartSequence();
		log.info("Lessons were cleared");
		studentRepository.deleteAll();
		studentRepository.clearTable();
		studentRepository.restartSequence();
		log.info("Students were cleared");
		courseRepository.deleteAll();
		courseRepository.clearTable();
		courseRepository.restartSequence();
		log.info("Courses were cleared");
		scheduleForDayRepository.deleteAll();
		studentRepository.clearTable();
		scheduleForDayRepository.restartSequence();
		log.info("SchedulesForDay were cleared");
		teacherRepository.deleteAll();
		teacherRepository.clearTable();
		teacherRepository.restartSequence();
		log.info("Teachers were cleared");
		groupRepository.deleteAll();
		groupRepository.clearTable();
		groupRepository.restartSequence();
		log.info("Groups were cleared");
		roleRepository.deleteAll();
		roleRepository.clearTable();
		roleRepository.restartSequence();
		log.info("Roles were cleared");
	}

}
