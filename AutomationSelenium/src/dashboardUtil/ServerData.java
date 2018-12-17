package dashboardUtil;

public class ServerData {

	private String NODEID;
	private String SH;
	private int FATAL;
	private int CRITICAL;
	private int WARNING;
	private int HARMLESS;
	private int INFORMATIONAL;
	private int UNKNOWN;
	private String CLUSTER;
	private int NUMBER_VMS;
	private double AVCPR;
	private int Memory_Used_Percent;
	private int CPU_Used_Percent;
	private String OS;
	private int PC;
	private int Used_Memory;
	private String CS;
	
	public ServerData() {
		// TODO Auto-generated constructor stub
	}
	
	public String getNODEID() {
		return NODEID;
	}
	public void setNODEID(String nODEID) {
		NODEID = nODEID;
	}
	public String getSH() {
		return SH;
	}
	public void setSH(String sH) {
		SH = sH;
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
	public String getCLUSTER() {
		return CLUSTER;
	}
	public void setCLUSTER(String cLUSTER) {
		CLUSTER = cLUSTER;
	}
	public int getNUMBER_VMS() {
		return NUMBER_VMS;
	}
	public void setNUMBER_VMS(int nUMBERVMS) {
		NUMBER_VMS = nUMBERVMS;
	}
	public double getAVCPR() {
		return AVCPR;
	}
	public void setAVCPR(double aVCPR) {
		AVCPR = aVCPR;
	}
	public int getMemory_Used_Percent() {
		return Memory_Used_Percent;
	}
	public void setMemory_Used_Percent(int memoryUsedPercent) {
		Memory_Used_Percent = memoryUsedPercent;
	}
	public int getCPU_Used_Percent() {
		return CPU_Used_Percent;
	}
	public void setCPU_Used_Percent(int cPUUsedPercent) {
		CPU_Used_Percent = cPUUsedPercent;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public int getPC() {
		return PC;
	}
	public void setPC(int d) {
		PC = d;
	}
	public int getUsed_Memory() {
		return Used_Memory;
	}
	public void setUsed_Memory(int usedMemory) {
		Used_Memory = usedMemory;
	}
	public String getCS() {
		return CS;
	}
	public void setCS(String cS) {
		CS = cS;
	}
	
	public int getSum(){
		int sum = FATAL + CRITICAL + WARNING + INFORMATIONAL + HARMLESS + UNKNOWN ;
		return sum;
	}

	@Override
	public String toString() {
		return SH+" "+
		FATAL+" "+
		CRITICAL+" "+
		WARNING+" "+
		HARMLESS+" "+
		INFORMATIONAL+" "+
		UNKNOWN+" "+
		CLUSTER+" "+
		NUMBER_VMS+" "+
		AVCPR+" "+
		CPU_Used_Percent+" "+
		Memory_Used_Percent+" "+
		OS+" "+
		PC+" "+
		Used_Memory+" "+
		CS;
		//NODEID+" | "+
	}
	
}
