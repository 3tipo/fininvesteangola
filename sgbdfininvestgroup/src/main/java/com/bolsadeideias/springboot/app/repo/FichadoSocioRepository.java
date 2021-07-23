package com.bolsadeideias.springboot.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bolsadeideias.springboot.app.modell.FichadoSocio;
import java.lang.String;
import java.util.List;

public interface FichadoSocioRepository extends JpaRepository<FichadoSocio, Integer>{

	@Query(value = "SELECT *" + 
			"  FROM fichadossocios s where s.numero = ?;", nativeQuery = true)
	List<FichadoSocio> socioPorBI(@Param(value = "numero") String numero);
	
	@Query(value = "SELECT *" + 
			"  FROM fichadossocios s where s.numero = ?;", nativeQuery = true)
	FichadoSocio socioPornUMERODEBI(@Param(value = "numero") String numero);
	
	List<FichadoSocio> findByNumerosocio(String numerosocio);
	
}
