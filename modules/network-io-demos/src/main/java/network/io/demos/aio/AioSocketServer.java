package network.io.demos.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AIO, Also known as NIO 2.0 is an asynchronous non-blocking communication mode.
 * AIO The concept of asynchronous channel is introduced. Asynchronous Server Socket Channel and Asynnous Socket Channel whose read and write method return value types are Future objects.
 */
public class AioSocketServer {
	private ExecutorService executorService;          // Thread pool
	private AsynchronousChannelGroup threadGroup;      // Channel group
	public AsynchronousServerSocketChannel asynServerSocketChannel;  // Server channel

	public void start(Integer port) {
		try {
			// 1. Create a cache pool
			executorService = Executors.newCachedThreadPool();
			// 2. Creating Channel Groups
			threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
			// 3. Create server channels
			asynServerSocketChannel = AsynchronousServerSocketChannel.open(threadGroup);
			// 4. Binding
			asynServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("server start , port : " + port);
			// 5. Waiting for Client Request
			asynServerSocketChannel.accept(this, new AioServerHandler());
			// The server is blocked all the time, and the real environment is running under tomcat, so this line of code is not needed.
			Thread.sleep(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AioSocketServer server = new AioSocketServer();
		server.start(8888);
	}
}
