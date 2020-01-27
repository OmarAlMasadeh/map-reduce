
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
