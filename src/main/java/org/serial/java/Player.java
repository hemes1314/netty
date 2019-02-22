package org.serial.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩家对象
 * @author wubin
 */
public class Player implements Serializable {

	private static final long serialVersionUID = -4395121872627348666L;

	public Player(long playerId, int age, String name) {
		super();
		this.playerId = playerId;
		this.age = age;
		this.name = name;
	}

	private long playerId;
	
	private int age;
	
	private String name;
	
	private List<Integer> skills = new ArrayList<>();

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
