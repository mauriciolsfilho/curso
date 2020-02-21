package com.curso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.curso.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
}
