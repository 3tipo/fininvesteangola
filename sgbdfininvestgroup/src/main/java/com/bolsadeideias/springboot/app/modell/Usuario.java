package com.bolsadeideias.springboot.app.modell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;
	
	private String bi;
	private int socioid;
	private String username;
	private String password;
	private boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL,
			 mappedBy = "usuario", orphanRemoval = true)
	private List<NiveldeAcesso> niveisdeacessos= new ArrayList<>();
	public Usuario() {
		
	}
	
	
	public void addRol(NiveldeAcesso niveldeacesso) {
		 this.niveisdeacessos.add(niveldeacesso);
		 niveldeacesso.setUsuario(this);
    }
	
	
	
	
	
	
	
	
	
	
	

}
