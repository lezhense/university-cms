package ua.foxminded.lezhenin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.lezhenin.dto.GroupDto;
import ua.foxminded.lezhenin.exeptions.GroupAlreadyExistException;
import ua.foxminded.lezhenin.generator.GroupsGeneratorImpl;
import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.repository.GroupRepository;
import ua.foxminded.lezhenin.service.EntityService;

@Service
@Slf4j
public class GroupServiceImpl implements EntityService<Group> {
	private final GroupRepository groupRepository;
	private final GroupsGeneratorImpl groupsGeneratorImpl;
	private static final String GROUPS_NAMES_PATH = "src/main/resources/GroupsNames.txt";

	@Autowired
	public GroupServiceImpl(GroupRepository groupRepository, GroupsGeneratorImpl groupsGeneratorImpl) {
		this.groupRepository = groupRepository;
		this.groupsGeneratorImpl = groupsGeneratorImpl;

	}

	@Override
	public List<Group> getAll() {
		List<Group> groups = groupRepository.findAll();
		log.info("List of all groups was found");
		return groups;
	}

	@Override
	public Group get(Integer id) {
		Optional<Group> groupOpt = groupRepository.findById(id);
		Group group = new Group();
		if (groupOpt.isPresent()) {
			group = groupOpt.get();
			log.info("Group with id {} was found", id);
		} else {
			log.info("Group with id {} was not found", id);
		}
		return group;
	}

	@Override
	public Group save(Group entity) {
		Group group = groupRepository.save(entity);
		log.info("Group with name {} was saved", entity.getName());
		return group;
	}

	@Override
	public void delete(Integer id) {
		groupRepository.deleteById(id);
		log.info("Group with id {} was deleted", id);
	}

	@Transactional
	public void createGroups() {
		List<Group> groups = groupsGeneratorImpl.generate(GROUPS_NAMES_PATH);
		for (Group group : groups) {
			groupRepository.save(group);
		}
		log.info("Groups were created");
	}

	@Override
	public Group getByName(String name) {
		Optional<Group> groupOpt = groupRepository.findByName(name);
		Group group = new Group();
		if (groupOpt.isPresent()) {
			group = groupOpt.get();
			log.info("Group with name {} was found", name);
		} else {
			log.info("Group with name {} was not found", name);
		}
		return group;
	}
	
	@Transactional
	public void createGroup(GroupDto groupDto) throws GroupAlreadyExistException {
		if (checkIfGroupExist(groupDto.getName())) {
			throw new GroupAlreadyExistException("Group already exists for this name");
		}
		Group group = new Group();
		BeanUtils.copyProperties(groupDto, group);
		groupRepository.save(group);
	}

	private boolean checkIfGroupExist(String name) {
		return groupRepository.findByName(name).isPresent();
	}

}
