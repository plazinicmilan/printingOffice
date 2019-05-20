package com.src.printing.office.po.assembler;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.measureUnit.save.MUSaveRequest;

@Component
public class JedinicaMereAssembler {

	public Set<POGenericType> fromEntityListToMUGetAllResponse(Set<JedinicaMere> jmList) {
		if (jmList == null)
			return new TreeSet<>();

		Set<POGenericType> responseList = new TreeSet<>();

		for (JedinicaMere jm : jmList) {
			responseList.add(new POGenericType(jm.getId(), jm.getNaziv()));
		}

		return responseList;
	}

	public JedinicaMere fromMUSaveRequestToEntity(MUSaveRequest request) {
		return new JedinicaMere(request.getName());
	}

}
