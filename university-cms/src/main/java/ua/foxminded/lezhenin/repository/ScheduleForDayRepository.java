package ua.foxminded.lezhenin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.lezhenin.model.ScheduleForDay;

@Repository
public interface ScheduleForDayRepository extends JpaRepository<ScheduleForDay, Integer> {

	@Modifying
	@Query(value = "TRUNCATE TABLE cms.schedule_for_day CASCADE", nativeQuery = true)
	public void clearTable();

	@Modifying
	@Query(value = "ALTER SEQUENCE cms.schedules_for_day_id_seq RESTART WITH 1", nativeQuery = true)
	public void restartSequence();

}
