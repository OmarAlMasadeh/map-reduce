
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WordCount implements Serializable , Mapper<String,Integer> , Reducer<String,Integer>{
    public HashMap<String, ArrayList<Integer>> Map(ArrayList<String> array) {
        HashMap<String,ArrayList<Integer>> hashMap = new HashMap<>();
        for(String el : array){
            if(!hashMap.containsKey(el)){
                hashMap.put(el,new ArrayList<Integer>());
            }
            hashMap.get(el).add(1);
        }
        return hashMap;
    }

    public HashMap<String, Integer> Reduce(HashMap<String, ArrayList<Integer>> map) {
        HashMap<String,Integer> reducedHashMap = new HashMap<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ArrayList<Integer> values = (ArrayList<Integer>) pair.getValue();
            reducedHashMap.put((String) pair.getKey(),values.size());
            it.remove();
        }
        return reducedHashMap;
    }
}
