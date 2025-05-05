import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LerArquivos implements Runnable {

    private final String filePath;
    private final JTextArea textArea;
    private final JTextField texto;
    private final int tempo;
    private final int totalLinhas;
    private final JProgressBar progressBar;
    private int linesRead;
    int progresso;

    public LerArquivos(String filePath, JTextArea textArea, JTextField text, int tempo, JProgressBar progress) {
        this.filePath = filePath;
        this.textArea = textArea;
        this.texto = text;
        this.tempo = tempo;
        this.totalLinhas = contaTotalLinhas(filePath);
        this.progressBar = progress;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            linesRead = 0;
            while ((line = reader.readLine()) != null) {
                final String currentLine = line;
                linesRead++;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (textArea) {
                            textArea.append(currentLine + "\n");
                        }
                        texto.setText(currentLine);

                        progresso = (int) (((double) linesRead / totalLinhas) * 100);
                        progressBar.setValue(progresso);
                    }
                });
                Thread.sleep(tempo); // Espera 1 segundo antes de ler a próxima linha
            }
        } catch (IOException e) {
            mostraErro("Erro ao ler o arquivo: " + e.getMessage());
        } catch (InterruptedException e) {
            mostraErro("A leitura do arquivo foi interrompida: " + e.getMessage());
        }
    }

    private int contaTotalLinhas(String filePath) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (Exception e) {
            mostraErro("Erro ao contar as linhas do aquivo" + e.getMessage());
        }
        return lineCount;

    }

    private void mostraErro(final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(textArea.getParent(), msg, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
