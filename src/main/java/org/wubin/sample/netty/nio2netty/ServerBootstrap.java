package org.wubin.sample.netty.nio2netty;

import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

import org.wubin.sample.netty.nio2netty.pool.Boss;
import org.wubin.sample.netty.nio2netty.pool.NioSelectorRunnablePool;

/**
 * 服务类
 * @author wubin
 */
public class ServerBootstrap {

	private NioSelectorRunnablePool selectorRunnablePool;
	
	public ServerBootstrap(NioSelectorRunnablePool selectorRunnablePool) {
		this.selectorRunnablePool = selectorRunnablePool;
	}
	
	public void bind(final SocketAddress localAddress) {
		try {
			// 获得一个ServerSocket通道
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			// 设置通道为非阻塞
			serverChannel.configureBlocking(false);
			// 将该通道对应的ServerSocket绑定到port端口
			serverChannel.socket().bind(localAddress);
			
			// 获取一个boss线程
			Boss boss = selectorRunnablePool.nextBoss();
			boss.registerAcceptChannelTask(serverChannel);
		} catch (Exception e) {
		}
	}
}
