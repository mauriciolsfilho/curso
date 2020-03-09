	package com.curso.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.curso.api.event.RecursoCriadoEvent;
import com.curso.api.model.Pessoa;
import com.curso.api.repository.PessoaRepository;
import com.curso.api.service.PessoaService;


@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;
   
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_PESSOA')")
    public ResponseEntity<?> listar(){
	    List<Pessoa> pessoas = pessoaService.listarPessoa();

        return !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_PESSOA')")
    public ResponseEntity<Pessoa> criar(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response){

    	Pessoa pessoaSalva = pessoaService.create(pessoa);

    	publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

    	return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_PESSOA')")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id){
    	Pessoa pessoa = pessoaService.findPessoaById(id);

    	return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_REMOVER_PESSOA')")
    public void remove(@PathVariable Long id) {
    	pessoaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_PESSOA')")
    public ResponseEntity<Pessoa> update(@PathVariable Long id,@Valid @RequestBody Pessoa pessoa){
    	Pessoa pessoaSalva = pessoaService.update(id, pessoa);
    	
    	return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_DESATIVAR_PESSOA')")
    public void desativarPessoa(@PathVariable Long id, @RequestBody Boolean ativo){
        pessoaService.desativarPessoa(id, ativo);
    }

}
