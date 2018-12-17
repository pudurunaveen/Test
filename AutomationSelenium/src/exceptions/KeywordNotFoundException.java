package exceptions;

import org.apache.log4j.Logger;

public class KeywordNotFoundException extends DefaultExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private Logger logger;
	public KeywordNotFoundException(Logger logger)
	{
		this.logger=logger;
	}*/
	public KeywordNotFoundException() {
		super();
	}
	public KeywordNotFoundException(String message)
	{
		super(message);
	}
	
	 public KeywordNotFoundException(Throwable reason) {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("Keyword Not found");
	}
	
}
