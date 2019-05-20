package com.src.printing.office.po.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.entity.NabavkaMaterijala;
import com.src.printing.office.po.entity.StavkaNabavke;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.pom.getOne.response.POMGetOneResponse;
import com.src.printing.office.po.model.pom.save.request.POMItem;
import com.src.printing.office.po.model.pom.save.request.POMSaveRequest;
import com.src.printing.office.po.model.pom.search.response.SearchPOMResponse;

@Component
public class NabavkaMaterijalaAssembler {

	public NabavkaMaterijala fromPOMSaveRequestToEntity(POMSaveRequest request) {
		NabavkaMaterijala nm = new NabavkaMaterijala();

		nm.setKorisnik(new Korisnik(request.getUserID()));
		nm.setDatumNabavke(request.getDate());
		nm.setBrojNabavke(request.getPomNumber());
		nm.setDobavljac(new Dobavljac(request.getSupplier().getId()));
		nm.setUkupnaSuma(request.getTotalAmount());

		nm.setStavkaNabavkeList(createStavkaNabavkeListFromPOMSaveRequest(nm, request.getPomItems()));
		return nm;
	}

	private List<StavkaNabavke> createStavkaNabavkeListFromPOMSaveRequest(NabavkaMaterijala nm,
			List<POMItem> pomItems) {

		List<StavkaNabavke> stavkaNabavkeList = new ArrayList<>();

		for (POMItem pomItem : pomItems) {

			StavkaNabavke sn = new StavkaNabavke();

			sn.setMaterijal(new Materijal(pomItem.getMaterial().getId()));
			sn.setKolicina(pomItem.getQuantity());
			sn.setCenaPoKg(pomItem.getPricePerUnit());
			sn.setCena(pomItem.getPrice());
			sn.setRedniBroj(pomItem.getNo());
			sn.setNabavkaMaterijala(nm);

			stavkaNabavkeList.add(sn);
		}

		return stavkaNabavkeList;
	}

	public List<SearchPOMResponse> fromEntityListToSearchPOMResponse(List<NabavkaMaterijala> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchPOMResponse> responseList = new ArrayList<>();

		for (NabavkaMaterijala nm : list) {
			SearchPOMResponse spomr = new SearchPOMResponse();

			spomr.setId(nm.getId());
			spomr.setDatePOM(nm.getDatumNabavke());
			spomr.setUser(nm.getKorisnik().getIme() + " " + nm.getKorisnik().getPrezime());
			spomr.setPomNumber(nm.getBrojNabavke());
			spomr.setSupplier(nm.getDobavljac().getNaziv());
			spomr.setTotalAmount(nm.getUkupnaSuma());

			responseList.add(spomr);

		}

		return responseList;
	}

	public POMGetOneResponse fromNabavkaMaterijalaEntityToGetOneResponse(NabavkaMaterijala nabavkaMaterijala) {
		POMGetOneResponse response = new POMGetOneResponse();

		response.setDate(nabavkaMaterijala.getDatumNabavke());
		response.setPomNumber(nabavkaMaterijala.getBrojNabavke());
		response.setSupplier(new POGenericType(nabavkaMaterijala.getDobavljac().getId(),
				nabavkaMaterijala.getDobavljac().getNaziv()));
		response.setTotalAmount(nabavkaMaterijala.getUkupnaSuma());

		response.setPomItems(createPOMItemsFromStavkaNabavkeList(nabavkaMaterijala.getStavkaNabavkeList()));

		return response;
	}

	private List<com.src.printing.office.po.model.pom.getOne.response.POMItem> createPOMItemsFromStavkaNabavkeList(
			List<StavkaNabavke> list) {
		if (list == null)
			return null;

		List<com.src.printing.office.po.model.pom.getOne.response.POMItem> pomItemsList = new ArrayList<>();

		for (StavkaNabavke sn : list) {

			com.src.printing.office.po.model.pom.getOne.response.POMItem item = new com.src.printing.office.po.model.pom.getOne.response.POMItem();

			item.setId(sn.getId());
			item.setMaterial(new POGenericType(sn.getMaterijal().getId(), sn.getMaterijal().getNaziv()));
			item.setNo(sn.getRedniBroj());
			item.setPrice(sn.getCena());
			item.setPricePerUnit(sn.getCenaPoKg());
			item.setQuantity(sn.getKolicina());

			pomItemsList.add(item);
		}

		return pomItemsList;
	}

}
