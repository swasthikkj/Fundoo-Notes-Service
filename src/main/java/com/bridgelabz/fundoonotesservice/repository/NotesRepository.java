package com.bridgelabz.fundoonotesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotesservice.model.NotesModel;

@Repository
public interface NotesRepository extends JpaRepository<NotesModel, Long>{

}
