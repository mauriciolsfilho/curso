package com.curso.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.curso.api.model.Categoria;
import com.curso.api.repository.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> listarCategoria(){
		return categoriaRepository.findAll();
	}

	public Categoria findCategoriaById(Long id){
		Categoria categoria = categoriaRepository.findById(id).orElse(null);

		if (categoria == null)
			throw new EmptyResultDataAccessException(1);

		return categoria;
	}

	public Categoria create(Categoria categoria){
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		return categoriaSalva;
	}

	public Categoria update(Long id, Categoria categoria){
		Categoria categoriaSalva = categoriaRepository.findById(id).orElse(null);

		if (categoriaSalva == null)
			throw new EmptyResultDataAccessException(1);

		BeanUtils.copyProperties(categoria, categoriaSalva, "id");

		return categoriaRepository.save(categoriaSalva);
	}
}
