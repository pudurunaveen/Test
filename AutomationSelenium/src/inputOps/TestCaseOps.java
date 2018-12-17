package inputOps;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.model.types.StshifAbstractType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import bsh.Capabilities;

import reports.TestSuiteReport;
import testcase.driverScript;
import testcase.testcase;
import dashboardUtil.BrowserOps;
import dashboardUtil.ScreenShotCaptureUtil;
import exceptions.DefaultExceptions;
import exceptions.ElementNotFoundException;
/**
 * Reads and processes the test case file
 * @author milind_gholap
 *
 */
public class TestCaseOps {

	Logger logger;

	public TestCaseOps(Logger logger) {
		this.logger = logger;
		//   report.initReport();	
	}
	HSSFWorkbook workbook = null;
	ArrayList<String[]> testCase;
	ArrayList<String[]> TCList;
	BrowserOps oBOps = null;
	WebDriver driver = null;
	InputParamOps iPOps = null;
	KeywordsExecutor oKWExe;
	TestSuiteReport oTSreport;
	List<String> lHeaders = new ArrayList<String>();
	public static List<String> Status;
	public static List<String> Bgcolor;
	public static List<String> Screnshotpath;
	public static int PassCounter = 0;
	public static int FailCounter = 0;
	public static int notExecutedCounter = 0;
	public static int numOfTCs = 0;
	public static boolean failFlag = false;
	public static String TestCaseStatus="";

	/**
	 * Executes the test case file 
	 * @param TCList
	 * @author milind_gholap
	 */
	public void ExecuteTestCases(ArrayList<String[]> TCList)
	{	

		iPOps = new InputParamOps(logger);
		oBOps = new BrowserOps(logger);
		oKWExe = new KeywordsExecutor(logger); 
		String sTCName ="";
		String isExecute="";
		String sPrereq = "";
		String onExit = "";
		boolean prereq_flag=false;
		boolean exit_flag = false;
		boolean print_header = true;
		Iterator<String[]> TCIterator = TCList.iterator();
		//driverScript.getoTSReport().setNumberOfTestCases(TCList.size());
		
		while (TCIterator.hasNext())
		{
			Status = new ArrayList<String>();
			Bgcolor = new ArrayList<String>();
			Screnshotpath = new ArrayList<String>();
			String[] TCname = (String[]) TCIterator.next();
			sTCName = TCname[0];
			isExecute = TCname[1];
			driverScript.getoTSReport().setTestCaseName(sTCName);
			if(TCname[2]!=null && (isExecute.equalsIgnoreCase("Y")||isExecute.equalsIgnoreCase("Yes"))){
				sPrereq = TCname[2];
				prereq_flag=true;
			}
			if(TCname[3]!=null&& (isExecute.equalsIgnoreCase("Y")||isExecute.equalsIgnoreCase("Yes"))){
				onExit = TCname[3];
				exit_flag = true;
			}
			if(isExecute.equalsIgnoreCase("Y")||isExecute.equalsIgnoreCase("Yes"))
			{
				numOfTCs = numOfTCs + 1;
				driver = oBOps.openDashboard();
				logger.info("Executing(" + sTCName + ")");
				if(print_header){printHeaderInfo(); print_header=false;	}
				//***********Prerequisite Test Case ************************
				if(prereq_flag)
				{
					logger.info(sTCName + " test case has prerequisite set to :" + sPrereq);
					logger.info("Running... " + sPrereq);
					ReadSheet(sPrereq);
					logger.info("Execution Steps " + testCase.size());
					try {
						oKWExe.ExecuteTestCase(driver, testCase);
					} catch (ElementNotFoundException e) {
						logger.log(Level.ERROR, "Error in prerequisite test case... remaining execution is aborted");
						ReadSheet(sTCName);
						driverScript.getoTSReport().setTestCaseDescription(testCase);
						Status.clear();
						Bgcolor.clear();
						int numOfSteps = testCase.size();
						int notExecuted=numOfSteps-Status.size();
						for (int i = 0; i < notExecuted ; i++) {
							Status.add("Not executed");
							Bgcolor.add("rgb(255, 255, 255);");
						}
						driver.quit();
						driverScript.getoTSReport().setStatus(Status);
						driverScript.getoTSReport().setBgcolor(Bgcolor);
						driverScript.getoTSReport().setNumberOfPassed(PassCounter);
						driverScript.getoTSReport().setNumberOfFailed(FailCounter);
						driverScript.getoTSReport().setNumberOfTestCases(numOfTCs);
						driverScript.getoTSReport().setTestCaseStatus("Not Executed");
						notExecutedCounter++;
						driverScript.getoTSReport().setNumberOfNotExecuted(notExecutedCounter);
						driverScript.getoTSReport().append();
						prereq_flag=false;
						exit_flag=false;
						continue;
					} 
					Status.clear();
					Bgcolor.clear();
					Screnshotpath.clear();
					prereq_flag=false;
				}

				//****************************************
				//*********Main test Case*******************
				logger.info("Running......... " + sTCName);
				ReadSheet(sTCName);
				logger.info("Execution Steps " + testCase.size());
				driverScript.getoTSReport().setTestCaseDescription(testCase);
				int numOfSteps = testCase.size();
				
				try {
					oKWExe.ExecuteTestCase(driver, testCase);
					//if(!failFlag)PassCounter = PassCounter + 1;
				} catch (ElementNotFoundException e) {
					Status.add("Fail");
					TestCaseStatus="Fail";
					Screnshotpath.add(ScreenShotCaptureUtil.captureScreenShot(logger, driver, sTCName));
					Bgcolor.add("rgb(255, 51, 0);");
					//FailCounter = FailCounter + 1;
					exit_flag=false;
				}
				finally{
					if(TestCaseStatus.equalsIgnoreCase("Fail"))
						FailCounter++;
					else PassCounter++;
					int notExecuted=numOfSteps-Status.size();
					for (int i = 0; i < notExecuted ; i++) {
						Status.add("Not executed");
						Screnshotpath.add("");
						Bgcolor.add("rgb(255, 255, 255);");
					}
				}
				//**********************************************
				//********On exit Test Case**********************
				if(exit_flag){
					logger.info("Logout on exit set to " + sTCName);
					ReadSheet(onExit);
					logger.info("Execution Steps " + testCase.size());
					try {
						oKWExe.ExecuteTestCase(driver,testCase);
					} catch (ElementNotFoundException e) {
						Status.add("Fail");
						Bgcolor.add("rgb(255, 51, 0);");
					}
					finally{
						/*driverScript.getoTSReport().setStatus(Status);
					driverScript.getoTSReport().setBgcolor(Bgcolor);*/
					}
					exit_flag = false;
				}
				//******************************************
				driver.quit();	
				driverScript.getoTSReport().setStatus(Status);
				driverScript.getoTSReport().setBgcolor(Bgcolor);
				driverScript.getoTSReport().setScreenshotURL(Screnshotpath);
				driverScript.getoTSReport().setNumberOfPassed(PassCounter);
				driverScript.getoTSReport().setNumberOfFailed(FailCounter);
				driverScript.getoTSReport().setNumberOfTestCases(numOfTCs);
				driverScript.getoTSReport().setTestCaseStatus(TestCaseStatus);
				driverScript.getoTSReport().append();
			}
			TestCaseStatus="";
		}
	}
	/**
	 * Prints report header
	 * @author milind_gholap
	 */
	private void printHeaderInfo(){
		String browserName = oBOps.getCapabilities().getBrowserName();
		String version = oBOps.getCapabilities().getVersion();
		Platform platform = oBOps.getCapabilities().getPlatform();
		String oSinfo = "OS :" +System.getProperty("os.name")+ ", Version :" + System.getProperty("os.version") +
		", Arch :" + System.getProperty("os.arch");

		logger.info("Browser name :"+browserName + version + 
				", Platform :" + platform + ", " +oSinfo);

		driverScript.getoTSReport().setBrowserName(browserName);
		driverScript.getoTSReport().setVersion(version);
		driverScript.getoTSReport().setPlatform(platform);
		driverScript.getoTSReport().setOSinfo(oSinfo);
		driverScript.getoTSReport().printHeaderDetails();
		//driverScript.getoTSReport().printHeaderSummary();
	}

	/**
	 *  Reads Test cases list from the TCList sheet of the test case .xls file
	 * @return ArrayList<String[]>
	 * @author Tanmay_Nakhate
	 */
	public ArrayList<String[]> readTCList()throws DefaultExceptions
	{
		iPOps = new InputParamOps(logger);
		oTSreport = new TestSuiteReport(logger);
		String[] sTCnames;
		TCList = new ArrayList<String[]>();
		int index;
		//String sPath =iPOps.getGlobalProps("tcfile_path");
		String sPath = iPOps.getTestCaseFilePath();
		if (sPath == null || sPath.trim().equals("")) {
			throw new DefaultExceptions("Input path is null, please check!");
		}
		getWorkBook(sPath);
		try {
			String TCListName = iPOps.getGlobalProps("SuiteName"); 
			int TCSheetNum = workbook.getSheetIndex(TCListName);
			String sSheetName = workbook.getSheetName(TCSheetNum);
			HSSFSheet sheet = workbook.getSheet(sSheetName);
			logger.info("Reading test case list........");

			int iRowCount = sheet.getLastRowNum();
			for(int i=0 ; i<=iRowCount; i++)
			{
				Row row = sheet.getRow(i);
				if(row==null)
					continue;
				if (row.getCell(0).getStringCellValue().startsWith("#"))
					continue;

				sTCnames = new String[4];
				index =0;
				int iCellCount = row.getLastCellNum();
				for(int j=0; j< iCellCount; j++)
				{
					Cell cell = row.getCell(j);
					if(cell==null)
					{
						sTCnames[index] = null;
						index++;
					}
					else {
						if(cell.getStringCellValue().startsWith("#"))
							break;
						String cellValue = getCellToString(cell);
						sTCnames[index]=cellValue;
						index++;
					}
				}
				TCList.add(sTCnames);
//				for(int p=0;p<index;p++)
//				{
//					logger.info("---------------"+sTCnames[index]);
//				}
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + "\n" + e.getStackTrace());
		}
		
		return TCList;

	}

	/**
	 * Open workbook to read for multiple test sheets
	 * @param sPath
	 * @throws DefaultExceptions
	 */
	private void getWorkBook(String sPath) throws DefaultExceptions
	{
		oTSreport = new TestSuiteReport(logger);
		File file;
		FileInputStream oFileInput;
		try {
			file = new File(sPath);
			if (!file.exists()) {
				throw new DefaultExceptions("Path \"" + file.getAbsolutePath() + "\" does not exist, please check!");
			}
			if (file.exists()) {
				logger.info("Loading test case from ...\"" + file.getAbsolutePath() + "\"");
			}

			//Set suite name
			String sFileName = file.getName();
			if(sFileName.endsWith(".xls"))
				sFileName = sFileName.replace(".xls", "");
			//oTSreport.setTestSuiteName(sFileName);
			Date date = new Date();
			driverScript.getoTSReport().setTestSuiteName(sFileName + "-" + (new SimpleDateFormat("yyyy-MM-dd HH_mm_ss").format(date)));

			oFileInput = new FileInputStream(file);
			//Get the workbook instance for XLS file
			workbook = new HSSFWorkbook(oFileInput);
			oFileInput.close();	

		} catch (FileNotFoundException e) {
			String sMessage = " Either file is missing or path is incorrect:" +sPath;
			logger.error(sMessage + " \n" + e.getLocalizedMessage());
		} catch (IOException e) {
			String sMessage = " IO exception";
			logger.error(sMessage + e.getLocalizedMessage() + "\n" + e.getStackTrace());
		} catch ( DefaultExceptions e){
			//String sMessage = "Critical.....";
			logger.error(e.getMessage());
		}
	}
	/**
	 * Reads test case sheet
	 * @param sTCName
	 * @author Tanmay_Nakhate
	 */

	public void ReadSheet(String sTCName)
	{
		String[] sTestStep;
		testCase = new ArrayList<String[]>();
		int i;
		sTCName = sTCName.toLowerCase();
		HSSFSheet sheet = workbook.getSheet(sTCName);
		//logger.info("Executing... " + sheet.getSheetName());
		//Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();
		int iRowNums = sheet.getLastRowNum();
		//logger.info("Execution steps: " + (iRowNums+1));
		while(rowIterator.hasNext())
		{ // Rows Iterator
			Row row = rowIterator.next();
			sTestStep = new String[5];
			i=0;
			//For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			if (row.getCell(0).getStringCellValue().startsWith("#"))
				continue;
			while(cellIterator.hasNext())
			{ // Cells Iterator
				Cell cell = cellIterator.next();
				if(cell.getStringCellValue().startsWith("#"))
				{
					break;
				}
				String cellValue = getCellToString(cell);

				sTestStep[i]=cellValue;
				i++;
			}
			//	testCaseInitializer();
			testCase.add(sTestStep);
		}
	}

	/**
	 * converts cell value to string
	 * @param cell
	 * @return cell value
	 */
	private String getCellToString(Cell cell) {
		// This function will convert an object of type excel cell
		int type = cell.getCellType();
		Object result;
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC: //0
			result = cell.getNumericCellValue();
			break;
		case HSSFCell.CELL_TYPE_STRING: //1
			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_FORMULA: //2
			throw new RuntimeException("We can't evaluate formulas in Java");
		case HSSFCell.CELL_TYPE_BLANK: //3
			result = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: //4
			result = cell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_ERROR: //5
			throw new RuntimeException ("This cell has an error");
		default:
			throw new RuntimeException("We don't support this cell type: " + type);
		}
		return result.toString();
	}

}
