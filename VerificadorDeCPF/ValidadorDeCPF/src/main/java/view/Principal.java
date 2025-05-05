package view;


import javax.swing.*;
import controller.VerificadorCPF;

public class Principal {

    public static void main(String[] args) {
        String CPF = JOptionPane.showInputDialog("Informe um CPF:");

        if (CPF != null && VerificadorCPF.isCPF(CPF)) {
            String resultado = VerificadorCPF.imprimeCPF(CPF);
            JOptionPane.showMessageDialog(null, "CPF válido: " + resultado);
        } else {
            JOptionPane.showMessageDialog(null, "Erro, CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
