package com.okv.protokoll.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Protokoll {

	@Id
	@GeneratedValue
	private int id;
	private String bezeichnung;
	private Date datum;
	private String protokollant;
	
	public Protokoll() {};
	
	public Protokoll(int id, String bezeichnung, Date datum, String protokollant) {
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.datum = datum;
		this.protokollant = protokollant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getProtokollant() {
		return protokollant;
	}

	public void setProtokollant(String protokollant) {
		this.protokollant = protokollant;
	}
	
}
