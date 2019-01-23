package org.wubin.sample.netty.nio2netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.wubin.sample.netty.nio2netty.pool.NioSelectorRunnablePool;

/**
  *  启动函数
 * @author wubin
 *
 */
public class Start {

	public static void main(String[] args) {
		// 初始化线程，boss和woker，master/worker，master阻塞获取任务给worker添加任务，多个woker轮训获取任务执行
		NioSelectorRunnablePool nioSelectorRunnablePool = new NioSelectorRunnablePool(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		
		// 获取服务类
		ServerBootstrap bootstrap = new ServerBootstrap(nioSelectorRunnablePool);
		
		// 监听端口
		bootstrap.bind(new InetSocketAddress(10101));
		
		System.out.println("start");
	}
}
