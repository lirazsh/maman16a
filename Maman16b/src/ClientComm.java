import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientComm {
	
	String host; 
	int port;
	
	public ClientComm(String host, int port) {
		
		this.host = host;
		this.port = port;
		
	}
	
	public String convert(String req) {
		try {
			
			DatagramSocket socket = new DatagramSocket();
			
			byte[] buf = new byte[256];
			buf = req.getBytes();
			
			InetAddress addr = InetAddress.getByName(host);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, port);
			socket.send(packet);
			
			buf = new byte[256];
			packet = new DatagramPacket(buf, buf.length);
			
			//socket.setSoTimeout(5000);
			socket.receive(packet);
			
			String response = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Server: " + response);
			
			socket.close();
			
			return response;
			
		} catch(IOException e) {e.printStackTrace(); }
		
		return "error";
	}
}
