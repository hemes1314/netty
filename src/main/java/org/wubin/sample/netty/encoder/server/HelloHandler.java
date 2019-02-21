package org.wubin.sample.netty.encoder.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.wubin.sample.netty.common.constant.StateCode;
import org.wubin.sample.netty.common.model.Request;
import org.wubin.sample.netty.common.model.Response;
import org.wubin.sample.netty.encoder.module.fuben.request.FightRequest;
import org.wubin.sample.netty.encoder.module.fuben.response.FightResponse;

public class HelloHandler extends SimpleChannelHandler {

	/**
	 * 接收消息
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
	    Request message = (Request)e.getMessage();
	    
	    if(message.getModule() == 1) {
	        if(message.getCmd() == 1) {
	            FightRequest fightRequest = new FightRequest();
	            fightRequest.readFromBytes(message.getData());
	            System.out.println("fubenId:"+fightRequest.getFubenId() + "     " + "count:" + fightRequest.getCount());
	            
	            // 回写数据
	            FightResponse fightResponse = new FightResponse();
	            fightResponse.setGold(1000);
	            
	            Response response = new Response();
	            response.setModule((short)1);
	            response.setCmd((short)1);
	            response.setStateCode(StateCode.SUCCESS);
	            response.setData(fightResponse.getBytes());
	            ctx.getChannel().write(response);
	        }else if(message.getCmd() == 2){
	            
	        }
	    } else if(message.getModule() == 2) {
	        
	    }
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
	 * 服务端未开启时会走closed
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("netty-message:channelClosed");
		super.channelClosed(ctx, e);
	}

	
}
