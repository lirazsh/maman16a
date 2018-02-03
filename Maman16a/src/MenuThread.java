import java.io.*;
import java.net.*;
import java.util.HashMap;

public class MenuThread extends Thread {
	
	private Socket socket;
	private HashMap<String, String> menu;	
	
	public MenuThread(Socket s, HashMap<String, String> menu) {
		socket = s;
		this.menu = menu;
	}
	
	public void run()
    {
		
		System.out.println("Server thread running...");
        
        try {
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        	System.out.println("Attempting to read command from client...");
        	
        	String clientAction = in.readLine();
        	
        	
        	System.out.println("Client action: " + clientAction);
        	
        	if (clientAction.equals("GetMenu")) {
        		
        		
        		//out.write(menu.toString() + "\n");
        		out.write(menu.toString());
        		out.flush();
        		
        		
        	} else if (clientAction.equals("Order")) {
        		
    			String input;
    			String order = "";
				input = in.readLine();
				while (in.ready()) { // changed from input != null
					order += in.readLine();
				}
        		out.write("VERIFICATION_FROM_SERVER: " + order + "\n");
        		out.flush();

        	}
        	
        	
        	// closing resources
        	out.close();
    		in.close();
    		socket.close();
        }
        catch(IOException e){ e.printStackTrace(); }
    }

	
}
