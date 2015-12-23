package com.dreamcatcher.member.model;

public class MemberDto {
	private String id;
	private String password;
	private String name;
	private int m_level;
	private int m_state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getM_level() {
		return m_level;
	}

	public void setM_level(int m_level) {
		this.m_level = m_level;
	}

	public int getM_state() {
		return m_state;
	}

	public void setM_state(int m_state) {
		this.m_state = m_state;
	}

}
