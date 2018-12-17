package tasks;

import inputOps.InputParamOps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Window;


import appObject.ElementsTasks;

import com.google.common.collect.Maps;
import com.google.common.collect.MapDifference.ValueDifference;
import dashboardUtil.ServerData;
import exceptions.ElementNotFoundException;

public class Servers extends ElementsTasks{
	private String classname = Servers.class.getSimpleName();
	private Logger logger;

	private ElementsTasks eTasks = new ElementsTasks(logger);
	private InputParamOps sIPops = new InputParamOps(logger);
//	private HashMap<String, ServerData> hmSUIData=new HashMap<String, ServerData>();
	private HashMap<String, String> hmSUIData=new HashMap<String, String>();
	HashMap<String, ServerData> hmSRestData = new HashMap<String, ServerData>();
	

	
	public Servers(Logger logger) {
		super(logger);
		this.logger=logger;
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * Open Servers page
	 * @param driver
	 * @throws Exception
	 * @author milind_gholap
	 */
	
	
	
	public ElementsTasks geteTasks() {
		return eTasks;
	}
	public void seteTasks(ElementsTasks eTasks) {
		this.eTasks = eTasks;
	}
	public InputParamOps getsIPops() {
		return sIPops;
	}
	public void setsIPops(InputParamOps sIPops) {
		this.sIPops = sIPops;
	}

	public void openServerPage(WebDriver driver,Logger logger) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		logger.info(geteTasks());
		geteTasks().getElement(driver, "system_statushealth_btn_id").click();
		geteTasks().getElement(driver, "open_server_dashboard_btn_id").click();
	}
	
	public String openServerPage(WebDriver driver,String sBy, String sObject,Logger logger)
	{
		
		String title = null;
		try {
			title = geteTasks().getElement(driver, sBy, sObject).getAttribute("title");
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Clicking on the Server "+title+" to move to its detail page");
		
		driver.findElement(By.xpath("//*[text()='"+title+"']")).click();
		
		int iPageLoadTimeout = Integer.parseInt(getsIPops().getGlobalProps("page_load_timeout"));
		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);
		return title;
	}
	
	public void openServerPageToVM(WebDriver driver,Logger logger) throws Exception
	{
		//eTasks = new ElementsTasks(logger);
		logger.info(classname);
		geteTasks().getElement(driver, "system_statushealth_btn_id").click();
		geteTasks().getElement(driver, "open_server_dashboard_btn_id").click();
		
		int iPageLoadTimeout = Integer.parseInt(getsIPops().getGlobalProps("page_load_timeout"));
		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);

	}

	/**
	 * Verifies servers row count displayed on Server Group Page with the no of row count returned by REST
	 * @param driver
	 * @throws Exception
	 * @author Tanmay Nakhate
	 */
	public void verifyServersData(WebDriver driver,String sBy, String sObject,Logger logger) throws ElementNotFoundException
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		logger.info(": Verifying Server page (Servers data in table).............");
		GetUIData oGetUIData = new GetUIData(logger);
		
		List<WebElement> rows = geteTasks().getElement(driver, sBy, "server_table_divid").findElements(By.tagName("tr"));
		
		// Zoom out the Page. Hardcoaded value
		WebElement html = driver.findElement(By.tagName("html"));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));


		
		String sID = sIPops.getLocator("ServerCount_Number");
		
		System.out.println("In verify Server data method : ServerSituation value  ="+sID);
		
		String tValue = driver.findElement(By.id(sID)).getText();
		System.out.println("value of total Servers "+tValue);
		
		String num = tValue.substring(7);
		Integer CountNum = Integer.parseInt(num);
//		System.out.println("New value of total Servers = "+CountNum);
		
		int restCount = returnRestCount();
		logger.info(classname+ " : Number of ServerRows in the table ="+CountNum);


		if (CountNum == restCount) {
			logger.info(classname+ " : Server Row count matches with the row count received from Rest Data provider");
		}
		else{
			logger.error(classname+ ": Server Row count does not match with the row count received from Rest Data provider");
		}
		
//		List<String> columns = getRowData(driver);
		List<String> columns = getRowData(driver, restCount);
		HashMap<String,String> tempMap = oGetUIData.getServersData(columns);
		hmSUIData.putAll(tempMap);
		logger.info(hmSUIData);
		compareData();
		html.sendKeys(Keys.chord(Keys.CONTROL, "0"));
		selectFrame(driver, "");
	}
	
	


/*	
 * actual method  Tanmay Nakhate 
  List<String> getRowData(WebDriver driver) {
		List<String> newColumns = new ArrayList<String>();
		
		String sID = sIPops.getLocator("Server_row_Path");
		List <WebElement> columns = driver.findElements(By.xpath(sID));
		
		for(WebElement s:columns)
		{
			
			 //Following code is used to check the hashcode of the blank rows and remove them.
			
			if(s.getText().hashCode()!=0) {
				newColumns.add(s.getText().toString());
			}
		}
		for(String s:newColumns)
		{
			System.out.println("newColumns = "+s.toString());
		}
		return newColumns;

	}
	*/
/*	Approach no. 2 = Tanmay Nakhate  + Rohit Swaroop + PSL
 * 
 * 
 * List<String> getRowData(WebDriver driver,int count) {
 
		List<String> newColumns = new ArrayList<String>();
		List <WebElement> columns = new ArrayList<WebElement>();
//		String sID = sIPops.getLocator("Server_row_Path");
//		driver.findElement(By.xpath("//*[text()='All']")).click();
		for (int i = 0; i < 10; i++) {
//			String sID = ".//*[@id='gridx_Grid_0']/div[3]/div[2]/div["
			
			int a = i*2;
			driver.findElement(By.xpath(".//*[@id='gridx_Grid_0']/div[3]/div[2]/div["+(i+1)+"]/table/tbody/tr/td[1]")).sendKeys(Keys.ENTER);
			WebElement row = driver.findElement(By.xpath(".//*[@id='dijit__WidgetsInTemplateMixin_"+a+"']/div/input"));
			row.click();
			columns = driver.findElements(By.xpath(".//*[@id='gridx_Grid_0']/div[3]/div[2]/div["+(i+1)+"]/table/tbody/tr"));
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			System.out.println("NEW PRINT: " + row.getText().toString());
				
			//columns = driver.findElements(By.xpath(".//*[@id='gridx_Grid_0']/div[3]/div[2]/div["+i+"]/table/tbody/tr/td[11]"));
			for (int j = 2; j < 17; j++) {
				columns = driver.findElements(By.xpath(".//*[@id='gridx_Grid_0']/div[3]/div[2]/div["+i+"]/table/tbody/tr/td["+j+"]"));	
			}
			for(WebElement s:columns)
			{
				
				System.out.println("Columns = "+s.getText().toString());
				System.out.println();
			}
			
		}
		
		for(WebElement s:columns)
		{
			System.out.println("Columns = "+s.getText().toString());
			System.out.println();
		}

		for(WebElement s:columns)
		{
			*//**
			 *	Following code is used to check the hashcode of the blank rows and remove them.
			 *//*
			if(s.getText().hashCode()!=0) {
				newColumns.add(s.getText().toString());
			}
		}
		for(String s:newColumns)
		{
			System.out.println("newColumns = "+s.toString());
		}
		return newColumns;

	}*/
	
	// Approach no. 2 = Tanmay Nakhate  + Niranjan Mandlik
	List<String> getRowData(WebDriver driver, Integer countNum) {
		
		GetRestData oRestData = new GetRestData(logger);
		List<String> newColumns = new ArrayList<String>();
		List<String> keys = new ArrayList<String>();
		HashMap<String,ServerData> map = new HashMap<String, ServerData>();
		
		try {
				map = oRestData.getServerRestData();
				for ( String str : map.keySet()) {
					keys.add(str.toString());
				}
				
				for ( String str : keys) {
					System.out.println("keys = "+str);;
				}
				
				for (int i = 0; i < keys.size(); i++) {
					driver.findElement(By.id("dijit_form_TextBox_0")).clear();
					driver.findElement(By.id("dijit_form_TextBox_0")).sendKeys(keys.get(i));
//					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					Thread.sleep(2000);

					String sID = sIPops.getLocator("Server_row_Path");
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					String str = driver.findElement(By.xpath(sID)).getText().toString();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					System.out.println("ToString = "+str);
					newColumns.add(str);
				}
				driver.findElement(By.id("dijit_form_TextBox_0")).clear();
				for(String s:newColumns)
				{
					System.out.println("newColumns = "+s.toString());
				}
				
		} catch (Exception e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newColumns;
	}

		
	private int returnRestCount()
	{
		GetRestData oRestData = new GetRestData(logger);
		int countRestRow =0;

		try {
			countRestRow = oRestData.getServersRowCount();
		} catch (Exception e) {
			String message = "Class '" + Servers.class.getSimpleName()+ "'\n"+ e.getMessage();
			logger.error(classname + ": " + message + e.getStackTrace());
			System.out.println("");
		}
		return countRestRow;
	}
	
	
	
	private void compareData()
	{
		GetRestData oRestData = new GetRestData(logger);
		List<String> matchColumns = new ArrayList<String>();
		List<String> restData = new ArrayList<String>();
		
		String sMatch="";
		String sNoMatch="";
		try {
			hmSRestData = oRestData.getServerRestData();
			
			Set<String> hmRKeys = hmSRestData.keySet();
			Set<String> hmCUKeys = hmSUIData.keySet();
			for(String hmRKey: hmRKeys){
				for(String cuKey: hmCUKeys){
					if( cuKey.equalsIgnoreCase(hmRKey)){
//						System.out.println("UI Columns matching with Rest = "+hmCUIData.get(cuKey));
						matchColumns.add(hmSUIData.get(cuKey).toString());
						restData.add(hmSRestData.get(cuKey).toString());
						continue;
					}
				}
			}
	
			for(String key: hmCUKeys){
				hmSRestData.remove(key);
			}
			
			
			logger.info(classname+" : Number of Server Rows captured in the UI : "+matchColumns.size());
			logger.info("======================================================================================================================");
			for (int i = 0; i < matchColumns.size(); i++) {
				String sExpData = restData.get(i).toString();
				String sActData = matchColumns.get(i).toString();
				if (sExpData.equals(sActData))
					sMatch = sMatch.concat(sActData + "\n");
				else
					sNoMatch = sNoMatch.concat("\n"+"Actual  :" + sActData + "\n" + "Expected :" + sExpData +"\n");
			}

			if(!sMatch.isEmpty()){
				String message = "Data matched: \n";
				logger.info(classname + ": " + message + sMatch);
			}
			if(!sNoMatch.isEmpty()){
				String message = "Differences found in data displayed on UI:";
				logger.error(classname + ": " + message + sNoMatch);
			}
			logger.info("======================================================================================================================");
			
//			for (int i = 0; i < matchColumns.size(); i++) {
//				for (int j = i; j < restData.size()+1; j++) {
//					System.out.println( " UI 	Columns   data = "+matchColumns.get(i));
//					System.out.println( " Rest  Columns	  data = "+restData.get(i));
//					System.out.println("=========================================================================================");
//					break;
//				}
//				continue;
//			}

			for (String name :hmSRestData.keySet()) {
	            String value = hmSRestData.get(name).toString();  
	            logger.info(classname+" : Rest Columns not displayed on UI = "+ value);
	            logger.info("-----------------------------------------------------------------------------------------------------------------------");			}

		} catch (Exception e) {
			String message = "Class '" + Clusters.class.getSimpleName()+ "'\n"+ e.getMessage();
			logger.error( classname + ": " + message + e.getStackTrace());
		}
	}
	
	/**
	 * Opens the Server Detail page and then verifies the server text.
	 * @param driver
	 * @throws Exception
	 * @author Tanmay Nakhate
	 */
	public void openServerDetailsPage(WebDriver driver, String sBy, String sObject,Logger logger) throws ElementNotFoundException
	{

		System.out.println(classname+": Opening Server page(Servers data in table)....");
		logger.info(classname+": Opening Server page(Servers data in table)....");
		
//		GetUIData oUIData = new GetUIData(logger);

			
			//String title = driver.findElement(By.cssSelector(sObject)).getAttribute("title");
			String title = getElement(driver, sBy, sObject).getAttribute("title");
			
			logger.info("Clicking on the Server "+title+" to move to its detail page");
			getElement(driver, sBy, sObject).click();
			//driver.findElement(By.xpath("//*[text()='"+title+"']")).click();
			
			int iPageLoadTimeout = Integer.parseInt(getsIPops().getGlobalProps("page_load_timeout"));
			driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);
			//serverSituation(driver, title);
			try {
				verifyServersPageTexts(driver, logger , title);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			selectFrame(driver, "");
	}
	
	
	/**
	 * Verifies static text on the Servers page
	 */
	
	public void verifyServersPageTexts(WebDriver driver,Logger logger,String name) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		logger.info(": Verifying Server Details page........");
		logger.info(": Verifying Server name Texts .......");
		
		String namePath=".//*[@id='dijit__WidgetBase_10_ServerInstTabWidget']/div/span[1]";
		String rrPath = ".//*[@id='dijit__WidgetBase_12_ServerInstResourceRelationshipView']/div/table/tr[2]/td[2]/div/div/div/div[2]";

		String pageTitle = driver.findElement(By.xpath(namePath)).getText();
		String rrTitle = driver.findElement(By.xpath(rrPath)).getText();
		
		if ((rrTitle.equals(pageTitle)) && (pageTitle.equals(name))) {
			logger.info(classname+" : Server Title and Server Name from Resource Relationship Matches.......");
			logger.info(classname+" : We have moved to detail page of "+name+" Server.......");
		}
		else {
			logger.error(classname+" : MissMatch in Server Title and Server Name in Resource Relationship.......");
		}

	}

	
	/**
	 * Verifies servers situation displayed on Server Group Page with the no of row count returned by REST
	 * @param driver
	 * @throws Exception
	 * @author Tanmay_Nakhate
	 * @throws ElementNotFoundException 
	 */
	
	public void serverSituation(WebDriver driver,String sBy, String sObject,Logger logger) throws ElementNotFoundException
	{
		//getting situation count from situation table 
		int value;
		
		String title = getElement(driver, sBy, sObject).getAttribute("title");
		logger.info("Clicking on the Server "+title+" to move to its detail page");
		getElement(driver, sBy, sObject).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.switchTo().frame(driver.findElement(By.name("isc_iframe")));
		
		value =GetRestData(title);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		try {
			String sID = getsIPops().getLocator("ServerSituation_Number");
			String tValue = driver.findElement(By.id(sID)).getText();
//			System.out.println(""+tValue);
		
			String num = tValue.substring(7);
			Integer newNum = Integer.parseInt(num);
//			System.out.println("New Num = "+newNum);

			//another method (hardcoding) to get situation count
		
			/*char num = tValue.charAt(7);
	    	String character = "+" + num;
	    	int newNum = getsIPops().convertToNum(character);
	    	
			 */
//			value = 1;
			if (value == newNum)
			{
				logger.info(classname+" : Sum of Situations on Server "+title+ " matches with the total value from situation table");
			}
			else
			{
				logger.error(classname+" : Sum of Situations on Server "+title+ " does not match with the total value from situation table");
			}
			selectFrame(driver, "");
			} catch (ElementNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	 }	
	
	
		//switching to diff frame so as to get into situation table.
		/*String name = driver.findElement(By.name("isc_iframe")).getAttribute("title");
		System.out.println(name);
		if(driver.findElement(By.xpath("//*[@name='isc_iframe']")).isDisplayed())
		{
			System.out.println("Frame is displayed");
		}
		
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@title='Content frame']")));
		if(driver.findElement(By.xpath("//*[@src='/ibm/console/contentRender.do?oid=_1726736249&pageid=com.ibm.tivoli.itm.VMD.dashboard.server.navigationElement;ITMVMD&XSS=0aDaI7ppTZnLBcD19RZA3Yf']")).isEnabled())
		{
			System.out.println("frame is enabled");
		}
		
		List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
		System.out.println(iframes.toString());
		System.out.println("Number of frames in a page :" + iframes.size());
		for(WebElement el : iframes){
		      //Returns the Id of a frame.
		        System.out.println("Frame Id :" + el.getAttribute("id"));
		      //Returns the Name of a frame.
		        System.out.println("Frame name :" + el.getAttribute("name"));
		      // returns loadurl
		        System.out.println("Frame src :"+el.getAttribute("loadurl"));
		     // returns title
		        System.out.println("Frame src :"+el.getAttribute("title"));
		}
		driver.switchTo().frame(1);
		List<WebElement> rows = getElement(driver, "id", "ServerSituation_Table").findElements(By.tagName("tr"));
		System.out.println(rows);*/
 		
				
	private int GetRestData(String serverName) {
		// TODO Auto-generated method stub
		GetRestData oRestData = new GetRestData(logger);
		int count=0;
		try {
			hmSRestData = oRestData.getServerRestData();
			for (String key : hmSRestData.keySet())
			{
//				String sExpData = hmSRestData.get(key).toString();
				ServerData sd=hmSRestData.get(key);
				if (serverName.equals(key))
				{
					count = sd.getSum();
					logger.info("Sum of Situations on Server "+key+ " is " +sd.getSum());
					break;
				}
			}
		}catch (Exception e) {
			String message = "Class '" + Servers.class.getSimpleName()+ "'\n"+ e.getMessage();
			logger.error( classname + ": " + message + e.getStackTrace());
		}
		return count;
	}
	
	
/*	private int compareServerData()
	{
		GetRestData oRestData = new GetRestData(logger);
		int countRestRow =0;
		try {
			countRestRow = oRestData.getServersData();
			Map<String, ValueDifference<ServerData>> mapDiff = Maps.difference(hmSUIData, hmSRestData).entriesDiffering();
			for (String key : mapDiff.keySet()) {
				System.out.println("difference in row " + mapDiff.get(key));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return countRestRow;
	}*/
	
	
	
	public void printServerData()
	{
		String sDataToPrint = "";
		for (String key : hmSUIData.keySet()) {
			sDataToPrint = sDataToPrint.concat(hmSUIData.get(key).toString() + "\n");
		}
		logger.info("Servers UI Data-----------\n" + sDataToPrint);
	}
	
	public void closeServersTab(WebDriver driver) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		geteTasks().getElement(driver, "server_tabclose_xpath").click();
		Thread.sleep(5000);
	}
}


