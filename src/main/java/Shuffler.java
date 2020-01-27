
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Shuffler {
    public static HashMap<String,Integer> Shuffle(MapReduce mapReduce , HashMap hashMap){
        HashMap indexHM = new HashMap();
        HashMap temp = hashMap;
        Iterator it = temp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            indexHM.put(pair.getKey(),generateIndexFromKey(mapReduce,(String)pair.getKey()));
            it.remove();
        }
        return indexHM;
    }
    public static HashMap<String,ArrayList<Integer>>[] ShuffleArrays(HashMap<String,Integer> indexes , HashMap<String,ArrayList<Integer>> hashMap ,int numberOfReducers){
        HashMap<String,ArrayList<Integer>>[] shuffledHashMaps = new HashMap[numberOfReducers];
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            int index = indexes.get(pair.getKey());
            shuffledHashMaps[index].put((String)pair.getKey(), (ArrayList<Integer>) pair.getValue());
            it.remove();
        }
        System.out.println(shuffledHashMaps[0].size());
        return shuffledHashMaps;
    }
    private static int generateIndexFromKey(MapReduce mapReduce,String s){
        return s.hashCode()%mapReduce.getNumberOfReducers();
    }
}
