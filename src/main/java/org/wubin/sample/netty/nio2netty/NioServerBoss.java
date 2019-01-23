package org.wubin.sample.netty.nio2netty;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

import org.wubin.sample.netty.nio2netty.pool.Boss;
import org.wubin.sample.netty.nio2netty.pool.NioSelectorRunnablePool;
import org.wubin.sample.netty.nio2netty.pool.Worker;

/**
 * boss实现类
 * @author wubin
 */
public class NioServerBoss extends AbstractNioSelector implements Boss {

	public NioServerBoss(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool) {
		super(executor, threadName, selectorRunnablePool);
	}

	/**
	 * 迎宾
	 */
	@Override
	public void process(Selector selector) throws Exception {
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		if(selectedKeys.isEmpty()) {
			return;
		}
		for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext();) {
			SelectionKey key = i.next();
			i.remove();
			ServerSocketChannel server = (ServerSocketChannel)key.channel();
			// 新客户端
			SocketChannel channel = server.accept();
			// 设置为非阻塞
			channel.configureBlocking(false);
			// 获取一个worker，处理read任务
			Worker nextWorker = getSelectorRunnablePool().nextWorker();
			// 注册新客户端接入任务
			nextWorker.registerNewChannelTask(channel);
			
			System.out.println("客户端连接");
		}
	}
	
	public void registerAcceptChannelTask(final ServerSocketChannel serverChannel) {
		final Selector selector = this.selector;
		registerTask(new Runnable() {
			
			@Override
			public void run() {
				try {
					// 注册serverChannel到selector
					serverChannel.register(selector, SelectionKey.OP_ACCEPT);
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void select(Selector selector) throws Exception {
		selector.select();// boss是阻塞获取
	}
}
