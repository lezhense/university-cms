package ua.foxminded.lezhenin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.generator.RoleGeneratorImpl;
import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.repository.RoleRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class RoleServiceImpl implements EntityService<Role> {
	private final RoleRepository roleRepository;
	private final RoleGeneratorImpl roleGeneratorImpl;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository, RoleGeneratorImpl roleGeneratorImpl) {
		this.roleRepository = roleRepository;
		this.roleGeneratorImpl = roleGeneratorImpl;
	}

	@Override
	public List<Role> getAll() {
		List<Role> roles = roleRepository.findAll();
		log.info("List of all roles was found");
		return roles;

	}

	@Override
	public Role get(Integer id) {
		Optional<Role> roleOpt = roleRepository.findById(id);
		Role role = new Role();
		if (roleOpt.isPresent()) {
			role = roleOpt.get();
			log.info("Role with id {} was found", id);
		} else {
			log.info("Role with id {} was not found", id);
		}
		return role;
	}

	@Override
	public Role save(Role entity) {
		Role role = roleRepository.save(entity);
		log.info("Role with name {} was saved", entity.getName());
		return role;
	}

	@Override
	public void delete(Integer id) {
		roleRepository.deleteById(id);
		log.info("Role with id {} was deleted", id);
	}

	@Transactional
	public void createRoles() {
		List<Role> roles = roleGeneratorImpl.generate();
		for (Role role : roles) {
			roleRepository.save(role);
		}
		log.info("Roles were created");
	}

	@Override
	public Role getByName(String name) {
		Optional<Role> roleOpt = roleRepository.findByName(name);
		Role role = new Role();
		if (roleOpt.isPresent()) {
			role = roleOpt.get();
			log.info("Role with name {} was found", name);
		} else {
			log.info("Role with id {} was not found", name);
		}
		return role;
	}
}
