package exceptions;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;

public class ElementNotFoundException extends DefaultExceptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger;
	
	public ElementNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	public ElementNotFoundException(Logger logger) {
		this.logger=logger;
	}
	public ElementNotFoundException(Logger logger, String sMessage)
	{
		super(sMessage);
		logger.error(sMessage);
	}
	
	public ElementNotFoundException(Logger logger, NoSuchElementException e) {
		this.logger=logger;
		this.logger.log(Level.ERROR, e.getMessage());
	}
	public ElementNotFoundException(ElementNotVisibleException e) {
		//logger.log(Level.ERROR, e.getMessage());
		super(e);
	}
	
	public ElementNotFoundException(NoSuchFrameException e) {
		logger.log(Level.ERROR, e.getMessage());
	}
	public ElementNotFoundException(NoSuchElementException e) {
		logger.log(Level.ERROR, e.getMessage());
	}
	public ElementNotFoundException(String message, TimeoutException e) {
		super(message);
	}
	public ElementNotFoundException(IllegalArgumentException e)
	{
		super(e);
	}
	public ElementNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public ElementNotFoundException(JSONException e1) {
		// TODO Auto-generated constructor stub
		logger.log(Level.ERROR,e1.getMessage());
	}
	
	

}
