package com.src.printing.office.po.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.src.printing.office.configuration.security.TokenUser;
import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.customer.save.request.CustomerSaveRequest;
import com.src.printing.office.po.model.customer.search.request.SearchCustomerRequest;
import com.src.printing.office.po.model.customer.update.request.CustomerUpdateRequest;
import com.src.printing.office.po.service.KupacService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/customer/")
public class KupacController {

	private Logger logger = LoggerFactory.getLogger(KupacController.class);

	@Autowired
	private KupacService kupacService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Kupac getAll. " + principal.getUsername());

			return new ResponseEntity<>(kupacService.getAll(), tokenHeaderCreator.getHeader(principal.getToken()),
					HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Kupac getAll unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllActive", method = RequestMethod.GET)
	public ResponseEntity<?> getAllActive(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Kupac getAllActive. " + principal.getUsername());

			return new ResponseEntity<>(kupacService.getAllActive(), tokenHeaderCreator.getHeader(principal.getToken()),
					HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Kupac getAllActive unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid CustomerSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Kupac save. " + principal.getUsername());

			return new ResponseEntity<>(kupacService.save(request), tokenHeaderCreator.getHeader(principal.getToken()),
					HttpStatus.CREATED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-310":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Kupac save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchCustomerRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Kupac search. " + principal.getUsername());

			return new ResponseEntity<>(kupacService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Kupac search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid CustomerUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Kupac update. " + principal.getUsername());

			kupacService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Kupac update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}
}
