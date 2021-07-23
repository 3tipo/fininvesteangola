package com.bolsadeideias.springboot.app.controll;

import java.util.Optional;
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
import com.bolsadeideias.springboot.app.modell.PedidodeCredito;
import com.bolsadeideias.springboot.app.repo.CreditoRepository;
import com.bolsadeideias.springboot.app.repo.FichadoSocioRepository;
import com.bolsadeideias.springboot.app.repo.JuroMembroGestaoRepository;
import com.bolsadeideias.springboot.app.repo.PedidodeCreditoRepository;
/*verificar a entidade credto e pedido creddito*/

@Controller
@RequestMapping("/")

@SessionAttributes(value = { "pedidodeCredito", "credito" })
public class PedidodeCreditoController {

	@Autowired
	private PedidodeCreditoRepository pedido;
	@Autowired
	private CreditoRepository creditoRepository;
	
	@Autowired
	private FichadoSocioRepository fsr;
	@Autowired
	private JuroMembroGestaoRepository bonorepo;

	@GetMapping("pedido-form/{id}")
	public String GerirForm(PedidodeCredito pedidodeCredito, @PathVariable("id") int id, Model model,
			SessionStatus sesStatus, RedirectAttributes flash) {
		Credito credito = creditoRepository.findById(id).get();

		pedidodeCredito.setTipodecredito(credito.getTipodecredito());
		pedidodeCredito.setTipodecliente(credito.getTipodecliente());
		pedidodeCredito.setIntervalocred(credito.getIntervalodevalor());
		pedidodeCredito.setPrazo(credito.getPrazodereembolso());
		pedidodeCredito.setAplicaveljuro(credito.getJuroaplicavel());
		pedidodeCredito.setTaxaincumprimento(credito.getTaxadeincumprimento());

		model.addAttribute("titulo", "REGISTAR PEDIDO");
		model.addAttribute("assunto", "LISTA DE PEDIDOS CRÉDITOS");
		// model.addAttribute("tipocredito",credito.getTipodecredito());
		// model.addAttribute("tipocliente",credito.getTipodecliente());
		// model.addAttribute("intervalo",credito.getIntervalodevalor());
		// model.addAttribute("reembolso",credito.getPrazodereembolso());
		// model.addAttribute("juro",credito.getJuroaplicavel());

		model.addAttribute("credito", credito);

		model.addAttribute("pedidodeCredito", pedidodeCredito);
		// sesStatus.setComplete();
		return "pedidodecredito/pedido-form";
	}

	@PostMapping("/pedido/save")
	public String SavePedideCredito(Credito credito, PedidodeCredito pedidodeCredito, Model model,
			SessionStatus sesStatus, RedirectAttributes flash) {

		
		
		pedidodeCredito.setBonificado("N");
		pedidodeCredito.setTipodecredito(credito.getTipodecredito());
		pedidodeCredito.setTipodecliente(credito.getTipodecliente());
		pedidodeCredito.setIntervalocred(credito.getIntervalodevalor());
		pedidodeCredito.setPrazo(credito.getPrazodereembolso());
		pedidodeCredito.setAplicaveljuro(credito.getJuroaplicavel());
		pedidodeCredito.setTaxaincumprimento(credito.getTaxadeincumprimento());
		if (pedidodeCredito.getBisocio().length() <= 0) {
			pedidodeCredito.setBisocio("NENHUM INTERMEDIARIO");
		}
		
		pedidodeCredito.setId(0);
		//pedido.save(pedidodeCredito);
		pedido.save(pedidodeCredito);
		model.addAttribute("pedidos", pedido.findAll());
		model.addAttribute("assunto", "LISTA DE CREDITOS");
		sesStatus.setComplete();
		flash.addFlashAttribute("success", "PEDIDO PROCESSADO COM SUCESSO!");
		return "redirect:/pedidos";
	}

	@PostMapping("pedido/alterar")
	public String AlterPedideCredito(PedidodeCredito pedidodeCredito, Model model, SessionStatus sesStatus,
			RedirectAttributes flash) {

		pedido.save(pedidodeCredito);
		sesStatus.setComplete();
		flash.addFlashAttribute("success",
				"PEDIDO ALTERADO COM SUCESSO!" + pedidodeCredito.getIdentificacao().primeironome);
		return "redirect:/pedidos";
	}

	@GetMapping("pedidos")
	public String pedidos(Model model, SessionStatus sesStatus, RedirectAttributes flash) {

		model.addAttribute("pedidos", pedido.findAll());
		model.addAttribute("assunto", "DETALHES E ALTERAR ESTADO OU APLICAR MULTA");
		sesStatus.setComplete();
		return "pedidodecredito/lista-pedidos";
	}

	@GetMapping("detalhes/{id}")
	public String detalhes(@PathVariable("id") int id, Model model, SessionStatus sesStatus, RedirectAttributes flash) {

		PedidodeCredito pedidodeCredito =pedido.findById(id).orElse(null);
		if(pedidodeCredito.getEstado().equalsIgnoreCase("REEMBOLSADOCOMMULTA")
				|| pedidodeCredito.getEstado().equalsIgnoreCase("REEMBOLSADOSEMMULTA")) {
			flash.addFlashAttribute("error", "NÃO É PERMITIDO ALTERAR PEDIDO EM ESTADO REEBOLSADO.");
			return "redirect:/pedidos";
		}
		
		model.addAttribute("pedidodeCredito",pedidodeCredito );
		model.addAttribute("assunto", "LISTA DE CREDITOS ATRIBUIDOS");
		return "pedidodecredito/detalhes-pedido";
	}

}
