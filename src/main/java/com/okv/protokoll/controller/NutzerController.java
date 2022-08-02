package com.okv.protokoll.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okv.protokoll.helpers.User;
import com.okv.protokoll.services.Login;

@RestController
@RequestMapping("/nutzer")
public class NutzerController {
	
	@Resource(name = "login")
	private Login login;

	@PostMapping(value = "/login", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> loginWithName(@RequestBody User nutzer) {
		//Weiterer Fall falls ldap Authentication fehlschlägt -> anderer HTTP Code
		String response = login.login(nutzer.getBenutzername(), nutzer.getPasswort());
		if (response==null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} else {
			return ResponseEntity.ok(response);
		}
	}

}
