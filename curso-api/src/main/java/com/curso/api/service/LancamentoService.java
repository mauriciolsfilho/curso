package com.curso.api.service;

import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curso.api.model.Lancamento;
import com.curso.api.repository.LancamentoRepository;

import java.util.List;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public List<Lancamento> listarLancamento(){
		return lancamentoRepository.findAll();
	}

	public Lancamento findLancamentoById(Long id){
		Lancamento lancamentoSalvo = lancamentoRepository.findById(id).orElse(null);

		if (lancamentoSalvo == null)
			throw new EmptyResultDataAccessException(1);

		return lancamentoSalvo;
	}

	public Lancamento create(Lancamento lancamento){

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);

		return lancamentoSalvo;
	}

	public Lancamento update(Long id, Lancamento lancamento){

		Lancamento lancamentoSalvo = findLancamentoById(id);

		if (lancamentoSalvo == null)
			throw new EmptyResultDataAccessException(1);

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");

		return lancamentoRepository.save(lancamentoSalvo);
	}

}
