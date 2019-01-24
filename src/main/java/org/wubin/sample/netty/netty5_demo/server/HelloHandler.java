package org.wubin.sample.netty.netty5_demo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HelloHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		
		System.out.println(msg);
		// 向客户端发送信息 两个方法相同
		ctx.channel().writeAndFlush("hi");
		ctx.writeAndFlush("hi");
	}
}
