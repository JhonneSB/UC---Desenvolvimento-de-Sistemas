import java.util.List;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class MySwingWorker extends SwingWorker<String, Integer> {
    private final JProgressBar progressBar;
    private final JTextArea textArea;
    private final JButton button;

    public MySwingWorker(JProgressBar progressBar, JTextArea textArea, JButton button) {
        this.progressBar = progressBar;
        this.textArea = textArea;
        this.button = button;
    }

    @Override
    protected String doInBackground() throws Exception {

        for (int i = 0; i <= 100; i++) {
            Thread.sleep(100);
            publish(i);
            setProgress(i);
        }
        return "Tarefa concluída com sucessso!";

    }

    @Override
    protected void process(List<Integer> chunks) {

        for (int chunk : chunks) {
            progressBar.setValue(chunk);
            textArea.append("Progresso " + chunk + "%\n");
        }
    }

    @Override
    protected void done() {
        try {
            String result = get();
            textArea.append(result + "\n");
            button.setEnabled(true);
            button.setText("Iniciar tarefa");
        } catch (Exception e) {
            textArea.append("Erro: " + e.getMessage() + "\n");

        }
    }

}
