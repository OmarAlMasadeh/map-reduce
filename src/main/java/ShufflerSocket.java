import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShufflerSocket {
    public Socket[] reducersSockets;
    public ObjectOutputStream[] reducersOutputStreams;
    ShufflerSocket(int numberofreducers){
        reducersSockets = new Socket[numberofreducers];
        reducersOutputStreams = new ObjectOutputStream[numberofreducers];
        for(int i =0 ; i<numberofreducers ; i++){
            try {
                reducersSockets[i] = new Socket("172.18.2."+i,50505);
                reducersOutputStreams[i] = new ObjectOutputStream(reducersSockets[i].getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void SendtoReducers(HashMap<String, Integer> indexes, HashMap hashMap){
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int index = indexes.get(pair.getKey());
            try {
                reducersOutputStreams[index].writeObject(pair.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            it.remove();
        }
    }
}
