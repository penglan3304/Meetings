package com.meeting.pojo;

import java.io.Serializable;

public class MeetingParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7069573157355319634L;
    private long id;
    private long meetingid;
    private long departs;
    private String departnames;
    private long userid;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDeparts() {
		return departs;
	}
	public void setDeparts(long departs) {
		this.departs = departs;
	}
	public long getMeetingid() {
		return meetingid;
	}
	public void setMeetingid(long meetingid) {
		this.meetingid = meetingid;
	}
	public String getDepartnames() {
		return departnames;
	}
	public void setDepartnames(String departnames) {
		this.departnames = departnames;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
}
