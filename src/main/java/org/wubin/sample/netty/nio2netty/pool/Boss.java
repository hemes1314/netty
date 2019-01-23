package org.wubin.sample.netty.nio2netty.pool;

import java.nio.channels.ServerSocketChannel;

public interface Boss {

	public void registerAcceptChannelTask(final ServerSocketChannel serverChannel);
}
