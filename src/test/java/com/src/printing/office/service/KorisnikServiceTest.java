package com.src.printing.office.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.src.printing.office.PrintingOfficeApplicationTests;
import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Rola;
import com.src.printing.office.po.model.user.getOne.response.UserResponse;
import com.src.printing.office.po.repository.KorisnikRepository;
import com.src.printing.office.po.service.KorisnikService;

public class KorisnikServiceTest extends PrintingOfficeApplicationTests {

	@Autowired
	private KorisnikService korisnikService;

	@MockBean
	private KorisnikRepository korisnikRepository;

	private Long id = 1l;

	@Before
	public void setUp() {
		Korisnik korisnik = getMyKorisnik();

		Mockito.when(korisnikRepository.findById(korisnik.getId())).thenReturn(Optional.of(korisnik));
		Mockito.when(korisnikRepository.findByUsername(korisnik.getKorisnickoIme())).thenReturn(korisnik);
	}

	private Korisnik getMyKorisnik() {
		Korisnik korisnik = new Korisnik(1l);

		korisnik.setIme("Milan");
		korisnik.setPrezime("Plazinic");
		korisnik.setKorisnickoIme("milan");
		Set<Rola> rolaList = new HashSet<>();
		Rola r = new Rola(1l);
		r.setNaziv("admin");
		rolaList.add(r);
		korisnik.setRolaList(rolaList);

		return korisnik;
	}

	@Test
	public void korisnikIdCannotBeNull() throws Exception {

		ApiError error = new ApiError(ApiErrorMessage.MANDATORY, "id");
		try {
			korisnikService.getOne(null);
		} catch (ApiException e) {
			assertEquals(error.getErrorCode(), e.getApiError().getErrorCode());
			assertEquals(error.getErrorDesc(), e.getApiError().getErrorDesc());
		}
	}

	@Test
	public void korisnikNotNull() throws Exception {
		assertNotNull(korisnikService.getOne(id));
	}

	@Test
	public void korisnikNameIsMilan() throws Exception {
		UserResponse k = korisnikService.getOne(id);
		assertThat("Milan").isEqualTo(k.getName());
	}

	@Test
	public void korisnikLastNameIsPlazinic() throws Exception {
		UserResponse k = korisnikService.getOne(id);
		assertThat("Plazinic").isEqualTo(k.getLastName());
	}

	@Test
	public void korisnikHasAmdinRole() throws Exception {
		UserResponse k = korisnikService.getOne(id);

		boolean hasRole = false;
		for (String role : k.getRoles()) {
			if (role.equals("admin"))
				hasRole = true;
		}

		assertTrue(hasRole);
	}

	@Test
	public void findByUsernameNotNull() throws Exception {
		Korisnik k = korisnikService.findByUsername("milan");
		assertNotNull(k);
	}

}
