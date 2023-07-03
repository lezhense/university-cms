package ua.foxminded.lezhenin.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.model.ScheduleForDay;
import ua.foxminded.lezhenin.util.Reader;

@Component
@Slf4j
public class ScheduleForDayGeneratorImpl implements Generator<List<ScheduleForDay>, String> {
	private final Reader reader;

	@Autowired
	public ScheduleForDayGeneratorImpl(Reader reader) {
		this.reader = reader;
	}

	@Override
	public List<ScheduleForDay> generate(String datesPath) {
		List<String> scheduleDates = reader.getListFromFile(datesPath);
		List<ScheduleForDay> scheduleForDays = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (int i = 0; i < scheduleDates.size(); i++) {
			try {
				Date scheduleDate = simpleDateFormat.parse(scheduleDates.get(i));
				scheduleForDays.add(new ScheduleForDay(scheduleDate));
			} catch (ParseException e) {
				log.info("Exceptions happen!", e);
			}
		}
		return scheduleForDays;
	}

	@Override
	public List<ScheduleForDay> generate() {
		throw new UnsupportedOperationException();
	}
}
