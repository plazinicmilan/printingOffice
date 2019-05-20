package com.src.printing.office.configuration.security.token;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.src.printing.office.configuration.security.TokenService;
import com.src.printing.office.configuration.security.TokenUser;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.service.KorisnikService;

@Service
public class TokenAuthenticationUserDetailsService
		implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private KorisnikService userService;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authentication) {
		try {
			// najpre se dekoduje prosledjeni token, zatim pronalazi user u bazi, i ponovo
			// enkoduje, kako bi se sesija produzila
			Korisnik korisnik = userService.findByUsername(
					tokenService.decode((String) authentication.getPrincipal()).getClaim("usr").asString());
			return new TokenUser(korisnik.getKorisnickoIme(), korisnik.getKorisnickaSifra(), new ArrayList<>(),
					tokenService.encode(korisnik));
		} catch (ApiException e) {
			throw new UsernameNotFoundException(
					"Could not retrieve user details for '" + authentication.getPrincipal() + "'");
		}
	}

}
