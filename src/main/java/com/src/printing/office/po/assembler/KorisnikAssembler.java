package com.src.printing.office.po.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Rola;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.user.getOne.response.UserResponse;
import com.src.printing.office.po.model.user.save.UserSaveRequest;
import com.src.printing.office.po.model.user.search.response.SearchUserResponse;
import com.src.printing.office.po.model.user.update.UserUpdateRequest;

@Component
public class KorisnikAssembler {

	public UserResponse fromKorisnikEntityToUserResponse(Korisnik k) {
		try {

			UserResponse user = new UserResponse();

			user.setId(k.getId());
			user.setName(k.getIme());
			user.setLastName(k.getPrezime());
			user.setRoles(k.getRolaList().stream().map(x -> x.getNaziv()).collect(Collectors.toList()));

			return user;

		} catch (Exception e) {
			throw e;
		}
	}

	public Korisnik fromSaveRequestToEntity(UserSaveRequest request) {

		Korisnik k = new Korisnik();

		k.setIme(request.getName());
		k.setPrezime(request.getSurname());
		k.setKorisnickoIme(request.getUsername());
		if (request.getAuthList() != null)
			k.setRolaList(request.getAuthList().stream().map(x -> new Rola(x.getId())).collect(Collectors.toSet()));

		return k;
	}

	public List<POGenericType> fromEntityListToGetAllResponse(List<Korisnik> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		return list.stream().map(x -> new POGenericType(x.getId(), x.getIme() + " " + x.getPrezime()))
				.collect(Collectors.toList());
	}

	public List<SearchUserResponse> fromEntityListToSearchUserResponse(List<Korisnik> list) {
		if (list == null || list.size() == 0)
			return new ArrayList<>();

		List<SearchUserResponse> responseList = new ArrayList<>();
		for (Korisnik korisnik : list) {
			SearchUserResponse response = new SearchUserResponse();

			response.setId(korisnik.getId());
			response.setName(korisnik.getIme());
			response.setSurname(korisnik.getPrezime());
			response.setUsername(korisnik.getKorisnickoIme());
			response.setPassword(korisnik.getKorisnickaSifra());
			response.setAuthList(korisnik.getRolaList().stream().map(x -> new POGenericType(x.getId(), x.getPunNaziv()))
					.collect(Collectors.toList()));

			responseList.add(response);
		}

		return responseList;
	}

	public Korisnik fromUpdateRequestToEntity(Korisnik korisnik, UserUpdateRequest request) {

		korisnik.setIme(request.getName());
		korisnik.setPrezime(request.getSurname());
		korisnik.setKorisnickoIme(request.getUsername());
		korisnik.setRolaList(request.getAuthList() != null
				? request.getAuthList().stream().map(x -> new Rola(x.getId())).collect(Collectors.toSet())
				: null);

		return korisnik;
	}
}
