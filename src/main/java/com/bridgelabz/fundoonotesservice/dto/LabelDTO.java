package com.bridgelabz.fundoonotesservice.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * Purpose:DTO for label
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Data
public class LabelDTO {
	@Pattern(regexp = "^[a-zA-Z\\s]{2,}$", message = "Label name is Invalid")
	private String labelName;
}
