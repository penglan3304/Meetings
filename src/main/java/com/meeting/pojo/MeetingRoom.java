package com.meeting.pojo;

import java.io.Serializable;

public class MeetingRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8448679276045018037L;
	private long id;
	private String name;
	private String state;
	private String createname;
	private String createtime;
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
