import java.util.LinkedList;
import java.util.*;
import java.util.List;

public class ClauseBase{
    // ClauseBase: It maintains a set of clauses and expresses conjunction of these clauses. It should contain
    //             methods to load clauses from a file, to join two ClauseBases into one, and to perform resolution when the
    //             clauses represent KB ∧ ¬α, where α is the query sentence

    public List<Clause> clauses;
    public Clause query;

    public ClauseBase(){
        clauses = new LinkedList<Clause>();
    }

    public static void TestClauseBase(){
        System.out.println("Test clausebase");
    }

    public String GetConjunction(){
        String output = "";

        for(int i = 0; i < clauses.size(); i++){
            output += clauses.get(i);

            if(i < clauses.size() - 1){
                output +=  " ^ ";
            }
        }

        return output;
    }

    public void LoadQuery(String filePath){
        //add query to both query var, and the list var
    }

    public void LoadClauses(String filePath){

    }

    private String FileContents(String filePath){

    }

    public void Resolve(){
        //Magic?
    }

}