package ua.foxminded.lezhenin.generator;

public interface Generator<T, E> {

	T generate(E e);

	T generate();

}