package org.wubin.sample.netty.nio2netty.pool;

import java.nio.channels.SocketChannel;

public interface Worker {

	public void registerNewChannelTask(final SocketChannel channel);
}
