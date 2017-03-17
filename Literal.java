public class Literal{

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

    public String GetSymbolWithSign(){
        if(sign == true){
            return "~" + symbol.trim();
        }

        return symbol.trim();
    }

    public boolean Compare(Literal other){
        if(sign = other.sign){
            if(symbol.equals(other.symbol)){
                return true;
            }
        }
        return false;
    }

    public boolean SameSymbol(Literal other){
        if(symbol.equals(other.symbol)){
            return true;
        }
        return false;
    }

    public boolean IsOpposite(Literal other){
        if(sign != other.sign){
            if(symbol.equals(other.symbol)){
                return true;
            }
        }
        return false;
    }


}