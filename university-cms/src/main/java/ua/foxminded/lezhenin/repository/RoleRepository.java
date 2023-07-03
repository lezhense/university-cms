package ua.foxminded.lezhenin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.lezhenin.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Optional<Role> findByName(String name);

	@Modifying
	@Query(value = "TRUNCATE TABLE cms.roles CASCADE", nativeQuery = true)
	public void clearTable();

	@Modifying
	@Query(value = "ALTER SEQUENCE cms.roles_id_seq RESTART WITH 1", nativeQuery = true)
	public void restartSequence();

}
