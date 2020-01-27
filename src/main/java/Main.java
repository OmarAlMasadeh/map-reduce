
public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        long startTime = System.currentTimeMillis();
        WordCount wordCount = new WordCount();
        MapReduce mapReduce = new MapReduce.MapReduceBuilder()
                .numberOfMappers(2)
                .numberOfReducers(3)
                .mapper(wordCount)
                .reducer(wordCount)
                .build();
        mapReduce.ExecuteMapReduce();
        // Execution Time
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("End execution time : "+ elapsedTime);
    }
}
