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
 * The persistent class for the kupac database table.
 * 
 */
@Entity
@Table(name = "kupac")
@NamedQuery(name = "Kupac.findAll", query = "SELECT k FROM Kupac k")
public class Kupac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "naziv")
	private String naziv;

	@Column(name = "tip")
	private Boolean tip;

	@Column(name = "pib")
	private String pib;

	@Column(name = "pdv")
	private String pdv;

	@Column(name = "adresa")
	private String adresa;

	@Column(name = "grad")
	private String grad;

	@Column(name = "kontakt")
	private String kontakt;

	@Column(name = "neaktivan")
	private Boolean neaktivan;

	// uni-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "pripadnost")
	private Kupac pripadnost;

	public Kupac() {
	}

	public Kupac(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Boolean getTip() {
		return tip;
	}

	public void setTip(Boolean tip) {
		this.tip = tip;
	}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getPdv() {
		return pdv;
	}

	public void setPdv(String pdv) {
		this.pdv = pdv;
	}

	public String getKontakt() {
		return kontakt;
	}

	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}

	public Kupac getPripadnost() {
		return pripadnost;
	}

	public void setPripadnost(Kupac pripadnost) {
		this.pripadnost = pripadnost;
	}

	public Boolean getNeaktivan() {
		return neaktivan;
	}

	public void setNeaktivan(Boolean neaktivan) {
		this.neaktivan = neaktivan;
	}

}