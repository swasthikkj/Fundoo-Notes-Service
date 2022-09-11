package com.bridgelabz.fundoonotesservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotesservice.dto.NotesDTO;
import com.bridgelabz.fundoonotesservice.exception.NotesNotFoundException;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.repository.NotesRepository;
import com.bridgelabz.fundoonotesservice.util.Response;
import com.bridgelabz.fundoonotesservice.util.TokenUtil;

@Service
public class NotesService implements INotesService {
	@Autowired
	NotesRepository notesRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	MailService mailService;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public NotesModel createNotes(NotesDTO notesDTO, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			NotesModel model = new NotesModel(notesDTO);
			notesRepository.save(model);
			String body = "Notes added successfully with noteId " + model.getId();
			String subject = "Notes created successfully";
			mailService.send(model.getEmailId(), subject, body);		
			return model;
		}
		throw new NotesNotFoundException(400, "Notes not created");
	}

	@Override
	public NotesModel updateNotes(NotesDTO notesDTO, Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotePresent = notesRepository.findById(id);
			if(isNotePresent.isPresent()) {
				isNotePresent.get().setTitle(notesDTO.getTitle());
				isNotePresent.get().setDescription(notesDTO.getDescription());
				isNotePresent.get().setUserId(notesDTO.getUserId());
				isNotePresent.get().setLabelId(notesDTO.getLabelId());
				isNotePresent.get().setEmailId(notesDTO.getEmailId());
				isNotePresent.get().setColor(notesDTO.getColor());
				isNotePresent.get().setUpdateDate(notesDTO.getUpdateDate().now());
				isNotePresent.get().setReminderTime(notesDTO.getReminderTime().now());
				notesRepository.save(isNotePresent.get());
				String body = "Note updated successfully with note id" + isNotePresent.get().getId();
				String subject = "Note updated Successfully";
				mailService.send(isNotePresent.get().getEmailId(), subject, body);
				return isNotePresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	@Override
	public List<NotesModel> readAllNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> readAllNotes = notesRepository.findAll();
			if(readAllNotes.size()>0) {
				return readAllNotes;	
			} 
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	@Override
	public Optional<NotesModel> readNotesById(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				return isNotesPresent;
			} 
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	@Override
	public NotesModel archeiveNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setArchieve(true);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not find");
	}
	
	@Override
	public NotesModel unArcheiveNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setArchieve(false);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not find");
	}
	
	public NotesModel trashNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setTrash(true);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not find");
	}
	
	@Override
	public NotesModel restoreNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotesPresent = notesRepository.findById(id);
			if(isNotesPresent.isPresent()) {
				isNotesPresent.get().setTrash(false);
				notesRepository.save(isNotesPresent.get());
				return isNotesPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not find");
	}
	
	@Override
	public Response deleteNote(Long id, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<NotesModel> isUserPresent = notesRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			Optional<NotesModel> isIdPresent = notesRepository.findById(id);
			if(isIdPresent.isPresent()) {
				notesRepository.delete(isIdPresent.get());
				return new Response(200, "Success", isIdPresent.get());
			} else {
				throw new NotesNotFoundException(400, "User not found");
			}		
		}
		throw new NotesNotFoundException(400, "Invalid token");
	}
	
	@Override
	public NotesModel changeNoteColour(Long id, String colour, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isIdPresent = notesRepository.findById(id);
			if(isIdPresent.isPresent()) {
				isIdPresent.get().setColor(colour);
				notesRepository.save(isIdPresent.get());
				return isIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not find");
	}
	
	@Override
	public NotesModel pinNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isIdPresent = notesRepository.findById(id);
			if(isIdPresent.isPresent()) {
				isIdPresent.get().setPin(true);
				notesRepository.save(isIdPresent.get());
				return isIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	@Override
	public NotesModel unpinNote(Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isIdPresent = notesRepository.findById(id);
			if(isIdPresent.isPresent()) {
				isIdPresent.get().setPin(false);
				notesRepository.save(isIdPresent.get());
				return isIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
			}
		throw new NotesNotFoundException(400, "Token not found");
	}
}