package com.proxytester.proxytester.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxytester.proxytester.model.TesterModel;

public interface TesterRepository extends JpaRepository<TesterModel, String>{
    
}
