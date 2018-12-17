package testcase;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import reports.TestSuiteReport;
import inputOps.InputParamOps;
import inputOps.TestCaseOps;
import util.LogUtil;

public class driverScript {

	private static Logger logger = Logger.getLogger("Driver");
	private static TestSuiteReport oTSReport =  new TestSuiteReport(logger);
	//private static WebDriver driver=null;

	public static TestSuiteReport getoTSReport() {
		return oTSReport;
	}

	public static void setoTSReport(TestSuiteReport oTSReport) {
		driverScript.oTSReport = oTSReport;
	}

	/**
	 * Main driver script
	 * @param Global.properties path
	 */
	public static void main(String[] args) {

		LogUtil ologLogUtil = new LogUtil();
		//BrowserOps oBOps = new BrowserOps(logger);
		//KeywordsExecutor oKe = new KeywordsExecutor(logger);
		TestCaseOps oTCOps = new TestCaseOps(logger);
		ArrayList<String[]> TCList;
		
		try{
			//Initialize logger object
			ologLogUtil.init(logger, "Driver");
			//Check the argument
			if(args[0]==null)
				throw new ArrayIndexOutOfBoundsException("Provide Global.properties file path");
			
			//In case of changing project set the global file path here. 
			//in Run Configurations ${resource_path:/DashboardFramework/configFiles/Global.properties}
			
			InputParamOps.setGlobalFilePath(args[0]);
			//Read the test case list
			TCList = oTCOps.readTCList();
			//Initialize reports
			oTSReport.initReport();
			//Execute test suite
			oTCOps.ExecuteTestCases(TCList);
			//close report
			//oTSReport.closeReport();
			
		}catch(ArrayIndexOutOfBoundsException e){
			String message = e.getMessage();
			StackTraceElement[] stacktrace = e.getStackTrace();
			logger.error("Please check the Global.properties file path");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			oTSReport.closeReport();
		}
		}
}
