import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatServerThread extends Thread{

    private String username;
    private Socket socket;
    private ChatServer server;
    private BufferedReader  in;
    private PrintWriter  out;
    private boolean loggedIn=true;

    private List<String> userList= new ArrayList<>();


    public ChatServerThread(Socket socket,ChatServer chatServer){
        super();
        this.socket=socket;
        server=chatServer;
    }

    public void run(){
        try {
            //object input stream
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //output stream
            out =  new PrintWriter(socket.getOutputStream(), true);

            String[] message;
            while(loggedIn){

                message=  in.readLine().split(";");

                if(message!=null)System.out.println("message ontvangen");

                //login type
                if(message[0].equals("0")){
                    if(server.addUser(message[1])){
                        server.addClient(this);
                        username=message[1];
                        out.println(new Message(0,username,"True",null).toString());
                        server.sendUserList(username,false);
                    }
                    else{
                        out.println(new Message(0,username,"False",null).toString());
                    }
                }
                //textmessage type
                else if(message[0].equals("1")){

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    Date parsedDate = dateFormat.parse(message[3]);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                    server.broadcast(new Message(Integer.parseInt(message[0]),message[1],message[2],timestamp));

                }
                //logout type
                else if(message[0].equals("2")){
                    loggedIn=false;
                    socket.close();
                    server.logOut(username,this);
                }

                else{
                    System.out.println("Unknown type of message detected");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getUserList() {
        return userList;
    }


    public void sendMessage(Message message){
        out.println(message.toString());
    }
}
