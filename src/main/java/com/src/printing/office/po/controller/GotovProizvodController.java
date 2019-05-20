package com.src.printing.office.po.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import com.src.printing.office.po.model.finishedProduct.add.AddFPRequest;
import com.src.printing.office.po.model.finishedProduct.generatePDF.FPGeneratePDFRequest;
import com.src.printing.office.po.model.finishedProduct.save.FPSaveRequest;
import com.src.printing.office.po.model.finishedProduct.search.SearchFPRequest;
import com.src.printing.office.po.model.finishedProduct.transfer.TransferFPRequest;
import com.src.printing.office.po.model.finishedProduct.update.FPUpdateRequest;
import com.src.printing.office.po.model.finishedProduct.writeOff.FPWriteOffRequest;
import com.src.printing.office.po.service.GotovProizvodService;
import com.src.printing.office.token.TokenHeaderCreator;

@RestController
@RequestMapping(value = "/api/po/fp/")
public class GotovProizvodController {

	private Logger logger = LoggerFactory.getLogger(GotovProizvodController.class);

	@Autowired
	private GotovProizvodService gotovProizvodService;

	@Autowired
	private TokenHeaderCreator tokenHeaderCreator;

	@RequestMapping(value = "addFP", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> addFP(@RequestBody @Valid AddFPRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod addFP. " + principal.getUsername());

			gotovProizvodService.addFP(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Gotov Proizvod addFP unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> save(@RequestBody @Valid FPSaveRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod save. " + principal.getUsername());

			return new ResponseEntity<>(gotovProizvodService.save(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Gotov Proizvod save unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public synchronized ResponseEntity<?> update(@RequestBody @Valid FPUpdateRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod update. " + principal.getUsername());

			gotovProizvodService.update(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error("Gotov Proizvod update unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "transferFP", method = RequestMethod.POST)
	public ResponseEntity<?> transferFP(@RequestBody @Valid TransferFPRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod transferFP. " + principal.getUsername());

			return new ResponseEntity<>(gotovProizvodService.transferFP(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

		} catch (ApiException e) {
			switch (e.getApiError().getErrorCode()) {
			case "-404":
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_REQUEST);
			default:
				return new ResponseEntity<>(e.getApiError(), HttpStatus.BAD_GATEWAY);
			}
		} catch (Exception e) {
			logger.error("Gotov Proizvod transferFP unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "searchFP", method = RequestMethod.POST)
	public ResponseEntity<?> searchFP(@RequestBody SearchFPRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod searchFP. " + principal.getUsername());

			return new ResponseEntity<>(gotovProizvodService.searchFP(request),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Gotov Proizvod searchFP unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getOne", method = RequestMethod.GET)
	public ResponseEntity<?> getOne(@Nullable @RequestParam("id") Long id,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod getOne for id: " + id + ". " + principal.getUsername());

			return new ResponseEntity<>(gotovProizvodService.getOne(id),
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
			logger.error("Gotov Proizvod getOne unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getSelected", method = RequestMethod.POST)
	public ResponseEntity<?> getSelected(@RequestBody List<Long> fpIDList,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod getSelected. " + principal.getUsername());

			return new ResponseEntity<>(gotovProizvodService.getSelected(fpIDList),
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
			logger.error("Gotov Proizvod getSelected unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "writeOff", method = RequestMethod.POST)
	public ResponseEntity<?> writeOff(@RequestBody @Valid FPWriteOffRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod writeOff for id: " + request.getFpID() + ". " + principal.getUsername());

			gotovProizvodService.writeOff(request);

			return new ResponseEntity<>(tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.ACCEPTED);

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
			logger.error("Gotov Proizvod writeOff unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "getAllActive", method = RequestMethod.GET)
	public ResponseEntity<?> getAllActive(@AuthenticationPrincipal TokenUser principal) {
		try {

			logger.info("Gotov Proizvod getAllActive. " + principal.getUsername());

			return new ResponseEntity<>(gotovProizvodService.getAllActive(),
					tokenHeaderCreator.getHeader(principal.getToken()), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Gotov Proizvod getAllActive unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		}
	}

	@RequestMapping(value = "generatePDF", method = RequestMethod.POST)
	public ResponseEntity<?> generatePDF(@RequestBody FPGeneratePDFRequest request,
			@AuthenticationPrincipal TokenUser principal) {
		File file = null;
		InputStream resource = null;
		try {

			logger.info("Gotov Proizvod generatePDF. " + principal.getUsername());

			file = gotovProizvodService.generatePDF(request);
			resource = new FileInputStream(file);

			HttpHeaders headers = tokenHeaderCreator.getHeaderForFile(principal.getToken(), file);

			return new ResponseEntity<>(IOUtils.toByteArray(resource), headers, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Gotov Proizvod generatePDF unexpected error", e);
			return new ResponseEntity<>(new ApiError(ApiErrorMessage.DEFAULT_ERROR, e.getMessage()),
					HttpStatus.BAD_GATEWAY);
		} finally {
			if (resource != null)
				try {
					resource.close();
				} catch (IOException e) {
					logger.error("Gotov Proizvod generatePDF -> resource.close() unexpected error", e);
				}
			if (file != null)
				file.delete();
		}
	}
}
