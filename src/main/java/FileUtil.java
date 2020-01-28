
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    private static final Pattern pattern = Pattern.compile("[a-zA-Z]+");

    /**
     * @return an array containing all input from file
     */
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

    /**
     * @param hashMap
     * Writes hash map to output file /home/MapReduce/output.txt in sorted order
     *
     * Note: This function uses Arrays.sort
     */
    //write output
    public static void WriteOutputFile(HashMap hashMap) {
        File f = new File("/home/MapReduce/output.txt");
        if(f.exists() && !f.isDirectory())
            try(BufferedWriter OutPutFileWriter = new BufferedWriter(new FileWriter(f))){
                Object[] keys = hashMap.keySet().toArray();
                Arrays.sort(keys);
                for (Object key : keys) {
                    OutPutFileWriter.write(key + "," + hashMap.get(key));
                    OutPutFileWriter.newLine();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
    }

    public static void WriteStatsFile(long Time,int [] sizes) throws IOException {
        File f = new File("/home/MapReduce/stats.txt");
        if(!f.exists())
            f.createNewFile();
        if(f.exists() && !f.isDirectory())
            try(BufferedWriter OutPutFileWriter = new BufferedWriter(new FileWriter(f))){
                OutPutFileWriter.write("Stats File For MapReduce");
                OutPutFileWriter.newLine();
                float total = Arrays.stream(sizes).sum();
                for(int i =0;i<sizes.length;i++) {
                    OutPutFileWriter.write("Received from Reducer "+ i + ": "+ sizes[i] + " keys  "+ sizes[i]/total*100 + "%");
                    OutPutFileWriter.newLine();
                }
                OutPutFileWriter.write("Total keys = " + total);
                OutPutFileWriter.newLine();
                OutPutFileWriter.write("Execution Time = " + Time);
            }
            catch (IOException e){
                e.printStackTrace();
            }
    }

    /**
     * @param mapReduce
     * Writes a MapReduce Object containing MapperFunction , ReducerFunction,etc.. to MapReduce.obj file
     */
    public static void  WriteMapReduceObject(MapReduce mapReduce){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("MapReduce.obj"))){
            objectOutputStream.writeObject(mapReduce);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
