package dashboardUtil;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testcase.VerifyServersPage;

import appObject.ElementsTasks;


public class ServerDataInitializer extends ElementsTasks{

	private static Logger logger;
	//private static JavascriptExecutor driver;

	public ServerDataInitializer(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
	}

	public static void initRest(ServerData s, JSONArray restData) throws JSONException {
		JSONObject jObjProp=null;
		for (int j = 0; j < restData.length(); j++) 
		{
			jObjProp = restData.getJSONObject(j);
			String sId = jObjProp.getString("id");
			//System.out.println("sId["+j+"] = "+sId);
			String sValue = jObjProp.get("value").toString();

			if("NODEID".equals(sId)){
				s.setNODEID(sValue);
			}else 
				if("SH".equals(sId)){
				s.setSH((sValue));
			}else if("FATAL".equals(sId)){
				s.setFATAL(Integer.parseInt(sValue));
			}else if("CRITICAL".equals(sId)){
				s.setCRITICAL(Integer.parseInt(sValue));
			}else if("WARNING".equals(sId)){
				s.setWARNING(Integer.parseInt(sValue));
			}else if("HARMLESS".equals(sId)){
				s.setHARMLESS(Integer.parseInt(sValue));
			}else if("INFORMATIONAL".equals(sId)){
				s.setINFORMATIONAL(Integer.parseInt(sValue));
			}else if("UNKNOWN".equals(sId)){
				s.setUNKNOWN(Integer.parseInt(sValue));
			}else if("CLUSTER".equals(sId)){
				s.setCLUSTER(sValue);
			}else if("NUMBER_VMS".equals(sId)){
				s.setNUMBER_VMS(Integer.parseInt(sValue));
			}else if("AVCPR".equals(sId)){
				if(sValue.equals("-1"))
					s.setAVCPR(Integer.parseInt(sValue));
				else
					s.setAVCPR(Double.parseDouble(sValue));
			}else if("Memory_Used_Percent".equals(sId)){
				s.setMemory_Used_Percent(Integer.parseInt(sValue));
			}else if("CPU_Used_Percent".equals(sId)){
				s.setCPU_Used_Percent(Integer.parseInt(sValue));
			}else if("OS".equals(sId)){
				s.setOS(sValue);
			}else if("PC".equals(sId)){
				s.setPC(Integer.parseInt(sValue));
			}else if("Used_Memory".equals(sId)){
				s.setUsed_Memory(Integer.parseInt(sValue));
			}else if("CS".equals(sId)){
				s.setCS(sValue);
			}
		}
	}

	public static void initUI(ServerData s, Iterator<WebElement> rIterator,	List<String> lheaders, WebDriver driver) {

		Iterator<String> hIterator= lheaders.iterator();		
			
		
		while(rIterator.hasNext())
		{
			ElementsTasks etask=new ElementsTasks(logger);
			
			WebElement column = rIterator.next();
			String sColname = hIterator.next();
			if(!column.isDisplayed())
			{
			etask.scrollToElement(column,driver);
				//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", column);
			}
			
			String sValue = column.getText().toString();
		
			if(sValue.isEmpty())
			{
				column = rIterator.next();
				sValue = column.getText().toString();
				System.out.println("sValue in if condition = "+sValue);
			}
//			if("Node ID".equals(sColname.toString())){
//				s.setNODEID(sValue);
//			}else 
		
				if("Server Name".equals(sColname.toString())){
				s.setSH((sValue));
			}else if("Fatal".equals(sColname.toString())){
				s.setFATAL(Integer.parseInt(sValue));
			}else if("Critical".equals(sColname.toString())){
				s.setCRITICAL(Integer.parseInt(sValue));
			}else if("Warning".equals(sColname.toString())){
				s.setWARNING(Integer.parseInt(sValue));
			}else if("Harmless".equals(sColname.toString())){
				s.setHARMLESS(Integer.parseInt(sValue));
			}else if("Informational".equals(sColname.toString())){
				s.setINFORMATIONAL(Integer.parseInt(sValue));
			}else if("Unknown".equals(sColname.toString())){
				s.setUNKNOWN(Integer.parseInt(sValue));
			}else if("Cluster".equals(sColname.toString())){
				s.setCLUSTER(sValue);
			}else if("Virtual Machines".equals(sColname.toString())){
				s.setNUMBER_VMS(Integer.parseInt(sValue));
			}else if("Average VMs CPU % Ready".equals(sColname.toString())){
				if(sValue.equals("Unavailable"))
				{
					sValue="-0.0010";
					s.setAVCPR(Double.parseDouble(sValue));
				}else
					s.setAVCPR(Double.parseDouble(sValue));
			}else if("Memory Used (%)".equals(sColname.toString())){
				if(sValue.equals("Unavailable"))
				{
					sValue="-1";
					s.setMemory_Used_Percent(Integer.parseInt(sValue));
				}
				else
					s.setMemory_Used_Percent(Integer.parseInt(sValue));
			}else if("CPU Used (%)".equals(sColname.toString())){
				if(sValue.equals("Unavailable"))
				{
					sValue="-1";
					s.setCPU_Used_Percent(Integer.parseInt(sValue));
				}
				else
					s.setCPU_Used_Percent(Integer.parseInt(sValue));
			}else if("Overall Status".equals(sColname.toString())){
				s.setOS(sValue);
			}else if("Number of CPUs".equals(sColname.toString())){
				s.setPC(Integer.parseInt(sValue));
			}else if("Memory Used (MB)".equals(sColname.toString())){
				if(sValue.equals("Unavailable"))
				{
					sValue="-1";
					s.setUsed_Memory(Integer.parseInt(sValue));
				}else
					s.setUsed_Memory(Integer.parseInt(sValue));
			}else if("Connection Status".equals(sColname.toString())){
				s.setCS(sValue);
			}
				
				
		}
			
		
	
	}
}
	
