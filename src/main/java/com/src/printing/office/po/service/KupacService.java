package com.src.printing.office.po.service;

import java.util.List;
import java.util.Set;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.customer.getAll.response.CustomerGetAllActiveResponse;
import com.src.printing.office.po.model.customer.save.request.CustomerSaveRequest;
import com.src.printing.office.po.model.customer.search.request.SearchCustomerRequest;
import com.src.printing.office.po.model.customer.search.response.SearchCustomerResponse;
import com.src.printing.office.po.model.customer.update.request.CustomerUpdateRequest;

public interface KupacService {

	public Set<POGenericType> getAll();

	public Set<CustomerGetAllActiveResponse> getAllActive();

	public long save(CustomerSaveRequest request) throws ApiException;

	public List<SearchCustomerResponse> search(SearchCustomerRequest request);

	public void update(CustomerUpdateRequest request) throws ApiException;

}
