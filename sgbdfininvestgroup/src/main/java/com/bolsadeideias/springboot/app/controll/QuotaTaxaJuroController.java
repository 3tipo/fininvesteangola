package com.bolsadeideias.springboot.app.controll;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideias.springboot.app.modell.FichadoSocio;
import com.bolsadeideias.springboot.app.modell.JuroMembroGestao;
import com.bolsadeideias.springboot.app.modell.QuotaGestao;
import com.bolsadeideias.springboot.app.modell.TaxaGestao;
import com.bolsadeideias.springboot.app.repo.JuroMembroGestaoRepository;
import com.bolsadeideias.springboot.app.repo.QuotaGestaoRepository;
//import com.bolsadeideias.springboot.app.repo.Quotarepo;
import com.bolsadeideias.springboot.app.repo.TaxaGestaoRepository;
//import com.bolsadeideias.springboot.app.repo.Taxarepo;



@Controller
@RequestMapping("/")
public class QuotaTaxaJuroController {
      
	@Autowired
	private TaxaGestaoRepository taxarepo;
	@Autowired
	private QuotaGestaoRepository quotarepo;
	
	@Autowired
	private JuroMembroGestaoRepository  jurorepo;
	
	@GetMapping("gerirtaxaquota-form")
	public String getform(Model model, SessionStatus sesStatus,RedirectAttributes flash) {
		
		TaxaGestao taxa = new TaxaGestao();
		QuotaGestao quota = new QuotaGestao();
		JuroMembroGestao juro = new JuroMembroGestao();
		
		model.addAttribute("titulo",  "REGISTAR QUOTA");
		model.addAttribute("titulo1", "REGISTAR TAXA");
		model.addAttribute("titulo2", "REGISTAR PERCENTAGEM DE JUROS POR ATRAZO DE PAGAMENTO");
		model.addAttribute("quota", quota);
	   
		List<QuotaGestao>   listquota = new ArrayList<QuotaGestao>();
	    listquota= (List<QuotaGestao>) quotarepo.findAll();
	    
		model.addAttribute("quotas", listquota);
		model.addAttribute("taxa", taxa);
		model.addAttribute("taxas", taxarepo.findAll());
		
		model.addAttribute("juro", juro);
		model.addAttribute("juros", jurorepo.findAll());
		return "quotataxajuro/quotataxajuro-form";
		
	}
	
	@PostMapping("quota/save")
	public String savequota(QuotaGestao quota,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		
		quotarepo.save(quota);
		flash.addFlashAttribute("success", "QUOTA SALVA COM SUCESSO!");
		
		return "redirect:/gerirtaxaquota-form";
	}
	
	
	@GetMapping("quota/excluir/{id}")
	public String deletequota(@PathVariable("id") int id,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		
		quotarepo.deleteById(id);
		flash.addFlashAttribute("success", "QUOTA ELIMINADO COM SUCESSO!");
		
		return "redirect:/gerirtaxaquota-form";
	}
	
	
	
	
	@PostMapping("taxa/save")
	public String savetaxa(TaxaGestao taxa,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		
		taxarepo.save(taxa);
		flash.addFlashAttribute("success", "TAXA SALVA COM SUCESSO!");
		return "redirect:/gerirtaxaquota-form";
	}
	
	
	
	//taxa/excluir/{id}
	
	@GetMapping("taxa/excluir/{id}")
	public String deletetaxa(@PathVariable("id") int id,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		
		taxarepo.deleteById(id);
		flash.addFlashAttribute("success", "TAXA ELIMINADA COM SUCESSO!");
		return "redirect:/gerirtaxaquota-form";
	}
	
	
	@PostMapping("juro/save")
	public String savejuromembro(JuroMembroGestao juro,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		jurorepo.save(juro);
		flash.addFlashAttribute("success", "JUROS SALVA COM SUCESSO!");
		return "redirect:/gerirtaxaquota-form";
	}
	
	@GetMapping("juro/excluir/{id}")
	public String deletejuromembro(@PathVariable("id") int id,Model model, SessionStatus sesStatus,RedirectAttributes flash){
		jurorepo.deleteById(id);
		flash.addFlashAttribute("success", "JUROS ELIMINADO COM SUCESSO!");
		return "redirect:/gerirtaxaquota-form";
	}
}
