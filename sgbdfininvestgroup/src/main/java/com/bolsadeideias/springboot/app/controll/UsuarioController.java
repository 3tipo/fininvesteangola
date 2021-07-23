package com.bolsadeideias.springboot.app.controll;

import java.security.Principal;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideias.springboot.app.modell.FichadoSocio;
import com.bolsadeideias.springboot.app.modell.Pesquisa;
import com.bolsadeideias.springboot.app.modell.Usuario;
import com.bolsadeideias.springboot.app.repo.FichadoSocioRepository;
import com.bolsadeideias.springboot.app.repo.UserRepository;
@Controller
@RequestMapping("/")
public class UsuarioController {

	@Autowired  
	private FichadoSocioRepository fichadoSocioRepository;
	@Autowired
	private UserRepository userepo;
	
	@GetMapping("login")
	public String formlogin(
			@RequestParam(value = "login", required = false) String login,
			@RequestParam(value = "logout", required = false) String logout
			,@RequestParam(value = "error", required = false) String error,
			Model model,Principal principal, SessionStatus sesStatus,RedirectAttributes flash){
		Usuario usuario = new Usuario();
		if(principal != null) {
			flash.addFlashAttribute("error","JÁ INICIOU A CESSÃO ANTERIORMENTE");
				return "redirect:/";
			}
		
		if(error!=null) {
			flash.addFlashAttribute("error","Nome do usuário ou senha incorreta");
			return "redirect:login";
		}
		
		if(logout!=null) {
			flash.addFlashAttribute("success","SESSÃO ENCERRADA COM ÊXITO");
			return "redirect:login";
		}
		
		model.addAttribute("usuario",usuario);
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("criarconta")
	public String formcriarconta(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		Usuario usuario = new Usuario();
		model.addAttribute("usuario",usuario);
		return "criarconta";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("home")
	public String home(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		return "home";
	}
	
	@GetMapping("concessaodocredito")
	public String concessaodocredito(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		return "concessaodocredito";
	}
	
	@GetMapping("sobrenos")
	public String sobrenos(Model model, SessionStatus sesStatus,RedirectAttributes flash){
		return "sobrenos";
	}
	
	
		
	
	
	/*@PostMapping("salvarusuario")
	public String salvarusuario(Usuario usuario, SessionStatus sesStatus,RedirectAttributes flash){
		FichadoSocio fichadoSocio;int size = 0;
		size =	fichadoSocioRepository.socioPorBI(usuario.getBi()).size();
		if(size <= 0) {
		flash.addFlashAttribute("error","NÃO TENS PERMISSÃO PARA CRIAR CONTA, CONSULTE O ADMINISTRADOR");
		return "redirect:/criarconta";
		}
		if(userepo.findByBi(usuario.getBi()).size() > 0) {
			flash.addFlashAttribute("error","JÁ TENS UMA CONTA CRIADA,SOLICITA O ADMINISTRADOR EM CASO DE ESQUECIMENTO DE SENHA.");
			return "redirect:/criarconta";
		}
		
		fichadoSocio = fichadoSocioRepository.socioPornUMERODEBI(usuario.getBi());
		usuario.setSocioid(fichadoSocio.getId());
		userepo.save(usuario);
		flash.addFlashAttribute("success", "CONTA DO SÓCIO CRIADO COM SUCESSO, AGORA PODES ENTRR NO SISTEMA E CONSULTAR OS SEUS VALORES");
		return "redirect:/login";
	}*/
}
