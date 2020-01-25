
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
    private static int generateIndexFromKey(MapReduce mapReduce,String s){
        return s.hashCode()%mapReduce.getNumberOfReducers();
    }
}
