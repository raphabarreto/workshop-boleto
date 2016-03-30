package com.workshop.cobranca.util.boleto;

import java.io.File;
import java.io.Serializable;

import com.workshop.cobranca.model.Cedente;
import com.workshop.cobranca.model.Cobranca;

public interface EmissorBoleto extends Serializable  {

	public byte[] gerarBoleto(Cedente cedenteSistema, Cobranca cobrancaSistema);

	public File gerarBoletoEmArquivo(String arquivo, Cedente cedenteSistema, Cobranca cobrancaSistema);
}