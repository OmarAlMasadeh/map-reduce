
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SplitReceiver {
    Socket socket;
    private final String HOSTNAME = "172.18.0.2";
    private final int PORT = 50505;
    ObjectInputStream objectInputStream;
    public SplitReceiver() {
        try {
            socket = new Socket(HOSTNAME, PORT);
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> receiveSplit(){
        ArrayList<String> Split = new ArrayList<>();
        try (
                BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ){
            socketWriter.write(0x2406);
            socketWriter.flush();
            Split = (ArrayList<String>) objectInputStream.readObject();
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
            System.out.println("receiveing map reduce");
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("writing");
            socketWriter.write(0x2406);
            socketWriter.flush();
            System.out.println("writing2");
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("obj");
            return (MapReduce) objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
