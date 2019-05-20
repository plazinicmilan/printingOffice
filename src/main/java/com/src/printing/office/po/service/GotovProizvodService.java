package com.src.printing.office.po.service;

import java.io.File;
import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.finishedProduct.add.AddFPRequest;
import com.src.printing.office.po.model.finishedProduct.generatePDF.FPGeneratePDFRequest;
import com.src.printing.office.po.model.finishedProduct.getAllActive.FPGetAllActiveResponse;
import com.src.printing.office.po.model.finishedProduct.getOne.response.FPGetOneResponse;
import com.src.printing.office.po.model.finishedProduct.getSelected.response.FPGetSelectedResponse;
import com.src.printing.office.po.model.finishedProduct.save.FPSaveRequest;
import com.src.printing.office.po.model.finishedProduct.search.SearchFPRequest;
import com.src.printing.office.po.model.finishedProduct.search.SearchFPResponse;
import com.src.printing.office.po.model.finishedProduct.transfer.TransferFPRequest;
import com.src.printing.office.po.model.finishedProduct.transfer.TransferFPResponse;
import com.src.printing.office.po.model.finishedProduct.update.FPUpdateRequest;
import com.src.printing.office.po.model.finishedProduct.writeOff.FPWriteOffRequest;

public interface GotovProizvodService {

	public void addFP(AddFPRequest request) throws ApiException;

	public List<SearchFPResponse> searchFP(SearchFPRequest request);

	public TransferFPResponse transferFP(TransferFPRequest request) throws ApiException;

	public FPGetOneResponse getOne(Long id) throws ApiException;

	public long save(FPSaveRequest request);

	public void writeOff(FPWriteOffRequest request) throws ApiException;

	public List<FPGetAllActiveResponse> getAllActive();

	public FPGetSelectedResponse getSelected(List<Long> fpIDList) throws ApiException;

	public void update(FPUpdateRequest request) throws ApiException;

	public File generatePDF(FPGeneratePDFRequest request) throws Exception;

}
