
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ManagerMain {
    public static void main(String[] args) {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/home/MapReduce/target/classes/obj/MapReduce.obj"))) {
            MapReduce mapReduce = (MapReduce)objectInputStream.readObject();
            ArrayList<String> arrayList = FileUtil.ReadInputFile();
            SplittingServer splittingServer = new SplittingServer(mapReduce,arrayList);
            splittingServer.Connect();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("Done Sending Splits");
    }
}
