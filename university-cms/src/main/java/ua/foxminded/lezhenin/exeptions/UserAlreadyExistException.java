package ua.foxminded.lezhenin.exeptions;

@SuppressWarnings("serial")
public class UserAlreadyExistException extends RuntimeException {
	public UserAlreadyExistException(String errorMessage) {
		super(errorMessage);
	}
}
