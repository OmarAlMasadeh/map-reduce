
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReducerMain {
    public static void main(String[] args) {

        System.out.println("Reducer Created");
        SplitReceiver splitReceiver = new SplitReceiver("172.18.1.1");
        MapReduce mapReduce = splitReceiver.receiveMapReduce();
        System.out.println("mapreduce "+mapReduce.getNumberOfMappers());
        HashMap<String, ArrayList<Integer>>[] beforeReduce = new HashMap[mapReduce.getNumberOfMappers()];
        for(int i =2;i<mapReduce.getNumberOfMappers();i++) {
            beforeReduce[i]= new SplitReceiver("172.18.1."+i).receiveSplit();
            System.out.println(beforeReduce[i].size());
        }
    }
}
