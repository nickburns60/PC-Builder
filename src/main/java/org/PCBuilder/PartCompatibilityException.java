package org.PCBuilder;

public class PartCompatibilityException extends Exception{
    public PartCompatibilityException(){
        super();
    }
    public PartCompatibilityException(String message){
        super(message);
    }
    public PartCompatibilityException(String message, Exception cause){
        super(message, cause);
    }
}
