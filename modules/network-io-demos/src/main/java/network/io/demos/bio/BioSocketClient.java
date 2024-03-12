package network.io.demos.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class BioSocketClient {
	public static void main(String[] args) {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			// Create Socket
			socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8765);
			//Connect servers
			socket.connect(socketAddress);
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//Send a request to the server
			out.println("Bio Client sends request information  request");
			String response;
			//Receive server response data
			response = in.readLine();
			System.out.println(new String(response));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			socket = null;
		}
	}
}
