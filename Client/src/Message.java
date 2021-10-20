import java.sql.Timestamp;

public class Message {
    private int type;
    private String username;
    private String text;
    private Timestamp timestamp;


    /*
     type 0: username application
     type 1: text message for the groupchat
     type 2: going offline
     */

    public Message(int type, String username,String text,Timestamp timestamp){
        this.type=type;
        this.username=username;
        this.text=text;
        this.timestamp=timestamp;
    }

    public String toChatString(){return username +" "+timestamp + "\n"+text+"\n\n";}

    @Override
    public String toString(){
        return type+";"+username+";"+text+";"+timestamp;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
