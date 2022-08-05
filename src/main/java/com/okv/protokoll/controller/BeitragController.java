package com.okv.protokoll.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.okv.protokoll.model.Beitrag;
import com.okv.protokoll.repositories.BeitragRepository;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/beitrag")
public class BeitragController {
	
	@Autowired
	private BeitragRepository beitragRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<List<Beitrag>> getForProtokoll(@RequestHeader("Authorization") String token, @PathVariable int id){
		try {
			JWTUtils.verifyToken(token);
			return ResponseEntity.ok(beitragRepository.findAllForId(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Void> create(@RequestHeader("Authorization") String token, @RequestBody List<Beitrag> beitraege){
		try {
			JWTUtils.verifyToken(token);
			if (beitraege.size() > 0) {
				Collections.sort(beitraege);
				beitragRepository.saveAll(beitraege);
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PutMapping("/")
	public ResponseEntity<Void> editForProtokoll(@RequestHeader("Authorization") String token,
			@RequestBody List<Beitrag> beitraege) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (claims.get("scope", String.class).equals("admin")) {
				for (Beitrag beitrag : beitraege)
					beitragRepository.updateBeitrag(beitrag.getId().getId(), beitrag.getId().getBenutzername(),
							beitrag.getAussagen(), beitrag.getNummer());
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			} else {
				for (Beitrag beitrag : beitraege) {
					if (claims.get("name", String.class).equals(beitrag.getId().getBenutzername()))
						beitragRepository.updateBeitrag(beitrag.getId().getId(), beitrag.getId().getBenutzername(),
								beitrag.getAussagen(), beitrag.getNummer());
				}
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@DeleteMapping("/")
	public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token, @RequestBody List<Beitrag> beitraege) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (claims.get("scope", String.class).equals("admin")) {
				for (Beitrag beitrag : beitraege)
					beitragRepository.delete(beitrag);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
			else {
				for (Beitrag beitrag : beitraege) {
					if (claims.get("name", String.class).equals(beitrag.getId().getBenutzername()))
						beitragRepository.delete(beitrag);
				}
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}
