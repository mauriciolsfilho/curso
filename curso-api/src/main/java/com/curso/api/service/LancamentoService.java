package com.curso.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.curso.api.model.Lancamento;
import com.curso.api.repository.LancamentoRepository;

import antlr.collections.List;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	
	public Lancamento findLancamentoById(Long id){
		Lancamento lancamentoSalvo = lancamentoRepository.findById(id).orElse(null);

		if (lancamentoSalvo == null)
			throw new EmptyResultDataAccessException(1);

		return lancamentoSalvo;
	}

	public Lancamento listar() {
		List lancamentos = lancamentoRepository.findAll();
	}
}
