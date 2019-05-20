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
import com.src.printing.office.po.model.material.generateConsumptionPDF.MaterialGenerateConsumptionPDFRequest;
import com.src.printing.office.po.model.material.save.request.MaterialSaveRequest;
import com.src.printing.office.po.model.material.search.request.SearchMaterialRequest;
import com.src.printing.office.po.model.material.update.MaterialUpdateRequest;
import com.src.printing.office.po.service.MaterijalService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/material/")
public class MaterijalController {

	private Logger logger = LoggerFactory.getLogger(MaterijalController.class);

	@Autowired
	private MaterijalService materijalService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@Nullable @RequestParam("type") String type,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getAll. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getAll(type),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal getAll unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllPapers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllPapers(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getAllPapers. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getAllPapers(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal getAllPapers unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllCritical", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCritical(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getAllCritical. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getAllCritical(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal getAllCritical unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllPapersForDN", method = RequestMethod.GET)
	public ResponseEntity<?> getAllPapersForDN(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getAllPapersForDN. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getAllPapersForDN(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal getAllPapersForDN unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllActiveForDN", method = RequestMethod.GET)
	public ResponseEntity<?> getAllActiveForDN(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getAllActiveForDN. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getAllActiveForDN(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal getAllActiveForDN unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllSpirals", method = RequestMethod.GET)
	public ResponseEntity<?> getAllSpirals(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getAllSpirals. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getAllSpirals(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal getAllSpirals unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid MaterialSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal save. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.save(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-310":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Materijal save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody SearchMaterialRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal search. " + principal.getUsername());

			return new ResponseEntity<>(materijalService.search(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal search unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOne", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Materijal getOne for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(materijalService.getOne(id), tokenHeaderCreator.getHeader(principal.getToken()),
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
			logger.error("Materijal getOne unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid MaterialUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Material update. " + principal.getUsername());

			materijalService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-310":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Material update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "generatePDF", method = RequestMethod.POST)
	public ResponseEntity<?> generatePDF(@RequestBody SearchMaterialRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		File file = null;
		InputStream resource = null;
		try {

			logger.info("Materijal generatePDF. " + principal.getUsername());

			file = materijalService.generatePDF(request);
			resource = new FileInputStream(file);

			HttpHeaders headers = tokenHeaderCreator.getHeaderForFile(principal.getToken(), file);

			return new ResponseEntity<>(IOUtils.toByteArray(resource), headers, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal generatePDF unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		} finally {
			if (resource != null)
				try {
					resource.close();
				} catch (IOException e) {
					logger.error("Materijal generatePDF -> resource.close() unexpected error", e);
				}
			if (file != null)
				file.delete();
		}
	}

	@RequestMapping(value = "generateConsumptionPDF", method = RequestMethod.POST)
	public ResponseEntity<?> generateConsumptionPDF(@RequestBody MaterialGenerateConsumptionPDFRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		File file = null;
		InputStream resource = null;
		try {

			logger.info("Materijal generateConsumptionPDF. " + principal.getUsername());

			file = materijalService.generateConsumptionPDF(request);
			resource = new FileInputStream(file);

			HttpHeaders headers = tokenHeaderCreator.getHeaderForFile(principal.getToken(), file);

			return new ResponseEntity<>(IOUtils.toByteArray(resource), headers, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Materijal generateConsumptionPDF unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		} finally {
			if (resource != null)
				try {
					resource.close();
				} catch (IOException e) {
					logger.error("Materijal generateConsumptionPDF -> resource.close() unexpected error", e);
				}
			if (file != null)
				file.delete();
		}
	}
}
