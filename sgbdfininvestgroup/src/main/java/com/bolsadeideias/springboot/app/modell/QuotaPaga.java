package com.bolsadeideias.springboot.app.modell;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.bolsadeideias.springboot.app.embedable.DatadeRecepcao;
import com.bolsadeideias.springboot.app.embedable.Multa;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "quotaspagas")
public class QuotaPaga implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private String tipodedocumento;
	private String numerorecibo;
	private double valor; 
	
	private String datapagamentoquota;
	//private String datapagamentojuro;
	private String datapagamentomulta;
	private String mespago;
	private String mespagoint;
	private String anopago;
	
	
	
	//taxa ou quota
	//@Embedded
	//private Multa  multa;
	private String datademulta;
	private boolean multado;
	private double valordamulta;
	private double juros;
	private String descricao;
	//@Embedded
	//private DatadeRecepcao datarecepcao;
	private String dataderecepcao;
	private boolean recebido;
	private boolean estapaga;
	private String secretaria;
	
	private String nomedosocio;
	private String codigodosocio;
	private String bidosocio;
	private String datalimite;
	
	
	
	@ManyToOne
	@JoinColumn(name = "fichadoSocio")
	private FichadoSocio fichadoSocio;
	public QuotaPaga() {
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		this.dataderecepcao = df.format(new Date());
		      
	}
	public QuotaPaga(String tipodedocumento, double valor, String mespago, String anopago,
			String nomedosocio,String codigodosocio,String bidosocio, String datalimite,String mespagoint) {
		
		this.tipodedocumento = tipodedocumento;
		this.valor = valor;
		this.mespago = mespago;
		this.anopago = anopago;
		this.nomedosocio = nomedosocio;
		this.codigodosocio = codigodosocio;
		this.bidosocio  = bidosocio;
		this.datalimite = datalimite;
		this.mespagoint = mespagoint;
	}
	
	public void savefich(FichadoSocio fichadoSocio) {
		
		
		this.fichadoSocio = fichadoSocio;
		fichadoSocio.getListquotaspagas().add(this);
		
	}
	
}
