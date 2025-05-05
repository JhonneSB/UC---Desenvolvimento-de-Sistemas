import java.net.*;

public class ServidorUDP {
    public static void main(String[] args) throws Exception {
        try {
            DatagramSocket socket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];

            System.out.println("Servidor UDP esperando por dados...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String mensagem = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Mensagem recebida: " + mensagem);

                InetAddress clienteAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String resposta = "Mensagem recebida!";
                DatagramPacket sendPacket = new DatagramPacket(resposta.getBytes(), resposta.length(), clienteAddress,
                        clientPort);
                socket.send(sendPacket);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
