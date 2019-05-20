package com.src.printing.office.po.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.assembler.DobavljacAssembler;
import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.model.supplier.getAll.response.SupplierGetAllResponse;
import com.src.printing.office.po.model.supplier.save.request.SupplierSaveRequest;
import com.src.printing.office.po.model.supplier.search.request.SearchSupplierRequest;
import com.src.printing.office.po.model.supplier.search.response.SearchSupplierResponse;
import com.src.printing.office.po.model.supplier.update.request.SupplierUpdateRequest;
import com.src.printing.office.po.repository.DobavljacRepository;
import com.src.printing.office.po.service.DobavljacService;

@Service
public class DobavljacServiceImpl implements DobavljacService {

	@Autowired
	private DobavljacRepository dobavljacRepository;

	@Autowired
	private DobavljacAssembler dobavljacAssembler;

	@Override
	public Set<SupplierGetAllResponse> getAll() {

		Set<Dobavljac> dobavljacList = dobavljacRepository.findAll();

		return dobavljacAssembler.fromDobavljacEntityListToSupplierGetAllResponse(dobavljacList);
	}

	@Override
	public long save(SupplierSaveRequest request) throws ApiException {
		Dobavljac d = dobavljacRepository.findByName(request.getName());

		if (d != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "Dobavljac"));

		Dobavljac dobavljac = dobavljacAssembler.fromSupplierSaveRequestToEntity(request);

		dobavljac = dobavljacRepository.save(dobavljac);

		return dobavljac.getId();
	}

	@Override
	public List<SearchSupplierResponse> search(SearchSupplierRequest request) {
		List<Dobavljac> list = dobavljacRepository
				.search(request.getSupplier() != null ? request.getSupplier().getName() : null);

		return dobavljacAssembler.fromEntityListToSearchSupplerResponse(list);
	}

	@Override
	public void update(SupplierUpdateRequest request) throws ApiException {
		Optional<Dobavljac> d = dobavljacRepository.findById(request.getId());
		if (!d.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "dobavljac"));

		Dobavljac dobavljac = dobavljacAssembler.updateDobavljacFromRequest(d.get(), request);

		dobavljacRepository.save(dobavljac);
	}

}
