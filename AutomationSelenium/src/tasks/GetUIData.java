package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import dashboardUtil.ClusterData;
import dashboardUtil.ClusterDataInitializer;
import dashboardUtil.ServerData;
import dashboardUtil.ServerDataInitializer;
import exceptions.ElementNotFoundException;

public class GetUIData {

	Logger logger;
	public GetUIData(Logger logger) {
		this.logger = logger;
		
	}
//	private HashMap<String, ClusterData> CUIData = new HashMap<String, ClusterData>();
	private HashMap<String, String> CUIData = new HashMap<String, String>();
	private HashMap<String, ServerData> SUIData = new HashMap<String, ServerData>();
	private WebDriver driver;
	/**Reads UI table elements and prints all data
	 * 
	 * @param driver
	 * @param rows
	 * @throws ElementNotFoundException 
	 * @throws Exception
	 *
	 */
	
	
	public HashMap<String,String> getClusterData(List<String> columns2) throws ElementNotFoundException
	{
		ClusterData c = new ClusterData();
		List <String> newList = new ArrayList<String>();
		List <String> columns = new ArrayList<String>();
		List <String> key = new ArrayList<String>();
		List <String> splits = new ArrayList<String>();

		for (String elem : columns2) {
			if(columns2.isEmpty())
				{
					ElementNotFoundException e = new ElementNotFoundException("No data in table");
					throw e;
				}
			newList.add(elem.toString());
//			columns.add(elem.getText().toString().replaceAll(" ", " | "));
			columns.add(elem.toString().replaceAll("\n", " "));
		}
		for(int i = 0;i<newList.size();i++){
			String str1[] = newList.get(i).toString().split("\n");
			splits.add(str1[0]);
		}
		
		for (int i=0;i<key.size();i++) {
			CUIData.put(key.get(i),columns.get(i) );
		}
		
		for(int i=0;i<columns.size();i++)
		{
//			System.out.println(newList.get(i));
			CUIData.put(splits.get(i), columns.get(i));
		}
				
/*		Set<String> keys = CUIData.keySet();
		Set<String> values = (Set<>) CUIData.values();
		Iterator iterator = CUIData.keySet().iterator();  
		for (String name :CUIData.keySet()) {
		  	String k =name.toString();
            String value = CUIData.get(name).toString();  
            System.out.println(k + " " + value);
		}
		
		for (String keyss: keys) {
			System.out.println("Key="+keyss+ "Values="+ CUIData.get(keyss));
		}*/
		
		
		return CUIData;
	}
	
	public HashMap<String, String> getServersData(List<String> columns2) throws ElementNotFoundException {
		List <String> newList = new ArrayList<String>();
		List <String> columns = new ArrayList<String>();
		List <String> splits = new ArrayList<String>();

		for (String elem : columns2) {
			if(columns2.isEmpty())
				{
					ElementNotFoundException e = new ElementNotFoundException("No data in table");
					throw e;
				}
			newList.add(elem.toString());
//			columns.add(elem.getText().toString().replaceAll(" ", " | "));
			columns.add(elem.toString().replaceAll("\n", " "));
		}
		for(int i = 0;i<newList.size();i++){
			String str1[] = newList.get(i).toString().split("\n");
			splits.add(str1[0]);
		}

		for(int i=0;i<columns.size();i++)
		{
			CUIData.put(splits.get(i), columns.get(i));
		}
				
		return CUIData;
		
	}
	
	
	/*
	 * public HashMap<String, ClusterData> getClustersData(List<WebElement> rows) throws ElementNotFoundException
	{
		Iterator<WebElement> i = rows.iterator();
		List<String> lheaders = new ArrayList<String>(); 
		boolean isHeader=true;
		int counter = 0;
		CUIData = new HashMap<String, ClusterData>();
	
		while(i.hasNext())
		{
			if(counter++==(rows.size()-1)) break;//eliminate last row 
			WebElement row=i.next();
			if (isHeader)
			{
				List<WebElement> headers = row.findElements(By.tagName("th"));
				System.out.println(headers);
				if(headers.isEmpty())
				{
					continue;
					//ElementNotFoundException e = new ElementNotFoundException("No headers");
					//throw e;
				}
				Iterator<WebElement> h = headers.iterator();
				while(h.hasNext())
				{
					WebElement header = h.next();
					String sHeaderText = header.getText();
					System.out.println(sHeaderText);
					if (!sHeaderText.equals(""))
					{
						lheaders.add(sHeaderText);
					} else 
					{
						String[] sHeaderTitle = header.getAttribute("title").split("\\ -");
						System.out.println(sHeaderTitle[0]);
						if(sHeaderTitle[0].isEmpty())
						{
							continue;
							//ElementNotFoundException e = new ElementNotFoundException("No headers as well as no Title");
							//throw e;
						}
						lheaders.add(sHeaderTitle[0]);
					}
				}
				isHeader=false;
			}
			else
			{
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if(columns.isEmpty())
				{
					ElementNotFoundException e = new ElementNotFoundException("No data in table");
					throw e;
				}
				Iterator<WebElement> j = columns.iterator();
				ClusterData c = new ClusterData();
				ClusterDataInitializer.initUI(c, j, lheaders);
				CUIData.put(c.getCN(), c);
			}
		}
		
		return CUIData;
	}*/



	
	/*public HashMap<String, ServerData> getServersData(List<WebElement> rows, WebDriver driver) throws ElementNotFoundException
	{	
		Iterator<WebElement> i = rows.iterator();
		int counter = 0;
		List<String> lheaders = new ArrayList<String>(); 
		boolean isHeader=true;
		SUIData=new HashMap<String, ServerData>();
		while(i.hasNext())
		{
			if(counter++==rows.size()-1) break;//eliminate last row
			WebElement row=i.next();
			if (isHeader)
			{
				List<WebElement> headers = row.findElements(By.tagName("th"));
				if(headers.isEmpty())
				{
					//ElementNotFoundException e = new ElementNotFoundException("No headers");
					//throw e;
					continue;
				}
				Iterator<WebElement> h = headers.iterator();
				while(h.hasNext())
				{
					WebElement header = h.next();
					String sHeaderText = header.getText();
					if (!sHeaderText.equals(""))
					{

						lheaders.add(sHeaderText);
					} else 
					{
						String[] sHeaderTitle = header.getAttribute("title").split("\\ -");
						System.out.println(sHeaderTitle[0]);if(sHeaderTitle[0].isEmpty())
						{
							continue;
							//ElementNotFoundException e = new ElementNotFoundException("No headers as well as no Title");
							//throw e;
						}
						lheaders.add(sHeaderTitle[0]);
					}
				}
				isHeader=false;
			}
			else
			{
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if(columns.isEmpty())
				{
					ElementNotFoundException e = new ElementNotFoundException("No data in table");
					throw e;
				}
				Iterator<WebElement> j = columns.iterator();

				ServerData s = new ServerData();
				ServerDataInitializer.initUI(s,j,lheaders,driver);
				SUIData.put(s.getSH(), s);				
			}
		}
		return SUIData;
	}*/

	public void printClusterData()
	{
		String sDatatoPrint = "";
		for (String key : CUIData.keySet()) {
			sDatatoPrint = sDatatoPrint.concat(CUIData.get(key).toString() + "\n");
		}
		logger.info("Cluster data on UI--------\n" + sDatatoPrint);
	}
	
	public void printServerData()
	{
		String sDataToPrint = "";
		for (String key : SUIData.keySet()) {
			sDataToPrint = sDataToPrint.concat(SUIData.get(key).toString() + "\n");
		}
		logger.info("Servers UI Data-----------\n" + sDataToPrint);
	}
	
}
