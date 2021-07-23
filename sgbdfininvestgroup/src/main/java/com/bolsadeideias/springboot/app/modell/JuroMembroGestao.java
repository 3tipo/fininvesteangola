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
@Entity
@Table(name = "joias")
public class JuroMembroGestao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private double  valorjuro;
	private String  taxaoucota;
	private String dataalteracao;
	private String admin;
	public JuroMembroGestao() {
		// TODO Auto-generated constructor stub
	}
}
