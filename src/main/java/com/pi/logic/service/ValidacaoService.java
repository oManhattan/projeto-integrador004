package com.pi.logic.service;

import java.util.Arrays;
import java.util.InputMismatchException;

import org.springframework.stereotype.Component;

@Component
public class ValidacaoService {

	private String removeSpecialCharacterFromDocument(String document) {
		return document.replace(".", "").replace("-", "").trim();
	}

	private int[] stringToIntegerArray(String document) {
		return Arrays.asList(document.split("")).stream().mapToInt((str) -> Integer.parseInt(str)).toArray();
	}

	public boolean CPFisValid(String cpf) throws Exception {

		String formattedCPF = removeSpecialCharacterFromDocument(cpf);

		if (formattedCPF.length() != 11) {
			throw new Exception("CPF deve conter 11 números.");
		}

		for (int i = 0; i < 10; i++) {
			String dummyCPF = String.format("%d", i).repeat(11);
			if (cpf.equals(dummyCPF)) {
				throw new Exception("CPF inválido 1");
			}
		}

		int[] cpfArr = stringToIntegerArray(formattedCPF);

		for (int i = 10; i <= 11; i++) {
			int sum = 0, resto = 0;
			for (int j = i; j >= 2; j--) {
				sum += cpfArr[i - j] * j;
			}
			resto = ((sum * 10) % 11) == 10 ? 0 : (sum * 10) % 11;
			if (cpfArr[i - 1] != resto) {
				throw new Exception("CPF inválido.");
			}
		}

		return true;
	}

	public boolean CNPJisValid(String cnpj) throws Exception {

		String formattedCNPJ = removeSpecialCharacterFromDocument(cnpj);

		if (cnpj.length() != 14) {
			throw new Exception("CNPJ deve conter 14 números");
		}

		for (int i = 0; i <= 9; i++) {
			String dummyDoc = String.format("%d", i).repeat(14);
			if (formattedCNPJ.equals(dummyDoc)) {
				throw new Exception("CNPJ Inválido.");
			}
		}

		int[] cnpjArr = stringToIntegerArray(formattedCNPJ);

		for (int i = 11; i <= 12; i++) {
			int soma = 0, resto = 0;
			for (int j = i; j >= 2; j++) {
				
			}
		}

		return true;
	}

	public boolean validaCPF(String cpf) {

		if (cpf.equals("00000000000") ||
				cpf.equals("11111111111") ||
				cpf.equals("22222222222") || cpf.equals("33333333333") ||
				cpf.equals("44444444444") || cpf.equals("55555555555") ||
				cpf.equals("66666666666") || cpf.equals("77777777777") ||
				cpf.equals("88888888888") || cpf.equals("99999999999") ||
				(cpf.length() != 11))
			return (false);

		char dig10, dig11;
		int soma, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			soma = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do cpf em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (cpf.charAt(i) - 48);
				soma = soma + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (soma % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			soma = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				soma = soma + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (soma % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}

	}

	public boolean validaCNPJ(String Cnpj) {
		// considera-se erro Cnpj's formados por uma sequencia de numeros iguais
		if (Cnpj.equals("00000000000000") || Cnpj.equals("11111111111111") ||
				Cnpj.equals("22222222222222") || Cnpj.equals("33333333333333") ||
				Cnpj.equals("44444444444444") || Cnpj.equals("55555555555555") ||
				Cnpj.equals("66666666666666") || Cnpj.equals("77777777777777") ||
				Cnpj.equals("88888888888888") || Cnpj.equals("99999999999999") ||
				(Cnpj.length() != 14))
			return (false);

		char dig13, dig14;
		int soma, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			soma = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do Cnpj em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (Cnpj.charAt(i) - 48);
				soma = soma + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = soma % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			soma = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (Cnpj.charAt(i) - 48);
				soma = soma + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = soma % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos informados.
			if ((dig13 == Cnpj.charAt(12)) && (dig14 == Cnpj.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

}
