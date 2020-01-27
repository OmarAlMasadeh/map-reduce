
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    private static final Pattern pattern = Pattern.compile("[a-zA-Z]+");
    private static BufferedWriter OutPutFileWriter;

    static {
        try {
            OutPutFileWriter = new BufferedWriter(new FileWriter("output.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> ReadInputFile() {
        ArrayList arrayList = new ArrayList<String>(32768);
        try (BufferedReader InputFileReader = new BufferedReader(new FileReader("/home/MapReduce/input.txt"))){
            Matcher matcher;
            String str = InputFileReader.readLine();
            while (str != null) {
                if (!str.equals("")) {
                    matcher = pattern.matcher(str);
                    while (matcher.find()) {
                        String word = matcher.group();
                        arrayList.add(word);
                    }
                }
                str = InputFileReader.readLine();
            }
        }
        catch (IOException e){e.printStackTrace();}
        return arrayList;
    }

    //write output
    public static void WriteOutputFile(HashMap hashMap) throws IOException {

        Object[] keys = hashMap.keySet().toArray();
        Arrays.sort(keys);
        for(Object key : keys) {
            OutPutFileWriter.write(key+","+hashMap.get(key));
            System.out.println(hashMap.get(key));
        }
    }
    public static void  WriteMapReduceObject(MapReduce mapReduce){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("MapReduce.obj"))){
            objectOutputStream.writeObject(mapReduce);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
