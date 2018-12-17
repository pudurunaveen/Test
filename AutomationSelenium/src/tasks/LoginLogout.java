package tasks;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import appObject.ElementsTasks;



public class LoginLogout extends ElementsTasks{
	
	//public Logger oLog = Logger.getLogger(DashboardLogin.class.getName());
	private String classname = LoginLogout.class.getSimpleName();
	private Logger logger;
	public LoginLogout(Logger logger)
	{
		super(logger);
		this.logger=logger;
	}
	public void login(WebDriver driver) throws Exception
	{	
		logger.info(classname);
		sendKeys(driver, "uname_text_id","milind");
		sendKeys(driver, "pass_text_id", "milind");
		clickElement(driver, "login_btn_id");
		if(isElementPresent(driver, "submit_again_btn_name"));
			clickElement(driver, "submit_again_btn_name");
	}
	
	public void logout(WebDriver driver) throws Exception
	{
		logger.info(classname + ": User is logging out........");
		selectFrame(driver, "logout_frame_relative");
		clickElement(driver, "user_btn_xpath");
		clickElement(driver, "user_btn_name");
		clickElement(driver, "logout_btn_id");
	}
}
