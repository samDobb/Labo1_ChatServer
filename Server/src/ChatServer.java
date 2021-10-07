
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private  List<String> userlist = new ArrayList<>();
    private  List<ChatServerThread> clientList = new ArrayList<>();

    public ChatServer(){
        try(
                ServerSocket serverSocket=new ServerSocket(4444)
                ){

            System.out.println("Server opgestart");
            while(true){
                System.out.println("while loop");
                ChatServerThread client=new ChatServerThread(serverSocket.accept(),this);
                client.start();

            }

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    //prototype logout function
    public synchronized void  logOut(String username,ChatServerThread thread){
        userlist.remove(username);
        clientList.remove(thread);
        thread.interrupt();
    }

    //check if the username is taken, if not then add him to the list
    public synchronized boolean addUser(String name){
        for(String user:userlist){
            if(user.equals(name))return false;
        }
        userlist.add(name);
        return true;
    }

    public void addClient(ChatServerThread thread){
        clientList.add(thread);
    }

    //returns the entire username list
    private List<String> getUserlist(){
        return userlist;
    }

    public synchronized void broadcast(Message message){
            for(ChatServerThread thread: clientList){
                thread.sendMessage(message);
            }
    }

}
