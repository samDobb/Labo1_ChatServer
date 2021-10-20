import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUIThread extends Thread{

    private ClientGUI client;

    private BufferedReader in;

    public ClientGUIThread(ClientGUI gui, BufferedReader socket) {
        client=gui;
        in=socket;

        System.out.println("constructor ok");
    }

    public void run() {
        String[] messageparts;

        while (true) {
            try {
                messageparts = in.readLine().split(";");

                //login type
                if (messageparts[0].equals("0")) {
                    if (messageparts[1].equals(client.getUsername().getText()) && messageparts[2].equals("True")) {
                        client.setLoggedIn(true);
                        client.getLogin().setText("Ingelogd");
                        client.setname(messageparts[1]);
                    } else {
                        client.getUsername().setText("Er is iets misgegaan");
                    }
                }
                //textmessage type
                else if (messageparts[0].equals("1") && client.isLoggedIn()) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                    Date parsedDate = dateFormat.parse(messageparts[3]);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                    Message m=new Message(Integer.parseInt(messageparts[0]),messageparts[1],messageparts[2],timestamp);

                    client.getChatMessages().add(m);
                    client.insertChatArea(m);
                }
                //logout type
                else if (messageparts[0].equals("2")&& client.isLoggedIn()) {

                }
                else if(messageparts[0].equals("3")&& client.isLoggedIn()){

                    client.setUserList(messageparts[2]);
                }
                else {
                    System.out.println("Unknown type of message detected");
                }

            } catch (IOException | ParseException exception) {
                exception.printStackTrace();


            }
        }
    }
}
