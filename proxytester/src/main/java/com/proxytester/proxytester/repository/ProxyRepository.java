package com.proxytester.proxytester.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxytester.proxytester.model.ProxyModel;

public interface ProxyRepository extends JpaRepository<ProxyModel, String>{
    
}
