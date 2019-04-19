package messageexchange;

import java.io.IOException;

public class MessageExchange {

    SocketInit sockInit;
    MainMenu menu;

    public MessageExchange() {
        sockInit = new SocketInit();
    }

    public static void main(String[] args) throws IOException {
        //inicializar o Menu
        MessageExchange app = new MessageExchange();
        app.menu = new MainMenu(app);
        app.menu.show();
    }
    
    //apo's confirmacao de existencia de pin, inicializamos o socketME com
    // porto = pin e nick=nick
    public void changeToMsgMenu(int pin, String nick) {
        menu.sockInit.DS.close();
        menu.dispose();
        MsgMenu msgMenu = new MsgMenu(pin,nick);
        msgMenu.show();
    }

}
