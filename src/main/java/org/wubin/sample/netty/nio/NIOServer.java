package org.wubin.sample.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * channel 大门，{port}
 * selector<key,channel> 一组服务员，监听大门：accept来人了，read需求
 * 
 */
public class NIOServer {

	// 通道管理器
	private Selector selector;
	
	/**
	 * 获得一个ServerScoket通道，并对该通道做一些初始化的工作
	 * @param port
	 * 				绑定端口号
	 * @throws IOException
	 */
	public void initServer(int port) throws IOException {
		// 获得一个ServerSocket通道
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置通道为非阻塞
		serverChannel.configureBlocking(false);
		// 将该通道对应的ServerSocket绑定到port端口
		serverChannel.socket().bind(new InetSocketAddress(port));
		// 获得一个通道管理器
		this.selector = Selector.open();
		// 将通道管理器和该通道绑定，并未该通道注册SelectionKey.OP_ACCEPT事件，注册事件后，
		// 当该事件到达时，slector.select()会返回，如果事件没到达selector.select()会一直阻塞，
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	/**
	 * 采用轮训的方式监听Selector上是否有需要处理的事件，如果有，则进行处理
	 * @throws IOException
	 */
	public void listen() throws IOException {
		System.out.println("服务端启动成功！");
		// 轮训访问selector
		while(true) {
			// 当注册的事件到达时，方法返回，否则，该方法会一直阻塞
			selector.select();//基于C，可以非阻塞：selector.select(timeout),一秒内没发生请求就返回0
			// selector.wakeup();//唤醒
			// 获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator<?> ite = this.selector.selectedKeys().iterator();
			while(ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// 删除已选的key，以防重复处理
				ite.remove();
				handler(key);
			}
		}
	}
	
	/**
	 * 处理请求
	 * @param key
	 * @throws IOException 
	 */
	public void handler(SelectionKey key) throws IOException {
		System.out.println("isWritable:"+key.isWritable());//缓冲区是否空闲,一般来说是true
		// 客户端请求连接事件
		if(key.isAcceptable()) {
			handlerAccept(key);
			// 获得了可读的事件
		} else if(key.isReadable()) {
			handlerRead(key);
		}
	}
	
	/**
	 * 处理连接请求
	 * @param key
	 * @throws IOException
	 */
	public void handlerAccept(SelectionKey key) throws IOException {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		// 获得和客户端连接的通道
		SocketChannel channel = server.accept();
		// 设置非阻塞
		channel.configureBlocking(false);
		
		// 在这里可以给客户端发送信息哦
		System.out.println("新的客户端连接");
		// 在和客户端连接成功后，为了可以接收到客户端的信息，需要给通道设置读的权限
		channel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}
	
	/**
	 * 处理读的事件
	 * @param key
	 * @throws IOException
	 */
	public void handlerRead(SelectionKey key) throws IOException {
		// 服务器可读取消息：得到事件发生的Socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024); 
		int read = channel.read(buffer);
		if(read > 0) {
			byte[] data = buffer.array();
			String msg = new String(data).trim();
			System.out.println("服务端收到信息：" + msg);
			
			// 回写
			ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
			channel.write(outBuffer);
		} else {
			System.out.println("客户端关闭");
			key.cancel();
		}
	}
	
	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer();
		server.initServer(8000);
		server.listen();
	}
}
