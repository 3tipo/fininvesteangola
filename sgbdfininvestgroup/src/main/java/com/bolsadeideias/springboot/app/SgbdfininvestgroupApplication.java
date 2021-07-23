package com.bolsadeideias.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideias.springboot.app.modell.NiveldeAcesso;
import com.bolsadeideias.springboot.app.modell.Usuario;
import com.bolsadeideias.springboot.app.repo.UserRepository;

@SpringBootApplication
public class SgbdfininvestgroupApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userrepo;

	public static void main(String[] args) {
		SpringApplication.run(SgbdfininvestgroupApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// userrepo.deleteById(1);
		if (userrepo.count() <= 0) {
			for (int i = 0; i < 1; i++) {
				String password = "1234567890";
				Usuario usuario = new Usuario();
				NiveldeAcesso na = new NiveldeAcesso();
				String passwordEncriptada = passwordEncoder.encode(password);
				usuario.setBi("1234567890");
				usuario.setSocioid(0);
				usuario.setPassword(passwordEncriptada);
				usuario.setUsername("root");
				usuario.setEnabled(true);
				na.setAuthority("ROLE_ADMIN");
				usuario.addRol(na);
				userrepo.save(usuario);
			}

		}
	}
}
