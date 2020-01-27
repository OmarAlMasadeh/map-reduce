import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSender {
    Socket socket;
    private String HOSTNAME;
    private final int PORT = 50505;
    public ClientSender(String hostname) {
        this.HOSTNAME = hostname;
        try {
            socket = new Socket(HOSTNAME, PORT);
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public <T> void Send(T msg){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
            objectOutputStream.writeObject(msg);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
