package reports;

import inputOps.InputParamOps;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;


/**
 * Generate report utility
 * @author milind_gholap
 *
 */

public class TestSuiteReport {

	Logger logger;
	public TestSuiteReport(Logger logger) {
		this.logger = logger;
	}

	public String testSuiteName;
	public String testCaseName;
	public ArrayList<String[]> testCaseDescription = new ArrayList<String[]>();
	public int numberOfTestCases; 
	public int numberOfSteps;
	public int numberOfFailed;
	public int numberOfPassed;
	public int numberOfNotExecuted;
	public String TestCaseStatus="";
	public List<String> status = new ArrayList<String>();
	public List<String> bgcolor= new ArrayList<String>();
	public String browserName;
	public String version;
	public Platform platform;
	public String OSinfo;
	public List<String> screenshotURL;
	FileOutputStream fOPStream, fOPStream2;
	BufferedOutputStream bOPStream, bOPStream2;
	PrintStream printStream,printStream2;
	File file;
	File file1;
	String DetailsfilePath = "";
	
	public String getTestCaseStatus() {
		return TestCaseStatus;
	}
	public void setTestCaseStatus(String testCaseStatus) {
		TestCaseStatus = testCaseStatus;
	}
	
	public int getNumberOfNotExecuted() {
		return numberOfNotExecuted;
	}
	public void setNumberOfNotExecuted(int numberOfNotExecuted) {
		this.numberOfNotExecuted = numberOfNotExecuted;
	}
	public int getNumberOfFailed() {
		return numberOfFailed;
	}
	public void setNumberOfFailed(int numberOfFailed) {
		this.numberOfFailed = numberOfFailed;
	}
	public int getNumberOfPassed() {
		return numberOfPassed;
	}
	public void setNumberOfPassed(int numberOfPass) {
		this.numberOfPassed = numberOfPass;
	}
	public List<String> getScreenshotURL() {
		return screenshotURL;
	}
	public void setScreenshotURL(List<String> screenshotURL) {
		this.screenshotURL = screenshotURL;
	}
	public List<String> getBgcolor() {
		return this.bgcolor;
	}
	public void setBgcolor(List<String> bgcolor) {
		this.bgcolor=bgcolor;
	}
	/*public void setBgcolor(String bgcolor) {
		this.bgcolor.add(bgcolor);
	}*/
	public int getNumberOfSteps() {
		return numberOfSteps;
	}
	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public ArrayList<String[]> getTestCaseDescription() {
		return testCaseDescription;
	}
	public void setTestCaseDescription(ArrayList<String[]> testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}

	public int getNumberOfTestCases() {
		return numberOfTestCases;
	}
	public void setNumberOfTestCases(int numberOfTestCases) {
		this.numberOfTestCases = numberOfTestCases;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform2) {
		this.platform = platform2;
	}

	public String getOSinfo() {
		return OSinfo;
	}
	public void setOSinfo(String oSinfo) {
		OSinfo = oSinfo;
	}

	/**
	 * Initialize the reports
	 */
	public void initReport()
	{
		InputParamOps IPOps = new InputParamOps(logger);
		logger.log(Level.INFO, "Initializing reports for TestSuite " + getTestSuiteName());
		file = new File(IPOps.getReportFilePath("reportFile_Path", getTestSuiteName()) + ".html");
		file1 = new File(IPOps.getReportFilePath("reportFile_Path", "Summary_"+getTestSuiteName()) + ".html");
		try {
			fOPStream = new FileOutputStream(file);
			bOPStream = new BufferedOutputStream(fOPStream);
			printStream = new PrintStream(bOPStream);
			printStream.print("<html>");
			printStream.print("<head><title>VMware Dashboard Automation Report</title></head>");

			fOPStream2 = new FileOutputStream(file1);
			bOPStream2 = new BufferedOutputStream(fOPStream2);
			printStream2 = new PrintStream(bOPStream2);
			printStream2.print("<html>");
			printStream2.print("<head><title>VMware Dashboard Automation Report</title></head>");

			DetailsfilePath = file.getCanonicalPath();
		} catch (FileNotFoundException e) {
			logger.error("Report file not found" + e.getMessage());
		} catch (IOException e) {
			logger.error("Error in report IO" + e.getMessage());
		}
	}
	/**
	 * Prints report header
	 */
	public void printHeaderDetails(){
		printStream.print("<body><h2 style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><strong>VMware Dashboard Automation Report</strong></span></h2>");
		printStream.print("<h4 style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><strong>" + getTestSuiteName()+ "</strong></span></h2>");
		printStream.print("<h6 style='text-align: center;'><span style='font-size:14px;'><span style='font-family: lucida sans unicode,lucida grande,sans-serif;'><strong>Browser :" + getBrowserName()+ " " + getVersion() + ", " + "Platform :" + getPlatform()+ ", "+ 
				getOSinfo() + "</strong></span></span></h6>");
		printStream.print("<table align='center' border='1' cellpadding='1' cellspacing='1' height='50' width='800'>");
		printStream.print("<tbody><tr>");
		printStream.print("<td style='background-color: rgb(204, 204, 204); text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><span style='font-size: 20px;'>Test Case Name</span></span></p></td>");
		printStream.print("<td style='text-align: center; background-color: rgb(204, 204, 204);'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><span style='font-size: 20px;'>Description</span></span></td>");
		printStream.print("<td style='background-color: rgb(204, 204, 204); text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><span style='font-size: 20px;'>Status</span></span></td>");
		printStream.print("</tr>");
		
		printStream2.print("<body><h2 style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><strong>VMware Dashboard Automation Report</strong></span></h2>");
		printStream2.print("<h4 style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><strong>" + getTestSuiteName()+ "</strong></span></h2>");
		printStream2.print("<h6 style='text-align: center;'><span style='font-size:14px;'><span style='font-family: lucida sans unicode,lucida grande,sans-serif;'><strong>Browser :" + getBrowserName()+ " " + getVersion() + ", " + "Platform :" + getPlatform()+ ", "+ 
				getOSinfo() + "</strong></span></span></h6>");
	}
	
	public void printHeaderSummary(){
		printStream2.print("<body><h2 style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><strong>VMware Dashboard Automation Report</strong></span></h2>");
		printStream2.print("<h4 style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'><strong>" + getTestSuiteName()+ "</strong></span></h2>");
		printStream2.print("<h6 style='text-align: center;'><span style='font-size:14px;'><span style='font-family: lucida sans unicode,lucida grande,sans-serif;'><strong>Browser :" + getBrowserName()+ " " + getVersion() + ", " + "Platform :" + getPlatform()+ ", "+ 
				getOSinfo() + "</strong></span></span></h6>");
	}
	
	
	/**
	 * Append the report during execution of the test suite<br>
	 * in a tabular format.
	 */
	public void append()
	{
		//int numOfsteps = testCaseDescription.size()+1;
		//oPs.print("<tr><td colspan='1' rowspan='"+numOfsteps+"' style='text-align: center;'>" + getTestCaseName() + "</td></tr>");
		int failStepCounter=0;
		//boolean isFail=false;
		boolean isPass =true;
		List<String> TCDescription = new ArrayList<String>();
		List<String> Failstatus = new ArrayList<String>();
		List<String> FailScreenshot = new ArrayList<String>();
	/*	for (int i = 0; i < testCaseDescription.size(); i++) {

			String[] testStep = testCaseDescription.get(i);
			//for (int j = 0; j < testStep.length; j++) {
			String bgcolr = bgcolor.get(i);
			String sTCData ="";
			if(status.get(i).equalsIgnoreCase("Pass")){
				if(!(sTCData.isEmpty()))
					sTCData ="<tr><td></td><td style='text-align: center; background-color: "+bgcolor.get(i)+"'>"+ status.get(i)+ "</td></tr>";
			}
			if(screenshotURL.get(i)!=""){
				sTCData = sTCData.concat("<tr><td>" + getTestStep(testStep) + 
						"</td><td style='text-align: center; background-color: "+bgcolor.get(i)+"'>"+ status.get(i)+ " "+
						"<a href='file:///" + screenshotURL.get(i) + "' onclick='window.open(this.href, 'ScreenShotName', 'resizable=yes,status=no,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=no,dependent=no'); return false;'>(Click here to see the error page)</a></td></tr>");
			}
			oPs.print(sTCData);
		}
		*/
		for (int i = 0; i < testCaseDescription.size(); i++) {
			if(status.get(i).equalsIgnoreCase("Fail")){
				String[] testStep = testCaseDescription.get(i);
				failStepCounter ++;
				isPass=false;
				TCDescription.add(getTestStep(testStep));
				Failstatus.add(status.get(i));
				FailScreenshot.add(screenshotURL.get(i));
			}
		}
		
		if((getTestCaseStatus().equalsIgnoreCase("Not Executed")))
		{
			String sTC = "<tr><td style='text-align: center;'>"+getTestCaseName()+"</td><td style='text-align: center;'>"+getTestCaseName()+"</td><td style='text-align: center;'>Not Executed</td></tr>";
			printStream.print(sTC);
		}else if(isPass){
			String sTC = "<tr><td style='text-align: center;'>"+getTestCaseName()+"</td><td style='text-align: center;'>"+getTestCaseName()+"</td><td style='text-align: center; background-color: rgb(102, 204, 0);'>Pass</td></tr>";
			printStream.print(sTC);
		}
		else
		{
			for(int i=0;i< failStepCounter; i++){
				String testStep = TCDescription.get(i);
				String FailMessage = Failstatus.get(i);
				String FailURL = FailScreenshot.get(i);
				if(i==0){
					printStream.print("<tr><td colspan='1' rowspan='" + (failStepCounter)+"' style='text-align: center;'>" + getTestCaseName()+"</td>");
					String sTC = "<td style='text-align: center;'> "+ testStep + 
					"</td><td style='text-align: center; background-color: rgb(255, 0, 0);'>"+ FailMessage +" "+
					"<a href='file:///" + FailURL + "' onclick='window.open(this.href, 'ScreenShotName', 'resizable=yes,status=no,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=no,dependent=no'); return false;'>(Click here to see the error page)</a></td></tr>";
					printStream.print(sTC);
				}else{
				String sTC = "<tr><td style='text-align: center;'> "+ testStep + 
				"</td><td style='text-align: center; background-color: rgb(255, 0, 0);'>"+ FailMessage +" "+
				"<a href='file:///" + FailURL + "' onclick='window.open(this.href, 'ScreenShotName', 'resizable=yes,status=no,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=no,dependent=no'); return false;'>(Click here to see the error page)</a></td></tr>";
				printStream.print(sTC);
				}
			}
		}
	}
	/**
	 * Close the report
	 */
	public void closeReport(){
		//printStream.print("</tbody>");
		//printStream.print("</table>");
		printStream2.print("<table align='center' border='1' cellpadding='1' cellspacing='1' style='width: 500px;'>");
		printStream2.print("<caption><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>TestSuite Summary</span></caption>");
		printStream2.print("<tbody><tr><td><span style='font-family:lucida sans unicode,lucida grande,sans-serif; width: 300px;'>Number of test cases </span></td>");
		printStream2.print("<td><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>" + getNumberOfTestCases() +"</span></td></tr>");
		printStream2.print("<tr><td><span style='font-family:lucida sans unicode,lucida grande,sans-serif; width: 300px;'>Number of Passed test cases</span></td>");
		printStream2.print("<td><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>" + getNumberOfPassed() + "</span></td></tr>");
		printStream2.print("<tr><td><span style='font-family:lucida sans unicode,lucida grande,sans-serif; width: 300px;'>Number of Failed test cases</span></td>");
		printStream2.print("<td><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>"+getNumberOfFailed()+"</span></td></tr>");
		printStream2.print("<tr><td><span style='font-family:lucida sans unicode,lucida grande,sans-serif; width: 300px;'>Number of Not Executed test cases</span></td>");
		printStream2.print("<td><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>"+getNumberOfNotExecuted()+"</span></td></tr>");

		printStream2.print("</tbody></table>");
		printStream2.print("<p style='text-align: center;'><a href='file:///"+DetailsfilePath+"'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>Click here to see detailed report</span></a></p>");
		printStream2.print("</body>");
		printStream2.print("</html>");
		printStream2.close();
		/*oPs.print("<p style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>Number of test cases :" + 
				getNumberOfTestCases() + "</span></p>");
		oPs.print("<p style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>Number of Passed test cases :" + 
				getNumberOfPassed() + "</span></p>");
		oPs.print("<p style='text-align: center;'><span style='font-family:lucida sans unicode,lucida grande,sans-serif;'>Number of Failed test cases :" + 
				getNumberOfFailed()+ "</span></p>");*/
		printStream.print("</tbody>");
		printStream.print("</table>");
		printStream.print("</body>");
		printStream.print("</html>");
		printStream.close();
	}
	private String getTestStep(String[] testStep)
	{
		String sTStep = "";
		for (int i = 0; i < testStep.length; i++) {

			if(testStep[i]==null)
				continue;
			sTStep = sTStep + " | " + testStep[i];
		}
		return sTStep;

	}
}