package org.wubin.sample.netty.nio2netty;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

import org.wubin.sample.netty.nio2netty.pool.NioSelectorRunnablePool;
import org.wubin.sample.netty.nio2netty.pool.Worker;

/**
 * worker实现
 * @author Administrator
 *
 */
public class NioServerWorker extends AbstractNioSelector implements Worker {

	public NioServerWorker(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool) {
		super(executor, threadName, selectorRunnablePool);
	}
	
	@Override
	public void process(Selector selector) throws Exception {
		
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		if(selectedKeys.isEmpty()) {
			return;
		}
		
		for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext();) {
			SelectionKey key = i.next();
			
			// 移除，防止重复处理
			i.remove();
			
			// 获取可读取的客户端
			SocketChannel channel = (SocketChannel)key.channel();
			
			// 数据总长度
			int ret = 0;
			boolean failure = true;
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			// 读取数据
			try {
				ret = channel.read(buffer);
				failure = false;
			} catch (Exception e) {
				// 
			}
			
			// 判断是否连接已断开
			if(ret <= 0 || failure) {
				key.cancel();
				System.out.println("客户端断开连接");
			} else {
				System.out.println("收到数据：" + new String(buffer.array()));
				
				// 回写数据
				ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
				channel.write(outBuffer);// 将消息回送给客户端
			}
		}
	}

	/**
	 *   加入一个新的socket客户
	 * @param channel
	 */
	public void registerNewChannelTask(final SocketChannel channel) {
		final Selector selector = this.selector;
		registerTask(new Runnable() {

			@Override
			public void run() {
				
				try {
					// 将客户端注册到selector中
					channel.register(selector, SelectionKey.OP_READ);
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void select(Selector selector) throws Exception {
		selector.select(500);//500ms获取一次
	}
}
