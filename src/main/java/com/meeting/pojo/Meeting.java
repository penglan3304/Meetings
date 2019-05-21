package com.meeting.pojo;

import java.io.Serializable;
import java.util.Date;

public class Meeting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8641896974977693738L;
	
    private long id;
    private String name;
    private String starttime;
    private String endtime;
    private String createtime;
    private String modifytime;
    private String createname;
    private long meetingroomid;
    private String state;
    private String isanother;
    private String meetingroom;
    private long departs;
    private String departname;
    private long checkpersonid;
    public long getCheckpersonid() {
		return checkpersonid;
	}
	public void setCheckpersonid(long checkpersonid) {
		this.checkpersonid = checkpersonid;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	private String checkperson;
    private String reason;
   
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getDeparts() {
		return departs;
	}
	public void setDeparts(long departs) {
		this.departs = departs;
	}
	public String getCheckperson() {
		return checkperson;
	}
	public void setCheckperson(String checkperson) {
		this.checkperson = checkperson;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getMeetingroom() {
		return meetingroom;
	}
	public void setMeetingroom(String meetingroom) {
		this.meetingroom = meetingroom;
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
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
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
	
	public long getMeetingroomid() {
		return meetingroomid;
	}
	public void setMeetingroomid(long meetingroomid) {
		this.meetingroomid = meetingroomid;
	}
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsanother() {
		return isanother;
	}
	public void setIsanother(String isanother) {
		this.isanother = isanother;
	}
}
