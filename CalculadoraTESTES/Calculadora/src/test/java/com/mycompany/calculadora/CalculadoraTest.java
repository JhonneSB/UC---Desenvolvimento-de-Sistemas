/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.calculadora;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author carlos_js_santos
 */
public class CalculadoraTest {
    
    
    
    public CalculadoraTest() {
    }
    @Test
    void testSoma(){
        Calculadora c = new Calculadora();
        Assertions.assertEquals(5, c.soma(2, 3));
    }
    
    @Test
    void testSubtracao(){
        Calculadora c = new Calculadora();
        Assertions.assertEquals(-1, c.subtrair(2, 3));
    
    }
    
    @Test
    void testMultiplicacao(){
        Calculadora c = new Calculadora();
        Assertions.assertEquals(6, c.multiplicacao(2, 3));
    }
    
    @Test
    void testDivisao(){
        Calculadora c = new Calculadora();
        Assertions.assertEquals(6, c.divisao(12, 2));
    }
    
    @Test
    void testDivisaoPorZero(){
        Calculadora c = new Calculadora();
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {c.divisao (3,0);
        
        });
        Assertions.assertEquals("Divisão por zero não é permitida.", exception.getMessage());
    }
  
}
