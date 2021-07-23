package com.bolsadeideias.springboot.app.modell;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "bonusadquiridos")
public class BonosAdquirido implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private double valorbonos;
	private String estadobonus;//pago, cancelado ou por pagar
	private String numeroentrada;
	private String codigodocredito;
	private String datapagamento;
	private String nomedocliente;
	private String bibeneficiari;
	private String administrador;
	private String dataregisto;
	
	public BonosAdquirido() {
		super();
	}
	
	
}
