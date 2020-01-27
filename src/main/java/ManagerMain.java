
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerMain {
    public static void main(String[] args) throws InterruptedException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/home/MapReduce/MapReduce.obj"))) {
            System.out.println("Manager Started");
            MapReduce mapReduce = (MapReduce)objectInputStream.readObject();
            System.out.println(mapReduce.getNumberOfMappers());
            ArrayList<String> arrayList = FileUtil.ReadInputFile();
            System.out.println(arrayList.size());
            ArrayList<String>[] Splits = Splitter.Split(arrayList,mapReduce.getNumberOfMappers());
            System.out.println("Finished Splitting \nWaiting for Connections");
            ServerSender serverSender = new ServerSender(mapReduce,Splits,mapReduce.getNumberOfMappers());
            serverSender.Connect();

            ServerReceiver serverReceiver = new ServerReceiver(mapReduce,mapReduce.getNumberOfReducers());
            ArrayList rec = serverReceiver.Connect();
            HashMap<String , Integer> output = new HashMap<>();
            for(int i = 0 ; i<rec.size();i++){
                System.out.println(((HashMap<String,Integer>)rec.get(i)).size());
                output.putAll((HashMap<String,Integer>)rec.get(i));
            }
            FileUtil.WriteOutputFile(output);
            while (true){}
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("Done Sending Splits");
    }
}
