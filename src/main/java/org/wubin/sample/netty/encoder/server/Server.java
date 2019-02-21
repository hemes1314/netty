package org.wubin.sample.netty.encoder.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.wubin.sample.netty.common.codc.RequestDecoder;
import org.wubin.sample.netty.common.codc.ResponseEncoder;

public class Server {

	public static void main(String[] args) {
		// 服务类
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		
		// 设置niosocket工厂
		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));
		
		// 设置管道的工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new RequestDecoder());
				pipeline.addLast("encoder", new ResponseEncoder());
				pipeline.addLast("helloHandler", new HelloHandler());
				return pipeline;
			}
		});
		
		// 设置参数，TCP参数，
		bootstrap.setOption("backlog", 2048);//serverSocketChannel的设置，连接缓冲池的大小
		bootstrap.setOption("tcpNoDelay", true);// socketChannel的设置，维持连接活跃，清除死链接，长时间没有读写就关闭连接，默认两小时
		bootstrap.setOption("keepAlive", true);// socketchannel的设置，关闭延迟发送	
		
		bootstrap.bind(new InetSocketAddress(10101));
		System.out.println("start!!!");
	}
}
