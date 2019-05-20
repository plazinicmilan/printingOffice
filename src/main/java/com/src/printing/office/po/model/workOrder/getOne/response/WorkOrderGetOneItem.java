package com.src.printing.office.po.model.workOrder.getOne.response;

public class WorkOrderGetOneItem {

	private long id;
	private int no;
	private String name;
	private int sheet;
	private int surplus;
	private Material material;
	private String sheetFormat;
	private String print;
	private String note;
	private int multiplier;
	private int fromSheet;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSheet() {
		return sheet;
	}

	public void setSheet(int sheet) {
		this.sheet = sheet;
	}

	public int getSurplus() {
		return surplus;
	}

	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getSheetFormat() {
		return sheetFormat;
	}

	public void setSheetFormat(String sheetFormat) {
		this.sheetFormat = sheetFormat;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getFromSheet() {
		return fromSheet;
	}

	public void setFromSheet(int fromSheet) {
		this.fromSheet = fromSheet;
	}

}
