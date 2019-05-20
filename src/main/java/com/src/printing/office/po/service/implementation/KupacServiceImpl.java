package com.src.printing.office.po.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.assembler.KupacAssembler;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.customer.getAll.response.CustomerGetAllActiveResponse;
import com.src.printing.office.po.model.customer.save.request.CustomerSaveRequest;
import com.src.printing.office.po.model.customer.search.request.SearchCustomerRequest;
import com.src.printing.office.po.model.customer.search.response.SearchCustomerResponse;
import com.src.printing.office.po.model.customer.update.request.CustomerUpdateRequest;
import com.src.printing.office.po.repository.KupacRepository;
import com.src.printing.office.po.service.KupacService;

@Service
public class KupacServiceImpl implements KupacService {

	@Autowired
	private KupacRepository kupacRepository;

	@Autowired
	private KupacAssembler kupacAssembler;

	@Override
	public Set<POGenericType> getAll() {

		Set<Kupac> kupacList = kupacRepository.findAll();

		return kupacAssembler.fromKupacEntityListToCustomerResponse(kupacList);

	}

	@Override
	public Set<CustomerGetAllActiveResponse> getAllActive() {

		List<Kupac> kupacList = kupacRepository.findByNeaktivanIsNullOrNeaktivan(false);

		return kupacAssembler.fromKupacEntityListToGetAllActiveResponse(kupacList);
	}

	@Override
	public long save(CustomerSaveRequest request) throws ApiException {

		Kupac k = kupacRepository.findByName(request.getName());

		if (k != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "Kupac"));

		Kupac kupac = kupacAssembler.fromCustomerSaveRequestToEntity(request);

		kupac = kupacRepository.save(kupac);

		return kupac.getId();
	}

	@Override
	public List<SearchCustomerResponse> search(SearchCustomerRequest request) {

		List<Kupac> list = kupacRepository
				.search(request.getCustomer() != null ? request.getCustomer().getName() : null);

		return kupacAssembler.fromEntityListToSearchCustomerResponse(list);
	}

	@Override
	public void update(CustomerUpdateRequest request) throws ApiException {

		Optional<Kupac> k = kupacRepository.findById(request.getId());
		if (!k.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "kupac"));

		Kupac kupac = kupacAssembler.updateKupacFromRequest(k.get(), request);

		kupacRepository.save(kupac);

	}

}
