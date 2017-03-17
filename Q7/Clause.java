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

    public Clause(Clause clause1, Clause clause2){
        literals = new LinkedList<Literal>();

        for(Literal l: clause1.literals){
            literals.add(l);
        }
        for(Literal l: clause2.literals){
            literals.add(l);
        }
    }

    public Clause(){
        literals = new LinkedList<Literal>(); 
    }

    public boolean HasAllLiterals(Clause other){
        //Figure out if this instance has all the same literals that other has
        if(literals.size() >= other.literals.size()){
            //System.out.println("HasAllLiterals current: " + literals.size() + ", " + other.literals.size());    
            return false;
        }

        boolean matchFound = false;

        for(Literal o: other.literals){
            for(Literal l: literals){

                if(l.Compare(o)){
                    matchFound = true;
                } 

            }
            if(!matchFound){
                //System.out.println("No match on other: " + o.GetSymbolWithSign());
                return false;
            }
            matchFound = false;
        }

        return true;
    }

    public void Remove(Literal remove){
        literals.remove(remove);
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
