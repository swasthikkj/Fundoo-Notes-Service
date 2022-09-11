package com.bridgelabz.fundoonotesservice.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotesservice.dto.NotesDTO;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.util.Response;

public interface INotesService {
	
	NotesModel createNotes(NotesDTO notesDTO, String token);
	
	NotesModel updateNotes(NotesDTO notesDTO, Long id, String token);
	
	List<NotesModel> readAllNotes(String token);
	
	Optional<NotesModel> readNotesById(Long id, String token);

	NotesModel archeiveNote(Long id, String token);

	NotesModel unArcheiveNote(Long id, String token);
	
	NotesModel trashNote(Long id, String token);

	NotesModel restoreNote(Long id, String token);

	Response deleteNote(Long id, String token);

	NotesModel changeNoteColour(Long id, String colour, String token);

	NotesModel pinNote(Long id, String token);

	NotesModel unpinNote(Long id, String token);
	
}
