package com.bolsadeideias.springboot.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.bolsadeideias.springboot.app.modell.TaxasPga;
//import com.bolsadeideias.springboot.app.repo.Taxarepo;

@Service
public class TaxaPagaService {

	//@Autowired
	//private Taxarepo taxarepo;
	
	
	@ReadOnlyProperty
	public List<TaxasPga> pagamentoTaxa(int ano, double valorjuroTaxaOuQuota
			,String nomedosocio,String codigodosocio,String bidosocio){
		
		List<TaxasPga> taxasPgas= new ArrayList<TaxasPga>();
		
		TaxasPga janeiro = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "JANEIRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-02-"+05,"01");
		TaxasPga fevereiro = new TaxasPga("TAXA",valorjuroTaxaOuQuota, "FEVEREIRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-03-"+05,"02");
		TaxasPga marco = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "MARÇO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-04-"+05,"03");
		TaxasPga abril = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "ABRIL", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-05-"+05,"04");
		
		TaxasPga maio = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "MAIO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-06-"+05,"05");
		TaxasPga junho = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "JUNHO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-07-"+05,"06");
		TaxasPga julho = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "JULHO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-08-"+05,"07");
		TaxasPga agosto = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "AGOSTO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-09-"+05,"08");
		
		TaxasPga setembro = new TaxasPga("TAXA",valorjuroTaxaOuQuota, "SETEMBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-10-"+05,"09");
		TaxasPga outubro = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "OUTUBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-11-"+05,"10");
		TaxasPga novembro = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "NOVEMBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-12-"+05,"11");
		TaxasPga dezembro = new TaxasPga("TAXA", valorjuroTaxaOuQuota, "DEZEMBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-01-"+05,"12");
		
		taxasPgas.add(janeiro);
		taxasPgas.add(fevereiro);
		taxasPgas.add(marco);
		taxasPgas.add(abril);
		taxasPgas.add(maio);
		taxasPgas.add(junho);
		taxasPgas.add(julho);
		taxasPgas.add(agosto);
		taxasPgas.add(setembro);
		taxasPgas.add(outubro);
		taxasPgas.add(novembro);
		taxasPgas.add(dezembro);
		
		return taxasPgas;
	}
	
	/*
	@ReadOnlyProperty
	public List<TaxasPga> findSegundoEscalaoTaxas(int ano){
		
		List<TaxasPga> taxasPgas= new ArrayList<TaxasPga>();
		
		TaxasPga janeiro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "JANEIRO", ""+ano);
		TaxasPga fevereiro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "FEVEREIRO", ""+ano);
		TaxasPga marco = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "MARÇO", ""+ano);
		TaxasPga abril = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "ABRIL", ""+ano);
		
		TaxasPga maio = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "MAIO", ""+ano);
		TaxasPga junho = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "JUNHO", ""+ano);
		TaxasPga julho = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "JULHO", ""+ano);
		TaxasPga agosto = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "AGOSTO", ""+ano);
		
		TaxasPga setembro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "SETEMBRO", ""+ano);
		TaxasPga outubro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "OUTUBRO", ""+ano);
		TaxasPga novembro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "NOVEMBRO", ""+ano);
		TaxasPga dezembro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 40000.00, "DEZEMBRO", ""+ano);
		
		taxasPgas.add(janeiro);
		taxasPgas.add(fevereiro);
		taxasPgas.add(marco);
		taxasPgas.add(abril);
		taxasPgas.add(maio);
		taxasPgas.add(junho);
		taxasPgas.add(julho);
		taxasPgas.add(agosto);
		taxasPgas.add(setembro);
		taxasPgas.add(outubro);
		taxasPgas.add(novembro);
		taxasPgas.add(dezembro);
		
		return taxasPgas;
	}

	
	@ReadOnlyProperty
	public List<TaxasPga> findTerceiroEscalaoTaxas(int ano){
		
		List<TaxasPga> taxasPgas= new ArrayList<TaxasPga>();
		
		TaxasPga janeiro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "JANEIRO", ""+ano);
		TaxasPga fevereiro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "FEVEREIRO", ""+ano);
		TaxasPga marco = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "MARÇO", ""+ano);
		TaxasPga abril = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "ABRIL", ""+ano);
		
		TaxasPga maio = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "MAIO", ""+ano);
		TaxasPga junho = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "JUNHO", ""+ano);
		TaxasPga julho = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "JULHO", ""+ano);
		TaxasPga agosto = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "AGOSTO", ""+ano);
		
		TaxasPga setembro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "SETEMBRO", ""+ano);
		TaxasPga outubro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "OUTUBRO", ""+ano);
		TaxasPga novembro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "NOVEMBRO", ""+ano);
		TaxasPga dezembro = new TaxasPga("RECIBO DE PAGAMENTO DE TAXA", 30000.00, "DEZEMBRO", ""+ano);
		
		taxasPgas.add(janeiro);
		taxasPgas.add(fevereiro);
		taxasPgas.add(marco);
		taxasPgas.add(abril);
		taxasPgas.add(maio);
		taxasPgas.add(junho);
		taxasPgas.add(julho);
		taxasPgas.add(agosto);
		taxasPgas.add(setembro);
		taxasPgas.add(outubro);
		taxasPgas.add(novembro);
		taxasPgas.add(dezembro);
		
		return taxasPgas;
	}
	*/
}
