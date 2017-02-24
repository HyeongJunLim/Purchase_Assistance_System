import java.awt.Font;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;

import LEAPMOTION.gui.LOGIN;


public class Main {
	public static void main(String[] args) {
		try {
			// 1. ÀÚ¹Ù ³»Àå ·è¾ØÇÊ
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
			// 2. liquidlnf.jar : Liquid Look and Feel
			//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			
			// 3. quaqua.jar : Quaqua Look and Feel
			//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
			//JFrame.setDefaultLookAndFeelDecorated(true);
			// 4
			WebLookAndFeel.globalTextFont = new Font( "°íµñ", Font.PLAIN, 13 );
		    WebLookAndFeel.globalAcceleratorFont = new Font( "°íµñ", Font.PLAIN, 12 );
		    WebLookAndFeel.globalTitleFont = new Font( "°íµñ", Font.BOLD, 13 );
		    WebLookAndFeel.globalAlertFont = new Font( "°íµñ", Font.PLAIN, 13 );
		    WebLookAndFeel.globalMenuFont = new  Font( "°íµñ", Font.PLAIN, 13 );
		    WebLookAndFeel.globalTooltipFont = new Font( "°íµñ", Font.PLAIN, 13 );
		    WebLookAndFeel.globalControlFont = new Font( "°íµñ", Font.PLAIN, 13 );
		    WebLookAndFeel.buttonFont = new Font( Font.DIALOG, Font.PLAIN, 12 );
		    WebLookAndFeel.install();
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (Exception e) {}
		
		LOGIN.getInstance();
	}
}
