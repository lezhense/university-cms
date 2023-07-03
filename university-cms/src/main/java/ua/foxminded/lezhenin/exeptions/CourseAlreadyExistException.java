package ua.foxminded.lezhenin.exeptions;

@SuppressWarnings("serial")
public class CourseAlreadyExistException extends RuntimeException {
	public CourseAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}
}
