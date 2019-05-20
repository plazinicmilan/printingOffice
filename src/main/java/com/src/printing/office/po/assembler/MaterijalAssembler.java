package com.src.printing.office.po.assembler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.jasper.JasperMaterialConsumptionItem;
import com.src.printing.office.po.model.jasper.JasperMaterialItem;
import com.src.printing.office.po.model.material.generateConsumptionPDF.MaterialGenerateConsumptionPDFRequest;
import com.src.printing.office.po.model.material.generateConsumptionPDF.MaterialGenerateConsumptionPDFResponse;
import com.src.printing.office.po.model.material.getAll.response.MaterialResponse;
import com.src.printing.office.po.model.material.getAllActiveForDN.MaterialGetAllActiveForDNResponse;
import com.src.printing.office.po.model.material.getAllCritical.response.MaterialGetAllCriticalResponse;
import com.src.printing.office.po.model.material.getAllPapers.MaterialGetAllPapersResponse;
import com.src.printing.office.po.model.material.getAllPapersForDN.MaterialGetAllPapersForDNResponse;
import com.src.printing.office.po.model.material.getOne.response.MaterialGetOneResponse;
import com.src.printing.office.po.model.material.save.request.MaterialSaveRequest;
import com.src.printing.office.po.model.material.search.response.SearchMaterialResponse;
import com.src.printing.office.po.model.material.update.MaterialUpdateRequest;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class MaterijalAssembler {

	@Value("${pdf.date.format}")
	private String dateFormat;

	@Value("${measure.unit.tabak}")
	private String tabakMeasureUnit;

	public List<MaterialResponse> fromMaterijalEntityListToMaterialResponse(List<Materijal> materijalList) {

		if (materijalList == null)
			return new ArrayList<>();

		List<MaterialResponse> responseList = new ArrayList<>();

		for (Materijal materijal : materijalList) {
			String jedinicaMere = materijal.getPakovanjeJM() != null
					? "(" + materijal.getPakovanjeKolicina() + " " + materijal.getPakovanjeJM().getNaziv() + ")"
					: materijal.getJedinicaMere() != null ? "(" + materijal.getJedinicaMere().getNaziv() + ")" : "";
			responseList.add(new MaterialResponse(materijal.getId(),
					materijal.getNaziv() + " " + materijal.getProizvodjac() + " " + jedinicaMere));
		}

		return responseList;
	}

	public List<MaterialGetAllPapersResponse> fromMaterijalEntityListToGetAllPapersResponse(
			List<Materijal> materijalList) {
		if (materijalList == null)
			return new ArrayList<>();

		List<MaterialGetAllPapersResponse> responseList = new ArrayList<>();

		for (Materijal materijal : materijalList) {
			String kolicina = materijal.getJedinicaMere() != null
					? "(" + materijal.getKolicina() + " " + materijal.getJedinicaMere().getNaziv() + ")"
					: "";
			responseList.add(new MaterialGetAllPapersResponse(materijal.getId(),
					materijal.getNaziv() + " " + materijal.getProizvodjac() + " " + kolicina,
					materijal.getKolicina() != null && materijal.getKolicina().doubleValue() > 0));
		}

		return responseList;
	}

	public List<MaterialGetAllCriticalResponse> fromMaterijalEntityListToGetAllCriticalResponse(
			List<Materijal> materijalList) {
		if (materijalList == null || materijalList.size() == 0)
			return new ArrayList<>();

		List<MaterialGetAllCriticalResponse> responseList = new ArrayList<>();

		for (Materijal materijal : materijalList) {
			MaterialGetAllCriticalResponse response = new MaterialGetAllCriticalResponse();

			response.setId(materijal.getId());
			response.setLimit(materijal.getDozvoljeniLimit());
			response.setMaker(materijal.getProizvodjac());
			response.setMaterialType(materijal.getTip());
			response.setName(materijal.getNaziv());
			response.setQuantity(materijal.getKolicina());
			response.setMeasureUnit(
					materijal.getJedinicaMere() != null ? materijal.getJedinicaMere().getNaziv() : null);
			response.setPackageQuantity(materijal.getPakovanjeKolicina());
			response.setPackageMeasureUnit(
					materijal.getPakovanjeJM() != null ? materijal.getPakovanjeJM().getNaziv() : null);

			responseList.add(response);
		}

		return responseList;
	}

	public List<MaterialGetAllPapersForDNResponse> fromMaterijalEntityListToGetAllPapersForDNResponse(
			List<Materijal> materijalList) {
		if (materijalList == null || materijalList.size() == 0)
			return new ArrayList<>();

		List<MaterialGetAllPapersForDNResponse> responseList = new ArrayList<>();

		for (Materijal materijal : materijalList) {
			MaterialGetAllPapersForDNResponse response = new MaterialGetAllPapersForDNResponse();

			response.setId(materijal.getId());
			JedinicaMere jm = materijal.getJedinicaMere();
			response.setMeasureUnit(jm == null ? null : new POGenericType(jm.getId(), jm.getNaziv()));
			response.setName(materijal.getNaziv() + " " + materijal.getProizvodjac() + " "
					+ (jm == null ? "" : "(" + materijal.getKolicina() + " " + jm.getNaziv() + ")"));
			response.setOriginalName(materijal.getNaziv());
			response.setQuantity(materijal.getKolicina() == null ? null : materijal.getKolicina().intValue());

			responseList.add(response);
		}

		return responseList;
	}

	public List<MaterialGetAllActiveForDNResponse> fromMaterijalEntityListToGetAllActiveForDNResponse(
			List<Materijal> materijalList) {
		if (materijalList == null || materijalList.size() == 0)
			return new ArrayList<>();

		List<MaterialGetAllActiveForDNResponse> responseList = new ArrayList<>();

		for (Materijal materijal : materijalList) {
			MaterialGetAllActiveForDNResponse response = new MaterialGetAllActiveForDNResponse();

			response.setId(materijal.getId());
			JedinicaMere jm = materijal.getJedinicaMere();
			response.setMeasureUnit(jm == null ? null : new POGenericType(jm.getId(), jm.getNaziv()));
			response.setName(materijal.getNaziv() + " " + materijal.getProizvodjac() + " "
					+ (jm == null ? "" : "(" + materijal.getKolicina() + " " + jm.getNaziv() + ")"));
			response.setOriginalName(materijal.getNaziv());
			response.setQuantity(materijal.getKolicina() == null ? null : materijal.getKolicina().intValue());

			responseList.add(response);
		}

		return responseList;
	}

	public List<POGenericType> fromMaterijalEntityListToGetAllSpirals(List<Materijal> materijalList) {
		if (materijalList == null)
			return new ArrayList<>();

		List<POGenericType> responseList = new ArrayList<>();

		for (Materijal materijal : materijalList) {
			String kolicina = materijal.getJedinicaMere() != null
					? "(" + materijal.getKolicina() + " " + materijal.getJedinicaMere().getNaziv() + ")"
					: "";
			responseList.add(new POGenericType(materijal.getId(),
					materijal.getNaziv() + " " + materijal.getProizvodjac() + " " + kolicina));
		}

		return responseList;
	}

	public Materijal fromMaterialSaveRequestToEntity(MaterialSaveRequest request) {

		Materijal m = new Materijal();

		m.setNaziv(request.getName());
		m.setProizvodjac(request.getMaker().toUpperCase());
//		m.setDobavljac(new Dobavljac(request.getSupplier().getId()));
		m.setTip(request.getMaterialType());
		m.setPakovanjeKolicina(request.getPackageQuantity());
		m.setPakovanjeJM(request.getPackageMeasureUnit() == null ? null
				: new JedinicaMere(request.getPackageMeasureUnit().getId()));
		m.setKolicina(request.getQuantity());
		m.setJedinicaMere(request.getMeasureUnit() == null ? null : new JedinicaMere(request.getMeasureUnit().getId()));
		m.setCena(request.getPrice());
		m.setDozvoljeniLimit(request.getLimit());

		return m;
	}

	public List<SearchMaterialResponse> fromEntityListToSearchMaterialResponse(List<Materijal> list) {

		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchMaterialResponse> responseList = new ArrayList<>();

		for (Materijal materijal : list) {
			SearchMaterialResponse smr = new SearchMaterialResponse();

			smr.setId(materijal.getId());
			smr.setName(materijal.getNaziv());
			smr.setLimit(materijal.getDozvoljeniLimit());
			smr.setMeasureUnit(materijal.getJedinicaMere() == null ? null : materijal.getJedinicaMere().getNaziv());
			smr.setQuantity(materijal.getKolicina());
			smr.setMaker(materijal.getProizvodjac());
//			smr.setSupplier(materijal.getDobavljac().getNaziv());
			smr.setPackageQuantity(materijal.getPakovanjeKolicina());
			smr.setPackageMeasureUnit(
					materijal.getPakovanjeJM() != null ? materijal.getPakovanjeJM().getNaziv() : null);
			smr.setInactive(materijal.getNeaktivan());

			responseList.add(smr);

		}

		return responseList;
	}

	public MaterialGetOneResponse fromMaterijalEntityToGetOneResponse(Materijal materijal) {
		MaterialGetOneResponse response = new MaterialGetOneResponse();

		response.setId(materijal.getId());
		response.setName(materijal.getNaziv());
		response.setQuantity(materijal.getKolicina());
		response.setLimit(materijal.getDozvoljeniLimit());
		response.setMeasureUnit(materijal.getJedinicaMere() == null ? null
				: new POGenericType(materijal.getJedinicaMere().getId(), materijal.getJedinicaMere().getNaziv()));
//		response.setSupplier(new POGenericType(materijal.getDobavljac().getId(), materijal.getDobavljac().getNaziv()));
		response.setMaker(materijal.getProizvodjac());
		response.setPrice(materijal.getCena());
		response.setPackageQuantity(materijal.getPakovanjeKolicina());
		response.setPackageMeasureUnit(materijal.getPakovanjeJM() == null ? null
				: new POGenericType(materijal.getPakovanjeJM().getId(), materijal.getPakovanjeJM().getNaziv()));
		response.setMaterialType(materijal.getTip());
		response.setInactive(materijal.getNeaktivan());

		return response;
	}

	public Materijal updateMaterijalFromRequest(Materijal materijal, MaterialUpdateRequest request) {

		materijal.setNaziv(request.getName());
		materijal.setKolicina(request.getQuantity());
		materijal.setDozvoljeniLimit(request.getLimit());
		materijal.setJedinicaMere(
				request.getMeasureUnit() == null ? null : new JedinicaMere(request.getMeasureUnit().getId()));
		materijal.setProizvodjac(request.getMaker().toUpperCase());
//		materijal.setDobavljac(new Dobavljac(request.getSupplier().getId()));
		materijal.setTip(request.getMaterialType());
		materijal.setPakovanjeKolicina(request.getPackageQuantity());
		materijal.setPakovanjeJM(request.getPackageMeasureUnit() == null ? null
				: new JedinicaMere(request.getPackageMeasureUnit().getId()));
		materijal.setCena(request.getPrice());
		materijal.setNeaktivan(request.getInactive());

		return materijal;
	}

	public Map<String, Object> fromEntityToPDFParameters(List<Materijal> list) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("datum", createDateString(Calendar.getInstance()));
		parameters.put("materijalLista", new JRBeanCollectionDataSource(createJasperListFromEntity(list)));
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY);

		return parameters;
	}

	private Collection<?> createJasperListFromEntity(List<Materijal> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<JasperMaterialItem> materialItems = new ArrayList<>();
		int no = 1;

		for (Materijal m : list) {
			JasperMaterialItem item = new JasperMaterialItem();

			item.setRb(no + ".");
			no++;
			item.setNaziv(m.getNaziv());
			item.setJedinicaMere(m.getJedinicaMere() != null ? m.getJedinicaMere().getNaziv() : null);
			item.setKolicina(m.getKolicina());
			item.setPakovanjeJedinicaMere(m.getPakovanjeJM() != null ? m.getPakovanjeJM().getNaziv() : null);
			item.setPakovanjeKolicina(m.getPakovanjeKolicina());
			item.setTip(m.getTip());
			item.setProizvodjac(m.getProizvodjac());

			materialItems.add(item);
		}

		return materialItems;
	}

	private String createDateString(Calendar cal) {
		return cal != null ? new SimpleDateFormat(dateFormat).format(cal.getTime()) : null;
	}

	public Map<String, Object> fromEntityToConsumptionPDFParameters(
			Set<MaterialGenerateConsumptionPDFResponse> materialList, MaterialGenerateConsumptionPDFRequest request) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("datumOD", createDateString(request.getDateFrom()));
		parameters.put("datumDO", createDateString(request.getDateTo()));
		parameters.put("materijalLista",
				new JRBeanCollectionDataSource(createMaterialConsumptionJasperListFromEntity(materialList)));
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY);

		return parameters;
	}

	private Collection<?> createMaterialConsumptionJasperListFromEntity(
			Set<MaterialGenerateConsumptionPDFResponse> materialList) {
		if (materialList == null || materialList.size() == 0)
			return new ArrayList<>();

		List<JasperMaterialConsumptionItem> materialItems = new ArrayList<>();
		int no = 1;

		for (MaterialGenerateConsumptionPDFResponse mgcr : materialList) {
			JasperMaterialConsumptionItem item = new JasperMaterialConsumptionItem();

			item.setRb(no + ".");
			no++;
			item.setNaziv(mgcr.getName());
			item.setProizvodjac(mgcr.getMaker());
			item.setJedinicaMere(mgcr.getMeasureUnit());
			item.setKolicina(mgcr.getQuantity());
			if (tabakMeasureUnit.equals(mgcr.getMeasureUnit()))
				item.setKolicinaKG(createQuantityKG(mgcr.getName().trim(), mgcr.getQuantity()));

			materialItems.add(item);
		}

		return materialItems;
	}

	private Double createQuantityKG(String name, Long quantity) {
		String[] nameSeparate = name.split(" ");
		if (quantity == null || nameSeparate.length < 4 || nameSeparate[nameSeparate.length - 1] == null
				|| !nameSeparate[nameSeparate.length - 1].toLowerCase().contains("x"))
			return null;

		try {
			Integer gram = Integer.parseInt(nameSeparate[nameSeparate.length - 3]);
			Integer format1 = Integer.parseInt(nameSeparate[nameSeparate.length - 1].toLowerCase().split("x")[0]);
			Integer format2 = Integer.parseInt(nameSeparate[nameSeparate.length - 1].toLowerCase().split("x")[1]);

			return format1 * format2 / 10000.0 * quantity * gram / 1000;
		} catch (Exception e) {
			return null;
		}
	}

}
