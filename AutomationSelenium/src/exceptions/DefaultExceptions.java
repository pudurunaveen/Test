package exceptions;

public class DefaultExceptions extends Exception{

	private static final long serialVersionUID = 1L;

	public DefaultExceptions(){
		super();
	}
	
	public DefaultExceptions(String message){
		super(message);
	}
	
	public DefaultExceptions(String message, Throwable cause){
		super(message, cause);
	}
	
	public DefaultExceptions(Throwable cause){
		super(cause);
	}
}

