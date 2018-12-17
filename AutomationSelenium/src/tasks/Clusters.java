package tasks;

import inputOps.InputParamOps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.log4j.Logger;


import appObject.ElementsTasks;
import dashboardUtil.ClusterData;
import dashboardUtil.ServerData;
import exceptions.ElementNotFoundException;



public class Clusters extends ElementsTasks{
	private String classname = Clusters.class.getSimpleName();  
	private Logger logger;
	private InputParamOps oIPops = new InputParamOps(logger);
//	private HashMap<String, ClusterData> hmCUIData = new HashMap<String, ClusterData>();
	HashMap<String, ClusterData> hmCRestData = new HashMap<String, ClusterData>();
	private HashMap<String, String> hmCUIData = new HashMap<String, String>();

	
	public Clusters(Logger logger) {
		super(logger);
		this.logger=logger;
	}
	
	/**Opens Clusters page
	 * @param driver
	 * @throws Exception
	 * @author milind_gholap
	 */
	public void openClustersPage(WebDriver driver) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		logger.info(classname);
		//eTasks.waitForElement(driver, "system_statushealth_btn_id").click();
		//eTasks.getElement(driver, "open_cluster_dashboard_btn_id").click();
		waitForElement(driver, "system_statushealth_btn_id").click();
		clickElement(driver, "open_cluster_dashboard_btn_id");
	}
	/**
	 * @param sB 
	 * @param sKey 
	 * @param sB 
	 * @param sKey 
	 * Verifies clusters data displayed on the UI with the data returned by REST. <br>
	 * Prints UI data 
	 * @param driver
	 * @throws Exception
	 * @author Tanmay_Nakhate
	 * @throws  
	 */
	public void verifyClustersData(WebDriver driver, String sBy, String sObject) throws ElementNotFoundException
	{
		logger.info(classname+": Verifying Cluster page(Clusters data in table)....");
		GetUIData oUIData = new GetUIData(logger);
		
		List<WebElement> rows = getElement(driver, sBy, "table_div_id").findElements(By.tagName("tr"));
		
		// Zoom out the Page. Hardcoaded value
		WebElement html = driver.findElement(By.tagName("html"));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		html.sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
		
		
		String sID = oIPops.getLocator("ClusterCount_Number");
		String tValue = driver.findElement(By.id(sID)).getText();
		
		String num = tValue.substring(7);
		Integer CountNum = Integer.parseInt(num);
//		System.out.println("New value of total clusters = "+CountNum);
		
		int restCount = returnRestCount();
		logger.info(classname+ " : Number of Cluster Rows in the table ="+CountNum);

		if (CountNum == restCount) {
			logger.info(classname+ " : Cluster Row count matches with the row count received from Rest Data provider");
		}
		else{
			logger.error(classname+ " : Cluster Row count matches with the row count received from Rest Data provider");
		}
		
		List<String> columns = getRowData(driver,CountNum);
		HashMap<String,String> tempData =oUIData.getClusterData(columns);
		hmCUIData.putAll(tempData);
		logger.info(hmCUIData);
		compareData();
		html.sendKeys(Keys.chord(Keys.CONTROL,"0"));
		selectFrame(driver, "");
	}

	private int returnRestCount() {
		GetRestData oRestData = new GetRestData(logger);
		int countRestRow = 0;

		try {
			
			countRestRow = oRestData.getClustersRowCount();

			} catch (Exception e) {

			e.printStackTrace();
		}
		return countRestRow;
	}

	
	List<String> getRowData(WebDriver driver, Integer countNum) {
		
		GetRestData oRestData = new GetRestData(logger);
		List<String> newColumns = new ArrayList<String>();
		List<String> keys = new ArrayList<String>();
		HashMap<String,ClusterData> map = new HashMap<String, ClusterData>();
		
		try {
				map = oRestData.getClusterRestData();
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

					String sID = oIPops.getLocator("Cluster_row_Path");
					String str = driver.findElement(By.xpath(sID)).getText().toString();
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
	
	
/*	List<String> getRowData(WebDriver driver,int count) {
		int rowCount = count;
		int div = 0;
		List<String> newColumns = new ArrayList<String>();
		List <WebElement> columns = null;
//		List<WebElement> columns = driver.findElements(By.xpath(".//table/tbody/tr"));
//		List<WebElement> columns = driver.findElements(By.xpath(".//*[@class='gridxRowTable']//tr"));
//		List<WebElement> columns = driver.findElements(By.xpath(".//*[table[@class='gridxRowTable']/tbody/tr]"));
		
		if(rowCount > 10)
		{
			div = rowCount/10;
			int mod = rowCount%10;
			if (mod >= 1) {
				div = div +1; 
			}
		}
		
		//hardcoded value : 
		
		for (int i = 1; i <= div ; i++) {
//			driver.findElement(By.xpath("//*[text()='"+(i+1)+"']")).click();
			String sID = oIPops.getLocator("Cluster_row_Path");
			if (columns == null) {
				columns = new ArrayList<WebElement>();
				columns = driver.findElements(By.xpath(sID));
			}
			else
			{
				columns.add(driver.findElement(By.xpath(sID)));
			}
			try{
//				driver.findElement(By.xpath("//span[@aria-label='Page "+i+"']")).click();
//				driver.findElement(By.xpath(".//span[@title='Page "+i+"']")).click();
//				driver.findElement(By.xpath(".//span[@pageindex='"+i+"']")).click();
//				driver.findElement(By.xpath(".//*[@id='dijit__FocusMixin_0']/span[2]/span[2]")).click();
//				driver.findElement(By.linkText(""+i+"")).click();
//				driver.findElement(By.linkText("2")).click();
				driver.findElement(By.partialLinkText("2")).click();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("Element not present");
				break;
			}
			
			
						
		}
//		List <WebElement> columns1 = driver.findElements(By.xpath(sID));
		
		
		for(WebElement s:columns)
		{

//		 		Following code is used to check the hashcode of the blank rows and remove them.

			if(s.getText().hashCode()!=0) {
				newColumns.add(s.getText().toString());
			}
		}
		
		
//		  Code for scrolling down (not working)
		 
		
//		WebElement elem = driver.findElement(By.className("gridxVScroller"));
//		builder.click(elem).keyDown(Keys.ARROW_DOWN).keyDown(Keys.ARROW_DOWN);
//		builder.click(elem).keyDown(Keys.PAGE_DOWN).keyDown(Keys.PAGE_DOWN);
////		builder.click(elem).sendKeys(elem, "\\40").release();
////		builder.perform();
		
		
		
		return newColumns;

	}
	*/

	private void compareData()
	{
		GetRestData oRestData = new GetRestData(logger);
		List<String> matchColumns = new ArrayList<String>();
		List<String> restData = new ArrayList<String>();
		
		String sMatch="";
		String sNoMatch="";
		try {
			hmCRestData = oRestData.getClustersData();
			
			Set<String> hmRKeys = hmCRestData.keySet();
			Set<String> hmCUKeys = hmCUIData.keySet();
			for(String hmRKey: hmRKeys){
				for(String cuKey: hmCUKeys){
					if( cuKey.equalsIgnoreCase(hmRKey)){
//						System.out.println("UI Columns matching with Rest = "+hmCUIData.get(cuKey));
						matchColumns.add(hmCUIData.get(cuKey).toString());
						restData.add(hmCRestData.get(cuKey).toString());
						continue;
					}
				}
			}
	
			for(String key: hmCUKeys){
				hmCRestData.remove(key);
			}
			
			logger.info(classname+" : Number of Cluster Rows captured in the UI : "+matchColumns.size());
			logger.info("======================================================================================================================");
			for (int i = 0; i <matchColumns.size(); i++) {
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

			for (String name :hmCRestData.keySet()) {
	            String value = hmCRestData.get(name).toString();  
	            logger.info(classname+" : Rest Columns not displayed on UI = "+ value);
	            logger.info("-----------------------------------------------------------------------------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
			String message = "Class '" + Clusters.class.getSimpleName()+ "'\n"+ e.getMessage();
			logger.error( classname + ": " + message + e.getStackTrace());
		}
	}
	
	/**
	 * You can print UI data of Clusters only after verifyClustersData method.
	 */
	
	
	public void printClustersUIData()
	{
		String sDatatoPrint = "";
		for (String key : hmCUIData.keySet()) {
			sDatatoPrint = sDatatoPrint.concat(hmCUIData.get(key).toString() + "\n");
		}
		logger.info("Cluster data on UI--------\n" + sDatatoPrint);
	}

	

	/**
	 * You can print REST data of Clusters only after verifyClustersData method.
	 * 
	 */
	public void printClustersRESTData()
	{
		String sDataToPrint="";
		for (String key : hmCRestData.keySet()) {
			sDataToPrint = sDataToPrint.concat(hmCRestData.get(key).toString()+"\n");
		}
		logger.info(classname + ": Cluster Rest Data-------------\n" + sDataToPrint);
	}
	
	//	TODO need to wait after closing the tab
	public void closeClustersTab(WebDriver driver) throws Exception
	{
		ElementsTasks eTasks = new ElementsTasks(logger);
		eTasks.getElement(driver, "cluster_tabclose_xpath").click();

		Thread.sleep(5000);
	}


	public void openClusterDetailsPage(WebDriver driver, String sBy, String sObject) throws ElementNotFoundException
	{
		
		System.out.println(classname+": Opening Cluster page(Clusters data in table)....");
		logger.info(classname+": Opening Cluster page(Clusters data in table)....");
	
		//	GetUIData oUIData = new GetUIData(logger);
	
		driver.findElement(By.xpath("//*[text()='Switch to Carousel']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[text()='Switch to Scorecard']")).click();	
	
	

		int iPageLoadTimeout = Integer.parseInt(oIPops.getGlobalProps("page_load_timeout"));
		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);
	
		driver.findElement(By.xpath("//*[text()='All']")).click();
		
		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);
		
		//String title = driver.findElement(By.cssSelector(sObject)).getAttribute("title");
		String title = getElement(driver, sBy, sObject).getAttribute("title");
		
		logger.info("Clicking on the cluster "+title+" to move to its detail page");
		getElement(driver, sBy, sObject).click();

		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);

		//serverSituation(driver, title);
		try {
			verifyClustersPageTexts(driver, logger , title);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectFrame(driver, "");
  }

	/**
	 * Verifies Static data displayed on the Clusters page. 
	 * Expected data is taken from Panel_resources.properties
	 * @param driver
	 * @throws Exception
	 * @author Tanmay_Nakhate
	 */

	public void verifyClustersPageTexts(WebDriver driver,Logger logger,String name) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		logger.info(": Verifying Server Details page........");
		logger.info(": Verifying Server name Texts .......");
		
		String namePath=".//*[@id='dijit__WidgetBase_10_ClusterInstTabWidget']/div/span[1]";
		String rrPath = ".//*[@id='dijit__WidgetBase_12_ClusterInstResourceRelationshipView']/div/table/tr[2]/td[1]/div/div/div/div[2]";

		String pageTitle = driver.findElement(By.xpath(namePath)).getText();
		String rrTitle = driver.findElement(By.xpath(rrPath)).getText();
		
		if ((rrTitle.equals(pageTitle)) && (pageTitle.equals(name))) {
			logger.info(classname+" : Cluster Detail Page Title and Cluster Name from Resource Relationship Matches.......");
			logger.info(classname+" : We have moved to detail page of "+name+" Cluster.......");
		}
		else {
			logger.error(classname+" : MissMatch in Cluster Detail Page Title and Cluster Name in Resource Relationship.......");
		}

	}
	public void clusterSituation(WebDriver driver,String sBy, String sObject,Logger logger) throws ElementNotFoundException, InterruptedException
	{
		
		//getting situation count from situation table 
		//*********** MAKE SURE THAT NO TWO CLUSTER NAMES ARE SAME.********************************************************
			String title = getElement(driver, sBy, sObject).getAttribute("title");
			logger.info("Clicking on the cluster "+title+" to move to its detail page");
			getElement(driver, sBy, sObject).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
			int value =GetRestData(title);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				
			try {	
				String sID = oIPops.getLocator("ClusterSituation_Number");
				Thread.sleep(10000);
				String tValue = driver.findElement(By.id(sID)).getText();
				System.out.println(""+tValue);
			
				String num = tValue.substring(7);
				Integer newNum = Integer.parseInt(num);
				System.out.println("New Num = "+newNum);

				//another method to get situation count
				/*char num = tValue.charAt(7);
			    String character = "+" + num;
			    Integer newNum = new Integer(character);*/
				
				if (value == newNum)
				{
					logger.info(classname+" : Sum of Situations on Cluster "+title+ " matches with the total value from situation table");
				}
				else
				{
					logger.error(classname+" : Sum of Situations on Cluster "+title+ " does not match with the total value from situation table");
				}
				selectFrame(driver, "");
			} catch (ElementNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
				// Enter into situations table ...
				/*String Lvalue = driver.findElement(By.id("dijit__WidgetBase_30_ClusterInstEvents")).getAttribute("aria-label");
				logger.info("Sum of Situations on Cluster "+title+ " matches with the total value from situation table");
				System.out.println("aria lable value = = "+Lvalue);
				List<WebElement> rows = getElement(driver, "id", "dijit__WidgetBase_30_ClusterInstEvents").findElements(By.tagName("tr"));
				System.out.println(rows);*/
	}

	private int GetRestData(String clusterName) {
	// TODO Auto-generated method stub
		GetRestData oRestData = new GetRestData(logger);
		int count=0;
		try {
			//hmCRestData = oRestData.getClustersData();
			hmCRestData = oRestData.getClusterRestData();
				for (String key : hmCRestData.keySet())
				{
//					String sExpData = hmCRestData.get(key).toString();
					ClusterData sd=hmCRestData.get(key);
//					System.out.println("Cluster: "+key +" Having NodeId: "+sd.getNODEID());
					if (clusterName.equals(key))
					{
						count = sd.getSum();
//						System.out.println("Sum of Situations on Cluster "+key+ " is " +sd.getSum());
						logger.info("Sum of Situations on Cluster "+key+ " is " +sd.getSum());
						break;
					}
				}
			}catch (Exception e) {
				String message = "Class '" + Servers.class.getSimpleName()+ "'\n"+ e.getMessage();
				logger.error( classname + ": " + message + e.getStackTrace());
			}
		return count;
		}
}