package com.src.printing.office.po.service;

import java.io.File;
import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.receipt.getOne.response.ReceiptGetOneResponse;
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveRequest;
import com.src.printing.office.po.model.receipt.save.response.ReceiptSaveResponse;
import com.src.printing.office.po.model.receipt.search.SearchReceiptRequest;
import com.src.printing.office.po.model.receipt.search.SearchReceiptResponse;
import com.src.printing.office.po.model.receipt.update.ReceiptUpdateRequest;

public interface RacunService {

	public ReceiptSaveResponse save(ReceiptSaveRequest request) throws ApiException;

	public List<SearchReceiptResponse> search(SearchReceiptRequest request);

	public void cancel(long receiptID) throws ApiException;

	public ReceiptGetOneResponse getOne(Long id) throws ApiException;

	public File generatePDF(long receiptID) throws Exception;

	public void update(ReceiptUpdateRequest request);

}
