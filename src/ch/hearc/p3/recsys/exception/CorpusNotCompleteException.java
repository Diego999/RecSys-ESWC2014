package ch.hearc.p3.recsys.exception;

public class CorpusNotCompleteException extends Exception
{
	private static final long	serialVersionUID	= -8012498914827987033L;

	public CorpusNotCompleteException()
	{
		super();
	}

	public CorpusNotCompleteException(String message)
	{
		super(message);
	}

	public CorpusNotCompleteException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CorpusNotCompleteException(Throwable cause)
	{
		super(cause);
	}

}
