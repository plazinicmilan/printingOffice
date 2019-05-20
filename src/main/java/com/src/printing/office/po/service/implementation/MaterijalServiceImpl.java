package com.src.printing.office.po.service.implementation;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.jasper.JasperReportGenerator;
import com.src.printing.office.po.assembler.MaterijalAssembler;
import com.src.printing.office.po.entity.JedinicaMere;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.enums.TipDokumenta;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.material.generateConsumptionPDF.MaterialGenerateConsumptionPDFRequest;
import com.src.printing.office.po.model.material.generateConsumptionPDF.MaterialGenerateConsumptionPDFResponse;
import com.src.printing.office.po.model.material.getAll.response.MaterialResponse;
import com.src.printing.office.po.model.material.getAllActiveForDN.MaterialGetAllActiveForDNResponse;
import com.src.printing.office.po.model.material.getAllCritical.response.MaterialGetAllCriticalResponse;
import com.src.printing.office.po.model.material.getAllPapers.MaterialGetAllPapersResponse;
import com.src.printing.office.po.model.material.getAllPapersForDN.MaterialGetAllPapersForDNResponse;
import com.src.printing.office.po.model.material.getOne.response.MaterialGetOneResponse;
import com.src.printing.office.po.model.material.save.request.MaterialSaveRequest;
import com.src.printing.office.po.model.material.search.request.SearchMaterialRequest;
import com.src.printing.office.po.model.material.search.response.SearchMaterialResponse;
import com.src.printing.office.po.model.material.update.MaterialUpdateRequest;
import com.src.printing.office.po.repository.MaterijalRepository;
import com.src.printing.office.po.service.MaterijalService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class MaterijalServiceImpl implements MaterijalService {

	@Autowired
	private MaterijalRepository materijalRepository;

	@Autowired
	private MaterijalAssembler materijalAssembler;

	@Autowired
	private JasperReportGenerator jasperReportGenerator;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Value("${material.type.paper}")
	private String materialTypePaper;

	@Value("${material.type.spiral}")
	private String materialTypeSpiral;

	@Override
	public List<MaterialResponse> getAll(String type) {

		List<Materijal> materijalList = null;
		if (type != null) {
			switch (type) {
			case "active":
				materijalList = materijalRepository.findAllActive();
				break;
			default:
				break;
			}
		} else {
			materijalList = materijalRepository.findAll();
		}

		return materijalAssembler.fromMaterijalEntityListToMaterialResponse(materijalList);

	}

	@Override
	public List<MaterialGetAllPapersResponse> getAllPapers() {
		List<Materijal> materijalList = materijalRepository.findActivePapers(materialTypePaper);

		return materijalAssembler.fromMaterijalEntityListToGetAllPapersResponse(materijalList);
	}

	@Override
	public List<MaterialGetAllCriticalResponse> getAllCritical() {
		List<Materijal> materijalList = materijalRepository.findCritical();

		return materijalAssembler.fromMaterijalEntityListToGetAllCriticalResponse(materijalList);
	}

	@Override
	public List<MaterialGetAllPapersForDNResponse> getAllPapersForDN() {
		List<Materijal> materijalList = materijalRepository.findActivePapers(materialTypePaper);

		return materijalAssembler.fromMaterijalEntityListToGetAllPapersForDNResponse(materijalList);
	}

	@Override
	public List<MaterialGetAllActiveForDNResponse> getAllActiveForDN() {
		List<Materijal> materijalList = materijalRepository.findAllActive();

		return materijalAssembler.fromMaterijalEntityListToGetAllActiveForDNResponse(materijalList);
	}

	@Override
	public List<POGenericType> getAllSpirals() {
		List<Materijal> materijalList = materijalRepository.findAllSpirals(materialTypeSpiral);

		return materijalAssembler.fromMaterijalEntityListToGetAllSpirals(materijalList);
	}

	@Override
	public long save(MaterialSaveRequest request) throws ApiException {

		Materijal m = materijalRepository.findByNazivAndProizvodjacAndPakovanjeKolicinaAndPakovanjeJMAndJedinicaMere(
				request.getName(), request.getMaker(), request.getPackageQuantity(),
				request.getPackageMeasureUnit() == null ? null
						: new JedinicaMere(request.getPackageMeasureUnit().getId()),
				request.getMeasureUnit() == null ? null : new JedinicaMere(request.getMeasureUnit().getId()));

		if (m != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "Materijal"));

		Materijal materijal = materijalAssembler.fromMaterialSaveRequestToEntity(request);

		materijal = materijalRepository.save(materijal);

		return materijal.getId();
	}

	@Override
	public List<SearchMaterialResponse> search(SearchMaterialRequest request) {

		List<Materijal> list = request.getMaterialTypeList() == null || request.getMaterialTypeList().size() == 0
				? materijalRepository.search(request.getMaterial() != null ? request.getMaterial().getId() : null)
				: materijalRepository.searchWithType(
						request.getMaterial() != null ? request.getMaterial().getId() : null,
						request.getMaterialTypeList());

		return materijalAssembler.fromEntityListToSearchMaterialResponse(list);
	}

	@Override
	public MaterialGetOneResponse getOne(Long id) throws ApiException {

		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<Materijal> materijal = materijalRepository.findById(id);
		if (!materijal.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "materijal"));

		return materijalAssembler.fromMaterijalEntityToGetOneResponse(materijal.get());
	}

	@Override
	public void update(MaterialUpdateRequest request) throws ApiException {

		Materijal mExists = materijalRepository
				.findByNazivAndProizvodjacAndPakovanjeKolicinaAndPakovanjeJMAndJedinicaMereAndIdNot(request.getName(),
						request.getMaker(), request.getPackageQuantity(),
						request.getPackageMeasureUnit() == null ? null
								: new JedinicaMere(request.getPackageMeasureUnit().getId()),
						request.getMeasureUnit() == null ? null : new JedinicaMere(request.getMeasureUnit().getId()),
						request.getId());

		if (mExists != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "Materijal"));

		Optional<Materijal> m = materijalRepository.findById(request.getId());
		if (!m.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "materijal"));

		Materijal materijal = materijalAssembler.updateMaterijalFromRequest(m.get(), request);

		materijalRepository.save(materijal);
	}

	@Override
	public File generatePDF(SearchMaterialRequest request) throws Exception {
		List<Materijal> list = request.getMaterialTypeList() == null || request.getMaterialTypeList().size() == 0
				? materijalRepository.searchActive(request.getMaterial() != null ? request.getMaterial().getId() : null)
				: materijalRepository.searchActiveWithType(
						request.getMaterial() != null ? request.getMaterial().getId() : null,
						request.getMaterialTypeList());

		Map<String, Object> parameters = materijalAssembler.fromEntityToPDFParameters(list);

		String pdfName = "Materijal" + "T" + Calendar.getInstance().getTimeInMillis();

		return jasperReportGenerator.generatePDF(TipDokumenta.MATERIJAL, pdfName, parameters);
	}

	@Override
	public File generateConsumptionPDF(MaterialGenerateConsumptionPDFRequest request) throws Exception {

		Calendar datumOd = request.getDateFrom() != null
				? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom())
				: null;
		Calendar datumDo = request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo())
				: null;

		Set<MaterialGenerateConsumptionPDFResponse> materialList = new TreeSet<>();

		// Materijal iz radnog naloga
		List<Object[]> listWO = materijalRepository.findConsumptionFromWO(datumOd, datumDo);
		for (Object[] obj : listWO) {
			materialList.add(new MaterialGenerateConsumptionPDFResponse((Long) obj[0], (String) obj[1], (String) obj[2],
					(String) obj[3], (Long) obj[4]));
		}

		// Materijal iz otpremnice
		List<Object[]> listDN = materijalRepository.findConsumptionFromDN(datumOd, datumDo);
		for (Object[] obj : listDN) {
			MaterialGenerateConsumptionPDFResponse mgc = new MaterialGenerateConsumptionPDFResponse((Long) obj[0],
					(String) obj[1], (String) obj[2], (String) obj[3], (Long) obj[4]);
			if (!materialList.contains(mgc))
				materialList.add(mgc);
			else {
				materialList.stream().filter(x -> x.getId() == mgc.getId())
						.forEach(x -> x.setQuantity(x.getQuantity() + mgc.getQuantity()));
			}
		}

		Map<String, Object> parameters = materijalAssembler.fromEntityToConsumptionPDFParameters(materialList, request);

		String pdfName = "MaterijalPotrosnja" + "T" + Calendar.getInstance().getTimeInMillis();

		return jasperReportGenerator.generatePDF(TipDokumenta.MATERIJAL_POTROSNJA, pdfName, parameters);
	}

}
