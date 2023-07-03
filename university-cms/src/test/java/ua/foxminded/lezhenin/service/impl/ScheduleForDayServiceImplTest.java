package ua.foxminded.lezhenin.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.lezhenin.generator.ScheduleForDayGeneratorImpl;
import ua.foxminded.lezhenin.model.ScheduleForDay;
import ua.foxminded.lezhenin.repository.ScheduleForDayRepository;

@ExtendWith(MockitoExtension.class)
class ScheduleForDayServiceImplTest {
	@InjectMocks
	ScheduleForDayServiceImpl scheduleForDayServiceImpl;
	@Mock
	ScheduleForDayRepository scheduleForDayRepository;
	@Mock
	ScheduleForDayGeneratorImpl scheduleForDayGenerator;

	@Test
	void testCreateScheduleForDays() {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			List<ScheduleForDay> scheduleForDays = new ArrayList<>(
					Arrays.asList(new ScheduleForDay(simpleDateFormat.parse("16-02-2007"))));
			when(scheduleForDayGenerator.generate(anyString())).thenReturn(scheduleForDays);
			scheduleForDayServiceImpl.createSchedulesForDay();
			verify(scheduleForDayRepository, times(1)).save(any());
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
