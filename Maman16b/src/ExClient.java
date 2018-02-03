import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import javax.swing.JFrame;

public class ExClient {
	
	private static String host;
	private static int port = 4444;
	private static final int FRAME_BORDER = 400;
	
	public static void main(String[] args) {
		
		try {
			
			Scanner sc = new Scanner(System.in);
			System.out.print("Please put in the server's IP: ");
			host = sc.nextLine();
			sc.close();
			
			// create a new frame (heavy container)
			JFrame frame = new JFrame("Maman16b");
			frame.setSize(FRAME_BORDER, 100);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			
			// create a new panel (light container)
			ClientPanel panel = new ClientPanel(host, port);
			panel.setSize(FRAME_BORDER, 100);
			
			frame.add(panel);
			frame.setVisible(true);
			panel.setVisible(true);
			
			
			
		}catch (Exception e) {e.printStackTrace();}
		
	}
	
	
		
}
