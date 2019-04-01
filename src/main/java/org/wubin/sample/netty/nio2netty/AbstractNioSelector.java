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
				// 原子标识
				wakeUp.set(false);
				
				// 服务生，boss，worker
				System.out.println(Thread.currentThread().getName()+":selector select start");
				select(selector);
				System.out.println(Thread.currentThread().getName()+":selector select end");
				
				
				// 取任务并执行
				System.out.println(Thread.currentThread().getName()+":processTaskQueue start");
				processTaskQueue();
				System.out.println(Thread.currentThread().getName()+":processTaskQueue end");
				
				// 服务生业务处理，boss：监听端口，worker：为客户端服务
				System.out.println(Thread.currentThread().getName()+":process start");
				process(selector);
				System.out.println(Thread.currentThread().getName()+":process start");
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
			System.out.println(Thread.currentThread().getName()+":selector wakeUp");
			// 加入客户端后，需要重新激活selector状态
			if(wakeUp.compareAndSet(false, true)) {
				selector.wakeup();//用于唤醒阻塞在select方法上的线程
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
			System.out.println(Thread.currentThread().getName()+":get task");
			if(task == null) {
				System.out.println(Thread.currentThread().getName()+":no task 500ms continue.");
				break;
			}
			System.out.println(Thread.currentThread().getName()+":get task(boss accpet,worker read) and run, continue.");
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
