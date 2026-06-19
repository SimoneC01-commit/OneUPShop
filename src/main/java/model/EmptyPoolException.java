package model;

public class EmptyPoolException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EmptyPoolException(String message) {
        super(message);
    }
}