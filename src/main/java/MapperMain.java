
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapperMain {
    public static void main(String[] args) {
        SplitReceiver splitReceiver = new SplitReceiver();
        MapReduce mapReduce = splitReceiver.receiveMapReduce();
        ArrayList<String> Split = splitReceiver.receiveSplit();
        System.out.println("Received Split of " + Split.size() + " Words");
        System.out.println("Mapping...");
        HashMap hashMap = mapReduce.getMapper().Map(Split);
        System.out.println("Finished Mapping");
        HashMap<String, Integer> indexes = Shuffler.Shuffle(mapReduce, hashMap);
        //System.out.println(indexes.values().stream().filter(v -> v == 0).count());

    }

}
