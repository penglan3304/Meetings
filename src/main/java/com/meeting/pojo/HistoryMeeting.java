package com.meeting.pojo;

public class HistoryMeeting {
	private long id;
    private String name;
    private String starttime;
    private String endtime;
    private String createtime;
    private long meetingroomid;
    private long meetingid;
    private String state;
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
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public long getMeetingroomid() {
		return meetingroomid;
	}
	public void setMeetingroomid(long meetingroomid) {
		this.meetingroomid = meetingroomid;
	}
	public long getMeetingid() {
		return meetingid;
	}
	public void setMeetingid(long meetingid) {
		this.meetingid = meetingid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
