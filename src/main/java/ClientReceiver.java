
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class ClientReceiver {
    Socket socket;
    private String HOSTNAME;
    private final int PORT = 50505;
    ObjectInputStream objectInputStream;
    public ClientReceiver(String hostname) {
        this.HOSTNAME = hostname;
        try {
            while (!(InetAddress.getByName(hostname).isReachable(36000))){}
            socket = new Socket(HOSTNAME,PORT);
            System.out.println("Connected to server");
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }
    public <T> T receiveSplit(){
        T Split = null;
        try{

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
