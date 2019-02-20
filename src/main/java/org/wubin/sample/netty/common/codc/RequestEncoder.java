 package org.wubin.sample.netty.common.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.wubin.sample.netty.common.constant.ConstantValue;
import org.wubin.sample.netty.common.model.Request;

/**
  * 请求编码对象
  * 包头+模块号+命令号+长度+数据
  * 包头4字节int+模块号2字节short+命令号2字节short+数据长度4字节（描述数据部分字节长度）
 * @author wubin
 * @date 2019/02/20
 */
public class RequestEncoder extends OneToOneEncoder{

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, java.lang.Object)
     */
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object rs) throws Exception {

        Request request = (Request)(rs);
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        // 包头
        buffer.writeInt(ConstantValue.FLAG);
        // module
        buffer.writeShort(request.getModule());
        // cmd
        buffer.writeShort(request.getCmd());
        // 长度
        buffer.writeInt(request.getDataLength());
        // data
        if(request.getData() != null) {
            buffer.writeBytes(request.getData());
        }
        return buffer;
    }
}
