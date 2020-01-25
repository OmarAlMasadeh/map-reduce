
import java.io.IOException;
import java.util.HashMap;

public class ReducerMain {
    public static void main(String[] args) {
        int reducerIndex = Integer.parseInt(args[0]);
        try {
            MapReduce mapReduce = ReduceReceiver.receiveMapReduce();
            ReduceReceiver reduceReceiver = new ReduceReceiver(mapReduce);
            HashMap hashMap = new HashMap(reduceReceiver.hashMap);//convert from concurrent to regular hashmap
            HashMap reducedHashMap = mapReduce.getReducer().Reduce(hashMap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
