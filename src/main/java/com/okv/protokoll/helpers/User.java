package com.okv.protokoll.helpers;

public class User {
	
	private String benutzername;
	private String passwort;
	
	public User() {}
	
	public User(String benutzername, String passwort) {
		this.benutzername=benutzername;
		this.passwort=passwort;
	}
	
	public String getBenutzername() {
		return benutzername;
	}
	
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	

}
