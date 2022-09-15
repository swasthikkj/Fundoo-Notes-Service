package com.bridgelabz.fundoonotesservice.service;

import java.util.List;

import com.bridgelabz.fundoonotesservice.dto.LabelDTO;
import com.bridgelabz.fundoonotesservice.model.LabelModel;

/**
 * Purpose:Interface for label service
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

public interface ILabelService {
	
	LabelModel createLabel(LabelDTO labelDTO, String token);

	List<LabelModel> getAllLabels(String token);

	LabelModel updateLabel(LabelDTO labelDTO, Long id, String token);
	
	LabelModel deleteLabel(Long id, String token);
}
