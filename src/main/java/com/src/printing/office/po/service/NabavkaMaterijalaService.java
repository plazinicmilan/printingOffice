package com.src.printing.office.po.service;

import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.pom.getOne.response.POMGetOneResponse;
import com.src.printing.office.po.model.pom.save.request.POMSaveRequest;
import com.src.printing.office.po.model.pom.search.request.SearchPOMRequest;
import com.src.printing.office.po.model.pom.search.response.SearchPOMResponse;

public interface NabavkaMaterijalaService {

	public void save(POMSaveRequest request);

	public List<SearchPOMResponse> search(SearchPOMRequest request);

	public void delete(long pomID) throws ApiException;

	public POMGetOneResponse getOne(Long id) throws ApiException;

}
