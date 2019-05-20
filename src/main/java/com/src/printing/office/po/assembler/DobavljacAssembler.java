package com.src.printing.office.po.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.model.supplier.getAll.response.SupplierGetAllResponse;
import com.src.printing.office.po.model.supplier.save.request.SupplierSaveRequest;
import com.src.printing.office.po.model.supplier.search.response.SearchSupplierResponse;
import com.src.printing.office.po.model.supplier.update.request.SupplierUpdateRequest;

@Component
public class DobavljacAssembler {

	public Set<SupplierGetAllResponse> fromDobavljacEntityListToSupplierGetAllResponse(Set<Dobavljac> dobavljacList) {

		if (dobavljacList == null)
			return new TreeSet<>();

		Set<SupplierGetAllResponse> responseList = new TreeSet<>();

		for (Dobavljac dobavljac : dobavljacList) {
			responseList.add(new SupplierGetAllResponse(dobavljac.getId(), dobavljac.getNaziv()));
		}

		return responseList;
	}

	public Dobavljac fromSupplierSaveRequestToEntity(SupplierSaveRequest request) {

		Dobavljac d = new Dobavljac();

		d.setNaziv(request.getName());
		d.setPdv(request.getPdv());
		d.setPib(request.getPib());
		d.setTip(request.isSupplierType());
		d.setKontakt(request.getContact());
		d.setAdresa(request.getAddress());

		return d;
	}

	public List<SearchSupplierResponse> fromEntityListToSearchSupplerResponse(List<Dobavljac> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchSupplierResponse> responseList = new ArrayList<>();

		for (Dobavljac dobavljac : list) {
			SearchSupplierResponse scr = new SearchSupplierResponse();

			scr.setId(dobavljac.getId());
			scr.setName(dobavljac.getNaziv());
			scr.setPib(dobavljac.getPib());
			scr.setPdv(dobavljac.getPdv());
			scr.setSupplierType(dobavljac.getTip());
			scr.setContact(dobavljac.getKontakt());
			scr.setAddress(dobavljac.getAdresa());

			responseList.add(scr);

		}

		return responseList;
	}

	public Dobavljac updateDobavljacFromRequest(Dobavljac dobavljac, SupplierUpdateRequest request) {

		dobavljac.setNaziv(request.getName());
		dobavljac.setPib(request.getPib());
		dobavljac.setPdv(request.getPdv());
		dobavljac.setTip(request.isSupplierType());
		dobavljac.setKontakt(request.getContact());
		dobavljac.setAdresa(request.getAddress());

		return dobavljac;
	}

}
