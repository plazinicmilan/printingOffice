package com.src.printing.office.po.service.implementation;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.jasper.JasperReportGenerator;
import com.src.printing.office.po.assembler.OtpremnicaAssembler;
import com.src.printing.office.po.entity.Brojac;
import com.src.printing.office.po.entity.GotovProizvod;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.entity.Otpremnica;
import com.src.printing.office.po.entity.RadniNalog;
import com.src.printing.office.po.entity.StavkaOtpremnice;
import com.src.printing.office.po.enums.TipDokumenta;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.deliveryNote.finish.DNFinishRequest;
import com.src.printing.office.po.model.deliveryNote.getOne.response.DNGetOneResponse;
import com.src.printing.office.po.model.deliveryNote.getOneForReceipt.response.DNGetOneForReceiptResponse;
import com.src.printing.office.po.model.deliveryNote.save.request.DNItem;
import com.src.printing.office.po.model.deliveryNote.save.request.DNSaveRequest;
import com.src.printing.office.po.model.deliveryNote.save.response.DNSaveResponse;
import com.src.printing.office.po.model.deliveryNote.search.SearchDNRequest;
import com.src.printing.office.po.model.deliveryNote.search.SearchDNResponse;
import com.src.printing.office.po.model.deliveryNote.update.request.DNUpdateItem;
import com.src.printing.office.po.model.deliveryNote.update.request.DNUpdateRequest;
import com.src.printing.office.po.repository.BrojacRepository;
import com.src.printing.office.po.repository.GotovProizvodRepository;
import com.src.printing.office.po.repository.MaterijalRepository;
import com.src.printing.office.po.repository.OtpremnicaRepository;
import com.src.printing.office.po.repository.StavkaOtpremniceRepository;
import com.src.printing.office.po.service.OtpremnicaService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class OtpremnicaServiceImpl implements OtpremnicaService {

	@Autowired
	private OtpremnicaRepository otpremnicaRepository;

	@Autowired
	private OtpremnicaAssembler otpremnicaAssembler;

	@Autowired
	private StavkaOtpremniceRepository stavkaOtpremniceRepository;

	@Autowired
	private BrojacRepository brojacRepository;

	@Autowired
	private GotovProizvodRepository gotovProizvodRepository;

	@Autowired
	private MaterijalRepository materijalRepository;

	@Autowired
	private JasperReportGenerator jasperReportGenerator;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Value("${internal.delivery.note.number.prefix}")
	private String internalDNNumberPrefix;

	@Override
	@Transactional(rollbackFor = ApiException.class)
	public DNSaveResponse save(DNSaveRequest request) throws ApiException {

		String no = getNoFromTableBrojac(request.isInternal());

		Otpremnica otpremnica = otpremnicaAssembler.fromSaveRequestToEntity(request);
		otpremnica.setBrojOtpremnice(no);
		Set<RadniNalog> rnList = new HashSet<>();
		for (DNItem item : request.getDeliveryNoteItems()) {
			if (item.getWoIDList() != null) {
				item.getWoIDList().stream().forEach((x) -> rnList.add(new RadniNalog(x)));
			}
		}
		otpremnica.setRadniNalogList(rnList);

		otpremnica = otpremnicaRepository.save(otpremnica);

		updateFinishedProductsAndMaterials(request.getDeliveryNoteItems());

		return new DNSaveResponse(otpremnica.getId(), otpremnica.getBrojOtpremnice());
	}

	private void updateFinishedProductsAndMaterials(List<DNItem> deliveryNoteItems) throws ApiException {
		for (DNItem dnItem : deliveryNoteItems) {
			int updateSum = dnItem.getQuantity() + (dnItem.getGratis() != null ? dnItem.getGratis() : 0);

			if (dnItem.isPaper()) {
				Optional<Materijal> materijal = materijalRepository.findById(dnItem.getId());
				if (!materijal.isPresent())
					throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "materijal"));

				if (materijal.get().getKolicina() < updateSum)
					throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
							materijal.get().getNaziv() + " " + materijal.get().getProizvodjac()));

				materijal.get().setKolicina(materijal.get().getKolicina() - updateSum);
				materijalRepository.save(materijal.get());
			} else {
				Optional<GotovProizvod> gp = gotovProizvodRepository.findById(dnItem.getId());
				if (!gp.isPresent())
					throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

				if (gp.get().getKolicina() < updateSum)
					throw new ApiException(
							new ApiError(ApiErrorMessage.FINISHED_PRODUCTS_NOT_ENOUGH, gp.get().getNaziv()));

				gp.get().setKolicina(gp.get().getKolicina() - updateSum);
				gotovProizvodRepository.save(gp.get());
			}
		}
	}

	private String getNoFromTableBrojac(boolean internal) {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int no = 1;
		String tipDokumenta = internal ? TipDokumenta.INTERNA_OTPREMINCA.getTip() : TipDokumenta.OTPREMNICA.getTip();

		// Pronalazak rednog broja
		Brojac b = brojacRepository.findByTypeAndYear(year, tipDokumenta);
		if (b != null) {
			no = b.getRedniBroj() + 1;
			b.setRedniBroj(no);
			brojacRepository.save(b);
		} else
			brojacRepository.save(new Brojac(year, no, tipDokumenta));

		return internal ? internalDNNumberPrefix + " " + no + "/" + String.valueOf(year).substring(2)
				: no + "/" + String.valueOf(year).substring(2);
	}

	@Override
	public List<SearchDNResponse> search(SearchDNRequest request) {

		List<Otpremnica> list = otpremnicaRepository.search(request.getDnNumber(),
				request.getCustomer() == null ? null : new Kupac(request.getCustomer().getId()),
				request.getDateFrom() != null ? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom()) : null,
				request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo()) : null,
				request.isInternal());

		return otpremnicaAssembler.fromEntityListToSearchResponse(list);
	}

	@Override
	@Transactional(rollbackFor = ApiException.class)
	public void cancel(long dnID) throws ApiException {
		Optional<Otpremnica> otpremnica = otpremnicaRepository.findById(dnID);
		if (!otpremnica.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "otpremnica"));

		updateAllFinishedProductsAndMaterials(otpremnica.get());

		otpremnica.get().setRadniNalogList(null);
		otpremnica.get().setStornirana(true);
		otpremnicaRepository.save(otpremnica.get());

	}

	private void updateAllFinishedProductsAndMaterials(Otpremnica otpremnica) throws ApiException {
		for (StavkaOtpremnice so : otpremnica.getStavkaOtpremniceList()) {
			if (so.getMaterijal() != null) {
				Optional<Materijal> materijal = materijalRepository.findById(so.getMaterijal().getId());
				if (!materijal.isPresent())
					throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "materijal"));

				materijal.get().setKolicina(materijal.get().getKolicina() + so.getKolicina()
						+ (so.getGratis() != null ? so.getGratis() : 0));
				materijalRepository.save(materijal.get());
			} else {
				Optional<GotovProizvod> gp = gotovProizvodRepository.findById(so.getGotovProizvod().getId());
				if (!gp.isPresent())
					throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

				gp.get().setKolicina(
						gp.get().getKolicina() + so.getKolicina() + (so.getGratis() != null ? so.getGratis() : 0));
				gotovProizvodRepository.save(gp.get());
			}
		}
	}

	@Override
	public DNGetOneResponse getOne(Long id) throws ApiException {
		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<Otpremnica> otpremnica = otpremnicaRepository.findById(id);
		if (!otpremnica.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "otpremnica"));

		return otpremnicaAssembler.fromEntityToGetOneResponse(otpremnica.get());
	}

	@Override
	public File generatePDF(long dnID) throws Exception {
		Optional<Otpremnica> o = otpremnicaRepository.findById(dnID);
		if (!o.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "otpremnica"));

		Map<String, Object> parameters = otpremnicaAssembler.fromEntityToPDFParameters(o.get());

		String pdfName = "Otpremnica" + o.get().getId() + "T" + Calendar.getInstance().getTimeInMillis();

		return jasperReportGenerator.generatePDF(TipDokumenta.OTPREMNICA, pdfName, parameters);
	}

	@Override
	public List<POGenericType> getAllUnreceipted() {
		List<Otpremnica> responseList = otpremnicaRepository.findAllUnreceipted();

		return otpremnicaAssembler.fromEntityListToGetAllUnreceiptedResponse(responseList);
	}

	@Override
	public DNGetOneForReceiptResponse getOneForReceipt(Long id) throws ApiException {
		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<Otpremnica> otpremnica = otpremnicaRepository.findById(id);
		if (!otpremnica.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "otpremnica"));

		return otpremnicaAssembler.fromEntityToGetOneForReceiptResponse(otpremnica.get());
	}

	@Override
	public void finish(DNFinishRequest request) {
		Otpremnica o = otpremnicaRepository.findById(request.getDnID()).get();

		o.setFakturisano(true);
		o.setKorisnikZavrsio(new Korisnik(request.getUserID()));
		otpremnicaRepository.save(o);
	}

	@Override
	@Transactional(rollbackFor = ApiException.class)
	public void update(DNUpdateRequest request) throws ApiException {
		Optional<Otpremnica> o = otpremnicaRepository.findById(request.getId());
		if (!o.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "otpremnica"));

		List<DNUpdateItem> oldDNItemsList = otpremnicaAssembler
				.getOldDNItemsFromOtpremnica(o.get().getStavkaOtpremniceList());

		// update otpremnice
		Otpremnica otpremnica = otpremnicaAssembler.fromUpdateRequestToEntity(o.get(), request);
		Set<RadniNalog> rnList = new HashSet<>();
		for (DNUpdateItem item : request.getDeliveryNoteItems()) {
			if (item.getWoIDList() != null) {
				item.getWoIDList().stream().forEach((x) -> rnList.add(new RadniNalog(x)));
			}
		}
		otpremnica.setRadniNalogList(rnList);
		otpremnicaRepository.save(otpremnica);

		// update gotovih proizvoda / materijala iz stavki...ukoliko ih je bilo
		checkIfDNItemIsRemovedOrChanged(oldDNItemsList, request.getDeliveryNoteItems());
	}

	private void checkIfDNItemIsRemovedOrChanged(List<DNUpdateItem> oldDNItemsList, List<DNUpdateItem> list)
			throws ApiException {
		// prolazak kroz listu prosledjenih stavki naloga
		for (DNUpdateItem dnItem : list) {
			boolean existingDNItem = false;

			// prolazak kroz stare stavke i ukoliko se id-evi poklapaju, azurira se stanje
			// gotovih proizvoda / materijala
			for (DNUpdateItem dni : oldDNItemsList) {
				if (dnItem.getDnItemID() != null && dnItem.getDnItemID().equals(dni.getDnItemID())) {
					existingDNItem = true;
					calculateExistingDNItem(dni, dnItem);

					oldDNItemsList.remove(dni);
					break;
				}
			}

			// ukoliko se nisu poklopili id-evi, radi se o novoj stavci
			if (!existingDNItem) {
				updateQuantities(dnItem, false, null);
			}
			existingDNItem = false;
		}

		// moze se desiti da se stavka obrise...brisanje svih iz liste, koja se
		// azurirala u prethodnoj petlji
		for (DNUpdateItem dni : oldDNItemsList) {
			updateQuantities(dni, true, null);

			stavkaOtpremniceRepository.delete(new StavkaOtpremnice(dni.getDnItemID()));
		}
	}

	private void calculateExistingDNItem(DNUpdateItem dnItemOld, DNUpdateItem dnItemNew) throws ApiException {
		int difference = dnItemNew.getQuantity() + (dnItemNew.getGratis() == null ? 0 : dnItemNew.getGratis())
				- dnItemOld.getQuantity() - (dnItemOld.getGratis() == null ? 0 : dnItemOld.getGratis());

		// postoji mogucnost da je za neku stavku izabran drugi materijal (stari treba
		// povecati, novi smanjiti)
		boolean choosenDifferent = dnItemOld.getId() != dnItemNew.getId() || dnItemOld.isPaper() != dnItemNew.isPaper();
		if (choosenDifferent) {
			updateQuantities(dnItemOld, true, null);
			updateQuantities(dnItemNew, false, null);
		} else {
			// ako je isti materijal, oduzima se samo razlika (ukoliko je ima)
			if (difference != 0) {
				updateQuantities(dnItemOld, false, difference);
			}
		}
	}

	private void updateQuantities(DNUpdateItem dnItem, boolean increaseQuantity, Integer sumToUpdate)
			throws ApiException {
		int updateSum = sumToUpdate != null ? sumToUpdate
				: dnItem.getQuantity() + (dnItem.getGratis() != null ? dnItem.getGratis() : 0);
		if (dnItem.isPaper()) {
			Materijal materijal = materijalRepository.findById(dnItem.getId()).get();

			if (!increaseQuantity && materijal.getKolicina() < updateSum)
				throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
						materijal.getNaziv() + " " + materijal.getProizvodjac()));

			materijal.setKolicina(
					increaseQuantity ? materijal.getKolicina() + updateSum : materijal.getKolicina() - updateSum);
			materijalRepository.save(materijal);
		} else {
			GotovProizvod gp = gotovProizvodRepository.findById(dnItem.getId()).get();

			if (!increaseQuantity && gp.getKolicina() < updateSum)
				throw new ApiException(new ApiError(ApiErrorMessage.FINISHED_PRODUCTS_NOT_ENOUGH, gp.getNaziv()));

			gp.setKolicina(increaseQuantity ? gp.getKolicina() + updateSum : gp.getKolicina() - updateSum);
			gotovProizvodRepository.save(gp);
		}
	}

}
