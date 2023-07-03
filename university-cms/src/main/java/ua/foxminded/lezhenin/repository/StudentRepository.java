package ua.foxminded.lezhenin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	public Optional<Student> findByFirstName(String firstName);

	public Optional<Student> findByUsername(String username);

	public List<Student> findByGroup(Group group);

	@Modifying
	@Query(value = "TRUNCATE TABLE cms.students CASCADE", nativeQuery = true)
	public void clearTable();

	@Modifying
	@Query(value = "ALTER SEQUENCE cms.students_id_seq RESTART WITH 1", nativeQuery = true)
	public void restartSequence();

}
