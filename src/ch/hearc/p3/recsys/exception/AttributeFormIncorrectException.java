package ch.hearc.p3.recsys.exception;

public class AttributeFormIncorrectException extends Exception
{

	private static final long	serialVersionUID	= 6205330568350236459L;

	public AttributeFormIncorrectException()
	{
		super();
	}

	public AttributeFormIncorrectException(String message)
	{
		super(message);
	}

	public AttributeFormIncorrectException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AttributeFormIncorrectException(Throwable cause)
	{
		super(cause);
	}

}
