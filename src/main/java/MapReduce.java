
import javax.imageio.IIOException;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class MapReduce implements Serializable {
    private final int numberOfMappers;
    private final int numberOfReducers;
    private final String inputFileName;
    private final String outputFileName;
    private final Mapper mapper ;
    private final Reducer reducer;
    //private InetAddress[] inetAddresses;

    private MapReduce(MapReduceBuilder builder) {
        this.numberOfMappers = builder.numberOfMappers;
        this.numberOfReducers = builder.numberOfReducers;
        this.inputFileName = builder.inputFileName;
        this.outputFileName = builder.outputFileName;
        this.mapper = builder.mapper;
        this.reducer = builder.reducer;
    }


    public int getNumberOfMappers() {
        return numberOfMappers;
    }

    public int getNumberOfReducers() {
        return numberOfReducers;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public Reducer getReducer() {
        return reducer;
    }

    public void ExecuteMapReduce(){
        FileUtil.WriteMapReduceObject(this);
        try {
            System.out.println("Hello");
            BashScriptExecuter.CreateManager();
            Thread.sleep(10000);
            BashScriptExecuter.CreateMappers(this.numberOfMappers);
            BashScriptExecuter.CreateReducers(this.numberOfReducers);
            //BashScriptExecuter.ManagerProcess.waitFor();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
    public static class MapReduceBuilder{
        private int numberOfMappers;
        private int numberOfReducers;
        private String inputFileName;
        private String outputFileName;
        private Mapper mapper ;
        private Reducer reducer;
        public MapReduceBuilder(){}
        public MapReduceBuilder numberOfMappers(int numberOfMappers){
            this.numberOfMappers = numberOfMappers;
            return this;
        }
        public MapReduceBuilder numberOfReducers(int numberOfReducers){
            this.numberOfReducers = numberOfReducers;
            return this;
        }
        public MapReduceBuilder inputFileName(String inputFileName){
            this.inputFileName = inputFileName;
            return this;
        }
        public MapReduceBuilder outputFileName(String outputFileName){
            this.outputFileName = outputFileName;
            return this;
        }
        public MapReduceBuilder mapper(Mapper mapper){
            this.mapper = mapper;
            return this;
        }
        public MapReduceBuilder reducer(Reducer reducer){
            this.reducer = reducer;
            return this;
        }
        public MapReduce build(){
            MapReduce mapReduce = new MapReduce(this);
            validateMapReduce(mapReduce);
            return mapReduce;
        }
        private void validateMapReduce(MapReduce mapReduce){
            if(mapReduce.mapper == null || mapReduce.reducer == null)
                throw new IllegalArgumentException();
            if(mapReduce.numberOfMappers <= 0 || mapReduce.numberOfReducers <=0)
                throw new IllegalArgumentException();
            if(mapReduce.inputFileName == null)
                inputFileName="input.txt";
            if(mapReduce.outputFileName==null)
                outputFileName="output.txt";
        }
    }
}
