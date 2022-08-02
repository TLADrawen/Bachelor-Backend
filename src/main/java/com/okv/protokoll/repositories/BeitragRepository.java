package com.okv.protokoll.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.okv.protokoll.model.Beitrag;
import com.okv.protokoll.model.BeitragId;

@Repository
public interface BeitragRepository extends JpaRepository<Beitrag, BeitragId>{

}
