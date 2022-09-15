package com.bridgelabz.fundoonotesservice.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * Purpose:DTO for notes service
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Data
public class NotesDTO {
	@Pattern(regexp = "^[a-zA-Z\\s]{2,}$", message = "Title name is Invalid")
	private String title;
	@Pattern(regexp = "^[a-zA-Z\\s]{2,}$", message = "Description is Invalid")
	private String description;
	private boolean trash;
	private boolean isArchieve;
	private boolean pin;
	private Long labelId;
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "email is invalid")
	private String emailId;
	@Pattern(regexp = "^[a-zA-Z\\s]{2,}$", message = "Colour is Invalid")
	private String color;
}
