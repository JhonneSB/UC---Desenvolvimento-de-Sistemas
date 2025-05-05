package controller;

public class VerificadorCPF {

	public static boolean isCPF(String CPF) {
		if (CPF == null || CPF.length() != 11 || !CPF.matches("\\d+")) {
			return false;
		}

		// Impede CPFs com todos os dígitos iguais
		if (CPF.matches("(\\d)\\1{10}")) {
			return false;
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			// Cálculo do 1º dígito verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = CPF.charAt(i) - '0'; // Converte char para número
				sm += (num * peso);
				peso--;
			}

			r = 11 - (sm % 11);
			dig10 = (r == 10 || r == 11) ? '0' : (char) (r + '0');

			// Cálculo do 2º dígito verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = CPF.charAt(i) - '0';
				sm += (num * peso);
				peso--;
			}

			r = 11 - (sm % 11);
			dig11 = (r == 10 || r == 11) ? '0' : (char) (r + '0');

			// Verifica se os dígitos calculados conferem com os informados
			return (dig10 == CPF.charAt(9) && dig11 == CPF.charAt(10));
		} catch (Exception e) {
			return false;
		}
	}

	public static String imprimeCPF(String CPF) {
		return CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
				CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
