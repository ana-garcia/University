package nameservice;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

class SocketNS extends Thread {

    InetAddress ER;
    DatagramSocket DS;
    byte bp[] = new byte[1024];
    TextArea textArea = new TextArea(12, 40);
    final String FILENAME = "sdpData.txt";

    //dicionario de pins-nomes
    Map<Integer, String> nickByPin = new HashMap<>();
    //dicionario de nomes-pins
    Map<String, Integer> pinByNick = new HashMap<>();
    //contador de pin - inicializado ao primeiro possivel
    int count = 8010;

    SocketNS(TextArea ta) {
        textArea = ta;

        //inicializar mapas
        //tenta abrir documento, apanha execcao no caso de nao existir documento
        try {
            String content;
            content = new String(Files.readAllBytes(Paths.get(FILENAME)));
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    String[] value = line.split(" ");
                    int pin = Integer.parseInt(value[0]);
                    String nick = value[1];
                    pinByNick.put(nick, pin);
                    nickByPin.put(pin, nick);
                    if (pin > count) {
                        count = pin;
                    }
                }
            }
            //contador inicializado ao proximo valor possivel de ser atribuido
            count++;
        } catch (IOException ex) {
            //se o documento nao existe entao o conta inicializa ao primeiro possivel
            count = 8010;
        }

    }

    public void run() {
        try {
            //servico de nomes escuta na porta 8081
            DS = new DatagramSocket(8081);
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
            if (msgSplit[0].equals("binding")) {
                String nick = msgSplit[1];
                //chamar funcao
                int pin = binding(nick);
                res = "" + pin;
            } else if (msgSplit[0].equals("getNick")) {
                int pin = Integer.parseInt(msgSplit[1]);
                //chamar funcao
                String nick = getNick(pin);
                res = nick;
            } else if (msgSplit[0].equals("getPin")) {
                String nick = msgSplit[1];
                //chamar funcao
                int pin = getPin(nick);
                res = "" + pin;
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

    //funcao binding
    private int binding(String nick) {
        Integer pin = pinByNick.get(nick);
        if (pin == null) {
            pin = count;
            nickByPin.put(pin, nick);
            pinByNick.put(nick, pin);
            count++;
            return pin;
        } else {
            return -1;
        }
    }

    //funcao getPin
    private int getPin(String nick) {
        Integer pin = pinByNick.get(nick);
        if (pin == null) {
            return -1;
        } else {
            return pin;
        }
    }

    //funcao getNick
    private String getNick(int pin) {
        String nick = nickByPin.get(pin);
        if (nick == null) {
            return "";
        } else {
            return nick;
        }

    }

    //salvar dados em disco
    public void saveData() throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(FILENAME)) {
            String data = "";
            for (int pin : nickByPin.keySet()) {
                String nick = nickByPin.get(pin);
                // guarda primeiro o nick + " " + pin
                data += pin + " " + nick + "\n";
            }
            out.print(data);
        }
    }
}
