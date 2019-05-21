package com.meeting.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.meeting.pojo.Depart;
import com.meeting.pojo.Roles;
import com.meeting.pojo.User;
import com.meeting.pojo.UserRole;
import com.meeting.service.DepartService;
import com.meeting.service.RoleService;
import com.meeting.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "departServiceImpl")
	private DepartService departService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;
	
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) 
	{
		List<User> user=userService.userlist();
		model.addAttribute("userlist", user);
		return "user/show";
	}
	
	@RequestMapping(value = "/detailshow", method = RequestMethod.GET)
	public String detailshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "user/detail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/userlist")
	public Object meetinglist(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10")int limit,
			@RequestParam(value = "username", defaultValue = "")String username, Model model, HttpSession session) {
		Integer start = (page - 1) * limit;
		Object pageResult = userService.userQuery(start, username,limit);
		return pageResult;
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		List<Depart> departlist=departService.list();
		List<Roles> rolelist=roleService.rolelist();
		model.addAttribute("departlist", departlist);
		model.addAttribute("rolelist", rolelist);
		return "user/add";
	}
	
	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String chat(HttpServletRequest request, HttpServletResponse response,Model model) {
		List<User> userlist=userService.userlist();
		/*List<Depart> departlist=departService.list();
		List<Roles> rolelist=roleService.rolelist();
		model.addAttribute("departlist", departlist);
		model.addAttribute("rolelist", rolelist);*/
		model.addAttribute("userlist", userlist);
		return "user/chat";
	}
	
	@ResponseBody
	@RequestMapping(value = "/chatptp")
	public String chatptp(Model model,String topic,String msg,HttpSession session) {
		User user=(User)session.getAttribute("users");
		String result=userService.chatptp(topic, msg, user);
		return result;
	}

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response,Model model,HttpSession session,
			Integer id) {
		List<Depart> departlist=departService.list();
		List<Roles> rolelist=roleService.rolelist();
		model.addAttribute("departlist", departlist);
		model.addAttribute("rolelist", rolelist);
		session.setAttribute("userid", id);
		return "user/edit";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/adduser")
	public int adduser(User paramlist,HttpSession session,int roleid) {
		int result=userService.add(paramlist,roleid);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateuser")
	public int updateuser(User paramlist,HttpSession session,int roleid) {
		int userid=(int)session.getAttribute("userid");
		int result=userService.update(userid,paramlist,roleid);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateusers")
	public int updateusers(User paramlist,HttpSession session,HttpServletRequest request) {
		long userid=(long)session.getAttribute("userid");
		int result=userService.updates(userid, paramlist);
		if(result==0) {
			request.getSession().removeAttribute("user");
			request.getSession().invalidate();
			//return "redirect:http://localhost:8080/meetings/";
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deluser")
	public int deluser(int id) {
		int result=userService.del(id);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/detail")
	public User detail(int id) {
		return userService.detail(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/findrole")
	public UserRole findrole(int id) {
		return userService.findrole(id);
	}
	
}
