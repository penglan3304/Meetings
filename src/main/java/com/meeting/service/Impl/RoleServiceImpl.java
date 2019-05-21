package com.meeting.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.RoleMapper;
import com.meeting.pojo.Roles;
import com.meeting.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleMapper rolemapper;
	
	public List<Roles> rolelist(){
		String sql="select * from roles";
		List<Roles> roles=rolemapper.selectBySql(sql);
		return roles;
	}
	
}
