package com.meeting.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.DepartMapper;
import com.meeting.pojo.Depart;
import com.meeting.service.DepartService;

@Service
public class DepartServiceImpl implements DepartService{
	@Autowired
	private DepartMapper departmapper;
	public List<Depart> list(){
		String sql="select * from depart";
		List<Depart> list=departmapper.selectBySql(sql);
		return list;
	}
}
