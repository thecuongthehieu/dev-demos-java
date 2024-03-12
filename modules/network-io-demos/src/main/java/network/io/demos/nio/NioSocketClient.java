package network.io.demos.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioSocketClient {
	public static void main(String[] args) {
		// 1. Create a connection address
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8765);
		// 2. Declare a connection channel
		SocketChannel socketChannel = null;
		// 3. Create a buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
			// 4. Open the Channel
			socketChannel = SocketChannel.open();
			// 5. Connecting servers
			socketChannel.connect(inetSocketAddress);
			while (true) {
				// 6. Define a byte array, and then use the system input function:
				byte[] bytes = new byte[1024];
				// 7. Keyboard input data
				System.in.read(bytes);
				// 8. Put the data in the buffer
				byteBuffer.put(bytes);
				// 9. Reset the buffer
				byteBuffer.flip();
				// 10. Write out the data
				socketChannel.write(byteBuffer);
				// 11. Emptying Buffer Data
				byteBuffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != socketChannel) {
				try {
					socketChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
