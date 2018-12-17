package util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class ReadFiles {

	private Logger logger; 
	public ReadFiles(Logger logger) {
		this.logger = logger;
	}
	
	public List<String> readFile(String sPath) throws IOException
	{
		File file = new File(sPath);
		List<String> readlines = FileUtils.readLines(file);
		
		for (String line : readlines) {
			
			if (!(line.equals(""))){
				System.out.println(line);
			}
		}
	
		return readlines;
	}
	
}
