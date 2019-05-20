/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.src.printing.office.configuration.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Rola;

@Service
public class TokenService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Algorithm algorithm;
	private JWTVerifier verifier;

	@Value("${token.exparation.time}")
	private String tokenTime;

	@Autowired
	public TokenService(@Value("${jwt.token.secure}") String secret) {
		this.algorithm = Algorithm.HMAC256(secret);
		this.verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
	}

	public String encode(Korisnik korisnik) throws ApiException {
		try {
			return JWT.create().withClaim("usr", korisnik.getKorisnickoIme()).withClaim("id", korisnik.getId())
					.withClaim("name", korisnik.getIme() + " " + korisnik.getPrezime()).withIssuedAt(new Date())
					.withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(Integer.parseInt(tokenTime))
							.atZone(ZoneId.systemDefault()).toInstant()))
					.withArrayClaim("role", korisnik.getRolaList().stream().map(Rola::getNaziv).toArray(String[]::new))
					.sign(algorithm);
		} catch (Exception e) {
			logger.error("Cannot properly create token", e);
			throw new ApiException(new ApiError(ApiErrorMessage.DEFAULT_ERROR, "token"));
		}
	}

	public DecodedJWT decode(String token) {
		return this.verifier.verify(token);
	}

}
