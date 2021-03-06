package ch.hearc.p3.recsys.exception;

public class KeyNotFoundException extends Exception
{

	private static final long	serialVersionUID	= 11008780163571122L;

	public KeyNotFoundException()
	{
		super();
	}

	public KeyNotFoundException(String message)
	{
		super(message);
	}

	public KeyNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public KeyNotFoundException(Throwable cause)
	{
		super(cause);
	}

}
