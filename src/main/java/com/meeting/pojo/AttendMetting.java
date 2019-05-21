package com.meeting.pojo;

public class AttendMetting {
	private long id;
	private long meetingid;
	private String attendperson;
	private String signtime;
	private String state;
	private String absencereason;
	private long personid;
	private String departid;
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public long getPersonid() {
		return personid;
	}
	public void setPersonid(long personid) {
		this.personid = personid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMeetingid() {
		return meetingid;
	}
	public void setMeetingid(long meetingid) {
		this.meetingid = meetingid;
	}
	public String getAttendperson() {
		return attendperson;
	}
	public void setAttendperson(String attendperson) {
		this.attendperson = attendperson;
	}
	public String getSigntime() {
		return signtime;
	}
	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAbsencereason() {
		return absencereason;
	}
	public void setAbsencereason(String absencereason) {
		this.absencereason = absencereason;
	}
}
