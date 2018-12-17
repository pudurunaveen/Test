package testcase;

import inputOps.InputParamOps;

import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import dashboardUtil.BrowserOps;
import tasks.Clusters;
import tasks.LoginLogout;
import tasks.Servers;
import util.LogUtil;

public  class testcase {

	private static String sFullClassname = testcase.class.getName();
	private static String sClassname = sFullClassname.substring(sFullClassname.indexOf(".")+1);
	private static Logger logger = Logger.getLogger(sClassname);
	private static BrowserOps oBOps = new BrowserOps(logger);
	private static WebDriver driver=null;
	private static InputParamOps oRops = new InputParamOps(logger);
	/**
	 * @param args
	 * @param sBy 
	 * @param sObject 
	 * @throws Exception 
	 */
	public static  void main(String[] args, String sBy, String sObject) {
		LoginLogout dl = new LoginLogout(logger);
		Clusters cl = new Clusters(logger);
		Servers oSe = new Servers(logger);
		LogUtil ologLogUtil = new LogUtil();
		try {
			ologLogUtil.init(logger, sClassname);
			driver = oBOps.openDashboard();
			int iTimout = Integer.parseInt(oRops.getGlobalProps("implicit_timout"));
			driver.manage().timeouts().implicitlyWait(iTimout, TimeUnit.SECONDS);
			dl.login(driver);
			cl.openClustersPage(driver);
			cl.verifyClustersPageTexts(driver, logger, sObject);
			cl.verifyClustersData(driver,sBy,sObject);
			cl.closeClustersTab(driver);
			oSe.openServerPage(driver,logger);
			oSe.verifyServersPageTexts(driver,logger, sObject);
			oSe.verifyServersData(driver,sBy,sObject,logger);
			oSe.serverSituation(driver, sBy, sObject, logger);
			dl.logout(driver);
		} catch (Exception e) {

			String message = e.getMessage();
			String stacktrace = e.getStackTrace().toString();
			logger.error(message + stacktrace );
		}
		finally
		{
			driver.close();
		}
	}
}
