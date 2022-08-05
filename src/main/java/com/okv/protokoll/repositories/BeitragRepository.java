package com.okv.protokoll.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.okv.protokoll.model.Beitrag;
import com.okv.protokoll.model.BeitragId;

@Repository
public interface BeitragRepository extends JpaRepository<Beitrag, BeitragId>{
	
	@Modifying
	@Query("select * from anwesend a where a.id = ?1 order by a.benutzername")
	public List<Beitrag> findAllForId(int id);
	
	@Modifying
	@Query("update Anwesend a set a.aussagen = ?3, a.nummer = ?4  where a.id = ?1 and a.benutzername= ?2")
	public void updateBeitrag(int id, String benutzername, String text, int nummer);

}
