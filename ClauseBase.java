import java.util.LinkedList;
import java.util.*;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClauseBase{

    public List<Clause> clauses;
    public Clause query;

    public ClauseBase(){
        clauses = new LinkedList<Clause>();
    }


    public String GetConjunction(){
        return Conjunctify(clauses);
    }

    private String Conjunctify(List<Clause> clauseList){
        String output = "";

        for(int i = 0; i < clauseList.size(); i++){
            output += clauseList.get(i).GetDisjunction();

            if(i < clauseList.size() - 1){
                output +=  " ^ ";
            }
        }

        return output;
    }

    public void LoadQuery(String filePath){
       System.out.println("\nClauses in file: " + filePath);
       String rawClause = FileContents(filePath); 
       String splitClause = rawClause.split("[\\r\\n]+")[0];
       Clause q = new Clause(splitClause);

       System.out.println("\t" + splitClause);

       query = q;
       clauses.add(q);

    }

    public void LoadClauses(String filePath){
        System.out.println("\nClauses in file: " + filePath);
        String rawClauses = FileContents(filePath);
        String[] splitClauses = rawClauses.split("[\\r\\n]+");

        for(String clause: splitClauses){
            System.out.println("\t" + clause); 
            clauses.add(new Clause(clause));
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

        int resolutionCount = 0;

        System.out.println("\n---> Starting resolution algorithm <---");

        while(true){
            resolutionCount++;

            System.out.println("\nResolution round: " + resolutionCount);
            System.out.println("\tClause count of clause base: " + clauses.size()); 
            System.out.println("\tClause count of (new): " + newClauses.size());

            ///
            /// Resolve each clause against everyother clause  
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
            /// If there  is nothing new, return false  
            ///
            if(HasAllClauses(clauses, newClauses) || newClauses.size() > 10000){
                return false;
            }

            ///
            /// If there was something new, add it, and iterate again
            ///
            for(Clause newClause: newClauses){
                clauses.add(newClause);
            }

        }

    }

    private boolean HasAllClauses(List<Clause> container, List<Clause> containee){

        boolean matchFound = false;
        for(Clause d: containee){
            for(Clause c: container){

                if(c.HasAllLiterals(d)){
                    
                    matchFound = true;
                    break;
                }
            }
            if(!matchFound){
                return false;
            }
            matchFound = false;
        }

        return true;
    }



    private List<Clause> Resolve(Clause clause1, Clause clause2){
        List<Literal> complimentList = new LinkedList<Literal>();
        List<Clause> result = new LinkedList<Clause>();


        ///
        ///  Check for complimentry literals, for every pair of complimentry literals found between clause1 and clause2
        ///         create a new clause that is a bigger disjuction of the two clauses, but does not contain that complimentry pair
        for(Literal l1: clause1.literals){
            for(Literal l2: clause2.literals){

                if(l1.IsOpposite(l2)){
                    Clause newClause = new Clause(clause1, clause2);
                    newClause.Remove(l1);
                    newClause.Remove(l2);
                    result.add(newClause);
                }

            }
        }

        result.add( new Clause(clause1.GetDisjunction() + " v " + clause2.GetDisjunction()) );

        return result;
    }

}