package com.src.printing.office.po.service;

import java.io.File;
import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.material.generateConsumptionPDF.MaterialGenerateConsumptionPDFRequest;
import com.src.printing.office.po.model.material.getAll.response.MaterialResponse;
import com.src.printing.office.po.model.material.getAllActiveForDN.MaterialGetAllActiveForDNResponse;
import com.src.printing.office.po.model.material.getAllCritical.response.MaterialGetAllCriticalResponse;
import com.src.printing.office.po.model.material.getAllPapers.MaterialGetAllPapersResponse;
import com.src.printing.office.po.model.material.getAllPapersForDN.MaterialGetAllPapersForDNResponse;
import com.src.printing.office.po.model.material.getOne.response.MaterialGetOneResponse;
import com.src.printing.office.po.model.material.save.request.MaterialSaveRequest;
import com.src.printing.office.po.model.material.search.request.SearchMaterialRequest;
import com.src.printing.office.po.model.material.search.response.SearchMaterialResponse;
import com.src.printing.office.po.model.material.update.MaterialUpdateRequest;

public interface MaterijalService {

	public List<MaterialResponse> getAll(String type);

	public List<MaterialGetAllPapersResponse> getAllPapers();

	public List<MaterialGetAllCriticalResponse> getAllCritical();

	public List<MaterialGetAllPapersForDNResponse> getAllPapersForDN();

	public List<MaterialGetAllActiveForDNResponse> getAllActiveForDN();

	public List<POGenericType> getAllSpirals();

	public long save(MaterialSaveRequest request) throws ApiException;

	public List<SearchMaterialResponse> search(SearchMaterialRequest request);

	public MaterialGetOneResponse getOne(Long id) throws ApiException;

	public void update(MaterialUpdateRequest request) throws ApiException;

	public File generatePDF(SearchMaterialRequest request) throws Exception;

	public File generateConsumptionPDF(MaterialGenerateConsumptionPDFRequest request) throws Exception;

}
