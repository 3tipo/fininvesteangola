package com.bolsadeideias.springboot.app.embedable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class DadosPessoais {
	public String primeironome;
	private String nomecompleto;
	private String segundonome;
	private String nomedopai;
	private String nomedamae;
	private String datanascimento;
	private String naturalidade;
	private String provincia;
	private String municipio;
	private String municipioreseidente;
	private String bairro;
	private String rua;
	private String numerocasa;
	private String _nacionalidade;
	private String _telefone1;
	private String _telefone2;
	private String _email;
	private String residenciactual;
  
}
