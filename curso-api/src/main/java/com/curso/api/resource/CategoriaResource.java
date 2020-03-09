package com.curso.api.resource;

import com.curso.api.event.RecursoCriadoEvent;
import com.curso.api.model.Categoria;
import com.curso.api.model.Pessoa;
import com.curso.api.repository.CategoriaRepository;
import com.curso.api.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<?> listar(){
	    List<Categoria> categorias = categoriaService.listarCategoria();

        return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_CATEGORIA')")
    public ResponseEntity<Categoria> criar(@RequestBody @Valid Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = categoriaService.create(categoria);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public ResponseEntity<Categoria> findById(@PathVariable Long id){
    	Categoria categoria = categoriaService.findCategoriaById(id);

    	return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_REMOVER_CATEGORIA')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
    	categoriaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_CATEGORIA')")
    public ResponseEntity<Categoria> update(@PathVariable Long id,@Valid @RequestBody Categoria categoria){
    	Categoria categoriaSalva = categoriaService.update(id, categoria);

    	return ResponseEntity.ok(categoriaSalva);
    }
}
