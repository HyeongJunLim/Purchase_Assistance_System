package MVC.model;


public class TransHandGesture {
	private String userId;				// ÁöÁ¡_¾ÆÀÌµð
	private int type;					// Å¸ÀÔ(¸Þ´º¼±ÅÃ/¼ö·®/ÇÖ¾ÆÀÌ½º µîµî)
	private String content;				// ³»¿ë
	private double[][] RhandX;			// R XÁÂÇ¥
	private double[][] RhandY;			// R YÁÂÇ¥
	private double[][] RhandZ;			// R ZÁÂÇ¥
	private double[][] LhandX;			// L XÁÂÇ¥
	private double[][] LhandY;			// L YÁÂÇ¥
	private double[][] LhandZ;			// L ZÁÂÇ¥
	private double RpalmPositionX;		// ¿À¸¥¼Õ¹Ù´Ú Áß¾Ó X
	private double RpalmPositionY;		// ¿À¸¥¼Õ¹Ù´Ú Áß¾Ó Y
	private double RpalmPositionZ;		// ¿À¸¥¼Õ¹Ù´Ú Áß¾Ó Z
	private double LpalmPositionX;		// ¿Þ¼Õ¹Ù´Ú Áß¾Ó X
	private double LpalmPositionY;		// ¿Þ¼Õ¹Ù´Ú Áß¾Ó Y
	private double LpalmPositionZ;		// ¿Þ¼Õ¹Ù´Ú Áß¾Ó Z
	
	public TransHandGesture() {
		RhandX = new double[5][5];
		RhandY = new double[5][5];
		RhandZ = new double[5][5];
		LhandX = new double[5][5];
		LhandY = new double[5][5];
		LhandZ = new double[5][5];
	}
	
	public double getRHandX(int finger, int boneType) {
		return RhandX[finger][boneType];
	}
	public double getRHandY(int finger, int boneType) {
		return RhandY[finger][boneType];
	}
	public double getRHandZ(int finger, int boneType) {
		return RhandZ[finger][boneType];
	}
	public double getLHandX(int finger, int boneType) {
		return LhandX[finger][boneType];
	}
	public double getLHandY(int finger, int boneType) {
		return LhandY[finger][boneType];
	}
	public double getLHandZ(int finger, int boneType) {
		return LhandZ[finger][boneType];
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getRPalmPositionX() {
		return RpalmPositionX;
	}
	public void setRPalmPositionX(double RpalmPositionX) {
		this.RpalmPositionX = RpalmPositionX;
	}
	public double getRPalmPositionY() {
		return RpalmPositionY;
	}
	public void setRPalmPositionY(double RpalmPositionY) {
		this.RpalmPositionY = RpalmPositionY;
	}
	public double getRPalmPositionZ() {
		return RpalmPositionZ;
	}
	public void setRPalmPositionZ(double RpalmPositionZ) {
		this.RpalmPositionZ = RpalmPositionZ;
	}
	public double getLPalmPositionX() {
		return LpalmPositionX;
	}
	public void setLPalmPositionX(double LpalmPositionX) {
		this.LpalmPositionX = LpalmPositionX;
	}
	public double getLPalmPositionY() {
		return LpalmPositionY;
	}
	public void setLPalmPositionY(double LpalmPositionY) {
		this.LpalmPositionY = LpalmPositionY;
	}
	public double getLPalmPositionZ() {
		return LpalmPositionZ;
	}
	public void setLPalmPositionZ(double LpalmPositionZ) {
		this.LpalmPositionZ = LpalmPositionZ;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setTransHandGesture(int i, int j, double rx, double ry, double rz, double lx, double ly, double lz) {
		RhandX[i][j] = rx;
		RhandY[i][j] = ry;
		RhandZ[i][j] = rz;
		LhandX[i][j] = lx;
		LhandY[i][j] = ly;
		LhandZ[i][j] = lz;
	}
}
