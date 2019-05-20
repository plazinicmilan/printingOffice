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
import com.src.printing.office.po.model.deliveryNote.finish.DNFinishRequest;
import com.src.printing.office.po.model.deliveryNote.save.request.DNSaveRequest;
import com.src.printing.office.po.model.deliveryNote.search.SearchDNRequest;
import com.src.printing.office.po.model.deliveryNote.update.request.DNUpdateRequest;
import com.src.printing.office.po.service.OtpremnicaService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/dn/")
public class OtpremnicaController {

	private Logger logger = LoggerFactory.getLogger(OtpremnicaController.class);

	@Autowired
	private OtpremnicaService otpremnicaService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid DNSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica save. " + principal.getUsername());

			return new ResponseEntity<>(otpremnicaService.save(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-320":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-321":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Otpremnica save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid DNUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica update. " + principal.getUsername());

			otpremnicaService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-320":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-321":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Otpremnica update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchDNRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica search. " + principal.getUsername());

			return new ResponseEntity<>(otpremnicaService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Otpremnica search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public synchronized ResponseEntity<?> cancel(@RequestParam("id") long dnID,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica cancel. " + principal.getUsername());

			otpremnicaService.cancel(dnID);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Otpremnica cancel unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOne", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica getOne for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(otpremnicaService.getOne(id),
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
			logger.error("Otpremnica getOne unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOneForReceipt", method = RequestMethod.GET)
	public ResponseEntity<?> getOneForReceipt(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica getOneForReceipt for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(otpremnicaService.getOneForReceipt(id),
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
			logger.error("Otpremnica getOneForReceipt unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllUnreceipted", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUnreceipted(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica getAllUnreceipted: " + principal.getUsername());

			return new ResponseEntity<>(otpremnicaService.getAllUnreceipted(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Otpremnica getAllUnreceipted unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "generatePDF", method = RequestMethod.GET)
	public ResponseEntity<?> generatePDF(@RequestParam("id") long dnID, @AuthenticationPrincipal TokenUser principal) {
		File file = null;
		InputStream resource = null;
		try {

			logger.info("Otpremnica generatePDF. " + principal.getUsername());

			file = otpremnicaService.generatePDF(dnID);
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
			logger.error("Otpremnica generatePDF unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		} finally {
			if (resource != null)
				try {
					resource.close();
				} catch (IOException e) {
					logger.error("Otpremnica generatePDF -> resource.close() unexpected error", e);
				}
			if (file != null)
				file.delete();
		}
	}

	@RequestMapping(value = "finish", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> finish(@RequestBody DNFinishRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Otpremnica finish. " + principal.getUsername());

			otpremnicaService.finish(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Otpremnica finish unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

}
