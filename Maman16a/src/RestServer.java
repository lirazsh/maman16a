import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RestServer {
	
	private static HashMap<String, String> menuItems;
	private String menuPath;

	public static void main(String[] args) {
			
		
		readMenuFromFile("C:\\Users\\jasminen\\Desktop\\menu.txt");
		System.out.println(menuItems.toString());
		
		startServer();

	}
	
	public static void readMenuFromFile(String file) {
		ArrayList<String> menuLines = new ArrayList<String>();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while (line != null) {
				menuLines.add(line);
				line = br.readLine();
			}
			
			HashMap<String, String> hm = new HashMap<String, String>();
			
			for (String item : menuLines) {
				String[] record = item.split(",");
				hm.put(record[0], record[1]);
			}
			
			menuItems = hm;
			
			br.close();
		} 
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
	    catch (IOException e) { e.printStackTrace(); }
		
		
		
	}
	

	
	public static void startServer() {

		ServerSocket srv = null;
		
		try {
			
			srv = new ServerSocket(3333);
			System.out.println("Starting server at port 3333...");
			while (true) {
				
				new RestThread(srv.accept(), menuItems).start();
			}
			
			
		} catch(IOException e){ e.printStackTrace(); }
	}


}
