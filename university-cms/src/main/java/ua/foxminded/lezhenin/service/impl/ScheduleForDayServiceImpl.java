package ua.foxminded.lezhenin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.generator.ScheduleForDayGeneratorImpl;
import ua.foxminded.lezhenin.model.ScheduleForDay;
import ua.foxminded.lezhenin.repository.ScheduleForDayRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class ScheduleForDayServiceImpl implements EntityService<ScheduleForDay> {
	private final ScheduleForDayRepository scheduleForDayRepository;
	private final ScheduleForDayGeneratorImpl scheduleForDayGenerator;
	private static final String SCHEDULES_DATES_NAMES_PATH = "src/main/resources/SchedulesDates.txt";

	@Autowired
	public ScheduleForDayServiceImpl(ScheduleForDayRepository scheduleForDayRepository,
			ScheduleForDayGeneratorImpl scheduleForDayGenerator) {
		this.scheduleForDayRepository = scheduleForDayRepository;
		this.scheduleForDayGenerator = scheduleForDayGenerator;
	}

	@Override
	public List<ScheduleForDay> getAll() {
		List<ScheduleForDay> schedulesForDay = scheduleForDayRepository.findAll();
		log.info("List of all schedulesForDay was found");
		return schedulesForDay;
	}

	@Override
	public ScheduleForDay get(Integer id) {
		Optional<ScheduleForDay> scheduleForDayOpt = scheduleForDayRepository.findById(id);
		ScheduleForDay scheduleForDay = new ScheduleForDay();
		if (scheduleForDayOpt.isPresent()) {
			scheduleForDay = scheduleForDayOpt.get();
			log.info("ScheduleForDay with id {} was found", id);
		} else {
			log.info("ScheduleForDay with id {} was not found", id);
		}
		return scheduleForDay;
	}

	@Override
	public ScheduleForDay save(ScheduleForDay entity) {
		ScheduleForDay scheduleForDay = scheduleForDayRepository.save(entity);
		log.info("ScheduleForDay with id {} was saved", entity.getId());
		return scheduleForDay;
	}

	@Override
	public void delete(Integer id) {
		scheduleForDayRepository.deleteById(id);
		log.info("ScheduleForDay with id {} was deleted", id);
	}

	@Transactional
	public void createSchedulesForDay() {
		List<ScheduleForDay> schedulesForDay = scheduleForDayGenerator.generate(SCHEDULES_DATES_NAMES_PATH);
		for (ScheduleForDay scheduleForDay : schedulesForDay) {
			scheduleForDayRepository.save(scheduleForDay);
		}
		log.info("SchedulesForDay were created");
	}

	@Override
	public ScheduleForDay getByName(String name) {
		throw new UnsupportedOperationException();
	}

}
