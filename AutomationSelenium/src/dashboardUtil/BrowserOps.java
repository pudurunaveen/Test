package dashboardUtil;

import inputOps.InputParamOps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Capabilities;

public class BrowserOps {
	private Logger logger;
	public BrowserOps(Logger logger) {
		this.logger = logger;
	}
	private String URL="";
	WebDriver driver=null;
	InputParamOps oRops = new InputParamOps(logger);
	private DesiredCapabilities dr;
	public WebDriver openDashboard()
	{
		String sBrowserType = oRops.getGlobalProps("browser");
		try {
			if(sBrowserType.equals("FF"))
			{
				dr = setCapabilities();
				driver = new FirefoxDriver(dr);
				//setURL();
				driver.manage().window().setSize(new Dimension(1024, 768));
//				driver.manage().window().maximize();
				//String URL = getURL("URL");
				driver.get(getURL("URL"));
				logger.log(Level.INFO, "Opening firefox WebDriver URL:" + URL );
			}
			if(sBrowserType.equals("IE"))
			{
				//more code here
				driver = new InternetExplorerDriver();
			}
		} catch (WebDriverException e) {
			/*String sMessage = e.getMessage() + "\n" +
			"System info :" +e.getSystemInformation()+ "\n" +
			"Additional info :" + e.getAdditionalInformation();
			logger.error(sMessage);*/

		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
		int iImplicitTimeout = Integer.parseInt(oRops.getGlobalProps("implicit_timout"));
		int iPageLoadTimeout = Integer.parseInt(oRops.getGlobalProps("page_load_timeout"));
		driver.manage().timeouts().implicitlyWait(iImplicitTimeout, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(iPageLoadTimeout, TimeUnit.SECONDS);
		return driver;

	}
	public Capabilities getCapabilities()
	{
		Capabilities caps = ((RemoteWebDriver)driver).getCapabilities();
		return caps;
		
	}
	public WebDriver openDashboardInIE() throws Exception
	{
		driver = new InternetExplorerDriver();
		driver.get(getURL("URL"));
		return driver;
	}
	public WebDriver openRestInFF() throws Exception
	{
		driver=new FirefoxDriver();
		driver.get(getURL("home_page"));
		return driver;
	}

	private void setURL() throws FileNotFoundException, IOException
	{
		URL=oRops.getExpText("URL");
	}

	public String getURL(String sKey) throws Exception
	{
		URL=oRops.getGlobalProps(sKey);
		return URL;
	}

	private DesiredCapabilities setCapabilities()
	{
		//FirefoxProfile fp = new FirefoxProfile();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return capabilities;

	}
	public void findAndClosePopup(WebDriver driver)
	{
		Iterable<String> handles = driver.getWindowHandles();
		Iterator<String> i = handles.iterator();
		WebDriver popup = null;
		while(handles.iterator().hasNext()){
			String handle = i.next();
			popup = driver.switchTo().frame(handle);
			System.out.println(popup.getTitle());
		}
	}
	public void maximizeBrowser(WebDriver driver)
	{
		((JavascriptExecutor)driver).executeScript("if(window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};"); 
	}
}
