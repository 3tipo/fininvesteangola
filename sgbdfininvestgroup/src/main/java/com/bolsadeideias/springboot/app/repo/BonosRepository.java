package com.bolsadeideias.springboot.app.repo;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideias.springboot.app.modell.BonosAdquirido;
import java.lang.String;
import java.util.List;

public interface BonosRepository extends CrudRepository<BonosAdquirido, Integer>{

	List<BonosAdquirido> findByBibeneficiari(String bibeneficiari);
}
