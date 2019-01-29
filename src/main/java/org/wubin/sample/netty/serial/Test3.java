package org.wubin.sample.netty.serial;

import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * netty序列化
 * @author wubin
 */
public class Test3 {

	public static void main(String[] args) {
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();//自动扩容并且提供各数据类型方法
		buffer.writeInt(101);
		buffer.writeDouble(80.1);
		// 无writeString方法，反序列化时无字节数组长度，所以序列化时写入一个字节长度及字节数组short+byte[]
		
		byte[] bytes = new byte[buffer.writerIndex()];
		buffer.readBytes(bytes);
		
		System.out.println(Arrays.toString(bytes));
		
		//========================================
		ChannelBuffer wrappedBuffer = ChannelBuffers.wrappedBuffer(bytes);
		System.out.println(wrappedBuffer.readInt());
		System.out.println(wrappedBuffer.readDouble());
	}
}
