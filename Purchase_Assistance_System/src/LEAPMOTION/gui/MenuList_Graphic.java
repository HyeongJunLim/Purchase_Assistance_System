package LEAPMOTION.gui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import MVC.model.TransHandGesture;

public class MenuList_Graphic extends Canvas {
	Image img = null;
	Graphics2D gImg = null;
	public TransHandGesture trans = null;
	int width;
	int height;
	
	
	final double ZOOM = (1.0/2.0);
	// ±½±â
	final int stroke = 2;
	// ÆùÆ®
	int fontSize = 12;
	Font font = new Font("°íµñ", Font.BOLD, fontSize);
	
	
	public MenuList_Graphic(int width, int height, TransHandGesture trans) {
		this.trans = trans;
		this.width = width;
		this.height = height;
		this.setSize(this.width/2, this.height/2);
		this.setVisible(true);
	}
	public void paint(Graphics g) {
		img = createImage(this.width/2, this.height/2);
		gImg = (Graphics2D)img.getGraphics();

		gImg.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, 0));
		gImg.setFont(font);	
		drawPoint();
		g.drawImage(img, 0, 0, this); 
	}
	public void drawPoint()	{
		gImg.clearRect(0, 0, width, height);

		for(int q1=0; q1<5; q1++)	{
			for(int w1=0; w1<5; w1++)	{
				gImg.setColor( Color.BLACK );
				if(w1 > 1)	{
					gImg.drawLine( ((int)(trans.getRHandX(q1, w1-1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getRHandZ(q1, w1-1)*ZOOM))+(this.getHeight()/2),
									((int)(trans.getRHandX(q1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getRHandZ(q1, w1)*ZOOM))+(this.getHeight()/2) );
					gImg.drawLine( ((int)(trans.getLHandX(q1, w1-1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getLHandZ(q1, w1-1)*ZOOM))+(this.getHeight()/2),
									((int)(trans.getLHandX(q1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getLHandZ(q1, w1)*ZOOM))+(this.getHeight()/2) );
				}
				else if(q1==4 && w1>0)	{
					gImg.drawLine( ((int)(trans.getRHandX(q1, w1-1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getRHandZ(q1, w1-1)*ZOOM))+(this.getHeight()/2),
									((int)(trans.getRHandX(q1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getRHandZ(q1, w1)*ZOOM))+(this.getHeight()/2) );
					gImg.drawLine( ((int)(trans.getLHandX(q1, w1-1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getLHandZ(q1, w1-1)*ZOOM))+(this.getHeight()/2),
									((int)(trans.getLHandX(q1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getLHandZ(q1, w1)*ZOOM))+(this.getHeight()/2) );
				}
				
				if(q1>0 && w1<=1)	{
					gImg.drawLine( ((int)(trans.getRHandX(q1-1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getRHandZ(q1-1, w1)*ZOOM))+(this.getHeight()/2),
									((int)(trans.getRHandX(q1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getRHandZ(q1, w1)*ZOOM))+(this.getHeight()/2) );
					gImg.drawLine( ((int)(trans.getLHandX(q1-1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getLHandZ(q1-1, w1)*ZOOM))+(this.getHeight()/2),
									((int)(trans.getLHandX(q1, w1)*ZOOM))+(this.getWidth()/2) , ((int)(trans.getLHandZ(q1, w1)*ZOOM))+(this.getHeight()/2) );
				}
			}	// end of for(int w1=0; w1<5; w1++)
		}	// for(int q1=0; q1<5; q1++)
		
		gImg.setColor(Color.BLUE);
		gImg.drawString("³»¿ë: " + trans.getContent().toString(), 0, (this.getHeight()*3/4));
	}
}
