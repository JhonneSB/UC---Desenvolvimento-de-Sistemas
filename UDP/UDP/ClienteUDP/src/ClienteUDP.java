import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress servAddress = InetAddress.getByName("192.168.1.8");

            // Loop para continuar recebendo e enviando mensagens
            while (true) {
                System.out.println("Digite a mensagem para enviar (ou 'sair' para encerrar):");
                String mensagem = scanner.nextLine();

                // Condição para encerrar o loop
                if (mensagem.equalsIgnoreCase("sair")) {
                    System.out.println("Saindo...");
                    break;
                }

                byte[] sendData = mensagem.getBytes();

                // Enviar a mensagem para o servidor
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, servAddress, 9876);
                socket.send(sendPacket);

                // Receber a resposta do servidor
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                // Exibir a resposta do servidor
                String resposta = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Resposta do servidor: " + resposta);
            }

            socket.close(); // Fechar o socket quando terminar

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
