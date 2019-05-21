package com.meeting.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.DepartMapper;
import com.meeting.mapper.RoleMapper;
import com.meeting.mapper.UserMapper;
import com.meeting.mapper.UserRoleMapper;
import com.meeting.pojo.User;
import com.meeting.pojo.UserRole;
import com.meeting.service.UserService;
import com.meeting.utils.MosquittoSslTimeTest;
import com.meeting.utils.MyQueue;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper usermapper;
	@Autowired
	private UserRoleMapper userrolemapper;
	@Autowired
	private DepartMapper departmapper;
	@Autowired
	private RoleMapper rolemapper;
	
	
	public List<User> userlist(){
		String sql="select * from users";
		List<User> list=usermapper.selectBySql(sql);
		for(int i=0;i<list.size();i++) {
			list.get(i).setDepartname(departmapper.selectNameById(list.get(i).getDepartid()));
		}
		return list;
	}
	
	public List<User> userlists(User user){
		String sql="select a.* from users a,userrole b where a.id=b.userid and a.id!="+user.getId()+" and (b.roleid=1 or b.roleid=3)";
		List<User> list=usermapper.selectBySql(sql);
		for(int i=0;i<list.size();i++) {
			list.get(i).setDepartname(departmapper.selectNameById(list.get(i).getDepartid()));
		}
		return list;
	}
	
	@Override
	public Object userQuery(int start,String username, int limit) {
		List<User> users=null;
		int count=userlist().size();
		if(username!=null) {
			String sql="select * from users where username like"+"'%"+username+"%'";
			users=usermapper.selectByCondition(sql, start, limit);
			for(int i=0;i<users.size();i++) {
				users.get(i).setDepartname(departmapper.selectNameById(users.get(i).getDepartid()));
			}
		}
		else {
			String sql="select * from users";
			users=usermapper.selectByCondition(sql, start, limit);
			for(int i=0;i<users.size();i++) {
				users.get(i).setDepartname(departmapper.selectNameById(users.get(i).getDepartid()));
			}
		}
		
		Map<String,Object> datasource=new LinkedHashMap<String,Object>();
        datasource.put("code",0);
        datasource.put("msg","");
        datasource.put("count",count);
        datasource.put("data",users);
		return datasource;
	}
	
	public int add(User user,int roleid) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		user.setCreatetime(sdf.format(date));
		user.setModifytime(sdf.format(date));
		user.setPassword("123456");
		usermapper.insert(user);
		UserRole userrole=new UserRole();
		userrole.setRoleid(roleid);
		userrole.setUserid(user.getId());
		userrolemapper.insert(userrole);
		return 0;
	}
	
	
	public int update(int userid,User user,int roleid) {
		List<User> users=usermapper.select(userid);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		users.get(0).setModifytime(sdf.format(date));
		users.get(0).setEmail(user.getEmail());
		users.get(0).setDepartid(user.getDepartid());
		users.get(0).setPhone(user.getPhone());
		usermapper.update(users.get(0));
		List<UserRole> userroles=userrolemapper.selectByuserId(userid);
		userroles.get(0).setRoleid(roleid);
		userrolemapper.update(userroles.get(0));
		return 0;
	}
	
	public int updates(long userid,User user) {
		List<User> users=usermapper.select(userid);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		users.get(0).setModifytime(sdf.format(date));
		users.get(0).setPassword(user.getPassword());
		usermapper.update(users.get(0));
		return 0;
	}
	
	public int del(int id) {
		usermapper.deleteById(id);
		userrolemapper.deleteByUserId(id);
		return 0;
	}
	
	public User detail(int id) {
		List<User> user=usermapper.select(id);
		return user.get(0);
	}
	
	public UserRole findrole(int id) {
		List<UserRole> role=userrolemapper.selectByuserId(id);
		return role.get(0);
	}
	
	public String chatptp(String topic,String msg,User user){
		long start = System.currentTimeMillis();
		MosquittoSslTimeTest mosquitto=new MosquittoSslTimeTest();
		try {
			mosquitto.clientPublish(start, topic, msg);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		mosquitto.clientSubsreibe(start,/*String.valueOf(user.getId())*/topic );
		MyQueue myQueue=MyQueue.getInstance();
		String string="";
		while(!(myQueue.size()==0)){
			string=myQueue.get();
			System.out.println(string);
			}
		return string;
	}
	
}
