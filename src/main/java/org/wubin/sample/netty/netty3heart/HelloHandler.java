package org.wubin.sample.netty.netty3heart;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import io.netty.handler.timeout.IdleStateHandler;

// IdleStateAwareChannelHandler
public class HelloHandler extends SimpleChannelHandler implements ChannelHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println(e.getMessage());
	}
	
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		if(e instanceof IdleStateEvent) {
			
			SimpleDateFormat format = new SimpleDateFormat("ss");
			IdleStateEvent event = (IdleStateEvent)e;
			System.out.println(format.format(new Date())+":"+event.getState());
			
			if(((IdleStateEvent) e).getState() == IdleState.ALL_IDLE) {
				System.out.println(ctx.getChannel().getLocalAddress()+"踢玩家下线");
				// 关闭会话,踢玩家下线
				ChannelFuture write = ctx.getChannel().write("time out, you will close.");
				write.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						ctx.getChannel().close();
					}
				});
			}
		} else {
			super.handleUpstream(ctx, e);
		}
	}
	
//	@Override
//	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("ss");
//		System.out.println(e.getState()+"      "+dateFormat.format(new Date()));
//	}
	
	
}
