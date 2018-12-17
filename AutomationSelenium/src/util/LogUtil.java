package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LogUtil {
	private String sLoggerName="";
	private Logger logger;
	private String sDateTime=getCurrentTimestamp();
	

	public void init(Logger logger, String sLoggerName)throws Exception
	{
	
		//logger = Logger.getLogger(sLoggerName);
		setLoggerName(sLoggerName);
		String sFilePath = "./logs/";
				
		FileAppender appender =new FileAppender();
		appender.setName("appender1");
		appender.setFile(sFilePath+sDateTime+"."+sLoggerName+".log");
	//For log4j layouts refer http://www.allapplabs.com/log4j/log4j_layouts.htm
		String pattern = "%d %-5p [%c{1}] %m %n";
		PatternLayout oPL = new PatternLayout(pattern);
		
		appender.setLayout(oPL);
		
		appender.setThreshold(Level.DEBUG);
		appender.activateOptions();
		
		logger.addAppender(appender);
		setLogger(logger);
		
	}
	public void setLoggerName(String className)throws Exception{
		//sLoggerName=className;
		sLoggerName = "TestLogger";
	}
	private String getLoggerName()throws Exception
	{
		return this.sLoggerName;
	}
	public Logger getlogger()throws Exception
	{
		return logger;
	}
	private void setLogger(Logger logger)
	{
		this.logger=logger;
	}
	private String getCurrentTimestamp()
	{
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd-HHmm");
		Calendar cal = Calendar.getInstance();
		return sdFormat.format(cal.getTime());
			
	}
}
