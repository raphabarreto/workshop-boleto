package com.workshop.cobranca.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.workshop.cobranca.model.Cobranca;
import com.workshop.cobranca.model.Status;
import com.workshop.cobranca.repository.Cobrancas;
import com.workshop.cobranca.util.jpa.Transactional;

public class NovaCobrancaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Cobrancas cobrancas;

	@Transactional
	public Cobranca salvar(Cobranca cobranca) {
		cobranca.setStatus(Status.PEDENTE);
		return this.cobrancas.guardar(cobranca);
	}
}