
public class Main {
    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        MapReduce mapReduce = new MapReduce.MapReduceBuilder()
                .numberOfMappers(4)
                .numberOfReducers(5)
                .mapper(wordCount)
                .reducer(wordCount)
                .build();
        mapReduce.ExecuteMapReduce();
    }
}
