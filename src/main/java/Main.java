
public class Main {
    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        MapReduce mapReduce = new MapReduce.MapReduceBuilder()
                .numberOfMappers(3)
                .numberOfReducers(4)
                .mapper(wordCount)
                .reducer(wordCount)
                .build();
        mapReduce.ExecuteMapReduce();
    }
}
