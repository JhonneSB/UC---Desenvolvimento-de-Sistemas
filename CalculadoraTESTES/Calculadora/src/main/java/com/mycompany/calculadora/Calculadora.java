/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadora;

/**
 *
 * @author carlos_js_santos
 */
public class Calculadora {
    
    public double soma(double a, double b){
        return a+b;
    }
    
    public double subtrair (double a, double b){
        return a-b;   
    }
    
    public double multiplicacao (double a, double b){
        return a*b;
    }
    
    public double divisao (double a, double b){
        if (b == 0) {
            throw new IllegalArgumentException(" Erro: Divisão por zero!");
    }
        return a/b;
    }
    
    public double potencia(double base, double expoente) {
        return Math.pow(base, expoente);
   }
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        double resultadoSoma = calculadora.soma(1, 2); //resultado 3
        double resultadoSubtracao = calculadora.subtrair(1, 2); // resultado -1
        double resultadoMultiplicacao = calculadora.multiplicacao(1, 2); // resultado 2
        double resultadoDivisao = calculadora.divisao(1, 2); // resultado 0,5
        
        if (resultadoSoma == 3){
            System.out.println("Teste soma funcionou, pois o resultado era 3");
        } else {
            System.out.println("Teste soma funcionou, pois o resultado era 3");
        }
        if (resultadoSoma == -1){
            System.out.println("Teste soma funcionou, pois o resultado era -1");
        } else {
            System.out.println("Teste soma funcionou, pois o resultado era -1");
        }
        System.out.println("Hello World!");
    }
}
