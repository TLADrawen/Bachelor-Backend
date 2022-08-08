package com.okv.protokoll.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okv.protokoll.JWTUtils;
import com.okv.protokoll.model.Nutzer;
import com.okv.protokoll.repositories.AdminRepository;
import com.okv.protokoll.repositories.NutzerRepository;

@Service("login")
public class Login {
	
	@Autowired
	private NutzerRepository nutzerRepository;
	@Autowired
	private AdminRepository adminRepository;

	/**
	 * 
	 * @param benutzername Vom einloggenden Nutzer
	 * @param passwort Vom einloggenden Nutzer
	 * @return JWT falls Login erfolgreich, ansonsten null
	 */
	public String login(String benutzername, String passwort) {
		// ldap Authentication
		Optional<Nutzer> dbNutzer = nutzerRepository.findById(benutzername);
		if (dbNutzer.isPresent()) {
			if (dbNutzer.get().isAktiv()) {
				if (adminRepository.findById(benutzername).isPresent())
					return JWTUtils.generateToken(benutzername, "admin");
				else
					return JWTUtils.generateToken(benutzername, "nutzer");
			} else
				return null;
		} else
			return null;
	}
}