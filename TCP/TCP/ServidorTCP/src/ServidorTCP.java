import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Servidor TCP esperando conexão...");

            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String mensagem = in.readLine();
            System.out.println("Mensagem recebida: " + mensagem);

            out.println("Mensagem recebida com sucesso!");

            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
