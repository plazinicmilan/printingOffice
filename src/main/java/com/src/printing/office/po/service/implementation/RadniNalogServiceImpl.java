package com.src.printing.office.po.service.implementation;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.jasper.JasperReportGenerator;
import com.src.printing.office.po.assembler.RadniNalogAssembler;
import com.src.printing.office.po.entity.Brojac;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.entity.RadniNalog;
import com.src.printing.office.po.entity.StavkaNaloga;
import com.src.printing.office.po.enums.TipDokumenta;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.workOrder.finish.WOFinishRequest;
import com.src.printing.office.po.model.workOrder.getLastNumber.WorkOrderGetLastNumberResponse;
import com.src.printing.office.po.model.workOrder.getOne.response.WorkOrderGetOneResponse;
import com.src.printing.office.po.model.workOrder.save.request.WorkOrderRequest;
import com.src.printing.office.po.model.workOrder.save.response.WorkOrderSaveResponse;
import com.src.printing.office.po.model.workOrder.search.request.SearchWORequest;
import com.src.printing.office.po.model.workOrder.search.response.SearchWOResponse;
import com.src.printing.office.po.model.workOrder.update.WorkOrderItem;
import com.src.printing.office.po.model.workOrder.update.WorkOrderUpdateRequest;
import com.src.printing.office.po.repository.BrojacRepository;
import com.src.printing.office.po.repository.KupacRepository;
import com.src.printing.office.po.repository.MaterijalRepository;
import com.src.printing.office.po.repository.RadniNalogRepository;
import com.src.printing.office.po.repository.StavkaNalogaRepository;
import com.src.printing.office.po.service.RadniNalogService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class RadniNalogServiceImpl implements RadniNalogService {

	@Autowired
	private RadniNalogRepository radniNalogRepository;

	@Autowired
	private RadniNalogAssembler radniNalogAssembler;

	@Autowired
	private StavkaNalogaRepository stavkaNalogaRepository;

	@Autowired
	private BrojacRepository brojacRepository;

	@Autowired
	private MaterijalRepository materijalRepository;

	@Autowired
	private KupacRepository kupacRepository;

	@Autowired
	private JasperReportGenerator jasperReportGenerator;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Override
	@Transactional(rollbackFor = ApiException.class)
	public WorkOrderSaveResponse save(WorkOrderRequest request) throws ApiException {

		String no = null;

		if (request.getWoNumber() != null && !request.getWoNumber().trim().equals("")) {
			RadniNalog rnProvera = radniNalogRepository.findByBrojRadnogNaloga(request.getWoNumber().trim());
			if (rnProvera != null)
				throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "radni nalog"));
			else
				no = request.getWoNumber().trim();
		}

		// generise se novi radni nalog za izradu...potrebno je dodeliti mu broj i
		// smanjiti zalihe materijala (papira)
		if (no == null) {
			no = getNoFromTableBrojac();

			updateMaterialQuantities(request);
		}

		RadniNalog rn = radniNalogAssembler.fromWorkOrderRequestToEntity(request);
		rn.setBrojRadnogNaloga(no);

		rn = radniNalogRepository.save(rn);
		return new WorkOrderSaveResponse(rn.getId(), rn.getBrojRadnogNaloga());
	}

	/**
	 * @param request
	 * @throws ApiException
	 * @author Plazinic <br>
	 *         Prilikom SAVE-a radnog naloga, potrebno je smanjiti stanje na
	 *         zalihama materijala. Izuzetak se baca ukoliko nema dovoljno kolicine
	 *         na stanju
	 */
	private void updateMaterialQuantities(WorkOrderRequest request) throws ApiException {
		// AZURIRANJE PAPIRA
		for (com.src.printing.office.po.model.workOrder.save.request.WorkOrderItem workOrderItem : request
				.getWorkOrderItems()) {
			Optional<Materijal> m = materijalRepository.findById(workOrderItem.getMaterial().getId());

			int sheetToUpdate = (int) Math
					.ceil((workOrderItem.getMultiplier() * workOrderItem.getSheet() + workOrderItem.getSurplus())
							/ (double) workOrderItem.getFromSheet());
			if (m.get().getKolicina() == null || m.get().getKolicina() < sheetToUpdate)
				throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
						m.get().getNaziv() + " " + m.get().getProizvodjac()));

			m.get().setKolicina(m.get().getKolicina() - sheetToUpdate);
			materijalRepository.save(m.get());
		}

		// AZURIRANJE PLOCA
		if (request.getPanelType() != null)
			savePanelChanges(request.getPanelType(), request.getPanelNumber());
		if (request.getPanelType2() != null)
			savePanelChanges(request.getPanelType2(), request.getPanelNumber2());

		// AZURIRANJE SPIRALE
		if (request.getSpiralType() != null) {
			Optional<Materijal> spiral = materijalRepository.findById(request.getSpiralType().getId());

			if (spiral.get().getKolicina() == null || spiral.get().getKolicina() < request.getSpiralQuantity())
				throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
						spiral.get().getNaziv() + " " + spiral.get().getProizvodjac()));

			spiral.get().setKolicina(spiral.get().getKolicina() - request.getSpiralQuantity());
			materijalRepository.save(spiral.get());
		}
	}

	private void savePanelChanges(String panelType, Integer panelNumber) throws ApiException {
		Materijal ploca = materijalRepository.findByNaziv(panelType);

		if (ploca.getKolicina() == null || ploca.getKolicina() < panelNumber)
			throw new ApiException(
					new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH, ploca.getNaziv() + " " + ploca.getProizvodjac()));

		ploca.setKolicina(ploca.getKolicina() - panelNumber);
		materijalRepository.save(ploca);
	}

	private String getNoFromTableBrojac() {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int no = 1;

		// Pronalazak rednog broja
		Brojac b = brojacRepository.findByTypeAndYear(year, TipDokumenta.RADNI_NALOG.getTip());
		if (b != null) {
			no = b.getRedniBroj() + 1;
			b.setRedniBroj(no);
			brojacRepository.save(b);
		} else
			brojacRepository.save(new Brojac(year, no, TipDokumenta.RADNI_NALOG.getTip()));

		return no + "/" + String.valueOf(year).substring(2);
	}

	@Override
	public File generateWorkOrderPDF(long workOrderID) throws Exception {

		Optional<RadniNalog> rn = radniNalogRepository.findById(workOrderID);
		if (!rn.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "radni nalog"));

		Map<String, Object> parameters = radniNalogAssembler.fromEntityToPDFParameters(rn.get());

		String pdfName = "RadniNalog" + rn.get().getId() + "T" + Calendar.getInstance().getTimeInMillis();

		return jasperReportGenerator.generatePDF(TipDokumenta.RADNI_NALOG, pdfName, parameters);

	}

	@Override
	public List<SearchWOResponse> search(SearchWORequest request) {

//		List<StatusNaloga> statusNalogList = request.getStatusList() != null && request.getStatusList().size() > 0
//				? getStatusNalogaList(request.getStatusList())
//				: null;
		Kupac kupac = request.getCustomer() != null ? kupacRepository.findById(request.getCustomer().getId()).get()
				: null;
		Calendar datumOd = request.getDateFrom() != null
				? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom())
				: null;
		Calendar datumDo = request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo())
				: null;

		// Zbog problema prilikom prosledjivanja liste, koriste se jedna od dve
		// razlicite
		// metode...sa i bez liste statusa
//		List<RadniNalog> list = statusNalogList != null
//				? radniNalogRepository.search(request.getWoNumber(), kupac, statusNalogList, datumOd, datumDo)
//				: radniNalogRepository.searchWithoutStatus(request.getWoNumber(), kupac, datumOd, datumDo);
		List<RadniNalog> list = radniNalogRepository.searchWithoutStatus(request.getWoNumber(), kupac,
				kupac != null ? kupac.getPripadnost() : null, datumOd, datumDo, request.isOldWO());
		return radniNalogAssembler.fromEntityListToSearchWOResponse(list);
	}

//	private List<StatusNaloga> getStatusNalogaList(List<StatusWO> statusList) {
//
//		return statusList.stream().map(x -> new StatusNaloga(x.getId())).collect(Collectors.toList());
//
//	}

	@Override
	public void delete(long woID) throws ApiException {

		Optional<RadniNalog> rn = radniNalogRepository.findById(woID);
		if (!rn.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "radni nalog"));

		radniNalogRepository.delete(rn.get());

	}

	@Override
	public WorkOrderGetOneResponse getOne(Long id) throws ApiException {
		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<RadniNalog> rn = radniNalogRepository.findById(id);
		if (!rn.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "radni nalog"));

		return radniNalogAssembler.fromRadniNalogEntityToGetOneResponse(rn.get());
	}

	@Override
	@Transactional(rollbackFor = ApiException.class)
	public void update(WorkOrderUpdateRequest request) throws ApiException {

		Optional<RadniNalog> rn = radniNalogRepository.findById(request.getId());
		if (!rn.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "radni nalog"));

		List<WorkOrderItem> oldWOItemsList = getOldWOItemsFromRadniNalog(rn.get().getStavkaNalogaList());

		if (!request.getOldWO()) {
			// update broja ploca ukoliko je menjano
			checkIfPanelNumberIsChanged(rn.get().getTipPloce(), rn.get().getUkupnoPloca(), request.getPanelType(),
					request.getPanelNumber());
			checkIfPanelNumberIsChanged(rn.get().getTipPloce2(), rn.get().getUkupnoPloca2(), request.getPanelType2(),
					request.getPanelNumber2());
			// update spirala
			checkIfSprialIsChanged(rn.get(), request);
		}

		// update radnog naloga
		RadniNalog rnUpdate = radniNalogAssembler.updateRadniNalogFromRequest(rn.get(), request);
		radniNalogRepository.save(rnUpdate);

		// update materijala i brisanje stavki naloga (ukoliko se to desilo)
		checkIfWOItemIsRemovedOrChanged(oldWOItemsList, request.getWorkOrderItems(), request.getOldWO());

	}

	/**
	 * @param radniNalog
	 * @param request
	 * @author Plazinic Prilikom UPDATE-a radnog naloga, moze doci do promene tipa
	 *         ili broja spirale koje treba azurirati na stanju materijala
	 * @throws ApiException
	 */
	private void checkIfSprialIsChanged(RadniNalog radniNalog, WorkOrderUpdateRequest request) throws ApiException {
		// spirale nisu ni birane
		if (radniNalog.getSpiralaTip() == null && request.getSpiralType() == null)
			return;
		if (radniNalog.getSpiralaTip() != null) {
			// spirala imala vrednost pa iskljucena
			if (request.getSpiralType() == null) {
				Materijal oldPanels = materijalRepository.findById(radniNalog.getSpiralaTip().getId()).get();
				oldPanels.setKolicina(oldPanels.getKolicina() + radniNalog.getSpiralaKolicina());
				materijalRepository.save(oldPanels);
				return;
			}
			// spirala ostala ista
			if (radniNalog.getSpiralaTip().getId() == request.getSpiralType().getId()) {
				// promenja kolicina
				if (!radniNalog.getSpiralaKolicina().equals(request.getSpiralQuantity())) {
					Materijal oldPanels = materijalRepository.findById(radniNalog.getSpiralaTip().getId()).get();

					if (oldPanels.getKolicina() == null
							|| oldPanels.getKolicina() < request.getSpiralQuantity() - radniNalog.getSpiralaKolicina())
						throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
								oldPanels.getNaziv() + " " + oldPanels.getProizvodjac()));

					oldPanels.setKolicina(
							oldPanels.getKolicina() + radniNalog.getSpiralaKolicina() - request.getSpiralQuantity());
					materijalRepository.save(oldPanels);
					return;
				} else
					return;
			}
			// promenjena spirala
			else {
				Materijal oldPanels = materijalRepository.findById(radniNalog.getSpiralaTip().getId()).get();
				oldPanels.setKolicina(oldPanels.getKolicina() + radniNalog.getSpiralaKolicina());
				materijalRepository.save(oldPanels);

				Materijal newPanels = materijalRepository.findById(request.getSpiralType().getId()).get();

				if (newPanels.getKolicina() == null || newPanels.getKolicina() < request.getSpiralQuantity())
					throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
							newPanels.getNaziv() + " " + newPanels.getProizvodjac()));

				newPanels.setKolicina(newPanels.getKolicina() - request.getSpiralQuantity());
				materijalRepository.save(newPanels);
				return;
			}
		}
		// bilo je null pa je izabrano
		if (request.getSpiralType() != null) {
			Materijal newPanels = materijalRepository.findById(request.getSpiralType().getId()).get();

			if (newPanels.getKolicina() == null || newPanels.getKolicina() < request.getSpiralQuantity())
				throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
						newPanels.getNaziv() + " " + newPanels.getProizvodjac()));

			newPanels.setKolicina(newPanels.getKolicina() - request.getSpiralQuantity());
			materijalRepository.save(newPanels);
			return;
		}
	}

	/**
	 * @param radniNalog
	 * @param request
	 * @author Plazinic Prilikom UPDATE-a radnog naloga, moze doci do promene tipa
	 *         ili broja ploca koje treba azurirati na stanju materijala
	 * @throws ApiException
	 */
	private void checkIfPanelNumberIsChanged(String tipPloce, Integer ukupnoPloca, String panelType,
			Integer panelNumber) throws ApiException {
		// ploce nisu ni birane
		if (tipPloce == null && panelType == null)
			return;
		if (tipPloce != null) {
			// ploca imala vrednost pa iskljucena
			if (panelType == null) {
				Materijal oldPanels = materijalRepository.findByNaziv(tipPloce);
				oldPanels.setKolicina(oldPanels.getKolicina() + ukupnoPloca);
				materijalRepository.save(oldPanels);
				return;
			}
			// ploca ostala ista
			if (tipPloce.equals(panelType)) {
				// promenja kolicina
				if (!ukupnoPloca.equals(panelNumber)) {
					Materijal oldPanels = materijalRepository.findByNaziv(tipPloce);

					if (oldPanels.getKolicina() == null || oldPanels.getKolicina() < panelNumber - ukupnoPloca)
						throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
								oldPanels.getNaziv() + " " + oldPanels.getProizvodjac()));

					oldPanels.setKolicina(oldPanels.getKolicina() + ukupnoPloca - panelNumber);
					materijalRepository.save(oldPanels);
					return;
				} else
					return;
			}
			// promenjena ploca
			else {
				Materijal oldPanels = materijalRepository.findByNaziv(tipPloce);
				oldPanels.setKolicina(oldPanels.getKolicina() + ukupnoPloca);
				materijalRepository.save(oldPanels);

				Materijal newPanels = materijalRepository.findByNaziv(panelType);

				if (newPanels.getKolicina() == null || newPanels.getKolicina() < panelNumber)
					throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
							newPanels.getNaziv() + " " + newPanels.getProizvodjac()));

				newPanels.setKolicina(newPanels.getKolicina() - panelNumber);
				materijalRepository.save(newPanels);
				return;
			}
		}
		// bilo je null pa je izabrano
		if (panelType != null) {
			Materijal newPanels = materijalRepository.findByNaziv(panelType);

			if (newPanels.getKolicina() == null || newPanels.getKolicina() < panelNumber)
				throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
						newPanels.getNaziv() + " " + newPanels.getProizvodjac()));

			newPanels.setKolicina(newPanels.getKolicina() - panelNumber);
			materijalRepository.save(newPanels);
			return;
		}
	}

	private List<WorkOrderItem> getOldWOItemsFromRadniNalog(List<StavkaNaloga> stavkaNalogaList) {
		List<WorkOrderItem> list = new ArrayList<>();

		for (StavkaNaloga stavkaNaloga : stavkaNalogaList) {
			WorkOrderItem woi = new WorkOrderItem();

			woi.setId(stavkaNaloga.getId());
			woi.setMultiplier(stavkaNaloga.getMnozilac());
			woi.setSheet(stavkaNaloga.getTabaka());
			woi.setSurplus(stavkaNaloga.getVisak());
			woi.setMaterial(new POGenericType(stavkaNaloga.getMaterijal().getId(),
					stavkaNaloga.getMaterijal().getNaziv() + " " + stavkaNaloga.getMaterijal().getProizvodjac()));
			woi.setFromSheet(stavkaNaloga.getIzTabaka());

			list.add(woi);
		}

		return list;
	}

	/**
	 * @param oldWOItemsList
	 * @param list
	 * @throws ApiException
	 * @author Plazinic <br>
	 *         Metoda koja proverava da li je prilikom UPDATE-a radnog naloga doslo
	 *         do promene (dodavanja, brisanja, menjanja) stavki naloga <br>
	 *         Takodje moze materijala (stanja, vrste, itd)
	 */
	private void checkIfWOItemIsRemovedOrChanged(List<WorkOrderItem> oldWOItemsList, List<WorkOrderItem> list,
			Boolean oldWO) throws ApiException {
		// prolazak kroz listu prosledjenih stavki naloga
		for (WorkOrderItem woItem : list) {
			boolean existingWOItem = false;

			// prolazak kroz stare stavke i ukoliko se id-evi poklapaju, azurira stanje
			// materijala
			for (WorkOrderItem woi : oldWOItemsList) {
				if (woItem.getId() != null && woItem.getId().equals(woi.getId())) {
					existingWOItem = true;
					if (oldWO == null || !oldWO)
						calculateExistingWOItem(woi, woItem);

					oldWOItemsList.remove(woi);
					break;
				}
			}

			// ukoliko se nisu poklopili id-evi, radi se o novoj stavci
			if (!existingWOItem) {
				if (oldWO == null || !oldWO)
					calculateNewWOItem(woItem);
			}
			existingWOItem = false;
		}

		// moze se desiti da se stavka obrise...brisanje svih iz liste, koja se
		// azurirala u prethodnoj petlji
		for (WorkOrderItem woi : oldWOItemsList) {
			if (oldWO == null || !oldWO)
				calculateRemovedWOItem(woi);

			stavkaNalogaRepository.delete(new StavkaNaloga(woi.getId()));
		}
	}

	/**
	 * @param woi
	 * @author Plazinic <br>
	 *         Prilikom brisanja stavke radnog naloga, mora se azurirati vrednost
	 *         materijala...tj. vratiti stara vrednost
	 */
	private void calculateRemovedWOItem(WorkOrderItem woi) {
		int sheetToUpdate = (int) Math
				.ceil((woi.getMultiplier() * woi.getSheet() + woi.getSurplus()) / (double) woi.getFromSheet());
		Materijal mat = materijalRepository.findById(woi.getMaterial().getId()).get();

		// dodavanje materijala
		mat.setKolicina(mat.getKolicina() + sheetToUpdate);
		materijalRepository.save(mat);
	}

	/**
	 * @param woItem
	 * @throws ApiException
	 * @author Plazinic <br>
	 *         Prilikom UPDATE-a radnog naloga, ukoliko se doda nova stavka, mora se
	 *         azurirati stanje materijala. Izuzetak se baca ukoliko nema dovoljno
	 *         kolicine na stanju
	 */
	private void calculateNewWOItem(WorkOrderItem woItem) throws ApiException {
		Materijal mat = materijalRepository.findById(woItem.getMaterial().getId()).get();
		int sheetToUpdate = (int) Math.ceil(
				(woItem.getMultiplier() * woItem.getSheet() + woItem.getSurplus()) / (double) woItem.getFromSheet());

		if (mat.getKolicina() == null || mat.getKolicina() < sheetToUpdate)
			throw new ApiException(
					new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH, mat.getNaziv() + " " + mat.getProizvodjac()));

		mat.setKolicina(mat.getKolicina() - sheetToUpdate);
		materijalRepository.save(mat);
	}

	/**
	 * @param woItemOld
	 * @param woItemNew
	 * @throws ApiException
	 * @author Plazinic <br>
	 *         Prilikom update-a materijala proverava se da li je doslo do promene
	 *         stanja u postojecoj stavci naloga, kao i da li je promenjena vrsta
	 *         materijala
	 */
	private void calculateExistingWOItem(WorkOrderItem woItemOld, WorkOrderItem woItemNew) throws ApiException {
		int oldWO = (int) Math.ceil((woItemOld.getMultiplier() * woItemOld.getSheet() + woItemOld.getSurplus())
				/ (double) woItemOld.getFromSheet());
		int newWO = (int) Math.ceil((woItemNew.getMultiplier() * woItemNew.getSheet() + woItemNew.getSurplus())
				/ (double) woItemNew.getFromSheet());

		int difference = newWO - oldWO;

		// postoji mogucnost da je za neku stavku izabran drugi materijal (stari treba
		// povecati, novi smanjiti)
		boolean choosenDifferent = woItemOld.getMaterial().getId() != woItemNew.getMaterial().getId();
		if (choosenDifferent) {
			Materijal matNew = materijalRepository.findById(woItemNew.getMaterial().getId()).get();
			Materijal matOld = materijalRepository.findById(woItemOld.getMaterial().getId()).get();
			if (matNew.getKolicina() == null || matNew.getKolicina() < newWO)
				throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
						matNew.getNaziv() + " " + matNew.getProizvodjac()));

			matNew.setKolicina(matNew.getKolicina() - newWO);
			materijalRepository.save(matNew);

			matOld.setKolicina(matOld.getKolicina() + oldWO);
			materijalRepository.save(matOld);
		} else {
			// ako je isti materijal, oduzima se samo razlika (ukoliko je ima)
			if (difference != 0) {
				Materijal mat = materijalRepository.findById(woItemOld.getMaterial().getId()).get();

				if (mat.getKolicina() == null || mat.getKolicina() < difference)
					throw new ApiException(new ApiError(ApiErrorMessage.MATERIAL_NOT_ENOUGH,
							mat.getNaziv() + " " + mat.getProizvodjac()));

				mat.setKolicina(mat.getKolicina() - difference);
				materijalRepository.save(mat);
			}
		}
	}

	@Override
	public WorkOrderGetLastNumberResponse getLastWONumber() {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		// Pronalazak rednog broja
		Brojac b = brojacRepository.findByTypeAndYear(year, TipDokumenta.RADNI_NALOG.getTip());

		return b != null
				? new WorkOrderGetLastNumberResponse(b.getRedniBroj() + "/" + String.valueOf(year).substring(2))
				: new WorkOrderGetLastNumberResponse("");

	}

	@Override
	public void finish(WOFinishRequest request) {
		RadniNalog rn = radniNalogRepository.findById(request.getWoID()).get();
		rn.setKorisnikZavrsio(new Korisnik(request.getUserID()));
		radniNalogRepository.save(rn);
	}

	@Override
	public List<SearchWOResponse> getAllActive() {
		List<RadniNalog> list = radniNalogRepository.findAllActive();

		return radniNalogAssembler.fromEntityListToSearchWOResponse(list);
	}

}
