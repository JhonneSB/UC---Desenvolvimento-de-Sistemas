import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) throws Exception {
        try {
            Socket socket = new Socket("10.74.241.66", 1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensagem = "Carlos";
            out.println(mensagem);

            String resposta = in.readLine();
            System.out.println("Resposta do servidor" + resposta);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
