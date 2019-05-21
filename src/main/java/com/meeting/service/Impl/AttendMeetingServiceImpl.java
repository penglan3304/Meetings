package com.meeting.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.AttendMeetingMapper;
import com.meeting.pojo.AddMeeting;
import com.meeting.pojo.AttendMetting;
import com.meeting.pojo.User;
import com.meeting.service.AttendMeetingService;

@Service
public class AttendMeetingServiceImpl implements AttendMeetingService{

	@Autowired
	private AttendMeetingMapper attendmapper;
	@Override
	public int update(int meetingid, int personid) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		AttendMetting meeting=attendmapper.select(meetingid, personid);
		meeting.setSigntime(sdf.format(date));
		meeting.setState("已签到");
		attendmapper.update(meeting);
		return 0;
	}

	public Object perdetail(int id,String attendperson) {
		
		List<AttendMetting> meetings=null;
		if(!attendperson.equals("")) {
			meetings=attendmapper.selectBymeetingidN(id,attendperson);
		}else {
			meetings=attendmapper.selectBymeetingid(id);
		}
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}
	
	public List<AttendMetting> attendlist(int meetingid) {
		List<AttendMetting> meetinglist=attendmapper.selectBymeetingid(meetingid);
		return meetinglist;
	}
	public Object attendmeetinglist(int id,int start,int limit) {
		
		List<AttendMetting> meetings=null;
	   String sql="select * from attendmeeting where signtime!='' and meetingid="+id;
	   meetings=attendmapper.selectByCondition(sql, start, limit);
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}
	
	public int  counts(int id) {
		
	   String sql="select * from attendmeeting where signtime!='' and meetingid="+id;
	   int count=attendmapper.selectBySql(sql).size();
		return count;
	}
	
	public AttendMetting detail(int meetingid,int personid) {
		AttendMetting meeting=attendmapper.select(meetingid, personid);
		return meeting;
	}



	public int add(int meetingid, int personid,User user) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		AttendMetting meeting=new AttendMetting();
		meeting.setMeetingid(meetingid);
		meeting.setPersonid(personid);
		meeting.setAttendperson(user.getUsername());
		meeting.setSigntime(sdf.format(date));
		meeting.setState("旁听");
		attendmapper.insert(meeting);
		return 0;
	}

	@Override
	public int count(int meetingid) {
		List<AttendMetting> m=attendmapper.selectBymeetingid(meetingid);
		return m.size();
	}
	
	
	
}
