package utn.project.exceptions;

public class InvalidLoginException extends Throwable {
    public InvalidLoginException(String message ) {
        super(message);
    }

}