package com.okv.protokoll.model;

import java.io.Serializable;
import java.util.Objects;


@SuppressWarnings("serial")
public class BeitragId implements Serializable {
	
	private int id;
	private String benutzername;
	
	public BeitragId(){}
	
	public BeitragId(int id, String benutzername) {
		this.id=id;
		this.benutzername=benutzername;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		BeitragId other = (BeitragId) o;
		if (this.id==other.getId() && this.benutzername.equals(other.getBenutzername()))
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, benutzername);
	}
}
