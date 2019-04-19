package userresgister;

import java.io.*;
import java.awt.*;


public class UserResgister extends Frame{

    TextArea textArea;
    SocketUR sock;
  
    
    public UserResgister(String str) throws IOException {
        super(str);
        textArea = new TextArea(12, 40);
        sock = new SocketUR(textArea);
    }

    public static void main(String[] args) throws IOException {
        UserResgister app = new UserResgister("UserResgister");
        app.resize(320, 240);
        app.GUI();
        app.show();
        app.StartSocket();
    }

    public void GUI() {
        setBackground(Color.lightGray);
        textArea.setEditable(false);
        GridBagLayout GBL = new GridBagLayout();
        setLayout(GBL);
        Panel P1 = new Panel();
        P1.setLayout(new BorderLayout(5, 5));
        P1.add("Center", textArea);
        GridBagConstraints P1C = new GridBagConstraints();
        P1C.gridwidth = GridBagConstraints.REMAINDER;
        GBL.setConstraints(P1, P1C);
        add(P1);
    }

    public void StartSocket() {
        sock.start();
    }

    public boolean handleEvent(Event i) {
        if (i.id == Event.WINDOW_DESTROY) {
            dispose();
            System.exit(0);
            return true;
        }
        return super.handleEvent(i);
    }
}
