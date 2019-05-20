package com.src.printing.office.po.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the radni_nalog database table.
 * 
 */
@Entity
@Table(name = "radni_nalog")
@NamedQuery(name = "RadniNalog.findAll", query = "SELECT r FROM RadniNalog r")
public class RadniNalog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "arhivirati")
	private Boolean arhivirati;

	@Column(name = "bigovanje")
	private Boolean bigovanje;

	@Column(name = "broj_radnog_naloga")
	private String brojRadnogNaloga;

	@Column(name = "broj_unetih_gotovih_proizvoda")
	private Integer brojUnetihGotovihProizvoda;

	@Column(name = "brosirani_povez")
	private Boolean brosiraniPovez;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum")
	private Calendar datum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datum_poslednje_izmene")
	private Calendar datumPoslednjeIzmene;

	@Column(name = "format")
	private String format;

	@Column(name = "heft")
	private Boolean heft;

	@Column(name = "lajm")
	private Boolean lajm;

	@Column(name = "lepljenje")
	private Boolean lepljenje;

	@Column(name = "naziv_posla")
	private String nazivPosla;

	@Column(name = "numeracija")
	private Boolean numeracija;

	@Column(name = "perforacija")
	private Boolean perforacija;

	@Column(name = "plastifikacija")
	private Boolean plastifikacija;

	@Column(name = "plastifikacija_tip")
	private Boolean plastifikacijaTip;

	@Column(name = "posao_dogovorio")
	private String posaoDogovorio;

	@Column(name = "radnik")
	private String radnik;

	@Column(name = "ricovanje")
	private Boolean ricovanje;

	@Column(name = "savijanje")
	private Boolean savijanje;

	@Column(name = "siveno")
	private Boolean siveno;

	@Column(name = "spirala")
	private Boolean spirala;

	@Column(name = "stancovanje")
	private Boolean stancovanje;

	@Column(name = "tip_ploce")
	private String tipPloce;

	@Column(name = "tiraz")
	private Integer tiraz;

	@Column(name = "tvrdi_povez")
	private Boolean tvrdiPovez;

	@Column(name = "ukupno_ploca")
	private Integer ukupnoPloca;

	@Column(name = "uputstvo")
	private String uputstvo;

	@Column(name = "zastititi_ploce")
	private Boolean zastititiPloce;

	@Column(name = "dogovorena_cena")
	private Double dogovorenaCena;

	@Column(name = "tip_ploce2")
	private String tipPloce2;

	@Column(name = "ukupno_ploca2")
	private Integer ukupnoPloca2;

	@Column(name = "stari_nalog")
	private Boolean stariNalog;

	// uni-directional many-to-one association to Materijal
	@ManyToOne
	@JoinColumn(name = "spirala_tip")
	private Materijal spiralaTip;

	@Column(name = "spirala_kolicina")
	private Integer spiralaKolicina;

	// uni-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik_poslednja_izmena")
	private Korisnik korisnikPoslednjaIzmena;

	// bi-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik")
	private Korisnik korisnik;

	// bi-directional many-to-one association to Korisnik
	@ManyToOne
	@JoinColumn(name = "id_korisnik_zavrsio")
	private Korisnik korisnikZavrsio;

	// bi-directional many-to-one association to Kupac
	@ManyToOne
	@JoinColumn(name = "id_kupac")
	private Kupac kupac;

	// bi-directional many-to-one association to Kupac
	@ManyToOne
	@JoinColumn(name = "id_kupac_faktura")
	private Kupac kupacFaktura;

	// bi-directional many-to-one association to JedinicaMere
	@ManyToOne
	@JoinColumn(name = "id_tiraz_jm")
	private JedinicaMere tirazJM;

	// uni-directional many-to-one association to StatusNaloga
	@ManyToOne
	@JoinColumn(name = "id_status")
	private StatusNaloga statusNaloga;

	// bi-directional many-to-one association to StavkaNaloga
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "radniNalog")
	private List<StavkaNaloga> stavkaNalogaList;

	// uni-directional many-to-many association to Optremnica
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "otpremnica_radni_nalog", joinColumns = @JoinColumn(name = "id_radni_nalog", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_otpremnica", referencedColumnName = "id"))
	private Set<Otpremnica> otpremnicaList;

	// uni-directional many-to-many association to Racun
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "radni_nalog_racun", joinColumns = @JoinColumn(name = "id_radni_nalog", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_racun", referencedColumnName = "id"))
	private Set<Racun> racunList;

	public RadniNalog() {
	}

	public RadniNalog(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getArhivirati() {
		return arhivirati;
	}

	public void setArhivirati(Boolean arhivirati) {
		this.arhivirati = arhivirati;
	}

	public Boolean getBigovanje() {
		return bigovanje;
	}

	public void setBigovanje(Boolean bigovanje) {
		this.bigovanje = bigovanje;
	}

	public String getBrojRadnogNaloga() {
		return brojRadnogNaloga;
	}

	public void setBrojRadnogNaloga(String brojRadnogNaloga) {
		this.brojRadnogNaloga = brojRadnogNaloga;
	}

	public Integer getBrojUnetihGotovihProizvoda() {
		return brojUnetihGotovihProizvoda;
	}

	public void setBrojUnetihGotovihProizvoda(Integer brojUnetihGotovihProizvoda) {
		this.brojUnetihGotovihProizvoda = brojUnetihGotovihProizvoda;
	}

	public Boolean getBrosiraniPovez() {
		return brosiraniPovez;
	}

	public void setBrosiraniPovez(Boolean brosiraniPovez) {
		this.brosiraniPovez = brosiraniPovez;
	}

	public Calendar getDatum() {
		return datum;
	}

	public void setDatum(Calendar datum) {
		this.datum = datum;
	}

	public Calendar getDatumPoslednjeIzmene() {
		return datumPoslednjeIzmene;
	}

	public void setDatumPoslednjeIzmene(Calendar datumPoslednjeIzmene) {
		this.datumPoslednjeIzmene = datumPoslednjeIzmene;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Boolean getHeft() {
		return heft;
	}

	public void setHeft(Boolean heft) {
		this.heft = heft;
	}

	public Boolean getLajm() {
		return lajm;
	}

	public void setLajm(Boolean lajm) {
		this.lajm = lajm;
	}

	public String getNazivPosla() {
		return nazivPosla;
	}

	public void setNazivPosla(String nazivPosla) {
		this.nazivPosla = nazivPosla;
	}

	public String getPosaoDogovorio() {
		return posaoDogovorio;
	}

	public void setPosaoDogovorio(String posaoDogovorio) {
		this.posaoDogovorio = posaoDogovorio;
	}

	public String getRadnik() {
		return radnik;
	}

	public void setRadnik(String radnik) {
		this.radnik = radnik;
	}

	public Boolean getPlastifikacijaTip() {
		return plastifikacijaTip;
	}

	public void setPlastifikacijaTip(Boolean plastifikacijaTip) {
		this.plastifikacijaTip = plastifikacijaTip;
	}

	public Boolean getRicovanje() {
		return ricovanje;
	}

	public void setRicovanje(Boolean ricovanje) {
		this.ricovanje = ricovanje;
	}

	public Boolean getSiveno() {
		return siveno;
	}

	public void setSiveno(Boolean siveno) {
		this.siveno = siveno;
	}

	public Boolean getLepljenje() {
		return lepljenje;
	}

	public void setLepljenje(Boolean lepljenje) {
		this.lepljenje = lepljenje;
	}

	public Boolean getNumeracija() {
		return numeracija;
	}

	public void setNumeracija(Boolean numeracija) {
		this.numeracija = numeracija;
	}

	public Boolean getPerforacija() {
		return perforacija;
	}

	public void setPerforacija(Boolean perforacija) {
		this.perforacija = perforacija;
	}

	public Boolean getPlastifikacija() {
		return plastifikacija;
	}

	public void setPlastifikacija(Boolean plastifikacija) {
		this.plastifikacija = plastifikacija;
	}

	public Boolean getSavijanje() {
		return savijanje;
	}

	public void setSavijanje(Boolean savijanje) {
		this.savijanje = savijanje;
	}

	public Boolean getSpirala() {
		return spirala;
	}

	public void setSpirala(Boolean spirala) {
		this.spirala = spirala;
	}

	public Boolean getStancovanje() {
		return stancovanje;
	}

	public void setStancovanje(Boolean stancovanje) {
		this.stancovanje = stancovanje;
	}

	public String getTipPloce() {
		return tipPloce;
	}

	public void setTipPloce(String tipPloce) {
		this.tipPloce = tipPloce;
	}

	public Integer getTiraz() {
		return tiraz;
	}

	public void setTiraz(Integer tiraz) {
		this.tiraz = tiraz;
	}

	public Boolean getTvrdiPovez() {
		return tvrdiPovez;
	}

	public void setTvrdiPovez(Boolean tvrdiPovez) {
		this.tvrdiPovez = tvrdiPovez;
	}

	public Integer getUkupnoPloca() {
		return ukupnoPloca;
	}

	public void setUkupnoPloca(Integer ukupnoPloca) {
		this.ukupnoPloca = ukupnoPloca;
	}

	public Korisnik getKorisnikZavrsio() {
		return korisnikZavrsio;
	}

	public void setKorisnikZavrsio(Korisnik korisnikZavrsio) {
		this.korisnikZavrsio = korisnikZavrsio;
	}

	public String getUputstvo() {
		return uputstvo;
	}

	public void setUputstvo(String uputstvo) {
		this.uputstvo = uputstvo;
	}

	public Boolean getZastititiPloce() {
		return zastititiPloce;
	}

	public void setZastititiPloce(Boolean zastititiPloce) {
		this.zastititiPloce = zastititiPloce;
	}

	public Korisnik getKorisnikPoslednjaIzmena() {
		return korisnikPoslednjaIzmena;
	}

	public void setKorisnikPoslednjaIzmena(Korisnik korisnikPoslednjaIzmena) {
		this.korisnikPoslednjaIzmena = korisnikPoslednjaIzmena;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Kupac getKupac() {
		return kupac;
	}

	public void setKupac(Kupac kupac) {
		this.kupac = kupac;
	}

	public Kupac getKupacFaktura() {
		return kupacFaktura;
	}

	public void setKupacFaktura(Kupac kupacFaktura) {
		this.kupacFaktura = kupacFaktura;
	}

	public JedinicaMere getTirazJM() {
		return tirazJM;
	}

	public void setTirazJM(JedinicaMere tirazJM) {
		this.tirazJM = tirazJM;
	}

	public StatusNaloga getStatusNaloga() {
		return statusNaloga;
	}

	public void setStatusNaloga(StatusNaloga statusNaloga) {
		this.statusNaloga = statusNaloga;
	}

	public Double getDogovorenaCena() {
		return dogovorenaCena;
	}

	public void setDogovorenaCena(Double dogovorenaCena) {
		this.dogovorenaCena = dogovorenaCena;
	}

	public List<StavkaNaloga> getStavkaNalogaList() {
		return stavkaNalogaList;
	}

	public void setStavkaNalogaList(List<StavkaNaloga> stavkaNalogaList) {
		this.stavkaNalogaList = stavkaNalogaList;
	}

	public String getTipPloce2() {
		return tipPloce2;
	}

	public void setTipPloce2(String tipPloce2) {
		this.tipPloce2 = tipPloce2;
	}

	public Integer getUkupnoPloca2() {
		return ukupnoPloca2;
	}

	public void setUkupnoPloca2(Integer ukupnoPloca2) {
		this.ukupnoPloca2 = ukupnoPloca2;
	}

	public Materijal getSpiralaTip() {
		return spiralaTip;
	}

	public void setSpiralaTip(Materijal spiralaTip) {
		this.spiralaTip = spiralaTip;
	}

	public Integer getSpiralaKolicina() {
		return spiralaKolicina;
	}

	public void setSpiralaKolicina(Integer spiralaKolicina) {
		this.spiralaKolicina = spiralaKolicina;
	}

	public Set<Otpremnica> getOtpremnicaList() {
		return otpremnicaList;
	}

	public void setOtpremnicaList(Set<Otpremnica> otpremnicaList) {
		this.otpremnicaList = otpremnicaList;
	}

	public Set<Racun> getRacunList() {
		return racunList;
	}

	public void setRacunList(Set<Racun> racunList) {
		this.racunList = racunList;
	}

	public Boolean getStariNalog() {
		return stariNalog;
	}

	public void setStariNalog(Boolean stariNalog) {
		this.stariNalog = stariNalog;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		RadniNalog other = (RadniNalog) obj;
		if (id != other.id)
			return false;
		return true;
	}

}