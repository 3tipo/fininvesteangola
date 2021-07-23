package com.bolsadeideias.springboot.app.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideias.springboot.app.modell.PedidodeCredito;

public interface PedidodeCreditoRepository extends CrudRepository<PedidodeCredito, Integer>{

  Optional<PedidodeCredito>	findByBisocio(String bisocio);
}
