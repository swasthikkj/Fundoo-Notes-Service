package com.bridgelabz.fundoonotesservice.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotesservice.dto.NotesDTO;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.util.Response;

/**
 * Purpose:Interface for notes service
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

public interface INotesService {
	
	NotesModel createNotes(NotesDTO notesDTO, String token);
	
	NotesModel updateNotes(Long noteId, NotesDTO notesDTO, String token);
	
	List<NotesModel> readAllNotes(String token);
	
	Optional<NotesModel> readNotesById(Long noteId, String token);

	NotesModel archeiveNote(Long noteId, String token);

	NotesModel unArcheiveNote(Long noteId, String token);
	
	NotesModel trashNote(Long noteId, String token);

	NotesModel restoreNote(Long noteId, String token);

	Response deleteNote(Long noteId, String token);

	NotesModel changeNoteColour(Long noteId, String colour, String token);

	NotesModel pinNote(Long noteId, String token);

	NotesModel unpinNote(Long noteId, String token);
	
	List<NotesModel> getAllPinNotes(String token);

	List<NotesModel> getAllArchievedNotes(String token);

	List<NotesModel> getAllTrashNotes(String token);

	NotesModel noteLabelList(Long noteId, Long labelId, String token);

	NotesModel addCollaborator(Long noteId, String collaborator, String token);

	NotesModel remainder(String token, Long noteId, String remaindTime);
	
}
