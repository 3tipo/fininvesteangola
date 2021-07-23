package com.bolsadeideias.springboot.app.modell;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bolsadeideias.springboot.app.embedable.DatadeRecepcao;
import com.bolsadeideias.springboot.app.embedable.Multa;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "taxaspagas")
public class TaxasPga implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private String t_tipodedocumento;
	private String t_numerorecibo;
	private double t_valor; 
	private String t_datapagamento;
	private String t_mespago;
	private String t_mespagoint;
	private String t_anopago;
	
	//taxa ou quota
	//@Embedded
	//private Multa  t_multa;
	private String datademulta;
	private boolean multado;
	private double valordamulta;
	private double t_juros;
	private String t_descricao;
	//@Embedded
	//private DatadeRecepcao datarecepcao;
	private String dataderecepcao;
	private boolean recebido;
	private boolean t_estapaga;
	private String t_secretaria;
	
	private String nomedosocio;
	private String codigodosocio;
	private String bidosocio;
	private String datalimite;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fichadoSocio")
	private FichadoSocio fichadoSocio;
	
	public TaxasPga() {
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:dd");
		this.dataderecepcao = df.format(new Date());
	}
	
	public TaxasPga( String t_tipodedocumento,double valor, String mespago, String anopago
			,String nomedosocio,String codigodosocio,String bidosocio, String datalimite,String t_mespagoint) {
		
		this.t_valor = valor;
		this.t_mespago = mespago;
		this.t_anopago = anopago;
		this.t_tipodedocumento= t_tipodedocumento;
		this.nomedosocio = nomedosocio;
		this.codigodosocio = codigodosocio;
		this.bidosocio  = bidosocio;
		this.datalimite = datalimite;
		this.t_mespagoint = t_mespagoint;
	}


	/*public void saveficha(FichadoSocio fichadoSocio) {
		this.fichadoSocio= fichadoSocio;
		fichadoSocio.getListtaxaspagas().add(this);
	}*/
	
}
