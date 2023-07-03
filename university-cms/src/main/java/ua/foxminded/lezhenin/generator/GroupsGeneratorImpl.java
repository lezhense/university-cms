package ua.foxminded.lezhenin.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.foxminded.lezhenin.model.Group;
import ua.foxminded.lezhenin.util.Reader;

@Component
public class GroupsGeneratorImpl implements Generator<List<Group>, String> {
	private final Reader reader;

	@Autowired
	public GroupsGeneratorImpl(Reader reader) {
		this.reader = reader;
	}

	@Override
	public List<Group> generate(String groupsNamesPath) {
		List<String> groupsNames = reader.getListFromFile(groupsNamesPath);
		List<Group> groups = new ArrayList<>();
		for (String groupName : groupsNames) {
			groups.add(new Group(groupName));
		}
		return groups;
	}

	@Override
	public List<Group> generate() {
		throw new UnsupportedOperationException();
	}
}
