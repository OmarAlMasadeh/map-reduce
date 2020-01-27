import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerReceiver<T> {
    private int numberOfClients;
    private ExecutorService executor ;
    private int counter;
    final private static int PORT = 50505;
    private ArrayList<T> Msgs;
    public ServerSocket serverSocket;
    private final MapReduce mapReduce;
    public ServerReceiver(MapReduce mapReduce , int numberOfClients){
        try { serverSocket = new ServerSocket(PORT); }
        catch (IOException e){ System.out.println("Unable to Create ServerSocket"); }
        this.mapReduce = mapReduce;
        this.numberOfClients = numberOfClients;
        counter = 0;
        Msgs = new ArrayList<T>(numberOfClients);
    }
    ArrayList<T> Connect() {
        executor = Executors.newFixedThreadPool(numberOfClients);
        while(!serverSocket.isClosed()){
            try {
                if(counter == numberOfClients){
                    Thread.sleep(3000);
                    serverSocket.close();
                    executor.shutdown();
                    System.out.println("closing Splitting Server");
                }
                Socket socket = serverSocket.accept();
                executor.execute(new ReceiveFromClients(counter++,socket));

            }
            catch (SocketException e){ System.out.println("Closed socket"); }
            catch (IOException | InterruptedException e){ e.printStackTrace(); }
        }
        return Msgs;
    }
    private class ReceiveFromClients implements Runnable
    {
        private Socket clientSocket;
        private int clientIndex;
        ReceiveFromClients(int clientIndex,Socket clientSocket)
        {
            this.clientIndex = clientIndex;
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())){
                String clientAddress = clientSocket.getRemoteSocketAddress().toString();
                System.out.println(clientAddress);
                int index = Integer.parseInt(clientAddress.substring(clientAddress.lastIndexOf(".")+1,clientAddress.lastIndexOf(":")));
                System.out.println("sending to "+clientAddress+ " .. " + index);
                Msgs.add((T)objectInputStream.readObject());
                System.out.println("finished sending split to " + clientIndex);
            }
            catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        }
    }
}
