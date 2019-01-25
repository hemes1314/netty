package org.wubin.sample.netty.netty5_demo.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HiHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("客户端收到消息："+msg);
	}
}
