package com.meeting.service.Impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.google.zxing.WriterException;
import com.meeting.mapper.AttendMeetingMapper;
import com.meeting.mapper.DepartMapper;
import com.meeting.mapper.HistoryMeetingMapper;
import com.meeting.mapper.MeetingMapper;
import com.meeting.mapper.MeetingParamMapper;
import com.meeting.mapper.MeetingRoomMapper;
import com.meeting.mapper.NotifyMapper;
import com.meeting.mapper.UserMapper;
import com.meeting.pojo.AddMeeting;
import com.meeting.pojo.AttendInfo;
import com.meeting.pojo.AttendMetting;
import com.meeting.pojo.Depart;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingParam;
import com.meeting.pojo.MeetingRoom;
import com.meeting.pojo.Notify;
import com.meeting.pojo.User;
import com.meeting.service.MeetingService;
import com.meeting.utils.PageResult;
@Service
public class MeetingServiceImpl implements MeetingService{
	@Autowired
	private MeetingMapper meetingmapper;
	
	@Autowired
	private DepartMapper departmapper;
	
	@Autowired
	private MeetingParamMapper meetingparammapper;
	
	@Autowired
	private HistoryMeetingMapper historymeetingmapper;
	
	@Autowired
	private AttendMeetingMapper attendmeetingmapper;
	
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private MeetingRoomMapper meetingroommapper;
	
	
	@Autowired
	private NotifyMapper notifymapper;
	
	
	public Meeting meeting(int id){
		Meeting meeting=meetingmapper.selectById(id);
		return meeting;
	}
	
	public MeetingParam meetingparam(int meetingid){
		String sql="select * from meetingparam where meetingid="+meetingid;
		List<MeetingParam> meetingparam=meetingparammapper.selectBySql(sql);
		return meetingparam.get(0);
	}
	
	@Override
	public Object meetingQuery(int start,String starttime,String endtime, int limit) {
	
		int count=meetingmapper.count();
		String sqls="";
		if(!starttime.equals("")&&!endtime.equals("")) {
			sqls="select a.*,b.name as meetingroom from meeting a,meetingroom b WHERE a.meetingroomid=b.id and "
					+ "unix_timestamp(a.starttime) between "+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"
					+" and unix_timestamp(a.endtime) between "+"unix_timestamp('"+starttime+"')"
					+ "  and "+"unix_timestamp('"+endtime+"')"+" order by id asc";
		}
		else{
			sqls="select a.*,b.name as meetingroom from meeting a,meetingroom b WHERE a.meetingroomid=b.id order by a.id asc";
		}
		List<Meeting> meeting=meetingmapper.selectByCondition(sqls, start, limit);
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",count);
        datasource.put("data",meeting);
		return datasource;
	}
	
	public List<Meeting> information(){
		String sql="select a.*,b.name as meetingroom from meeting a,meetingroom b WHERE a.meetingroomid=b.id order "
				+ "by unix_timestamp(a.starttime) asc";
		List<Meeting> meeting=meetingmapper.selectBySql(sql);
		return meeting;
	}
	
	public List<Meeting> endmeeting(){
		String sql="select a.*,b.name as meetingroom from meeting a,meetingroom b WHERE a.meetingroomid=b.id  and a.state='已结束' order "
				+ "by unix_timestamp(a.starttime) asc";
		List<Meeting> meeting=meetingmapper.selectBySql(sql);
		return meeting;
	}
	
	
	public List<Map<String,Object>> attendendInfo(){
		String sql="select a.*,b.name as meetingroom from meeting a,meetingroom b WHERE a.meetingroomid=b.id  and a.state='已结束' order "
				+ "by unix_timestamp(a.starttime) asc";
		List<Meeting> meeting=meetingmapper.selectBySql(sql);
		List<Map<String,Object>> attendinfos=new ArrayList<>();
		for(int i=0;i<meeting.size();i++) {
			List<AttendInfo> attendinfo=attendmeetingmapper.selectInfo(meeting.get(i).getId());
			Map<String,Object> datasource=new LinkedHashMap<String,Object>();
			datasource.put("id",meeting.get(i).getId());
			datasource.put("name",meeting.get(i).getName());
	        datasource.put("data",attendinfo);
	        attendinfos.add(datasource);
		}
		return attendinfos;
	}
	
	@Override
	public int del(List<Integer> id,int meetingroomid) {
		int result=1;
		for(int i=0;i<id.size();i++) {
			meetingmapper.deleteById(id.get(i));
			meetingparammapper.deleteById(id.get(i));
			meetingroommapper.updatestate(meetingroomid);
			attendmeetingmapper.deleteBymeetingId(id.get(i));
			notifymapper.deleteById(meetingroomid);
			result=0;
		}
		return result;
	}
	
	
	@Override
	public List<Meeting> detail(int id) {
		String sql="select a.*,b.username as checkperson,d.name as meetingroom,c.departs  from "
				+ "meeting a,users b,meetingparam c,meetingroom d WHERE a.id="+id+" and c.meetingid=a.id and "
						+ "c.userid=b.id and a.meetingroomid=d.id";
		List<Meeting> meetings=meetingmapper.selectBySql(sql);
		List<Depart> departs=departmapper.selectBySql("select * from depart");
		Map<Integer,String> departMap=departs.stream().collect(Collectors.toMap(Depart::getId,Depart::getName));
		meetings.forEach(e->e.setDepartname(departMap.get(Integer.parseInt(String.valueOf(e.getDeparts())))));
		return meetings;
		
	}
	
	
	@Override
	public PageResult notifydetail(User user) {
		List<Notify> notifys=notifymapper.selectBySql("select * from notify");
		List<Meeting> meeting = new ArrayList<Meeting>();
		if(notifys.size()>0) {
			for(int i=0;i<notifys.size();i++) {
				String sql="select a.*,b.name as meetingroom from meeting a,meetingroom b,meetingparam c "
						+ "where a.id=c.meetingid and b.id=a.meetingroomid and a.id="+notifys.get(i).getMeetingid()+" "
								+ "and c.userid="+user.getId()+" and a.state='待审核'";
				List<Meeting> meetings=meetingmapper.selectBySql(sql);
				if(meetings.size()!=0) {
					meeting.add(meetings.get(0));
				}
				
			}
			List<Depart> departs=departmapper.selectBySql("select * from depart");
			Map<Integer,String> departMap=departs.stream().collect(Collectors.toMap(Depart::getId,Depart::getName));
			meeting.forEach(e->e.setDepartname(departMap.get(Integer.parseInt(String.valueOf(e.getDeparts())))));
		}
		
		PageResult pageresult=new PageResult();
		pageresult.setCode(0);
		pageresult.setMsg("");
		pageresult.setCount(meeting.size());
		pageresult.setData(meeting);
		return pageresult;
		
	}
	
	/**
	 * 添加会议：包括添加会议表，会议基本信息表，会议签到信息表
	 */
	
	public int add(AddMeeting paramlist,String username) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		Meeting meeting=new Meeting();
		MeetingParam meetingparam=new MeetingParam();
	//	HistoryMeeting historymeeting=new HistoryMeeting();
		meeting.setName(paramlist.getTitle());
		meeting.setCreatename(username);
		meeting.setMeetingroomid(Integer.parseInt(paramlist.getPlaceid()));
		meeting.setState("待审核");
		meeting.setIsanother(paramlist.getIsanother());
		meeting.setStarttime(paramlist.getStarttime());
		meeting.setEndtime(paramlist.getEndtime());
		meeting.setCreatetime(sdf.format(date));
		meeting.setModifytime(sdf.format(date));
		meetingmapper.insert(meeting);
		 
		//添加会议时添加会议的一些基本参数
		meetingparam.setMeetingid(meeting.getId());
		meetingparam.setUserid(Integer.parseInt(paramlist.getCheckperson()));
		meetingparam.setDeparts(paramlist.getDepartid());
		meetingparammapper.insert(meetingparam);
		
		//添加会议时修改会议室使用状态
		List<MeetingRoom>meetingrooms=meetingroommapper.selectById(meeting.getMeetingroomid());
		meetingrooms.get(0).setState("已预定");
		meetingroommapper.update(meetingrooms.get(0));
		
		/*historymeeting.setMeetingid(meeting.getId());
		historymeeting.setCreatetime(meeting.getCreatetime());
		historymeeting.setEndtime(meeting.getEndtime());
		historymeeting.setMeetingroomid(meeting.getMeetingroomid());
		historymeeting.setName(meeting.getName());
		historymeeting.setStarttime(meeting.getStarttime());
		historymeeting.setState(meeting.getState());
		historymeetingmapper.insert(historymeeting);-*/
		
		return 0;
	}
	
	
	public int updatemeetings(Meeting paramlist) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		/*Meeting meeting=new Meeting();
		
		meeting.setModifytime(sdf.format(date));
		meeting.setName(paramlist.getName());*/
		MeetingParam meetingparam=new MeetingParam();
		paramlist.setModifytime(sdf.format(date));
		meetingmapper.updateS(paramlist);
		meetingparam=meetingparammapper.selectByMeetingId(paramlist.getId());
		meetingparam.setDeparts(paramlist.getDeparts());
		meetingparammapper.update(meetingparam);
		
		return 0;
	}
	
	//待审核会议
	public PageResult waitcheck(User user,String starttime,String endtime) {
		
		List<MeetingParam> meetingparam= meetingparammapper.selectByUserId(user.getId());
		List<Meeting> waitcheck=new ArrayList<Meeting>();
		if(!starttime.equals("")&&!endtime.equals("")) {
			for(int i=0;i<meetingparam.size();i++) {
				String sql="select a.*,d.name as meetingroom,c.departs  from meeting a,users b,meetingparam c,meetingroom d "
						+ "WHERE a.id="+meetingparam.get(i).getMeetingid()+ " and c.meetingid=a.id and c.userid=b.id and a.meetingroomid=d.id and a.state='待审核'"
								+ " and unix_timestamp(a.starttime) between "
						+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"+" and unix_timestamp(a.endtime) between "+"unix_timestamp('"+starttime+"')"
								+ "  and "+"unix_timestamp('"+endtime+"')"; 
				if(null!=meetingmapper.selectBySql(sql)&&meetingmapper.selectBySql(sql).size()!=0) {
				    waitcheck.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		else {
			for(int i=0;i<meetingparam.size();i++) {
				String sql="select a.*,d.name as meetingroom,c.departs  from meeting a,users b,meetingparam c,meetingroom d "
						+ "WHERE a.id="+meetingparam.get(i).getMeetingid()+ " and c.meetingid=a.id and c.userid=b.id and a.meetingroomid=d.id and a.state='待审核'";
				if(null!=meetingmapper.selectBySql(sql)&&meetingmapper.selectBySql(sql).size()!=0) {
				    waitcheck.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		PageResult pageresult=new PageResult();
		pageresult.setCode(0);
		pageresult.setMsg("");
		pageresult.setCount(waitcheck.size());
		pageresult.setData(waitcheck);
		return pageresult;
	}
	
	//被待审核会议
	public PageResult waitchecked(User user,String starttime,String endtime) {
		String sql="";
		if(!starttime.equals("")&&!endtime.equals("")) {
			sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.state='待审核' and a.createname="+"'"+user.getUsername()+"'"
					+ " and unix_timestamp(a.starttime) between "
					+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"+" "
							+ "and unix_timestamp(a.endtime) between"+" unix_timestamp('"+starttime+"')"
					+ "  and "+"unix_timestamp('"+endtime+"')";
		}else{
			sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.state='待审核' and a.createname="+"'"+user.getUsername()+"'";
		}
		List<Meeting> waitchecked=meetingmapper.selectBySql(sql);
		PageResult pageresult=new PageResult();
		pageresult.setCode(0);
		pageresult.setMsg("");
		pageresult.setCount(waitchecked.size());
		pageresult.setData(waitchecked);
		return pageresult;
	}
	
	//已审核会议(被审核通过的会议)
  /* public PageResult checked(User user,String starttime,String endtime) {
		List<MeetingParam> meetingparam= meetingparammapper.selectByUserId(user.getId());
		List<Meeting> checked=new ArrayList<Meeting>();
		if(!starttime.equals("")&&!endtime.equals("")) {
			for(int i=0;i<meetingparam.size();i++) {
				String sql="select a.*,d.name as meetingroom,c.departs  from meeting a,users b,meetingparam c,meetingroom d "
						+ "WHERE a.id="+meetingparam.get(i).getMeetingid()+ "and c.meetingid=a.id and c.userid=b.id and a.meetingroomid=d.id and (a.state='审核通过' or a.state='审核不通过')"
								+ " and cast(a.starttime as TIMESTAMP) between "+"'"+starttime+"' and "+"'"+endtime+"'"+" and cast(a.endtime as TIMESTAMP) between"+"'"+starttime+"'"
								+ "  and "+"'"+endtime+"'"; 
				if(null!=meetingmapper.selectBySql(sql)&&meetingmapper.selectBySql(sql).size()!=0) {
					checked.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		else {
			for(int i=0;i<meetingparam.size();i++) {
				String sql="select a.*,d.name as meetingroom,c.departs  from meeting a,users b,meetingparam c,meetingroom d "
						+ "WHERE a.id="+meetingparam.get(i).getMeetingid()+ "and c.meetingid=a.id and c.userid=b.id and a.meetingroomid=d.id and (a.state='审核通过' or a.state='审核不通过')"; 
				if(null!=meetingmapper.selectBySql(sql)&&meetingmapper.selectBySql(sql).size()!=0) {
					checked.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		
		
		
		PageResult pageresult=new PageResult();
		pageresult.setCode(0);
		pageresult.setMsg("");
		pageresult.setCount(checked.size());
		pageresult.setData(checked);
		return pageresult;
	}*/
	//已审核会议(被审核通过的会议)
	   public PageResult checked(User user,String starttime,String endtime) {
			String sql="";
			if(!starttime.equals("")&&!endtime.equals("")) {
				sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.state!='待审核' and a.state!='审核不通过' and a.createname="+"'"+user.getUsername()+"'"
						+ " and unix_timestamp(a.starttime) between "
						+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"+" and unix_timestamp(a.endtime) "
								+ "between "+"unix_timestamp('"+starttime+"')"
						+ "  and "+"unix_timestamp('"+endtime+"')";
			}else{
				sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.state!='待审核' and a.state!='审核不通过' and a.createname="+"'"+user.getUsername()+"'";
			}
			List<Meeting> checked=meetingmapper.selectBySql(sql);
			
			PageResult pageresult=new PageResult();
			pageresult.setCode(0);
			pageresult.setMsg("");
			pageresult.setCount(checked.size());
			pageresult.setData(checked);
			return pageresult;
		}
	
	
	
	   public PageResult comment(int start, int limit ) {
				String sql="select a.*,b.name as meetingroom from meeting a, meetingroom b where a.state='已结束' and a.meetingroomid=b.id";
				List<Meeting> meeting=meetingmapper.selectByCondition(sql, start, limit);
				PageResult pageresult=new PageResult();
				pageresult.setCode(0);
				pageresult.setMsg("");
				pageresult.setCount(meeting.size());
				pageresult.setData(meeting);
				return pageresult;
			}
	
	

	
	//被审核未通过会议
		public PageResult checkednopass(User user,String starttime,String endtime) {
			String sql="";
			if(!starttime.equals("")&&!endtime.equals("")) {
				sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.state='审核不通过' and a.createname="+"'"+user.getUsername()+"'"
						+ " and unix_timestamp(a.starttime) between "
						+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"
						+" and unix_timestamp(a.endtime) between "+"unix_timestamp('"+starttime+"')"
						+ "  and "+"unix_timestamp('"+endtime+"')";
			}else{
				sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.state='审核不通过' and a.createname="+"'"+user.getUsername()+"'";
			}
			
			
			List<Meeting> waitchecked=meetingmapper.selectBySql(sql);
			PageResult pageresult=new PageResult();
			pageresult.setCode(0);
			pageresult.setMsg("");
			pageresult.setCount(waitchecked.size());
			pageresult.setData(waitchecked);
			return pageresult;
		}
	
		//已发布会议
		public Map<String,Object> published(User user,String starttime,String endtime) {
			String sql="";
			if(!starttime.equals("")&&!endtime.equals("")) {
				sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.createname="+"'"+user.getUsername()+"'"
						+ " and unix_timestamp(a.starttime) between "
						+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"
						+" and unix_timestamp(a.endtime) between "+"unix_timestamp('"+starttime+"')"
						+ "  and "+"unix_timestamp('"+endtime+"')";
			}else{
				sql="select a.*,d.name as meetingroom from meeting a,meetingroom d WHERE a.meetingroomid=d.id and a.createname="+"'"+user.getUsername()+"'";
			}
			List<Meeting> published=meetingmapper.selectBySql(sql);
			
			
			
			Map<String,Object> datasource=new LinkedHashMap<String,Object>();
	        datasource.put("code",0);
	        datasource.put("msg","");
	        datasource.put("count",published.size());
	        datasource.put("data",published);
			return datasource;
		}
	
	

		
		
	//未报名会议
	public Map<String,Object> nosign(User user) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectBypersonId(user.getId());
		List<Meeting> nosign=new ArrayList<Meeting>();
		if(attendmeeting.size()!=0) {
			for(int i=0;i<attendmeeting.size();i++) {
				if(attendmeeting.get(i).getState().equals("未报名")) {
					nosign.add(meetingmapper.selectById(attendmeeting.get(i).getMeetingid()));
				}
			}
		}
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",nosign.size());
        datasource.put("data",nosign);
		return datasource;
	}
	
	//未签到会议
	public Map<String,Object> signed(User user,int flag) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectBypersonId(user.getId());
		List<Meeting> signed=new ArrayList<Meeting>();
		List<Meeting> signeds=new ArrayList<Meeting>();
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
		if(attendmeeting.size()!=0) {
			for(int i=0;i<attendmeeting.size();i++) {
				if(attendmeeting.get(i).getState().equals("已报名")) {
					signed.add(meetingmapper.selectById(attendmeeting.get(i).getMeetingid()));
					if(new Date(signed.get(i).getStarttime()).getTime()>new Date().getTime()) {
						signeds.add(signed.get(i));
					}
				}
			}
			if(flag==0) {
				
		        datasource.put("code",0);
		        datasource.put("msg","");
		        datasource.put("count",signed.size());
		        datasource.put("data",signed);
				
			}
			else {
		        datasource.put("code",0);
		        datasource.put("msg","");
		        datasource.put("count",signeds.size());
		        datasource.put("data",signeds);
			}
		}
		else {
			datasource.put("code",0);
	        datasource.put("msg","");
	        datasource.put("count",signeds.size());
	        datasource.put("data",signeds);
		}
		
		return datasource;
	}
	
	
	//会议通过后参会人员可报名
	public int pass(int id) {
		meetingmapper.updateState(id);
		MeetingParam meetingparam=meetingparammapper.selectByMeetingId(id);
		AttendMetting attendmeeting=new AttendMetting();
		List<User> users=usermapper.selectByDepartId(meetingparam.getDeparts());
		for(int i=0;i<users.size();i++) {
			attendmeeting.setMeetingid(id);
			attendmeeting.setState("未报名");
			attendmeeting.setAbsencereason("");
			attendmeeting.setSigntime("");
			attendmeeting.setAttendperson(users.get(i).getUsername());
			attendmeeting.setPersonid(users.get(i).getId());
			attendmeetingmapper.insert(attendmeeting);
		}
		return 0;
	}
	
	
	//审核未通过
	public int nopass(@Param("id") int id,@Param("reason") String reason) {
		meetingmapper.updateStates(id,reason);
		return 0;
	}
	
	//会议请假
	public int absent(@Param("id") int id,@Param("reason") String reason,User user) {
		attendmeetingmapper.updateStates(id,reason,user.getId());
		return 0;
	}
	
	//会议报名成功
		public int changeState(int id,User user) {
		  AttendMetting attend=	attendmeetingmapper.select(id, user.getId());
			attendmeetingmapper.updateState(attend.getId());
			return 0;
		}
	//
	
	public int notifys(int id) {
		List<Notify> notify=notifymapper.selectByMeetingId(id);
		if(notify.size()==0) {
			Notify notifys=new Notify();
			notifys.setMeetingid(id);
			notifymapper.insert(notifys);
			return 0;
		}
		return 1;
	}
	
	public int notifydel(String ids) {
		int count=0;
		String id[]=ids.split(",");
		for(int i=0;i<id.length;i++) {
			notifymapper.deleteBymeetingId(Integer.parseInt(id[i]));
			count++;
		}
		if(count==id.length) {
			return 0;
		}
		return 1;
	}
	
	//未报名
	@Override
	public PageResult noattend(User user,String starttime,String endtime) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectByState(user.getId(), "未报名");
		List<Meeting> meetings=new ArrayList<Meeting>();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		String dates=sdf.format(date);
		String sql="";
		if(attendmeeting.size()!=0) {
			for(int i=0;i<attendmeeting.size();i++) {
				if(!starttime.equals("")&&!endtime.equals("")) {
						sql="select * from meeting WHERE unix_timestamp(starttime)>"+"unix_timestamp('"+dates+"') and id="+attendmeeting.get(i).getMeetingid()
							+ " and unix_timestamp(starttime) between "+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"
								+" and unix_timestamp(endtime) between "+"unix_timestamp('"+starttime+"')"
							+ "  and "+"unix_timestamp('"+endtime+"')";
					
				}
				else {
					sql="select * from meeting WHERE unix_timestamp(starttime)>"+"unix_timestamp('"+dates+"') and id="+attendmeeting.get(i).getMeetingid();
				}
				if(meetingmapper.selectBySql(sql).size()!=0) {
					meetings.add(meetingmapper.selectBySql(sql).get(0));
				}
				
			}
		}
		
		PageResult pageresult=new PageResult();
		pageresult.setCode(0);
		pageresult.setMsg("");
		pageresult.setCount(meetings.size());
		pageresult.setData(meetings);
		return pageresult;
	}
	
	//未参加
	@Override
	public PageResult absent(User user,String starttime,String endtime) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectByState(user.getId(), "请假");
		List<Meeting> meetings=new ArrayList<Meeting>();
		String sql="";
		if(attendmeeting.size()!=0) {
			for(int i=0;i<attendmeeting.size();i++) {
				if(!starttime.equals("")&&!endtime.equals("")) {
						sql="select * from meeting WHERE id="+attendmeeting.get(i).getMeetingid()
							+ " and unix_timestamp(starttime) between "
								+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"+" and unix_timestamp(endtime) between "
							+" unix_timestamp('"+starttime+"')"
							+ "  and "+"unix_timestamp('"+endtime+"')";
					
				}
				else {
					sql="select * from meeting WHERE id="+attendmeeting.get(i).getMeetingid();
				}
				if(meetingmapper.selectBySql(sql).size()!=0) {
					meetings.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		PageResult pageresult=new PageResult();
		pageresult.setCode(0);
		pageresult.setMsg("");
		pageresult.setCount(meetings.size());
		pageresult.setData(meetings);
		return pageresult;
	}
	
	//已报名会议
	
	public Object attended(User user,String starttime,String endtime) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectByState(user.getId(), "已报名");
		List<Meeting> meetings=new ArrayList<Meeting>();
		String sql="";
		if(attendmeeting.size()!=0) {
			for(int i=0;i<attendmeeting.size();i++) {
				if(!starttime.equals("")&&!endtime.equals("")) {
						sql="select * from meeting WHERE id="+attendmeeting.get(i).getMeetingid()
							+ " and unix_timestamp(starttime) between "
								+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"+" and unix_timestamp(endtime) between "
							+"unix_timestamp('"+starttime+"')"
							+ "  and "+"unix_timestamp('"+endtime+"')";
					
				}
				else {
					sql="select * from meeting WHERE id="+attendmeeting.get(i).getMeetingid();
				}
				if(meetingmapper.selectBySql(sql).size()!=0) {
					meetings.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}
	
	//已参加会议
	public Object hasattended(User user,String starttime,String endtime) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectByState(user.getId(), "已签到");
		List<Meeting> meetings=new ArrayList<Meeting>();
		String sql="";
		if(attendmeeting.size()!=0) {
			for(int i=0;i<attendmeeting.size();i++) {
				if(!starttime.equals("")&&!endtime.equals("")) {
						sql="select * from meeting WHERE id="+attendmeeting.get(i).getMeetingid()+" and state='已结束'"
							+ " and unix_timestamp(starttime) between "
								+"unix_timestamp('"+starttime+"') and "+"unix_timestamp('"+endtime+"')"+" and unix_timestamp(endtime) between "
							+"unix_timestamp('"+starttime+"')"
							+ "  and "+"unix_timestamp('"+endtime+"')";
					
				}
				else {
					sql="select * from meeting WHERE id="+attendmeeting.get(i).getMeetingid()+" and state='已结束'";
				}
				if(meetingmapper.selectBySql(sql).size()!=0) {
					meetings.add(meetingmapper.selectBySql(sql).get(0));
				}
			}
		}
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}
	
	public Object listen(User user) {
		String sql="select a.* from meeting a,meetingparam b where a.isanother='是' and a.id=b.meetingid and state='审核通过' and b.departs!="+user.getDepartid()+" and createname!="+"'"+user.getUsername()+"'";
		List<Meeting> meetings=meetingmapper.selectBySql(sql);
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}
	
	
	
	//已结束会议
	public Object end() {
		String sql="select a.*,b.name as meetingname from attendmeeting a,meeting b where a.meetingid=b.id and b.state='已结束' ";
		List<AttendMetting> meetings=attendmeetingmapper.selectBySql(sql);
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}
	
	public int meetingstate(int id,int fg) {
		Meeting meeting=meetingmapper.selectById(id);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		List<MeetingRoom> meetingroom=null;
		List<Meeting> meetings=null;
		int counta=0;
		int countb=0;
		if(null!=meeting) {
			meetingroom=meetingroommapper.selectById(meeting.getMeetingroomid());
			meetings=meetingmapper.selectBymeetingroomid(meeting.getMeetingroomid());
			for(int i=0;i<meetings.size();i++)
			{
				if(meetings.get(i).getState().equals("已结束")) {
					counta++;
				}
				if(meetings.get(i).getState().equals("正进行")) {
					countb++;
				}
			}
		}
		if(fg==0) {
			meeting.setState("正进行");
			meeting.setStarttime(sdf.format(date));
			meetingroom.get(0).setState("使用中");
			meetingroommapper.update(meetingroom.get(0));
		}
		if(fg==1) {
			meeting.setState("已结束");
			meeting.setEndtime(sdf.format(date));
			if(counta==meetings.size()) {
				meetingroom.get(0).setState("空闲");
				meetingroommapper.update(meetingroom.get(0));
			}
			else if(countb>0) {
				meetingroom.get(0).setState("使用中");
				meetingroommapper.update(meetingroom.get(0));
			}
			else {
				meetingroom.get(0).setState("已预定");
				meetingroommapper.update(meetingroom.get(0));
			}
		}
		meetingmapper.update(meeting);
		
		
		return 0;
	}
	/*public void QRCodeInit(String content,HttpServletResponse response) throws WriterException, IOException {
		String bottom="会议签到系统";
		 Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		 hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		 hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		 hints.put(EncodeHintType.MARGIN,2);
			int width=250;
			int height=250;
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, height,width,hints);
			OutputStream stream = response.getOutputStream();
			Zxing.writeToStream(bitMatrix, "jpg", stream,bottom);
			stream.flush();
		    stream.close();
	}*/
	
    
    
    public void QRCodeInit(String content,HttpServletResponse response) throws WriterException, IOException {
		
    }
	
	/*public Object absent(User user) {
		List<AttendMetting> attendmeeting=attendmeetingmapper.selectByState(user.getId(), "请假");
		List<Meeting> meetings=new ArrayList<Meeting>();
		for(int i=0;i<attendmeeting.size();i++) {
			Meeting meeting=meetingmapper.selectById(attendmeeting.get(i).getMeetingid());
			meetings.add(meeting);
		}
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",meetings.size());
        datasource.put("data",meetings);
		return datasource;
	}*/

}
