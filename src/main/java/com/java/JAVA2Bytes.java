package com.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class JAVA2Bytes {

	private static byte[] toBytes() throws IOException {
		
		Player player = new Player(101, 20, "peter");
		player.getList().add(101);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		
		// 写入对象
		objectOutputStream.writeObject(player);
		
		// 获取字节数组
		byte[] byteArray = byteArrayOutputStream.toByteArray(); 
		System.out.println(Arrays.toString(byteArray));
		return byteArray;
	}
	
	/**
	 *   反序列化
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static void toPlayer(byte[] bs) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bs));
		Player player = (Player) inputStream.readObject();
		
		// print
		System.out.println("playerId:"+player.getPlayerId());
		System.out.println("Age:"+player.getAge());
		System.out.println("Name"+player.getName());
		System.out.println("player:"+(Arrays.toString(player.getList().toArray())));
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		byte[] byteArray = toBytes();
		toPlayer(byteArray);
	}
}
