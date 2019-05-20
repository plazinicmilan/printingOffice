package com.src.printing.office.po.enums;

public enum TipDokumenta {

	RADNI_NALOG("RadniNalog"), OTPREMNICA("Otpremnica"), RACUN("Racun"), INTERNA_OTPREMINCA("InternaOtpremnica"),
	PONUDA("Ponuda"), PROFAKTURA("Profaktura"), GOTOVINSKI("Gotovinski"), MATERIJAL("Materijal"),
	MATERIJAL_POTROSNJA("MaterijalPotrosnja"), GOTOV_PROIZVOD("GotovProizvod");

	private String tip;

	private TipDokumenta(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}
}
