package ch.hearc.p3.recsys;

public class KeyNotFoundException extends Exception {

	public KeyNotFoundException() {
		super();
	}

	public KeyNotFoundException(String message) {
		super(message);
	}

	public KeyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeyNotFoundException(Throwable cause) {
		super(cause);
	}

}
