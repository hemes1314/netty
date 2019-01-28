package org.wubin.sample.netty.netty5heart;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HelloHandler extends SimpleChannelInboundHandler<String> {
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(msg);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object e) throws Exception {
		if(e instanceof IdleStateEvent) {
			
			SimpleDateFormat format = new SimpleDateFormat("ss");
			IdleStateEvent event = (IdleStateEvent)e;
			System.out.println(format.format(new Date())+":"+event.state());
			
			if(((IdleStateEvent) e).state() == IdleState.ALL_IDLE) {
				System.out.println(ctx.channel().localAddress()+"踢玩家下线");
				// 关闭会话,踢玩家下线
				ChannelFuture write = ctx.writeAndFlush("time out, you will close.");
				write.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						ctx.channel().close();
					}
				});
			}
		} else {
			super.userEventTriggered(ctx, e);
		}
	}
	
}
