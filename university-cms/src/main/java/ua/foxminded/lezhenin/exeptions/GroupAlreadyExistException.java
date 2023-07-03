package ua.foxminded.lezhenin.exeptions;

@SuppressWarnings("serial")
public class GroupAlreadyExistException extends RuntimeException {
	public GroupAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}
}
