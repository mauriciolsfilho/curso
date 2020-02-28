package com.curso.api.resource;

import com.curso.api.event.RecursoCriadoEvent;
import com.curso.api.exceptionhandler.CursoapiExceptionHandler.Error;
import com.curso.api.model.Lancamento;
import com.curso.api.repository.filter.LancamentoFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.curso.api.service.LancamentoService;
import com.curso.api.service.exception.PessoaInexistenteOuInativaException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
    private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter){
		return lancamentoService.listarLancamento(lancamentoFilter);
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

	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		
		String usermessage = messageSource.getMessage("MSG004", null, LocaleContextHolder.getLocale());
		String developermessage = ex.toString();
	
		List<Error> errors = Arrays.asList(new Error(usermessage, developermessage));
	
		return ResponseEntity.badRequest().body(errors);
	}
	/*
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Long id){
		lancamentoRepository.deleteById(id);
	}*/
}
