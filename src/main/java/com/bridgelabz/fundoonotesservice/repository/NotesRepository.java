package com.bridgelabz.fundoonotesservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesservice.model.NotesModel;

@Repository
public interface NotesRepository extends JpaRepository<NotesModel, Long> {
	
	@Query(value = "select * from notes where pin = true", nativeQuery = true) 
	List<NotesModel> findAllByPin();
	
	@Query(value = "select * from notes where is_archeive = true", nativeQuery = true)
	List<NotesModel> findAllByArchieved();
	
	@Query(value = "select * from notes where trash = true", nativeQuery = true)
	List<NotesModel> findAllByTrash();

}
