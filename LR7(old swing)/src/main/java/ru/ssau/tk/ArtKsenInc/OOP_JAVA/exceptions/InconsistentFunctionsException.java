package ru.ssau.tk.ArtKsenInc.OOP_JAVA.exceptions;

public class InconsistentFunctionsException extends RuntimeException {
    public InconsistentFunctionsException() {
        super();
    }

    public InconsistentFunctionsException(String massage) {
        super(massage);
    }
}
