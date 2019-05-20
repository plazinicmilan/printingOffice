package com.src.printing.office.po.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.src.printing.office.po.model.workOrder.finish.WOFinishRequest;
import com.src.printing.office.po.model.workOrder.save.request.WorkOrderRequest;
import com.src.printing.office.po.model.workOrder.search.request.SearchWORequest;
import com.src.printing.office.po.model.workOrder.update.WorkOrderUpdateRequest;
import com.src.printing.office.po.service.RadniNalogService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/workorder/")
public class RadniNalogController {

	private Logger logger = LoggerFactory.getLogger(RadniNalogController.class);

	@Autowired
	private RadniNalogService radniNalogService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid WorkOrderRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Radni nalog save. " + principal.getUsername());

			return new ResponseEntity<>(radniNalogService.save(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-310":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-321":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Radni nalog save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid WorkOrderUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Radni nalog update. " + principal.getUsername());

			radniNalogService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-321":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Radni nalog update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "generatePDF", method = RequestMethod.GET)
	public ResponseEntity<?> generateWorkOrderPDF(@RequestParam("id") long woID,
			@AuthenticationPrincipal TokenUser principal) {
		File file = null;
		InputStream resource = null;
		try {

			logger.info("Radni nalog generatePDF. " + principal.getUsername());

			file = radniNalogService.generateWorkOrderPDF(woID);
			resource = new FileInputStream(file);

			HttpHeaders headers = tokenHeaderCreator.getHeaderForFile(principal.getToken(), file);

			return new ResponseEntity<>(IOUtils.toByteArray(resource), headers, HttpStatus.OK);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Radni nalog generatePDF unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		} finally {
			if (resource != null)
				try {
					resource.close();
				} catch (IOException e) {
					logger.error("Radni nalog generateWorkOrderPDF -> resource.close() unexpected error", e);
				}
			if (file != null)
				file.delete();
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchWORequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Radni nalog search. " + principal.getUsername());

			return new ResponseEntity<>(radniNalogService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Radni nalog search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllActive", method = RequestMethod.GET)
	public ResponseEntity<?> getAllActive(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Radni nalog getAllActive. " + principal.getUsername());

			return new ResponseEntity<>(radniNalogService.getAllActive(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Radni nalog getAllActive unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	public synchronized ResponseEntity<?> delete(@RequestParam("id") long woID,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Radni nalog delete. " + principal.getUsername());

			radniNalogService.delete(woID);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Radni nalog unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOne", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("RadniNalog getOne for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(radniNalogService.getOne(id),
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
			logger.error("RadniNalog getOne unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getLastWONumber", method = RequestMethod.GET)
	public ResponseEntity<?> getLastWONumber(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("RadniNalog getLastWONumber: " + principal.getUsername());

			return new ResponseEntity<>(radniNalogService.getLastWONumber(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("RadniNalog getLastWONumber unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "finish", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> finish(@RequestBody WOFinishRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Radni nalog finish. " + principal.getUsername());
			radniNalogService.finish(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error("Radni nalog finish unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

}
