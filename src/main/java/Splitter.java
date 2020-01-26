import java.util.ArrayList;

public class Splitter {
    public static ArrayList<String>[] Split(ArrayList<String> array, int num){
        ArrayList[] Splits = new ArrayList[num];
        int chunkSize = array.size()/num;
        for(int i = 0;i<num;i++) {
            Splits[i] = new ArrayList();
            for (int j = i * chunkSize; j < i * chunkSize + chunkSize; j++)
                Splits[i].add(array.get(j));
            if (i == num - 1)
                for (int j = i * chunkSize + chunkSize; j < array.size(); j++)
                    Splits[i].add(array.get(j));
        }
        return Splits;
    }
}