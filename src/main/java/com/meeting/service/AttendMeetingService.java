package com.meeting.service;

import java.util.List;

import com.meeting.pojo.AttendMetting;
import com.meeting.pojo.User;

public interface AttendMeetingService {
	public int update(int meetingid, int personid);
	public Object perdetail(int id,String attendperson);
	public AttendMetting detail(int meetingid,int personid);
	public int add(int meetingid, int personid,User user);
	public Object attendmeetinglist(int id,int start,int limit);
	public int count(int meetingid);
	public List<AttendMetting> attendlist(int meetingid);
}
