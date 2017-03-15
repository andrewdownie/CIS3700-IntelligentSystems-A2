import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*; 
import java.util.List; 
import java.util.Arrays;

public class ExpToCnf{
    public static void main(String[] args){
        System.out.println("\n---- Starting program ExpToCnf ----");


        String expFile_Contents = "";
        String expFile_Path, cnfFile_Path;

        expFile_Path = args[0];
        cnfFile_Path = args[1];

        ///
        /// Read teh ExpFile in
        try{
            expFile_Contents = new String(Files.readAllBytes(Paths.get(expFile_Path)));
        }
        catch(Exception e){
            System.out.println("Error opening exp file: " + expFile_Path);
        }


        ///
        /// Print the contents of ExpFile for human verification
        System.out.println("\n\t---- ExpFile contents ----");
        System.out.println(expFile_Contents);
        System.out.println("");

        ///
        /// Run the getCnf algorithm
        ///
        String cnfResult = getCnf(expFile_Contents);

        System.out.println("Result is: " + cnfResult);   

        CleanTautologies(cnfResult);

    }


    public static String CleanTautologies(String cnfExpression){
        //split by ands
        //go through each symbol, and check if its opposite exists         

        List<String> splitCnf = new ArrayList<String>(Arrays.asList(cnfExpression.split("\\^")));
        String[] curOr;

        //for (String currentItem: splitCnf ) {
        Iterator<String> iterator = splitCnf.iterator();
        while (iterator.hasNext()) {
            String currentItem = iterator.next();
            String tempOr = currentItem.replace("(","");
            tempOr = tempOr.replace(")", "");
            tempOr = tempOr.replace(" ", "");
            curOr = tempOr.split("v");

            for(int j = 0; j < curOr.length; j++){
                //System.out.println("WAT: " + curOr[j]);
                for(int k = 0; k < curOr.length; k++){

                    if(j != k){
                        //System.out.println("Cur or: " + curOr[j] + " : " + curOr[k]);
                        if(("~" + curOr[j]).trim().equals(curOr[k].trim())){

                            iterator.remove();

                        }
                    }

                }
                
            }
        }

        String result = ""; 


        for(int i = 0; i < splitCnf.size(); i++){
            result += splitCnf.get(i).trim();

            if(i < splitCnf.size() - 1){
                result += " ^ ";
            }
        }

        System.out.println(result);
        return result; 
    }



    public static String getCnf(String expression){
        System.out.println("Convert: " + expression);

        String result = "";

        int openBrackets = 0;
        int closedBrackets = 0;

        expression = expression.trim(); 



        ///
        /// Rule 2: a
        ///
        if(expression.charAt(0) == '~'){
            if(expression.charAt(1) == '('){
                String doubleNegative = expression.substring(2, expression.length() - 1);
                
                if(IsLiteral(doubleNegative)){
                    if(doubleNegative.charAt(0) == '~'){
                        return doubleNegative.trim().substring(1, doubleNegative.length());
                    }
                }
            }
        }



        ///
        /// Rule 2: b, c
        ///
        boolean applyDemorgans = false;
        if(expression.charAt(0) == '~'){
            if(expression.charAt(1) == '('){
                applyDemorgans = true; 
                expression = expression.substring(2, expression.length() - 1);
                //System.out.println("Apply demorgans");
            }
        }



        ///
        /// Rule 3,4,5 and 6
        ///
        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '('){
                openBrackets++; 
            }
            else if(expression.charAt(i) == ')'){
                closedBrackets++;
            }

            String propOp = FindPropositionalOperator(expression, i);

            if(openBrackets == closedBrackets){
                if(propOp != null){

                    String left = expression.substring(0, i);
                    String right = expression.substring(i + propOp.length(), expression.length());

                    if(applyDemorgans){
                        if(propOp.equals("^")){
                            propOp = "v";
                        }
                        else if(propOp.equals("v")){
                            propOp = "^";
                        }

                        left = "~" + left.trim();
                        right = "~" + right.trim();

                    }


                    //System.out.println("Convert the above demorgans");
                    result = Convert(left, right, propOp, applyDemorgans);
                }
            }



        }

        ///
        /// Rule 1. If s is a literal, return s
        ///
        if(IsLiteral(expression)){
            System.out.println("\tLiteral: " + expression);
            return expression;
        }

        ///
        /// If we got something new, then return the result
        ///
        if(!result.equals(expression)){
            //System.out.println("Result and expression were differnt");
            System.out.println("\tReturn: " + expression);
            return result;///////////////////////
        }



        return null;
    }





    public static String Convert(String left, String right, String operator, boolean applyDemorgans){
        String result = "";

        left = left.trim();
        right = right.trim();
        operator = operator.trim();


        ///
        /// Remove the outermost brackets
        ///
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


        ///
        /// Remove double ~~
        ///
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




        ///
        /// Check what the current operator is
        ///
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
            result = getCnf(left);
            result += " " + operator + " ";
            result += getCnf(right);

            if(!applyDemorgans){
                result = "(" + result + ")";
            }
        }
        else if(operator.equals("v")){
            //Do rule six here
            // send left and right to getCnf
            // split left and right by and's 
            // cross product left and right


            //how do you perform the cross product????
            String resultLeft = getCnf(left);
            String resultRight = getCnf(right);

            String[] leftSplit = resultLeft.split("\\^");
            String[] rightSplit = resultRight.split("\\^");

            String curLeft, curRight;

            result = "";

            for(int i = 0; i < leftSplit.length; i++){
                for(int j = 0; j < rightSplit.length; j++){

                    curLeft = leftSplit[i];
                    curRight = rightSplit[j];

                    curLeft = curLeft.replace("(", "");
                    curLeft = curLeft.replace(")", "");
                    curRight = curRight.replace("(", "");
                    curRight = curRight.replace(")", "");

                    curLeft = curLeft.trim();
                    curRight = curRight.trim();

                    result += "(" + curLeft + " v " + curRight + ")";

                    if(i + j < rightSplit.length + leftSplit.length - 2){
                        result += " ^ ";
                    }
                }
            }

        }

        if(applyDemorgans){
            System.out.println("Convert: " + result);
        }
        return result;
    }






    public static String FindPropositionalOperator(String expression, int index){

        if(index < expression.length() - 4 && expression.substring(index, index + 3).equals("<->")){
            return "<->";
        }
        else if(index < expression.length() - 3 && expression.substring(index, index + 2).equals("->") && !expression.substring(index -1, index).equals("<")){
            return "->";
        }
        else if(expression.substring(index, index + 1).equals("v")){
            return "v";
        }
        else if(expression.substring(index, index + 1).equals("^")){
            return "^";
        }



        return null;
    }





    public static boolean IsLiteral(String literal){
        boolean noSpaces = literal.length() == literal.replaceAll(" ", "").length();
        boolean openingBrackets = literal.contains(Character.toString('('));
        boolean closingBrackets = literal.contains(Character.toString(')'));
        
        return noSpaces && !openingBrackets && !closingBrackets;
    }

}