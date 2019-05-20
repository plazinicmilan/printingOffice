package com.src.printing.office.configuration.security.basicAuth;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.src.printing.office.configuration.security.TokenService;
import com.src.printing.office.configuration.security.TokenUser;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Rola;
import com.src.printing.office.po.service.KorisnikService;

@Service
public class UsernamePasswordDetailsService implements UserDetailsService {

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private TokenService tokenService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return getUserDetails(korisnikService.findByUsername(username));
		} catch (ApiException ex) {
			throw new UsernameNotFoundException("Account for '" + username + "' not found", ex);
		}
	}

	private TokenUser getUserDetails(Korisnik user) throws ApiException {
		return new TokenUser(user.getKorisnickoIme(), user.getKorisnickaSifra(), getAuthorities(user.getRolaList()),
				tokenService.encode(user));
	}

	private List<SimpleGrantedAuthority> getAuthorities(Set<Rola> set) {
		return set.stream().map(role -> new SimpleGrantedAuthority(role.getNaziv())).collect(Collectors.toList());
	}
}