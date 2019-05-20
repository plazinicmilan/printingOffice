package com.src.printing.office.po.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the dobavljac database table.
 * 
 */
@Entity
@Table(name = "brojac")
public class Brojac implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "godina")
	private Integer godina;

	@Column(name = "redni_broj")
	private Integer redniBroj;

	@Column(name = "tip_dokumenta")
	private String tipDokumenta;

	public Brojac() {
	}

	public Brojac(Integer godina, Integer redniBroj, String tipDokumenta) {
		this.godina = godina;
		this.redniBroj = redniBroj;
		this.tipDokumenta = tipDokumenta;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public Integer getRedniBroj() {
		return redniBroj;
	}

	public void setRedniBroj(Integer redniBroj) {
		this.redniBroj = redniBroj;
	}

	public String getTipDokumenta() {
		return tipDokumenta;
	}

	public void setTipDokumenta(String tipDokumenta) {
		this.tipDokumenta = tipDokumenta;
	}

}