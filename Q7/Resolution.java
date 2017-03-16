import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*; 
import java.util.List; 
import java.util.Arrays;

public class Resolution{

    public static void main(String[] args){
        String kb_path, percept_path, query_path;

        kb_path = args[0];
        percept_path = args[1];
        query_path = args[2];

        ClauseBase cb = new ClauseBase();
        cb.LoadClauses(kb_path);
        cb.LoadClauses(percept_path);
        cb.LoadQuery(query_path);


    }

}