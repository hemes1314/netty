package org.wubin.sample.netty.common.codc;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.wubin.sample.netty.common.constant.ConstantValue;
import org.wubin.sample.netty.common.model.Response;

/**
 * response解码器 
 * 包头+模块号+命令号+状态码+长度+数据 
 * 包头4字节int+模块号2字节short+命令号2字节short+状态码长度4字节+数据长度4字节（描述数据部分字节长度）
 * @author wubin
 * @date 2019/02/20
 */
public class ResponseDecoder extends FrameDecoder {

    /**
     * 数据包基本长度
     */
    public static int BASE_LENGTH = 4 + 2 + 2 + 4 + 4;

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {

        // 可读长度必须大于基本长度
        if(buffer.readableBytes() >= BASE_LENGTH) {
            
            // 记录包头开始的index
            // 读指针（>=0），写指针(>=读指针)
            int beginReaderIndex = buffer.readerIndex();
            
            while(true) {
                // 读取到是包头就玩下走
                if(buffer.readInt() == ConstantValue.FLAG) {
                    break;
                }
            }
            // 模块号
            short module = buffer.readShort();
            // 命令号
            short cmd = buffer.readShort();
            // 状态码
            int stateCode = buffer.readInt();
            // 数据长度
            int length = buffer.readInt();
            // 判断请求数据包是否到齐
            if(buffer.readableBytes() < length) {
                // 还原读指针，不然下次就不是从正确位置开始
                buffer.readerIndex(beginReaderIndex);
                return null;
            } 
            // 读取data数据
            byte[] data = new byte[length];
            buffer.readBytes(data);
            
            Response response = new Response();
            response.setModule(module);
            response.setCmd(cmd);
            response.setStateCode(stateCode);
            response.setData(data);
            
            // 继续往下传递 sendUpStreamEvent
            return response;
        }
        // 数据包不完整，需要等待后边的包来
        return null;
    }
}
