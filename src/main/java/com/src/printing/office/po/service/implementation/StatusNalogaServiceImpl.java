package com.src.printing.office.po.service.implementation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.printing.office.po.assembler.StatusNalogaAssembler;
import com.src.printing.office.po.entity.StatusNaloga;
import com.src.printing.office.po.model.statusWO.getAll.response.StatusWOResponse;
import com.src.printing.office.po.repository.StatusNalogaRepository;
import com.src.printing.office.po.service.StatusNalogaService;

@Service
public class StatusNalogaServiceImpl implements StatusNalogaService {

	@Autowired
	private StatusNalogaRepository statusNalogaRepository;

	@Autowired
	private StatusNalogaAssembler statusNalogaAssembler;

	@Override
	public Set<StatusWOResponse> getAll() {

		Set<StatusNaloga> statusNalogaList = statusNalogaRepository.findAll();

		return statusNalogaAssembler.fromStatusNalogaEntityListToStatusWOResponse(statusNalogaList);

	}

}
