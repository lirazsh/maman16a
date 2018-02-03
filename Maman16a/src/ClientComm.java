import java.io.*;
import java.net.*;


public class ClientComm {
	
	private String host; 
	private int port;
	private Socket socket;
	
	public ClientComm(String host, int port) {
		
		this.host = host;
		this.port = port;
		
	}
	
	public String getMenu() {
		
		try {
			socket = new Socket(host, port);
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            
			System.out.println("Requesting Menu...");
			out.write("GetMenu\n");
			out.flush();
			
			String menu = in.readLine();
			//while (input != null) {
			//	menu += input;
			//	input = in.readLine();
			// }
			
			out.close();
			in.close();
			socket.close();
			
			return menu;
					
		} catch (UnknownHostException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
		
		return "Menu error";
		
	}
	
	public void sendOrder(Order order) {
		
		try {
			
			Socket socket = new Socket(host, port);
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println("Requesting to send order...");
			out.write("Order\n");
			out.flush();
			
			out.write(order.getInfo() + order.toString() + "\n");
			out.flush();
			
			System.out.println("SERVER CONFIRMATION:");
			String output = in.readLine();
			while (output != null) {
				System.out.println(output);
				output = in.readLine();
			}
			
			out.close();
			in.close();
			socket.close();
			
		} 
    	catch (UnknownHostException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

}
