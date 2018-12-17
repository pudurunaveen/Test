package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TableUtil {

	/**
	 * Reads data in the table and prints
	 * @param driver
	 * @param rows
	 * @throws Exception
	 */
	public void readTable(WebDriver driver, List<WebElement> rows) throws Exception
	{

		Iterator<WebElement> i = rows.iterator();
		List<String> lheaders = new ArrayList<String>(); 
		System.out.println("-----------------------------");
		boolean isHeader=true;
		while(i.hasNext())
		{
			WebElement row=i.next();
			System.out.print(rows.indexOf(row));
			// Get Headers in the row
			if (isHeader)
			{
				List<WebElement> headers = row.findElements(By.tagName("th"));
				if(headers.isEmpty())
				{
					System.out.println("No header");
				}else{
					Iterator<WebElement> h = headers.iterator();
					while(h.hasNext())
					{
						WebElement header = h.next();
						String sHeaderText = header.getText();
						System.out.print("|");
						if (!sHeaderText.equals(""))
						{
							System.out.print(sHeaderText);
							lheaders.add(sHeaderText);
						} else 
						{
							String[] sHeaderTitle = header.getAttribute("title").split("\\ -");
							System.out.print(sHeaderTitle[0]);	
							lheaders.add(sHeaderTitle[0]);
						}
					}
					isHeader=false;
				}
			}
			else //get rows data
			{
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if(columns.isEmpty())
				{
					Exception e = new Exception("No data in table");
					throw e;
				}
				Iterator<WebElement> j = columns.iterator();
				System.out.print("|");

				while(j.hasNext())
				{
					WebElement column = j.next();
					System.out.print(column.getText());
					System.out.print("|");
				}
			}
			System.out.println();
		}

		System.out.println("----------------------------");
		//
	}
}
