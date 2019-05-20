package com.src.printing.office.po.model.user.getOne.response;

import java.io.Serializable;
import java.util.List;

public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String lastName;
	private List<String> roles;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
