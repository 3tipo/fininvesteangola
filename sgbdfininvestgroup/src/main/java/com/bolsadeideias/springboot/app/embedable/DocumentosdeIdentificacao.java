package com.bolsadeideias.springboot.app.embedable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DocumentosdeIdentificacao {
	
	private String tipodedocumento;
	private String numero;
	private String localdeemissao;
	private String datadeemissao;
	private String caducidade;
	private String nacionalidadedoc;
	
}
