import java.util.LinkedList;
import java.util.*;
import java.util.List;

public class Clause{
    //Clause: It maintains a list of Literals in the clause and represents their disjunction

    public List<Literal> literals;

    public Clause(String disjunction){
        literals = new LinkedList<Literal>(); 

        disjunction = disjunction.replace("(", "");
        disjunction = disjunction.replace(")", "");

        String[] _literals = disjunction.split("v");

        boolean isPositive;
        for(String literal: _literals){
            literals.add(new Literal(literal));
        }
    }

    public Clause(){
        literals = new LinkedList<Literal>(); 
    }

    public boolean HasAll(Clause other){
        if(literals.size() < other.literals.size()){
            return false;
        }

        boolean matchFound = false;

        for(Literal l: literals){
            for(Literal o: other.literals){

                if(l.Compare(o)){
                    matchFound = true;
                } 

            }
            if(!matchFound){
                return false;
            }
            matchFound = false;
        }

        return true;
    }


    public void AddLiteral(String literal){
        literals.add(new Literal(literal));
    }


    public String GetDisjunction(){
        String output = "";

        for(int i = 0; i < literals.size(); i++){
            output += literals.get(i).GetSymbolWithSign();

            if(i < literals.size() - 1){
                output += " v ";
            }
        }

        if(output != ""){
            output = "(" + output + ")";
        }

        return output;
    }
}
