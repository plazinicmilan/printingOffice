package com.src.printing.office.po.service;

import java.util.List;

import com.src.printing.office.exception.ApiException;
import com.src.printing.office.po.entity.Korisnik;
import com.src.printing.office.po.model.POGenericType;
import com.src.printing.office.po.model.user.checkUnique.response.UserCheckUniqueResponse;
import com.src.printing.office.po.model.user.getOne.response.UserResponse;
import com.src.printing.office.po.model.user.save.UserSaveRequest;
import com.src.printing.office.po.model.user.search.request.SearchUserRequest;
import com.src.printing.office.po.model.user.search.response.SearchUserResponse;
import com.src.printing.office.po.model.user.update.UserUpdateRequest;

public interface KorisnikService {

	public UserResponse getOne(Long id) throws ApiException;

	public Korisnik findByUsername(String username);

	public List<POGenericType> getAllRolas();

	public UserCheckUniqueResponse checkUnique(String username);

	public void save(UserSaveRequest request) throws ApiException;

	public List<POGenericType> getAll();

	public List<SearchUserResponse> search(SearchUserRequest request);

	public void update(UserUpdateRequest request) throws ApiException;

}
