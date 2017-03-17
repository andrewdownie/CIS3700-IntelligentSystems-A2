import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*; 
import java.util.List; 
import java.util.Arrays;

public class Resolution{

    public static void main(String[] args){
        String kb_path, percept_path, query_path;

        kb_path = args[0];
        query_path = args[1];
        percept_path = args[2];

        ClauseBase cb = new ClauseBase();
        cb.LoadClauses(percept_path);
        cb.LoadClauses(kb_path);
        cb.LoadQuery(query_path);


        boolean entails = cb.Resolution();

        if(entails){
            System.out.println("\nKB |= alpha");
        }
        else{
            System.out.println("\nKB !|= alpha");
        }


    }

}