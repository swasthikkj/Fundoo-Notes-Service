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
	
	List<NotesModel> getAllPinNotes(String token);

	List<NotesModel> getAllArchievedNotes(String token);

	List<NotesModel> getAllTrashNotes(String token);

	NotesModel noteLabelList(Long noteId, Long labelId, String token);

	NotesModel addCollaborator(String emailId, Long noteId, List<String> collaborator);

	NotesModel remainder(String token, Long noteId, String remaindTime);
	
}
