package com.src.printing.office.po.model.user.update;

import java.io.Serializable;
import java.util.List;

import com.src.printing.office.po.model.POGenericType;

public class UserUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private List<POGenericType> authList;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<POGenericType> getAuthList() {
		return authList;
	}

	public void setAuthList(List<POGenericType> authList) {
		this.authList = authList;
	}

}
