package com.bolsadeideias.springboot.app.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsadeideias.springboot.app.auth.handler.LoginSuccessHandler;
import com.bolsadeideias.springboot.app.service.UsuarioService;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	@Autowired
	private UsuarioService userDetailsService;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {	
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  
		http.authorizeRequests().antMatchers("/home","/criarconta","/concessaodocredito","/sobrenos","/assets/**","/css/**","/img/**","/js/**").permitAll() 
		.antMatchers("/socios").hasAnyRole("ADMIN")
		.antMatchers("/socio-form").hasAnyRole("ADMIN")
		.antMatchers("/socios/save").hasAnyRole("ADMIN")
		.antMatchers("/socios").hasAnyRole("ADMIN")
		.antMatchers("/savetaxa").hasAnyRole("ADMIN")
		.antMatchers("/savequota").hasAnyRole("ADMIN")
		.antMatchers("/socio/pagarquota/{id}").hasAnyRole("ADMIN")
		.antMatchers("/socio/pagartaxa/{id}").hasAnyRole("ADMIN")
		
		.antMatchers("/quota/edit/{id}").hasAnyRole("ADMIN")
		.antMatchers("/socio/istorico/{id}").hasAnyRole("ADMIN")
		.antMatchers("/socio/dividas/{id}").hasAnyRole("ADMIN")
		.antMatchers("/socio/editar/{id}").hasAnyRole("ADMIN")
		.antMatchers("/socios/update").hasAnyRole("ADMIN")
		.antMatchers("/pesquisa").hasAnyRole("ADMIN")
		
		.antMatchers("/quota/save").hasAnyRole("ADMIN")
		.antMatchers("/quota/excluir/{id}").hasAnyRole("ADMIN")
		.antMatchers("/taxa/save").hasAnyRole("ADMIN")
		
		.antMatchers("/taxa/excluir/{id}").hasAnyRole("ADMIN")
		.antMatchers("/juro/save").hasAnyRole("ADMIN")
		.antMatchers("/juro/excluir/{id}").hasAnyRole("ADMIN")
		
		.antMatchers("/socios/update").hasAnyRole("ADMIN")
		.antMatchers("/bonos/save").hasAnyRole("ADMIN")
		.antMatchers("/bonos").hasAnyRole("ADMIN")
		.antMatchers("/bonos/ver").hasAnyRole("USER","ADMIN")
		.antMatchers("/credito-form").hasAnyRole("ADMIN")
		
		.antMatchers("/credito/save").hasAnyRole("ADMIN")
		.antMatchers("/pedido-credito").hasAnyRole("ADMIN")
		.antMatchers("/pedido/save").hasAnyRole("USER","ADMIN")
		.antMatchers("/taxa/save").hasAnyRole("ADMIN")
		
		.antMatchers("/detalhes/{id}").hasAnyRole("ADMIN")
		.antMatchers("/credito/excluir/{id}").hasAnyRole("ADMIN")
		.antMatchers("/bonos/editar/{id}").hasAnyRole("USER","ADMIN")
		.antMatchers("/bonos/{id}").hasAnyRole("ADMIN")
		
		
		
		
		
		.antMatchers("/juro/save").hasAnyRole("ADMIN")
		.antMatchers("/pedidos").hasAnyRole("ADMIN")
		.antMatchers("/gerirtaxaquota-form").hasAnyRole("ADMIN")
		.antMatchers("/bonos/editar/{id}").hasAnyRole("ADMIN")
		//.antMatchers("/pedidos").hasAnyRole("ADMIN")
		
		
		
		.antMatchers("/").hasAnyRole("USER","ADMIN")
		.anyRequest().authenticated()
			.and()
			.formLogin()
			.successHandler(successHandler)
			.loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder= passwordEncoder();
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(encoder);
	}
}
