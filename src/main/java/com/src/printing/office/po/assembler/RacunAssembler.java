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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Otpremnica;
import com.src.printing.office.po.entity.Racun;
import com.src.printing.office.po.entity.StavkaRacuna;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.jasper.JasperReceiptItem;
import com.src.printing.office.po.model.receipt.getOne.response.ReceiptGetOneResponse;
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveItem;
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveRequest;
import com.src.printing.office.po.model.receipt.search.SearchReceiptResponse;
import com.src.printing.office.po.model.receipt.update.ReceiptUpdateRequest;
import com.src.printing.office.util.NumberToLetterConverter;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class RacunAssembler {

	@Value("${pdf.date.format}")
	private String dateFormat;

	@Value("${job.title.substr.length}")
	private int jobTitleLength;

	@Autowired
	private NumberToLetterConverter numberToLetterConverter;

	public Racun fromSaveRequestToEntity(ReceiptSaveRequest request) {
		Racun racun = new Racun();

		racun.setDatum(request.getDate());
		racun.setDatumDospecaPlacanja(request.getDatePayment());
		racun.setTip(request.getReceiptType());
		racun.setKorisnik(new Korisnik(request.getUserID()));
		racun.setKupac(new Kupac(request.getCustomer().getId()));
		racun.setIznosBezPdv(request.getTotalWithoutPDV());
		racun.setIznosPdv(request.getTotalPDV());
		racun.setIznosUkupno(request.getTotalAmount());
		racun.setNapomena(request.getNote());
		racun.setUkljuciPDV(request.getPdvIncluded());
		racun.setStavkaRacunaList(createStavkaRacunaListFromSaveRequest(racun, request.getReceiptItems()));

		return racun;
	}

	private List<StavkaRacuna> createStavkaRacunaListFromSaveRequest(Racun racun, List<ReceiptSaveItem> receiptItems) {
		List<StavkaRacuna> list = new ArrayList<>();

		for (ReceiptSaveItem receiptItem : receiptItems) {
			StavkaRacuna sr = new StavkaRacuna();

			if (receiptItem.getId() != null)
				sr.setId(receiptItem.getId());
			sr.setRedniBroj(receiptItem.getNo());
			sr.setOpis(receiptItem.getItemName());
			sr.setKolicina(receiptItem.getQuantity());
			sr.setJedinicaMere(receiptItem.getUnitOfMeasure());
			sr.setCenaPoJediniciMere(receiptItem.getPrice());
			sr.setCenaPDV(receiptItem.getPdv());
			sr.setCenaSaPdv(receiptItem.getPriceWithPDV());
			sr.setIznos(receiptItem.getTotal());
			if (receiptItem.getDnID() != null)
				sr.setOtpremnica(new Otpremnica(receiptItem.getDnID()));
			sr.setPdvStopa(receiptItem.getPdvRate());
			sr.setRacun(racun);

			list.add(sr);
		}
		return list;
	}

	public List<SearchReceiptResponse> fromEntityListToSearchResponse(List<Racun> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchReceiptResponse> responseList = new ArrayList<>();

		for (Racun racun : list) {
			SearchReceiptResponse response = new SearchReceiptResponse();

			response.setCustomer(racun.getKupac().getNaziv());
			response.setDateReceipt(racun.getDatum());
			response.setReceiptNumber(racun.getBrojRacuna());
			response.setId(racun.getId());
			response.setUser(racun.getKorisnik().getIme() + " " + racun.getKorisnik().getPrezime());
			response.setTotal(racun.getIznosUkupno());
			String jobTitles = "";
			for (StavkaRacuna stavkaRacuna : racun.getStavkaRacunaList()) {
				jobTitles += stavkaRacuna.getOpis().length() < jobTitleLength ? stavkaRacuna.getOpis()
						: stavkaRacuna.getOpis().substring(0, jobTitleLength);
				jobTitles += ", ";
			}
			response.setJobTitle(jobTitles.substring(0, jobTitles.length() - 2));

			responseList.add(response);
		}

		return responseList;
	}

	public ReceiptGetOneResponse fromEntityToGetOneResponse(Racun racun) {
		ReceiptGetOneResponse response = new ReceiptGetOneResponse();

		response.setId(racun.getId());
		response.setCustomer(new POGenericType(racun.getKupac().getId(), racun.getKupac().getNaziv()));
		response.setDate(racun.getDatum());
		response.setDatePayment(racun.getDatumDospecaPlacanja());
		response.setReceiptType(racun.getTip());
		response.setReceiptNumber(racun.getBrojRacuna());
		response.setTotalAmount(racun.getIznosUkupno());
		response.setTotalPDV(racun.getIznosPdv());
		response.setTotalWithoutPDV(racun.getIznosBezPdv());
		response.setPdvIncluded(racun.getUkljuciPDV());
		response.setNote(racun.getNapomena());
		response.setUserID(racun.getKorisnik().getId());

		response.setReceiptItems(createGetOneResponseList(racun.getStavkaRacunaList()));

		return response;
	}

	private List<ReceiptSaveItem> createGetOneResponseList(List<StavkaRacuna> stavkaRacunaList) {
		List<ReceiptSaveItem> responseList = new ArrayList<>();

		for (StavkaRacuna stavkaRacuna : stavkaRacunaList) {
			ReceiptSaveItem item = new ReceiptSaveItem();

			item.setId(stavkaRacuna.getId());
			if (stavkaRacuna.getOtpremnica() != null) {
				item.setDeliveryNote(stavkaRacuna.getOtpremnica().getBrojOtpremnice());
				item.setDnID(stavkaRacuna.getOtpremnica().getId());
			}
			item.setItemName(stavkaRacuna.getOpis());
			item.setNo(stavkaRacuna.getRedniBroj());
			item.setPdvRate(stavkaRacuna.getPdvStopa());
			item.setPdv(stavkaRacuna.getCenaPDV());
			item.setPrice(stavkaRacuna.getCenaPoJediniciMere());
			item.setPriceWithPDV(stavkaRacuna.getCenaSaPdv());
			item.setQuantity(stavkaRacuna.getKolicina());
			item.setTotal(stavkaRacuna.getIznos());
			item.setUnitOfMeasure(stavkaRacuna.getJedinicaMere());

			responseList.add(item);
		}

		return responseList;
	}

	public Map<String, Object> fromEntityToPDFParameters(Racun racun) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		Kupac k = racun.getKupac();

		parameters.put("datum", createDateString(racun.getDatum()));
		parameters.put("datumDospeca", createDateString(racun.getDatumDospecaPlacanja()));
		parameters.put("racunBroj", racun.getBrojRacuna());
		parameters.put("kupacPripadnost", k.getPripadnost() != null ? k.getPripadnost().getNaziv() : null);
		parameters.put("kupac", k.getNaziv());
		parameters.put("pibKupca", k.getPripadnost() != null ? k.getPripadnost().getPib() : k.getPib());
		parameters.put("pdvKupca", k.getPripadnost() != null ? k.getPripadnost().getPdv() : k.getPdv());
		parameters.put("adresa", getCustomerAddress(k));
		parameters.put("napomena", racun.getNapomena());
		parameters.put("iznosBezPDV", racun.getIznosBezPdv());
		parameters.put("iznosPDV", racun.getIznosPdv());
		parameters.put("iznosUkupno", racun.getIznosUkupno());
		parameters.put("pdvStopa", racun.getStavkaRacunaList().get(0).getPdvStopa().intValue());
		parameters.put("tip", racun.getTip());
		parameters.put("ukupnoSlovima", numberToLetterConverter.numberToLetter(racun.getIznosUkupno()));
		parameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY);

		String otpremnice = "";
		String datumiOtpremnice = "";
		Set<Otpremnica> otpremnicaList = racun.getStavkaRacunaList().stream().filter(x -> x.getOtpremnica() != null)
				.map(x -> x.getOtpremnica()).collect(Collectors.toSet());
		if (otpremnicaList.size() > 0) {
			for (Otpremnica o : otpremnicaList) {
				otpremnice += o.getBrojOtpremnice() + ", ";
				datumiOtpremnice += createDateString(o.getDatum()) + " ";
			}
			parameters.put("otpremnice", otpremnice.substring(0, otpremnice.length() - 2));
			parameters.put("datumiOtpremnice", datumiOtpremnice.substring(0, datumiOtpremnice.length() - 2));
		}

		parameters.put("stavkeRacuna",
				new JRBeanCollectionDataSource(createJasperListFromEntity(racun.getStavkaRacunaList())));

		return parameters;
	}

	private String getCustomerAddress(Kupac k) {
		String adresa = k.getPripadnost() != null ? k.getPripadnost().getAdresa() : k.getAdresa();
		String grad = k.getPripadnost() != null ? k.getPripadnost().getGrad() : k.getGrad();
		return (adresa != null && adresa.length() > 0 ? adresa : "")
				+ (adresa != null && adresa.length() > 0 && grad != null && grad.length() > 0 ? ", " : "")
				+ (grad != null ? grad : "");
	}

	private Collection<?> createJasperListFromEntity(List<StavkaRacuna> stavkaRacunaList) {
		List<JasperReceiptItem> receiptItems = new ArrayList<>();

		for (StavkaRacuna stavkaRacuna : stavkaRacunaList) {
			JasperReceiptItem item = new JasperReceiptItem();

			item.setRb(stavkaRacuna.getRedniBroj() + ".");
			item.setNaziv(stavkaRacuna.getOpis());
			item.setKolicina(stavkaRacuna.getKolicina());
			item.setJedinicaMere(stavkaRacuna.getJedinicaMere());
			item.setCenaPoJediniciMere(stavkaRacuna.getCenaPoJediniciMere());
			item.setCenaPDV(stavkaRacuna.getCenaPDV());
			item.setCenaSaPDV(stavkaRacuna.getCenaSaPdv());
			item.setUkupno(stavkaRacuna.getIznos());

			receiptItems.add(item);
		}

		return receiptItems;

	}

	private String createDateString(Calendar cal) {
		return new SimpleDateFormat(dateFormat).format(cal.getTime());
	}

	public Racun fromUpdateRequestToEntity(Racun racun, ReceiptUpdateRequest request) {

		racun.setBrojRacuna(request.getReceiptNumber());
		racun.setDatum(request.getDate());
		racun.setDatumDospecaPlacanja(request.getDatePayment());
		racun.setTip(request.getReceiptType());
		racun.setKorisnik(new Korisnik(request.getUserID()));
		racun.setKupac(new Kupac(request.getCustomer().getId()));
		racun.setIznosBezPdv(request.getTotalWithoutPDV());
		racun.setIznosPdv(request.getTotalPDV());
		racun.setIznosUkupno(request.getTotalAmount());
		racun.setNapomena(request.getNote());
		racun.setUkljuciPDV(request.getPdvIncluded());
		racun.setStavkaRacunaList(createStavkaRacunaListFromSaveRequest(racun, request.getReceiptItems()));

		return racun;
	}
}
