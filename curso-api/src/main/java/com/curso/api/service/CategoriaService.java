package com.curso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.curso.api.model.Categoria;
import com.curso.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria update(Long id, Categoria categoria){
		Categoria categoriaSalva = categoriaRepository.findById(id).orElse(null);

		if (categoriaSalva == null)
			throw new EmptyResultDataAccessException(1);

		BeanUtils.copyProperties(categoria, categoriaSalva, "id");

		return categoriaRepository.save(categoriaSalva);
	}
}
