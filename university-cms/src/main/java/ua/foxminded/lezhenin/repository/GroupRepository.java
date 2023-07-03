package ua.foxminded.lezhenin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.lezhenin.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

	public Optional<Group> findByName(String name);

	@Modifying
	@Query(value = "TRUNCATE TABLE cms.groups CASCADE", nativeQuery = true)
	public void clearTable();

	@Modifying
	@Query(value = "ALTER SEQUENCE cms.groups_id_seq RESTART WITH 1", nativeQuery = true)
	public void restartSequence();

}
