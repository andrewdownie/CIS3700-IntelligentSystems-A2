import java.util.LinkedList;
import java.util.*;
import java.util.List;

public class Clause{
    //Clause: It maintains a list of Literals in the clause and represents their disjunction

    public List<String> literals;

    public Clause(){
        literals = new LinkedList<String>(); 
    }

    public static void TestClause(){
        System.out.println("Test clause");
    }

    public void AddLiteral(String literal){
        literals.add(literal);
    }


    public String GetDisjunction(){
        String output = "";

        for(int i = 0; i < literals.size() - 1; i++){
            output += literals.get(i);

            if(i < literals.size() - 2){
                output += " v ";
            }
        }
        return output;
    }
}
