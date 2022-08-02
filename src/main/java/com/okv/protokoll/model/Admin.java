package com.okv.protokoll.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
	
	@Id
	private String benutzername;
	
	public Admin() {}
	
	public Admin(String benutzername) {
		this.benutzername=benutzername;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

}
