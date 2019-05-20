package com.src.printing.office.po.assembler;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.StatusNaloga;
import com.src.printing.office.po.model.statusWO.getAll.response.StatusWOResponse;

@Component
public class StatusNalogaAssembler {

	public Set<StatusWOResponse> fromStatusNalogaEntityListToStatusWOResponse(Set<StatusNaloga> statusNalogaList) {

		if (statusNalogaList == null)
			return new TreeSet<>();

		Set<StatusWOResponse> responseList = new TreeSet<>();

		for (StatusNaloga sn : statusNalogaList) {
			responseList.add(new StatusWOResponse(sn.getId(), sn.getNaziv()));
		}

		return responseList;
	}

}
