package com.curso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.curso.api.model.Pessoa;
import com.curso.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa update(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = findPessoaById(id);

		if (pessoaSalva == null)
			throw new EmptyResultDataAccessException(1);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");

		return pessoaRepository.save(pessoaSalva);
	}

	public Pessoa findPessoaById(Long id){
		Pessoa pessoaSalva = pessoaRepository.findById(id).orElse(null);

		if (pessoaSalva == null)
			throw new EmptyResultDataAccessException(1);

		return pessoaSalva;
	}

	public void desativarPessoa(Long id, Boolean ativo){
		Pessoa pessoaSalva = findPessoaById(id);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
}
