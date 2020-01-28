
import java.util.ArrayList;
import java.util.HashMap;

public class MapperMain {
    public static void main(String[] args) {
        ClientReceiver clientReceiver = new ClientReceiver("172.18.0.2");
        MapReduce mapReduce = clientReceiver.receiveMapReduce();
        ArrayList<String> Split = clientReceiver.receiveSplit();
        System.out.println("Received Split of " + Split.size() + " Words");

        System.out.println("Mapping...");
        HashMap<String,ArrayList<Integer>> Mapped = mapReduce.getMapper().Map(Split);

        HashMap<String, Integer> indexes = Shuffler.Shuffle(mapReduce, Mapped);
        System.out.println("indexes "+ indexes.size());

        HashMap<String,ArrayList<Integer>>[] shuffledHashMaps = Shuffler.ShuffleArrays(indexes,Mapped,mapReduce.getNumberOfReducers());
        ServerSender serverSender = new ServerSender(mapReduce,shuffledHashMaps,mapReduce.getNumberOfReducers());
        serverSender.Connect();
        System.out.println("Finished Shuffling");
    }

}
