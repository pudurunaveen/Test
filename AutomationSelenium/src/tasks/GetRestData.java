package tasks;

import inputOps.InputParamOps;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.LogUtil;
import dashboardUtil.ClusterData;
import dashboardUtil.ClusterDataInitializer;
import dashboardUtil.ServerData;
import dashboardUtil.ServerDataInitializer;
import exceptions.ElementNotFoundException;

public class GetRestData {
	private Logger logger;
	public GetRestData(Logger logger)
	{
		this.logger=logger;

	}
	private String classname = GetRestData.class.getSimpleName();
	private InputParamOps orOps = new InputParamOps(logger);
	
	private JSONObject jObj = null;
	private JSONArray jArrItems = null;
	private JSONObject jObjItems = null;
	private JSONArray jArrProp = null;
	private HashMap<String, ClusterData> hmCLRestData = null;
	int countRestData = 0;
	private HashMap<String, ServerData> hmSERestData = null;
	LogUtil ologLogUtil = new LogUtil();
	

	/**
	 * Get clusters data from REST
	 * @return {@link HashMap}
	 * @throws Exception
	 */
	
	public HashMap<String, ClusterData> getClustersData() throws Exception
	{
		String sURL = orOps.getGlobalProps("clusters_url");
		readClusterData(sURL);
		return hmCLRestData;
	}
	public int getClustersRowCount() throws Exception
	{
		String sURL = orOps.getGlobalProps("clusters_url");
		countRestData = readClusterDataCount(sURL);
		return countRestData;
	}
	
	public HashMap<String, ClusterData> getClusterRestData() throws Exception
	{
		String sURL = orOps.getGlobalProps("clusters_url");
		readClusterRestData(sURL);
		return hmCLRestData;
	}
	
	/**
	 * Get servers data from Rest
	 * @return {@link HashMap}
	 * @throws Exception
	 */
	public int getServersRowCount() throws Exception
	{
		String sURL = orOps.getGlobalProps("servers_url");
		int  rowCount = readServerData(sURL);
		return rowCount;
	}
	
	public HashMap<String, ServerData> getServerRestData() throws Exception
	{
		String sURL = orOps.getGlobalProps("servers_url");
		readServerRestData(sURL);
		return hmSERestData;
	}
	
	public HashMap<String, ServerData> getServerRestNodeData() throws Exception
	{
		String sURL = orOps.getGlobalProps("servers_url");
		readServerRestData(sURL);
		return hmSERestData;
	}
	
	// Used to get data regarding server situations on server group page
	public HashMap<String, ServerData> getServerSituationData() throws Exception
	{
		String sURL = orOps.getGlobalProps("serverSituation_url");
		readServerData(sURL);
		return hmSERestData;
	}
	
	/**
	 * Prints Clusters data 
	 
	public void printClustersData()
	{
		String sDataToPrint="";
		for (String key : countRestData.keySet()) {
			sDataToPrint = sDataToPrint.concat(countRestData.get(key).toString()+"\n");
		}
		logger.info(classname + ": Cluster Rest Data-------------\n" + sDataToPrint);
	}
	*/
	
	/**
	 * Prints Servers data
	 */
	public void printServersData()
	{
		String sDataToPrint = "";
		for (String key : hmSERestData.keySet()) {
			sDataToPrint = sDataToPrint.concat(hmSERestData.get(key).toString()+"\n");
		}
		logger.info(classname + ": Server Rest Data ------------\n" + sDataToPrint);
	}

	@SuppressWarnings("finally")
	private int readClusterDataCount(String sURL)
	{
		String sJSONString = getJSONString(sURL);
		int jArrItemsCount =0;
		try {


			jObj = new JSONObject(sJSONString);
			logger.info(jObj);
			jArrItems = jObj.getJSONArray("items");
			
			logger.info(classname + ": Number of rows returned by REST: " + jArrItems.length());
			jArrItemsCount = jArrItems.length();
		} catch (Exception e) {
			//throw new ElementNotFoundException(e);
			logger.fatal(e.getMessage()+e.getStackTrace());
			e.printStackTrace();
		}
		return jArrItemsCount;
	}
	
	private void readClusterData(String sURL)
	{
		String sJSONString = getJSONString(sURL);
		try {


			jObj = new JSONObject(sJSONString);
			logger.info(jObj);
			jArrItems = jObj.getJSONArray("items");
			
			logger.info(classname + ": Number of rows returned by REST: " + jArrItems.length());
			hmCLRestData=new HashMap<String, ClusterData>();
			
			ClusterData c = null;
			for (int i = 0; i < jArrItems.length(); i++) 
			{
				jObjItems = jArrItems.getJSONObject(i);
				jArrProp = jObjItems.getJSONArray("properties");
							
				c = new ClusterData();
				ClusterDataInitializer.initRest(c, jArrProp);
				hmCLRestData.put(c.getCN(),c);
			}
		} catch (Exception e) {
			//throw new ElementNotFoundException(e);
			logger.fatal(e.getMessage()+e.getStackTrace());
			e.printStackTrace();
		}
		
	}
	private void readClusterRestData(String sURL)
	{
		String sJSONString = getJSONString(sURL);
		try {


			jObj = new JSONObject(sJSONString);
			logger.info(jObj);
			jArrItems = jObj.getJSONArray("items");
			
			logger.info(classname + ": Number of rows returned by REST: " + jArrItems.length());
			hmCLRestData=new HashMap<String, ClusterData>();
			
			ClusterData c = null;
			for (int i = 0; i < jArrItems.length(); i++) 
			{
				jObjItems = jArrItems.getJSONObject(i);
				jArrProp = jObjItems.getJSONArray("properties");
							
				c = new ClusterData();
				ClusterDataInitializer.initRest(c, jArrProp);
				hmCLRestData.put(c.getCN(),c);
			}
		} catch (Exception e) {
			//throw new ElementNotFoundException(e);
			logger.fatal(e.getMessage()+e.getStackTrace());
			e.printStackTrace();
		}
		
	}

	private int readServerData(String sURL)
	{
		int rowCount=0;
		String sJSONString = getJSONString(sURL);
		try {
			jObj = new JSONObject(sJSONString);
			jArrItems = jObj.getJSONArray("items");

			logger.info(classname + ": Number of rows returned by REST: " + jArrItems.length());
			rowCount = jArrItems.length();
		} catch (Exception e) {
			logger.fatal(e.getMessage()+e.getStackTrace());
			e.printStackTrace();
		}
		return rowCount;
	}
	
	private void readServerRestData(String sURL)
	{

		String sJSONString = getJSONString(sURL);
		try {
			jObj = new JSONObject(sJSONString);
			jArrItems = jObj.getJSONArray("items");

			logger.info(classname + ": Number of rows returned by REST: " + jArrItems.length());
			hmSERestData = new HashMap<String, ServerData>();
			ServerData s = null;
			for (int i = 0; i < jArrItems.length(); i++) 
			{

				jObjItems = jArrItems.getJSONObject(i);
				jArrProp = jObjItems.getJSONArray("properties");
				s= new ServerData();
				ServerDataInitializer.initRest(s, jArrProp);
				hmSERestData.put(s.getSH(), s);
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage()+e.getStackTrace());
			e.printStackTrace();
		}
	}

	private String getJSONString(String sUrl)
	{
		HttpURLConnection connection = null;
		String 	restURL="";
		String restResponse="";
		try {
			String sBaseUrl = orOps.getGlobalProps("base_url");
			restURL = sBaseUrl.concat(sUrl);
			//restURL="http://10.44.184.90:15210/ibm/tivoli/rest/providers/itm.HUB_IBMESX6V7/datasources/TMSAgent.%26IBM.STATIC000/datasets/Topology.cluster/items?properties=NODEID%2CCN%2CFATAL%2CCRITICAL%2CWARNING%2CHARMLESS%2CINFORMATIONAL%2CUNKNOWN%2CDATACENTER%2CNS%2CPercent_Used_Storage%2CPercent_Used_CPU%2CPercent_Used_Memory&param_tz=Asia%2FKolkata&param_refId=1355215972082&count=100";
			URL url = new URL(restURL);
			connection = (HttpURLConnection)url.openConnection();
			String username = "sysadmin";
			String password = "Ibm1234";
			String userpass = username + ":" + password;
			String basicAuth = "Basic "
				+ javax.xml.bind.DatatypeConverter
				.printBase64Binary(userpass.getBytes());

			logger.info("REST URL::::" + url);
			logger.info(userpass + "  " + basicAuth);
			connection.setRequestProperty("Authorization", basicAuth);
			int responseCode = connection.getResponseCode();

			if (200 <= responseCode && responseCode <= 399) {
				InputStream in = connection.getInputStream();
				int data;

				while ((data = in.read()) != -1) {
					restResponse = restResponse + (char) data;
				}

				in.close();

			}else{
				logger.error(classname + ": URL <" + restURL + "> inaccessible, responseCode <" + responseCode + ">");
				//System.out.println("test");
			}
		} catch (Exception e) {
			//logger.fatal(classname + ": " + e.getStackTrace());
			e.printStackTrace();
		}
		connection.disconnect();
		System.out.println("end of method!");
		return restResponse;

		
	}

}
