package org.wubin.sample.netty.common.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.wubin.sample.netty.common.constant.ConstantValue;
import org.wubin.sample.netty.common.model.Response;

/**
 * 请求解码器 
 * 包头+模块号+命令号+状态码+长度+数据 
 * 包头4字节int+模块号2字节short+命令号2字节short+状态码4字节+数据长度4字节（描述数据部分字节长度）
 * @author wubin
 * @date 2019/02/20
 */
public class ResponseEncoder extends OneToOneEncoder {

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, java.lang.Object)
     */
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object rs) throws Exception {

        Response response = (Response)(rs);
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        // 包头
        buffer.writeInt(ConstantValue.FLAG);
        // module
        buffer.writeShort(response.getModule());
        // cmd
        buffer.writeShort(response.getCmd());
        // 状态码
        buffer.writeInt(response.getStateCode());
        // 长度
        buffer.writeInt(response.getDataLength());
        // data
        if(response.getData() != null) {
            buffer.writeBytes(response.getData());
        }
        return buffer;
    }
}
