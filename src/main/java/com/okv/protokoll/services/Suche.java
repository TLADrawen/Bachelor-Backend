package com.okv.protokoll.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okv.protokoll.model.Beitrag;
import com.okv.protokoll.model.Nutzer;
import com.okv.protokoll.model.Protokoll;
import com.okv.protokoll.repositories.BeitragRepository;
import com.okv.protokoll.repositories.NutzerRepository;
import com.okv.protokoll.repositories.ProtokollRepository;

@Service("suche")
public class Suche {
	
	@Autowired
	private NutzerRepository nutzerRepository;
	@Autowired
	private ProtokollRepository protokollRepository;
	@Autowired
	private BeitragRepository beitragRepository;
	
	public List<Protokoll> suche(Nutzer nutzer, Protokoll protokoll, Beitrag beitrag){
		//suche implementation
		return null;
	}

}
