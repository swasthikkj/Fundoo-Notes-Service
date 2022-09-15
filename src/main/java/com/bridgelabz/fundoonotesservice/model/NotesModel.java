package com.bridgelabz.fundoonotesservice.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bridgelabz.fundoonotesservice.dto.NotesDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose:Model class for notes service
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Entity
@Table(name = "NotesService")
@Data
@NoArgsConstructor
public class NotesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	private String title;
	private String description;
	private long userId;
	private LocalDateTime registerDate;
	private LocalDateTime updateDate;
	private boolean trash;
	private boolean isArchieve;
	private boolean pin;
	private Long labelId;
	private String emailId;
	private String color;
	private String reminderTime;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<LabelModel> labellist;

    @ElementCollection(targetClass = String.class)
     private List<String> collaborator;

	public NotesModel(NotesDTO notesDTO) {
		this.title = notesDTO.getTitle();
		this.description = notesDTO.getDescription();
		this.trash = notesDTO.isTrash();
		this.isArchieve = notesDTO.isArchieve();
		this.pin = notesDTO.isPin();
		this.labelId = notesDTO.getLabelId();
		this.emailId = notesDTO.getEmailId();
		this.color = notesDTO.getColor();
	}	
}
