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

import com.src.printing.office.po.entity.GotovProizvod;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.entity.Otpremnica;
import com.src.printing.office.po.entity.StavkaOtpremnice;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.deliveryNote.getOne.response.DNGetOneResponse;
import com.src.printing.office.po.model.deliveryNote.getOneForReceipt.response.DNGetOneForReceiptResponse;
import com.src.printing.office.po.model.deliveryNote.getOneForReceipt.response.DNItemForReceipt;
import com.src.printing.office.po.model.deliveryNote.save.request.DNItem;
import com.src.printing.office.po.model.deliveryNote.save.request.DNSaveRequest;
import com.src.printing.office.po.model.deliveryNote.search.SearchDNResponse;
import com.src.printing.office.po.model.deliveryNote.update.request.DNUpdateItem;
import com.src.printing.office.po.model.deliveryNote.update.request.DNUpdateRequest;
import com.src.printing.office.po.model.jasper.JasperDeliveryNoteItem;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class OtpremnicaAssembler {

	@Value("${pdf.date.format}")
	private String dateFormat;

	@Value("${job.title.substr.length}")
	private int jobTitleLength;

	public Otpremnica fromSaveRequestToEntity(DNSaveRequest request) {

		Otpremnica otpremnica = new Otpremnica();

		otpremnica.setDatum(request.getDate());
		otpremnica.setKorisnik(new Korisnik(request.getUserID()));
		otpremnica.setKupac(new Kupac(request.getCustomer().getId()));
		otpremnica.setInterna(request.isInternal());
		otpremnica.setOpis(request.getDescription());
		otpremnica.setStavkaOtpremniceList(
				createStavkaOtpremniceListFromSaveRequest(otpremnica, request.getDeliveryNoteItems()));

		return otpremnica;
	}

	private List<StavkaOtpremnice> createStavkaOtpremniceListFromSaveRequest(Otpremnica otpremnica,
			List<DNItem> deliveryNoteItems) {
		List<StavkaOtpremnice> list = new ArrayList<>();

		for (DNItem dnItem : deliveryNoteItems) {
			StavkaOtpremnice so = new StavkaOtpremnice();

			so.setGotovProizvod(dnItem.isPaper() ? null : new GotovProizvod(dnItem.getId()));
			so.setMaterijal(dnItem.isPaper() ? new Materijal(dnItem.getId()) : null);
			so.setRedniBroj(dnItem.getNo());
			so.setNazivArtikla(dnItem.getItemName());
//			so.setJedinicaMere(new JedinicaMere(dnItem.getUnitOfMeasureId()));
			so.setKolicina(dnItem.getQuantity());
			so.setCenaPoJediniciMere(dnItem.getPricePerUnit());
			so.setUkupno(dnItem.getPrice());
			so.setOtpremnica(otpremnica);
			so.setGratis(dnItem.getGratis());
			so.setZajednickiNaziv(dnItem.getCommonName() != null ? dnItem.getCommonName().trim() : null);

			list.add(so);
		}

		return list;
	}

	public DNGetOneResponse fromEntityToGetOneResponse(Otpremnica otpremnica) {
		DNGetOneResponse response = new DNGetOneResponse();

		response.setId(otpremnica.getId());
		response.setCustomer(new POGenericType(otpremnica.getKupac().getId(), otpremnica.getKupac().getNaziv()));
		response.setDnNumber(otpremnica.getBrojOtpremnice());
		response.setDate(otpremnica.getDatum());
		response.setUser(otpremnica.getKorisnik().getIme() + " " + otpremnica.getKorisnik().getPrezime());
		response.setDescription(otpremnica.getOpis());
		response.setDeliveryNoteItems(
				createGetOneDNItemsFromStavkaOtpremnicaList(otpremnica.getStavkaOtpremniceList()));

		return response;
	}

	private List<com.src.printing.office.po.model.deliveryNote.getOne.response.DNItem> createGetOneDNItemsFromStavkaOtpremnicaList(
			List<StavkaOtpremnice> stavkaOtpremniceList) {
		List<com.src.printing.office.po.model.deliveryNote.getOne.response.DNItem> dnItemsList = new ArrayList<>();

		for (StavkaOtpremnice stavkaOtpremnice : stavkaOtpremniceList) {
			com.src.printing.office.po.model.deliveryNote.getOne.response.DNItem item = new com.src.printing.office.po.model.deliveryNote.getOne.response.DNItem();

			item.setDnItemID(stavkaOtpremnice.getId());
			item.setId(stavkaOtpremnice.getGotovProizvod() == null ? stavkaOtpremnice.getMaterijal().getId()
					: stavkaOtpremnice.getGotovProizvod().getId());
			item.setItemName(stavkaOtpremnice.getNazivArtikla());
			item.setNo(stavkaOtpremnice.getRedniBroj());
			item.setPrice(stavkaOtpremnice.getUkupno());
			item.setPricePerUnit(stavkaOtpremnice.getCenaPoJediniciMere());
			item.setQuantity(stavkaOtpremnice.getKolicina());
			item.setUnitOfMeasure(stavkaOtpremnice.getGotovProizvod() == null
					? new POGenericType(stavkaOtpremnice.getMaterijal().getJedinicaMere().getId(),
							stavkaOtpremnice.getMaterijal().getJedinicaMere().getNaziv())
					: new POGenericType(stavkaOtpremnice.getGotovProizvod().getJedinicaMere().getId(),
							stavkaOtpremnice.getGotovProizvod().getJedinicaMere().getNaziv()));
			item.setGratis(stavkaOtpremnice.getGratis());
			item.setCommonName(stavkaOtpremnice.getZajednickiNaziv());
			item.setPaper(stavkaOtpremnice.getGotovProizvod() == null);

			item.setWoIDList(
					stavkaOtpremnice.getGotovProizvod() != null
							? stavkaOtpremnice.getGotovProizvod().getRadniNalogList().stream().map(x -> x.getId())
									.collect(Collectors.toList())
							: null);

			dnItemsList.add(item);
		}

		return dnItemsList;
	}

	public List<SearchDNResponse> fromEntityListToSearchResponse(List<Otpremnica> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchDNResponse> responseList = new ArrayList<>();

		for (Otpremnica otpremnica : list) {
			SearchDNResponse response = new SearchDNResponse();

			response.setCustomer(otpremnica.getKupac().getNaziv());
			response.setDateDN(otpremnica.getDatum());
			response.setDnNumber(otpremnica.getBrojOtpremnice());
			response.setId(otpremnica.getId());
			response.setUser(otpremnica.getKorisnik().getIme() + " " + otpremnica.getKorisnik().getPrezime());
			response.setReceipted(otpremnica.getFakturisano());
			response.setInternal(otpremnica.getInterna());
			response.setFinishUser(otpremnica.getKorisnikZavrsio() != null
					? otpremnica.getKorisnikZavrsio().getIme() + " " + otpremnica.getKorisnikZavrsio().getPrezime()
					: null);

			String jobTitles = "";
			for (StavkaOtpremnice stavkaOtpremnice : otpremnica.getStavkaOtpremniceList()) {
				jobTitles += stavkaOtpremnice.getNazivArtikla().length() < jobTitleLength
						? stavkaOtpremnice.getNazivArtikla()
						: stavkaOtpremnice.getNazivArtikla().substring(0, jobTitleLength);
				jobTitles += ", ";
			}
			response.setJobTitle(jobTitles.substring(0, jobTitles.length() - 2));

			responseList.add(response);
		}

		return responseList;
	}

	public Map<String, Object> fromEntityToPDFParameters(Otpremnica otpremnica) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		Kupac k = otpremnica.getKupac();

		parameters.put("datum", createDateString(otpremnica.getDatum()));
		parameters.put("otpremnicaBroj", otpremnica.getBrojOtpremnice());
		parameters.put("kupac", k.getNaziv());
		parameters.put("kupacPripadnost", k.getPripadnost() != null ? k.getPripadnost().getNaziv() : null);
		parameters.put("adresa", k.getGrad());
		parameters.put("opis", otpremnica.getOpis());
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY);

		parameters.put("stavkeOtpremnice",
				new JRBeanCollectionDataSource(createJasperListFromEntity(otpremnica.getStavkaOtpremniceList())));

		return parameters;
	}

	private Collection<?> createJasperListFromEntity(List<StavkaOtpremnice> stavkaOtpremniceList) {

		List<JasperDeliveryNoteItem> deliveryNoteItems = new ArrayList<>();
		int brojac = 1;

		List<String> commonNames = new ArrayList<>();
		for (StavkaOtpremnice stavkaOtpremnice : stavkaOtpremniceList) {
			JasperDeliveryNoteItem item = new JasperDeliveryNoteItem();
			if (stavkaOtpremnice.getZajednickiNaziv() != null
					&& stavkaOtpremnice.getZajednickiNaziv().trim().length() > 0) {
				if (commonNames.contains(stavkaOtpremnice.getZajednickiNaziv()))
					continue;
				else {
					commonNames.add(stavkaOtpremnice.getZajednickiNaziv());
					item.setNaziv(stavkaOtpremnice.getZajednickiNaziv());
				}
			} else {
				item.setNaziv(stavkaOtpremnice.getNazivArtikla());
			}
			item.setRb(brojac + ".");
			brojac++;
			item.setKolicina(String.valueOf(stavkaOtpremnice.getKolicina()));
			item.setJedinicaMere(stavkaOtpremnice.getGotovProizvod() == null
					? stavkaOtpremnice.getMaterijal().getJedinicaMere().getNaziv()
					: stavkaOtpremnice.getGotovProizvod().getJedinicaMere().getNaziv());
			item.setCenaPoJediniciMere(stavkaOtpremnice.getCenaPoJediniciMere());
			item.setUkupno(stavkaOtpremnice.getUkupno());

			deliveryNoteItems.add(item);
		}

		// da tabela uvek bude duzine bar 7 - nece ipak
//		if (deliveryNoteItems.size() < 7)
//			for (int i = deliveryNoteItems.size(); i < 7; i++) {
//				deliveryNoteItems.add(new JaserDeliveryNoteItem());
//			}
		return deliveryNoteItems;
	}

	private String createDateString(Calendar cal) {
		return new SimpleDateFormat(dateFormat).format(cal.getTime());
	}

	public List<POGenericType> fromEntityListToGetAllUnreceiptedResponse(List<Otpremnica> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		return list.stream()
				.map(otpremnica -> new POGenericType(otpremnica.getId(),
						otpremnica.getBrojOtpremnice() + " - " + otpremnica.getKupac().getNaziv()))
				.collect(Collectors.toList());
	}

	public DNGetOneForReceiptResponse fromEntityToGetOneForReceiptResponse(Otpremnica otpremnica) {
		DNGetOneForReceiptResponse response = new DNGetOneForReceiptResponse();
		List<String> commonNames = new ArrayList<>();

		for (StavkaOtpremnice stavkaOtpremnice : otpremnica.getStavkaOtpremniceList()) {
			DNItemForReceipt item = new DNItemForReceipt();

			GotovProizvod gp = stavkaOtpremnice.getGotovProizvod();
			Materijal m = stavkaOtpremnice.getMaterijal();

			if (stavkaOtpremnice.getZajednickiNaziv() != null
					&& stavkaOtpremnice.getZajednickiNaziv().trim().length() > 0) {
				if (commonNames.contains(stavkaOtpremnice.getZajednickiNaziv()))
					continue;
				else {
					commonNames.add(stavkaOtpremnice.getZajednickiNaziv());
					item.setItemName(stavkaOtpremnice.getZajednickiNaziv());
				}
			} else {
				item.setItemName(stavkaOtpremnice.getNazivArtikla());
			}

			item.setDnID(otpremnica.getId());
			item.setDnNumber(otpremnica.getBrojOtpremnice());
			item.setMeasureUnit(gp != null ? gp.getJedinicaMere().getNaziv() : m.getJedinicaMere().getNaziv());
			item.setQuantity(stavkaOtpremnice.getKolicina());
			item.setDnPricePerUnit(stavkaOtpremnice.getCenaPoJediniciMere());

			response.getDnItems().add(item);
		}

		return response;
	}

	public List<DNUpdateItem> getOldDNItemsFromOtpremnica(List<StavkaOtpremnice> stavkaOtpremniceList) {
		List<DNUpdateItem> list = new ArrayList<>();

		for (StavkaOtpremnice stavkaOtpremnice : stavkaOtpremniceList) {
			DNUpdateItem dnui = new DNUpdateItem();

			dnui.setDnItemID(stavkaOtpremnice.getId());
			dnui.setId(stavkaOtpremnice.getGotovProizvod() != null ? stavkaOtpremnice.getGotovProizvod().getId()
					: stavkaOtpremnice.getMaterijal().getId());
			dnui.setQuantity(stavkaOtpremnice.getKolicina());
			dnui.setGratis(stavkaOtpremnice.getGratis());
			dnui.setPaper(stavkaOtpremnice.getGotovProizvod() == null);

			list.add(dnui);
		}

		return list;
	}

	public Otpremnica fromUpdateRequestToEntity(Otpremnica otpremnica, DNUpdateRequest request) {

		otpremnica.setId(request.getId());
		otpremnica.setDatum(request.getDate());
		otpremnica.setKorisnik(new Korisnik(request.getUserID()));
		otpremnica.setKupac(new Kupac(request.getCustomer().getId()));
		otpremnica.setInterna(request.isInternal());
		otpremnica.setOpis(request.getDescription());
		otpremnica.setStavkaOtpremniceList(
				createStavkaOtpremniceListFromUpdateRequest(otpremnica, request.getDeliveryNoteItems()));

		return otpremnica;
	}

	private List<StavkaOtpremnice> createStavkaOtpremniceListFromUpdateRequest(Otpremnica otpremnica,
			List<DNUpdateItem> deliveryNoteItems) {
		List<StavkaOtpremnice> list = new ArrayList<>();

		for (DNUpdateItem dnItem : deliveryNoteItems) {
			StavkaOtpremnice so = new StavkaOtpremnice();

			so.setId(dnItem.getDnItemID());
			so.setGotovProizvod(dnItem.isPaper() ? null : new GotovProizvod(dnItem.getId()));
			so.setMaterijal(dnItem.isPaper() ? new Materijal(dnItem.getId()) : null);
			so.setRedniBroj(dnItem.getNo());
			so.setNazivArtikla(dnItem.getItemName());
//			so.setJedinicaMere(new JedinicaMere(dnItem.getUnitOfMeasureId()));
			so.setKolicina(dnItem.getQuantity());
			so.setCenaPoJediniciMere(dnItem.getPricePerUnit());
			so.setUkupno(dnItem.getPrice());
			so.setOtpremnica(otpremnica);
			so.setGratis(dnItem.getGratis());
			so.setZajednickiNaziv(dnItem.getCommonName() != null ? dnItem.getCommonName().trim() : null);

			list.add(so);
		}

		return list;
	}
}
