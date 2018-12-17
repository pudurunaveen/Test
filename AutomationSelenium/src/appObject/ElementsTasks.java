package appObject;

import inputOps.InputParamOps;
import inputOps.TestCaseOps;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opera.core.systems.testing.drivers.TestOperaDesktopDriver;

import testcase.driverScript;

import dashboardUtil.ScreenShotCaptureUtil;

import exceptions.DefaultExceptions;
import exceptions.ElementNotFoundException;

/**
 * Finds the element and performs actions on it.<br>
 * Object locator information is takes from Locator.properties file
 * @author milind_gholap
 *
 */

public class ElementsTasks {

	private static final long serialVersionUID = 1L;
	private Logger logger;
	private InputParamOps oIPops = new InputParamOps(logger);
	//private WebDriver driver;
	//private JavascriptExecutor driver;

	public ElementsTasks(Logger logger)
	{
		this.logger=logger;
	}

	/**
	 * Returns true if element is showing on the UI. <br>
	 * If element is not showing waits for default timeout period 
	 * @param driver
	 * @param sKey
	 * @return
	 */
	public boolean isElementPresent(WebDriver driver, String sKey){
		try {
			waitForElement(driver, sKey);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}catch (TimeoutException e1){
			return false;
		}
	}
	public boolean isElementPresent(WebDriver driver, String sBy, String sKey){
		try {
			waitForElement(driver, sBy, sKey);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}catch (TimeoutException e1){
			return false;
		} catch (ElementNotFoundException e) {
			return false;
		}

	}
	//need to work on this
	public WebElement waitForElement(WebDriver driver, String sKey)
	{

		WebElement webElement = null;
		String sLocaor = oIPops.getLocator(sKey);
		int iTimeout = Integer.parseInt(oIPops.getGlobalProps("explicit_timeout"));
		try {
			By by = null;

			if (sKey.contains("id")) {
				by = By.id(sLocaor);
			} else if (sKey.contains("name")) {
				by = By.name(sLocaor);
			} else if (sKey.contains("xpath")) {
				by = By.xpath(sLocaor);
			} else if (sKey.contains("linkText")) {
				by = By.linkText(sLocaor);
			} else if (sKey.contains("partial")) {
				by = By.partialLinkText(sLocaor);
			} else if (sKey.contains("class")) {
				by = By.className(sLocaor);
			} else if (sKey.contains("css")) {
				by = By.cssSelector(sLocaor);
			} else if (sKey.contains("tagname")) {
				by = By.tagName(sLocaor);
			} else {
				throw new NoSuchElementException("Element is not found, may be either hidden or disabled");
			}
			ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOfElementLocated(by);
			webElement = (new WebDriverWait(driver, iTimeout)).until(condition);
		} catch (NoSuchElementException e) {
			/*String sMessage = "Element not found :" + e.getMessage()+"\n" +
			"System info : " + e.getSystemInformation() +"\n" +
			"Additional info : " + e.getAdditionalInformation();
			logger.error(sMessage);*/
		}
		return webElement;
	}

	/**
	 * Waits for element for specified timeout set in Global.properties file
	 * @param driver
	 * @param sBy
	 * @param sKey
	 * @return {@link WebElement}
	 * @throws ElementNotFoundException
	 */

	public WebElement waitForElement(WebDriver driver, String sBy, String sKey) throws ElementNotFoundException
	{
		WebElement webElement = null;
		String sLocator = oIPops.getLocator(sKey);
		int iTimeout = Integer.parseInt(oIPops.getGlobalProps("explicit_timeout"));
		try {
			By by = null;

			if (sBy.equalsIgnoreCase("id")) {
				by = By.id(sLocator);
			} else if (sBy.equalsIgnoreCase("name")) {
				by = By.name(sLocator);
			} else if (sBy.equalsIgnoreCase("xpath")) {
				by = By.xpath(sLocator);
			} else if (sBy.equalsIgnoreCase("linkText")) {
				by = By.linkText(sLocator);
			} else if (sBy.equalsIgnoreCase("partial")) {
				by = By.partialLinkText(sLocator);
			} else if (sBy.equalsIgnoreCase("class")) {
				by = By.className(sLocator);
			} else if (sBy.equalsIgnoreCase("css")) {
				by = By.cssSelector(sLocator);
			} else if (sBy.equalsIgnoreCase("tagname")) {
				by = By.tagName(sLocator);
			} else {
				throw new NoSuchElementException("Element is not found, may be either hidden or disabled");
			}
			ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOfElementLocated(by);
			webElement = (new WebDriverWait(driver, iTimeout)).until(condition);
		} catch (TimeoutException e) {
			String sMessage = e.getMessage();
			logger.error(e.getMessage());
			//	ScreenShotCaptureUtil.captureScreenShot(logger, driver, driverScript.getoTSReport().getTestCaseName());
			throw new ElementNotFoundException(sMessage, e);
		}catch (ElementNotVisibleException e){
			String sMessage = e.getMessage();
			logger.error(sMessage);
			//	ScreenShotCaptureUtil.captureScreenShot(logger, driver, driverScript.getoTSReport().getTestCaseName());
			throw new ElementNotFoundException(e);
		}catch(NoSuchElementException e){
			//	ScreenShotCaptureUtil.captureScreenShot(logger, driver, driverScript.getoTSReport().getTestCaseName());
			throw new ElementNotFoundException(e);
		}
		return webElement;
	}

	public WebElement getElement(WebDriver driver, String sKey) throws Exception
	{
		WebElement element=null;

		String sLocator = oIPops.getLocator(sKey);
		if (sKey.contains("id"))
			element= driver.findElement(By.id(sLocator));
		else if (sKey.contains("name"))
			element= driver.findElement(By.name(sLocator));
		else if (sKey.contains("xpath"))
			element= driver.findElement(By.xpath(sLocator));
		else if (sKey.contains("link"))
			element= driver.findElement(By.linkText(sLocator));
		else if (sKey.contains("css"))
			element= driver.findElement(By.cssSelector(sLocator));
		else if (sKey.contains("class"))
			element= driver.findElement(By.className(sLocator));
		else
			element = driver.findElement(By.id(sLocator));
		return element;
	}

	/**
	 * Finds the element on UI 
	 * @param driver
	 * @param sBy
	 * @param sKey
	 * @return {@link WebElement}
	 * @throws ElementNotFoundException
	 */
	public WebElement getElement(WebDriver driver, String sBy, String sKey) throws ElementNotFoundException
	{
		WebElement element=null;
		String sLocator = oIPops.getLocator(sKey);
		try {
			if (sBy.equalsIgnoreCase("id"))
				element= driver.findElement(By.id(sLocator));
			else if (sBy.equalsIgnoreCase("name"))
				element= driver.findElement(By.name(sLocator));
			else if (sBy.equalsIgnoreCase("xpath"))
				element= driver.findElement(By.xpath(sLocator));
			else if (sBy.equalsIgnoreCase("link"))
				element= driver.findElement(By.linkText(sLocator));
			else if (sBy.equalsIgnoreCase("css"))
				element= driver.findElement(By.cssSelector(sLocator));
			else if (sBy.equalsIgnoreCase("class"))
				element= driver.findElement(By.className(sLocator));
			else
				element = driver.findElement(By.id(sLocator));
		}catch (NoSuchElementException e) {
			//String TCname = driverScript.getoTSReport().getTestCaseName();
			//ScreenShotCaptureUtil.captureScreenShot(logger, driver, TCname);
			throw new ElementNotFoundException(logger, e);
		}catch (ElementNotVisibleException e){
			String sMessage = "Element not visible: " + sBy + " value: " +sLocator;
			logger.error(sMessage + " " + e.getMessage());
			//ScreenShotCaptureUtil.captureScreenShot(logger, driver, driverScript.getoTSReport().getTestCaseName());
			throw new ElementNotFoundException(e);
		}catch (TimeoutException e) {
			String message = "TimeoutException :" +e.getMessage() + e.getCause();
			logger.error(message);
			//ScreenShotCaptureUtil.captureScreenShot(logger, driver, driverScript.getoTSReport().getTestCaseName());
			throw new ElementNotFoundException(message , e);
		}catch (IllegalArgumentException e){
			logger.error("Illegal argument to find the element, please check the locator key " +sKey);
			throw new ElementNotFoundException(e);
		}
//logger.info(element);
		return element;
		
	}

	public void verifyText(WebDriver driver, String sKey) throws Exception
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		//String sActText = eTasks.getElement(driver, sKey).getText();
		String sActText = getElement(driver, sKey).getText();
		String sExpText = oIPops.getExpText(sKey);
		if (sExpText.equals(sActText))
			logger.info("Actual Text: " + sActText + " (matches)");
		else
			logger.info("Text not matching with expected\nActual :" + sActText + "\nExpected :"+sExpText);
		//ScreenShotCaptureUtil.captureScreenShot(driver, "new");
	}

	/**
	 * Verify text of the element, logs the result pass if text matches exactly 
	 * @param driver
	 * @param sBy
	 * @param sKey
	 * @throws ElementNotFoundException 
	 * 
	 */
	public void verifyText(WebDriver driver, String sBy, String sKey) throws ElementNotFoundException
	{
		//ElementsTasks eTasks = new ElementsTasks(logger);
		//String sActText = eTasks.getElement(driver, sKey).getText();
		String sActText;
		sActText = getElement(driver,sBy, sKey).getText();

		String sExpText = oIPops.getExpText(sKey);

		if(!(sExpText==null))
		{
			if (sExpText.equals(sActText))
				logger.info("Pass::Actual Text: " + sActText + " (matches)");
			else{
				String sMessage = "Fail::Text not matching with expected\nActual :" + sActText + "\nExpected :"+sExpText;
				logger.info(sMessage);
				String TCname = driverScript.getoTSReport().getTestCaseName();
				TestCaseOps.Status.add("Fail");
				TestCaseOps.Screnshotpath.add(ScreenShotCaptureUtil.captureScreenShot(logger, driver, TCname));
				TestCaseOps.Bgcolor.add("rgb(255, 51, 0);");
				//TestCaseOps.FailCounter = TestCaseOps.FailCounter + 1;
				TestCaseOps.TestCaseStatus="Fail";
				TestCaseOps.failFlag = true;
			}
		} else
			logger.error("Expected text not defined in property file.. or Key is incorrect " + sKey);

	}

	public void verifyElementPresent(WebDriver driver, String sBy, String sKey) throws ElementNotFoundException
	{
		if(isElementPresent(driver, sBy, sKey))
			logger.info("Pass: element present " + sKey);
	}


	public void selectFrame(WebDriver driver, String sKey) throws ElementNotFoundException
	{
		try {
			if (sKey.contains("name"))
				driver.switchTo().frame(driver.findElement(By.name(oIPops.getLocator(sKey))));
			else if (sKey.contains("id"))
				driver.switchTo().frame(driver.findElement(By.id(oIPops.getLocator(sKey))));
			else if (sKey.contains("xpath"))
				driver.switchTo().frame(driver.findElement(By.xpath(oIPops.getLocator(sKey))));
			else if (sKey.contains("css"))
				driver.switchTo().frame(driver.findElement(By.cssSelector(oIPops.getLocator(sKey))));
			else
				driver.switchTo().defaultContent();
		} catch (NoSuchFrameException e) {
			//logger.info(Level.ERROR,e);
			throw new ElementNotFoundException(e);
		}
		catch(NoSuchElementException e1)
		{
			throw new ElementNotFoundException(e1);
		}
	}
	/**
	 * Finds a frame and switch the context to it
	 * @param driver
	 * @param sBy
	 * @param sKey
	 * @throws ElementNotFoundException
	 */

	public void selectFrame(WebDriver driver,String sBy, String sKey) throws ElementNotFoundException
	{
		//String whandle = driver.getWindowHandle();
  		//logger.info("--------------------------");
		
		
		//logger.info("frame found");
		
		String sLocator = oIPops.getLocator(sKey);
		//logger.info(sLocator);
		try {
			if (sBy.equalsIgnoreCase("name"))
			
				driver.switchTo().frame(sLocator);
				//driver.switchTo().frame(getElement(driver, sBy, sKey));
				//driver.switchTo().frame(driver.findElement(By.name(sLocator)));
			else if (sBy.equalsIgnoreCase("id"))
			{
				//logger.info("--------------------------");
				driver.switchTo().frame(sLocator);
			}
			else if (sBy.equalsIgnoreCase("xpath"))
				driver.switchTo().frame(driver.findElement(By.xpath(sLocator)));
			else if (sBy.equalsIgnoreCase("css"))
				driver.switchTo().frame(driver.findElement(By.cssSelector(sLocator)));
			else
				driver.switchTo().defaultContent();
				//logger.info("--------------------------");
			if(sBy.equals(null)||sBy.equals(""))
				driver.switchTo().defaultContent();
//			else
//				driver.switchTo().frame(getElement(driver, sBy, sKey));
		} catch (NoSuchFrameException e) {
			//logger.info(Level.ERROR,e);
			throw new ElementNotFoundException(e);
		}
	}

	//native method calls
	public void clickElement(WebDriver driver, String sKey) throws Exception{
		getElement(driver, sKey).click();

	}
	public void clickElement(WebDriver driver, String sBy, String sKey) throws ElementNotFoundException{
		getElement(driver, sBy, sKey).click();

	}
	public void clickElementNWait(WebDriver driver, String sBy, String sKey) throws InterruptedException, ElementNotFoundException{
		getElement(driver, sBy, sKey).click();
		Thread.sleep(10000);
	}
	public void sendKeys(WebDriver driver, String sElementKey, String sValueKey) throws Exception
	{
		getElement(driver, sElementKey).sendKeys(sValueKey);

	}
	public void sendKeys(WebDriver driver, String sBy, String sKey, String sValueKey) throws ElementNotFoundException{

		getElement(driver, sBy, sKey).sendKeys(sValueKey);

	}
	public void scrollToElement(WebElement element, WebDriver driver){
	//WebDriver driver=null;
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		}

	
//	public void clickSlider(WebDriver driver, String sBy, String sKey){
//		 WebElement slider;
//		 //driver.findElement(By.xpath("//div[@id='slider']/a"))
//		 slider=getElement(driver, sBy, sKey);
	//} 
	
	//}
}
