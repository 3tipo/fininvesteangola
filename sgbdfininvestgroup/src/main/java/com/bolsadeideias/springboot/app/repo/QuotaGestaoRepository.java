package com.bolsadeideias.springboot.app.repo;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideias.springboot.app.modell.QuotaGestao;
import java.lang.String;
import java.util.List;
import java.util.Optional;

public interface QuotaGestaoRepository extends CrudRepository<QuotaGestao, Integer>{
			
	Optional<QuotaGestao> findByEscalao(String escalao);
}
