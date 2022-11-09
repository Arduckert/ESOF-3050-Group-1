package src.protocol;

/**
 * Parameter Exception
 * @author Written by Connor McNally on 11/08/2022
 * 
 * @apiNote The Parameter Exception is thrown when an invalid number of parameters
 * are passed for the specific data type
 */
public class ParameterException extends Exception
{
	@java.io.Serial
	private static final long serialVersionUID = 7718828512143293558L;
	
	//passes default message to superclass
	public ParameterException()
	{
		super();
	}
	
	//passes custom message to superclass
	public ParameterException(String msg)
	{
		super(msg);
	}
}
