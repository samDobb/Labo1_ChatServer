
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private  List<String> userlist;
    private  List<ChatServerThread> clientList = new ArrayList<>();

    public ChatServer(){
        try(
                ServerSocket serverSocket=new ServerSocket(4444)
                ){

            userlist = new ArrayList<>();

            System.out.println("Server opgestart");
            while(true){
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

    public synchronized void sendUserList(String username){

        String userList="";

        for(int i = 0 ; i <userlist.size();i++){
            userList=userList.concat(userlist.get(i)).concat("/");
        }
        broadcast(new Message(3,username,userList,null));
    }

    public synchronized void broadcast(Message message){
            for(ChatServerThread thread: clientList){
                thread.sendMessage(message);
            }
    }

}
