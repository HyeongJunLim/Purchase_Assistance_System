package MVC.model;


public class TransHandGesture {
	private String userId;				// ����_���̵�
	private int type;					// Ÿ��(�޴�����/����/�־��̽� ���)
	private String content;				// ����
	private double[][] RhandX;			// R X��ǥ
	private double[][] RhandY;			// R Y��ǥ
	private double[][] RhandZ;			// R Z��ǥ
	private double[][] LhandX;			// L X��ǥ
	private double[][] LhandY;			// L Y��ǥ
	private double[][] LhandZ;			// L Z��ǥ
	private double RpalmPositionX;		// �����չٴ� �߾� X
	private double RpalmPositionY;		// �����չٴ� �߾� Y
	private double RpalmPositionZ;		// �����չٴ� �߾� Z
	private double LpalmPositionX;		// �޼չٴ� �߾� X
	private double LpalmPositionY;		// �޼չٴ� �߾� Y
	private double LpalmPositionZ;		// �޼չٴ� �߾� Z
	
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
