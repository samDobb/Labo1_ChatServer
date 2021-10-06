import java.net.Socket;

public class ChatServerThread extends Thread{

    private Socket socket;
    private ChatServer server;

    public ChatServerThread(Socket socket,ChatServer chatServer){
        super();
        this.socket=socket;
        server=chatServer;
    }

    public void run(){

    }
}
