package com.okv.protokoll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okv.protokoll.model.Nutzer;

@Repository
public interface NutzerRepository extends JpaRepository<Nutzer, String>{

}