public class Literal{
    //Literal: It maintains a literal as a symbol and a sign. The symbol is a Java String. The sign is Boolean and
    //         indicate whether the literal is positive or negative.

    public boolean sign;
    public String symbol;


    public Literal(String literal){
        sign = false;

        if(literal.charAt(0) == '~'){
            sign = true;
            literal = literal.substring(1, literal.length());
        }

        symbol = literal;
    }

    public String GetLiteralWithSign(){
        if(sign == true){
            return "~" + symbol;
        }

        return symbol;
    }


}