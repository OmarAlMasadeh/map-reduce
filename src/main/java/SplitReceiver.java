
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SplitReceiver {
    Socket socket;
    public SplitReceiver() {
        try {
        socket = new Socket("172.18.0.2", 50505);
        System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> receiveSplit(){
        ArrayList<String> Split = new ArrayList<>();

       try (
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       ){

           socketWriter.write(0x2406);
           socketWriter.flush();
           char c =0;
           while(true) {
               String str = socketReader.readLine();
               if(str.charAt(0)==0x2406) break;
               Split.add(str);
           }
       }
       catch (UnknownHostException e){
           System.out.println("Unable to find Server");
       }
       catch (IOException e){
           System.out.println("Failed to send data");
       }
       return Split;
   }
   public MapReduce receiveMapReduce(){
        try {
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socketWriter.write(0x2406);
            socketWriter.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (MapReduce) objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
   }
}
