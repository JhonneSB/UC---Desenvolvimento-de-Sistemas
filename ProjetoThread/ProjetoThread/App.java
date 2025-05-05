class MinhaThread extends Thread {
    public void run() {
        // Código a ser executado pela thread
        for (int i = 0; i < 5; i++) {
            System.out.println("Executando " + Thread.currentThread().getName() + " - Valor do contador: " + i);
            try {
                Thread.sleep(1000); // pausa a thread por 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        MinhaThread thread_1 = new MinhaThread();
        thread_1.start(); // inicia a thread 1 e chama o método run()

        MinhaThread thread_2 = new MinhaThread();
        thread_2.start(); // inicia a thread 2 e chama o método run()

        System.out.println("Programa encerrado com sucesso!");
    }
    
}
