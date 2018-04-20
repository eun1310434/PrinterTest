import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class main  {
	public static void main(String[] args){
		
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If translucent windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
            System.err.println(
                "Translucency is not supported");
                System.exit(0);
        }
        
        //JFrame.setDefaultLookAndFeelDecorated(true);
    	Round20_Ex05_Sub tw = new Round20_Ex05_Sub();

        // Set the window to 55% opaque (45% translucent).
        //tw.setOpacity(0.55F);

        // Display the window.
        //tw.setVisible(true);

		
		
	}
}
