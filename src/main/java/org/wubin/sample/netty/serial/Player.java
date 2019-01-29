package org.wubin.sample.netty.serial;

import java.util.ArrayList;
import java.util.List;

import org.wubin.sample.netty.serial.core.Serializer;

public class Player extends Serializer {

	private long playerId;
	
	private int age;
	
	private String name;
	
	private List<Integer> skills = new ArrayList<>();
	
	private Resource resource = new Resource();
	
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

	public List<Integer> getSkills() {
		return skills;
	}

	public void setSkills(List<Integer> skills) {
		this.skills = skills;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void read() {
		this.playerId = readLong();
		this.age = readInt();
		this.name = readString();
		this.skills = readList(Integer.class);
//		this.resource = read(Resource.class);
	}
	
	@Override
	protected void write() {
		writeLong(playerId);
		writeInt(age);
		writeString(name);
		writeList(skills);
//		writeObject(resource);
	}
	
}
