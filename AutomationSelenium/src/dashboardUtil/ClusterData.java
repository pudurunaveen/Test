package dashboardUtil;

public class ClusterData {
	
	private String NODEID;
	private String CN;
	private int FATAL;
	private int CRITICAL;
	private int WARNING;
	private int HARMLESS;
	private int INFORMATIONAL;
	private int UNKNOWN;
	private String DATACENTER;
	private int NS;
	private int Percent_Used_Storage;
	private double Percent_Used_CPU;
	private double Percent_Used_Memory;
	
	public ClusterData() {
	}
		public String getNODEID() {
		return NODEID;
	}

	public void setNODEID(String nODEID) {
		NODEID = nODEID;
	}

	public String getCN() {
		return CN;
	}

	public void setCN(String cN) {
		CN = cN;
	}

	public int getFATAL() {
		return FATAL;
	}

	public void setFATAL(int fATAL) {
		FATAL = fATAL;
	}

	public int getCRITICAL() {
		return CRITICAL;
	}

	public void setCRITICAL(int cRITICAL) {
		CRITICAL = cRITICAL;
	}

	public int getWARNING() {
		return WARNING;
	}

	public void setWARNING(int wARNING) {
		WARNING = wARNING;
	}

	public int getHARMLESS() {
		return HARMLESS;
	}

	public void setHARMLESS(int hARMLESS) {
		HARMLESS = hARMLESS;
	}

	public int getINFORMATIONAL() {
		return INFORMATIONAL;
	}

	public void setINFORMATIONAL(int iNFORMATIONAL) {
		INFORMATIONAL = iNFORMATIONAL;
	}

	public int getUNKNOWN() {
		return UNKNOWN;
	}

	public void setUNKNOWN(int uNKNOWN) {
		UNKNOWN = uNKNOWN;
	}

	public String getDataCenter() {
		return DATACENTER;
	}

	public void setDataCenter(String DataCenter) {
		DATACENTER = DataCenter;
	}

	public int getNS() {
		return NS;
	}

	public void setNS(int nS) {
		NS = nS;
	}

	public int getPercent_Used_Storage() {
		return Percent_Used_Storage;
	}

	public void setPercent_Used_Storage(int percentUsedStorage) {
		Percent_Used_Storage = percentUsedStorage;
	}

	public double getPercent_Used_CPU() {
		return Percent_Used_CPU;
	}

	public void setPercent_Used_CPU(double d) {
		Percent_Used_CPU = d;
	}

	public double getPercent_Used_Memory() {
		return Percent_Used_Memory;
	}

	public void setPercent_Used_Memory(double d) {
		Percent_Used_Memory = d;
	}
	
	public int getSum(){
		int sum = FATAL + CRITICAL + WARNING + INFORMATIONAL + HARMLESS + UNKNOWN ;
		return sum;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public String toString() {
		return CN+" "+
		FATAL+" "+
		CRITICAL+" "+
		WARNING+" "+
		HARMLESS+" "+
		INFORMATIONAL+" "+
		UNKNOWN+" "+
		DATACENTER+" "+
		NS+" "+
		Percent_Used_Storage+" "+
		Percent_Used_CPU+" "+
		Percent_Used_Memory;
		//NODEID+""+
		//return super.toString();
	}
	

	
	
}
