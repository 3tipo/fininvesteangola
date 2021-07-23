package com.bolsadeideias.springboot.app.embedable;

import javax.persistence.Embeddable;


import lombok.Data;

@Embeddable
@Data
public class Multa {
	private String datademulta;
	private boolean multado;
	private double valordamulta;
}
