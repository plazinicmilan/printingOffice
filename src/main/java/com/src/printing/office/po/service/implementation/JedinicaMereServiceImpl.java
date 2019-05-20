package com.src.printing.office.po.service.implementation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.assembler.JedinicaMereAssembler;
import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.measureUnit.save.MUSaveRequest;
import com.src.printing.office.po.repository.JedinicaMereRepository;
import com.src.printing.office.po.service.JedinicaMereService;

@Service
public class JedinicaMereServiceImpl implements JedinicaMereService {

	@Autowired
	private JedinicaMereRepository jedinicaMereRepository;

	@Autowired
	private JedinicaMereAssembler jedinicaMereAssembler;

	@Override
	public Set<POGenericType> getAll() {

		Set<JedinicaMere> jmList = jedinicaMereRepository.findAll();

		return jedinicaMereAssembler.fromEntityListToMUGetAllResponse(jmList);

	}

	@Override
	public long save(MUSaveRequest request) throws ApiException {
		JedinicaMere jm = jedinicaMereRepository.findByName(request.getName());

		if (jm != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "Jedinica mere"));

		JedinicaMere jedinicaMere = jedinicaMereAssembler.fromMUSaveRequestToEntity(request);

		jedinicaMere = jedinicaMereRepository.save(jedinicaMere);

		return jedinicaMere.getId();
	}

}
