package testcase;

import inputOps.InputParamOps;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import tasks.Clusters;
import tasks.LoginLogout;
import tasks.GetUIData;
import util.LogUtil;

import dashboardUtil.BrowserOps;
import exceptions.ElementNotFoundException;

public class VerifyClustersPage {

	private static String sFullClassname = VerifyClustersPage.class.getName();
	private static String sClassname = sFullClassname.substring(sFullClassname.indexOf(".")+1);
	private static Logger logger = Logger.getLogger(sClassname);
	private static BrowserOps oBOps = new BrowserOps(logger);
	private static WebDriver driver=null;

	/**
	 * @param args
	 * @param sBy 
	 * @param sObject 
	 * @throws ElementNotFoundException 
	 */
	public static void main(String[] args, String sBy, String sObject) throws ElementNotFoundException {
		
		LoginLogout dl = new LoginLogout(logger);
		Clusters cl = new Clusters(logger);
		GetUIData oUI = new GetUIData(logger);
		LogUtil ologLogUtil = new LogUtil();
		try {
			ologLogUtil.init(logger, sClassname);
			InputParamOps.setGlobalFilePath(args[0]);
			driver = oBOps.openDashboard();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			dl.login(driver);
		//	dl.logout(driver);
			cl.openClustersPage(driver);
		//	cl.verifyClustersPageTexts(driver);
			cl.verifyClustersData(driver,sBy,sObject);
			//cl.printClustersUIData();
			//cl.printClustersRESTData();
		//	dl.logout(driver);
		} catch (Exception e) {

			String message = e.getMessage();
			String stacktrace = e.getStackTrace().toString();
			logger.error(message + stacktrace);
		}
		finally
		{
		//	driver.close();
		}
	}
}
