 package org.wubin.sample.netty.netty3packet.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author wubin
 * @date 2019/02/27
 */
public class MyDecoder extends FrameDecoder {

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        
        if(buffer.readableBytes() > 4) {
            // 标记
            buffer.markReaderIndex();
            // 长度
            int dataLen = buffer.readInt();
            
            if(buffer.readableBytes() < dataLen) {
                // 如果剩余数据不完整则回到标记的位置markReaderIndex
                buffer.resetReaderIndex();
                // 缓存当前剩余的buffer数据，等待剩下数据包
                return null;
            }
            
            // 读数据,剩余数据>datalength则继续递归读取
            byte[] bytes = new byte[dataLen];
            buffer.readBytes(bytes);
            // 往下传递对象
            return new String(bytes);
        }
        // 缓存当前剩余的buffer数据，等待剩下数据包
        return null;
    }

    
}
