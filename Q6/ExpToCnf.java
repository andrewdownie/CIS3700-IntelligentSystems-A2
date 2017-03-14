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
        System.out.println("\n\nCurrent expression is: " + expression);

        String result = "";

        int openBrackets = 0;
        int closedBrackets = 0;

        expression = expression.trim(); 

        ///
        /// Rule 1. If s is a literal, return s
        ///
        if(IsLiteral(expression)){
            System.out.println("expression was a literal");
            return expression;
        }


        ///
        /// Rule 2: a
        ///
        //System.out.println(expression.substring(0, 3));
        //if(expression.substring(0, 3).equals("~(~")){
        if(expression.charAt(0) == '~'){
            if(expression.charAt(1) == '('){
                String doubleNegative = expression.substring(2, expression.length() - 1);
                
                if(IsLiteral(doubleNegative)){
                    if(doubleNegative.charAt(0) == '~'){
                        return doubleNegative.trim().substring(1, doubleNegative.length());
                    }
                }

            //this is when there is a not at the front, and literals within brackets.
            }
        }



        boolean applyDemorgans = false;
        ///
        /// Rule 2: b, c
        ///
        if(expression.charAt(0) == '~'){
            if(expression.charAt(1) == '('){
                System.out.println("Apply demorgans");
                applyDemorgans = true; 
                expression = expression.substring(2, expression.length() - 1);
            }
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

                    if(applyDemorgans){
                        if(propOp == "^"){
                            propOp = "v";
                        }
                        else if(propOp == "v"){
                            propOp = "^";
                        }

                        left = "~" + left.trim();
                        right = "~" + right.trim();
                    }


                    result = Convert(left, right, propOp);
                }
            }



        }

        if(!result.equals(expression)){
            System.out.println("Result and expression were differnt");
            return result;///////////////////////
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

        for(int i = 0; i < left.length() - 1; i++){
            if(left.charAt(i) == '~' && left.charAt(i + 1) == '~'){
                left = left.substring(0, i) + left.substring(i + 2, left.length());
            }
        }

        for(int i = 0; i < right.length() - 1; i++){
            if(right.charAt(i) == '~' && right.charAt(i + 1) == '~'){
                right = right.substring(0, i) + right.substring(i + 2, right.length());
            }
        }


        System.out.println("Left: " + left);
        System.out.println("Right: " + right);
        System.out.println("Operator: " + operator);

        if(operator.equals("<->")){
            if(!IsLiteral(left)){
                left = "(" + left + ")"; 
            }
            if(!IsLiteral(right)){
                right = "(" + right + ")";
            }

            result = getCnf("(" + left + " ^ " + right + ")" + " v " + "(~" + left + " ^ " + "~" + right + ")");
        }
        else if(operator.equals("^")){
            result = "(" + getCnf(left);
            result += " " + operator + " ";
            result += getCnf(right) + ")";
        }
        else if(operator.equals("v")){
            //Do rule six here
            // send left and right to getCnf
            // split left and right by and's 
            // cross product left and right


            //how do you perform the cross product????
            String resultLeft = getCnf(left);
            String resultRight = getCnf(right);

            String[] leftSplit = resultLeft.split("^");
            String[] rightSplit = resultRight.split("^");

            result = "";

            for(int i = 0; i < leftSplit.length; i++){
                for(int j = 0; j < rightSplit.length; j++){
                    result += "(" + leftSplit[i] + " v " + rightSplit[j] + ")";
                }
            }

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
        boolean noSpaces = literal.length() == literal.replaceAll(" ", "").length();
        boolean openingBrackets = literal.contains(Character.toString('('));
        boolean closingBrackets = literal.contains(Character.toString(')'));
        
        return noSpaces && !openingBrackets && !closingBrackets;
    }

}