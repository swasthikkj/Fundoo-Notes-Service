package com.bridgelabz.fundoonotesservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NotesNotFoundException extends RuntimeException {
	private int statuscode;
	private String message;

	public NotesNotFoundException(int statuscode, String message) {
		super(message);
		this.statuscode = statuscode;
		this.message = message;
	}
}
