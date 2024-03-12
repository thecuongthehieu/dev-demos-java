package network.io.demos.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioSocketServer implements Runnable {
	//1 Multiplexer (manage all channels)
	private Selector selector;
	// Read buffer
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

	public NioSocketServer() {
		try {
			// 1. Turn on multiplexer
			selector = Selector.open();
			//2. Open Server Channel (Network Read-Write Channel)
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			// 3. Set the server channel as non-blocking mode, true as blocking mode and false as non-blocking mode.
			serverSocketChannel.configureBlocking(false);
			// 4. Binding ports
			serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8765));
			// 5 Register Server Channel to Multiplexer
			// SelectionKey.OP_READ: Expresses concern about read data ready events
			// SelectionKey.OP_WRITE: Expresses concern about write data ready events
			// SelectionKey.OP_CONNECT: Expresses concern about socket channel connection completion events
			// SelectionKey.OP_ACCEPT: Expresses concern about the accept event of server-socket channel
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Server initialization completed-------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				/**
				 * a.select() Blocked to at least one channel ready for your registered event
				 * b.select(long timeOut) Blocked to at least one channel ready for your registered event or timeOut timeout
				 * c.selectNow() Return immediately. If there is no ready channel, return to 0
				 * select The return value of the method represents the number of ready channels.
				 */
				// 1. Multiplexer listening blocking
				selector.select();
				// 2. Result Set Selected by Multiplexer
				Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
				// 3. Continuous polling
				while (selectionKeys.hasNext()) {
					// 4. Get a selected key
					SelectionKey key = selectionKeys.next();
					// 5. Remove it from the container after acquisition
					selectionKeys.remove();
					// 6. Get only valid key s
					if (!key.isValid()) {
						continue;
					}
					// Blocking state processing
					if (key.isAcceptable()) {
						accept(key);
					}
					// Readable state processing
					if (key.isReadable()) {
						read(key);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Set the block and wait for the Client request. In traditional IO programming, ServerSocket and Socket are used. Server Socket Channel and Socket Channel used in NIO
	private void accept(SelectionKey selectionKey) {
		try {
			// 1. Access Channel Service
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
			// 2. Execution of blocking methods
			SocketChannel socketChannel = serverSocketChannel.accept();
			// 3. Set the server channel as non-blocking mode, true as blocking mode and false as non-blocking mode.
			socketChannel.configureBlocking(false);
			// 4. Register channels on multiplexers and set read identifiers
			socketChannel.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read(SelectionKey selectionKey) {
		try {
			// 1. Emptying Buffer Data
			readBuffer.clear();
			// 2. Get the channel registered on the multiplexer
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			// 3. Read the data and return it.
			int count = socketChannel.read(readBuffer);
			// 4. Return content - 1 indicates no data
			if (-1 == count) {
				selectionKey.channel().close();
				selectionKey.cancel();
				return;
			}
			// 5. If there is data, reset operation is performed before reading data.
			readBuffer.flip();
			// 6. Create a bytes array of the corresponding size according to the buffer size to get the value
			byte[] bytes = new byte[readBuffer.remaining()];
			// 7. Receiving Buffer Data
			readBuffer.get(bytes);
			// 8. Print the acquired data
			System.out.println("NIO Server : " + new String(bytes)); // You cannot use bytes.toString()
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(new NioSocketServer()).start();
	}
}
