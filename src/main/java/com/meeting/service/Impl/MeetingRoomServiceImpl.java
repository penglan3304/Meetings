package com.meeting.service.Impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.HistoryMeetingMapper;
import com.meeting.mapper.MeetingMapper;
import com.meeting.mapper.MeetingRoomMapper;
import com.meeting.pojo.HistoryMeeting;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingRoom;
import com.meeting.service.MeetingRoomService;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService{

	@Autowired
	private MeetingMapper meetingmapper;
	
	@Autowired
	private HistoryMeetingMapper meetinghistorymapper;
	
	@Autowired
	private MeetingRoomMapper meetingroommapper;
	@Override
	public Object meetingQuery(int start,String name,String state, int limit) {
		String sql="";
		List<MeetingRoom> meetingrooms=new ArrayList<MeetingRoom>();
		List<MeetingRoom> meetingroom=new ArrayList<MeetingRoom>();
		if(name.equals("")&&!state.equals("")) {
			if(state.equals("0")) {
				sql="select * from meetingroom where state='空闲'";
				
			}
			if(state.equals("1")) {
				sql="select * from meetingroom where state='已预定'";
				
			}
			if(state.equals("2")) {
				sql="select * from meetingroom where state='使用中'";
				
			}
		}
		if(name.equals("")&&state.equals("")) {
			sql="select * from meetingroom";
		}
		
		if(!name.equals("")&&state.equals("")) {
			sql="select * from meetingroom where name like"+"'%"+name+"%'";
		}
		if(!name.equals("")&&!state.equals("")){
			if(state.equals("0")) {
				sql="select * from meetingroom where state='空闲' and name like"+"'%"+name+"%'";
				
			}
			if(state.equals("1")) {
				sql="select * from meetingroom where state='已预定' and name like"+"'%"+name+"%'";
				
			}
			if(state.equals("2")) {
				sql="select * from meetingroom where state='使用中' and name like"+"'%"+name+"%'";
				
			}
		}
		meetingrooms=meetingroommapper.selectBySql(sql);
		meetingroom=meetingroommapper.selectByCondition(sql, start, limit);
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetingrooms.size());
        datasource.put("data",meetingroom);
		return datasource;
	}
	
	
	public List<MeetingRoom> meetingroomlist(){
		String sql="select * from meetingroom where state='空闲'";
		List<MeetingRoom> list=meetingroommapper.selectBySql(sql);
		return list;
	}
	
	public List<MeetingRoom> meetingroom(){
		String sql="select * from meetingroom";
		List<MeetingRoom> list=meetingroommapper.selectBySql(sql);
		return list;
	}
	public List<MeetingRoom> selectmeeting(String starttime,String endtime){
		String sql="select * from meetingroom";
		List<MeetingRoom> meetingrooms=meetingroommapper.selectBySql(sql);
		String sql1="select a.* from meeting a where a.state!='已结束' and ((unix_timestamp('"+starttime+"') between  unix_timestamp(a.starttime) and unix_timestamp(a.endtime)) or (unix_timestamp('"+endtime+"') between  unix_timestamp(a.starttime) and unix_timestamp(a.endtime)) or (unix_timestamp('"+starttime+"')<unix_timestamp(a.starttime) and unix_timestamp('"+endtime+"')>unix_timestamp(a.endtime)))";
		List<Meeting> meetings=meetingmapper.selectBySql(sql1);
		for(int i=0;i<meetings.size();i++) {
				for(int j=0;j<meetingrooms.size();j++) {
					if(meetingrooms.get(j).getId()==meetings.get(i).getMeetingroomid()) {
						meetingrooms.remove(j);
						break;
					}
				}
		}
		return meetingrooms;
	}
	public int add(MeetingRoom meetingroom,String username) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		meetingroom.setCreatename(username);
		meetingroom.setCreatetime(sdf.format(date));
		meetingroom.setState("空闲");
		meetingroommapper.insert(meetingroom);
		return 0;
	}
	
	public List<Meeting> querymeeting(int id) {
		List<Meeting> meetings=new ArrayList<Meeting>();
		meetings=meetingmapper.selectBymeetingroomid(id);
		if(null!=meetings&&meetings.size()!=0) {
			return meetings;
		}
		return null;
	}
	
	public Object meetinghistoryQuery(int meetingroomid) {
		List<HistoryMeeting> historymeeting=new ArrayList<HistoryMeeting>();
		historymeeting=meetinghistorymapper.selectById(meetingroomid);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return null;
	}
	
	public MeetingRoom meetingroom(int meetingroomid) {
		MeetingRoom meetingroom=meetingroommapper.selectById(meetingroomid).get(0);
		return meetingroom;
	}

	public int del(int id) {
		meetingroommapper.deleteById(id);
		return 0;
	}
}
