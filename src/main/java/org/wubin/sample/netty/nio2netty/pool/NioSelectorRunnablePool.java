package org.wubin.sample.netty.nio2netty.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.wubin.sample.netty.nio2netty.NioServerBoss;
import org.wubin.sample.netty.nio2netty.NioServerWorker;

public class NioSelectorRunnablePool {

	// 原子类，不会有并发问题
	private final AtomicInteger bossIndex = new AtomicInteger();
	private Boss[] bosses;

	private final AtomicInteger workerIndex = new AtomicInteger();
	private Worker [] workeres;
	
	public NioSelectorRunnablePool(Executor boss, Executor worker) {
		System.out.println(Thread.currentThread().getName()+":init boss/worker thread， boss：1，workers："+Runtime.getRuntime().availableProcessors() * 2);
		
		initBoss(boss, 1);
		// 核心线程数量*2
		//initWorker(worker, Runtime.getRuntime().availableProcessors() * 2);
		initWorker(worker, 2);
	}
	
	/**
	 * 初始化boss线程
	 * @param boss
	 * @param count
	 */
	private void initBoss(Executor boss, int count) {
		this.bosses = new NioServerBoss[count];
		for(int i = 0; i < bosses.length; i++) {
			bosses[i] = new NioServerBoss(boss, "boss thread "+(i+1), this);
		}
	}
	
	/**
	 * 初始化worker线程
	 */
	private void initWorker(Executor worker, int count) {
		this.workeres = new NioServerWorker[count];
		for(int i = 0; i < workeres.length; i++) {
			workeres[i] = new NioServerWorker(worker, "worker thread " + (i+1), this);
		}
	}
	
	/**
	 * 获取一个worker
	 * @return
	 */
	public Worker nextWorker() {
		return workeres[Math.abs(workerIndex.getAndIncrement() % workeres.length)];
	}
	
	/**
	 * 获取一个boss
	 * @return
	 */
	public Boss nextBoss() {
		// 原子递增求模
		return bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
	}
}
