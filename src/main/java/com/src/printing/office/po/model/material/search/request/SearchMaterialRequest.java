package com.src.printing.office.po.model.material.search.request;

import java.io.Serializable;
import java.util.List;

public class SearchMaterialRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Material material;
	private List<String> materialTypeList;

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<String> getMaterialTypeList() {
		return materialTypeList;
	}

	public void setMaterialTypeList(List<String> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}

}
