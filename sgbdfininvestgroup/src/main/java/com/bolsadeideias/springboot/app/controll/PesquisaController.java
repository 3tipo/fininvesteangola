package com.bolsadeideias.springboot.app.controll;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideias.springboot.app.modell.FichadoSocio;
import com.bolsadeideias.springboot.app.modell.JuroMembroGestao;
import com.bolsadeideias.springboot.app.modell.Pesquisa;
import com.bolsadeideias.springboot.app.modell.QuotaPaga;
import com.bolsadeideias.springboot.app.modell.TaxasPga;
import com.bolsadeideias.springboot.app.relatorio.Relactorio;
import com.bolsadeideias.springboot.app.repo.BonosRepository;
import com.bolsadeideias.springboot.app.repo.FichadoSocioRepository;
import com.bolsadeideias.springboot.app.repo.JuroMembroGestaoRepository;
import com.bolsadeideias.springboot.app.repo.QuotaGestaoRepository;
import com.bolsadeideias.springboot.app.repo.QuotasPagaRepository;
import com.bolsadeideias.springboot.app.repo.TaxaGestaoRepository;
import com.bolsadeideias.springboot.app.repo.TaxasPagaRepository;
import com.bolsadeideias.springboot.app.service.QuotaPagaService;
import com.bolsadeideias.springboot.app.service.TaxaPagaService;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/")
public class PesquisaController {

	
	@Autowired
	private TaxasPagaRepository taxaspagaRepository;
	
	
	@Autowired
	private QuotasPagaRepository quotaspagaRepository;
	
	
	@Autowired
	private JuroMembroGestaoRepository juroMembroGestaoRepository;
	
	
	@Autowired
	private FichadoSocioRepository fichadoSocioRepository;
	
	@Autowired
	private Relactorio relatorio;
	
	@Autowired
	private TaxasPagaRepository taxasPagaRepository;
	
	@GetMapping("pesquisa")
	public String pesquisaform(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		Pesquisa pesquisa = new Pesquisa();
		model.addAttribute("pesquisa",pesquisa);
		model.addAttribute("titulo","FORMULÁRIO DE PESQUISA");
		return "pesquisa/pesquisa-form";
	}
	
	
	@PostMapping("/pesquisa/pagamentodequota")
	public String pesquisaListadeQuota(Pesquisa pesquisa,  Model model, SessionStatus sesStatus,RedirectAttributes flash){
		if(pesquisa.getMes()=="") {
			
			flash.addFlashAttribute("error", "ESCOLHE UM MÊS PARA A PESQUISA SER PROCESSADA.");
			return "redirect:/pesquisa";
		}
		model.addAttribute("quotas", quotaspagaRepository.listadePagamentodeQuotaPorAnoEmes(""+pesquisa.getAno(), pesquisa.getMes()));
		model.addAttribute("titulo","LISTA DE COBRANÇA DE QUOTA REFERENTE AO ANO:"+pesquisa.getAno()+" MÊS: "+pesquisa.getMes());
		return "pesquisa/cobrar-quota";
	}
	
	
	@PostMapping("/pesquisa/pagamentodetaxa")
	public String pesquisalistaTaxa(Pesquisa pesquisa,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		if(pesquisa.getMes()=="") {
			
			flash.addFlashAttribute("error", "ESCOLHE UM MÊS PARA A PESQUISA SER PROCESSADA.");
			return "redirect:/pesquisa";
		}
		model.addAttribute("taxas", taxaspagaRepository.listadePagamentodeTaxaPorAnoEmes(""+pesquisa.getAno(), pesquisa.getMes()));
		model.addAttribute("titulo","LISTA DE COBRANÇA DE TAXA REFERENTE AO ANO:"+pesquisa.getAno()+" MÊS: "+pesquisa.getMes());
		return "pesquisa/pagar-taxa";
	} 
	
	@PostMapping("/pesquisa/pagamentodedividadequota")
	public String pesquisaListdividaquota(Pesquisa pesquisa,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		if(pesquisa.getMes()=="") {
			
			flash.addFlashAttribute("error", "ESCOLHE UM MÊS PARA A PESQUISA SER PROCESSADA.");
			return "redirect:/pesquisa";
		}
		model.addAttribute("quotas", quotaspagaRepository.listadeDividasdeQuotaporAnoEmes(""+pesquisa.getAno(), pesquisa.getMes()));
		model.addAttribute("titulo","LISTA DE DÍVIDAS DE QUOTA INFERIOR OU IGUAL AO ANO:"+pesquisa.getAno()+" MÊS: "+pesquisa.getMes());
		return "pesquisa/cobrar-quotadivida";
	}
	@PostMapping("/pesquisa/pagamentodedividadetaxa")
	public String pesquisalistdividataxa(Pesquisa pesquisa,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		if(pesquisa.getMes()=="") {
			flash.addFlashAttribute("error", "ESCOLHE UM MÊS PARA A PESQUISA SER PROCESSADA.");
			return "redirect:/pesquisa";
		}
		model.addAttribute("taxas", taxaspagaRepository.listadeDividasdeTaxasporAnoeMes(""+pesquisa.getAno(), pesquisa.getMes()));
		model.addAttribute("titulo","LISTA DE DÍVIDAS DE TAXA INFERIOR OU IGUAL AO ANO:"+pesquisa.getAno()+" MÊS: "+pesquisa.getMes());
		return "pesquisa/pagar-taxadivida";
	}
	
	
	@GetMapping("pagarquota/pesquisa")
	public String showlistquota(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		SimpleDateFormat mes = new SimpleDateFormat("MM");
		SimpleDateFormat ano = new SimpleDateFormat("YYYY");
		String ano1 = ano.format(new Date());
		String mes1 = mes.format(new Date());
		model.addAttribute("quotas", quotaspagaRepository.listadePagamentodeQuotaPorAnoEmes(ano1, mes1));
		model.addAttribute("titulo","LISTA DE COBRANÇA DE QUOTA REFERENTE AO ANO:"+ano1+" MÊS: "+mes1);
		return "pesquisa/cobrar-quota";
	}
	
	@GetMapping("/quota/{id}")
	//@ResponseBody
	public String pagarQuota(@PathVariable("id") int id,Model model,RedirectAttributes flas) {
	   // 
		QuotaPaga q= new QuotaPaga();
		
		
		QuotaPaga quota =quotaspagaRepository.findById(id).get();
		
		int idf=quota.getFichadoSocio().getId();
		FichadoSocio fc = fichadoSocioRepository.findById(idf).get();
         if(quota.isRecebido() || quota.isMultado()) {
			
			flas.addFlashAttribute("error", "NAO E PERMITIDO REGISTAR POR DUAS VEZES OU ALTERAR UM PAGAMENTO");
			return "redirect:/pagarquota/pesquisa";
		}  
		for (QuotaPaga quotaPaga : fc.getListquotaspagas()) {
        	  
        	  if(quotaPaga.getId()==id) {
				q= quotaPaga;
			}
		}
  		model.addAttribute("titulo", "REGISTO DE TAXA DO SOCIO " + " : " + 
		fc.getDadospessoais().getNomecompleto() );
  		model.addAttribute("escalao"," ESCALAO:\t        "+ fc.getEscalao());
  		model.addAttribute("mes"," REFERENTE A: "+ q.getMespago());
  		model.addAttribute("ano"," DO "+ " ANO: "+ q.getAnopago());
  		model.addAttribute("valor1","VALOR DA TAXA: "+ " "+ q.getValor());
  		
  		model.addAttribute("quota", q);
	
		return "pesquisa/socio-editar-quota-";
	}
	
	
	@PostMapping("/savequota/quota")
    public String SaveQuota( QuotaPaga quota, BindingResult result, Model model,RedirectAttributes flash ) {
		
		int diamulta = 5; int id = 0; int quantidadequota = 0;
		
		QuotaPaga quota1 = quotaspagaRepository.findById(quota.getId()).get();
		quantidadequota = (int) (quotaspagaRepository.count() + 1);
		
		id = quota1.getFichadoSocio().getId();
		
		double valor = 0;
		
		double juro = 	juroMembroGestaoRepository.findByTaxaoucota("ATRASOQUOTA").orElseGet(null).getValorjuro();
		
		
		if(quota.isRecebido() & !quota.isMultado() ) {
						
			quota1.setRecebido(quota.isRecebido());
			quota1.setMultado(false);
			quota1.setDescricao(quota.getDescricao());
			///quota1.setDataderecepcao(quota.getDataderecepcao());
			quota1.setDatapagamentoquota(quota.getDataderecepcao());
			quota1.setNumerorecibo("RCQTA"+""+String.valueOf(Calendar.
					getInstance().get(Calendar.YEAR))+"-"+quota1.getId());
			
			quota1.getFichadoSocio().setTotallicado(quota1.getFichadoSocio().getTotallicado() + quota1.getValor() );
			
			quotaspagaRepository.save(quota1);
		}else if(!quota.isRecebido() && quota.isMultado()) {
			
			valor =  quota1.getValor()*(juro/100);
					
			quota1.setDescricao(quota.getDescricao());
			//quota1.setDatademulta(quota.getDataderecepcao());
			quota1.setMultado(quota.isMultado());
			quota1.setRecebido(true);
			quota1.setDatapagamentoquota(quota.getDataderecepcao());
			quota1.setDatapagamentomulta(quota.getDataderecepcao());
			quota1.setNumerorecibo("RCQTA"+""+String.valueOf(Calendar.
					getInstance().get(Calendar.YEAR))+"-"+quota1.getId());
			quota1.setJuros(juro);
			
			quota1.setValordamulta(valor);
			quota1.getFichadoSocio().setTotallicado(quota1.getFichadoSocio().getTotallicado() + quota1.getValor() );

						
			quotaspagaRepository.save(quota1);
		}
		return "redirect:/pagarquota/pesquisa";
	}
	
	
	
	@GetMapping("dividadequota")
	public String showlistdividaquota(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		SimpleDateFormat mes = new SimpleDateFormat("MM");
		SimpleDateFormat ano = new SimpleDateFormat("YYYY");
		String ano1 = ano.format(new Date());
		String mes1 = mes.format(new Date());
		model.addAttribute("quotas", quotaspagaRepository.listadeDividasdeQuotaporAnoEmes(ano1, mes1));
		model.addAttribute("titulo","LISTA DE DÍVIDAS DE QUOTA INFERIOR OU IGUAL AO ANO:"+ano1+" MÊS: "+mes1);
		return "pesquisa/cobrar-quotadivida";
	}
	
	@GetMapping("dividadetaxas")
	public String showlistdividataxa(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		SimpleDateFormat mes = new SimpleDateFormat("MM");
		SimpleDateFormat ano = new SimpleDateFormat("YYYY");
		String ano1 = ano.format(new Date());
		String mes1 = mes.format(new Date());
		model.addAttribute("taxas", taxaspagaRepository.listadeDividasdeTaxasporAnoeMes(ano1, mes1));
		model.addAttribute("titulo","LISTA DE DÍVIDAS DE TAXA INFERIOR OU IGUAL AO ANO:"+ano1+" MÊS: "+mes1);
		return "pesquisa/pagar-taxadivida";
	}
	
	
	@GetMapping("pagartaxa")
	public String showlisttaxa(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		SimpleDateFormat mes = new SimpleDateFormat("MM");
		SimpleDateFormat ano = new SimpleDateFormat("YYYY");
		String ano1 = ano.format(new Date());
		String mes1 = mes.format(new Date());
		model.addAttribute("taxas", taxaspagaRepository.listadePagamentodeTaxaPorAnoEmes(ano1, mes1));
		model.addAttribute("titulo","LISTA DE COBRANÇA DE TAXA REFERENTE AO ANO:"+ano1+" MÊS: "+mes1);
		return "pesquisa/pagar-taxa";
	}  
	@GetMapping("taxa/{id}")
	public String  pagarTaxa(@PathVariable("id") int id,Model model,RedirectAttributes flas) {
	   // 
		TaxasPga t= new TaxasPga();
		TaxasPga taxa =taxaspagaRepository.findById(id).get();
		int idf=taxa.getFichadoSocio().getId();
		FichadoSocio fc = fichadoSocioRepository.findById(idf).get();
		if(taxa.isRecebido() || taxa.isMultado()) {
			
			flas.addFlashAttribute("error", "NAO E PERMITIDO REGISTAR POR DUAS VEZES OU ALTERAR UM PAGAMENTO");
			return "redirect:/pagartaxa";
		}
		for (TaxasPga taxaPaga : fc.getListtaxaspagas()) {
        	  
        	  if(taxaPaga.getId()==id) {
				t= taxaPaga;
			}
		}
  		model.addAttribute("titulo", "REGISTO DE TAXA DO SOCIO " + " : " + 
		fc.getDadospessoais().getNomecompleto() );
  		model.addAttribute("escalao"," ESCALAO:\t        "+ fc.getEscalao());
  		model.addAttribute("mes"," REFERENTE A: "+ t.getT_mespago());
  		model.addAttribute("ano"," DO "+ " ANO: "+ t.getT_anopago());
  		model.addAttribute("valor1","VALOR DA TAXA: "+ " "+ t.getT_valor());
  		
  		model.addAttribute("taxa", t);
	
		return "pesquisa/socio-editar-taxa-";
	}
	@PostMapping("savetaxa/taxa")
	public String Save( TaxasPga taxa, BindingResult result, Model model, RedirectAttributes flash ) {
		
			  
		int diamulta = 5; int id=0; int quantidadetaxa= 0;
		TaxasPga taxa1 =taxaspagaRepository.findById(taxa.getId()).get();
		quantidadetaxa = (int) (taxaspagaRepository.count() + 1);
		id= taxa1.getFichadoSocio().getId();
		double valor =0;
		double juro= 	juroMembroGestaoRepository.findByTaxaoucota("ATRASOTAXA").orElseGet(null).getValorjuro();
		
		
		if(taxa.isRecebido() & !taxa.isMultado() ) {
						
			taxa1.setRecebido(taxa.isRecebido());
			taxa1.setMultado(taxa.isMultado());
			taxa1.setT_descricao(taxa.getT_descricao());
			//taxa1.setDataderecepcao(taxa.getDataderecepcao());
			taxa1.setT_datapagamento(taxa.getDataderecepcao());
			taxa1.setT_numerorecibo("RCTXA"+""+String.valueOf(Calendar.
					getInstance().get(Calendar.YEAR))+"-"+taxa1.getId());
			
			
			
			taxaspagaRepository.save(taxa1);
		}else if(!taxa.isRecebido() && taxa.isMultado()) {
			
			valor =  taxa1.getT_valor()*(juro/100);
					
			taxa1.setT_descricao(taxa.getT_descricao());
			taxa1.setDatademulta(taxa.getDataderecepcao());
			taxa1.setMultado(taxa.isMultado());
			taxa1.setRecebido(true);
			taxa1.setT_datapagamento(taxa.getDataderecepcao());
			taxa1.setT_numerorecibo("RCTXA"+""+String.valueOf(Calendar.
					getInstance().get(Calendar.YEAR))+"-"+taxa1.getId());
			taxa1.setT_juros(juro);
			
			

			taxa1.setValordamulta(valor);
						
			taxaspagaRepository.save(taxa1);
		}
	
	return "redirect:/pagartaxa";
	}
	
	
	
	@GetMapping("quota/divida/{id}")
	//@ResponseBody
	public String  EditarDividadeQuota(@PathVariable("id") int id,Model model,RedirectAttributes flas) {
	   // 
		QuotaPaga q= new QuotaPaga();
		
		
		QuotaPaga taxa =quotaspagaRepository.findById(id).get();
		
		int idf=taxa.getFichadoSocio().getId();
		FichadoSocio fc = fichadoSocioRepository.findById(idf).get();
         if(taxa.isRecebido() || taxa.isMultado()) {
			
			flas.addFlashAttribute("error", "NAO E PERMITIDO REGISTAR POR DUAS VEZES OU ALTERAR UM PAGAMENTO");
			return "redirect:/dividadequota";
		}  
		for (QuotaPaga quotaPaga : fc.getListquotaspagas()) {
        	  
        	  if(quotaPaga.getId()==id) {
				q= quotaPaga;
			}
		}
  		model.addAttribute("titulo", "REGISTO DE TAXA DO SOCIO " + " : " + 
		fc.getDadospessoais().getNomecompleto() );
  		model.addAttribute("escalao"," ESCALAO:\t        "+ fc.getEscalao());
  		model.addAttribute("mes"," REFERENTE A: "+ q.getMespago());
  		model.addAttribute("ano"," DO "+ " ANO: "+ q.getAnopago());
  		model.addAttribute("valor1","VALOR DA TAXA: "+ " "+ q.getValor());
  		
  		model.addAttribute("quota", q);
	
		return "pesquisa/socio-editar-quotadivida";
	}

	@GetMapping("taxa/divida/{id}")
	//@ResponseBody
	public String  EditarTaxa(@PathVariable("id") int id,Model model,RedirectAttributes flas) {
	   // 
		TaxasPga t= new TaxasPga();
		TaxasPga taxa =taxaspagaRepository.findById(id).get();
		int idf=taxa.getFichadoSocio().getId();
		FichadoSocio fc = fichadoSocioRepository.findById(idf).get();
		if(taxa.isRecebido() || taxa.isMultado()) {
			
			flas.addFlashAttribute("error", "NAO E PERMITIDO REGISTAR POR DUAS VEZES OU ALTERAR UM PAGAMENTO");
			return "redirect:/dividadetaxa";
		}
		for (TaxasPga taxaPaga : fc.getListtaxaspagas()) {
        	  
        	  if(taxaPaga.getId()==id) {
				t= taxaPaga;
			}
		}
  		model.addAttribute("titulo", "REGISTO DE TAXA DO SOCIO " + " : " + 
		fc.getDadospessoais().getNomecompleto() );
  		model.addAttribute("escalao"," ESCALAO:\t        "+ fc.getEscalao());
  		model.addAttribute("mes"," REFERENTE A: "+ t.getT_mespago());
  		model.addAttribute("ano"," DO "+ " ANO: "+ t.getT_anopago());
  		model.addAttribute("valor1","VALOR DA TAXA: "+ " "+ t.getT_valor());
  		
  		model.addAttribute("taxa", t);
	
		return "pesquisa/socio-editar-taxadivida";
	}
	/*String.valueOf(Calendar.
				getInstance().get(Calendar.YEAR))*/
	
	
	@GetMapping("/taxa/exportar/{id}")
	public String reciboDeTaxa(HttpServletResponse response,@PathVariable("id") int id,RedirectAttributes flash) throws JRException, IOException {
		TaxasPga taxa = taxasPagaRepository.findById(id).get();
		
		if(taxa.isRecebido() ) {
			relatorio.reciboDeTaxa(response, id, "FININVEST-ANGOLA");
		}
		if(taxa.isMultado() ) {
			relatorio.reciboDeTaxa(response, id, "FININVEST-ANGOLA");
		}
		
		flash.addFlashAttribute("error", "NÃO É PERMITIDO EMITIR ESTE RECIBO");
		return "redirect:/pagartaxa";
	}
	
	@GetMapping("/quota/exportar/{id}")
	public String reciboDeQuota(HttpServletResponse response,@PathVariable("id") int id,RedirectAttributes flash) throws JRException, IOException {
		QuotaPaga quota = quotaspagaRepository.findById(id).get();
		
		if(quota.isRecebido() ) {
			relatorio.reciboDeQuota(response, id, "FININVEST-ANGOLA");
		}
		if(quota.isMultado() ) {
			relatorio.reciboDeQuota(response, id, "FININVEST-ANGOLA");
		}
		
		flash.addFlashAttribute("error", "NÃO É PERMITIDO EMITIR ESTE RECIBO");
		return "redirect:/pagarquota/pesquisa";
	}
	
	
}
