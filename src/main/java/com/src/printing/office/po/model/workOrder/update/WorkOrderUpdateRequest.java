package com.src.printing.office.po.model.workOrder.update;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.src.printing.office.po.model.POGenericType;

public class WorkOrderUpdateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	@NotNull
	private long userID;
	private String worker;
	private Double agreedPrice;
	@NotNull
	private POGenericType customer;
	private POGenericType customerReceipt;
	private String format;
	private String jobAgreed;
	private String jobTitle;
	private Integer circulation;
	@NotNull
	private POGenericType circulationUnitOfMeasure;
	private List<WorkOrderItem> workOrderItems;
	private Boolean protectPanels;
	private Boolean archive;
	private String panelType;
	private Integer panelNumber;
	private String panelType2;
	private Integer panelNumber2;
	private POGenericType spiralType;
	private Integer spiralQuantity;
	private Boolean bigging;
	private Boolean ricing;
	private Boolean hardCover;
	private Boolean sewing;
	private Boolean broochBind;
	private Boolean lajm;
	private Boolean staple;
	private Boolean punching;
	private Boolean perforation;
	private Boolean numbering;
	private Boolean spiral;
	private Boolean plastification;
	private Boolean plastificationType;
	private Boolean bendingType;
	private Boolean gluing;
	private String instructions;
	private Boolean oldWO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public Double getAgreedPrice() {
		return agreedPrice;
	}

	public void setAgreedPrice(Double agreedPrice) {
		this.agreedPrice = agreedPrice;
	}

	public POGenericType getCustomer() {
		return customer;
	}

	public void setCustomer(POGenericType customer) {
		this.customer = customer;
	}

	public POGenericType getCustomerReceipt() {
		return customerReceipt;
	}

	public void setCustomerReceipt(POGenericType customerReceipt) {
		this.customerReceipt = customerReceipt;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getJobAgreed() {
		return jobAgreed;
	}

	public void setJobAgreed(String jobAgreed) {
		this.jobAgreed = jobAgreed;
	}

	public String getPanelType2() {
		return panelType2;
	}

	public void setPanelType2(String panelType2) {
		this.panelType2 = panelType2;
	}

	public Integer getPanelNumber2() {
		return panelNumber2;
	}

	public void setPanelNumber2(Integer panelNumber2) {
		this.panelNumber2 = panelNumber2;
	}

	public POGenericType getSpiralType() {
		return spiralType;
	}

	public void setSpiralType(POGenericType spiralType) {
		this.spiralType = spiralType;
	}

	public Integer getSpiralQuantity() {
		return spiralQuantity;
	}

	public void setSpiralQuantity(Integer spiralQuantity) {
		this.spiralQuantity = spiralQuantity;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getCirculation() {
		return circulation;
	}

	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}

	public POGenericType getCirculationUnitOfMeasure() {
		return circulationUnitOfMeasure;
	}

	public void setCirculationUnitOfMeasure(POGenericType circulationUnitOfMeasure) {
		this.circulationUnitOfMeasure = circulationUnitOfMeasure;
	}

	public List<WorkOrderItem> getWorkOrderItems() {
		return workOrderItems;
	}

	public void setWorkOrderItems(List<WorkOrderItem> workOrderItems) {
		this.workOrderItems = workOrderItems;
	}

	public Boolean getProtectPanels() {
		return protectPanels;
	}

	public void setProtectPanels(Boolean protectPanels) {
		this.protectPanels = protectPanels;
	}

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public String getPanelType() {
		return panelType;
	}

	public void setPanelType(String panelType) {
		this.panelType = panelType;
	}

	public Boolean getPlastificationType() {
		return plastificationType;
	}

	public void setPlastificationType(Boolean plastificationType) {
		this.plastificationType = plastificationType;
	}

	public Integer getPanelNumber() {
		return panelNumber;
	}

	public void setPanelNumber(Integer panelNumber) {
		this.panelNumber = panelNumber;
	}

	public Boolean getBigging() {
		return bigging;
	}

	public void setBigging(Boolean bigging) {
		this.bigging = bigging;
	}

	public Boolean getRicing() {
		return ricing;
	}

	public void setRicing(Boolean ricing) {
		this.ricing = ricing;
	}

	public Boolean getHardCover() {
		return hardCover;
	}

	public void setHardCover(Boolean hardCover) {
		this.hardCover = hardCover;
	}

	public Boolean getSewing() {
		return sewing;
	}

	public void setSewing(Boolean sewing) {
		this.sewing = sewing;
	}

	public Boolean getBroochBind() {
		return broochBind;
	}

	public void setBroochBind(Boolean broochBind) {
		this.broochBind = broochBind;
	}

	public Boolean getLajm() {
		return lajm;
	}

	public void setLajm(Boolean lajm) {
		this.lajm = lajm;
	}

	public Boolean getStaple() {
		return staple;
	}

	public void setStaple(Boolean staple) {
		this.staple = staple;
	}

	public Boolean getPunching() {
		return punching;
	}

	public void setPunching(Boolean punching) {
		this.punching = punching;
	}

	public Boolean getPerforation() {
		return perforation;
	}

	public void setPerforation(Boolean perforation) {
		this.perforation = perforation;
	}

	public Boolean getNumbering() {
		return numbering;
	}

	public void setNumbering(Boolean numbering) {
		this.numbering = numbering;
	}

	public Boolean getSpiral() {
		return spiral;
	}

	public void setSpiral(Boolean spiral) {
		this.spiral = spiral;
	}

	public Boolean getPlastification() {
		return plastification;
	}

	public void setPlastification(Boolean plastification) {
		this.plastification = plastification;
	}

	public Boolean getBendingType() {
		return bendingType;
	}

	public void setBendingType(Boolean bendingType) {
		this.bendingType = bendingType;
	}

	public Boolean getGluing() {
		return gluing;
	}

	public void setGluing(Boolean gluing) {
		this.gluing = gluing;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Boolean getOldWO() {
		return oldWO;
	}

	public void setOldWO(Boolean oldWO) {
		this.oldWO = oldWO;
	}

}
