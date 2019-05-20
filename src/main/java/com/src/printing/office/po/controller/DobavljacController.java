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
import com.src.printing.office.po.model.supplier.save.request.SupplierSaveRequest;
import com.src.printing.office.po.model.supplier.search.request.SearchSupplierRequest;
import com.src.printing.office.po.model.supplier.update.request.SupplierUpdateRequest;
import com.src.printing.office.po.service.DobavljacService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/supplier/")
public class DobavljacController {

	private Logger logger = LoggerFactory.getLogger(DobavljacController.class);

	@Autowired
	private DobavljacService dobavljacService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Dobavljac getAll. " + principal.getUsername());

			return new ResponseEntity<>(dobavljacService.getAll(), tokenHeaderCreator.getHeader(principal.getToken()),
					HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Dobavljac getAll unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid SupplierSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Dobavljac save. " + principal.getUsername());

			return new ResponseEntity<>(dobavljacService.save(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-310":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Dobavljac save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchSupplierRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Dobavljac search. " + principal.getUsername());

			return new ResponseEntity<>(dobavljacService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Dobavljac search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid SupplierUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Dobavljac update. " + principal.getUsername());

			dobavljacService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Dobavljac update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}
}
