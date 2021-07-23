package com.bolsadeideias.springboot.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.bolsadeideias.springboot.app.modell.QuotaPaga;
import com.bolsadeideias.springboot.app.modell.TaxasPga;
//import com.bolsadeideias.springboot.app.repo.Quotarepo;

@Service
public class QuotaPagaService {

	//@Autowired
	//private Quotarepo quotarepo;
	
	
	
	
	@ReadOnlyProperty
	public List<QuotaPaga> QuotaPaga(int ano, double valorescalao,
			String nomedosocio,String codigodosocio,String bidosocio){
		
        List<QuotaPaga> quotaspagas= new ArrayList<QuotaPaga>();
		
		QuotaPaga janeiro = new QuotaPaga("QUOTA",valorescalao, "JANEIRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-02-"+10,"01");
		QuotaPaga fevereiro = new QuotaPaga("QUOTA", valorescalao, "FEVEREIRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-03-"+10,"02");
		QuotaPaga marco = new QuotaPaga("QUOTA", valorescalao, "MARÇO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-04-"+10,"03");
		QuotaPaga abril = new QuotaPaga("QUOTA", valorescalao, "ABRIL", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-05-"+10,"04");
		
		QuotaPaga maio = new QuotaPaga("QUOTA", valorescalao, "MAIO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-06-"+10,"05");
		QuotaPaga junho = new QuotaPaga("QUOTA", valorescalao, "JUNHO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-07-"+10,"06");
		QuotaPaga julho = new QuotaPaga("QUOTA", valorescalao, "JULHO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-08-"+10,"07");
		QuotaPaga agosto = new QuotaPaga("QUOTA", valorescalao, "AGOSTO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-09-"+10,"08");
		
		QuotaPaga setembro = new QuotaPaga("QUOTA", valorescalao, "SETEMBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-10-"+10,"09");
		QuotaPaga outubro = new QuotaPaga("QUOTA", valorescalao, "OUTUBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-11-"+10,"10");
		QuotaPaga novembro = new QuotaPaga("QUOTA", valorescalao, "NOVEMBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-12-"+10,"11");
		QuotaPaga dezembro = new QuotaPaga("QUOTA", valorescalao, "DEZEMBRO", ""+ano,nomedosocio,codigodosocio,bidosocio,""+ano+"-01-"+10,"12");
		
		quotaspagas.add(janeiro);
		quotaspagas.add(fevereiro);
		quotaspagas.add(marco);
		quotaspagas.add(abril);
		quotaspagas.add(maio);
		quotaspagas.add(junho);
		quotaspagas.add(julho);
		quotaspagas.add(agosto);
		quotaspagas.add(setembro);
		quotaspagas.add(outubro);
		quotaspagas.add(novembro);
		quotaspagas.add(dezembro);
		
		return quotaspagas;
	}
	
	
	
	
	
	
	
	/*
	@ReadOnlyProperty
	public List<QuotaPaga> findPrimeiroEscalaoQuota(int ano){
		
		List<QuotaPaga> quotaspagas= new ArrayList<QuotaPaga>();
		
		QuotaPaga janeiro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "JANEIRO", ""+ano);
		QuotaPaga fevereiro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "FEVEREIRO", ""+ano);
		QuotaPaga marco = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "MARÇO", ""+ano);
		QuotaPaga abril = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "ABRIL", ""+ano);
		
		QuotaPaga maio = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "MAIO", ""+ano);
		QuotaPaga junho = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "JUNHO", ""+ano);
		QuotaPaga julho = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "JULHO", ""+ano);
		QuotaPaga agosto = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "AGOSTO", ""+ano);
		
		QuotaPaga setembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "SETEMBRO", ""+ano);
		QuotaPaga outubro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "OUTUBRO", ""+ano);
		QuotaPaga novembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "NOVEMBRO", ""+ano);
		QuotaPaga dezembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 50000.00, "DEZEMBRO", ""+ano);
		
		quotaspagas.add(janeiro);
		quotaspagas.add(fevereiro);
		quotaspagas.add(marco);
		quotaspagas.add(abril);
		quotaspagas.add(maio);
		quotaspagas.add(junho);
		quotaspagas.add(julho);
		quotaspagas.add(agosto);
		quotaspagas.add(setembro);
		quotaspagas.add(outubro);
		quotaspagas.add(novembro);
		quotaspagas.add(dezembro);
		
		return quotaspagas;
	}
	
	
	@ReadOnlyProperty
	public List<QuotaPaga> findSegundoEscalaoQuota(int ano){
		
		List<QuotaPaga> quotaspagas= new ArrayList<QuotaPaga>();
		
		QuotaPaga janeiro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "JANEIRO", ""+ano);
		QuotaPaga fevereiro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "FEVEREIRO", ""+ano);
		QuotaPaga marco = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "MARÇO", ""+ano);
		QuotaPaga abril = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "ABRIL", ""+ano);
		
		QuotaPaga maio = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "MAIO", ""+ano);
		QuotaPaga junho = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "JUNHO", ""+ano);
		QuotaPaga julho = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "JULHO", ""+ano);
		QuotaPaga agosto = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "AGOSTO", ""+ano);
		
		QuotaPaga setembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "SETEMBRO", ""+ano);
		QuotaPaga outubro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "OUTUBRO", ""+ano);
		QuotaPaga novembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "NOVEMBRO", ""+ano);
		QuotaPaga dezembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 40000.00, "DEZEMBRO", ""+ano);
		
		quotaspagas.add(janeiro);
		quotaspagas.add(fevereiro);
		quotaspagas.add(marco);
		quotaspagas.add(abril);
		quotaspagas.add(maio);
		quotaspagas.add(junho);
		quotaspagas.add(julho);
		quotaspagas.add(agosto);
		quotaspagas.add(setembro);
		quotaspagas.add(outubro);
		quotaspagas.add(novembro);
		quotaspagas.add(dezembro);
		
		return quotaspagas;
	}

	
	@ReadOnlyProperty
	public List<QuotaPaga> findTerceiroEscalaoQuota(int ano){
		
		List<QuotaPaga> quotaspagas= new ArrayList<QuotaPaga>();
		
		QuotaPaga janeiro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "JANEIRO", ""+ano);
		QuotaPaga fevereiro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "FEVEREIRO", ""+ano);
		QuotaPaga marco = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "MARÇO", ""+ano);
		QuotaPaga abril = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "ABRIL", ""+ano);
		
		QuotaPaga maio = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "MAIO", ""+ano);
		QuotaPaga junho = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "JUNHO", ""+ano);
		QuotaPaga julho = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "JULHO", ""+ano);
		QuotaPaga agosto = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "AGOSTO", ""+ano);
		
		QuotaPaga setembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "SETEMBRO", ""+ano);
		QuotaPaga outubro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "OUTUBRO", ""+ano);
		QuotaPaga novembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "NOVEMBRO", ""+ano);
		QuotaPaga dezembro = new QuotaPaga("RECIBO DE PAGAMENTO DE QUOTA", 30000.00, "DEZEMBRO", ""+ano);
		
		quotaspagas.add(janeiro);
		quotaspagas.add(fevereiro);
		quotaspagas.add(marco);
		quotaspagas.add(abril);
		quotaspagas.add(maio);
		quotaspagas.add(junho);
		quotaspagas.add(julho);
		quotaspagas.add(agosto);
		quotaspagas.add(setembro);
		quotaspagas.add(outubro);
		quotaspagas.add(novembro);
		quotaspagas.add(dezembro);
		
		return quotaspagas;
	}
	
	*/
}
