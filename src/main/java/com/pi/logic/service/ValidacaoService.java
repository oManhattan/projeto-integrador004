package com.pi.logic.service;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class ValidacaoService {

	private String removeSpecialCharacterFromDocument(String document) {
		return document.replace(".", "").replace("-", "").replace("/", "").trim();
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

		if (formattedCNPJ.length() != 14) {
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
			int soma = 0, resto = 0, peso = 2;
			for (int j = i; j >= 0; j--) {
				soma += cnpjArr[j] * peso;
				peso = (peso + 1) > 9 ? 2 : peso + 1;
			}
			resto = 11 - (soma % 11);
			resto = (resto > 9) ? 0 : resto;
			if (cnpjArr[i + 1] != resto) {
				throw new Exception("CNPJ inválido.");
			}
		}

		return true;
	}
}
