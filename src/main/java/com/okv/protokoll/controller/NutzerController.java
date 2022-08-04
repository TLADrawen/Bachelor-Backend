package com.okv.protokoll.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okv.protokoll.JWTUtils;
import com.okv.protokoll.helpers.User;
import com.okv.protokoll.model.Admin;
import com.okv.protokoll.model.Nutzer;
import com.okv.protokoll.repositories.AdminRepository;
import com.okv.protokoll.repositories.NutzerRepository;
import com.okv.protokoll.services.Login;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/nutzer")
public class NutzerController {
	
	@Autowired
	private NutzerRepository nutzerRepository;
	@Autowired
	private AdminRepository adminRepository;
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

	@GetMapping("/{benutzername}")
	public ResponseEntity<Nutzer> getOne(@RequestHeader("Authorization") String token, @PathVariable String benutzername){
		try {
			JWTUtils.verifyToken(token);
			Optional<Nutzer> nutzer = nutzerRepository.findById(benutzername);
			if (nutzer.isPresent())
				return ResponseEntity.ok(nutzer.get());
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Nutzer>> getAll(@RequestHeader("Authorization") String token) {
		try {
			JWTUtils.verifyToken(token);
			return ResponseEntity.ok(nutzerRepository.findAll(Sort.by("id")));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Nutzer>> getAktive(@RequestHeader("Authorization") String token) {
		try {
			JWTUtils.verifyToken(token);
			return ResponseEntity.ok(nutzerRepository.findAktiveNutzer());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping("/{admin}")
	public ResponseEntity<Void> create(@RequestHeader("Authorization") String token, @RequestBody Nutzer nutzer,
			@PathVariable boolean admin) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (claims.get("scope", String.class).equals("admin")) {
				if (nutzer.getBenutzername() != null && nutzer.getEmail() != null && nutzer.getNachname() != null
						&& nutzer.getVorname() != null) {
					nutzer.setAktiv(true);
					nutzerRepository.save(nutzer);
					if (admin) {
						adminRepository.save(new Admin(nutzer.getBenutzername()));
					}
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
				} else
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			} else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<Void> edit(@RequestHeader("Authorization") String token, @RequestBody Nutzer nutzer) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (!claims.get("scope", String.class).equals("admin"))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			else {
				if (!nutzerRepository.findById(nutzer.getBenutzername()).isPresent())
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				else {
					if (nutzer.getBenutzername() != null && nutzer.getEmail() != null && nutzer.getNachname() != null
							&& nutzer.getVorname() != null) {
						nutzerRepository.updateNutzer(nutzer.getBenutzername(), nutzer.getEmail(), nutzer.getNachname(),
								nutzer.getVorname(), nutzer.isAktiv());
						return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
					} else
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@DeleteMapping("/{benutzername}")
	public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token, @PathVariable String benutzername) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (!claims.get("scope", String.class).equals("admin"))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			else {
				if (!nutzerRepository.findById(benutzername).isPresent())
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				else {
					if (adminRepository.findById(benutzername).isPresent())
						adminRepository.deleteById(benutzername);
					nutzerRepository.deleteById(benutzername);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}
