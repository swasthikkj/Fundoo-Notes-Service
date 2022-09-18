package com.bridgelabz.fundoonotesservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesservice.dto.NotesDTO;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.service.INotesService;
import com.bridgelabz.fundoonotesservice.util.Response;

/**
 * Purpose:create notes service controller
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@RestController
@RequestMapping("/fundooNoteService")
public class NotesController {
	@Autowired
	INotesService notesService;

	/**
	 * Purpose:create notes
	 */

	@PostMapping("/createNotes")
	public ResponseEntity<Response> createNotes(@Valid @RequestBody NotesDTO notesDTO, @RequestHeader String token) {
		NotesModel notesModel = notesService.createNotes(notesDTO, token);
		Response response = new Response(200, "Notes created successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose:update notes
	 */

	@PutMapping("/updateNotes/{noteId}")
	public ResponseEntity<Response> updateNotes(@PathVariable Long noteId, @Valid @RequestBody NotesDTO notesDTO, @RequestHeader String token) {
		NotesModel notesModel = notesService.updateNotes(noteId, notesDTO, token);
		Response response = new Response(200, "Notes updated successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:read all notes
	 */

	@GetMapping("/readAllNotes")
	public ResponseEntity<Response> readAllNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.readAllNotes(token);
		Response response = new Response(200, "Fetching all notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:read notes by id
	 */

	@GetMapping("/readNotesById/{noteId}")
	public ResponseEntity<Response> readNotesById(@PathVariable Long noteId, @RequestHeader String token){
		Optional<NotesModel> notesModel = notesService.readNotesById(noteId, token);
		Response response = new Response(200, "Fetching notes by id successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:archeive note by id
	 */

	@PutMapping("/archeiveNote/{noteId}")
	public ResponseEntity<Response> archeiveNote(@PathVariable Long noteId, @RequestHeader String token) {
		NotesModel notesModel = notesService.archeiveNote(noteId, token);
		Response response = new Response(200, "Note archeived successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:unarcheive note by id
	 */

	@PutMapping("/unArcheiveNote/{noteId}")
	public ResponseEntity<Response> unArcheiveNote(@PathVariable Long noteId, @RequestHeader String token) {
		NotesModel notesModel = notesService.unArcheiveNote(noteId, token);
		Response response = new Response(200, "Note unarcheived successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:trash note by id
	 */

	@PutMapping("/trashNote/{noteId}")
	public ResponseEntity<Response> trashNote(@PathVariable Long noteId, @RequestHeader String token) {
		NotesModel notesModel = notesService.trashNote(noteId, token);
		Response response = new Response(200, "Note trashed successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:restore note by id
	 */

	@PutMapping("/restoreNote/{noteId}")
	public ResponseEntity<Response> restoreNote(@PathVariable Long noteId, @RequestHeader String token) {
		NotesModel notesModel = notesService.restoreNote(noteId, token);
		Response response = new Response(200, "Note restored successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:delete note by id
	 */

	@DeleteMapping("/deleteNote/{noteId}")
	public ResponseEntity<Response> deleteNote(@PathVariable Long noteId, @RequestHeader String token) {
		Response notesModel = notesService.deleteNote(noteId, token);
		Response response = new Response(200, "Note deleted successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:change note colour
	 */

	@PutMapping("/changeNoteColour/{noteId}")
	public ResponseEntity<Response> changeNoteColour(@PathVariable Long noteId, @RequestParam String colour, @RequestHeader String token) {
		NotesModel notesModel = notesService.changeNoteColour(noteId, colour, token);
		Response response = new Response(200, "Note colour changed successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:pin note 
	 */

	@PutMapping("/pinNote/{noteId}")
	public ResponseEntity<Response> pinNote(@PathVariable Long noteId, @RequestHeader String token) {
		NotesModel notesModel = notesService.pinNote(noteId, token);
		Response response = new Response(200, "Note pinned successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:unpin note
	 */

	@PutMapping("/unpinNote/{noteId}")
	public ResponseEntity<Response> unpinNote(@PathVariable Long noteId, @RequestHeader String token) {
		NotesModel notesModel = notesService.unpinNote(noteId, token);
		Response response = new Response(200, "Note unpinned successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get all pinned notes
	 */

	@GetMapping("/getAllPinnedNotes")
	public ResponseEntity<Response> getAllPinNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.getAllPinNotes(token);
		Response response = new Response(200, "Fetching all pinned notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get all archieved notes
	 */

	@GetMapping("/getAllArchievedNotes")
	public ResponseEntity<Response> getAllArchievedNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.getAllArchievedNotes(token);
		Response response = new Response(200, "Fetching all archieved notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get all trash notes
	 */

	@GetMapping("/getAllTrashNotes")
	public ResponseEntity<Response> getAllTrashNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.getAllTrashNotes(token);
		Response response = new Response(200, "Fetching all trash notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get note label list
	 */

	@GetMapping("/noteLabelList")
	public ResponseEntity<Response> noteLabelList(@RequestParam Long noteId, @RequestParam Long labelId, @RequestHeader String token) {
		NotesModel notesModel = notesService.noteLabelList(noteId, labelId, token);
		Response response = new Response(200, "Fetching all note label list", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:To collaborate
	 */

	@PutMapping("/addCollaborator/{noteId}")
	public ResponseEntity<Response> addCollaborator(@RequestParam Long noteId, @RequestParam String collaborator, @RequestHeader String token) {
		NotesModel notesModel = notesService.addCollaborator(noteId, collaborator, token);
		Response response = new Response(200, "Collaborating notes", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:notify user
	 */

	@PutMapping("/remainder")
	public ResponseEntity<Response> remainder(@RequestHeader String token, @RequestParam Long noteId, @RequestParam String remaindTime) {
		NotesModel notesModel = notesService.remainder(token, noteId, remaindTime);
		Response response = new Response(200, "remainding", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
