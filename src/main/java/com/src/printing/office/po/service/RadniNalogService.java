package com.src.printing.office.po.service;

import java.io.File;
import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.model.workOrder.finish.WOFinishRequest;
import com.src.printing.office.po.model.workOrder.getLastNumber.WorkOrderGetLastNumberResponse;
import com.src.printing.office.po.model.workOrder.getOne.response.WorkOrderGetOneResponse;
import com.src.printing.office.po.model.workOrder.save.request.WorkOrderRequest;
import com.src.printing.office.po.model.workOrder.save.response.WorkOrderSaveResponse;
import com.src.printing.office.po.model.workOrder.search.request.SearchWORequest;
import com.src.printing.office.po.model.workOrder.search.response.SearchWOResponse;
import com.src.printing.office.po.model.workOrder.update.WorkOrderUpdateRequest;

public interface RadniNalogService {

	public WorkOrderSaveResponse save(WorkOrderRequest request) throws ApiException;

	public File generateWorkOrderPDF(long workOrderID) throws Exception;

	public List<SearchWOResponse> search(SearchWORequest request);

	public void delete(long woID) throws ApiException;

	public WorkOrderGetOneResponse getOne(Long id) throws ApiException;

	public void update(WorkOrderUpdateRequest request) throws ApiException;

	public WorkOrderGetLastNumberResponse getLastWONumber();

	public void finish(WOFinishRequest request);

	public List<SearchWOResponse> getAllActive();

}
