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
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveRequest;
import com.src.printing.office.po.model.receipt.search.SearchReceiptRequest;
import com.src.printing.office.po.model.receipt.update.ReceiptUpdateRequest;
import com.src.printing.office.po.service.RacunService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/receipt/")
public class RacunController {

	private Logger logger = LoggerFactory.getLogger(RacunController.class);

	@Autowired
	private RacunService racunService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid ReceiptSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Racun save. " + principal.getUsername());

			return new ResponseEntity<>(racunService.save(request), tokenHeaderCreator.getHeader(principal.getToken()),
					HttpStatus.CREATED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-330":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Racun save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid ReceiptUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Racun update. " + principal.getUsername());

			racunService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error("Racun update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchReceiptRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Racun search. " + principal.getUsername());

			return new ResponseEntity<>(racunService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Racun search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public synchronized ResponseEntity<?> cancel(@RequestParam("id") long dnID,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Racun cancel. " + principal.getUsername());

			racunService.cancel(dnID);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Racun cancel unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOne", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Racun getOne for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(racunService.getOne(id), tokenHeaderCreator.getHeader(principal.getToken()),
					HttpStatus.OK);

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
			logger.error("Racun getOne unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "generatePDF", method = RequestMethod.GET)
	public ResponseEntity<?> generatePDF(@RequestParam("id") long receiptID,
			@AuthenticationPrincipal TokenUser principal) {
		File file = null;
		InputStream resource = null;
		try {

			logger.info("Racun generatePDF. " + principal.getUsername());

			file = racunService.generatePDF(receiptID);
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
			logger.error("Racun generatePDF unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		} finally {
			if (resource != null)
				try {
					resource.close();
				} catch (IOException e) {
					logger.error("Racun generatePDF -> resource.close() unexpected error", e);
				}
			if (file != null)
				file.delete();
		}
	}

}
