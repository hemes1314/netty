package org.wubin.sample.netty.netty5_demo.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 多连接客户端
 * @author wubin
 */
public class MultClient {

	/**
	 * 服务类
	 */
	private Bootstrap bootstrap = new Bootstrap();
	
	/**
	 * 会话
	 */
	private List<Channel> channels = new ArrayList<Channel>();
	
	/**
	 * worker线程数组
	 */
	private final AtomicInteger channelIndex = new AtomicInteger();
	
	/**
	 * 初始化
	 */
	public void init(int count) {
		
		// worker线程池
		EventLoopGroup worker = new NioEventLoopGroup();
		
		// 设置线程池
		bootstrap.group(worker);
		
		// 设置socket工厂
		bootstrap.channel(NioSocketChannel.class);
		
		// 设置管道
		bootstrap.handler(new ChannelInitializer<Channel>() {
			
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new StringEncoder());
				ch.pipeline().addLast(new HiHandler());
			}
		});
		
		// 连接服务端
		for(int i = 1; i <= count; i++) {
			ChannelFuture future = bootstrap.connect("10.50.146.70", 10101);
			channels.add(future.channel());
		}
	}
	
	/**
	 * 获取会话
	 */
	public Channel nextChannel() {
		
		return getFirstActiveChannel(0);
	}
	
	/**
	 * 获取第一个可用会话
	 * @return
	 */
	private Channel getFirstActiveChannel(int count) {
		Channel channel = channels.get(Math.abs(channelIndex.getAndIncrement() % channels.size()));
		if(!channel.isActive()) {
			// 重连
			reconnect(channel);
			if(count > channels.size()) {
				throw new RuntimeException("no can use channel");
			}
			getFirstActiveChannel(count+1);
		}
		return channel;
	}
	
	/**
	 * 重连
	 */
	private void reconnect(Channel channel) {
		// 用锁换成单任务队列执行，锁会阻塞请求
		synchronized (channel) {
			if(channels.indexOf(channel) == -1) {
				return;
			}
			
			System.out.println("自动重连服务器，server:10.50.146.70");
			Channel newChannel = bootstrap.connect("10.50.146.70", 10101).channel();
			channels.set(channels.indexOf(channel), newChannel);
		}
	}
}
