package dashboardUtil;

import inputOps.InputParamOps;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



public class ScreenShotCaptureUtil {

	private Logger logger;
	
	public static String captureScreenShot(Logger logger, WebDriver driver, String TCname)
	{
		InputParamOps IOps = new InputParamOps(logger);
		String filePath="";
		if (driver instanceof HtmlUnitDriver ||
				driver instanceof InternetExplorerDriver) return null; //can't capture the screen shot for this driver.
		try {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		File destFile = new File(IOps.getScreenShotFilePath("screenshotFile_Path", TCname));
		filePath = destFile.getCanonicalPath();
		
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
		
	}
	
}
