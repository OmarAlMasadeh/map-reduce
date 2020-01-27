

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSender<T>  {
    private int numberOfClients;
    private ExecutorService executor ;
    private int counter;
    final private static int PORT = 50505;
    private T[] Splits;
    public ServerSocket serverSocket;
    private final MapReduce mapReduce;
    public ServerSender(MapReduce mapReduce , T[] input , int numberOfClients){
        try { serverSocket = new ServerSocket(PORT); }
        catch (IOException e){ System.out.println("Unable to Create ServerSocket"); }
        this.mapReduce = mapReduce;
        this.numberOfClients = numberOfClients;
        counter = 0;
        Splits=input;
    }
    void Connect() {
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
                executor.execute(new SendToWorker(counter++,socket));

            }
            catch (SocketException e){ System.out.println("Closed socket"); }
            catch (IOException e){ e.printStackTrace(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private class SendToWorker implements Runnable
    {
        private Socket clientSocket;
        private int clientIndex;
        SendToWorker(int clientIndex,Socket clientSocket)
        {
            this.clientIndex = clientIndex;
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try {
                System.out.println("Connected to worker "+ clientIndex + " " + clientSocket);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                //while (!(reader.read() == 0x2406)){System.out.print("1");}
                objectOutputStream.writeObject(mapReduce);
                objectOutputStream.flush();
                String clientAddress = clientSocket.getRemoteSocketAddress().toString();
                System.out.println(clientAddress);
                int index = Integer.parseInt(clientAddress.substring(clientAddress.lastIndexOf(".")+1,clientAddress.lastIndexOf(":")));
                System.out.println("sending to "+clientAddress+ " .. " + index);
                objectOutputStream.writeObject(Splits[index]);
                objectOutputStream.flush();
                System.out.println("finished sending split to " + clientIndex);
            }
            catch (IOException e) { e.printStackTrace(); }
        }
    }
}
