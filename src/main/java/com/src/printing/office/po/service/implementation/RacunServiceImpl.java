package com.src.printing.office.po.service.implementation;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.jasper.JasperReportGenerator;
import com.src.printing.office.po.assembler.RacunAssembler;
import com.src.printing.office.po.entity.Brojac;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Otpremnica;
import com.src.printing.office.po.entity.Racun;
import com.src.printing.office.po.entity.StavkaRacuna;
import com.src.printing.office.po.enums.TipDokumenta;
import com.src.printing.office.po.model.receipt.getOne.response.ReceiptGetOneResponse;
import com.src.printing.office.po.model.receipt.save.request.ReceiptSaveRequest;
import com.src.printing.office.po.model.receipt.save.response.ReceiptSaveResponse;
import com.src.printing.office.po.model.receipt.search.SearchReceiptRequest;
import com.src.printing.office.po.model.receipt.search.SearchReceiptResponse;
import com.src.printing.office.po.model.receipt.update.ReceiptUpdateRequest;
import com.src.printing.office.po.repository.BrojacRepository;
import com.src.printing.office.po.repository.OtpremnicaRepository;
import com.src.printing.office.po.repository.RacunRepository;
import com.src.printing.office.po.repository.StavkaRacunaRepository;
import com.src.printing.office.po.service.RacunService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class RacunServiceImpl implements RacunService {

	@Autowired
	private RacunRepository racunRepository;

	@Autowired
	private RacunAssembler racunAssembler;

	@Autowired
	private StavkaRacunaRepository stavkaRacunaRepository;

	@Autowired
	private OtpremnicaRepository otpremnicaRepository;

	@Autowired
	private BrojacRepository brojacRepository;

	@Autowired
	private JasperReportGenerator jasperReportGenerator;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Value("${main.receipt.type}")
	private String mainReceiptType;

	@Override
	@Transactional
	public ReceiptSaveResponse save(ReceiptSaveRequest request) throws ApiException {

		String no = getNoFromTableBrojac(request.getReceiptTypeNumber());

		Racun racun = racunAssembler.fromSaveRequestToEntity(request);
		racun.setBrojRacuna(no);

		if (request.getReceiptType().equals(mainReceiptType)) {
			// setovanje listi radnih naloga koji se pronalaze u otpremnici
			Set<Long> dnList = request.getReceiptItems().stream().filter(x -> x.getDnID() != null).map(x -> x.getDnID())
					.collect(Collectors.toSet());
			for (Long dnID : dnList) {
				Otpremnica o = otpremnicaRepository.findById(dnID).get();
				racun.getRadniNalogList().addAll(o.getRadniNalogList());
			}
		}

		racun = racunRepository.save(racun);

		return new ReceiptSaveResponse(racun.getId(), racun.getBrojRacuna());
	}

	private String getNoFromTableBrojac(Integer receiptType) throws ApiException {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int no = 1;
		String documentType = "";

		switch (receiptType) {
		case 1:
			documentType = TipDokumenta.RACUN.getTip();
			break;
		case 2:
			documentType = TipDokumenta.PONUDA.getTip();
			break;
		case 3:
			documentType = TipDokumenta.PROFAKTURA.getTip();
			break;
		case 4:
			documentType = TipDokumenta.GOTOVINSKI.getTip();
			break;
		default:
			throw new ApiException(new ApiError(ApiErrorMessage.RECEIPT_TYPE_DONT_EXISTS, receiptType.toString()));
		}

		// Pronalazak rednog broja
		Brojac b = brojacRepository.findByTypeAndYear(year, documentType);
		if (b != null) {
			no = b.getRedniBroj() + 1;
			b.setRedniBroj(no);
			brojacRepository.save(b);
		} else
			brojacRepository.save(new Brojac(year, no, documentType));

		return no + "/" + String.valueOf(year).substring(2);
	}

	@Override
	public List<SearchReceiptResponse> search(SearchReceiptRequest request) {
		List<Racun> list = racunRepository.search(request.getReceiptNumber(),
				request.getCustomer() == null ? null : new Kupac(request.getCustomer().getId()),
				request.getDateFrom() != null ? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom()) : null,
				request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo()) : null,
				request.getType());

		return racunAssembler.fromEntityListToSearchResponse(list);
	}

	@Override
	@Transactional
	public void cancel(long receiptID) throws ApiException {
		Optional<Racun> racun = racunRepository.findById(receiptID);
		if (!racun.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "racun"));

//		updateAllDeliveryNotes(
//				racun.get().getStavkaRacunaList().stream().map(x -> x.getOtpremnica()).collect(Collectors.toSet()));
		racun.get().setRadniNalogList(null);
		racun.get().setStorniran(true);
		racunRepository.save(racun.get());
	}

//	private void updateAllDeliveryNotes(Set<Otpremnica> otpremnicaList) {
//		for (Otpremnica otpremnica : otpremnicaList) {
//			otpremnica.setFakturisano(false);
//			otpremnicaRepository.save(otpremnica);
//		}
//	}

	@Override
	public ReceiptGetOneResponse getOne(Long id) throws ApiException {
		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<Racun> racun = racunRepository.findById(id);
		if (!racun.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "racun"));

		return racunAssembler.fromEntityToGetOneResponse(racun.get());
	}

	@Override
	public File generatePDF(long receiptID) throws Exception {
		Optional<Racun> racun = racunRepository.findById(receiptID);
		if (!racun.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "racun"));

		Map<String, Object> parameters = racunAssembler.fromEntityToPDFParameters(racun.get());

		String pdfName = "Racun" + racun.get().getId() + "T" + Calendar.getInstance().getTimeInMillis();

		return jasperReportGenerator.generatePDF(TipDokumenta.RACUN, pdfName, parameters);
	}

	@Override
	public void update(ReceiptUpdateRequest request) {
		Racun racun = racunRepository.findById(request.getId()).get();

		List<Long> oldReceiptItemIDs = racun.getStavkaRacunaList().stream().map(x -> x.getId())
				.collect(Collectors.toList());
		List<Long> newReceiptItemIDs = request.getReceiptItems().stream().map(x -> x.getId())
				.collect(Collectors.toList());

		if (request.getReceiptType().equals(mainReceiptType)) {
			// setovanje listi radnih naloga koji se pronalaze u otpremnici
			Set<Long> dnList = request.getReceiptItems().stream().filter(x -> x.getDnID() != null).map(x -> x.getDnID())
					.collect(Collectors.toSet());
			racun.setRadniNalogList(new HashSet<>());
			for (Long dnID : dnList) {
				Otpremnica o = otpremnicaRepository.findById(dnID).get();
				racun.getRadniNalogList().addAll(o.getRadniNalogList());
			}
		}

		racun = racunAssembler.fromUpdateRequestToEntity(racun, request);

		racunRepository.save(racun);

		checkIfReceiptItemIsRemovedOrChanged(oldReceiptItemIDs, newReceiptItemIDs);
	}

	private void checkIfReceiptItemIsRemovedOrChanged(List<Long> oldReceiptItemIDs, List<Long> newReceiptItemIDs) {
		for (Long newID : newReceiptItemIDs) {
			if (oldReceiptItemIDs.contains(newID))
				oldReceiptItemIDs.remove(newID);
		}

		if (oldReceiptItemIDs.size() > 0)
			for (Long removeID : oldReceiptItemIDs) {
				stavkaRacunaRepository.delete(new StavkaRacuna(removeID));
			}
	}

}
