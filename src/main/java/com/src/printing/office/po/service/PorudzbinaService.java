package com.src.printing.office.po.service;

import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.order.accept.OrderAcceptRequest;
import com.src.printing.office.po.model.order.save.OrderSaveRequest;
import com.src.printing.office.po.model.order.search.OrderSearchRequest;
import com.src.printing.office.po.model.order.search.OrderSearchResponse;
import com.src.printing.office.po.model.order.update.OrderUpdateRequest;

public interface PorudzbinaService {

	public void save(OrderSaveRequest request);

	public List<OrderSearchResponse> search(OrderSearchRequest request);

	public void delete(long orderID) throws ApiException;

	public void accept(OrderAcceptRequest request);

	public void update(OrderUpdateRequest request);

	public List<OrderSearchResponse> getAllUnaccepted();
}
