package messageexchange;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JTextArea;

public class SocketME extends Thread {

    InetAddress ER;
    DatagramSocket DS;
    byte bp[] = new byte[1024];
    JTextArea textArea = new JTextArea(12, 40);
    int port;
    String nick;
    String nickTo, msgToSend;

    SocketME(JTextArea ta, int port, String nick) {
        textArea = ta;
        this.port = port;
        this.nick = nick;
        try {
            DS = new DatagramSocket(port);
        } catch (IOException e) {
            System.out.println("Error a inicializar o SocketME na porta " + port);
        }
    }

    public void run() {
        while (true) {
            receiveMsg();
        }
    }

    //recebe mensagem
    public void receiveMsg() {
        try {
            DatagramPacket DP = new DatagramPacket(bp, 1024);
            DS.receive(DP);
            byte payload[] = DP.getData();
            String msg = new String(payload, 0, 0, DP.getLength());

            System.out.println(msg);
            //analise da mensagem recebida
            int index = msg.indexOf(" ");
            if (index == -1) {
                int portToSend = Integer.parseInt(msg);
                if (portToSend == -1) {
                    textArea.append("\nMensagem não pode ser enviada pois o destinatário nao existe.");
                } else {
                    sendSavedMsg(portToSend);
                }
            } else {
                String nickFrom = msg.substring(0, index);
                String content = msg.substring(index + 1);
                textArea.append("\nMensagem recebida de:->" + nickFrom + "\n" + content);
            }
        } catch (IOException e) {
        }
    }

    //envio de mensagem
    public void sendMsg(String nickTo, String msg) {
        this.nickTo = nickTo;
        this.msgToSend = msg;
        //obter porto de destino
        getPin(nickTo);
    }

    //faz pedido ao servidor de nomes
    private void sendToNS(String msg) {
        int len = msg.length();
        byte b[] = new byte[len];
        msg.getBytes(0, len, b, 0);
        try {
            ER = InetAddress.getByName("127.0.0.1");
            DatagramPacket DP = new DatagramPacket(b, len, ER, 8081);
            DS.send(DP);
        } catch (IOException e) {
        }
    }

    //obter porto para envio de mensagem
    public void getPin(String nickTo) {
        sendToNS("getPin " + nickTo);
    }

    private void sendSavedMsg(int portToSend) {
        String nickAndMsg = nick + " " + msgToSend;
        int len = nickAndMsg.length();
        byte b[] = new byte[len];
        nickAndMsg.getBytes(0, len, b, 0);
        try {
            ER = InetAddress.getByName("127.0.0.1");
            DatagramPacket DP = new DatagramPacket(b, len, ER, portToSend);
            DS.send(DP);
            textArea.append("\nMensagem enviada para:->" + nickTo + "\n" + msgToSend);
        } catch (IOException e) {
        }
    }

}
