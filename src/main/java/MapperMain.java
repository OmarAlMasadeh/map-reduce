
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapperMain {
    public static void main(String[] args) {
        SplitReceiver splitReceiver = new SplitReceiver("172.18.0.2");
        MapReduce mapReduce = splitReceiver.receiveMapReduce();
        System.out.println(mapReduce.getNumberOfMappers());
        ArrayList<String> Split = splitReceiver.receiveSplit();
        System.out.println("Received Split of " + Split.size() + " Words");
        System.out.println("Mapping...");
        HashMap hashMap = mapReduce.getMapper().Map(Split);
        System.out.println("Finished Mapping");
        HashMap<String, Integer> indexes = Shuffler.Shuffle(mapReduce, hashMap);
        //System.out.println(indexes.values().stream().filter(v -> v == 0).count());
        HashMap<String,ArrayList<Integer>>[] shuffledHashMaps = Shuffler.ShuffleArrays(indexes,hashMap,mapReduce.getNumberOfReducers());
        for(int i = 0; i<shuffledHashMaps.length;){
            System.out.println(shuffledHashMaps[i].size());
        }
        SplittingServer splittingServer = new SplittingServer(mapReduce,shuffledHashMaps,mapReduce.getNumberOfReducers());
        splittingServer.Connect();
        System.out.println("Finished Shuffling");
    }

}
