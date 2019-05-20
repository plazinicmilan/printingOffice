package com.src.printing.office.po.assembler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.entity.GotovProizvod;
import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.RadniNalog;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.finishedProduct.add.AddFPRequest;
import com.src.printing.office.po.model.finishedProduct.generatePDF.FPGeneratePDFRequest;
import com.src.printing.office.po.model.finishedProduct.getAllActive.FPGetAllActiveResponse;
import com.src.printing.office.po.model.finishedProduct.getOne.response.FPGetOneResponse;
import com.src.printing.office.po.model.finishedProduct.getSelected.response.FPGetSelectedResponse;
import com.src.printing.office.po.model.finishedProduct.getSelected.response.FPItem;
import com.src.printing.office.po.model.finishedProduct.save.FPSaveRequest;
import com.src.printing.office.po.model.finishedProduct.search.SearchFPResponse;
import com.src.printing.office.po.model.jasper.JasperFinishedProductItem;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class GotovProizvodAssembler {

	@Value("${pdf.date.format}")
	private String dateFormat;

	public List<SearchFPResponse> fromEntityListToSearchFPResponse(List<GotovProizvod> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchFPResponse> response = new ArrayList<>();

		for (GotovProizvod gotovProizvod : list) {
			SearchFPResponse sfpr = new SearchFPResponse();

			sfpr.setId(gotovProizvod.getId());
			sfpr.setCustomerName(gotovProizvod.getKupac().getNaziv());
			if (gotovProizvod.getRadniNalogList() != null) {
				String woNumbers = "";
				for (RadniNalog rn : gotovProizvod.getRadniNalogList()) {
					woNumbers += rn.getBrojRadnogNaloga() + ", ";
				}
				sfpr.setWoNumber(woNumbers.length() > 2 ? woNumbers.substring(0, woNumbers.length() - 2) : woNumbers);
			}
			sfpr.setJobTitle(gotovProizvod.getNaziv());
			sfpr.setQuantity(gotovProizvod.getKolicina());
			sfpr.setQuantityMeasureUnit(gotovProizvod.getJedinicaMere().getNaziv());
			sfpr.setSupplierName(gotovProizvod.getDobavljac() == null ? null : gotovProizvod.getDobavljac().getNaziv());
			sfpr.setPricePerUnit(gotovProizvod.getCenaPoJedinici());

			response.add(sfpr);
		}

		return response;
	}

	public FPGetOneResponse fromGotovProizvodEntityToGetOneResponse(GotovProizvod gotovProizvod) {
		FPGetOneResponse response = new FPGetOneResponse();

		response.setId(gotovProizvod.getId());
		response.setItemName(gotovProizvod.getNaziv());
		response.setMeasureUnit(
				new POGenericType(gotovProizvod.getJedinicaMere().getId(), gotovProizvod.getJedinicaMere().getNaziv()));
		response.setQuantity(gotovProizvod.getKolicina());
		response.setCustomer(new POGenericType(gotovProizvod.getKupac().getId(), gotovProizvod.getKupac().getNaziv()));
		response.setPricePerUnit(gotovProizvod.getCenaPoJedinici());

		return response;
	}

	public GotovProizvod fromSaveRequestToEntity(FPSaveRequest request) {
		GotovProizvod gp = new GotovProizvod();

		gp.setKupac(new Kupac(request.getCustomer().getId()));
		gp.setDobavljac(new Dobavljac(request.getSupplier().getId()));
		gp.setJedinicaMere(new JedinicaMere(request.getMeasureUnit().getId()));
		gp.setNaziv(request.getName());
		gp.setKolicina(request.getQuantity());
		gp.setCenaPoJedinici(request.getPricePerUnit());

		return gp;
	}

	public List<FPGetAllActiveResponse> fromGotovProizvodEntityListToGetAllActiveResponse(
			List<GotovProizvod> gotovProizvodList) {

		if (gotovProizvodList == null)
			return new ArrayList<>();
		List<FPGetAllActiveResponse> responseList = new ArrayList<>();

		for (GotovProizvod gp : gotovProizvodList) {
			String naziv = gp.getNaziv() + " (" + gp.getKupac().getNaziv()
					+ (gp.getDobavljac() == null ? "" : " - " + gp.getDobavljac().getNaziv()) + ")";

			FPGetAllActiveResponse resp = new FPGetAllActiveResponse();

			resp.setId(gp.getId());
			resp.setName(naziv);
			resp.setQuantity(gp.getKolicina());
			resp.setMeasureUnit(new POGenericType(gp.getJedinicaMere().getId(), gp.getJedinicaMere().getNaziv()));
			resp.setOriginalName(gp.getNaziv());
			if (gp.getRadniNalogList() != null) {
				for (RadniNalog rn : gp.getRadniNalogList()) {
					resp.getWoIDList().add(rn.getId());
				}
			}
			resp.setPricePerUnit(gp.getCenaPoJedinici());

			responseList.add(resp);
		}

		return responseList;
	}

	public FPGetSelectedResponse fromEntityListToGetSelectedResponse(List<GotovProizvod> gpList) {
		FPGetSelectedResponse response = new FPGetSelectedResponse();

		response.setCustomer(new POGenericType(gpList.get(0).getKupac().getId(), gpList.get(0).getKupac().getNaziv()));
		for (GotovProizvod gotovProizvod : gpList) {
			FPItem fpItem = new FPItem();

			fpItem.setId(gotovProizvod.getId());
			fpItem.setItemName(gotovProizvod.getNaziv());
			fpItem.setMeasureUnit(new POGenericType(gotovProizvod.getJedinicaMere().getId(),
					gotovProizvod.getJedinicaMere().getNaziv()));
			fpItem.setPricePerUnit(gotovProizvod.getCenaPoJedinici());
			fpItem.setQuantity(gotovProizvod.getKolicina());
			fpItem.setWoIDList(
					gotovProizvod.getRadniNalogList().stream().map(x -> x.getId()).collect(Collectors.toList()));

			response.getFpItems().add(fpItem);
		}

		return response;
	}

	public GotovProizvod fromAddFPRequestToEntity(RadniNalog radniNalog, AddFPRequest request) {
		GotovProizvod gp = new GotovProizvod();

		gp.setKupac(radniNalog.getKupac());
		gp.setJedinicaMere(radniNalog.getTirazJM());
		gp.setNaziv(request.getFpName().trim());
		gp.setKolicina(request.getQuantity());
		gp.getRadniNalogList().add(radniNalog);

		return gp;
	}

	public Map<String, Object> fromEntityToPDFParameters(List<GotovProizvod> list, FPGeneratePDFRequest request) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("datumOD", createDateString(request.getDateFrom()));
		parameters.put("datumDO", createDateString(request.getDateTo()));
		parameters.put("gotovProizvodLista", new JRBeanCollectionDataSource(createJasperListFromEntity(list)));
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY);

		return parameters;
	}

	private Collection<?> createJasperListFromEntity(List<GotovProizvod> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<JasperFinishedProductItem> fpItems = new ArrayList<>();
		int no = 1;

		for (GotovProizvod gp : list) {
			JasperFinishedProductItem item = new JasperFinishedProductItem();

			item.setRb(no + ".");
			no++;
			item.setNaziv(gp.getNaziv());
			item.setDatumOtpisa(createDateString(gp.getDatumOtpisa()));
			item.setDobavljac(gp.getDobavljac() != null ? gp.getDobavljac().getNaziv() : null);
			item.setJedinicaMere(gp.getJedinicaMere().getNaziv());
			item.setKolicinaOtpisano(gp.getOtpisano());
			item.setKupac(gp.getKupac().getNaziv());
			item.setRazlogOtpisa(gp.getRazlogOtpisa());

			fpItems.add(item);
		}

		return fpItems;
	}

	private String createDateString(Calendar cal) {
		return cal != null ? new SimpleDateFormat(dateFormat).format(cal.getTime()) : null;
	}
}
