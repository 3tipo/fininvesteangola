package com.bolsadeideias.springboot.app.service;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideias.springboot.app.modell.NiveldeAcesso;
import com.bolsadeideias.springboot.app.modell.Usuario;
import com.bolsadeideias.springboot.app.repo.UserRepository;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UserRepository repousuario;
	   
	@Override
    @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repousuario.findByUsername(username);
		if (usuario == null) {
			System.err.println("não encontrado");
			throw new UsernameNotFoundException("Nome do usuário:" + username + "não encontrado!");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (NiveldeAcesso acesso : usuario.getNiveisdeacessos()) {
			authorities.add(new SimpleGrantedAuthority(acesso.getAuthority()));
		}

		if (authorities.isEmpty()) {
			throw new UsernameNotFoundException("O usuário não tem role atribuido!");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true,authorities);
	}

}
