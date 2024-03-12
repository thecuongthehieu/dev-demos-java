package network.io.demos.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class AioServerHandler implements CompletionHandler<AsynchronousSocketChannel, AioSocketServer> {
	private final Integer BUFFER_SIZE = 1024;

	@Override
	public void completed(AsynchronousSocketChannel asynSocketChannel, AioSocketServer attachment) {
		// Ensure that multiple clients can block
		attachment.asynServerSocketChannel.accept(attachment, this);
		read(asynSocketChannel);
	}

	//Read data
	private void read(final AsynchronousSocketChannel asynSocketChannel) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		asynSocketChannel.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				//After reading, reset the identifier bit
				attachment.flip();
				//Get the number of bytes read
				System.out.println("Server -> " + "The length of data received from the client is:" + resultSize);
				//Get the read data
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + "The data information received from the client is:" + resultData);
				String response = "Server response, Received data from client: " + resultData;
				write(asynSocketChannel, response);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
			}
		});
	}

	// Write data
	private void write(AsynchronousSocketChannel asynSocketChannel, String response) {
		try {
			// Write the data into the buffer
			ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
			buf.put(response.getBytes());
			buf.flip();
			// Write from buffer to channel
			asynSocketChannel.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable exc, AioSocketServer attachment) {
		exc.printStackTrace();
	}
}
