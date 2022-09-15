package com.bridgelabz.fundoonotesservice.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LabelDTO {
	@Pattern(regexp = "^[a-zA-Z\\s]{2,}$", message = "Label name is Invalid")
	private String labelName;
}
