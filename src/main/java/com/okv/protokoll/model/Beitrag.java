package com.okv.protokoll.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="anwesend")
@IdClass(BeitragId.class)
public class Beitrag {
	
	@Id
	private int id;
	@Id
	private String benutzername;
	private String aussagen;
	private int nummer;
	
	public Beitrag() {}
	
	public Beitrag(int id, String benutzername, String aussagen, int nummer) {
		this.id = id;
		this.benutzername = benutzername;
		this.aussagen = aussagen;
		this.nummer = nummer;
	}

	public BeitragId getId() {
		return new BeitragId(id,benutzername);
	}

	public void setId(BeitragId key) {
		this.id = key.getId();
		this.benutzername = key.getBenutzername();
	}

	public String getAussagen() {
		return aussagen;
	}

	public void setAussagen(String aussagen) {
		this.aussagen = aussagen;
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	
}
