package testcase;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import dashboardUtil.BrowserOps;
import tasks.LoginLogout;
import tasks.Servers;
import util.LogUtil;

public class VerifyServersPage {


	private static String sFullClassname = VerifyServersPage.class.getName();
	private static String sClassname = sFullClassname.substring(sFullClassname.indexOf(".")+1);
	private static Logger logger = Logger.getLogger(sClassname);
	private static BrowserOps oBOps = new BrowserOps(logger);
	private static WebDriver driver=null;
	/**
	 * @param args
	 * @param sObject 
	 * @param sBy 
	 */
	public static void main(String[] args, String sObject, String sBy) {
		LoginLogout dl = new LoginLogout(logger);
		Servers oSe = new Servers(logger);
		LogUtil ologLogUtil = new LogUtil();
		try {
			ologLogUtil.init(logger, sClassname);
			driver = oBOps.openDashboard();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			dl.login(driver);
			oSe.openServerPage(driver,logger);
//			oSe.verifyServersPageTexts(driver,logger);
			oSe.verifyServersData(driver,sBy,sObject,logger);
			oSe.closeServersTab(driver);

		} catch (Exception e) {

			String message = e.getMessage();
			String stacktrace = e.getStackTrace().toString();
			logger.error(message + stacktrace);
			e.printStackTrace();
		}
		finally
		{
			//driver.close();
		}
	}
}
