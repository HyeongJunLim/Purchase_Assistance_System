import java.awt.Font;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;

import LEAPMOTION.gui.LOGIN;


public class Main {
	public static void main(String[] args) {
		try {
			// 1. �ڹ� ���� �����
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
			// 2. liquidlnf.jar : Liquid Look and Feel
			//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			
			// 3. quaqua.jar : Quaqua Look and Feel
			//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
			//JFrame.setDefaultLookAndFeelDecorated(true);
			// 4
			WebLookAndFeel.globalTextFont = new Font( "���", Font.PLAIN, 13 );
		    WebLookAndFeel.globalAcceleratorFont = new Font( "���", Font.PLAIN, 12 );
		    WebLookAndFeel.globalTitleFont = new Font( "���", Font.BOLD, 13 );
		    WebLookAndFeel.globalAlertFont = new Font( "���", Font.PLAIN, 13 );
		    WebLookAndFeel.globalMenuFont = new  Font( "���", Font.PLAIN, 13 );
		    WebLookAndFeel.globalTooltipFont = new Font( "���", Font.PLAIN, 13 );
		    WebLookAndFeel.globalControlFont = new Font( "���", Font.PLAIN, 13 );
		    WebLookAndFeel.buttonFont = new Font( Font.DIALOG, Font.PLAIN, 12 );
		    WebLookAndFeel.install();
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (Exception e) {}
		
		LOGIN.getInstance();
	}
}
