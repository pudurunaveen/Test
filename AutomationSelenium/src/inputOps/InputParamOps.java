package inputOps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;

import net.sourceforge.htmlunit.corejs.javascript.tools.shell.Environment;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;

/**
 * 
 * @author milind_gholap
 *
 */
public class InputParamOps {

	private Logger logger;
	public static String globalFilePath;
	
	
	public static String getGlobalFilePath() {
		return globalFilePath;
	}
	public static void setGlobalFilePath(String globalFilePath) throws IOException {
	//TODO to be changed later 
		File file = new File(new File("D:\\Cassini_Automation"), globalFilePath);		
		//File file = new File(new File("F:\\Selenium"), globalFilePath);
		InputParamOps.globalFilePath = file.getCanonicalPath();
	}
	public InputParamOps(Logger logger) {
		this.logger=logger;
	}
	/**
	 * Returns the Locator based on the key provided (Locator_resources.properties)
	 * @param sKey
	 * @return Locator value

	 */
	public String getLocator(String sKey)
	{	
		Properties oProp = new Properties();
		try {
			//String sPath = "./configFiles/Locator_resources.properties";
			String sPath = getLocatorFilePath("locatorFile_Path");
			oProp.load(new FileInputStream(sPath));
		} catch (FileNotFoundException e) {
			
			logger.fatal("Either Locaror_resources.properties file is missing or path is incorrect \n");
			logger.fatal("message:" +e.getLocalizedMessage()+ "Stacktrace::" + e.getStackTrace());
		} catch (IOException e) {
			logger.fatal("IO exception::" + e.getStackTrace());
		}
		return oProp.getProperty(sKey);
	}
	/**
	 * Returns the value based on the key provided (Panel_resources.properties)<br>
	 * Panel_resources.properties keeps all expected static texts for verification
	 * @param sKey
	 * @return expected text

	 */
	public String getExpText(String sKey)
	{
		Properties oProp = new Properties();
		try {
			//String sPath = "./configFiles/Panel_resources.properties";
			String sPath = getResourcesPath("panelResources_Path");
			oProp.load(new FileInputStream(sPath));
		} catch (FileNotFoundException e) {
			logger.fatal("Either Panel_resources.properties file is missing or path is incorrect \n");
			logger.fatal("message :" + e.getLocalizedMessage() + e.getStackTrace());
			//e.printStackTrace();
		} catch (IOException e) {
			logger.fatal("IO exception :" + e.getStackTrace());
		}
		return oProp.getProperty(sKey);
	}
	
	/**
	 * Returns the value based on the key provided from Global.properties.<br>
	 * Global.properties keeps all global data required.
	 * @param sKey
	 * @return global property

	 */
	public String getGlobalProps(String sKey)
	{
		Properties oProp = new Properties();
		try {
			//String sPath = "./configFiles/Global.properties";
			String sPath = getGlobalFilePath();
			oProp.load(new FileInputStream(sPath));
		} catch (FileNotFoundException e) {
			logger.fatal("Either Global.properties file is missing or path is incorrect \n");
			logger.fatal("message :" + e.getLocalizedMessage() + e.getStackTrace());
		} catch (IOException e) {
			logger.fatal("Io exception :" + e.getStackTrace());
		}
		return oProp.getProperty(sKey);
	}
	
	private String getLocatorFilePath(String sKey)
	{
		return getFilePath(sKey);
	}
	private String getResourcesPath(String sKey)
	{
		return getFilePath(sKey);
	}
	public String getTestCaseFilePath()
	{
		return getFilePath("tcfile_path");
	}
	private String getFilePath(String sKey)
	{
		Properties oProp = new Properties();
		String filePath = "";
		try {
			String sPath = getGlobalFilePath();
			oProp.load(new FileInputStream(sPath));
			String sDirectory = oProp.getProperty(sKey);
			File file = new File(new File(".").getAbsoluteFile(), sDirectory);
			
			if(!file.exists())
				throw new FileNotFoundException();
			
			filePath = file.getCanonicalPath();
		} catch (FileNotFoundException e) {
			logger.fatal("Error in Locator file path \n");
			logger.fatal("message :" + e.getLocalizedMessage() + e.getStackTrace());
		} catch (IOException e) {
			logger.fatal("Io exception :" + e.getStackTrace());
		}
		return filePath;
		
	}
	
	//converts string to int
	public int convertToNum(String value){
	    Integer newNum = new Integer(value);
		return newNum;		
	}
	
	
	public String getScreenShotFilePath(String sKey, String TCname)
	{
		Properties oProp = new Properties();
		String path = "";
		try {
			//String sPath = "./configFiles/Global.properties";
			String sPath = getGlobalFilePath();
			oProp.load(new FileInputStream(sPath));
		
		String sDirectory = oProp.getProperty(sKey);
		File file = new File(new File(".").getAbsoluteFile(), sDirectory);
		
		//System.out.println(file.getCanonicalPath());
		//System.out.println(file.getAbsolutePath());
		
		if(!file.exists())
			file.mkdirs();
				
		String folderPath = file.getCanonicalPath()+file.separator;
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
		Date date = new Date();
		
		StringBuilder filename = new StringBuilder(TCname);
		filename.append("-");
		filename.append(sdf.format(date));
		filename.append(".png");
		
		path = folderPath +filename;
		
		} catch (FileNotFoundException e) {
			logger.fatal("Error in path \n");
			logger.fatal("message :" + e.getLocalizedMessage() + e.getStackTrace());
		} catch (IOException e) {
			logger.fatal("Io exception :" + e.getStackTrace());
		}
		return path;
	}
	public String getReportFilePath(String sKey, String Suitename)
	{
		Properties oProp = new Properties();
		String path = "";
		try {
			//String sPath = "./configFiles/Global.properties";
			String sPath = getGlobalFilePath();
			oProp.load(new FileInputStream(sPath));
			
			String sDirectory = oProp.getProperty(sKey);
			File file = new File(new File(".").getAbsoluteFile(), sDirectory);
			
			if(!file.exists())
				file.mkdirs();
			
			
			String folderPath = file.getCanonicalPath()+file.separator;
		/*	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
			Date date = new Date();
			
			StringBuilder filename = new StringBuilder(Suitename);
			filename.append("-");
			filename.append(sdf.format(date));
			filename.append(".html");*/
			
			path = folderPath + Suitename;
		} catch (FileNotFoundException e) {
			logger.fatal("Error in path \n");
			logger.fatal("message :" + e.getLocalizedMessage() + e.getStackTrace());
		} catch (IOException e) {
			logger.fatal("Io exception :" + e.getStackTrace());
		}
		return path;
	}
}

