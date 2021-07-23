package com.bolsadeideias.springboot.app.embedable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Emergencia {
	private String contacto;
	private String familiar;
	private String entidadeseguradora;
	private String telefoneseguradora;	 
}
