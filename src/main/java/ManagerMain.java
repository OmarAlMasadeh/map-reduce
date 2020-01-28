
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerMain {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/home/MapReduce/MapReduce.obj"))) {
            System.out.println("Manager Started");

            MapReduce mapReduce = (MapReduce)objectInputStream.readObject();
            ArrayList<String> inputList = FileUtil.ReadInputFile();
            ArrayList<String>[] Splits = Splitter.Split(inputList,mapReduce.getNumberOfMappers());

            System.out.println("Finished Splitting \nWaiting for Connections");
            ServerSender serverSender = new ServerSender(mapReduce,Splits,mapReduce.getNumberOfMappers());
            serverSender.Connect();

            ServerReceiver serverReceiver = new ServerReceiver(mapReduce,mapReduce.getNumberOfReducers());
            ArrayList rec = serverReceiver.Connect();
            HashMap<String , Integer> output = new HashMap<>();
            int[] sizes = new int[mapReduce.getNumberOfReducers()];
            for(int i = 0 ; i<rec.size();i++){
                output.putAll((HashMap<String,Integer>)rec.get(i));
                sizes[i] = ((HashMap<String,Integer>)rec.get(i)).size();
            }
            FileUtil.WriteOutputFile(output);

            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            FileUtil.WriteStatsFile(elapsedTime,sizes);
            while (true){}
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("Done Sending Splits");


    }

}
