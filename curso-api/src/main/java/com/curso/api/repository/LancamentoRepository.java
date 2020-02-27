package com.curso.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	
}
