package tasks;

import inputOps.InputParamOps;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import appObject.ElementsTasks;

public class VirtualMachines {

	private Logger logger =null;
	private InputParamOps sIPops = new InputParamOps(logger);
	private ElementsTasks eTasks =  new ElementsTasks(logger);
	private Servers oServers = new Servers(logger);
	private ServerInstance oSI = new ServerInstance(logger);
	
	
	
	//getters and setters for classes so that these objects can be used in many methods below.
	
	public InputParamOps getsIPops() {
		return sIPops;
	}

	public void setsIPops(InputParamOps sIPops) {
		this.sIPops = sIPops;
	}

	public ElementsTasks geteTasks() {
		return eTasks;
	}

	public void seteTasks(ElementsTasks eTasks) {
		this.eTasks = eTasks;
	}

	public ServerInstance getoSI() {
		return oSI;
	}

	public void setoSI(ServerInstance oSI) {
		this.oSI = oSI;
	}

	public Servers getoServers() {
		return oServers;
	}

	public void setoServers(Servers oServers) {
		this.oServers = oServers;
	}

	public VirtualMachines(Logger logger) {
		this.logger = logger;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public void matchVMCount(WebDriver driver,String sBy, String sObject) throws Exception
	{
		getoServers().openServerPageToVM(driver,this.logger);
		int vmCount = getoSI().openServerInstancePageToVM(driver, sBy, sObject,this.logger);

		driver.findElement(By.xpath("//*[text()='All']")).click();
		
		String title = getoServers().openServerPage(driver, sBy, sObject,this.logger);
		
		
	/*	passing css and Css path to getElement method which will get the title.
	 * 
	 * String title = eTasks.getElement(driver, sBy, sObject).getAttribute("title");
	
		
		logger.info("Clicking on the Server "+title+" to move to its detail page");
		
		driver.findElement(By.xpath("//*[text()='"+title+"']")).click();
		
		int iPageLoadTimeout = Integer.parseInt(sIPops.getGlobalProps("page_load_timeout"));
		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);
		
	*/
		String vmNo = geteTasks().getElement(driver, "xpath", "RRvmNo").getText();
		int vmNum = getsIPops().convertToNum(vmNo);
		
		if(vmNum == vmCount)
		{
			getLogger().info("Server "+title+ " has "+vmCount+ " Virtual Machines which Matches with the value displayed in Resource Relationship");
		}
		else
		{
			getLogger().error("Virtual Machine value does not match");
		}
		
	}
	

}
