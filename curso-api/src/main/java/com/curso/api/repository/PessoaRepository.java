package com.curso.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
