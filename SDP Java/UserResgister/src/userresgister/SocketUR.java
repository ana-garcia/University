package userresgister;

import java.awt.TextArea;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketUR extends Thread {

    InetAddress ER;
    DatagramSocket DS;
    byte bp[] = new byte[1024];
    TextArea textArea = new TextArea(12, 40);

    SocketUR(TextArea ta) {
        textArea = ta;

    }

    public void run() {
        try {
            //registo de utilizadores escuta na porta 8082
            DS = new DatagramSocket(8082);
        } catch (IOException e) {
        }
        while (true) {
            receiveDP();
        }
    }

    //recebe pedido
    public void receiveDP() {
        try {
            DatagramPacket DP = new DatagramPacket(bp, 1024);
            DS.receive(DP);
            byte payload[] = DP.getData();
            int len = DP.getLength();
            String msg = new String(payload, 0, 0, len);
            textArea.appendText("\nPedido:->" + msg);
            //mensagem de resposta
            String res = "";

            //analise da mensagem recebida
            //lista com argumentos obtidos da string
            String[] msgSplit = msg.split(" ");
            //consultar o primeiro argumento para saber qual o pedido
            //se primeiro argumento e binding
            
            String nick = msgSplit[1];
            if (msgSplit[0].equals("register")) {
                //chamar funcao
                String pin = register(nick);
                res = pin;
            } else if (msgSplit[0].equals("getPin")) {
                //chamar funcao
                String pin = getPin(nick);
                res = pin;
            }

            textArea.appendText("\nResposta:->" + res + "\n");
            sendDP(DP.getPort(), res);
        } catch (IOException e) {
        }
    }

    //envio de resposta
    public void sendDP(int Pr, String msg) {
        int len = msg.length();
        byte b[] = new byte[len];
        msg.getBytes(0, len, b, 0);
        try {
            ER = InetAddress.getByName("127.0.0.1");
            DatagramPacket DP = new DatagramPacket(b, len, ER, Pr);
            DS.send(DP);
        } catch (IOException e) {
        }
    }

    //funcao register
    private String register(String nick) {
        sendToNS("binding " + nick);
        String pin = receiveFromNS();
        return pin;

    }

    //funcao getPin
    private String getPin(String nick) {
        sendToNS("getPin " + nick);
        String pin = receiveFromNS();
        return pin;
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
            textArea.appendText("\nPedido para NameService:->" + msg);
        } catch (IOException e) {
        }
    }

    //recebe pedido do servidor de nomes
    private String receiveFromNS() {
        DatagramPacket DP = new DatagramPacket(bp, 1024);
        try {
            DS.receive(DP);
        } catch (IOException ex) {
            Logger.getLogger(SocketUR.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte payload[] = DP.getData();
        int len = DP.getLength();
        String msg = new String(payload, 0, 0, len);
        textArea.appendText("\nRespota de NameService:->" + msg);
        return msg;
    }
}
