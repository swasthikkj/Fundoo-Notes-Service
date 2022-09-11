package com.bridgelabz.fundoonotesservice.controller;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/fundoonoteservice")
public class NotesController {
	@Autowired
	INotesService notesService;

	/**
	 * Purpose:create notes
	 */

	@PostMapping("/createnotes")
	public ResponseEntity<Response> createNotes(@RequestBody NotesDTO notesDTO, @RequestHeader String token) {
		NotesModel notesModel = notesService.createNotes(notesDTO, token);
		Response response = new Response(200, "Notes created successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose:update notes
	 */

	@PutMapping("/updatenotes/{id}")
	public ResponseEntity<Response> updateNotes(@RequestBody NotesDTO notesDTO, @PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.updateNotes(notesDTO, id, token);
		Response response = new Response(200, "Notes updated successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:read all notes
	 */

	@GetMapping("/readallnotes")
	public ResponseEntity<Response> readAllNotes(@RequestHeader String token) {
		List<NotesModel> notesModel = notesService.readAllNotes(token);
		Response response = new Response(200, "Fetching all notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:read notes by id
	 */

	@GetMapping("/readnotesbyid/{id}")
	public ResponseEntity<Response> readNotesById(@PathVariable Long id, @RequestHeader String token){
		Optional<NotesModel> notesModel = notesService.readNotesById(id, token);
		Response response = new Response(200, "Fetching notes by id successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:archeive note by id
	 */
	
	@PutMapping("/archeivenote/{id}")
	public ResponseEntity<Response> archeiveNote(@PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.archeiveNote(id, token);
		Response response = new Response(200, "Note archeived successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:unarcheive note by id
	 */
	
	@PutMapping("/unarcheivenote/{id}")
	public ResponseEntity<Response> unArcheiveNote(@PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.unArcheiveNote(id, token);
		Response response = new Response(200, "Note unarcheived successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:trash note by id
	 */
	
	@PutMapping("/trashNote/{id}")
	public ResponseEntity<Response> trashNote(@PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.trashNote(id, token);
		Response response = new Response(200, "Note trashed successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:restore note by id
	 */
	
	@GetMapping("/restoreNote/{id}")
	public ResponseEntity<Response> restoreNote(@PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.restoreNote(id, token);
		Response response = new Response(200, "Note restored successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:delete note by id
	 */
	
	@DeleteMapping("/deletenote/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable Long id, @RequestHeader String token) {
		Response notesModel = notesService.deleteNote(id, token);
		Response response = new Response(200, "Note deleted successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/changeNoteColour/{id}")
	public ResponseEntity<Response> changeNoteColour(@PathVariable Long id, @RequestParam String colour, @RequestHeader String token) {
		NotesModel notesModel = notesService.changeNoteColour(id, colour, token);
		Response response = new Response(200, "Note colour changed successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/pinNote/{id}")
	public ResponseEntity<Response> pinNote(@PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.pinNote(id, token);
		Response response = new Response(200, "Note pinned successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/unpinNote/{id}")
	public ResponseEntity<Response> unpinNote(@PathVariable Long id, @RequestHeader String token) {
		NotesModel notesModel = notesService.unpinNote(id, token);
		Response response = new Response(200, "Note unpinned successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
