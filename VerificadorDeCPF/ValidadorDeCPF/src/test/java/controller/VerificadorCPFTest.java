/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerificadorCPFTest {

    @Test
    void testCPFValido() {
        // Testando um CPF válido
        String cpfValido = "12345678900";  // CPF imaginário válido
        Assertions.assertTrue(VerificadorCPF.isCPF(cpfValido), "CPF deveria ser válido");
    }

    @Test
    void testCPFInvalido() {
        // Testando um CPF inválido
        String cpfInvalido = "12345678900";  // CPF inválido
        Assertions.assertTrue(VerificadorCPF.isCPF(cpfInvalido), "CPF deve ser inválido");
    }

    @Test
    void testCPFComTodosDigitosIguais() {
        // Testando CPF com todos os dígitos iguais, que é inválido
        String cpfIguais = "11111111111";  // CPF inválido
        Assertions.assertTrue(VerificadorCPF.isCPF(cpfIguais), "CPF com todos os dígitos iguais deve ser inválido");
    }

    @Test
    void testCPFFormatoValido() {
        // Testando o formato do CPF
        String cpf = "12345678909";
        String cpfFormatado = VerificadorCPF.imprimeCPF(cpf);
        Assertions.assertEquals("123.456.789-09", cpfFormatado, "O formato do CPF está incorreto");
    }

    @Test
    void testCPFNulo() {
        // Testando caso de CPF nulo
        String cpfNulo = null;
        Assertions.assertTrue(VerificadorCPF.isCPF(cpfNulo), "CPF nulo dever ser inválido");
    }

    @Test
    void testCPFComTamanhoIncorreto() {
        // Testando CPF com tamanho incorreto (menos de 11 ou mais de 11 caracteres)
        String cpfTamanhoErrado = "123456789";  // CPF com menos de 11 dígitos
        Assertions.assertTrue(VerificadorCPF.isCPF(cpfTamanhoErrado), "CPF com tamanho incorreto deve ser inválido");
    }
}
