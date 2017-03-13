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

        System.out.println("Result is: " + getCnf(expFile_Contents));        
    }

    public static String getCnf(String expression){
        System.out.println("\nCurrent expression is: " + expression);

        int openBrackets = 0;
        int closedBrackets = 0;
        
        ///
        /// Rule 1. If s is a literal, return s
        ///
        if(IsLiteral(expression)){
            return expression;
        }

        ///
        /// Rule 2: a, b, c
        ///
        if(expression.charAt(0) == '~'){
            System.out.println("Expression starts with ~");
            
        }

        ///
        /// Rule 3,4, and 5
        ///
        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '('){
                openBrackets++; 
            }
            else if(expression.charAt(i) == ')'){
                closedBrackets++;
            }

            String propOp = PropositionalOperator(expression, i);
            //System.out.println(propOp);

            if(openBrackets == closedBrackets){
                if(propOp != null){
                    System.out.println("found PropositionalOperator at index: " + i);
                    String left = expression.substring(0, i);
                    String right = expression.substring(i + propOp.length(), expression.length());
                    return Convert(left, right, propOp);
                }
            }


        }


        return null;
    }

    public static String Convert(String left, String right, String operator){
        String result = "";

        left = left.trim();
        right = right.trim();
        operator = operator.trim();


        //TODO: does this bracket handling need to be more robust
        if(left.charAt(0) == '('){
            if(left.charAt(left.length() - 1) == ')'){
                left = left.substring(1, left.length() - 1);
            }
        }

        if(right.charAt(0) == '('){
            if(right.charAt(right.length() - 1) == ')'){
                right = right.substring(1, right.length() - 1);
            }
        }


        System.out.println("Left: " + left);
        System.out.println("Right: " + right);
        System.out.println("Operator: " + operator);

        if(operator.equals("<->")){
            result = getCnf("(" + left + " ^ " + right + ")" + " v " + "(~" + left + " ^ " + "~" + right + ")");
        }
        else if(operator.equals("v") || operator.equals("^")){
            result = getCnf(left);
            result += " " + operator + " ";
            result += getCnf(right);
        }

        return result;
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

        if(expression.substring(index, index + 1).equals("^")){
            return "^";
        }



        return null;
    }


    public static boolean IsLiteral(String literal){
        //This function defines a literal as being a string without any spaces in it
        return literal.length() == literal.replaceAll(" ", "").length();
    }

}