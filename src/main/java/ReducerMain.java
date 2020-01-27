
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReducerMain {
    public static void main(String[] args) {
        System.out.println("Reducer Created");
        SplitReceiver splitReceiver = new SplitReceiver("172.18.1.0");
        MapReduce mapReduce = splitReceiver.receiveMapReduce();
        System.out.println("mapreduce "+mapReduce.getNumberOfMappers());
        HashMap<String, ArrayList<Integer>>[] beforeReduce = new HashMap[mapReduce.getNumberOfMappers()];
        beforeReduce[0]=splitReceiver.receiveSplit();
        System.out.println(beforeReduce[0].size());
        for(int i =1;i<mapReduce.getNumberOfMappers();i++) {
            splitReceiver = new SplitReceiver("172.18.1."+i);
            splitReceiver.receiveMapReduce();
            beforeReduce[i]= splitReceiver.receiveSplit();
            System.out.println(beforeReduce[i].size());
        }
        HashMap<String, ArrayList<Integer>> mergedHasMap = MergeHashMaps(beforeReduce);
        System.out.println(mergedHasMap.size());
    }
    private static HashMap<String, ArrayList<Integer>> MergeHashMaps(HashMap<String, ArrayList<Integer>>[] splitHashMaps){
        HashMap<String, ArrayList<Integer>> mergedHashMap = splitHashMaps[0];
        for(int i = 1;i<splitHashMaps.length;i++){
            Iterator it = splitHashMaps[i].entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if(mergedHashMap.containsKey(pair.getKey())){
                    mergedHashMap.get(pair.getKey()).addAll((ArrayList<Integer>) pair.getValue());
                }else{
                    mergedHashMap.put((String)pair.getKey(),(ArrayList<Integer>) pair.getValue());
                }
            }
        }
        return mergedHashMap;
    }
}
