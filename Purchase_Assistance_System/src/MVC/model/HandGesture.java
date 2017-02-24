package MVC.model;


import java.util.concurrent.Semaphore;

import com.leapmotion.leap.Vector;

public class HandGesture {
	private static HandGesture instance = new HandGesture();
	public static HandGesture getInstance()	{
		return instance;
	}

	public final Semaphore check_mutex = new Semaphore(1);
	
	public HandGesture() {
		// �ʱ�ȭ
		rightX = new double[5][5];
		rightY = new double[5][5];
		rightZ = new double[5][5];
		leftX = new double[5][5];
		leftY = new double[5][5];
		leftZ = new double[5][5];
	}
	//----------------------------------------------------------
	
	private double[][] rightX; 			// ������ x��ǥ
	private double[][] rightY; 			// ������ Y��ǥ
	private double[][] rightZ;			// ������ Z��ǥ
	private double[][] leftX;			// �޼� X��ǥ
	private double[][] leftY;			// �޼� Y��ǥ
	private double[][] leftZ;			// �޼� Z��ǥ
	private Vector rightPalmPosition;	// ������ �չٴ� �߾�
	private Vector leftPalmPosition;	// �޼� �չٴ� �߾�

	public void initInstance()	{
		try {
			check_mutex.acquire();
			
			rightPalmPosition = new Vector(-1000, -1000, -1000);
			leftPalmPosition = new Vector(-1000, -1000, -1000);
			
			for(int i=0; i<5; i++)	{
				for(int j=0; j<5; j++)	{
					rightX[i][j] = -1000.0;
					rightY[i][j] = -1000.0;
					rightZ[i][j] = -1000.0;
					leftX[i][j] = -1000.0;
					leftY[i][j] = -1000.0;
					leftZ[i][j] = -1000.0;
				}
			}
			check_mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean hasRight()	{
		if(rightX[0][0] == -1000)	
			return false;
		else
			return true;
	}
	public boolean hasLeft()	{
		if(leftX[0][0] == -1000)	
			return false;
		else
			return true;
	}
	
	
	public Vector getRightPalmPosition() {
		return rightPalmPosition;
	}
	public void setRightPalmPosition(Vector rightPalmPosition) {
		this.rightPalmPosition = rightPalmPosition;
	}
	public Vector getLeftPalmPosition() {
		return leftPalmPosition;
	}
	public void setLeftPalmPosition(Vector leftPalmPosition) {
		this.leftPalmPosition = leftPalmPosition;
	}
	
	public double getRightX(int finger, int boneType) {
		return rightX[finger][boneType];
	}
	public void setRightX(int finger, int boneType, double point) {
		rightX[finger][boneType] = point;
	}
	public double getRightY(int finger, int boneType) {
		return rightY[finger][boneType];
	}
	public void setRightY(int finger, int boneType, double point) {
		rightY[finger][boneType] = point;
	}
	public double getRightZ(int finger, int boneType) {
		return rightZ[finger][boneType];
	}
	public void setRightZ(int finger, int boneType, double point) {
		rightZ[finger][boneType] = point;
	}
	public double getLeftX(int finger, int boneType) {
		return leftX[finger][boneType];
	}
	public void setLeftX(int finger, int boneType, double point) {
		leftX[finger][boneType] = point;
	}
	public double getLeftY(int finger, int boneType) {
		return leftY[finger][boneType];
	}
	public void setLeftY(int finger, int boneType, double point) {
		leftY[finger][boneType] = point;
	}
	public double getLeftZ(int finger, int boneType) {
		return leftZ[finger][boneType];
	}
	public void setLeftZ(int finger, int boneType, double point) {
		leftZ[finger][boneType] = point;
	}
}