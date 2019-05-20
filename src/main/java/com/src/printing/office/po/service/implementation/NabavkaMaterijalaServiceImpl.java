package com.src.printing.office.po.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.assembler.NabavkaMaterijalaAssembler;
import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.entity.Materijal;
import com.src.printing.office.po.entity.NabavkaMaterijala;
import com.src.printing.office.po.entity.StavkaNabavke;
import com.src.printing.office.po.model.pom.getOne.response.POMGetOneResponse;
import com.src.printing.office.po.model.pom.save.request.POMItem;
import com.src.printing.office.po.model.pom.save.request.POMSaveRequest;
import com.src.printing.office.po.model.pom.search.request.SearchPOMRequest;
import com.src.printing.office.po.model.pom.search.response.SearchPOMResponse;
import com.src.printing.office.po.repository.MaterijalRepository;
import com.src.printing.office.po.repository.NabavkaMaterijalaRepository;
import com.src.printing.office.po.service.NabavkaMaterijalaService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class NabavkaMaterijalaServiceImpl implements NabavkaMaterijalaService {

	@Autowired
	private NabavkaMaterijalaRepository nabavkaMaterijalaRepository;

	@Autowired
	private NabavkaMaterijalaAssembler nabavkaMaterijalaAssembler;

	@Autowired
	private MaterijalRepository materijalRepository;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Override
	@Transactional
	public void save(POMSaveRequest request) {
		updateAllMaterialQuantities(request.getPomItems());

		NabavkaMaterijala nm = nabavkaMaterijalaAssembler.fromPOMSaveRequestToEntity(request);

		nabavkaMaterijalaRepository.save(nm);
	}

	private void updateAllMaterialQuantities(List<POMItem> pomItems) {
		for (POMItem pomItem : pomItems) {
			Optional<Materijal> m = materijalRepository.findById(pomItem.getMaterial().getId());

			Materijal matUpdate = m.get();
			matUpdate.setKolicina((matUpdate.getKolicina() != null ? matUpdate.getKolicina().doubleValue() : 0)
					+ pomItem.getQuantity());
			matUpdate.setCena(pomItem.getPricePerUnit());

			materijalRepository.save(matUpdate);
		}
	}

	@Override
	public List<SearchPOMResponse> search(SearchPOMRequest request) {
		List<NabavkaMaterijala> list = nabavkaMaterijalaRepository.search(
				request.getSupplier() != null ? new Dobavljac(request.getSupplier().getId()) : null,
				request.getDateFrom() != null ? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom()) : null,
				request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo()) : null);

		return nabavkaMaterijalaAssembler.fromEntityListToSearchPOMResponse(list);
	}

	@Override
	@Transactional
	public void delete(long pomID) throws ApiException {
		Optional<NabavkaMaterijala> nm = nabavkaMaterijalaRepository.findById(pomID);
		if (!nm.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "nabavka materijala"));

		reduceMaterialsState(nm.get());

		nabavkaMaterijalaRepository.delete(nm.get());
	}

	private void reduceMaterialsState(NabavkaMaterijala nabavkaMaterijala) {
		for (StavkaNabavke sn : nabavkaMaterijala.getStavkaNabavkeList()) {
			Optional<Materijal> m = materijalRepository.findById(sn.getMaterijal().getId());

			m.get().setKolicina(m.get().getKolicina() - sn.getKolicina());

			materijalRepository.save(m.get());
		}
	}

	@Override
	public POMGetOneResponse getOne(Long id) throws ApiException {
		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<NabavkaMaterijala> nm = nabavkaMaterijalaRepository.findById(id);
		if (!nm.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "nabavka materijala"));

		return nabavkaMaterijalaAssembler.fromNabavkaMaterijalaEntityToGetOneResponse(nm.get());
	}

}
