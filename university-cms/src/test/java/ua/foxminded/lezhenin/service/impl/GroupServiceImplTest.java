package ua.foxminded.lezhenin.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.lezhenin.generator.GroupsGeneratorImpl;
import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.repository.GroupRepository;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
	@InjectMocks
	GroupServiceImpl groupServiceImpl;
	@Mock
	GroupRepository groupRepository;
	@Mock
	GroupsGeneratorImpl groupsGeneratorImpl;

	@Test
	void testCreateGroups_shouldCreateGroups() {
		List<Group> groups = new ArrayList<>(Arrays.asList(new Group("Test-01"), new Group("Test-02")));
		when(groupsGeneratorImpl.generate(anyString())).thenReturn(groups);
		groupServiceImpl.createGroups();
		verify(groupRepository, times(2)).save(any());
	}

}
