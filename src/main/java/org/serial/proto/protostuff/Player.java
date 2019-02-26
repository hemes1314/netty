package org.serial.proto.protostuff;

import java.util.List;

import io.protostuff.Tag;

/**
 * 玩家对象
 * @author wubin
 */
public class Player {

    public Player() {
    }
    
	public Player(long playerId, int age, String name) {
        super();
        this.playerId = playerId;
        this.age = age;
        this.name = name;
    }

    @Tag(1)
	private long playerId;
	
	@Tag(2)
	private int age;
	
	@Tag(3)
	private String name;
	
	@Tag(4)
	private List<Integer> skills;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Player [playerId=" + playerId + ", age=" + age + ", name=" + name + ", skills=" + skills + "]";
    }
    
}
