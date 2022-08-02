package com.okv.protokoll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okv.protokoll.repositories.NutzerRepository;

@Service("login")
public class Login {
	
	@Autowired
	private NutzerRepository nutzerRepository;

	public String login(String benutzername, String passwort) {
		//ldap Authentication
		if(!nutzerRepository.findById(benutzername).isPresent()) {
			return null;
		}
		else {
			//JWT Token
			return "jwt";
		}
	}
}