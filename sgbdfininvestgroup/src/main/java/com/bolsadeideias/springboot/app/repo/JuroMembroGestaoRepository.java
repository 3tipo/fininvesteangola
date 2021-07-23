package com.bolsadeideias.springboot.app.repo;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideias.springboot.app.modell.JuroMembroGestao;
import java.lang.String;
import java.util.List;
import java.util.Optional;

public interface JuroMembroGestaoRepository extends CrudRepository<JuroMembroGestao, Integer>{

	Optional<JuroMembroGestao> findByTaxaoucota(String taxaoucota);


}
