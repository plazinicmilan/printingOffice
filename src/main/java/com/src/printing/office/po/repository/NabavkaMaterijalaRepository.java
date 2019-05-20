package com.src.printing.office.po.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.src.printing.office.po.entity.Dobavljac;
import com.src.printing.office.po.entity.NabavkaMaterijala;

public interface NabavkaMaterijalaRepository extends CrudRepository<NabavkaMaterijala, Long> {

	@Query("SELECT nm FROM NabavkaMaterijala nm where (?1 is null or nm.dobavljac = ?1) AND "
			+ "(?2 is null or ?2 <= nm.datumNabavke) AND (?3 is null or ?3 > nm.datumNabavke) ORDER BY nm.id DESC")
	List<NabavkaMaterijala> search(Dobavljac dobavljac, Calendar datumOd, Calendar datumDo);
}
