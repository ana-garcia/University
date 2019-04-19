package messageexchange;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketInit {

    InetAddress ER;
    DatagramSocket DS;
    byte bp[] = new byte[1024];

    public SocketInit() {
        try {
            //troca de mensagens usa a porta 8083 por default até ter a sua
            DS = new DatagramSocket(8083);
            ER = InetAddress.getByName("127.0.0.1");
        } catch (IOException e) {
            System.out.println("Erro a inicializar o SocketInit");
        }
    }

    //funcao de registo de utilizador
    public String register(String nick) {
        sendToRU("register " + nick);
        int pin = Integer.parseInt(receiveFrom());
        if (pin == -1) {
            return "\nO nick já existe";
        } else {
            return "\nO seu pin de acesso é: " + pin;
        }
    }

    //funcao getPin - recupera o pin esquecido
    public String getPin(String nick) {
        sendToRU("getPin " + nick);
        int pin = Integer.parseInt(receiveFrom());
        if (pin == -1) {
            return "\nO nick inserido não está registado.";
        } else {
            return "\nO seu pin de acesso é: " + pin;
        }
    }

    public String getNick(int pin) {
        sendToNS("getNick " + pin);
        String nick = receiveFrom();
        return nick;
    }

    //faz pedido ao servidor de nomes
    private void sendToNS(String msg) {
        int len = msg.length();
        byte b[] = new byte[len];
        msg.getBytes(0, len, b, 0);
        try {            
            DatagramPacket DP = new DatagramPacket(b, len, ER, 8081);
            DS.send(DP);
        } catch (IOException e) {
        }
    }

    //faz pedido ao registo de utilizadores
    private void sendToRU(String msg) {
        int len = msg.length();
        byte b[] = new byte[len];
        msg.getBytes(0, len, b, 0);
        try {
            DatagramPacket DP = new DatagramPacket(b, len, ER, 8082);
            DS.send(DP);
        } catch (IOException e) {
        }
    }

    //recebe resposta do pedido
    private String receiveFrom() {
        DatagramPacket DP = new DatagramPacket(bp, 1024);
        try {
            DS.receive(DP);
        } catch (IOException ex) {
            Logger.getLogger(SocketInit.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte payload[] = DP.getData();
        int len = DP.getLength();
        String msg = new String(payload, 0, 0, len);
        return msg;
    }
}
