package com.bolsadeideias.springboot.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsadeideias.springboot.app.modell.Usuario;
import java.lang.String;
import java.util.List;

public interface UserRepository extends JpaRepository<Usuario, Integer>{

	List<Usuario>  findByUsernameAndPassword(String username, String Senha);
    Usuario findByBi(String bi);
	Usuario findByUsername(String username);
}
