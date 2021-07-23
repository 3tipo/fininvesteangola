package com.bolsadeideias.springboot.app.modell;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bolsadeideias.springboot.app.embedable.DadosPessoais;
import com.bolsadeideias.springboot.app.embedable.DocumentosdeIdentificacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Entity
@Getter
@Table(name = "pedidosdecreditos")
public class PedidodeCredito implements Serializable{
	public PedidodeCredito() {
		this.reembolsoComMulta();
		this.reembolsoSemMulta();
		this.getIdentificacao();
	}
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	@Embedded
	public DadosPessoais identificacao;
	@Embedded
	private DocumentosdeIdentificacao documento;
	
	private String telefonerequerente;
	private String emailrequerente;
	
	private String provinciatest;
	private String municipiotest;
	private String comunatest;
	private String ruatest;
	private String casanumtest;
	private String telefonetest;
	private String nometest;
	
	private String tipodecliente;
	private String tipodecredito;
	private String intervalocred;
	private String datainicio;
	private String datafim;
	private String prazo;
	private int    dias;
	
	private double aplicaveljuro;
	private double taxaincumprimento;
	private double taxaemkz;
	private double valordocreditokz;
	private double aplicaveljurokz;
	private double valorareembolsar;

	private String descricao;
	private String artigoaipotecar;
	private String estado;
	
	private String conta;
	private String banco;
	private String iban; 
	private String ben;  
	private String pais_;
	
	private String bisocio;
	private String bonificado;// S   N
	
	public double juroDaAplicacao() {
		 this.setAplicaveljurokz(this.getValordocreditokz()*(this.getAplicaveljuro()/100));
		 return this.getAplicaveljurokz();
	}
	
	public double jurosDeMulta() {
		return this.getValordocreditokz()*(this.getTaxaincumprimento()/100);
	}
	
	public double reembolsoSemMulta() {
		 this.setValorareembolsar(this.getValordocreditokz() + this.juroDaAplicacao());
	
		 return this.getValorareembolsar();
	}
	
	
	public double reembolsoComMulta() {
		return this.getValordocreditokz() + this.jurosDeMulta();
	}

	
}
