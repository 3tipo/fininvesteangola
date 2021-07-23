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

import com.bolsadeideias.springboot.app.modell.Credito;
import com.bolsadeideias.springboot.app.repo.CreditoRepository;
import com.bolsadeideias.springboot.app.repo.JuroMembroGestaoRepository;
import com.bolsadeideias.springboot.app.repo.PedidodeCreditoRepository;
import com.bolsadeideias.springboot.app.repo.QuotaGestaoRepository;
import com.bolsadeideias.springboot.app.repo.TaxaGestaoRepository;

@Controller
@RequestMapping("/")

public class CreditoController {

	@Autowired
	private CreditoRepository creditoRepository;
	
	@Autowired
	private TaxaGestaoRepository taxaGestaoRepository;
	@Autowired
	private QuotaGestaoRepository quotaGestaoRepository;
	@Autowired
	private JuroMembroGestaoRepository juroMembroGestaoRepository;
	
	
	@GetMapping("credito-form")
	public String GerirForm(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		Credito credito = new Credito();
		
		model.addAttribute("titulo",  "REGISTAR QUOTA");
		model.addAttribute("assunto",  "LISTA DE CRÉDITOS");
		model.addAttribute("credito",credito);
		model.addAttribute("creditos",creditoRepository.findAll());
		return "credito/credito-form";
	}
	
		
	@PostMapping("credito/save")
	public String Save(Credito credito,RedirectAttributes flash) {
		long sizejuro=0,sizetaxa=0,sizequota=0;
		sizetaxa=taxaGestaoRepository.count();
		sizequota=quotaGestaoRepository.count();
		sizejuro=juroMembroGestaoRepository.count();
		
		if(sizetaxa<=0) {
			flash.addFlashAttribute("error", "VAI AO MENU DE CONFIGURAÇÕES E REGISTE UM VALOR PARA TAXA.");
			return "redirect:/credito-form";
		}
		if(sizequota<=0) {
		 flash.addFlashAttribute("error", "VAI AO MENU DE CONFIGURAÇÕES E REGISTE UM VALOR PARA QUOTA.");
		 return "redirect:/credito-form";
		}
		 if(sizejuro<=0) {
			 flash.addFlashAttribute("error", "VAI AO MENU DE CONFIGURAÇÕES E REGISTE VALORES PARA: \"BONOS\",\"ATRASO DE QUOTA\" E \"ATRASO DE JUROS\".");
			 return "redirect:/credito-form";
		}
		
		
		flash.addAttribute("success", "CRÉDITO registado com sucesso!");
		creditoRepository.save(credito);
		return "redirect:/credito-form";
	}
	
	@GetMapping("credito/excluir/{id}")
	public String Save(@PathVariable("id") int id, RedirectAttributes flash) {
		flash.addAttribute("success", "CRÉDITO excluido da lista!");
		creditoRepository.deleteById(id);
		return "redirect:/credito-form";
	}
	
	
	@GetMapping("pedido-credito")
	public String PedidoCredito(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		model.addAttribute("assunto",  "LISTA DE CRÉDITOS");
		model.addAttribute("creditos",creditoRepository.findAll());
		return "credito/pedido-credito";
	}
	
	
	
}
