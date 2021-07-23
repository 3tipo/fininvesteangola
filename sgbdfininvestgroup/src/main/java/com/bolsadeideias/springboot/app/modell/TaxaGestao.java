package com.bolsadeideias.springboot.app.modell;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Table(name = "taxagestao")
@Entity
public class TaxaGestao implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private static final long serialVersionUID = 1L;
	private String escalao;
	private double valortaxa;
	private String dataalteracao;
	private String admin;
	public TaxaGestao() {
		
	}
	

}
