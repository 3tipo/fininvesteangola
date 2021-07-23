package com.bolsadeideias.springboot.app.modell;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bolsadeideias.springboot.app.embedable.DadosPessoais;
import com.bolsadeideias.springboot.app.embedable.DocumentosdeIdentificacao;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Entity
@Table(name = "fichadossocios")
public class FichadoSocio implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	private String  numerosocio;
	private String nivel;
	private double totallicado;
	private double totallucro;
	private String escalao;
	
	@Embedded
	private DadosPessoais dadospessoais;
	@Embedded
	private DocumentosdeIdentificacao identificacao;
	@OneToMany(cascade = CascadeType.ALL)
	private List<QuotaPaga> listquotaspagas;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TaxasPga> listtaxaspagas;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<BonosAdquirido> listbonosadquiridos;
	
	private double joiapaga;
	private String nif;
	private String datadeadmissao;
	private String dataregisto;
	private String descricao;
	private String administrador;
	private int anoinicio;
	private int anodefim;
	
	public FichadoSocio() {
		this.listquotaspagas= new ArrayList<QuotaPaga>();
		this.listtaxaspagas= new ArrayList<TaxasPga>();
	}
	
	public void savetaxpaga(List<TaxasPga> listtaxaspagas) {
		
		for (TaxasPga taxasPga : listtaxaspagas) {
			this.listtaxaspagas.add(taxasPga);
			taxasPga.setFichadoSocio(this);
		}
		
	}
	
	
	public void savequotapaga(List<QuotaPaga> listquotaspagas) {
		
		for (QuotaPaga quotaPaga : listquotaspagas) {
			this.listquotaspagas.add(quotaPaga);
			quotaPaga.setFichadoSocio(this);
		}
	}
	
	
	public void savebonos(BonosAdquirido bonos) {
		
		//for (BonosAdquirido bonosAdquirido : listbonosadquiridos) {
			this.listbonosadquiridos.add(bonos);
			//bonos.setFichadoSocio(this);
		//}
	}
	
	
	
}
