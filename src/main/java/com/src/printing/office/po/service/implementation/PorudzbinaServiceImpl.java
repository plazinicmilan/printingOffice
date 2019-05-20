package com.src.printing.office.po.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.assembler.PorudzbinaAssembler;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Kupac;
import com.src.printing.office.po.entity.Porudzbina;
import com.src.printing.office.po.model.order.accept.OrderAcceptRequest;
import com.src.printing.office.po.model.order.save.OrderSaveRequest;
import com.src.printing.office.po.model.order.search.OrderSearchRequest;
import com.src.printing.office.po.model.order.search.OrderSearchResponse;
import com.src.printing.office.po.model.order.update.OrderUpdateRequest;
import com.src.printing.office.po.repository.PorudzbinaRepository;
import com.src.printing.office.po.service.PorudzbinaService;
import com.src.printing.office.util.POCalendarUtil;

@Service
public class PorudzbinaServiceImpl implements PorudzbinaService {

	@Autowired
	private PorudzbinaRepository porudzbinaRepository;

	@Autowired
	private PorudzbinaAssembler porudzbinaAssembler;

	@Autowired
	private POCalendarUtil calendarUtil;

	@Override
	public void save(OrderSaveRequest request) {
		porudzbinaRepository.save(porudzbinaAssembler.fromOrderSaveRequestToEntity(request));
	}

	@Override
	public List<OrderSearchResponse> search(OrderSearchRequest request) {
		List<Porudzbina> list = porudzbinaRepository.search(
				request.getCustomer() != null ? new Kupac(request.getCustomer().getId()) : null,
				request.getDateFrom() != null ? calendarUtil.getCalendarBeginningOfTheDay(request.getDateFrom()) : null,
				request.getDateTo() != null ? calendarUtil.getCalendarEndOfTheDay(request.getDateTo()) : null);

		return porudzbinaAssembler.fromEntityListToSearchResponse(list);
	}

	@Override
	public void delete(long orderID) throws ApiException {
		Optional<Porudzbina> porudzbina = porudzbinaRepository.findById(orderID);
		if (!porudzbina.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "porudzbina"));

		porudzbinaRepository.delete(porudzbina.get());
	}

	@Override
	public void accept(OrderAcceptRequest request) {
		Optional<Porudzbina> p = porudzbinaRepository.findById(request.getOrderID());

		p.get().setKorisnikIzvrsilac(new Korisnik(request.getAcceptedUserID()));
		porudzbinaRepository.save(p.get());
	}

	@Override
	public void update(OrderUpdateRequest request) {
		Optional<Porudzbina> p = porudzbinaRepository.findById(request.getId());

		porudzbinaRepository.save(porudzbinaAssembler.fromUpdateRequestToEntity(p.get(), request));
	}

	@Override
	public List<OrderSearchResponse> getAllUnaccepted() {
		List<Porudzbina> list = porudzbinaRepository.findAllUnaccepted();

		return porudzbinaAssembler.fromEntityListToSearchResponse(list);
	}

}
