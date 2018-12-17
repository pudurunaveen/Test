package dashboardUtil;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import exceptions.ElementNotFoundException;

public class ClusterDataInitializer {
	public static void initRest(ClusterData c, JSONArray restData) throws JSONException {
		JSONObject jObjProp=null;
		for (int j = 0; j < restData.length(); j++) 
		{
			jObjProp = restData.getJSONObject(j);
			String sID = jObjProp.getString("id");
			String sValue = jObjProp.getString("value");

			if("NODEID".equals(sID)){
				c.setNODEID(sValue);
			}else 
				if("CN".equals(sID)){
				c.setCN((sValue));
			}else if("FATAL".equals(sID)){
				c.setFATAL(Integer.parseInt(sValue));
			}else if("CRITICAL".equals(sID)){
				c.setCRITICAL(Integer.parseInt(sValue));
			}else if("WARNING".equals(sID)){
				c.setWARNING(Integer.parseInt(sValue));
			}else if("HARMLESS".equals(sID)){
				c.setHARMLESS(Integer.parseInt(sValue));
			}else if("INFORMATIONAL".equals(sID)){
				c.setINFORMATIONAL(Integer.parseInt(sValue));
			}else if("UNKNOWN".equals(sID)){
				c.setUNKNOWN(Integer.parseInt(sValue));
			}else if("DATACENTER".equals(sID)){
				c.setDataCenter(sValue);
			}else if("NS".equals(sID)){
				c.setNS(Integer.parseInt(sValue));
			}else if("Percent_Used_Storage".equals(sID)){
				c.setPercent_Used_Storage(Integer.parseInt(sValue));
			}else if("Percent_Used_CPU".equals(sID)){
				if(sValue.equals("-1"))
					c.setPercent_Used_CPU(Double.parseDouble(sValue));
				else 
					c.setPercent_Used_CPU(Double.parseDouble(sValue));
			}else if("Percent_Used_Memory".equals(sID)){
				if(sValue.equals("-1"))
					c.setPercent_Used_Memory(Double.parseDouble(sValue));
				else
					c.setPercent_Used_Memory(Double.parseDouble(sValue));

			}
		}
	}
	public static void initUI(ClusterData c, Iterator<WebElement> rIterator, List<String> lheaders) throws ElementNotFoundException {

		Iterator<String> hIterator=lheaders.iterator();
		while(rIterator.hasNext())
		{
			WebElement column = rIterator.next();
			String sColname = hIterator.next();
			String sValue = column.getText().toString();
			if(sValue.isEmpty())
			{
				column = rIterator.next();
				sValue = column.getText().toString();
				System.out.println("sValue in if condition = "+sValue);
			}
			/*	if (column.getText().equalsIgnoreCase("Unavailable")||column.getText().trim().isEmpty())
			{
				String message = "Data is showing 'Unavailable' or blank in column:" + sColname.toString();
				Exception e = new Exception(message);
				throw e;
			}*/
			if("Cluster Name".equals(sColname.toString())){
				c.setCN(sValue);
			}else if("Fatal".equals(sColname.toString())){
				c.setFATAL(Integer.parseInt(sValue));
			}else if("Critical".equals(sColname.toString())){
				c.setCRITICAL(Integer.parseInt(sValue));
			}else if("Warning".equals(sColname.toString())){
				c.setWARNING(Integer.parseInt(sValue));
			}else if("Harmless".equals(sColname.toString())){
				c.setHARMLESS(Integer.parseInt(sValue));
			}else if("Informational".equals(sColname.toString())){
				c.setINFORMATIONAL(Integer.parseInt(sValue));
			}else if("Unknown".equals(sColname.toString())){
				c.setUNKNOWN(Integer.parseInt(sValue));
			}else if("Datacenter".equals(sColname.toString())){
				c.setDataCenter(sValue);
			}else if("Servers".equals(sColname.toString())){
				c.setNS(Integer.parseInt(sValue));
			}else if("Storage Used (%)".equals(sColname.toString())){
				c.setPercent_Used_Storage(Integer.parseInt(sValue));
			}else if("CPU Used (%)".equals(sColname.toString())){
				if(sValue.equals("Unavailable"))
				{
					sValue="-1";
					c.setPercent_Used_CPU(Double.parseDouble(sValue));
				}else

					c.setPercent_Used_CPU(Double.parseDouble(sValue));
			}else if("Memory Used (%)".equals(sColname.toString())){
				if(sValue.equals("Unavailable"))
				{
					sValue="-1";
					c.setPercent_Used_Memory(Double.parseDouble(sValue));
				}else
					c.setPercent_Used_Memory(Double.parseDouble(sValue));
//			}
//			else if("Node ID".equals(sColname.toString())){
//				c.setNODEID(sValue);
			}else continue;
		}
	}
}
