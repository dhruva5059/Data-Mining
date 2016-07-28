package Model;

public class TicTacToe {
	
	private String TL;	//x, o, b
	private String TM;	//x, o, b
	private String TR;	//x, o, b
	private String ML;	//x, o, b
	private String MM;	//x, o, b
	private String MR;	//x, o, b
	private String BL;	//x, o, b
	private String BM;	//x, o, b
	private String BR;	//x, o, b
	private String Label;	//positive, negative
	
	public String getTL() {
		return TL;
	}
	public void setTL(String tL) {
		TL = tL;
	}
	public String getTM() {
		return TM;
	}
	public void setTM(String tM) {
		TM = tM;
	}
	public String getTR() {
		return TR;
	}
	public void setTR(String tR) {
		TR = tR;
	}
	public String getML() {
		return ML;
	}
	public void setML(String mL) {
		ML = mL;
	}
	public String getMM() {
		return MM;
	}
	public void setMM(String mM) {
		MM = mM;
	}
	public String getMR() {
		return MR;
	}
	public void setMR(String mR) {
		MR = mR;
	}
	public String getBL() {
		return BL;
	}
	public void setBL(String bL) {
		BL = bL;
	}
	public String getBM() {
		return BM;
	}
	public void setBM(String bM) {
		BM = bM;
	}
	public String getBR() {
		return BR;
	}
	public void setBR(String bR) {
		BR = bR;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String toString(){
		return "TL = "+ TL +" TM = "+ TM +" TR = "+ TR + "\nML = "+ ML +" MM = "+ MM +" MR = "+ MR + "\nBL = "+ BL +" BM = "+ BM +" BR = "+ BR + "\nLabel = " + Label;
	}
}
