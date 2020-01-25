
import java.util.ArrayList;
import java.util.HashMap;

public interface Reducer <T,E> {
    HashMap<T,E> Reduce(HashMap<T, ArrayList<E>> map);
}
