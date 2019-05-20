package com.src.printing.office.assembler;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.src.printing.office.PrintingOfficeApplicationTests;
import com.src.printing.office.po.assembler.KupacAssembler;
import com.src.printing.office.po.entity.Kupac;

public class KupacAssemblerTest extends PrintingOfficeApplicationTests {

	@Autowired
	private KupacAssembler kupacAssembler;

	@Test
	public void listSizeIsCorrect() {
		assertEquals(2, kupacAssembler.fromKupacEntityListToCustomerResponse(getKupacList()).size());
	}

	@Test
	public void listItemsCorrectOrder() {
		assertEquals("Idea",
				kupacAssembler.fromKupacEntityListToCustomerResponse(getKupacList()).iterator().next().getName());
	}

	private Set<Kupac> getKupacList() {
		Set<Kupac> kupacList = new HashSet<>();

		Kupac k1 = new Kupac();
		k1.setId(1l);
		k1.setNaziv("Interex");

		Kupac k2 = new Kupac();
		k2.setId(2l);
		k2.setNaziv("Idea");

		kupacList.add(k1);
		kupacList.add(k2);

		return kupacList;
	}
}
