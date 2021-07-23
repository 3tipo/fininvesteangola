package com.bolsadeideias.springboot.app.embedable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DatadeRecepcao {
	private String dataderecepcao;
	private boolean recebido;
}
