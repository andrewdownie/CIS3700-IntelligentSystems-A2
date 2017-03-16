import java.util.LinkedList;
import java.util.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClauseBase{
    // ClauseBase: It maintains a set of clauses and expresses conjunction of these clauses. It should contain
    //             methods to load clauses from a file, to join two ClauseBases into one, and to perform resolution when the
    //             clauses represent KB ∧ ¬α, where α is the query sentence

    public List<Clause> clauses;
    public Clause query;

    public ClauseBase(){
        clauses = new LinkedList<Clause>();
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
       String rawClause = FileContents(filePath); 
       Clause q = new Clause(rawClause.split("[\\r\\n]+")[0]);

       query = q;
       clauses.add(q);

    }

    public void LoadClauses(String filePath){
        String rawClauses = FileContents(filePath);
        String[] splitClauses = rawClauses.split("[\\r\\n]+");

        for(String clause: splitClauses){
            clauses.add(new Clause(clause));//TODO make teh constructor for clause rip the clause apart
        }
    }

    private String FileContents(String filePath){
        String fileContents = "";

        try{
            fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch(Exception e){
            System.out.println("Error opening exp file: " + filePath);
        }

        return fileContents;
    }

    public void Resolve(){
        //Magic?
    }

}