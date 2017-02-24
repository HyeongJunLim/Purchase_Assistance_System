package LEAPMOTION.gui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import LEAPMOTION.listener.LEAP_TRANS;
import MVC.model.HandGesture;



public class Graphic extends Canvas {
	private static Graphic instance = new Graphic();
	public static Graphic getInstance()	{
		return instance;
	}
	//----------------------------------------------------------
	
	HandGesture handGesture = HandGesture.getInstance();
	Image buffImage;
	Graphics2D buffg;
	
	int stroke = 10;							// 선굵기
	
	int height;									// 프레임 높이 
	int width;									// 프레임 너비
	
	// 폰트
	int fontSize = 20;
	Font font = new Font("고딕", Font.BOLD, fontSize);

	public void paint(Graphics g)   {
		height = this.getHeight()/2;
		width = this.getWidth()/2;
		
		buffImage = createImage(1000, 1000);
		buffg = (Graphics2D)buffImage.getGraphics();
		buffg.setFont(font);                     // 폰트설정
		buffg.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, 0));
	}
	public void update(Graphics g)	{
		try {
			handGesture.check_mutex.acquire();
			Draw_Point();
			handGesture.check_mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		g.drawImage(buffImage, 0, 0, this);
	}
	
	public void Draw_Point() {
		buffg.clearRect(0, 0, 1000, 1000);

		buffg.setColor(Color.BLACK);
		int trans_type = LEAP_TRANS.getInstance().trans_type;
		if(trans_type == 0)
			buffg.drawString("[기본]", 500, 50);
		else if(trans_type == 1)
			buffg.drawString("[메뉴 주문]", 500, 50);
		else if(trans_type == 2)
			buffg.drawString("[HOT/ICE 주문]", 500, 50);
		else if(trans_type == 3)
			buffg.drawString("[사이즈 선택]", 500, 50);
		else if(trans_type == 4)
			buffg.drawString("[수량 선택]", 500, 50);
		
		for(int q1=0; q1<5; q1++)	{
			for(int w1=0; w1<5; w1++)	{
				buffg.setColor( Color.getHSBColor(251, 204, 193) );
				if(w1 > 1)	{
					buffg.drawLine( ((int)handGesture.getRightX(q1, w1-1))+width , ((int)handGesture.getRightZ(q1, w1-1))+height,
									((int)handGesture.getRightX(q1, w1))+width , ((int)handGesture.getRightZ(q1, w1))+height );
					buffg.drawLine( ((int)handGesture.getLeftX(q1, w1-1))+width , ((int)handGesture.getLeftZ(q1, w1-1))+height,
									((int)handGesture.getLeftX(q1, w1))+width , ((int)handGesture.getLeftZ(q1, w1))+height );
				}
				else if(q1==4 && w1>0)	{
					buffg.drawLine( ((int)handGesture.getRightX(q1, w1-1))+width , ((int)handGesture.getRightZ(q1, w1-1))+height,
									((int)handGesture.getRightX(q1, w1))+width , ((int)handGesture.getRightZ(q1, w1))+height );
					buffg.drawLine( ((int)handGesture.getLeftX(q1, w1-1))+width , ((int)handGesture.getLeftZ(q1, w1-1))+height,
									((int)handGesture.getLeftX(q1, w1))+width , ((int)handGesture.getLeftZ(q1, w1))+height );
				}
				
				if(q1>0 && w1<=1)	{
					buffg.drawLine( ((int)handGesture.getRightX(q1-1, w1))+width , ((int)handGesture.getRightZ(q1-1, w1))+height,
									((int)handGesture.getRightX(q1, w1))+width , ((int)handGesture.getRightZ(q1, w1))+height );
					buffg.drawLine( ((int)handGesture.getLeftX(q1-1, w1))+width , ((int)handGesture.getLeftZ(q1-1, w1))+height,
									((int)handGesture.getLeftX(q1, w1))+width , ((int)handGesture.getLeftZ(q1, w1))+height );
				}
				
				
				
				buffg.setColor(Color.BLUE);
				if(handGesture.hasRight())	{
					buffg.fillOval( ((int)handGesture.getRightX(q1, w1))+width-4 , ((int)handGesture.getRightZ(q1, w1))+height-4, 10, 10);
				}
				if(handGesture.hasLeft())	{
					buffg.fillOval( ((int)handGesture.getLeftX(q1, w1))+width-4 , ((int)handGesture.getLeftZ(q1, w1))+height-4, 10, 10);
				}
				
				
			}	// end of for(int w1=0; w1<5; w1++)
		}	// for(int q1=0; q1<5; q1++)
	}
}
