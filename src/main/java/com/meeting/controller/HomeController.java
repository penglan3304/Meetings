package com.meeting.controller;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.meeting.constant.CVal;
import com.meeting.mapper.UserMapper;
import com.meeting.mapper.UserRoleMapper;
import com.meeting.pojo.AttendInfo;
import com.meeting.pojo.AttendMetting;
import com.meeting.pojo.JsonMsgBean;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingRoom;
import com.meeting.pojo.Menu;
import com.meeting.pojo.User;
import com.meeting.pojo.UserRole;
import com.meeting.utils.PageResult;
import com.meeting.utils.SecurityCode;
import com.meeting.service.AttendMeetingService;
import com.meeting.service.MailService;
import com.meeting.service.MeetingRoomService;
import com.meeting.service.MeetingService;
import com.meeting.service.MenuService;



@Controller
public class HomeController {

	@Autowired
    private MailService maliService;
	@Autowired
	private UserMapper usermapper;
	@Autowired
	private UserRoleMapper userrolemapper;
	@Autowired
	private MeetingRoomService meetingroomService;
	
	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) 
	{
		return "index";
	}
	
	@RequestMapping(value = "/home/show", method = RequestMethod.GET)
	public String home(HttpSession session,Model model) 
	{
		List<Meeting> meeting=meetingService.information();
		List<Map<String,Object>> ameetings=meetingService.attendendInfo();
		List<MeetingRoom> meetingroomlist=meetingroomService.meetingroom();
		session.setAttribute("information_", meeting);
		model.addAttribute("infos", meeting);
		model.addAttribute("endmeetings", ameetings);
		model.addAttribute("leng_", meeting.size());
		session.setAttribute("informations_", meetingroomlist);
		return "home/show";
	}
	
	@ResponseBody
	@RequestMapping(value = "/endInfo/show")
	public List<Map<String,Object>> endInfo() 
	{
		List<Map<String,Object>> ameetings=meetingService.attendendInfo();
		return ameetings;
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) 
	{
		User users=(User)session.getAttribute("users");
		PageResult waitcheck=meetingService.waitcheck(users,"","");
		PageResult waitchecked=meetingService.waitchecked(users,"","");
		PageResult pageResult = meetingService.checkednopass(users,"","");
		PageResult pageResult1 = meetingService.noattend(users,"","");
		PageResult pageResult2=meetingService.absent(users,"","");
        model.addAttribute("checkcount", waitcheck.getCount());
        model.addAttribute("checkedcount", waitchecked.getCount());
        model.addAttribute("nopasscount", pageResult.getCount());
        model.addAttribute("noattend", pageResult1.getCount());
        model.addAttribute("absent", pageResult2.getCount());
		return "welcome";
	}
	/**
	 * 
	   *   忘记密码
	 */
	@RequestMapping(value = "/forget", method = RequestMethod.GET)
	public String forget(HttpSession session,Model model ) 
	{
		//model.addAttribute("user", new User());   //表单提交时所用到的user
		return "forget";
	}
	/**
	    *   注册跳转
	 */
	
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String regist(HttpSession session,Model model ) 
	{
		model.addAttribute("user", new User());   //表单提交时所用到的user
		return "regist";
	}
	
	/**
	 * 
	 * @param user
	 * @param securitycode  
	 * 注册数据到数据库
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login/regist", method = { RequestMethod.POST })
	public String regist(User user,String securitycode, HttpSession session,Model model)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String email=user.getEmail();
		String phone=user.getPhone();
		List<User> user1=usermapper.selectByEmail(email);
		List<User> user2=usermapper.selectByPhone(phone);
		if((user1!=null&&user1.size()!=0)||(user2!=null&&user2.size()!=0))
		{
			model.addAttribute("registResult","此邮箱或手机号已注册" );
			return "regist";
		}
		else
		{
			Date date=new Date();
			user.setCreatetime(sdf.format(date));
			user.setModifytime(sdf.format(date));
			if(session.getAttribute("securitycode1")==null)
			{
				model.addAttribute("registResult", "验证码已失效");
				return "regist";
			}
			else if(securitycode.toLowerCase().equals(session.getAttribute("securitycode1").toString().toLowerCase()))
			{
				usermapper.insert(user);
				return "index";
				
			}
			
			else
			{
				model.addAttribute("registResult", "验证码输入有误");
				return "regist";
			}
		}
	}
	
	///登录验证
	
	@RequestMapping(value = "/login/authen", method = { RequestMethod.POST })
	public String authen(User user,String securitycode, HttpSession session,Model model,String way,String rememberMe,
			String email,HttpServletResponse res,String securitycode1)
	{
		if(way.equals("1"))
		{
			String phone=user.getPhone();
			String password=user.getPassword();
			String sql="select * from users where phone='"+phone+"'and password='"+password+"'";
			List<User> users=usermapper.selectBySql(sql);
			if(users!=null&&users.size()!=0)
			{
				String sqls="select * from userrole where userid="+users.get(0).getId();
				List<UserRole> roles=userrolemapper.selectBySql(sqls);
				if(roles!=null&&roles.size()!=0) {
					long status=roles.get(0).getRoleid();
					try {
						if(null==session.getAttribute("randCheckCode").toString()||session.getAttribute("randCheckCode").toString()=="")
						{
							model.addAttribute("registResult", "验证码已失效");
							return "index";
						}
						else if(securitycode.equals(session.getAttribute("randCheckCode").toString()))
						{
							//记住密码
							String loginInfo = phone + "_" + password;
							Cookie userCookie = new Cookie("loginInfo", loginInfo);
							if("1".equals(rememberMe)||"on".equals(rememberMe))
							{
								userCookie.setMaxAge(1 * 24 * 60 * 60); // 存活期为一天 1*24*60*60
							}
							else
							{
								userCookie.setMaxAge(0);	   
							}
							userCookie.setPath("/");
					        res.addCookie(userCookie);
					        /*List<Menu> menu=menuService.listMenu();
					        
					        session.setAttribute("mainmenus", menu);*/
					        session.setAttribute("currentUser", users.get(0).getUsername());
					        session.setAttribute("users",users.get(0) );
					        session.setAttribute("userid",users.get(0).getId() );
					        PageResult notifys=meetingService.notifydetail(users.get(0));
					        session.setAttribute("notifys", notifys.getCount());
					        session.setAttribute("status", status);
					        return "home";
						}
						else {
							model.addAttribute("registResult", "验证码输入有误");
							return "index";
						}
					}catch(Exception e) {
						model.addAttribute("registResult", "验证码失效");
						return "index";
					}
					
					
				}
				else
				{
					model.addAttribute("registResult", "用户名或密码错误");
					return "index";
				}
			}
			else
			{
				model.addAttribute("registResult", "用户不存在");
				return "index";
			}
		}
		
		if(way.equals("2"))
		{
			
			List<User> users=usermapper.selectByEmail(email);
			String sqls="select * from userrole where userid="+users.get(0).getId();
			List<UserRole> roles=userrolemapper.selectBySql(sqls);
			
			if(users!=null&&users.size()!=0)
			{
				long status=roles.get(0).getRoleid();
				if(session.getAttribute("securitycode1")==null)
				{
					model.addAttribute("registResult", "验证码已失效");
					return "index";
				}
				else if(securitycode1.toLowerCase().equals(session.getAttribute("securitycode1").toString().toLowerCase()))
				{
					//记住密码
					String loginInfo = email;
					Cookie userCookie = new Cookie("loginInfos", loginInfo);
					System.out.println(rememberMe);
					if("1".equals(rememberMe)||"on".equals(rememberMe))
					{
						userCookie.setMaxAge(1 * 24 * 60 * 60); // 存活期为一天 1*24*60*60
					}
					else
					{
						userCookie.setMaxAge(0);	   
					}
					/*userCookie.setPath("/");
			        res.addCookie(userCookie);
                    List<Menu> menu=menuService.listMenu();*/
			        //session.setAttribute("mainmenus", menu);
			        session.setAttribute("currentUser", users.get(0).getUsername());
			        session.setAttribute("users",users.get(0) );
			        session.setAttribute("userid",users.get(0).getId() );
			        PageResult notifys=meetingService.notifydetail(users.get(0));
			        session.setAttribute("notifys", notifys.getCount());
			        session.setAttribute("status", status);
			        return "home";
					
				}
				
				else
				{
					model.addAttribute("registResult", "验证码输入有误");
					return "index";
				}
			}
			else
			{
				model.addAttribute("registResult", "用户名或密码错误");
				return "index";
			}
		}
		return "home";
	}
	/**
	 * 系统注销的控制器
	 * 
	 * @param request
	 *            当前的Http请求，用于获取上下文路径
	 * @param session
	 *            当前会话，用于获取当前登录用户的信息
	 * @return 重定向到登录页面
	 */
	@RequestMapping(value = "/updatepassword", method = RequestMethod.GET)
	public String updatepassword(int id, HttpSession session) {
		List<User> user=usermapper.select(id);
		session.setAttribute("user", user.get(0));
		return "updatepassword";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public RedirectView logout(HttpServletRequest request, HttpSession session) {
		return new RedirectView(request.getContextPath() + "/", false);
	}
	
	
	
	/**
	 * 生成登录用的验证码
	 * 
	 * @param response
	 *            Http响应对象，用于输出验证码流
	 * @param session
	 *            Session对象，用于存放验证码
	 * @throws IOException
	 */
	@RequestMapping(value = "/graphics", method = RequestMethod.GET)
	public void securityCode(HttpServletResponse response, HttpSession session)
			throws IOException {
		// SecurityCode sc = SecurityCode.GenerateCode();
		SecurityCode sc = SecurityCode.GenerateCode(4, true, true, false,
				true, new Color(0, 0, 255), new Color(0, 0, 0));
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应是图片
		response.setContentType("image/jpeg");

		// 将生成的验证码保存到Session中
		session.setAttribute("randCheckCode", sc.getCode());
	//	this.logger.info("Generate security code:" + sc.getCode());

		ImageIO.write(sc.getImg(), "GIF", response.getOutputStream());
	}



	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param email      邮箱验证后发送验证码
	 * @param flag     0为登录时获取验证码；1为注册时获取验证码
	 * @return
	 * @throws IOException
	 */
	
	@RequestMapping (value = "/randomNum.do")
	public String  randomNum(Model model, HttpServletRequest request, HttpServletResponse response,String email,String flag) throws IOException {
		JsonMsgBean jsonMsg = null;
		int randomNum1= (int)((Math.random() * 9 + 1) * 100000);
		String s1 = String.valueOf(randomNum1);
		List<User> user=usermapper.selectByEmail(email);
		if(flag.equals("0"))
		{
			if (user == null || user.size() == 0) 
			{
				jsonMsg = new JsonMsgBean(0, CVal.Fail , "此邮箱未注册");
				
			}
			else if(user!=null&&user.size()!=0) 
			{
				//发送邮件	
					int result=0;
					
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date date=new java.util.Date();
					String str=sdf.format(date);
					String subject="登录验证"+str;
					String content="您的验证码是:"+s1+",请在有效时间60秒内使用";
					String toMail=email;
					result=maliService.sendSimpleMail(subject, content, toMail);
					/*model.addAttribute("tomail",toMail);
					model.addAttribute("mailResult",result);*/
					if(result==1)
					{
						jsonMsg = new JsonMsgBean(0, CVal.Success , "邮件发送成功");
						request.getSession().setAttribute("securitycode1", s1);
						request.getSession().setMaxInactiveInterval(60);
					}
					else
					{
						jsonMsg = new JsonMsgBean(0, CVal.Fail , "邮件发送失败");
					}
				 
			}
			else
			{
				jsonMsg = new JsonMsgBean(0, CVal.Exception, "邮件发送失败");
				
			}
		}
		if(flag.equals("1"))
		{
			//发送邮件	
			int result=0;
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date=new java.util.Date();
			String str=sdf.format(date);
			String subject="注册验证"+str;
			String content="您的验证码是:"+s1+",请在有效时间60秒内使用";
			String toMail=email;
			result=maliService.sendSimpleMail(subject, content, toMail);
			/*model.addAttribute("tomail",toMail);
			model.addAttribute("mailResult",result);*/
			if(result==1)
			{
				jsonMsg = new JsonMsgBean(0, CVal.Success , "邮件发送成功");
				request.getSession().setAttribute("securitycode1", s1);
				request.getSession().setMaxInactiveInterval(60);
			}
			else
			{
				jsonMsg = new JsonMsgBean(0, CVal.Fail , "邮件发送失败");
			}
		}
		model.addAttribute("jsonMsg", jsonMsg);
		return "common/jsonTextHtml";
	}
}
