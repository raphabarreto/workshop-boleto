package com.workshop.cobranca.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.workshop.cobranca.model.Cedente;
import com.workshop.cobranca.model.Cobranca;
import com.workshop.cobranca.model.Sacado;
import com.workshop.cobranca.repository.Cedentes;
import com.workshop.cobranca.service.NovaCobrancaService;

@Named
@ViewScoped
public class NovaCobrancaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cobranca cobranca;
	
	@Inject
	private Cedentes cedentes;
	
	@Inject
	private NovaCobrancaService novaCobrancaService;
	
	public void inicializar(){
		cobranca = new Cobranca();
		cobranca.setSacado(new Sacado());
	}

	public void emitir() {
		Cedente cedente = cedentes.porCodigo(1L);
		cobranca = novaCobrancaService.salvar(cobranca);
	}

	public Cobranca getCobranca() {
		return cobranca;
	}
}