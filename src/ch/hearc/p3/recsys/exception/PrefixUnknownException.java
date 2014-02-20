package ch.hearc.p3.recsys.exception;

public class PrefixUnknownException extends Exception
{

	private static final long	serialVersionUID	= 6205330568350236459L;

	public PrefixUnknownException()
	{
		super();
	}

	public PrefixUnknownException(String message)
	{
		super(message);
	}

	public PrefixUnknownException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public PrefixUnknownException(Throwable cause)
	{
		super(cause);
	}

}
