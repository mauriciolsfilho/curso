package com.curso.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.curso.api.model.Lancamento;
import com.curso.api.model.Pessoa;
import com.curso.api.repository.LancamentoRepository;
import com.curso.api.repository.PessoaRepository;
import com.curso.api.repository.filter.LancamentoFilter;
import com.curso.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Page<Lancamento> listarLancamento(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

	public Lancamento findLancamentoById(Long id){
		Lancamento lancamentoSalvo = lancamentoRepository.findById(id).orElse(null);

		if (lancamentoSalvo == null)
			throw new EmptyResultDataAccessException(1);

		return lancamentoSalvo;
	}

	public Lancamento create(Lancamento lancamento){

		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getId()).orElse(null);

		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
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
