package com.src.printing.office.po.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.src.printing.office.po.model.order.accept.OrderAcceptRequest;
import com.src.printing.office.po.model.order.save.OrderSaveRequest;
import com.src.printing.office.po.model.order.search.OrderSearchRequest;
import com.src.printing.office.po.model.order.update.OrderUpdateRequest;
import com.src.printing.office.po.service.PorudzbinaService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/order/")
public class PorudzbinaController {

	private Logger logger = LoggerFactory.getLogger(PorudzbinaController.class);

	@Autowired
	private PorudzbinaService porudzbinaService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody OrderSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Porudzbina save. " + principal.getUsername());

			porudzbinaService.save(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Porudzbina save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody OrderSearchRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Porudzbina search. " + principal.getUsername());

			return new ResponseEntity<>(porudzbinaService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Porudzbina search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	public synchronized ResponseEntity<?> delete(@RequestParam("id") long orderID,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Porudzbina delete. " + principal.getUsername());

			porudzbinaService.delete(orderID);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Porudzbina unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "accept", method = RequestMethod.POST)
	public ResponseEntity<?> accept(@RequestBody OrderAcceptRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Porudzbina accept. " + principal.getUsername());

			porudzbinaService.accept(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error("Porudzbina accept unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestBody OrderUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Porudzbina update. " + principal.getUsername());

			porudzbinaService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Porudzbina update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllUnaccepted", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUnaccepted(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Porudzbina getAllUnaccepted: " + principal.getUsername());

			return new ResponseEntity<>(porudzbinaService.getAllUnaccepted(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Porudzbina getAllUnaccepted unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

}
