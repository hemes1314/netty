package org.wubin.sample.netty.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) throws IOException {
		int id = 101;
		int age = 21;
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();//自动拓容但需要自己实现不同数据类型的字节转换
		arrayOutputStream.write(int2byte(id));
		arrayOutputStream.write(int2byte(age));
		
		byte[] byteArray = arrayOutputStream.toByteArray();
		System.out.println(Arrays.toString(byteArray));
		
		//====================================================
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
		byte[] idBytes = new byte[4];
		arrayInputStream.read(idBytes);
		System.out.println("id:"+bytes2int(idBytes));
		
		byte[] ageBytes = new byte[4];
		arrayInputStream.read(ageBytes);
		System.out.println("age:"+bytes2int(ageBytes));
	}
	
	/**
	 * 大端字节序列（先写高位，再写低位） --小端序列（先写低位，再写高位）
	 * @param i
	 * @return
	 */
	public static byte[] int2byte(int i) {
		byte[] bytes = new byte[4];
		// 先写高位，再写低位
		bytes[0] = (byte)(i >> 3*8);
		bytes[1] = (byte)(i >> 2*8);
		bytes[2] = (byte)(i >> 1*8);
		bytes[3] = (byte)(i >> 0*8);
		return bytes;
	}
	
	/**
	 * 大端
	 * @param bytes
	 * @return
	 */
	public static int bytes2int(byte[] bytes) {
		return (bytes[0] << 3*8) |
				(bytes[1] << 2*8) |
				(bytes[2] << 1*8) |
				(bytes[3] << 0*8);
	}
}
