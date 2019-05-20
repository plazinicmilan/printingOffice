package com.src.printing.office.po.assembler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.entity.RadniNalog;
import com.src.printing.office.po.entity.StavkaNaloga;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.jasper.JasperWorkOrderItem;
import com.src.printing.office.po.model.workOrder.getOne.response.Customer;
import com.src.printing.office.po.model.workOrder.getOne.response.Material;
import com.src.printing.office.po.model.workOrder.getOne.response.WorkOrderGetOneItem;
import com.src.printing.office.po.model.workOrder.getOne.response.WorkOrderGetOneResponse;
import com.src.printing.office.po.model.workOrder.save.request.WorkOrderItem;
import com.src.printing.office.po.model.workOrder.save.request.WorkOrderRequest;
import com.src.printing.office.po.model.workOrder.search.response.SearchWOResponse;
import com.src.printing.office.po.model.workOrder.update.WorkOrderUpdateRequest;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class RadniNalogAssembler {

	@Value("${pdf.date.format}")
	private String dateFormat;

	@Value("${panel.type.measure.unit.pdf.name}")
	private String panelTypeMeasureUnit;

	@Value("${spiral.measure.unit.pdf.name}")
	private String spiralMeasureUnit;

	@Value("${old.work.order.name}")
	private String oldWorkOrderName;

	public RadniNalog fromWorkOrderRequestToEntity(WorkOrderRequest request) {

		RadniNalog rn = new RadniNalog();

		rn.setDatum(request.getDate());
		rn.setKorisnik(new Korisnik(request.getUserID()));
		rn.setKupac(new Kupac(request.getCustomer().getId()));
		rn.setKupacFaktura(
				request.getCustomerReceipt() == null ? null : new Kupac(request.getCustomerReceipt().getId()));
//		rn.setStatusNaloga(new StatusNaloga(StatusNalogaEnum.U_IZRADI.getId()));
		rn.setNazivPosla(request.getJobTitle());
		rn.setFormat(request.getFormat());
		rn.setTiraz(request.getCirculation());
		rn.setTirazJM(new JedinicaMere(request.getCirculationUnitOfMeasure().getId()));
		rn.setRadnik(request.getWorker());
		rn.setDogovorenaCena(request.getAgreedPrice());
		rn.setPosaoDogovorio(request.getJobAgreed());
		rn.setTipPloce(request.getPanelType());
		rn.setUkupnoPloca(request.getPanelNumber());
		rn.setTipPloce2(request.getPanelType2());
		rn.setUkupnoPloca2(request.getPanelNumber2());
		rn.setSpiralaTip(request.getSpiralType() != null ? new Materijal(request.getSpiralType().getId()) : null);
		rn.setSpiralaKolicina(request.getSpiralQuantity());
		rn.setZastititiPloce(request.getProtectPanels());
		rn.setArhivirati(request.getArchive());
		rn.setBigovanje(request.getBigging());
		rn.setRicovanje(request.getRicing());
		rn.setTvrdiPovez(request.getHardCover());
		rn.setSiveno(request.getSewing());
		rn.setBrosiraniPovez(request.getBroochBind());
		rn.setLajm(request.getLajm());
		rn.setHeft(request.getStaple());
		rn.setStancovanje(request.getPunching());
		rn.setPerforacija(request.getPerforation());
		rn.setNumeracija(request.getNumbering());
		rn.setSpirala(request.getSpiral());
		rn.setPlastifikacija(request.getPlastification());
		rn.setPlastifikacijaTip(request.getPlastificationType());
		rn.setSavijanje(request.getBendingType());
		rn.setLepljenje(request.getGluing());
		rn.setUputstvo(request.getInstructions());
		rn.setStariNalog(request.getOldWO() == true);

		rn.setStavkaNalogaList(createStavkaNalogaListFromWORequest(rn, request.getWorkOrderItems()));

		return rn;
	}

	private List<StavkaNaloga> createStavkaNalogaListFromWORequest(RadniNalog rn, List<WorkOrderItem> workOrderItems) {

		if (workOrderItems == null)
			return null;

		List<StavkaNaloga> stavkaNalogList = new ArrayList<>();

		for (WorkOrderItem workOrderItem : workOrderItems) {

			StavkaNaloga sn = new StavkaNaloga();

			sn.setFormatTabaka(workOrderItem.getSheetFormat());
			sn.setMaterijal(new Materijal(workOrderItem.getMaterial().getId()));
			sn.setNapomena(workOrderItem.getNote());
			sn.setNaziv(workOrderItem.getName());
			sn.setRadniNalog(rn);
			sn.setRedniBroj(workOrderItem.getNo());
			sn.setStampa(workOrderItem.getPrint());
			sn.setTabaka(workOrderItem.getSheet());
			sn.setVisak(workOrderItem.getSurplus());
			sn.setMnozilac(workOrderItem.getMultiplier());
			sn.setIzTabaka(workOrderItem.getFromSheet());

			stavkaNalogList.add(sn);
		}

		return stavkaNalogList;
	}

	public Map<String, Object> fromEntityToPDFParameters(RadniNalog radniNalog) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		Kupac k = radniNalog.getKupac();

		parameters.put("datum", createDateString(radniNalog.getDatum()));
		parameters.put("radniNalogBroj", radniNalog.getBrojRadnogNaloga());
		parameters.put("radnik", radniNalog.getRadnik());
		parameters.put("narucilac", k.getNaziv());
		parameters.put("kontakt", k.getPripadnost() != null ? k.getPripadnost().getNaziv() : null);
		parameters.put("kreirao", radniNalog.getKorisnik().getIme() + " " + radniNalog.getKorisnik().getPrezime());
		parameters.put("format", radniNalog.getFormat());
		parameters.put("posaoDogovorio", radniNalog.getPosaoDogovorio());
		parameters.put("nazivPosla", radniNalog.getNazivPosla());
		parameters.put("tiraz", radniNalog.getTiraz() + " " + radniNalog.getTirazJM().getNaziv());

		parameters.put("stavkeNaloga",
				new JRBeanCollectionDataSource(createJasperListFromEntity(radniNalog.getStavkaNalogaList())));

		parameters.put("zastitiPloce", radniNalog.getZastititiPloce());
		parameters.put("arhivirati", radniNalog.getArhivirati());
		parameters.put("tipPloce", radniNalog.getTipPloce());
		parameters.put("ukupnoPloca",
				radniNalog.getUkupnoPloca() != null ? radniNalog.getUkupnoPloca() + " " + panelTypeMeasureUnit : null);
		parameters.put("tipPloce2", radniNalog.getTipPloce2());
		parameters.put("ukupnoPloca2",
				radniNalog.getUkupnoPloca2() != null ? radniNalog.getUkupnoPloca2() + " " + panelTypeMeasureUnit
						: null);
		parameters.put("spiralaTip",
				radniNalog.getSpiralaTip() != null
						? radniNalog.getSpiralaTip().getNaziv() + " " + radniNalog.getSpiralaTip().getProizvodjac()
						: null);
		parameters.put("spiralaKolicina",
				radniNalog.getSpiralaKolicina() != null ? radniNalog.getSpiralaKolicina() + " " + spiralMeasureUnit
						: null);

		parameters.put("bigovanje", radniNalog.getBigovanje());
		parameters.put("ricovanje", radniNalog.getRicovanje());
		parameters.put("tvrdiPovez", radniNalog.getTvrdiPovez());
		parameters.put("siveno", radniNalog.getSiveno());
		parameters.put("brosiraniPovez", radniNalog.getBrosiraniPovez());
		parameters.put("lajm", radniNalog.getLajm());
		parameters.put("heft", radniNalog.getHeft());
		parameters.put("stancovanje", radniNalog.getStancovanje());
		parameters.put("perforacija", radniNalog.getPerforacija());
		parameters.put("numeracija", radniNalog.getNumeracija());
		parameters.put("spirala", radniNalog.getSpirala());
		parameters.put("plastifikacija", radniNalog.getPlastifikacija());
		parameters.put("plastifikacijaTip", radniNalog.getPlastifikacijaTip());
		parameters.put("savijanje", radniNalog.getSavijanje());
		parameters.put("lepljenje", radniNalog.getLepljenje());
		parameters.put("uputstvo", radniNalog.getUputstvo());

		return parameters;
	}

	private List<JasperWorkOrderItem> createJasperListFromEntity(List<StavkaNaloga> stavkaNalogaList) {

		if (stavkaNalogaList == null || stavkaNalogaList.size() == 0)
			return new ArrayList<>();

		List<JasperWorkOrderItem> workOrderItems = new ArrayList<>();

		for (StavkaNaloga stavkaNaloga : stavkaNalogaList) {
			JasperWorkOrderItem item = new JasperWorkOrderItem();

			item.setRb(stavkaNaloga.getRedniBroj() + ".");
			item.setNaziv(stavkaNaloga.getNaziv());
			item.setTabaka(stavkaNaloga.getMnozilac() + " x " + stavkaNaloga.getTabaka());
			item.setVisak(String.valueOf(stavkaNaloga.getVisak()));
			item.setMaterijal(
					stavkaNaloga.getMaterijal().getNaziv() + " " + stavkaNaloga.getMaterijal().getProizvodjac());
			item.setFormatTabaka(stavkaNaloga.getFormatTabaka());
			item.setStampa(stavkaNaloga.getStampa());
			item.setNapomena(stavkaNaloga.getNapomena());

			workOrderItems.add(item);
		}

		// da tabela uvek bude duzine bar 8
//		if (workOrderItems.size() < 8)
//			for (int i = workOrderItems.size(); i < 8; i++) {
//				workOrderItems.add(new JasperWorkOrderItem());
//			}
		return workOrderItems;
	}

	private String createDateString(Calendar cal) {
		return new SimpleDateFormat(dateFormat).format(cal.getTime());
	}

	public List<SearchWOResponse> fromEntityListToSearchWOResponse(List<RadniNalog> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchWOResponse> responseList = new ArrayList<>();

		for (RadniNalog radniNalog : list) {
			SearchWOResponse swor = new SearchWOResponse();

			swor.setId(radniNalog.getId());
			swor.setWoNumber(radniNalog.getBrojRadnogNaloga());
			swor.setDateWO(radniNalog.getDatum());
			swor.setCustomer(radniNalog.getKupac().getNaziv());
			swor.setUser(radniNalog.getKorisnikPoslednjaIzmena() == null
					? radniNalog.getKorisnik().getIme() + " " + radniNalog.getKorisnik().getPrezime()
					: radniNalog.getKorisnikPoslednjaIzmena().getIme() + " "
							+ radniNalog.getKorisnikPoslednjaIzmena().getPrezime());
			swor.setCirculation(radniNalog.getTiraz());
			swor.setCirculationMeasureUnit(radniNalog.getTirazJM().getNaziv());
			swor.setJobTitle(radniNalog.getNazivPosla());
//			swor.setStatus(radniNalog.getStatusNaloga().getNaziv());
			swor.getDnList().addAll(radniNalog.getOtpremnicaList().stream()
					.map(x -> new POGenericType(x.getId(), x.getBrojOtpremnice())).collect(Collectors.toList()));
			swor.getReceiptList().addAll(radniNalog.getRacunList().stream()
					.map(x -> new POGenericType(x.getId(), x.getBrojRacuna())).collect(Collectors.toList()));
			swor.setFinishUser(radniNalog.getKorisnikZavrsio() != null
					? radniNalog.getKorisnikZavrsio().getIme() + " " + radniNalog.getKorisnikZavrsio().getPrezime()
					: null);
			if (swor.getFinishUser() == null && radniNalog.getStariNalog())
				swor.setFinishUser(oldWorkOrderName);
			swor.setOldWO(radniNalog.getStariNalog());
			swor.setInsertedFP(radniNalog.getBrojUnetihGotovihProizvoda());

			responseList.add(swor);

		}

		return responseList;
	}

	public WorkOrderGetOneResponse fromRadniNalogEntityToGetOneResponse(RadniNalog rn) {
		WorkOrderGetOneResponse response = new WorkOrderGetOneResponse();

		response.setId(rn.getId());
		response.setDate(rn.getDatum());
		response.setUserID(rn.getKorisnik().getId());
		response.setWoNumber(rn.getBrojRadnogNaloga());
		response.setCustomer(new Customer(rn.getKupac().getId(), rn.getKupac().getNaziv()));
		response.setCustomerReceipt(rn.getKupacFaktura() == null ? null
				: new Customer(rn.getKupacFaktura().getId(), rn.getKupacFaktura().getNaziv()));
//		response.setStatus(rn.getStatusNaloga().getNaziv());
		response.setJobTitle(rn.getNazivPosla());
		response.setFormat(rn.getFormat());
		response.setCirculation(rn.getTiraz());
		response.setCirculationUnitOfMeasure(new POGenericType(rn.getTirazJM().getId(), rn.getTirazJM().getNaziv()));
		response.setWorker(rn.getRadnik());
		response.setAgreedPrice(rn.getDogovorenaCena());
		response.setJobAgreed(rn.getPosaoDogovorio());
		response.setPanelType(rn.getTipPloce());
		response.setPanelNumber(rn.getUkupnoPloca());
		response.setPanelType2(rn.getTipPloce2());
		response.setPanelNumber2(rn.getUkupnoPloca2());
		response.setSpiralType(
				rn.getSpiralaTip() != null
						? new POGenericType(rn.getSpiralaTip().getId(),
								rn.getSpiralaTip().getNaziv() + " " + rn.getSpiralaTip().getProizvodjac() + " ("
										+ rn.getSpiralaTip().getKolicina() + " "
										+ rn.getSpiralaTip().getJedinicaMere().getNaziv() + ")")
						: null);
		response.setSpiralQuantity(rn.getSpiralaKolicina());
		response.setProtectPanels(rn.getZastititiPloce());
		response.setArchive(rn.getArhivirati());
		response.setBigging(rn.getBigovanje());
		response.setRicing(rn.getRicovanje());
		response.setHardCover(rn.getTvrdiPovez());
		response.setSewing(rn.getSiveno());
		response.setBroochBind(rn.getBrosiraniPovez());
		response.setLajm(rn.getLajm());
		response.setStaple(rn.getHeft());
		response.setPunching(rn.getStancovanje());
		response.setPerforation(rn.getPerforacija());
		response.setNumbering(rn.getNumeracija());
		response.setSpiral(rn.getSpirala());
		response.setPlastification(rn.getPlastifikacija());
		response.setPlastificationType(rn.getPlastifikacijaTip());
		response.setBendingType(rn.getSavijanje());
		response.setGluing(rn.getLepljenje());
		response.setInstructions(rn.getUputstvo());
		response.setEnteredFP(rn.getBrojUnetihGotovihProizvoda());
		response.setFinishUser(rn.getKorisnikZavrsio() != null
				? rn.getKorisnikZavrsio().getIme() + " " + rn.getKorisnikZavrsio().getPrezime()
				: null);
		response.setOldWO(rn.getStariNalog());

		response.setWorkOrderItems(createWOItemsFromStavkaNalogaList(rn.getStavkaNalogaList()));

		return response;
	}

	private List<WorkOrderGetOneItem> createWOItemsFromStavkaNalogaList(List<StavkaNaloga> stavkaNalogaList) {
		if (stavkaNalogaList == null)
			return null;

		List<WorkOrderGetOneItem> woItemsList = new ArrayList<>();

		for (StavkaNaloga sn : stavkaNalogaList) {

			WorkOrderGetOneItem item = new WorkOrderGetOneItem();

			item.setId(sn.getId());
			String kolicina = sn.getMaterijal().getJedinicaMere() != null
					? "(" + sn.getMaterijal().getKolicina() + " " + sn.getMaterijal().getJedinicaMere().getNaziv() + ")"
					: "";
			item.setMaterial(new Material(sn.getMaterijal().getId(),
					sn.getMaterijal().getNaziv() + " " + sn.getMaterijal().getProizvodjac() + " " + kolicina));
			item.setSheetFormat(sn.getFormatTabaka());
			item.setNote(sn.getNapomena());
			item.setName(sn.getNaziv());
			item.setNo(sn.getRedniBroj());
			item.setPrint(sn.getStampa());
			item.setSheet(sn.getTabaka());
			item.setSurplus(sn.getVisak());
			item.setMultiplier(sn.getMnozilac());
			item.setFromSheet(sn.getIzTabaka());

			woItemsList.add(item);
		}

		return woItemsList;
	}

	public RadniNalog updateRadniNalogFromRequest(RadniNalog rn, WorkOrderUpdateRequest request) {

		rn.setKupac(new Kupac(request.getCustomer().getId()));
		rn.setKupacFaktura(
				request.getCustomerReceipt() == null ? null : new Kupac(request.getCustomerReceipt().getId()));
		rn.setNazivPosla(request.getJobTitle());
		rn.setFormat(request.getFormat());
		rn.setTiraz(request.getCirculation());
		rn.setTirazJM(new JedinicaMere(request.getCirculationUnitOfMeasure().getId()));
		rn.setRadnik(request.getWorker());
		rn.setDogovorenaCena(request.getAgreedPrice());
		rn.setPosaoDogovorio(request.getJobAgreed());
		rn.setTipPloce(request.getPanelType());
		rn.setUkupnoPloca(request.getPanelNumber());
		rn.setTipPloce2(request.getPanelType2());
		rn.setUkupnoPloca2(request.getPanelNumber2());
		rn.setSpiralaTip(request.getSpiralType() != null ? new Materijal(request.getSpiralType().getId()) : null);
		rn.setSpiralaKolicina(request.getSpiralQuantity());
		rn.setZastititiPloce(request.getProtectPanels());
		rn.setArhivirati(request.getArchive());
		rn.setBigovanje(request.getBigging());
		rn.setRicovanje(request.getRicing());
		rn.setTvrdiPovez(request.getHardCover());
		rn.setSiveno(request.getSewing());
		rn.setBrosiraniPovez(request.getBroochBind());
		rn.setLajm(request.getLajm());
		rn.setHeft(request.getStaple());
		rn.setStancovanje(request.getPunching());
		rn.setPerforacija(request.getPerforation());
		rn.setNumeracija(request.getNumbering());
		rn.setSpirala(request.getSpiral());
		rn.setPlastifikacija(request.getPlastification());
		rn.setPlastifikacijaTip(request.getPlastificationType());
		rn.setSavijanje(request.getBendingType());
		rn.setLepljenje(request.getGluing());
		rn.setUputstvo(request.getInstructions());
		rn.setKorisnikPoslednjaIzmena(new Korisnik(request.getUserID()));
		rn.setDatumPoslednjeIzmene(Calendar.getInstance());

//		if (rn.getBrojUnetihGotovihProizvoda() != null
//				&& rn.getBrojUnetihGotovihProizvoda().intValue() >= rn.getTiraz().intValue())
//			rn.setStatusNaloga(new StatusNaloga(StatusNalogaEnum.NAPRAVLJEN.getId()));

		rn.setStavkaNalogaList(createStavkaNalogaListFromWOUpdateRequest(rn, request.getWorkOrderItems()));

		return rn;
	}

	private List<StavkaNaloga> createStavkaNalogaListFromWOUpdateRequest(RadniNalog rn,
			List<com.src.printing.office.po.model.workOrder.update.WorkOrderItem> list) {

		List<StavkaNaloga> stavkaNalogList = new ArrayList<>();

		for (com.src.printing.office.po.model.workOrder.update.WorkOrderItem workOrderItem : list) {

			StavkaNaloga sn = new StavkaNaloga();

			sn.setId(workOrderItem.getId());
			sn.setFormatTabaka(workOrderItem.getSheetFormat());
			sn.setMaterijal(new Materijal(workOrderItem.getMaterial().getId()));
			sn.setNapomena(workOrderItem.getNote());
			sn.setNaziv(workOrderItem.getName());
			sn.setRadniNalog(rn);
			sn.setRedniBroj(workOrderItem.getNo());
			sn.setStampa(workOrderItem.getPrint());
			sn.setTabaka(workOrderItem.getSheet());
			sn.setVisak(workOrderItem.getSurplus());
			sn.setMnozilac(workOrderItem.getMultiplier());
			sn.setIzTabaka(workOrderItem.getFromSheet());

			stavkaNalogList.add(sn);
		}

		return stavkaNalogList;
	}

}
