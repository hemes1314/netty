package org.wubin.sample.netty.nio2netty;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.wubin.sample.netty.nio2netty.pool.NioSelectorRunnablePool;

public abstract class AbstractNioSelector implements Runnable {

	/**
	 * 线程池
	 */
	private final Executor executor;
	
	/**
	 * 选择器
	 */
	protected Selector selector;
	 
	/**
	 * 选择器wakeUp状态标记
	 */
	protected final AtomicBoolean wakeUp = new AtomicBoolean();
	
	/**
	 * 任务队列
	 */
	private final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * 线程名称
	 */
	private String threadName;
	
	/**
	 * 线程管理对象
	 */
	protected NioSelectorRunnablePool selectorRunnablePool;
	
	public AbstractNioSelector(Executor executor, String threadName, NioSelectorRunnablePool selectorRunnablePool) {
		this.executor = executor;
		this.threadName = threadName;
		this.selectorRunnablePool = selectorRunnablePool;
		openSelector();
	}
	
	/**
	 * 获取selector并启动线程，一个线程一个服务生
	 */
	private void openSelector() {
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			throw new RuntimeException("Failed to create a selector.");
		}
		executor.execute(this);
	}

	@Override
	public void run() {
		Thread.currentThread().setName(this.threadName);
		try {
			while(true) {
				// 休眠
				wakeUp.set(false);
				
				// 服务生，boss，worker
				select(selector);
				
				// 取任务并执行
				processTaskQueue();
				
				// 服务生业务处理，boss：监听端口，worker：微客户端服务
				process(selector);
			}
		} catch(Exception e1) {
			//
		}
	}
	
	/**
	 * 注册一个任务并激活selector
	 */
	protected final void registerTask(Runnable task) {
		taskQueue.add(task);
		
		Selector selector = this.selector;
		
		if(selector != null) {
			// 加入客户端后，需要重新激活selector状态
			if(wakeUp.compareAndSet(false, true)) {
				selector.wakeup();
			}
		} else {
			taskQueue.remove(task);
		}
	}
	
	/**
	  *  执行队列里的任务
	 */
	private void processTaskQueue() {
		for(;;) {
			final Runnable task = taskQueue.poll();
			if(task == null) {
				break;
			}
			task.run();
		}
	}
	
	/**
	 * 获取线程管理对象
	 * @return
	 */
	public NioSelectorRunnablePool getSelectorRunnablePool() {
		return selectorRunnablePool;
	}
	
	public abstract void select(Selector selector) throws Exception;
	
	public abstract void process(Selector selector) throws Exception;
	
}
