package exceptions;

public class CommonException extends DefaultExceptions {
	
	public CommonException(){
		super();
	}
	
	public CommonException(String message)
	{
		super(message);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("Common Exception : Problem During execution");
	}
	
}
