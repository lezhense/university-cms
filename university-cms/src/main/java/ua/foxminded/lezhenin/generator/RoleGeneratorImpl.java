package ua.foxminded.lezhenin.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ua.foxminded.lezhenin.model.Role;
import ua.foxminded.lezhenin.model.UserRole;

@Component
public class RoleGeneratorImpl implements Generator<List<Role>, String> {

	@Override
	public List<Role> generate(String coursesNamesPath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Role> generate() {
		List<Role> roles = new ArrayList<>();
		for (UserRole userRole : UserRole.values()) {
			roles.add(new Role(userRole.name()));
		}
		return roles;
	}
}
