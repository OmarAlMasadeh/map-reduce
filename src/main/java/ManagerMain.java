
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;

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
            SplittingServer splittingServer = new SplittingServer(mapReduce,Splits,mapReduce.getNumberOfMappers());
            splittingServer.Connect();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("Done Sending Splits");
    }
}
