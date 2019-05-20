package com.src.printing.office.po.service.implementation;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.jasper.JasperReportGenerator;
import com.src.printing.office.po.assembler.GotovProizvodAssembler;
import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.entity.GotovProizvod;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.RadniNalog;
import com.src.printing.office.po.entity.StatusNaloga;
import com.src.printing.office.po.enums.StatusNalogaEnum;
import com.src.printing.office.po.enums.TipDokumenta;
import com.src.printing.office.po.model.finishedProduct.add.AddFPRequest;
import com.src.printing.office.po.model.finishedProduct.generatePDF.FPGeneratePDFRequest;
import com.src.printing.office.po.model.finishedProduct.getAllActive.FPGetAllActiveResponse;
import com.src.printing.office.po.model.finishedProduct.getOne.response.FPGetOneResponse;
import com.src.printing.office.po.model.finishedProduct.getSelected.response.FPGetSelectedResponse;
import com.src.printing.office.po.model.finishedProduct.save.FPSaveRequest;
import com.src.printing.office.po.model.finishedProduct.search.SearchFPRequest;
import com.src.printing.office.po.model.finishedProduct.search.SearchFPResponse;
import com.src.printing.office.po.model.finishedProduct.transfer.TransferFPRequest;
import com.src.printing.office.po.model.finishedProduct.transfer.TransferFPResponse;
import com.src.printing.office.po.model.finishedProduct.update.FPUpdateRequest;
import com.src.printing.office.po.model.finishedProduct.writeOff.FPWriteOffRequest;
import com.src.printing.office.po.repository.GotovProizvodRepository;
import com.src.printing.office.po.repository.RadniNalogRepository;
import com.src.printing.office.po.service.GotovProizvodService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class GotovProizvodServiceImpl implements GotovProizvodService {

	@Autowired
	private RadniNalogRepository radniNalogRepository;

	@Autowired
	private GotovProizvodAssembler gotovProizvodAssembler;

	@Autowired
	private GotovProizvodRepository gotovProizvodRepository;

	@Autowired
	private JasperReportGenerator jasperReportGenerator;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Override
	@Transactional
	public void addFP(AddFPRequest request) throws ApiException {
		Optional<RadniNalog> rn = radniNalogRepository.findById(request.getWoID());
		if (!rn.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "radni nalog"));

		GotovProizvod gp = gotovProizvodRepository.findByKupacAndNazivAndKolicinaGreaterThan(rn.get().getKupac(),
				request.getFpName(), 0);

		if (gp == null)
			gp = gotovProizvodAssembler.fromAddFPRequestToEntity(rn.get(), request);
		else {
			gp.setKolicina(gp.getKolicina() + request.getQuantity());
			if (gp.getRadniNalogList() != null)
				gp.getRadniNalogList().add(rn.get());
			else {
				Set<RadniNalog> list = new HashSet<>();
				list.add(rn.get());
				gp.setRadniNalogList(list);
			}
		}

		gotovProizvodRepository.save(gp);

		int enteredFPForWO = (rn.get().getBrojUnetihGotovihProizvoda() == null ? 0
				: rn.get().getBrojUnetihGotovihProizvoda()) + request.getQuantity();
		rn.get().setBrojUnetihGotovihProizvoda(enteredFPForWO);
		radniNalogRepository.save(rn.get());

	}

	@Override
	public List<SearchFPResponse> searchFP(SearchFPRequest request) {
		List<GotovProizvod> list = gotovProizvodRepository
				.search(request.getCustomer() != null ? new Kupac(request.getCustomer().getId()) : null);

		return gotovProizvodAssembler.fromEntityListToSearchFPResponse(list);
	}

	@Override
	@Transactional
	@Deprecated
	public synchronized TransferFPResponse transferFP(TransferFPRequest request) throws ApiException {
		Optional<RadniNalog> rn = radniNalogRepository.findById(request.getWoID());
		if (!rn.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "radni nalog"));

		Optional<GotovProizvod> gp = gotovProizvodRepository.findById(request.getFpID());
		if (!gp.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

		// Update Radnog naloga
		int enteredFPForWO = (rn.get().getBrojUnetihGotovihProizvoda() == null ? 0
				: rn.get().getBrojUnetihGotovihProizvoda()) + gp.get().getKolicina();
		rn.get().setBrojUnetihGotovihProizvoda(enteredFPForWO);
		if (enteredFPForWO >= rn.get().getTiraz())
			rn.get().setStatusNaloga(new StatusNaloga(StatusNalogaEnum.NAPRAVLJEN.getId()));
		radniNalogRepository.save(rn.get());

		// Update Gotovog proizvoda...ako je za prosledjeni nalog vec uneto gotovih
		// proizvoda
//		GotovProizvod gpExisting = gotovProizvodRepository.findByIdNotAndRadniNalog(gp.get().getId(), rn.get());
//		if (gpExisting == null) {
//			GotovProizvod gpNew = new GotovProizvod(rn.get().getKupac(), rn.get().getNazivPosla(),
//					gp.get().getKolicina(), new JedinicaMere(rn.get().getTirazJM().getId()));
//			gotovProizvodRepository.save(gpNew);
//		} else {
//			gpExisting.setKolicina(gpExisting.getKolicina() + gp.get().getKolicina());
//			gotovProizvodRepository.save(gpExisting);
//		}
		gp.get().setKolicina(0);
		gotovProizvodRepository.save(gp.get());

		return new TransferFPResponse(rn.get().getStatusNaloga().getId() == 1 ? "U izradi" : "Napravljen");
	}

	@Override
	public FPGetOneResponse getOne(Long id) throws ApiException {
		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<GotovProizvod> gp = gotovProizvodRepository.findById(id);
		if (!gp.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

		return gotovProizvodAssembler.fromGotovProizvodEntityToGetOneResponse(gp.get());
	}

	@Override
	public long save(FPSaveRequest request) {

		GotovProizvod gp = gotovProizvodRepository.findByKupacAndNazivAndKolicinaGreaterThan(
				new Kupac(request.getCustomer().getId()), request.getName(), 0);

		if (gp == null)
			gp = gotovProizvodAssembler.fromSaveRequestToEntity(request);
		else {
			gp.setKolicina(gp.getKolicina() + request.getQuantity());
			gp.setDobavljac(new Dobavljac(request.getSupplier().getId()));
			gp.setCenaPoJedinici(request.getPricePerUnit());
		}

		gp = gotovProizvodRepository.save(gp);

		return gp.getId();
	}

	@Override
	public void writeOff(FPWriteOffRequest request) throws ApiException {

		Optional<GotovProizvod> gp = gotovProizvodRepository.findById(request.getFpID());
		if (!gp.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

		gp.get().setOtpisano(gp.get().getOtpisano() == null ? request.getQuantity()
				: gp.get().getOtpisano() + request.getQuantity());
		gp.get().setKolicina(gp.get().getKolicina() - request.getQuantity());
		gp.get().setRazlogOtpisa(request.getReason());
		gp.get().setDatumOtpisa(Calendar.getInstance());
		gp.get().setKorisnikOtpisao(new Korisnik(request.getUserID()));

		gotovProizvodRepository.save(gp.get());
	}

	@Override
	public List<FPGetAllActiveResponse> getAllActive() {

		List<GotovProizvod> gotovProizvodList = gotovProizvodRepository.findAllActive();

		return gotovProizvodAssembler.fromGotovProizvodEntityListToGetAllActiveResponse(gotovProizvodList);
	}

	@Override
	public FPGetSelectedResponse getSelected(List<Long> fpIDList) throws ApiException {
		if (fpIDList == null || fpIDList.size() == 0)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "fpIDList"));

		List<GotovProizvod> gpList = gotovProizvodRepository.findAllById(fpIDList);
		if (gpList == null || gpList.size() == 0)
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

		return gotovProizvodAssembler.fromEntityListToGetSelectedResponse(gpList);
	}

	@Override
	public void update(FPUpdateRequest request) throws ApiException {
		Optional<GotovProizvod> gp = gotovProizvodRepository.findById(request.getId());
		if (!gp.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "gotov proizvod"));

		int quantity = gp.get().getKolicina();
		GotovProizvod gpSame = gotovProizvodRepository.findByIdNotAndKupacAndNazivAndKolicinaGreaterThan(
				gp.get().getId(), gp.get().getKupac(), request.getName(), 0);

		if (gpSame != null) {
			gpSame.setKolicina(gpSame.getKolicina() + quantity);
			gpSame.getRadniNalogList().addAll(gp.get().getRadniNalogList());
			gotovProizvodRepository.save(gpSame);

			gp.get().setKolicina(0);
			gotovProizvodRepository.save(gp.get());
		} else {
			gp.get().setNaziv(request.getName());
			gotovProizvodRepository.save(gp.get());
		}
	}

	@Override
	public File generatePDF(FPGeneratePDFRequest request) throws Exception {
		Calendar datumOd = request.getDateFrom() != null
				? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom())
				: null;
		Calendar datumDo = request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo())
				: null;

		List<GotovProizvod> list = gotovProizvodRepository.searchWrittenOff(datumOd, datumDo);

		Map<String, Object> parameters = gotovProizvodAssembler.fromEntityToPDFParameters(list, request);

		String pdfName = "GotoviProizvodi" + "T" + Calendar.getInstance().getTimeInMillis();

		return jasperReportGenerator.generatePDF(TipDokumenta.GOTOV_PROIZVOD, pdfName, parameters);
	}

}
