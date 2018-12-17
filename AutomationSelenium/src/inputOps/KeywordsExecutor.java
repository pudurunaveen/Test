package inputOps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.openqa.selenium.WebDriver;


//import com.sun.org.apache.bcel.internal.generic.NEW;

import tasks.Clusters;
import tasks.Servers;
import tasks.VirtualMachines;
import testcase.VerifyClustersPage;
import testcase.driverScript;

import exceptions.CommonException;
import exceptions.DefaultExceptions;
import exceptions.ElementNotFoundException;
import exceptions.KeywordNotFoundException;
import appObject.ElementsTasks;

public class KeywordsExecutor extends ElementsTasks {
	private static final long serialVersionUID = 1L;
	private Logger logger;
	private Logger logger1;
	
	public KeywordsExecutor(Logger logger) {
		super(logger);
		this.logger=logger; 
		// TODO Auto-generated constructor stub
	}

	//ArrayList<String[]> aTC;
	private String Task;
	private String sBy;
	private String sObject;
	private String Action;
	private String Data;

	public void ExecuteTestCase(WebDriver driver, ArrayList<String[]> TCSteps) throws ElementNotFoundException
	{
		//TestCaseOps oTCOps = new TestCaseOps(logger);
		//ArrayList<String[]> aTC = oTCOps.ReadTestCase(sPath);
		Iterator<String[]> testStep = TCSteps.iterator();
		while(testStep.hasNext())
		{
			Task="";sBy="";sObject="";Action="";Data="";
			String[] sKeyword = testStep.next();
			for(int i=0; i<sKeyword.length;i++)
			{
				if(sKeyword[i]!=null){
					switch (i) {
					case 0:
						Task = sKeyword[i];
						break;
					case 1:
						sBy = sKeyword[i];
						break;
					case 2:
						sObject = sKeyword[i];
						break;
					case 3:
						Action = sKeyword[i];
						break;
					case 4:
						Data = sKeyword[i];
						break;
					default:
						break;
					}
		
					//System.out.print(sKeyword[i]+"|");
				}
			}
			//System.out.println();
			
			ExecuteKeywords(driver);
			if(!(TestCaseOps.failFlag)){
			TestCaseOps.Status.add("Pass");
			TestCaseOps.Screnshotpath.add("");
			TestCaseOps.Bgcolor.add("rgb(0, 204, 51);");
			}
		}
	}

	private void ExecuteKeywords(WebDriver driver) throws ElementNotFoundException
	{	
		Clusters cld = new Clusters(logger);
		Servers srv =new Servers(logger);
		VirtualMachines vm = new VirtualMachines(logger);
		try {
			if(Task.equals(""))
				throw new KeywordNotFoundException();
			if (Action.equalsIgnoreCase("sendKeys"))
				sendKeys(driver, sBy, sObject, Data);
			if(Task.equalsIgnoreCase("getElement")&&Action.equalsIgnoreCase("click"))
				clickElement(driver,sBy, sObject);
			if(Action.equalsIgnoreCase("clickIfExists")&& Task.equalsIgnoreCase("getElement"))
				if(isElementPresent(driver, sBy, sObject))
					clickElement(driver, sBy, sObject);
			if(Task.equalsIgnoreCase("waitforelement")&& Action.equalsIgnoreCase("click"))
				waitForElement(driver, sBy, sObject).click();
			if(Task.equalsIgnoreCase("waitforelement"))
				waitForElement(driver, sBy, sObject);
			if(Task.equalsIgnoreCase("verifyText"))
				verifyText(driver, sBy, sObject);
			if(Task.equalsIgnoreCase("isElementPresent"))
				if(isElementPresent(driver, sBy, sObject))//insert else part
					logger.log(Level.INFO, "Element present :" + sObject);
				else
					logger.log(Level.ERROR, "Element not Present :" + sObject);
			if(Task.equalsIgnoreCase("getElement") && Action.equalsIgnoreCase("ClickandWait"))
				clickElementNWait(driver, sBy, sObject);
			if(Task.equalsIgnoreCase("selectFrame"))
				selectFrame(driver, sBy, sObject);
			if (Task.equalsIgnoreCase("VerifyClusterData"))
				cld.verifyClustersData(driver,sBy,sObject);
			if(Task.equalsIgnoreCase("VerifyServerData"))
				srv.verifyServersData(driver,sBy,sObject,this.logger);
			if(Task.equalsIgnoreCase("OpenClusterDetailsPage"))
				cld.openClusterDetailsPage(driver,sBy,sObject);
			if(Task.equalsIgnoreCase("OpenServerDetailsPage"))
				srv.openServerDetailsPage(driver, sBy, sObject,this.logger);
			if(Task.equalsIgnoreCase("VerifyServerSituationCount"))
				srv.serverSituation(driver, sBy, sObject, this.logger);
			if(Task.equalsIgnoreCase("VerifyClusterSituationCount"))
				cld.clusterSituation(driver, sBy, sObject, this.logger);
			if(Task.equalsIgnoreCase("matchVMCount"))
			{
				vm.matchVMCount(driver,sBy,sObject);
			}
		} catch (KeywordNotFoundException e) {
			logger.error(e);
		} catch (InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
