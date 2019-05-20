package com.src.printing.office.po.service;

import java.io.File;
import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.deliveryNote.finish.DNFinishRequest;
import com.src.printing.office.po.model.deliveryNote.getOne.response.DNGetOneResponse;
import com.src.printing.office.po.model.deliveryNote.getOneForReceipt.response.DNGetOneForReceiptResponse;
import com.src.printing.office.po.model.deliveryNote.save.request.DNSaveRequest;
import com.src.printing.office.po.model.deliveryNote.save.response.DNSaveResponse;
import com.src.printing.office.po.model.deliveryNote.search.SearchDNRequest;
import com.src.printing.office.po.model.deliveryNote.search.SearchDNResponse;
import com.src.printing.office.po.model.deliveryNote.update.request.DNUpdateRequest;

public interface OtpremnicaService {

	public DNSaveResponse save(DNSaveRequest request) throws ApiException;

	public List<SearchDNResponse> search(SearchDNRequest request);

	public void cancel(long dnID) throws ApiException;

	public DNGetOneResponse getOne(Long id) throws ApiException;

	public File generatePDF(long dnID) throws Exception;

	public List<POGenericType> getAllUnreceipted();

	public DNGetOneForReceiptResponse getOneForReceipt(Long id) throws ApiException;

	public void finish(DNFinishRequest request);

	public void update(DNUpdateRequest request) throws ApiException;

}
