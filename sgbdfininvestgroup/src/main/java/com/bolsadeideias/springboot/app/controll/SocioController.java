package com.bolsadeideias.springboot.app.controll;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideias.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideias.springboot.app.modell.Ano;
import com.bolsadeideias.springboot.app.modell.FichadoSocio;
import com.bolsadeideias.springboot.app.modell.NiveldeAcesso;
import com.bolsadeideias.springboot.app.modell.Pesquisa;
import com.bolsadeideias.springboot.app.modell.QuotaPaga;
import com.bolsadeideias.springboot.app.modell.TaxasPga;
import com.bolsadeideias.springboot.app.modell.Usuario;
import com.bolsadeideias.springboot.app.repo.BonosRepository;
import com.bolsadeideias.springboot.app.repo.FichadoSocioRepository;
import com.bolsadeideias.springboot.app.repo.JuroMembroGestaoRepository;
import com.bolsadeideias.springboot.app.repo.QuotaGestaoRepository;
//import com.bolsadeideias.springboot.app.repo.Quotarepo;
import com.bolsadeideias.springboot.app.repo.QuotasPagaRepository;
import com.bolsadeideias.springboot.app.repo.TaxaGestaoRepository;
//import com.bolsadeideias.springboot.app.repo.Taxarepo;
import com.bolsadeideias.springboot.app.repo.TaxasPagaRepository;
import com.bolsadeideias.springboot.app.repo.UserRepository;
import com.bolsadeideias.springboot.app.service.QuotaPagaService;
import com.bolsadeideias.springboot.app.service.TaxaPagaService;


//@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/")
@SessionAttributes(value = {"taxa","fichadoSocio"})
public class SocioController {

	@Autowired
	private FichadoSocioRepository fichadoSocioRepository;
	
	@Autowired
	private TaxaPagaService taxaPagaService;
	
	@Autowired
	private TaxasPagaRepository taxaspagaRepository;
	
	@Autowired
	private QuotaPagaService quotaPagaService;
	@Autowired
	private QuotasPagaRepository quotaspagaRepository;
	
	
	@Autowired
	private TaxaGestaoRepository taxaGestaoRepository;
	@Autowired
	private QuotaGestaoRepository quotaGestaoRepository;
	@Autowired
	private JuroMembroGestaoRepository juroMembroGestaoRepository;
	
	@Autowired
	private LoginSuccessHandler successHandler;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private BonosRepository bonosRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:dd");

	@GetMapping
	public String home(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		Pesquisa pesquisa = new Pesquisa();
		model.addAttribute("pesquisa",pesquisa );
		Usuario u = new Usuario();
		u = userRepository.findByUsername(successHandler.username());
		if(u.getSocioid()==0) {
			model.addAttribute("titulo","VALOR TOTAL DE QUOTAS" );
			model.addAttribute("socio","SÓCIO ROOT:");
			model.addAttribute("quota","0.0");
			return "home2";
		}
		FichadoSocio f = fichadoSocioRepository.findById(u.getSocioid()).get();
		model.addAttribute("titulo","VALOR TOTAL DE QUOTAS" );
		model.addAttribute("socio","SÓCIO :"+f.getDadospessoais().getNomecompleto() );
		model.addAttribute("quota",f.getTotallicado() );
	return "home2";
	}
	
	@PostMapping("consultarqt/anoa")
	public String consultarqt(Pesquisa pesquisa,Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		
		model.addAttribute("titulo","CONSULTA PARA O ANO DE "+pesquisa.getAno() );
		Usuario u = new Usuario();
		u=userRepository.findByUsername(successHandler.username());
		
		Optional<FichadoSocio> f= fichadoSocioRepository.findById(u.getSocioid());
		model.addAttribute("titulo", "REGISTO DE QUOTAS DO SOCIO " + " : " + 
		f.get().getDadospessoais().getNomecompleto() );
		List<QuotaPaga> quotas= new ArrayList<QuotaPaga>();
		for (QuotaPaga quotaPaga : f.get().getListquotaspagas()) {
			if(quotaPaga.getAnopago().equalsIgnoreCase(String.valueOf(pesquisa.getAno() ))) {
				quotas.add(quotaPaga);
			}
		}
		model.addAttribute("quotas",quotas );
		return "socio-pagar-quota-list";	
	}
	
	@GetMapping("mudarsenha/{id}")
	public String mudarsenha_id(@PathVariable("id") int id,Pesquisa pesquisa,Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		Usuario u = new Usuario();
		FichadoSocio fs= fichadoSocioRepository.findById(id).get();
		u =  userRepository.findByBi(fs.getIdentificacao().getNumero());
		
		
		String passwordEncriptada= passwordEncoder.encode(fs.getIdentificacao().getNumero());
		u.setPassword(passwordEncriptada);
		userRepository.save(u);
		flash.addFlashAttribute("success","SENHA ALTERADA COM SUCESSO");
		return "redirect:/socios";
		
	}
	
	
	@PostMapping("mudarsenha")
	public String mudarsenha(Pesquisa pesquisa,Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		
		Usuario u = new Usuario();
		u=userRepository.findByUsername(successHandler.username());
		String passwordEncriptada= passwordEncoder.encode(pesquisa.getMes());
		u.setPassword(passwordEncriptada);
		userRepository.save(u);
		flash.addFlashAttribute("success","SENHA ALTERADA COM SUCESSO: "+pesquisa.getMes()+" PROIBIDO PARTILHAR A SENHA");
		return "redirect:/";
		
	}
	
	@GetMapping("consultarqt")
	public String consultarqt(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		Usuario u = new Usuario();
		u=userRepository.findByUsername(successHandler.username());
		
		Optional<FichadoSocio> f= fichadoSocioRepository.findById(u.getSocioid());
		model.addAttribute("titulo", "REGISTO DE QUOTAS DO SOCIO " + " : " + 
		f.get().getDadospessoais().getNomecompleto() );
		List<QuotaPaga> quotas= new ArrayList<QuotaPaga>();
		for (QuotaPaga quotaPaga : f.get().getListquotaspagas()) {
			if(quotaPaga.getAnopago().equalsIgnoreCase(String.valueOf(Calendar.getInstance().
					get(Calendar.YEAR)))) {
				quotas.add(quotaPaga);
			}
		}
		model.addAttribute("quotas",quotas );
		return "socio-pagar-quota-list";
		
	}
	
	@GetMapping("consultartx")
	public String consultartx(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		Usuario u = new Usuario();
		u=userRepository.findByUsername(successHandler.username());
		Optional<FichadoSocio> f= fichadoSocioRepository.findById(u.getSocioid());
		model.addAttribute("titulo", "REGISTO DE TAXAS DO SOCIO " + " : " + 
				f.get().getDadospessoais().getNomecompleto() );
				List<TaxasPga> taxas= new ArrayList<TaxasPga>();
				for (TaxasPga taxaPaga : f.get().getListtaxaspagas()) {
					if(taxaPaga.getT_anopago().equalsIgnoreCase(String.valueOf(Calendar.
							getInstance().get(Calendar.YEAR)))) {
						taxas.add(taxaPaga);
					}
				}
				model.addAttribute("taxas",taxas );
				return "socio-pagar-taxa-list";
	
	}
	
	@PostMapping("consultartx/anoa")
	public String consultartxano(Pesquisa pesquisa, Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		model.addAttribute("titulo","CONSULTA PARA O ANO DE "+pesquisa.getAno());
		Usuario u = new Usuario();
		u=userRepository.findByUsername(successHandler.username());
		Optional<FichadoSocio> f= fichadoSocioRepository.findById(u.getSocioid());
		model.addAttribute("titulo", "REGISTO DE TAXAS DO SOCIO " + " : " + 
				f.get().getDadospessoais().getNomecompleto() );
				List<TaxasPga> taxas= new ArrayList<TaxasPga>();
				for (TaxasPga taxaPaga : f.get().getListtaxaspagas()) {
					if(taxaPaga.getT_anopago().equalsIgnoreCase(String.valueOf(pesquisa.getAno() ))) {
						taxas.add(taxaPaga);
					}
				}
				model.addAttribute("taxas",taxas );
				return "socio-pagar-taxa-list";
	}
	
	
	@GetMapping("/socio-form")
	public String showform(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		FichadoSocio fichadoSocio= new FichadoSocio();
		model.addAttribute("titulo", "REGISTAR UM NOVO SÓCIO");
		
		model.addAttribute("escalaos", quotaGestaoRepository.findAll());
		model.addAttribute("fichadoSocio", fichadoSocio);
		return "socio/socio-form";
	}
	
	@PostMapping("socios/save")
	public String save(FichadoSocio fichadoSocio,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		long sizejuro=0,sizetaxa=0,sizequota=0;
		int size = 0,size1 = 0; size = fichadoSocioRepository.findByNumerosocio(fichadoSocio.getNumerosocio()).size();
		size1 = fichadoSocioRepository.socioPorBI(fichadoSocio.getIdentificacao().getNumero()).size();
		sizetaxa=taxaGestaoRepository.count();
		sizequota=quotaGestaoRepository.count();
		if(size1 > 0) {
			
			flash.addFlashAttribute("error", "O NÚMERO DE IDENTIFICAÇÃO  JÁ EXISTE.");
			return "redirect:/socio-form";
		}
		
		
		 /*if(sizejuro<=0) {
			 flash.addFlashAttribute("error", "VAI AO MENU DE CONFIGURAÇÕES E REGISTE VALORES PARA: \"BONOS\",\"ART\".");
			 return "redirect:/socio-form";
			}*/
				 
		if(size > 0 ) {
			flash.addFlashAttribute("error", "O NÚMERO DO SÓCIO SUGERIDO JÁ EXISTE.");
			return "redirect:/socio-form";
		}
		int ano = fichadoSocio.getAnoinicio(), y = fichadoSocio.getAnodefim();
	    String escalao = fichadoSocio.getEscalao();
	    String nomecompleto = fichadoSocio.getDadospessoais().getPrimeironome() 
	    		+ " " +fichadoSocio.getDadospessoais().getSegundonome();
	    double valordoescalaoQuota  = quotaGestaoRepository.findByEscalao(escalao).get().getValorquota();
	    double valordoescalaoTaxa   = taxaGestaoRepository.findByEscalao(escalao).get().getValortaxa();
	    
	    FichadoSocio fs= new FichadoSocio();
	    Usuario usuario= new Usuario();
	    fichadoSocio.getDadospessoais().setNomecompleto(nomecompleto);
		while ( ano <= y ) {
				fichadoSocio.savequotapaga(quotaPagaService.QuotaPaga(ano,valordoescalaoQuota,nomecompleto,fichadoSocio.getNumerosocio(),fichadoSocio.getIdentificacao().getNumero()));			
				fichadoSocio.savetaxpaga(taxaPagaService.pagamentoTaxa(ano, valordoescalaoTaxa,nomecompleto,fichadoSocio.getNumerosocio(),fichadoSocio.getIdentificacao().getNumero()));
				fs = fichadoSocioRepository.save(fichadoSocio);
				
         ano = ano + 1;
			
		}
		if(fs.getId()>0) {
			usuario.setBi(fs.getIdentificacao().getNumero());
			//usuario.setId((int)userRepository.count());
			usuario.setSocioid((int) (fichadoSocioRepository.count()));
			String passwordEncriptada= passwordEncoder.encode(String.valueOf(fs.getIdentificacao().getNumero()));
			usuario.setPassword(passwordEncriptada);
			usuario.setUsername(fs.getDadospessoais().getPrimeironome().toLowerCase());
			usuario.setEnabled(true);
			NiveldeAcesso na = new NiveldeAcesso();
			na.setAuthority(fichadoSocio.getNivel());
			usuario.addRol(na);
			userRepository.save(usuario);
		}
		flash.addFlashAttribute("success", "Novo Membro Registado com Sucesso!");
		flash.addFlashAttribute("success", "SALVO COM SUCESSO!!...|Username:"+fs.getDadospessoais().getPrimeironome().toLowerCase()+" || "+"Password:"+fs.getIdentificacao().getNumero());
		return "redirect:/socio-form";
	}
	
	@GetMapping("/socios")
	public String listAllSocios(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		//flash.addFlashAttribute("titulo", "SOCIOS");
		model.addAttribute("titulo", "LISTA DE SOCIOS");
		model.addAttribute("socios", fichadoSocioRepository.findAll());
		return "socio/socio-list";
	}
	
	
	@GetMapping("/socio/pagarquota/{id}")
	public String pagarQuota(@PathVariable("id") Integer id,Model model) {
	
		Optional<FichadoSocio> f= fichadoSocioRepository.findById(id);
		model.addAttribute("titulo", "REGISTO DE QUOTAS DO SOCIO " + " : " + 
		f.get().getDadospessoais().getNomecompleto() );
		//model.addAttribute("ano", ano);
		List<QuotaPaga> quotas= new ArrayList<QuotaPaga>();
		for (QuotaPaga quotaPaga : f.get().getListquotaspagas()) {
			if(quotaPaga.getAnopago().equalsIgnoreCase(String.valueOf(Calendar.getInstance().
					get(Calendar.YEAR)))) {
				quotas.add(quotaPaga);
			}
		}
		
		model.addAttribute("quotas",quotas );
		return "socio/socio-pagar-quota-list";
		
	}
		
	
	@GetMapping("/socio/pagartaxa/{id}")
	public String pagarTaxa(@PathVariable("id") Integer id,Model model) {
		Optional<FichadoSocio> f= fichadoSocioRepository.findById(id);
		model.addAttribute("titulo", "REGISTO DE TAXAS DO SOCIO " + " : " + 
		f.get().getDadospessoais().getNomecompleto() );
		List<TaxasPga> taxas= new ArrayList<TaxasPga>();
		
		for (TaxasPga taxaPaga : f.get().getListtaxaspagas()) {
			if(taxaPaga.getT_anopago().equalsIgnoreCase(String.valueOf(Calendar.
					getInstance().get(Calendar.YEAR)))) {
				taxas.add(taxaPaga);
			}
		}
		
		model.addAttribute("taxas",taxas );
		//log.info(""+Calendar.getInstance().get(Calendar.YEAR));
		return "socio/socio-pagar-taxa-list";
		
	}

	@GetMapping("taxa/edit/{id}")
	//@ResponseBody
	public String  Findone(@PathVariable("id") int id,Model model,RedirectAttributes flas) {
	   // 
		TaxasPga t= new TaxasPga();
		TaxasPga taxa =taxaspagaRepository.findById(id).get();
		int idf=taxa.getFichadoSocio().getId();
		FichadoSocio fc = fichadoSocioRepository.findById(idf).get();
		if(taxa.isRecebido() || taxa.isMultado()) {
			
			flas.addFlashAttribute("error", "NAO E PERMITIDO REGISTAR POR DUAS VEZES OU ALTERAR UM PAGAMENTO");
			return "redirect:/socios";
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
	
		return "socio/socio-editar-taxa-";
	}
	
	@GetMapping("/quota/edit/{id}")
	//@ResponseBody
	public String  Findone1(@PathVariable("id") int id,Model model,RedirectAttributes flas) {
	   // 
		QuotaPaga q= new QuotaPaga();
		
		
		QuotaPaga taxa =quotaspagaRepository.findById(id).get();
		
		int idf=taxa.getFichadoSocio().getId();
		FichadoSocio fc = fichadoSocioRepository.findById(idf).get();
         if(taxa.isRecebido() || taxa.isMultado()) {
			
			flas.addFlashAttribute("error", "NAO E PERMITIDO REGISTAR POR DUAS VEZES OU ALTERAR UM PAGAMENTO");
			return "redirect:/socios";
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
	
		return "socio/socio-editar-quota-";
	}
				
	@PostMapping("/savetaxa")
	public String Save( TaxasPga taxa, BindingResult result, Model model ) {
			  
		int diamulta = 5; int id=0; int quantidadetaxa= 0;
		TaxasPga taxa1 =taxaspagaRepository.findById(taxa.getId()).get();
		quantidadetaxa = (int) (taxaspagaRepository.count() + 1);
		id= taxa1.getFichadoSocio().getId();
		double valor =0;
		double juro= 	juroMembroGestaoRepository.findByTaxaoucota("ATRASOTAXA").get().getValorjuro();
		
		
		if(taxa.isRecebido() & !taxa.isMultado() ) {
						
			taxa1.setRecebido(taxa.isRecebido());
			taxa1.setMultado(taxa.isMultado());
			taxa1.setT_descricao(taxa.getT_descricao());
			taxa1.setDataderecepcao( df.format(new Date()));
			taxa1.setT_datapagamento(taxa.getDataderecepcao());
			taxa1.setT_numerorecibo("RCTXA"+""+String.valueOf(Calendar.
					getInstance().get(Calendar.YEAR))+"-"+taxa1.getId());
			
			
			taxaspagaRepository.save(taxa1);
		}else if(!taxa.isRecebido() && taxa.isMultado()) {
			
			valor =  taxa1.getT_valor()*(juro/100);
					
			taxa1.setT_descricao(taxa.getT_descricao());
			taxa1.setDataderecepcao( df.format(new Date()));
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
	
	
	return "redirect:/socio/pagartaxa/"+id;
	}

@PostMapping("/savequota")
	public String Save1( QuotaPaga quota, BindingResult result, Model model ) {
	
	int diamulta = 5; int id = 0; int quantidadequota = 0;
	
	QuotaPaga quota1 = quotaspagaRepository.findById(quota.getId()).get();
	quantidadequota = (int) (quotaspagaRepository.count() + 1);
	
	id = quota1.getFichadoSocio().getId();
	
	double valor = 0;
	
	double juro = 	juroMembroGestaoRepository.findByTaxaoucota("ATRASOQUOTA").get().getValorjuro();
	
	
	
	if(quota.isRecebido() & !quota.isMultado() ) {
					
		quota1.setRecebido(quota.isRecebido());
		quota1.setMultado(false);
		quota1.setDescricao(quota.getDescricao());
		quota1.setDataderecepcao( df.format(new Date()));
		quota1.setDatapagamentoquota(quota.getDataderecepcao());
		quota1.setNumerorecibo("RCQTA"+""+String.valueOf(Calendar.
				getInstance().get(Calendar.YEAR))+"-"+quota1.getId());
		
		quota1.getFichadoSocio().setTotallicado(quota1.getFichadoSocio().getTotallicado() + quota1.getValor() );
		
		quotaspagaRepository.save(quota1);
	}else if(!quota.isRecebido() && quota.isMultado()) {
		
		valor =  quota1.getValor()*(juro/100);
				
		quota1.setDescricao(quota.getDescricao());
		quota1.setDatademulta( df.format(new Date()));
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
	return "redirect:/socio/pagarquota/"+id;
}

@GetMapping("/socio/istorico/{id}")
//@ResponseBody
	public String  historico(@PathVariable("id") int id,Model model) {
	FichadoSocio fc =  fichadoSocioRepository.findById(id).orElse(null);
	
	
	model.addAttribute("aplicacao","TOTAL DE VALOR APLICADO:"+ " "+ fc.getTotallicado());
	model.addAttribute("lucro","TOTAL DE LUCROS"+ " "+ fc.getTotallucro());
	model.addAttribute("titulo", "CONTA CORRENTE DO SOCIO:"+"  "+fc.getDadospessoais().getPrimeironome());
    model.addAttribute("bonos", bonosRepository.findByBibeneficiari(fc.getIdentificacao().getNumero()));
	return "socio/historico-financeiro";

}

@GetMapping("/socio/dividas/{id}")
	public String dividas(@PathVariable("id") int id,Model model) {
	return "nul";//"socio/socio-divida-list";
}

@GetMapping("/socio/editar/{id}")
	public String detalhes(@PathVariable("id") int id,Model model) {
	model.addAttribute("titulo", "DETALHES");
	model.addAttribute("fichadoSocio", fichadoSocioRepository.findById(id).orElse(null));
	model.addAttribute("escalaos", quotaGestaoRepository.findAll());
	return "socio/detalhes-socio";
}


@PostMapping("/socios/update")
	public String update(FichadoSocio fichadoSocio,Model model, SessionStatus sesStatus,RedirectAttributes flash){
	
	fichadoSocioRepository.save(fichadoSocio);
	sesStatus.setComplete();
	return "redirect:/socios";
}
}

























