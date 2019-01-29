package org.wubin.sample.netty.serial;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * jdk-nio序列化
 * @author wubin
 */
public class Test2 {

	public static void main(String[] args) {
		int id = 101;
		int age = 21;
		
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putInt(id);
		buffer.putInt(age);
//		buffer.putLong(11);//无拓容功能
		System.out.println(Arrays.toString(buffer.array()));
		
		//===================================================
		byte[] array = buffer.array();
		ByteBuffer buffer2 = ByteBuffer.wrap(array);
		System.out.println("id:"+buffer2.getInt());
		System.out.println("age:"+buffer2.getInt());
	}
}
