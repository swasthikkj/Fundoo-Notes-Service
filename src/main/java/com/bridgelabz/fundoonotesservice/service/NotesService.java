package com.bridgelabz.fundoonotesservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.hql.internal.ast.exec.IdSubselectUpdateExecutor;
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
	 * Purpose:To create notes
	 */

	@Override
	public NotesModel createNotes(NotesDTO notesDTO, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long decode = tokenUtil.decodeToken(token);
			NotesModel model = new NotesModel(notesDTO);
			model.setUserId(decode);
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
	public NotesModel updateNotes(Long noteId, NotesDTO noteDTO, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if(isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setTitle(noteDTO.getTitle());
				isUserAndNoteIdPresent.get().setDescription(noteDTO.getDescription());
				isUserAndNoteIdPresent.get().setUserId(userId);
				isUserAndNoteIdPresent.get().setLabelId(noteDTO.getLabelId());
				isUserAndNoteIdPresent.get().setEmailId(noteDTO.getEmailId());
				isUserAndNoteIdPresent.get().setColor(noteDTO.getColor());
				isUserAndNoteIdPresent.get().setUpdateDate(LocalDateTime.now());
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Note Not Found");
		}
		throw new NotesNotFoundException(400, "Token Id Not Found");
	}

	/**
	 * Purpose:fetch all notes
	 */

	@Override
	public List<NotesModel> readAllNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);		
			List<NotesModel> readAllNotes = notesRepository.findByUserId(userId);
			if(readAllNotes.size()>0) {
				return readAllNotes;
			}			
		} 
		throw new NotesNotFoundException(400, "Notes not present");
	}

	/**
	 * Purpose:fetch notes by id
	 */

	@Override
	public Optional<NotesModel> readNotesById(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if(isUserAndNoteIdPresent.isPresent()) {
				return isUserAndNoteIdPresent;
			} 
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:archeive notes
	 */

	@Override
	public NotesModel archeiveNote(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if(isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setArchieve(true);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:unarcheive notes
	 */

	@Override
	public NotesModel unArcheiveNote(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if(isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setArchieve(false);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:send notes to trash
	 */

	@Override
	public NotesModel trashNote(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if (isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setTrash(true);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:restore notes from trash
	 */

	@Override
	public NotesModel restoreNote(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if (isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setTrash(false);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:delete notes
	 */

	@Override
	public Response deleteNote(Long noteId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
		if (isUserAndNoteIdPresent.isPresent()) {
			notesRepository.delete(isUserAndNoteIdPresent.get());
			return new Response(200, "Success", isUserAndNoteIdPresent.get());
		} 
		throw new NotesNotFoundException(400, "Notes not found");
	}

	/**
	 * Purpose:change note color
	 */

	@Override
	public NotesModel changeNoteColour(Long noteId, String colour, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if (isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setColor(colour);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:pin note
	 */

	@Override
	public NotesModel pinNote(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if (isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setPin(true);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:unpin note
	 */

	@Override
	public NotesModel unpinNote(Long noteId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if (isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setPin(false);
				notesRepository.save(isUserAndNoteIdPresent.get());
				return isUserAndNoteIdPresent.get();
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
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);		
			List<NotesModel> readAllNotes = notesRepository.findByUserId(userId);
			if(readAllNotes.size() > 0) {
				List<NotesModel> isNotePresent = notesRepository.findAllByPin();
				if(isNotePresent.size() > 0) {
					return isNotePresent;
				}
				throw new NotesNotFoundException(400, "Notes not present");	
			}
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:fetch all archeived notes
	 */

	@Override
	public List<NotesModel> getAllArchievedNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);		
			List<NotesModel> readAllNotes = notesRepository.findByUserId(userId);
			if(readAllNotes.size() > 0) {
				List<NotesModel> isNotePresent = notesRepository.findAllByArchieved();
				if(isNotePresent.size() > 0) {
					return isNotePresent;
				}
				throw new NotesNotFoundException(400, "Notes not present");	
			}
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:fetch all notes from trash
	 */

	@Override
	public List<NotesModel> getAllTrashNotes(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Long userId = tokenUtil.decodeToken(token);		
			List<NotesModel> readAllNotes = notesRepository.findByUserId(userId);
			if(readAllNotes.size() > 0) {
				List<NotesModel> isNotePresent = notesRepository.findAllByTrash();
				if(isNotePresent.size() > 0) {
					return isNotePresent;
				}
				throw new NotesNotFoundException(400, "Notes not present");	
			}
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:note label list
	 */

	@Override
	public NotesModel noteLabelList(Long noteId, Long labelId,  String token) {
		Long userId = tokenUtil.decodeToken(token);		
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> label = new ArrayList<>();
			List<NotesModel> notes = new ArrayList<>();
			Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				label.add(isLabelPresent.get());
			}
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			if(isUserAndNoteIdPresent.isPresent()) {
				isUserAndNoteIdPresent.get().setLabellist(label);
				notes.add(isUserAndNoteIdPresent.get());
				isLabelPresent.get().setNotes(notes);
				notesRepository.save(isUserAndNoteIdPresent.get());
				labelRepository.save(isLabelPresent.get());
				return isUserAndNoteIdPresent.get();
			}
			throw new NotesNotFoundException(400, "Notes not present");	
		}
		throw new NotesNotFoundException(400, "Label id not found");
	}

	/**
	 * Purpose:To collaborate 
	 */

	@Override
	public NotesModel addCollaborator(Long noteId, String collaborator, String token) {
		Long userId = tokenUtil.decodeToken(token);
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<NotesModel> isUserAndNoteIdPresent = notesRepository.findByUserIdAndNoteId(userId, noteId);
			List<String> collaboratorList = new ArrayList<>();
			if (isUserAndNoteIdPresent.isPresent()) {
				boolean isEmailPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateEmail/" + collaborator, Boolean.class);
				if (isEmailPresent) {
					collaboratorList.add(collaborator);
					isUserAndNoteIdPresent.get().setCollaborator(collaboratorList);
					notesRepository.save(isUserAndNoteIdPresent.get());
					List<String> noteList = new ArrayList<>();
					noteList.add(isUserAndNoteIdPresent.get().getEmailId());

					NotesModel note = new NotesModel();
					note.setTitle(isUserAndNoteIdPresent.get().getTitle());
					note.setArchieve(isUserAndNoteIdPresent.get().isArchieve());
					note.setCollaborator(isUserAndNoteIdPresent.get().getCollaborator());
					note.setColor(isUserAndNoteIdPresent.get().getColor());
					note.setDescription(isUserAndNoteIdPresent.get().getDescription());
					note.setEmailId(isUserAndNoteIdPresent.get().getEmailId());
					note.setLabelId(isUserAndNoteIdPresent.get().getLabelId());
					note.setNoteId(isUserAndNoteIdPresent.get().getNoteId());
					note.setPin(isUserAndNoteIdPresent.get().isPin());
					note.setRegisterDate(isUserAndNoteIdPresent.get().getRegisterDate());
					note.setReminderTime(isUserAndNoteIdPresent.get().getReminderTime());
					note.setTrash(isUserAndNoteIdPresent.get().isTrash());
					note.setUpdateDate(isUserAndNoteIdPresent.get().getUpdateDate());
					note.setUserId(isUserAndNoteIdPresent.get().getUserId());
					notesRepository.save(note);
					return isUserAndNoteIdPresent.get();
				}
			}
			throw new NotesNotFoundException(400, "Collaborator not present");	
		}
		throw new NotesNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:remaind user
	 */

	@Override
	public NotesModel remainder(String token, Long noteId, String remaindTime) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
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