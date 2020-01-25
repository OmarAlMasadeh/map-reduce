
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplittingServer {
    private int numberOfWorkers;
    private ExecutorService executor ;
    private int counter;
    final private static String HOSTNAME = "localhost";
    final private static int PORT = 50505;
    private ArrayList<String> array;
    public ServerSocket serverSocket;
    private int chunckSize;
    private final MapReduce mapReduce;
    public SplittingServer(MapReduce mapReduce , ArrayList<String> input){
        try { serverSocket = new ServerSocket(PORT); }
        catch (IOException e){ System.out.println("Unable to Create ServerSocket"); }
        this.mapReduce = mapReduce;
        numberOfWorkers = mapReduce.getNumberOfMappers();
        counter = 0;
        array=input;
        chunckSize = input.size()/numberOfWorkers;
    }

    void Connect() {
        executor = Executors.newFixedThreadPool(numberOfWorkers);
        while(counter!=numberOfWorkers){
            try {
                Socket socket = serverSocket.accept();
                executor.execute(new SendToWorker(counter++,socket));
            }
            catch (IOException e){ e.printStackTrace(); }
        }
    }

    private class SendToWorker implements Runnable
    {
        private Socket workerSocket;
        private int workerIndex;
        SendToWorker(int workerIndex,Socket workerSocket)
        {
            this.workerIndex = workerIndex;
            this.workerSocket = workerSocket;
        }
        @Override
        public void run() {
            try {
                System.out.println("Connected to worker "+ workerIndex);
                BufferedReader reader = new BufferedReader(new InputStreamReader(workerSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(workerSocket.getOutputStream()));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(workerSocket.getOutputStream());

                while (!(reader.read() == 0x2406)){}
                objectOutputStream.writeObject(mapReduce);

                while (!(reader.read() == 0x2406)){}// acknowledgment code
                System.out.println("starting to send to " + workerIndex);
                    for(int i = workerIndex*chunckSize;i<workerIndex*chunckSize + chunckSize;i++)
                        writer.write(array.get(i)+"\n");
                    if(workerIndex == numberOfWorkers-1)
                        for(int i = workerIndex*chunckSize + chunckSize;i<array.size();i++)
                            writer.write(array.get(i)+"\n");
                    writer.write(((char)0x2406)+"\n");
                    writer.flush();
                    System.out.println("finished sending split (" + chunckSize + ") to " + workerIndex);
            }
            catch (IOException e) { e.printStackTrace(); }
        }
    }
}
