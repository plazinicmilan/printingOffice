package com.src.printing.office.po.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the stavka_naloga database table.
 * 
 */
@Entity
@Table(name = "stavka_naloga")
@NamedQuery(name = "StavkaNaloga.findAll", query = "SELECT s FROM StavkaNaloga s")
public class StavkaNaloga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "format_tabaka")
	private String formatTabaka;

	@Column(name = "napomena")
	private String napomena;

	@Column(name = "naziv")
	private String naziv;

	@Column(name = "redni_broj")
	private int redniBroj;

	@Column(name = "stampa")
	private String stampa;

	@Column(name = "tabaka")
	private int tabaka;

	@Column(name = "visak")
	private int visak;

	@Column(name = "mnozilac")
	private int mnozilac;

	@Column(name = "iz_tabaka")
	private int izTabaka;

	// uni-directional many-to-one association to Materijal
	@ManyToOne
	@JoinColumn(name = "id_materijal")
	private Materijal materijal;

	// bi-directional many-to-one association to RadniNalog
	@ManyToOne
	@JoinColumn(name = "id_radni_nalog")
	private RadniNalog radniNalog;

	public StavkaNaloga() {
	}

	public StavkaNaloga(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormatTabaka() {
		return this.formatTabaka;
	}

	public void setFormatTabaka(String formatTabaka) {
		this.formatTabaka = formatTabaka;
	}

	public String getNapomena() {
		return this.napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getRedniBroj() {
		return this.redniBroj;
	}

	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}

	public String getStampa() {
		return this.stampa;
	}

	public void setStampa(String stampa) {
		this.stampa = stampa;
	}

	public int getTabaka() {
		return this.tabaka;
	}

	public void setTabaka(int tabaka) {
		this.tabaka = tabaka;
	}

	public int getVisak() {
		return this.visak;
	}

	public void setVisak(int visak) {
		this.visak = visak;
	}

	public Materijal getMaterijal() {
		return this.materijal;
	}

	public void setMaterijal(Materijal materijal) {
		this.materijal = materijal;
	}

	public RadniNalog getRadniNalog() {
		return this.radniNalog;
	}

	public void setRadniNalog(RadniNalog radniNalog) {
		this.radniNalog = radniNalog;
	}

	public int getMnozilac() {
		return mnozilac;
	}

	public void setMnozilac(int mnozilac) {
		this.mnozilac = mnozilac;
	}

	public int getIzTabaka() {
		return izTabaka;
	}

	public void setIzTabaka(int izTabaka) {
		this.izTabaka = izTabaka;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StavkaNaloga other = (StavkaNaloga) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}