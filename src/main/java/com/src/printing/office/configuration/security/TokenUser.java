/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.src.printing.office.configuration.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author student
 */
public class TokenUser extends User {

	private static final long serialVersionUID = 1L;

	private String token;

	public TokenUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String token) {
		super(username, password, authorities);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
