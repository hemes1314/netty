package org.wubin.sample.netty.serial;

import java.util.Arrays;

/**
 * netty封装自定义序列化
 * @author wubin
 */
public class Test4 {

	public static void main(String[] args) {
		
		Player player = new Player();
		player.setPlayerId(101);
		player.setAge(20);
		player.setName("peter");
		player.getSkills().add(1001);
//		player.getResource().setGold(999);
		
		byte[] bytes = player.getBytes();
		System.out.println(Arrays.toString(bytes));
		
		// =================================================
		
		Player player2 = new Player();
		player2.readFromBytes(bytes);
		System.out.println(player2.getPlayerId()+" "+player2.getAge()
			+" "+player2.getName()
			+" "+Arrays.toString(player2.getSkills().toArray())
//			+" "+player.getResource().getGold()
			);
	}
}
