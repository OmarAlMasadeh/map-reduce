
import java.util.ArrayList;
import java.util.HashMap;

public interface Mapper <T,E>{
    HashMap<T,ArrayList<E>> Map(ArrayList<T> array);
}
