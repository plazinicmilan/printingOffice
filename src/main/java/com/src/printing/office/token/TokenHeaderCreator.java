package com.src.printing.office.token;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class TokenHeaderCreator {

	@Value("${token.header.name}")
	private String tokenHeaderName;

	public HttpHeaders getHeader(String token) {
		HttpHeaders header = new HttpHeaders();

		header.add(tokenHeaderName, token);

		return header;
	}

	public HttpHeaders getHeaderForFile(String token, File file) {
		HttpHeaders header = new HttpHeaders();

		header.add(tokenHeaderName, token);
		header.add("filename", file.getName());
		header.add("Content-Length", String.valueOf(file.length()));
		header.add("Content-Type", "application/pdf");

		return header;
	}
}
