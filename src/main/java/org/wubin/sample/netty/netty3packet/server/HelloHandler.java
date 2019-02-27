package org.wubin.sample.netty.netty3packet.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloHandler extends SimpleChannelHandler {

    private int count = 1;// 整个worker操作是单线程操，无并发问题 
    
	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
//		ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
//		byte[] array = buffer.array();
//		System.out.println(new String(array));
	    
	    System.out.println(e.getMessage()+"   "+count);
	    count++;
	}
}
