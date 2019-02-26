 package org.wubin.sample.netty.netty3Handlers.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.UpstreamMessageEvent;

/**
 * @author wubin
 * @date 2019/02/26
 */
public class MyHandler1 extends SimpleChannelHandler {

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
        byte[] array = buffer.array();
        String message = new String(array);
        System.out.println("handler1:" + message);
        
        // 传递
        ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(), message, e.getRemoteAddress()));
    }
}
