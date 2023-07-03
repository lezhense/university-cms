package ua.foxminded.lezhenin.dto;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GroupDto {
	@NotNull
	@NotEmpty(message = "Name can not be empty")
	private String name;
}
