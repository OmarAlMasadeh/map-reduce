
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    private static final Pattern pattern = Pattern.compile("[a-zA-Z]+");
    private static BufferedWriter OutPutFileWriter;

    static {
        try {
            OutPutFileWriter = new BufferedWriter(new FileWriter("/home/MapReduce/output.txt"));
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
    void WriteOutputFile(HashMap hashMap) throws IOException {
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            OutPutFileWriter.write(pair.getKey()+","+pair.getValue());
            it.remove();
        }
    }
    public static void  WriteMapReduceObject(MapReduce mapReduce){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/home/MapReduce/MapReduce.obj"))){
            objectOutputStream.writeObject(mapReduce);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
