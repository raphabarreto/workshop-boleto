package com.workshop.cobranca.util.boleto.bopepo;

import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.workshop.cobranca.model.Cedente;
import com.workshop.cobranca.model.Cobranca;
import com.workshop.cobranca.model.ContaBancaria;
import com.workshop.cobranca.model.Sacado;
import com.workshop.cobranca.util.GeradorDigitoVerificador;
import com.workshop.cobranca.util.boleto.EmissorBoleto;

public class BopepoEmissorBoletoTest {

	private EmissorBoleto emissorBoleto;

	@Before
	public void init() {
		GeradorDigitoVerificador geradorDigitoVerificador = new GeradorDigitoVerificador();
		emissorBoleto = new BopepoEmissorBoleto(geradorDigitoVerificador);
	}

	@Test
	public void deve_gerar_boleto_em_arquivo() throws Exception {
		Cedente cedenteSistema = new Cedente();
		cedenteSistema.setNome("Norte Cargas Transportes Ltda");
		cedenteSistema.setCnpj("55.851.919/0001-80");
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setAgencia(1111);
		contaBancaria.setDigitoAgencia("0");
		contaBancaria.setNumero(2222);
		contaBancaria.setDigitoConta("9");
		contaBancaria.setCodigoCarteira(6);
		cedenteSistema.setContaBancaria(contaBancaria);
		
		Cobranca cobrancaSistema = new Cobranca();
		cobrancaSistema.setCodigo(1L);
		cobrancaSistema.setDataVencimento(new Date());
		cobrancaSistema.setValor(new BigDecimal("200.22"));
		
		Sacado sacado = new Sacado();
		sacado.setNome("Raphael Guilherme Barreto");
		cobrancaSistema.setSacado(sacado);
		
		
		File boleto = this.emissorBoleto.gerarBoletoEmArquivo("boletoTeste1.pdf", cedenteSistema, cobrancaSistema);
		Desktop desktop = Desktop.getDesktop();
		desktop.open(boleto);
	}
}