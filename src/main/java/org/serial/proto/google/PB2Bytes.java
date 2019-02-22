package org.serial.proto.google;

import java.util.Arrays;

import com.google.protobuf.InvalidProtocolBufferException;
import org.serial.proto.google.PlayerModule.PBlayer;
import org.serial.proto.google.PlayerModule.PBlayer.Builder;

/**
 * protobuf学习
 * @author wubin
 */
public class PB2Bytes {

	public static void main(String[] args) throws InvalidProtocolBufferException {
		byte[] byteArray = toBytes();
		toPlayer(byteArray);
	}
	
	/**
	 * 序列化
	 */
	public static byte[] toBytes() {
		// 获取一个PBPlayer的构造器
		Builder builder = PlayerModule.PBlayer.newBuilder();
		// 设置数据
		builder.setPlayerId(101).setAge(20).setName("peter").addSkills(1001);
		// 构造处对象
		PBlayer player = builder.build();
		// 序列化成字节数组
		byte[] byteArray = player.toByteArray();
		
		System.out.println(Arrays.toString(byteArray));
		
		return byteArray;
	}
	
	/**
	 *   反序列化
	 * @param byteArray
	 * @throws InvalidProtocolBufferException 
	 */
	public static void toPlayer(byte[] bs) throws InvalidProtocolBufferException {
		
		PBlayer player = PlayerModule.PBlayer.parseFrom(bs);
		System.out.println("playerId:"+player.getPlayerId());
		System.out.println("Age:"+player.getAge());
		System.out.println("Name:"+player.getName());
		System.out.println("player:"+(Arrays.toString(player.getSkillsList().toArray())));
	}
}
