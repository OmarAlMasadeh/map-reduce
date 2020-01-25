
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReduceReceiver {
    public ConcurrentHashMap hashMap;
    public ServerSocket serverSocket;
    private ExecutorService executor ;
    private int counter;
    private final MapReduce mapReduce;
    public ReduceReceiver(MapReduce mapReduce){
        try {
            serverSocket = new ServerSocket( 8000);
            System.out.println("Connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mapReduce = mapReduce;
        counter = 0;
        hashMap = new ConcurrentHashMap<String,Integer[]>();
    }
    public void StartRecieving(){
        executor = Executors.newFixedThreadPool(mapReduce.getNumberOfMappers());
        while(counter!=mapReduce.getNumberOfReducers()){
            try {
                Socket socket = serverSocket.accept();
                executor.execute(new ReduceReceiver.ReceiveFromMapper(counter++,socket));
            }
            catch (IOException e){ e.printStackTrace(); }
        }
    }
    private class ReceiveFromMapper implements Runnable
    {
        private Socket mapperSocket;
        private int mapperIndex;
        ReceiveFromMapper(int mapperIndex,Socket mapperSocket)
        {
            this.mapperIndex = mapperIndex;
            this.mapperSocket = mapperSocket;
        }
        public void run() {
            try {
                System.out.println("Connected to mapper "+ mapperIndex);
                BufferedReader reader = new BufferedReader(new InputStreamReader(mapperSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(mapperSocket.getOutputStream()));


                while (!(reader.read() == 0x2406)){}// acknowledgment code
                System.out.println("starting to receive from mapper " + mapperIndex);
                ObjectInputStream objectInputStream = new ObjectInputStream(mapperSocket.getInputStream());
                String key = (String) objectInputStream.readObject();
                int[] values = (int[]) objectInputStream.readObject();

                if(!hashMap.contains(key)){
                    hashMap.put(key,values);
                }else{
                    hashMap.put(key,MergeArrays((int[]) hashMap.get(key),values));
                }

                writer.write(((char)0x2406)+"\n");
                writer.flush();
            } catch (IOException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        }
    }
    public static MapReduce receiveMapReduce() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        try {
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socketWriter.write(0x2406);
            socketWriter.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return (MapReduce) objectInputStream.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    private int[] MergeArrays(int[] array1,int[]array2){
        int aLen = array1.length;
        int bLen = array2.length;
        int[] result = new int[aLen + bLen];
        System.arraycopy(array1, 0, result, 0, aLen);
        System.arraycopy(array2, 0, result, aLen, bLen);
        return result;
    }
}
