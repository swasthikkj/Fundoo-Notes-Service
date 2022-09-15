package com.bridgelabz.fundoonotesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotesservice.model.LabelModel;

public interface LabelRepository extends JpaRepository<LabelModel, Long> {

}
