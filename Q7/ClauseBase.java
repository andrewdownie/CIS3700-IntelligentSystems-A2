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
            output += clauses.get(i).GetDisjunction();

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

    public boolean Resolution(){
        List<Clause> newClauses = new LinkedList<Clause>();
        List<Clause> resolvents = new LinkedList<Clause>();
        Clause clause1, clause2;


        while(true){

            ///
            /// Perform cross product on the clause list
            ///
            for(int i = 0; i < clauses.size() - 1; i++){
                clause1 = clauses.get(i);

                for(int j = i; j < clauses.size(); j++){
                    clause2 = clauses.get(j);

                    resolvents = Resolve(clause1, clause2);

                    if(resolvents == null){
                        return true;
                    }

                    newClauses.addAll(resolvents);
                }
            }

            ///
            /// Need to do a deep compare here I think
            ///
            if(HasAll(clauses, newClauses)){
                return false;
            }

            clauses.addAll(newClauses);
            newClauses = new LinkedList<Clause>();
        }
    }

    private boolean HasAll(List<Clause> container, List<Clause> containee){
        boolean matchFound = false;
        for(Clause c: container){
            for(Clause d: containee){
                if(c.HasAll(d)){
                    matchFound = true;
                    break;
                }
            }
            if(!matchFound){
                return false;
            }
        }

        return true;
    }



    private List<Clause> Resolve(Clause clause1, Clause clause2){
        System.out.println("1: " + clause1.GetDisjunction() + ", 2: " + clause2.GetDisjunction());
        List<Clause> result = new LinkedList<Clause>();
        result.add(new Clause("M"));

        return result;
    }

}