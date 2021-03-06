package com.workshop.cobranca.controller;

import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import com.workshop.cobranca.model.Cedente;
import com.workshop.cobranca.model.Cobranca;
import com.workshop.cobranca.model.Sacado;
import com.workshop.cobranca.repository.Cedentes;
import com.workshop.cobranca.service.NovaCobrancaService;
import com.workshop.cobranca.util.boleto.EmissorBoleto;

@Named
@ViewScoped
public class NovaCobrancaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cobranca cobranca;

	@Inject
	private Cedentes cedentes;

	@Inject
	private NovaCobrancaService novaCobrancaService;

	@Inject
	private EmissorBoleto emissorBoleto;

	public void inicializar() {
		cobranca = new Cobranca();
		cobranca.setSacado(new Sacado());
	}

	public void emitir() {
		Cedente cedente = cedentes.porCodigo(1L);
		cobranca = novaCobrancaService.salvar(cobranca);

		byte[] pdf = this.emissorBoleto.gerarBoleto(cedente, cobranca);
		enviarBoleto(pdf);
		
		inicializar();
	}

	private void enviarBoleto(byte[] pdf) {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=boleto" + cobranca.getCodigo() + ".pdf");
		
		try {
			OutputStream output = response.getOutputStream();
			output.write(pdf);
			response.flushBuffer();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao gerar boleto!", e);
		}
		FacesContext.getCurrentInstance().responseComplete();
	}

	public Cobranca getCobranca() {
		return cobranca;
	}
}