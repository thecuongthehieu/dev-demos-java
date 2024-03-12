package network.io.demos.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("channelRead threadId=" + Thread.currentThread().getId());
		RequestData requestData = (RequestData) msg;
		ResponseData responseData = new ResponseData();
		responseData.setIntValue(requestData.getIntValue() * 2);
		ChannelFuture future = ctx.writeAndFlush(responseData);
		future.addListener(ChannelFutureListener.CLOSE);
		System.out.println(requestData);
	}
}
