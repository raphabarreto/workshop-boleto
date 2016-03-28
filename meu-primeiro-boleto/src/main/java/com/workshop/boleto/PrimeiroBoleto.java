package com.workshop.boleto;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;

public class PrimeiroBoleto {

	public static void main(String[] args) {

		// Cedente
		Cedente cedente = new Cedente("Norte Cargas Transportes Ltda", "55.851.919/0001-80");

		// Sacado
		Sacado sacado = new Sacado("Raphael Guilherme Barreto");

		// Endereço do sacado
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.SP);
		endereco.setLocalidade("São Paulo");
		endereco.setCep(new CEP("02728-110"));
		endereco.setBairro("Vila Rica");
		endereco.setLogradouro("Rua Ovídio José Antônio Santana");
		endereco.setNumero("108");

		sacado.addEndereco(endereco);

		// Criando o título
		ContaBancaria contaBancaria = new ContaBancaria(BancosSuportados.BANCO_ITAU.create());
		contaBancaria.setAgencia(new Agencia(9283, "0"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(17586, "8"));
		contaBancaria.setCarteira(new Carteira(8));

		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("101010");
		titulo.setNossoNumero("12345678");
		titulo.setDigitoDoNossoNumero("P");

		titulo.setValor(BigDecimal.valueOf(650.99));
		titulo.setDataDoDocumento(new Date());

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 3, 28);
		titulo.setDataDoVencimento(calendar.getTime());
		
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		
		titulo.setAceite(Aceite.N);
		
		// Dados do boleto
		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("Pagar preferencialmente no Itaú");
		boleto.setInstrucaoAoSacado("Evite multas e juros");
		
		boleto.setInstrucao1("Pague em dia as suas contas");
		boleto.setInstrucao2("Não aceitar após a data de vencimento");
		
		BoletoViewer boletoViewer = new BoletoViewer(boleto);
		File arquivoPdf = boletoViewer.getPdfAsFile("meu-primeiro-boleto.pdf");
		
		mostraNaTela(arquivoPdf);

	}

	private static void mostraNaTela(File arquivo) {
		Desktop desktop = Desktop.getDesktop();
		
		try {
			desktop.open(arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}