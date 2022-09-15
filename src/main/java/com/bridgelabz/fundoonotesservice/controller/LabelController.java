package com.bridgelabz.fundoonotesservice.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotesservice.dto.LabelDTO;
import com.bridgelabz.fundoonotesservice.model.LabelModel;
import com.bridgelabz.fundoonotesservice.service.ILabelService;
import com.bridgelabz.fundoonotesservice.util.Response;

/**
 * Purpose:create label controller
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@RestController
@RequestMapping("/labelservice")
public class LabelController {
	@Autowired
	ILabelService labelService;
	
	/**
	 * Purpose:create label
	 */
	
	@PostMapping("/createlabel")
	public ResponseEntity<Response> createLabel(@Valid @RequestBody LabelDTO labelDTO, @RequestHeader String token) {
		LabelModel labelModel = labelService.createLabel(labelDTO,token);
		Response response = new Response(200, "Label created successfully", labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:update label
	 */
	
	@PutMapping("/updatelabel/{id}")
	public ResponseEntity<Response> updateLabel(@Valid @RequestBody LabelDTO labelDTO, @PathVariable Long id, @RequestHeader String token) {
		LabelModel labelModel = labelService.updateLabel(labelDTO,id,token);
		Response response = new Response(200, "Label updated successfully", labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get all label
	 */
	
	@GetMapping("/getAllLabels")
	public ResponseEntity<Response> getAllLabels(@RequestHeader String token) {
		List<LabelModel> labelModel = labelService.getAllLabels(token);
		Response response = new Response(200, "Fetching all labels successfully", labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:delete label
	 */
	
	@DeleteMapping("/deleteLabels")
	public ResponseEntity<Response> deleteLabel(@PathVariable Long id, @RequestHeader String token) {
		LabelModel labelModel = labelService.deleteLabel(id, token);
		Response response = new Response(200, "label deleted successfully", labelModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

