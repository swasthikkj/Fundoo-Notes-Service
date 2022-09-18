package com.bridgelabz.fundoonotesservice.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bridgelabz.fundoonotesservice.dto.LabelDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose:Model class for label
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Entity
@Table(name = "Label")
@Data
@NoArgsConstructor
public class LabelModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="labelName")
	private String labelName;
	@Column(name="userId")
	private Long userId;
	@Column(name="noteId")
	private Long noteId;
	@Column(name = "registeredDate")
	private LocalDateTime registerDate;
	@Column(name = "UpdatedDate")
	private LocalDateTime updateDate;
	@JsonIgnore
	@ManyToMany
	private List<NotesModel> notes;
	
	public LabelModel(LabelDTO labelDTO) {
		this.labelName = labelDTO.getLabelName();
	}
}
