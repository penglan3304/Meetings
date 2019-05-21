package com.meeting.service;

import java.util.List;

import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingRoom;

public interface MeetingRoomService {
	public Object meetingQuery(int start,String name,String state,int limit);
	public List<MeetingRoom> meetingroomlist();
	public int add(MeetingRoom meetingroom,String username);
	public List<Meeting> querymeeting(int id);
	public MeetingRoom meetingroom(int meetingroomid);
	public List<MeetingRoom> meetingroom();
	public List<MeetingRoom> selectmeeting(String starttime,String endtime);
	public int del(int id);
}
