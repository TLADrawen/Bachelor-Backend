package com.okv.protokoll.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Nutzer {
	
	@Id
	private String benutzername;
	private String email;
	private String nachname;
	private String vorname;
	private boolean aktiv;
	
	public Nutzer() {}
	
	public Nutzer(String benutzername, String email, String nachname, String vorname, boolean aktiv) {
		this.benutzername = benutzername;
		this.email = email;
		this.nachname = nachname;
		this.vorname = vorname;
		this.aktiv = aktiv;
	}
	
	public String getBenutzername() {
		return benutzername;
	}
	
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getVorname() {
		return vorname;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public boolean isAktiv() {
		return aktiv;
	}
	
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

}
