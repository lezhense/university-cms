package ua.foxminded.lezhenin.service;

import java.util.List;

public interface EntityService<T> {
	List<T> getAll();

	T get(Integer id);

	T getByName(String name);

	T save(T entity);

	void delete(Integer id);
}
