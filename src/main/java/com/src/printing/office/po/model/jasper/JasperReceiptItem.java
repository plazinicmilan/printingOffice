package com.src.printing.office.po.model.jasper;

public class JasperReceiptItem {

	private String rb;
	private String naziv;
	private String jedinicaMere;
	private Double kolicina;
	private Double cenaPoJediniciMere;
	private Double cenaPDV;
	private Double cenaSaPDV;
	private Double ukupno;

	public String getRb() {
		return rb;
	}

	public void setRb(String rb) {
		this.rb = rb;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public Double getKolicina() {
		return kolicina;
	}

	public void setKolicina(Double kolicina) {
		this.kolicina = kolicina;
	}

	public Double getUkupno() {
		return ukupno;
	}

	public void setUkupno(Double ukupno) {
		this.ukupno = ukupno;
	}

	public Double getCenaPoJediniciMere() {
		return cenaPoJediniciMere;
	}

	public void setCenaPoJediniciMere(Double cenaPoJediniciMere) {
		this.cenaPoJediniciMere = cenaPoJediniciMere;
	}

	public Double getCenaPDV() {
		return cenaPDV;
	}

	public void setCenaPDV(Double cenaPDV) {
		this.cenaPDV = cenaPDV;
	}

	public Double getCenaSaPDV() {
		return cenaSaPDV;
	}

	public void setCenaSaPDV(Double cenaSaPDV) {
		this.cenaSaPDV = cenaSaPDV;
	}

}
