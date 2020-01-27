
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SplitReceiver {
    Socket socket;
    private final String HOSTNAME;
    private final int PORT = 50505;
    ObjectInputStream objectInputStream;
    public SplitReceiver(String hostname) {
        this.HOSTNAME = hostname;
        try {
            socket = new Socket(HOSTNAME, PORT);
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public <T> T receiveSplit(){
        T Split = null;
        try (
                BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ){
            //socketWriter.write(0x2406);
            //socketWriter.flush();
            Split = (T) objectInputStream.readObject();
        }
        catch (UnknownHostException e){
            System.out.println("Unable to find Server");
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println("Failed to send data");
        }
        return Split;
    }
    public MapReduce receiveMapReduce(){
        try {
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //socketWriter.write(0x2406);
            //socketWriter.flush();
            Thread.sleep(2000);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (MapReduce) objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
