
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapperMain {
    public static void main(String[] args) {
        SplitReceiver splitReceiver = new SplitReceiver("172.18.0.2");
        MapReduce mapReduce = splitReceiver.receiveMapReduce();
        ArrayList<String> Split = splitReceiver.receiveSplit();
        System.out.println("Received Split of " + Split.size() + " Words");

        System.out.println("Mapping...");
        HashMap<String,ArrayList<Integer>> Mapped = mapReduce.getMapper().Map(Split);

        HashMap<String, Integer> indexes = Shuffler.Shuffle(mapReduce, Mapped);
        System.out.println("indexes "+ indexes.size());

        //System.out.println(indexes.values().stream().filter(v -> v == 0).count());

        HashMap<String,ArrayList<Integer>>[] shuffledHashMaps = Shuffler.ShuffleArrays(indexes,Mapped,mapReduce.getNumberOfReducers());
        SplittingServer splittingServer = new SplittingServer(mapReduce,shuffledHashMaps,mapReduce.getNumberOfReducers());
        splittingServer.Connect();
        System.out.println("Finished Shuffling");
    }

}
