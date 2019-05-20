package com.src.printing.office.po.model.jasper;

public class JasperMaterialConsumptionItem {
	private String rb;
	private String naziv;
	private String proizvodjac;
	private String jedinicaMere;
	private Long kolicina;
	private Double kolicinaKG;

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

	public String getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public String getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public Long getKolicina() {
		return kolicina;
	}

	public void setKolicina(Long kolicina) {
		this.kolicina = kolicina;
	}

	public Double getKolicinaKG() {
		return kolicinaKG;
	}

	public void setKolicinaKG(Double kolicinaKG) {
		this.kolicinaKG = kolicinaKG;
	}

}
