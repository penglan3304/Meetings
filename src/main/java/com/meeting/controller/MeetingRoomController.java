package com.meeting.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.meeting.pojo.AddMeeting;
import com.meeting.pojo.Depart;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingRoom;
import com.meeting.pojo.User;
import com.meeting.service.MeetingRoomService;
import com.meeting.utils.PageResult;




@Controller
@RequestMapping("/meetingroom")
public class MeetingRoomController {
	
	@Autowired
	private MeetingRoomService meetingroomService;
	
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meetingroom/show";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/meetingroomlist")
	public Object meetinglist(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10")int limit, 
			@RequestParam(value = "name", defaultValue = "")String name,
			@RequestParam(value = "state", defaultValue = "")String state,Model model, HttpSession session) {
		Integer start = (page - 1) * limit;
		Object pageResult = meetingroomService.meetingQuery(start,name,state, limit);
		return pageResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addmeetingroom")
	public int addmeeting(MeetingRoom paramlist,HttpSession session) {
		String username=(String)session.getAttribute("currentUser");
		int result=meetingroomService.add(paramlist, username);
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request, HttpServletResponse response,Model model) {
		return "meetingroom/add";
	}
	
	@ResponseBody
	@RequestMapping(value = "/del")
	public int del(int ids) {
		int result=meetingroomService.del(ids);
		return result;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public MeetingRoom detail(HttpSession session,int id,String name,Model model) {
		session.setAttribute("meetingroomid", id);
		session.setAttribute("meetingroomname", name);
		MeetingRoom meetingroom=meetingroomService.meetingroom(id);
		model.addAttribute("meetingroom", meetingroom);
		return meetingroom;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectmeeting",method=RequestMethod.POST,produces = {"application/json;charset=utf-8"})
	public Object selectmeeting(String starttime,String endtime, HttpServletResponse response) {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<MeetingRoom>  meetingroom=meetingroomService.selectmeeting(starttime, endtime);
		PageResult pageResult = new PageResult();
		pageResult.setData(meetingroom);
		pageResult.setCount(meetingroom.size());
		return JSON.toJSONString(pageResult);
	}
	
	
	@RequestMapping(value = "/meetinghistory", method = RequestMethod.GET)
	public String meetinghistory(HttpSession session,HttpServletRequest request, HttpServletResponse response,Model model) {
		int id=(int) session.getAttribute("meetingroomid");
		String name=(String)session.getAttribute("meetingroomname");
		model.addAttribute("name", name);
		return "meetingroom/detail";
	}
}
