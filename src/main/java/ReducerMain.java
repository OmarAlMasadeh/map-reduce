
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        while (true);
    }
}
