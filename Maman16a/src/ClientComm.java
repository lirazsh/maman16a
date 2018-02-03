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
			
			// Send requested action to server
			out.write("GetMenu\n");
			out.flush();
			
			//read menu from server in the form of a single stream
			String menu = in.readLine();

			
			// close all resources - terminate connection
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
            
            // Send Order action to server
			out.write("Order\n");
			out.flush();
			
			// now that the server is expecting an order, send it
			out.write(order.getInfo() + order.toString() + "\n");
			out.flush();
			
			
			// receive order confirmation from the server
			System.out.println("CONFIRMATION FROM SERVER:");
			String output = in.readLine();
			while (output != null) {
				System.out.println(output);
				output = in.readLine();
			}
			
			
			// close resources
			out.close();
			in.close();
			socket.close();
			
		} 
    	catch (UnknownHostException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

}
