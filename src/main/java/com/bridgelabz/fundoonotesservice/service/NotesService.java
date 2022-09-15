package com.bridgelabz.fundoonotesservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotesservice.dto.NotesDTO;
import com.bridgelabz.fundoonotesservice.exception.NotesNotFoundException;
import com.bridgelabz.fundoonotesservice.model.LabelModel;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.repository.LabelRepository;
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

	@Autowired
	LabelRepository labelRepository;
	
	/**
	 * Purpose:create notes and notify through email
	 */
	
	@Override
	public NotesModel createNotes(NotesDTO notesDTO, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			NotesModel model = new NotesModel(notesDTO);
			model.setRegisterDate(LocalDateTime.now());
			notesRepository.save(model);		
			return model;
		}
		throw new NotesNotFoundException(400, "Notes not created");
	}
	
	/**
	 * Purpose:update notes
	 */
	
	@Override
	public NotesModel updateNotes(NotesDTO notesDTO, Long id, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotePresent = notesRepository.findById(id);
			if(isNotePresent.isPresent()) {
				isNotePresent.get().setTitle(notesDTO.getTitle());
				isNotePresent.get().setDescription(notesDTO.getDescription());
				isNotePresent.get().setLabelId(notesDTO.getLabelId());
				isNotePresent.get().setEmailId(notesDTO.getEmailId());
				isNotePresent.get().setColor(notesDTO.getColor());
				isNotePresent.get().setUpdateDate(LocalDateTime.now());
				notesRepository.save(isNotePresent.get());
				return isNotePresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	/**
	 * Purpose:fetch all notes
	 */
	
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
	
	/**
	 * Purpose:fetch notes by id
	 */
	
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
	
	/**
	 * Purpose:archeive notes
	 */
	
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
	
	/**
	 * Purpose:unarcheive notes
	 */
	
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
	
	/**
	 * Purpose:send notes to trash
	 */
	
	@Override
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
	
	/**
	 * Purpose:restore notes from trash
	 */
	
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
	
	/**
	 * Purpose:delete notes
	 */
	
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
	
	/**
	 * Purpose:change note color
	 */
	
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
	
	/**
	 * Purpose:pin note
	 */
	
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
	
	/**
	 * Purpose:unpin note
	 */
	
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
	
	/**
	 * Purpose:fetch all pinned notes
	 */
	
	@Override
	public List<NotesModel> getAllPinNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> isNotePresent = notesRepository.findAllByPin();
			if(isNotePresent.size() > 0) {
				return isNotePresent;
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	/**
	 * Purpose:fetch all archeived notes
	 */
	
	@Override
	public List<NotesModel> getAllArchievedNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> isNotePresent = notesRepository.findAllByArchieved();
			if(isNotePresent.size() > 0) {
				return isNotePresent;
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	/**
	 * Purpose:fetch all notes from trash
	 */
	
	@Override
	public List<NotesModel> getAllTrashNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<NotesModel> isNotePresent = notesRepository.findAllByTrash();
			if(isNotePresent.size() > 0) {
				return isNotePresent;
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	/**
	 * Purpose:note label list
	 */
	
	@Override
	public NotesModel noteLabelList(Long noteId, Long labelId,  String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> label = new ArrayList<>();
			Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				label.add(isLabelPresent.get());
				Optional<NotesModel> isNotesPresent = notesRepository.findById(noteId);
				if(isNotesPresent.isPresent()) {
					isNotesPresent.get().setLabellist(label);
					notesRepository.save(isNotesPresent.get());
					return isNotesPresent.get();
				}
				throw new NotesNotFoundException(400, "Notes not present");	
			}
			throw new NotesNotFoundException(400, "Label id not found");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	/**
	 * Purpose:To collaborate 
	 */
	
	@Override
	public NotesModel addCollaborator(String emailId, Long noteId, List<String> collaborator) {
		boolean isEmailPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateemail/" + emailId, Boolean.class);
		if (isEmailPresent) {
			List<String> collaboratorList = new ArrayList<>();
			collaborator.stream().forEach(collaborate -> {
				boolean isEmailIdPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateemail/" + collaborate, Boolean.class);
				if (isEmailPresent) {
					collaboratorList.add(collaborate);
				}
			});
			Optional<NotesModel> isNotePresent = notesRepository.findById(noteId);
			if(collaboratorList.size()>0) {
				isNotePresent.get().setCollaborator(collaboratorList);
				notesRepository.save(isNotePresent.get());
				return isNotePresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
	
	/**
	 * Purpose:remaind user
	 */
	
	@Override
	public NotesModel remainder(String token, Long noteId, String remaindTime) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundoouserservice/validateuser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isNotePresent = notesRepository.findById(noteId);
			if(isNotePresent.isPresent()) {
				LocalDate today = LocalDate.now();
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				LocalDate remainder = LocalDate.parse(remaindTime, dateTimeFormatter);
				if(remainder.isBefore(today)) {
					throw new NotesNotFoundException(400, "invalid remainder time");
				}
				isNotePresent.get().setReminderTime(remaindTime);
				notesRepository.save(isNotePresent.get());
				return isNotePresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
}