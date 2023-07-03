package ua.foxminded.lezhenin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.lezhenin.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

	@Modifying
	@Query(value = "TRUNCATE TABLE cms.lessons CASCADE", nativeQuery = true)
	public void clearTable();

	@Modifying
	@Query(value = "ALTER SEQUENCE cms.lessons_id_seq RESTART WITH 1", nativeQuery = true)
	public void restartSequence();

}
