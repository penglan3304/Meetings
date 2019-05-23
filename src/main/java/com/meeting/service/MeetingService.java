package com.meeting.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.google.zxing.WriterException;
import com.meeting.pojo.AddMeeting;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingParam;
import com.meeting.pojo.User;
import com.meeting.utils.PageResult;

public interface MeetingService {
	//public Object meetingQuery(int start,int limit);
	
	
	public Object meetingQuery(int start,String starttime,String endtime, int limit);
	public int del(List<Integer> id,int meetingroomid);
	public List<Meeting> detail(int id);
	public int add(AddMeeting paramlist,String username);
	public PageResult waitcheck(User user,String starttime,String endtime);
	public PageResult waitchecked(User user,String starttime,String endtime);
	public Map<String,Object> nosign(User user);
	public Map<String,Object> signed(User user,int flag);
	public int pass(int id);
	public int nopass(int id,String reason);
	public int notifys(int id);
	public PageResult notifydetail(User user);
	public int notifydel(String ids);
	public PageResult checked(User user,String starttime,String endtime);
	public PageResult checkednopass(User user);
	public PageResult noattend(User user,String starttime,String endtime);
	public PageResult absent(User user,String starttime,String endtime);
	public int updatemeetings(Meeting paramlist);
	public void QRCodeInit(String content,HttpServletResponse response)throws WriterException, IOException;
	public int changeState(int id,User user);
	public Map<String,Object> published(User user,String starttime,String endtime);
	public List<Meeting> information();
	public Object attended(User user,String starttime,String endtime);
	public Object listen(User user);
	public int meetingstate(int id,int fg);
	public int absent(int id,String reason,User user);
	public Meeting meeting(int id);
	public Object hasattended(User user,String starttime,String endtime);
	public MeetingParam meetingparam(int meetingid);
	public PageResult comment(int start, int limit );

}
