package com.okv.protokoll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okv.protokoll.model.Protokoll;

@Repository
public interface ProtokollRepository extends JpaRepository<Protokoll, Integer>{

}
