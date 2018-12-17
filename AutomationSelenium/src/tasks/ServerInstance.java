package tasks;

import inputOps.InputParamOps;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import exceptions.ElementNotFoundException;

import appObject.ElementsTasks;

public class ServerInstance {
	private String className = ServerInstance.class.getSimpleName();
	private Logger logger=null;
	private ElementsTasks eTasks = new ElementsTasks(logger);
	private InputParamOps iPops = new InputParamOps(logger);
	private int vmCountMax = 0;
	private String linkText="";
	
	
	public ElementsTasks geteTasks() {
		return eTasks;
	}

	public void seteTasks(ElementsTasks eTasks) {
		this.eTasks = eTasks;
	}

	public InputParamOps getiPops() {
		return iPops;
	}

	public void setiPops(InputParamOps iPops) {
		this.iPops = iPops;
	}

	
	public ServerInstance(Logger logger) {
		this.logger=logger;
	}

	
	/**
	 * Open Server Instance page by selecting Server name having maximum VMs
	 * @param driver
	 * @throws Exception
	 */
	public void openServerInstancePage(WebDriver driver) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		this.logger.info(className);
		geteTasks().selectFrame(driver, "name","Frame1_name");
		
		geteTasks().waitForElement(driver, "server_table_divid");
		
		List<WebElement> rows = geteTasks().getElement(driver, "server_table_divid").findElements(By.tagName("tr"));
		int iRows = (rows.size()-2); //remove header and footer from the rows count

		if (iRows<=0){
			logger.error(className + ": No data displaying on Servers page..cannot continue.");
			/*Exception e = new Exception(className + ": No data displaying on Servers page");
			throw e;*/
		}else getServerNameWithMaxVMs(rows).click();
	}
	
	
	public int openServerInstancePageToVM(WebDriver driver,String sBy, String sObject,Logger logger) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		logger.info(className);
		int vmCount = 0;
		geteTasks().selectFrame(driver, "name","Frame1_name");
		
		geteTasks().waitForElement(driver, "server_table_divid");
		
		List<WebElement> rows = geteTasks().getElement(driver, "server_table_divid").findElements(By.tagName("tr"));
		int iRows = (rows.size()-2); //remove header and footer from the rows count

		if (iRows<=0){
			logger.error(className + ": No data displaying on Servers page..cannot continue.");
			/*Exception e = new Exception(className + ": No data displaying on Servers page");
			throw e;*/
		}else 
			{
				//getServerNameWithMaxVMs(rows).click();
				int Count= GetServerNameByMaxVM(driver,sBy,sObject,logger);
				vmCount = Count;
			}
		
		return vmCount;
	}

	public int GetServerNameByMaxVM(WebDriver driver ,String sBy, String sObject, Logger logger) {
		//ElementsTasks eTasks = new ElementsTasks(logger);
		//InputParamOps iPops = new InputParamOps(logger);	
		int vmCountMax = 0;
		driver.findElement(By.id("gridx_Grid_0-no_of_vms")).click();
		driver.findElement(By.id("gridx_Grid_0-no_of_vms")).click();
		
		try {
			String title = geteTasks().getElement(driver, sBy, sObject).getAttribute("title");
			String VMvalue =  geteTasks().getElement(driver, "xpath", "xPathvmNo").getText();
			logger.info("Name of Server is = "+title);
			logger.info("No of Virtual machine this server contains is "+VMvalue);
			
			vmCountMax = getiPops().convertToNum(VMvalue); 
			
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vmCountMax;
	}
	

	public void verifyTexts(WebDriver driver) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		geteTasks().selectFrame(driver, "Frame1_name");
		geteTasks().waitForElement(driver, "server_charts_div_id");
		//eTasks.verifyText(driver, "servername_breadcrumb_xpath");
		//eTasks.verifyText(driver, "servername_css");
		System.out.println(linkText);
		System.out.println(vmCountMax);

	}

	public void verifyServerInstanceData(WebDriver driver) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		//eTasks.selectFrame(driver, "Frame1_name");
		
		//verify server name in breadcrumb
		String sActText = geteTasks().waitForElement(driver, "server_breadcrumb_xpath").getText();
		if(sActText.equalsIgnoreCase(linkText))
			logger.info(className + ": Server name (" + sActText + ") found correct in breadcrumb");
		else{
			String message = "Server name found incorrect in server instance page breadcrumb\n" +
			"Actual  : " + sActText+
			"\nExpected: " + linkText;
			logger.error(className + message);
		}
		
		//verify server name in page title
		sActText = geteTasks().getElement(driver, "server_css").getText();
		if(sActText.equalsIgnoreCase(linkText))
			logger.info(className + ": Server name (" + sActText + ") found correct in page title");
		else{
			String message = "Server name found incorrect in server instance page page title\n" +
			"Actual  : " + sActText+
			"\nExpected: " + linkText;
			logger.error(className + message);
		}
		
		//verify server name in resource relationship
		sActText = geteTasks().getElement(driver, "server_resource_relationship_xpath").getText();
		if(sActText.equalsIgnoreCase(linkText))
			logger.info(className + ": Server name (" + sActText + ") found correct in resourse relationship");
		else{
			String message = "Server name found incorrect in server instance page resourse relationship\n" +
			"Actual  : " + sActText+
			"\nExpected: " + linkText;
			logger.error(className + message);
		}
		String sActVM = geteTasks().getElement(driver, "virtual_machines_css").getText();
		if(sActVM.equals(String.valueOf(vmCountMax)))
			logger.info(className + ": Virtual machine count (" + sActVM + ") found correct in resourse relationship");
		else{
			String message = "Virtual machine count found incorrect in server instance page resourse relationship\n" +
			"Actual  : " + sActText+
			"\nExpected: " + vmCountMax;
			logger.error(className + message);
		}
	}

	/**
	 * Get server name element from the table which have higher number of VMs 
	 * @param rows
	 * @return
	 * @throws Exception 
	 * @author milind_gholap
	 */
	public WebElement getServerNameWithMaxVMs(List<WebElement> rows) throws Exception
	{
		Iterator<WebElement> r = rows.iterator();
		int counter = 0;
		WebElement serverName = null;
		boolean b= true;
		List<WebElement> headers=null;
		//String[] serverLink = null;
		while(r.hasNext()) //iterate through rows
		{
			if(counter++==(rows.size()-1)) break;//eliminate last footer row 
			WebElement row = r.next();

			if (b){ headers = row.findElements(By.tagName("th")); b=false;}
			else{
				List<WebElement> columns = row.findElements(By.tagName("td"));
				Iterator<WebElement> c = columns.iterator();
				Iterator<WebElement> h = headers.iterator();
				WebElement column =null;
				WebElement header = null;
				WebElement tempName = null;
				WebElement nodeID = null;
				while (c.hasNext()&& h.hasNext())//Iterate through headers and columns
				{
					column = c.next();
					header = h.next();
					if(header.getText().equalsIgnoreCase("Server Name"))
						tempName = column; //store server name element temporary 
					if(header.getText().equalsIgnoreCase("Virtual Machines"))
					{
						int vmCount = Integer.parseInt(column.getText());
						if(vmCountMax<vmCount){
							vmCountMax=vmCount; //Assign maximum VM count
							serverName = tempName;// Assign temporary server name element to server name
						}
					}
					if(header.getText().equalsIgnoreCase("Node ID"))
					{
						nodeID = column;
						System.out.println(nodeID.getText());
					}
				}
			}
		}
		linkText = serverName.getText();
		logger.info(className + ": Server name '" + linkText + "' has max number of VMs " + vmCountMax + " on page 1.selecting.....");
		//return new String[]{linkText, String.valueOf(vmCountMax)};
		
		return serverName.findElement(By.linkText(linkText));
	
	}
	

}
