package com.curso.api.repository.lancamento;

import java.util.List;

import com.curso.api.model.Lancamento;
import com.curso.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
