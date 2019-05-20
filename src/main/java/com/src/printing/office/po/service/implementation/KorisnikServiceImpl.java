package com.src.printing.office.po.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.src.printing.office.enums.ApiErrorMessage;
import com.src.printing.office.exception.ApiError;
import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.assembler.KorisnikAssembler;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.entity.Rola;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.user.checkUnique.response.UserCheckUniqueResponse;
import com.src.printing.office.po.model.user.getOne.response.UserResponse;
import com.src.printing.office.po.model.user.save.UserSaveRequest;
import com.src.printing.office.po.model.user.search.request.SearchUserRequest;
import com.src.printing.office.po.model.user.search.response.SearchUserResponse;
import com.src.printing.office.po.model.user.update.UserUpdateRequest;
import com.src.printing.office.po.repository.KorisnikRepository;
import com.src.printing.office.po.repository.RolaRepository;
import com.src.printing.office.po.service.KorisnikService;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@Autowired
	private KorisnikAssembler korisnikAssembler;

	@Autowired
	private RolaRepository rolaRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(UserSaveRequest request) throws ApiException {
		Korisnik kExists = korisnikRepository.findByUsername(request.getUsername());
		if (kExists != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "korisnik"));

		Korisnik k = korisnikAssembler.fromSaveRequestToEntity(request);
		k.setKorisnickaSifra(passwordEncoder.encode(request.getPassword()));

		korisnikRepository.save(k);
	}

	@Override
	public void update(UserUpdateRequest request) throws ApiException {
		Optional<Korisnik> k = korisnikRepository.findById(request.getId());
		if (!k.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "korisnik"));

		Korisnik kExists = korisnikRepository.findByKorisnickoImeAndIdNot(request.getUsername(), request.getId());
		if (kExists != null)
			throw new ApiException(new ApiError(ApiErrorMessage.EXISTS, "korisnik"));

		Korisnik kUpdate = korisnikAssembler.fromUpdateRequestToEntity(k.get(), request);
		if (!kUpdate.getKorisnickaSifra().startsWith(request.getPassword()))
			kUpdate.setKorisnickaSifra(passwordEncoder.encode(request.getPassword()));

		korisnikRepository.save(kUpdate);
	}

	@Override
	public UserResponse getOne(Long id) throws ApiException {

		if (id == null)
			throw new ApiException(new ApiError(ApiErrorMessage.MANDATORY, "id"));

		Optional<Korisnik> k = korisnikRepository.findById(id);
		if (!k.isPresent())
			throw new ApiException(new ApiError(ApiErrorMessage.NOT_FOUND, "korisnik"));

		return korisnikAssembler.fromKorisnikEntityToUserResponse(k.get());

	}

	@Override
	public Korisnik findByUsername(String username) {
		return korisnikRepository.findByUsername(username);
	}

	@Override
	public List<POGenericType> getAllRolas() {
		List<Rola> rolaList = rolaRepository.findAll();

		return rolaList.stream().map(x -> new POGenericType(x.getId(), x.getPunNaziv())).collect(Collectors.toList());
	}

	@Override
	public UserCheckUniqueResponse checkUnique(String username) {
		Korisnik k = korisnikRepository.findByUsername(username);

		return k != null ? new UserCheckUniqueResponse(true) : new UserCheckUniqueResponse(false);
	}

	@Override
	public List<POGenericType> getAll() {
		List<Korisnik> korisnikList = korisnikRepository.findAll();

		return korisnikAssembler.fromEntityListToGetAllResponse(korisnikList);
	}

	@Override
	public List<SearchUserResponse> search(SearchUserRequest request) {
		List<Korisnik> list = korisnikRepository.search(request.getUser() != null ? request.getUser().getId() : null);

		return korisnikAssembler.fromEntityListToSearchUserResponse(list);
	}

}
