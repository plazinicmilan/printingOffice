package com.src.printing.office.po.service;

import java.util.Set;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.measureUnit.save.MUSaveRequest;

public interface JedinicaMereService {

	public Set<POGenericType> getAll();

	public long save(MUSaveRequest request) throws ApiException;

}
