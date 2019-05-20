package com.src.printing.office.po.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.src.printing.office.configuration.security.TokenUser;
import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.po.service.StatusNalogaService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/statusWO/")
public class StatusNalogaController {

	private Logger logger = LoggerFactory.getLogger(StatusNalogaController.class);

	@Autowired
	private StatusNalogaService statusNalogaService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Status naloga getAll. " + principal.getUsername());

			return new ResponseEntity<>(statusNalogaService.getAll(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Status naloga getAll unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}
}
