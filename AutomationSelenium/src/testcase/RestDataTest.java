package testcase;

import inputOps.InputParamOps;

import org.apache.log4j.Logger;
import tasks.GetRestData;
import util.LogUtil;

public class RestDataTest {
	
	private static String sFullClassname = RestDataTest.class.getSimpleName();
	private static String sClassname = sFullClassname.substring(sFullClassname.indexOf(".")+1);
	private static Logger logger = Logger.getLogger(sClassname);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetRestData oRestData = new GetRestData(logger);
		LogUtil ologLogUtil = new LogUtil();
		
		try {
			ologLogUtil.init(logger, sClassname);
			InputParamOps.setGlobalFilePath(args[0]);
			//driver = oBOps.openRestInFF();
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//oRestData.getClusterData(driver, "clusters_data");
			oRestData.getClustersData();
			//oRestData.printClustersData();
			//oRestData.getServersData();
			//oRestData.printServersData();
		} catch (Exception e) {

			String message = e.getMessage();
			String stacktrace = e.getStackTrace().toString();
			logger.error(message + stacktrace);
		}
		finally
		{
			//driver.close();
		}

	}

}
