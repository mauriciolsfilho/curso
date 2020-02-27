package com.curso.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.api.repository.LancamentoRepository;
import com.curso.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository; 

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
    private ApplicationEventPublisher publisher;

	
}
