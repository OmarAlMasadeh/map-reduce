
import java.util.*;

public class Shuffler {
    public static HashMap<String,Integer> Shuffle(MapReduce mapReduce , HashMap hashMap){
        HashMap indexHM = new HashMap();
        HashMap temp = hashMap;
        Iterator it = temp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            indexHM.put(pair.getKey(),generateIndexFromKey(mapReduce,(String)pair.getKey()));
        }
        return indexHM;
    }
    public static HashMap<String,ArrayList<Integer>>[] ShuffleArrays(HashMap<String,Integer> indexes , HashMap<String,ArrayList<Integer>> Mapped ,int numberOfReducers){
        HashMap[] shuffledHashMaps = new HashMap[numberOfReducers];
        for(int i = 0; i<numberOfReducers;i++) {
            shuffledHashMaps[i]=new HashMap<String,ArrayList<Integer>>();
        }
        Iterator it = Mapped.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int index = indexes.get(pair.getKey());
            shuffledHashMaps[index].put(pair.getKey(),pair.getValue());
        }
        return shuffledHashMaps;
    }
    private static int generateIndexFromKey(MapReduce mapReduce,String s){
        return Math.abs(s.hashCode()%mapReduce.getNumberOfReducers());
    }
}
