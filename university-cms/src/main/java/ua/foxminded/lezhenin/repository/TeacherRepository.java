package ua.foxminded.lezhenin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.lezhenin.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	public Optional<Teacher> findByFirstName(String firstName);

	public Optional<Teacher> findByUsername(String username);

	@Modifying
	@Query(value = "TRUNCATE TABLE cms.teachers CASCADE", nativeQuery = true)
	public void clearTable();

	@Modifying
	@Query(value = "ALTER SEQUENCE cms.teachers_id_seq RESTART WITH 1", nativeQuery = true)
	public void restartSequence();

}
