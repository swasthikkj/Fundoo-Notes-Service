package com.bridgelabz.fundoonotesservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotesservice.dto.LabelDTO;
import com.bridgelabz.fundoonotesservice.exception.NotesNotFoundException;
import com.bridgelabz.fundoonotesservice.model.LabelModel;
import com.bridgelabz.fundoonotesservice.repository.LabelRepository;
import com.bridgelabz.fundoonotesservice.util.TokenUtil;

/**
 * Purpose:Label service class for crud operations
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Service
public class LabelService implements ILabelService {
	@Autowired
	LabelRepository labelRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	MailService mailService;

	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * Purpose:create label
	 */
	
	@Override
	public LabelModel createLabel(LabelDTO labelDTO, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			LabelModel model = new LabelModel(labelDTO);
			model.setRegisterDate(LocalDateTime.now());
			labelRepository.save(model);
			return model;
		}
		throw new NotesNotFoundException(400, "Token Invalid");
	}
	
	/**
	 * Purpose:update label
	 */
	
	@Override
	public LabelModel updateLabel(LabelDTO labelDTO, Long labelId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<LabelModel>isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				isLabelPresent.get().setLabelName(labelDTO.getLabelName());
				isLabelPresent.get().setUpdateDate(LocalDateTime.now());
				labelRepository.save(isLabelPresent.get());
				return isLabelPresent.get();
			}
			throw new NotesNotFoundException(400, "Label not present");
		}
		throw new NotesNotFoundException(400, "Token Invalid");
	}
	
	/**
	 * Purpose:get all labels
	 */
	
	@Override
	public List<LabelModel> getAllLabels(String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			List<LabelModel> getAllLabels = labelRepository.findAll();
			if(getAllLabels.size()>0) {
				return getAllLabels;	
			}
			throw new NotesNotFoundException(400, "Label not present");
		}
		throw new NotesNotFoundException(400, "Token Invalid");
	}
	
	/**
	 * Purpose:delete label
	 */
	
	@Override
	public LabelModel deleteLabel(Long labelId, String token) {
		boolean isUserPresent = restTemplate.getForObject("http://FundooUserService:8088/fundooUserService/validateUser/" + token, Boolean.class);
		if (isUserPresent) {
			Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
			if(isLabelPresent.isPresent()) {
				labelRepository.delete(isLabelPresent.get());
				return isLabelPresent.get();
			}
			throw new NotesNotFoundException(400, "Label not found");
		}
		throw new NotesNotFoundException(400, "Token not found");
	}
}
