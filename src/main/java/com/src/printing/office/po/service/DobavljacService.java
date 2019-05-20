package com.src.printing.office.po.service;

import java.util.List;
import java.util.Set;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.supplier.getAll.response.SupplierGetAllResponse;
import com.src.printing.office.po.model.supplier.save.request.SupplierSaveRequest;
import com.src.printing.office.po.model.supplier.search.request.SearchSupplierRequest;
import com.src.printing.office.po.model.supplier.search.response.SearchSupplierResponse;
import com.src.printing.office.po.model.supplier.update.request.SupplierUpdateRequest;

public interface DobavljacService {

	public Set<SupplierGetAllResponse> getAll();

	public long save(SupplierSaveRequest request) throws ApiException;

	public List<SearchSupplierResponse> search(SearchSupplierRequest request);

	public void update(SupplierUpdateRequest request) throws ApiException;

}
