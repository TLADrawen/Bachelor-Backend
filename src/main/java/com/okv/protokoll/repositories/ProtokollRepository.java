package com.okv.protokoll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.okv.protokoll.model.Protokoll;

@Repository
public interface ProtokollRepository extends JpaRepository<Protokoll, Integer>{

	@Modifying
	@Query("update Protokoll p set p.bezeichnung = ?1 where p.id= ?2")
	public void updateProtokoll(String bezeichnung, int id);
}
