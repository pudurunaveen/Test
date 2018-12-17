package inputOps;

public class Testcase {

	private String TCNAME;
	private String TASK;
	private String LOCATOR;
	private String OBJECT;
	private String ACTION;
	private String DATA;
	
	public String getTCNAME() {
		return TCNAME;
	}
	public void setTCNAME(String tCNAME) {
		TCNAME = tCNAME;
	}
	public String getTASK() {
		return TASK;
	}
	public void setTASK(String tASK) {
		TASK = tASK;
	}
	public String getLOCATOR() {
		return LOCATOR;
	}
	public void setLOCATOR(String lOCATOR) {
		LOCATOR = lOCATOR;
	}
	public String getOBJECT() {
		return OBJECT;
	}
	public void setOBJECT(String oBJECT) {
		OBJECT = oBJECT;
	}
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
	public String getDATA() {
		return DATA;
	}
	public void setDATA(String dATA) {
		DATA = dATA;
	}
	
	@Override
	public String toString() {
		return TASK + " | " +
		LOCATOR + " | " +
		OBJECT+ " | " +
		ACTION + " | " +
		DATA;
		
		
	}

	
	
}
