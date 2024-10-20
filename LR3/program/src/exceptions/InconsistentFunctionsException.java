package exceptions;

public class InconsistentFunctionsException extends RuntimeException{
    public InconsistentFunctionsException(){
        super();
    }
    public InconsistentFunctionsException(String massage){
        super(massage);
    }
}
