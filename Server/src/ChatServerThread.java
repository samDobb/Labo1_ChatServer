import java.io.*;
import java.net.Socket;

public class ChatServerThread extends Thread{

    private String username;
    private Socket socket;
    private ChatServer server;
    private ObjectInputStream  in;
    private ObjectOutputStream  out;
    private boolean loggedIn=true;


    public ChatServerThread(Socket socket,ChatServer chatServer){
        super();
        this.socket=socket;
        server=chatServer;
        try {
            //object input stream
            in =  new ObjectInputStream (socket.getInputStream());

            //output stream
            out = new ObjectOutputStream(socket.getOutputStream());

            while(loggedIn){

                Message message= (Message) in.readObject();

                if(message.getType() == 0){
                    if(server.addUser(message.getUsername())){
                        username=message.getUsername();
                    }
                    else{
                        out.writeObject(new Message(0,username,"False",null));
                    }
                }
                else if(message.getType() == 1){

                }
                else if(message.getType() == 2){
                    loggedIn=false;
                    socket.close();
                    server.logOut(username,this);
                }
                else{
                    System.out.println("Unkown type of message detected");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void run(){


    }
}
