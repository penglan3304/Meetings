package com.meeting.pojo;

public class Comment {
	private long id;
	private long meetingid;
	private long userid;
	private String commenttime;
	private String content;
	private String username;
	private int replayid;
	private String replayname;
	public String getReplayname() {
		return replayname;
	}
	public void setReplayname(String replayname) {
		this.replayname = replayname;
	}
	public int getReplayid() {
		return replayid;
	}
	public void setReplayid(int replayid) {
		this.replayid = replayid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getCommenttime() {
		return commenttime;
	}
	public void setCommenttime(String commenttime) {
		this.commenttime = commenttime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
