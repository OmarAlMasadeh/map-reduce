
public class Main {
    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        MapReduce mapReduce = new MapReduce.MapReduceBuilder()
                .numberOfMappers(2)
                .numberOfReducers(3)
                .mapper(wordCount)
                .reducer(wordCount)
                .build();
        mapReduce.ExecuteMapReduce();
    }
}
