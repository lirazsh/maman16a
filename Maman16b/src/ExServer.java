import java.io.IOException;
import java.net.*;

public class ExServer {
	
	private static String from,to,result;
	private static float amount;
	
	public static void main(String[] args) {
		while (true) {
			try {
				
				// receive request
				DatagramSocket socket = new DatagramSocket(4444);
				System.out.println("Server is listenein at 4444...");
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				parsePacket(packet);
				Convertor myCon = new Convertor(from, to, amount);
				result = String.valueOf(myCon.convert());
				
				// response
				String response = new String(packet.getData(), 0, packet.getLength());
				response += ":" + result;
				System.out.println("Response: " + response);
				buf = response.getBytes();
				InetAddress addr = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, addr, port);
				
				socket.send(packet);
				socket.close();
				
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	private static void parsePacket(DatagramPacket pk) {
		
		String response = new String(pk.getData(), 0, pk.getLength());
		String[] str = response.split("/");
		from = str[0];
		to = str[1];
		amount = Float.parseFloat(str[2]);
		
	}
}
 