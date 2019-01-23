package org.wubin.sample.netty.demo.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HiHandler extends SimpleChannelHandler {

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
		System.out.println("netty-message:messageReceived");
//		ChannelBuffer message = (ChannelBuffer)e.getMessage();
//		String s = new String(message.array());
		String s = (String)e.getMessage();
		System.out.println(s);
		
		// 回写数据
//	 	ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer("hi".getBytes());
//		ctx.getChannel().write(copiedBuffer);
//		ctx.getChannel().write("hi");
		
		super.messageReceived(ctx, e);
	}

	/**
	 * 捕获异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("netty-message:exceptionCaught");
		super.exceptionCaught(ctx, e);
	}

	/**
	 * 新连接
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("netty-message:channelConnected");
		// 非法请求过滤
		super.channelConnected(ctx, e);
	}

	/**
	 * 必须是连接已经建立，关闭通道时候才会触发
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("netty-message:channelDisconnected");
		// 游戏下线清缓存
		super.channelDisconnected(ctx, e);
	}

	/**
	 * channel关闭时的时候触发
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("netty-message:channelClosed");
		super.channelClosed(ctx, e);
	}

	
}
