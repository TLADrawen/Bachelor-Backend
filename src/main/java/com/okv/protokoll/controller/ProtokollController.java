package com.okv.protokoll.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
import com.okv.protokoll.model.Protokoll;
import com.okv.protokoll.repositories.ProtokollRepository;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/protokoll")
public class ProtokollController {
	
	@Autowired
	private ProtokollRepository protokollRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Protokoll> getOne(@RequestHeader("Authorization") String token, @PathVariable int id){
		try {
			JWTUtils.verifyToken(token);
			Optional<Protokoll> protokoll = protokollRepository.findById(id);
			if (protokoll.isPresent())
				return ResponseEntity.ok(protokoll.get());
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Protokoll>> getAll(@RequestHeader("Authorization") String token){
		try {
			JWTUtils.verifyToken(token);
			return ResponseEntity.ok(protokollRepository.findAll(Sort.by("id")));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Void> create(@RequestHeader("Authorization") String token, @RequestBody String bezeichnung){
		try {
			Claims claims = JWTUtils.verifyToken(token);
			long date = new java.util.Date().getTime();
			Protokoll protokoll = new Protokoll(bezeichnung, new Date(date),claims.get("name", String.class));
			protokollRepository.save(protokoll);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> edit(@RequestHeader("Authorization") String token, @RequestBody Protokoll protokoll) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (!claims.get("scope", String.class).equals("admin"))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			else {
				if (!protokollRepository.findById(protokoll.getId()).isPresent())
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				else {
					if (protokoll.getBezeichnung() == null || protokoll.getProtokollant() == null)
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
					else {
						protokollRepository.updateProtokoll(protokoll.getBezeichnung(), protokoll.getId());
						return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
					}
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token, @PathVariable int id) {
		try {
			Claims claims = JWTUtils.verifyToken(token);
			if (!claims.get("scope", String.class).equals("admin"))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			else {
				if (!protokollRepository.findById(id).isPresent())
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				else {
					protokollRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

}
