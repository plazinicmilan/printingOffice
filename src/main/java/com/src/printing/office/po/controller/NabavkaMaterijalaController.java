package com.src.printing.office.po.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.src.printing.office.configuration.security.TokenUser;
import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.pom.save.request.POMSaveRequest;
import com.src.printing.office.po.model.pom.search.request.SearchPOMRequest;
import com.src.printing.office.po.service.NabavkaMaterijalaService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/pom/")
public class NabavkaMaterijalaController {

	private Logger logger = LoggerFactory.getLogger(NabavkaMaterijalaController.class);

	@Autowired
	private NabavkaMaterijalaService nabavkaMaterijalaService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid POMSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Nabavka materijala save. " + principal.getUsername());

			nabavkaMaterijalaService.save(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Nabavka materijala save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchPOMRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Nabavka materijala search. " + principal.getUsername());

			return new ResponseEntity<>(nabavkaMaterijalaService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Nabavka materijala search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	public synchronized ResponseEntity<?> delete(@RequestParam("id") long pomID,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Nabavka materijala delete. " + principal.getUsername());

			nabavkaMaterijalaService.delete(pomID);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Nabavka materijala unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOne", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Nabavka materijala getOne for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(nabavkaMaterijalaService.getOne(id),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-300":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Nabavka materijala getOne unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}
}
