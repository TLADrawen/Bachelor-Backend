package com.okv.protokoll.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.okv.protokoll.model.Nutzer;

@Repository
public interface NutzerRepository extends JpaRepository<Nutzer, String>{
	
	@Modifying
	@Query("select * from Nutzer n where n.aktiv = 1 order by n.benutzername")
	public List<Nutzer> findAktiveNutzer();

	@Modifying
	@Query("update Nutzer n set n.email = ?2, n.nachname = ?3, n.vorname = ?4, n.aktiv = ?5  where n.benutzername= ?1")
	public void updateNutzer(String benutzername, String email, String nachname, String vorname, boolean aktiv);
	
}