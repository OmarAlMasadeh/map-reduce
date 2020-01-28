import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReducerMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Reducer Created");
        ClientReceiver clientReceiver = new ClientReceiver("172.18.1.0");
        MapReduce mapReduce = clientReceiver.receiveMapReduce();

        HashMap<String, ArrayList<Integer>>[] beforeReduce = new HashMap[mapReduce.getNumberOfMappers()];
        beforeReduce[0]= clientReceiver.receiveSplit();
        for(int i =1;i<mapReduce.getNumberOfMappers();i++) {
            clientReceiver = new ClientReceiver("172.18.1."+i);
            clientReceiver.receiveMapReduce();
            beforeReduce[i]= clientReceiver.receiveSplit();
        }
        HashMap<String, ArrayList<Integer>> mergedHasMap = MergeHashMaps(beforeReduce);
        System.out.println("Merged "+ mergedHasMap.size());

        //Reducing
        HashMap reducedHM = mapReduce.getReducer().Reduce(mergedHasMap);
        System.out.println("Reduced size "+reducedHM.size());

        //Send reduced hashmap to manager
        ClientSender clientSender = new ClientSender("172.18.0.2");
        clientSender.Send(reducedHM);
        System.out.println("Finished sending");
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
