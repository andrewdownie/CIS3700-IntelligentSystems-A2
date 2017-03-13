import java.nio.file.Files;
import java.nio.file.Paths;

public class ExpToCnf{
    public static void main(String[] args){
        System.out.println("\n---- Starting program ExpToCnf ----");


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
        System.out.println("\nCurrent expression is: " + expression);

        String result = "";
        int openBrackets = 0;
        int closedBrackets = 0;

        if(IsLiteral(expression)){
            return expression;
        }

        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '('){
                openBrackets++; 
            }
            else if(expression.charAt(i) == ')'){
                closedBrackets++;
            }

            String propOp = PropositionalOperator(expression, i);
            //System.out.println(propOp);

            if(propOp != null){
                if(openBrackets == closedBrackets){
                    System.out.println("found PropositionalOperator at index: " + i);
                    String left = expression.substring(0, i);
                    String right = expression.substring(i + propOp.length(), expression.length() - 1);
                    result = Convert(left, right, propOp);
                    break;
                }
            }


        }

        return result;
    }

    public static String Convert(String left, String right, String operator){
        left = left.trim();
        right = right.trim();
        operator = operator.trim();


        //TODO: does this bracket handling need to be more robust
        if(left.charAt(0) == '(' && left.charAt(left.length() -1) == ')'){
            left = left.substring(1, left.length());
        }

        if(right.charAt(0) == '(' && right.charAt(right.length() -1) == ')'){
            right = right.substring(1, right.length());
        }

        System.out.println("Left: " + left);
        System.out.println("Right: " + right);
        System.out.println("Operator:" + operator);

        if(operator.equals("<->")){
            getCnf("(" + left + " ^ " + right + ")" + " v " + "(~" + left + " ^ " + "~" + right + ")");
        }
        else if(operator.equals("v")){

        }

        return "fart";
    }


    public static String PropositionalOperator(String expression, int index){
        if(index < expression.length() - 4){
            if(expression.substring(index, index + 3).equals("<->")){
                return "<->";
            }    
        }

        if(expression.substring(index, index + 1).equals("v")){
            return "v";
        }



        return null;
    }


    public static boolean IsLiteral(String literal){
        //This function defines a literal as being a string without any spaces in it
        return literal.length() == literal.replaceAll(" ", "").length();
    }

}