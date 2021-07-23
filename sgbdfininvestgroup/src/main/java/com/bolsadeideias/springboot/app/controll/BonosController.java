package com.bolsadeideias.springboot.app.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideias.springboot.app.modell.BonosAdquirido;
import com.bolsadeideias.springboot.app.modell.FichadoSocio;
import com.bolsadeideias.springboot.app.modell.JuroMembroGestao;
import com.bolsadeideias.springboot.app.modell.PedidodeCredito;
import com.bolsadeideias.springboot.app.repo.BonosRepository;
import com.bolsadeideias.springboot.app.repo.FichadoSocioRepository;
import com.bolsadeideias.springboot.app.repo.JuroMembroGestaoRepository;
import com.bolsadeideias.springboot.app.repo.PedidodeCreditoRepository;
import com.bolsadeideias.springboot.app.repo.QuotaGestaoRepository;
import com.bolsadeideias.springboot.app.repo.TaxaGestaoRepository;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"taxa","fichadoSocio","bonosAdquirido","pedidodeCredito"})
public class BonosController {
	
	@Autowired
	private JuroMembroGestaoRepository jurosrepo;
	@Autowired
	private FichadoSocioRepository fsr;
	@Autowired
	private PedidodeCreditoRepository pedido;
	
	@Autowired
	private BonosRepository bonosrepo;
	
	
	private JuroMembroGestaoRepository juroMembroGestaoRepository;
	
	
	@GetMapping("bonos/{id}")
	public String bonos(BonosAdquirido bonosAdquirido  ,@PathVariable("id") int id, Model model,
			SessionStatus sesStatus, RedirectAttributes flash) {
		  
		
		PedidodeCredito pedidodeCredito = pedido.findById(id).orElse(null);
		         bonosAdquirido.setCodigodocredito(String.valueOf(pedidodeCredito.getId()));
		         bonosAdquirido.setBibeneficiari(pedidodeCredito.getBisocio());
		         bonosAdquirido.setNomedocliente(pedidodeCredito.getIdentificacao().getPrimeironome()+" "+pedidodeCredito.getIdentificacao().getSegundonome());
		        double bonos = jurosrepo.findByTaxaoucota("BONOS").orElseGet(null).getValorjuro();
		 		double valorbonos = 0; 
		 		if (pedidodeCredito.getEstado().equalsIgnoreCase("ATIVO") ||
		 				pedidodeCredito.getBisocio().equalsIgnoreCase("NENHUM INTERMEDIARIO")
		 				|| pedidodeCredito.getBonificado().equalsIgnoreCase("S")) {
		 			flash.addFlashAttribute("error", "ACAO NEGADA, TALVEZ ESTEJA ACTIVO OU SEM INTERMEDIARIO OU AINDA O BONOS EM ESTA DO \''S\''");
		 			return "redirect:/pedidos";
		 		}
		 		
					if (pedidodeCredito.getEstado().equalsIgnoreCase("REEMBOLSADOCOMMULTA")) {
						valorbonos = pedidodeCredito.jurosDeMulta()*(bonos/100);
						 bonosAdquirido.setValorbonos(valorbonos);
					}
					if (pedidodeCredito.getEstado().equalsIgnoreCase("REEMBOLSADOSEMMULTA")) {
						valorbonos = pedidodeCredito.juroDaAplicacao()*(bonos/100);
						 bonosAdquirido.setValorbonos(valorbonos);
					}

			model.addAttribute("bonosAdquirido", bonosAdquirido);
			//model.addAttribute("bonos", valorbonos);
			model.addAttribute("titulo", "ATRIBUIR BONOS");
			return "bonos/bonos-form";
		
	}
	
	
	@PostMapping("bonos/save")
	public String save(Model model, BonosAdquirido bonosAdquirido,SessionStatus sesStatus,RedirectAttributes flash) {
		
		
		//FichadoSocio  fichadoSocio = (FichadoSocio) fsr.socioPorBI(bonosAdquirido.getBibeneficiari());
		PedidodeCredito pc = pedido.findById(Integer.valueOf(bonosAdquirido.getCodigodocredito())).orElse(null);
		pc.setBonificado("S");
        
		
		bonosrepo.save(bonosAdquirido);
		//fsr.save(fichadoSocio);
		
		model.addAttribute("assunto", ""+bonosAdquirido.getEstadobonus()+""+bonosAdquirido.getBibeneficiari());
		model.addAttribute("bonos", bonosrepo.findAll());
		flash.addFlashAttribute("success", "BONOS ATRIBUIDO COM SUCESSO");
		//sesStatus.setComplete();
		return "bonos/bonos-list";
		
	}
	
	@GetMapping("bonos/editar/{id}")
	public String editarbonos(@PathVariable("id") int id, Model model,
			SessionStatus sesStatus, RedirectAttributes flash) {
		        
		BonosAdquirido bonosAdquirido = bonosrepo.findById(id).get();
		         if(bonosAdquirido.getEstadobonus().equalsIgnoreCase("PAGO")) {
		        	   flash.addFlashAttribute("error", "NÃO É PERMITIDO ALTERAR BONOS EM ESTADO PAGO.");
		        	 return "redirect:/bonos";
		         }
		         model.addAttribute("bonosAdquirido", bonosAdquirido);
					//model.addAttribute("bonos", valorbonos);
					model.addAttribute("titulo", "ATRIBUIR BONOS");
	return "bonos/bonos-form-edit";
	}
	
	@GetMapping("bonos")
	public String LISTBONOS(Model model, BonosAdquirido bonosAdquirido,SessionStatus sesStatus,RedirectAttributes flash) {
		model.addAttribute("bonos", bonosrepo.findAll());
		flash.addAttribute("titulo", "BONOS ATRIBUIDO COM SUCESSO");
		//sesStatus.setComplete();
		return "bonos/bonos-list";
		
	}
	
	@GetMapping("bonos/ver")
	public String verbonos(Model model, BonosAdquirido bonosAdquirido,SessionStatus sesStatus,RedirectAttributes flash) {
		model.addAttribute("bonos", bonosrepo.findAll());
		flash.addAttribute("titulo", "BONOS ATRIBUIDO COM SUCESSO");
		//sesStatus.setComplete();
		return "bonos/bonos-listver";
		
	}

}
