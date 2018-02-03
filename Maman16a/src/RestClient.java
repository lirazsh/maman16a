
import java.net.*;
import java.util.*;

import javax.swing.JFrame;

import java.io.*;

public class RestClient {
	
	
	public static final int FRAME_BORDER = 600;
	private static Socket socket = null;
	private static String host = null;
	private static int port = 3333;
	private static HashMap<String, String> menu = null;
	private static String menuStr = null;
	
	public static void main(String[] args) {
		
		
		try {
			
			Scanner sc = new Scanner(System.in);
			System.out.print("Please put in the server's IP: ");
			host = sc.nextLine();
			sc.close();
			
			ClientComm cc = new ClientComm(host, port);
			menuStr = cc.getMenu();
			parseMenuStr();
			
			System.out.println("SERVER SENT MENU: " + menuStr);
			

						
			// create a new frame (heavy container)
			JFrame frame = new JFrame("Maman16a");
			frame.setSize(FRAME_BORDER, FRAME_BORDER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// create a new panel (light container)
			ClientPanel panel = new ClientPanel(menu, host, port);
			panel.setSize(FRAME_BORDER, FRAME_BORDER);
			
			frame.add(panel);
			frame.setVisible(true);
			panel.setVisible(true);
			
			
			
		}catch (Exception e) {e.printStackTrace();}
		
	}
	
	private static void parseMenuStr() {
		HashMap<String, String> hm = new HashMap<String, String>();
		String tmp = menuStr.substring(1, menuStr.length()-1);
				
		for (String str : tmp.split(",")) {
			String[] record = str.split("=");
			hm.put(record[0].trim(), record[1].trim());
		}
		
		menu = hm;
	}

}
