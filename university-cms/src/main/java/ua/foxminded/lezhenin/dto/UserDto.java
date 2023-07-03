package ua.foxminded.lezhenin.dto;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
	@NotNull
	@NotEmpty(message = "First name can not be empty")
	private String firstName;

	@NotNull
	@NotEmpty(message = "Last name can not be empty")
	private String lastName;

	@NotNull
	@NotEmpty(message = "Username can not be empty")
	private String username;

	@NotNull
	@NotEmpty(message = "Password can not be empty")
	private String password;

}
