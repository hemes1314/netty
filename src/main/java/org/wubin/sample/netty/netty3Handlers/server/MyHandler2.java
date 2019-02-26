 package org.wubin.sample.netty.netty3Handlers.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author wubin
 * @date 2019/02/26
 */
public class MyHandler2 extends SimpleChannelHandler {

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String message = (String)e.getMessage();
        System.out.println("handler2:" + message);
    }
}
