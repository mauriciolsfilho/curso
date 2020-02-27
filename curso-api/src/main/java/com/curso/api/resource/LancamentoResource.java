package com.curso.api.resource;

import com.curso.api.event.RecursoCriadoEvent;
import com.curso.api.model.Lancamento;
import com.curso.api.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.curso.api.repository.LancamentoRepository;
import com.curso.api.service.LancamentoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
    private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<?> listar(){
		List<Lancamento> lancamentos = lancamentoService.listarLancamento();

		return !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> findLancamentoById(@PathVariable  Long id){
		Lancamento lancamento = lancamentoService.findLancamentoById(id);

		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Lancamento> criar(@RequestBody @Valid Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = lancamentoService.create(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long id, @Valid @RequestBody Lancamento lancamento){

		Lancamento lancamentoSalvo = lancamentoService.update(id, lancamento);

		return ResponseEntity.ok(lancamentoSalvo);
	}

	/*
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Long id){
		lancamentoRepository.deleteById(id);
	}*/
}
