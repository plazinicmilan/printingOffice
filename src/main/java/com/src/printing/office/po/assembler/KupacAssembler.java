package com.src.printing.office.po.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.customer.getAll.response.CustomerGetAllActiveResponse;
import com.src.printing.office.po.model.customer.save.request.CustomerSaveRequest;
import com.src.printing.office.po.model.customer.search.response.SearchCustomerResponse;
import com.src.printing.office.po.model.customer.update.request.CustomerUpdateRequest;

@Component
public class KupacAssembler {

	public Set<POGenericType> fromKupacEntityListToCustomerResponse(Set<Kupac> kupacList) {

		if (kupacList == null)
			return new TreeSet<>();

		Set<POGenericType> responseList = new TreeSet<>();

		for (Kupac kupac : kupacList) {
			responseList.add(new POGenericType(kupac.getId(), kupac.getNaziv()));
		}

		return responseList;
	}

	public Set<CustomerGetAllActiveResponse> fromKupacEntityListToGetAllActiveResponse(List<Kupac> kupacList) {

		if (kupacList == null)
			return new TreeSet<>();

		Set<CustomerGetAllActiveResponse> responseList = new TreeSet<>();
		for (Kupac kupac : kupacList) {
			responseList.add(new CustomerGetAllActiveResponse(kupac.getId(), kupac.getNaziv(),
					kupac.getPripadnost() == null ? null
							: new POGenericType(kupac.getPripadnost().getId(), kupac.getPripadnost().getNaziv())));
		}

		return responseList;
	}

	public Kupac fromCustomerSaveRequestToEntity(CustomerSaveRequest request) {

		Kupac k = new Kupac();

		k.setAdresa(request.getAddress());
		k.setGrad(request.getCity());
		k.setPib(request.getPib());
		k.setPdv(request.getPdv());
		k.setTip(request.isCustomerType());
		k.setKontakt(request.getContact());
		k.setNaziv(request.getName());
		k.setPripadnost(request.getBelongsTo() == null ? null : new Kupac(request.getBelongsTo().getId()));

		return k;
	}

	public List<SearchCustomerResponse> fromEntityListToSearchCustomerResponse(List<Kupac> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchCustomerResponse> responseList = new ArrayList<>();

		for (Kupac kupac : list) {
			SearchCustomerResponse scr = new SearchCustomerResponse();

			scr.setId(kupac.getId());
			scr.setName(kupac.getNaziv());
			scr.setContact(kupac.getKontakt());
			scr.setAddress(kupac.getAdresa());
			scr.setCity(kupac.getGrad());
			scr.setPib(kupac.getPib());
			scr.setPdv(kupac.getPdv());
			scr.setCustomerType(kupac.getTip());
			scr.setBelongsTo(kupac.getPripadnost() == null ? null
					: new POGenericType(kupac.getPripadnost().getId(), kupac.getPripadnost().getNaziv()));
			scr.setInactive(kupac.getNeaktivan());

			responseList.add(scr);

		}

		return responseList;
	}

	public Kupac updateKupacFromRequest(Kupac kupac, CustomerUpdateRequest request) {

		kupac.setNaziv(request.getName());
		kupac.setKontakt(request.getContact());
		kupac.setAdresa(request.getAddress());
		kupac.setGrad(request.getCity());
		kupac.setPib(request.getPib());
		kupac.setPdv(request.getPdv());
		kupac.setTip(request.isCustomerType());
		kupac.setPripadnost(request.getBelongsTo() == null ? null : new Kupac(request.getBelongsTo().getId()));
		kupac.setNeaktivan(request.getInactive());

		return kupac;
	}

}
