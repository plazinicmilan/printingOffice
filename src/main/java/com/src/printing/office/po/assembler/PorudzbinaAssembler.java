package com.src.printing.office.po.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Porudzbina;
import com.src.printing.office.po.model.order.save.OrderSaveRequest;
import com.src.printing.office.po.model.order.search.OrderSearchResponse;
import com.src.printing.office.po.model.order.update.OrderUpdateRequest;

@Component
public class PorudzbinaAssembler {

	public Porudzbina fromOrderSaveRequestToEntity(OrderSaveRequest request) {
		Porudzbina p = new Porudzbina();

		p.setDatum(request.getDate());
		p.setKorisnik(new Korisnik(request.getUserID()));
		p.setKupac(request.getCustomer() == null ? null : new Kupac(request.getCustomer().getId()));
		p.setNazivPosla(request.getJobTitle());
		p.setOpis(request.getDescription());
		p.setTiraz(request.getCirculation());

		return p;
	}

	public List<OrderSearchResponse> fromEntityListToSearchResponse(List<Porudzbina> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<OrderSearchResponse> response = new ArrayList<>();

		for (Porudzbina porudzbina : list) {
			OrderSearchResponse osr = new OrderSearchResponse();

			osr.setId(porudzbina.getId());
			osr.setCirculation(porudzbina.getTiraz());
			osr.setCustomer(porudzbina.getKupac() == null ? null : porudzbina.getKupac().getNaziv());
			osr.setDate(porudzbina.getDatum());
			osr.setDescription(porudzbina.getOpis());
			osr.setJobTitle(porudzbina.getNazivPosla());
			osr.setUser(porudzbina.getKorisnik().getIme() + " " + porudzbina.getKorisnik().getPrezime());
			osr.setAcceptedUser(porudzbina.getKorisnikIzvrsilac() != null
					? porudzbina.getKorisnikIzvrsilac().getIme() + " " + porudzbina.getKorisnikIzvrsilac().getPrezime()
					: null);

			response.add(osr);
		}

		return response;
	}

	public Porudzbina fromUpdateRequestToEntity(Porudzbina porudzbina, OrderUpdateRequest request) {

		porudzbina.setId(request.getId());
		porudzbina.setDatum(request.getDate());
		porudzbina.setKorisnik(new Korisnik(request.getUserID()));
		porudzbina.setKupac(request.getCustomer() == null ? null : new Kupac(request.getCustomer().getId()));
		porudzbina.setNazivPosla(request.getJobTitle());
		porudzbina.setOpis(request.getDescription());
		porudzbina.setTiraz(request.getCirculation());

		return porudzbina;
	}

}
