import java.nio.file.Files;
import java.nio.file.Paths;

public class ExpToCnf{
    public static void main(String[] args){
        System.out.println("\n\t---- Starting program ExpToCnf ----\n");


        String expFile_Contents = "";
        String expFile_Path, cnfFile_Path;

        expFile_Path = args[0];
        cnfFile_Path = args[1];

        try{
            expFile_Contents = new String(Files.readAllBytes(Paths.get(expFile_Path)));
        }
        catch(Exception e){
            System.out.println("Error opening exp file: " + expFile_Path);
        }

        System.out.println("\n\t---- ExpFile contents ----");
        System.out.println(expFile_Contents);
        System.out.println("");

        System.out.println(getCnf(expFile_Contents));        
    }

    public static String getCnf(String expression){
        //look for first operator with a open bracket count of zero
        int openBrackets = 0;

        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '('){
                openBrackets++; 
            }
            else if(expression.charAt(i) == ')'){
                openBrackets--;
            }

            String propOp = PropositionalOperator(expression, i);
            System.out.println(propOp);
            if(propOp != null){
                if(openBrackets == 0){
                    System.out.println("found PropositionalOperator at index: " + i);
                    String left = expression.substring(0, i);
                    String right = expression.substring(i + propOp.length(), expression.length() - 1);
                    System.out.println(left);
                    System.out.println(propOp);
                    System.out.println(right);
                }
            }


        }

        return "this is getCnf";
    }


    public static String PropositionalOperator(String expression, int index){
        if(expression.substring(index, index + 3).equals("<->")){
            return "<->";
        }    



        return null;
    }

}