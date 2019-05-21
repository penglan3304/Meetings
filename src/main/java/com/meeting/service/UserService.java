package com.meeting.service;

import java.util.List;


import com.meeting.pojo.User;
import com.meeting.pojo.UserRole;

public interface UserService {
	//public List<User> 
	public List<User> userlist();
	public Object userQuery(int start,String username, int limit);
	public int add(User user,int roleid);
	public int del(int id);
	public User detail(int id);
	public UserRole findrole(int id);
	public int update(int userid,User user,int roleid);
	public int updates(long userid,User user);
	public String chatptp(String topic,String msg,User user);
	public List<User> userlists(User user);
}
