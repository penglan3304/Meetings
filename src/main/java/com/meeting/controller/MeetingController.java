package com.meeting.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.meeting.pojo.AddMeeting;
import com.meeting.pojo.AttendMetting;
import com.meeting.pojo.Depart;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingParam;
import com.meeting.pojo.MeetingRoom;
import com.meeting.pojo.User;
import com.meeting.service.AttendMeetingService;
import com.meeting.service.DepartService;
import com.meeting.service.MailService;
import com.meeting.service.MeetingRoomService;
import com.meeting.service.MeetingService;
import com.meeting.service.UserService;
import com.meeting.utils.Zxing;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	@Autowired
	private MeetingService meetingService;

	@Autowired
    private MailService maliService;
	
	@Autowired
	private MeetingRoomService meetingroomService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DepartService departService;
	
	@Autowired
	private AttendMeetingService attendmeetingService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/show";
	}
	
	
	@RequestMapping(value = "/waitshow", method = RequestMethod.GET)
	public String waitshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/waitcheck";
	}
	
	
	/**
	 * 
	 * @param model
	 * @param session  待审核
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/waitcheck")
	public Object waitcheck(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.waitcheck(user,starttime,endtime);
		return pageResult;
	}
	
	@RequestMapping(value = "/waitcheckedshow", method = RequestMethod.GET)
	public String waitcheckedshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/waitchecked";
	}
	/**
	 * 
	 * @param model
	 * @param session  待被审核
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/waitchecked")
	public Object waitchecked(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.waitchecked(user,starttime,endtime);
		return pageResult;
	}
	
	@RequestMapping(value = "/checkedshow", method = RequestMethod.GET)
	public String checkedshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/checked";
	}
	
	/**
	 * 
	 * @param model
	 * @param session  已审核
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/checked")
	public Object checked(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.checked(user,starttime,endtime);
		return pageResult;
	}
	
	
	
	@RequestMapping(value = "/checkednopassshow", method = RequestMethod.GET)
	public String checkednopassshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/checkednopass";
	}
	
	@RequestMapping(value = "/leaveshow", method = RequestMethod.GET)
	public String leaveshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/leaveshow";
	}
	
	@RequestMapping(value = "/detailshow", method = RequestMethod.GET)
	public String detailshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/detail";
	}

	@RequestMapping(value = "/nopassshow", method = RequestMethod.GET)
	public String nopassshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/nopassshow";
	}
	
	@RequestMapping(value = "/publishedshow", method = RequestMethod.GET)
	public String publishedshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/publishedshow";
	}
	
	
	@RequestMapping(value = "/noattendshow", method = RequestMethod.GET)
	public String noattendshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/noattendshow";
	}
	
	@RequestMapping(value = "/attendedshow", method = RequestMethod.GET)
	public String attendedshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/attendedshow";
	}
	
	@RequestMapping(value = "/listenshow", method = RequestMethod.GET)
	public String listenshow(HttpServletRequest request, HttpServletResponse response) 
	{
		return "meeting/listenshow";
	}
	
	@RequestMapping(value = "/perdetailshow", method = RequestMethod.GET)
	public String perdetailshow(Model model,int id) 
	{
		model.addAttribute("id", id);
		return "meeting/perdetailshow";
	}
	
	//已参加会议
	@RequestMapping(value = "/hasattendshow", method = RequestMethod.GET)
	public String hasattendshow(Model model) 
	{
		return "meeting/hasattendshow";
	}
	
	@RequestMapping(value = "/signshow", method = RequestMethod.GET)
	public String signshow(HttpSession session,int meetingid,Model model) 
	{
		session.setAttribute("singmeetingid", meetingid);
		int count=attendmeetingService.count(meetingid);
		model.addAttribute("counts", count);
		int count_=attendmeetingService.counts(meetingid);
		model.addAttribute("count", count_);
		return "meeting/signshow";
	}
	@RequestMapping(value = "/information", method = RequestMethod.GET)
	public String information(HttpSession session,Model model) 
	{
		List<Meeting> meeting=meetingService.information();
		List<MeetingRoom> meetingroomlist=meetingroomService.meetingroom();
		session.setAttribute("information", meeting);
		model.addAttribute("infos", meeting);
		model.addAttribute("leng", meeting.size());
		session.setAttribute("informations", meetingroomlist);
		return "meeting/infomation";
	}
	
	
	@RequestMapping(value = "/nopassedit", method = RequestMethod.GET)
	public String nopassedit(HttpServletRequest request, HttpServletResponse response,Model model
			                  ,Integer meetingroomid,String meetingroom,Integer id) 
	{
		List<MeetingRoom> meetingroomlist=meetingroomService.meetingroomlist();
		List<User> userlist=userService.userlist();
		List<Depart> departlist=departService.list();
		model.addAttribute("meetingroomlist", meetingroomlist);
		model.addAttribute("userlist", userlist);
		model.addAttribute("departlist", departlist);
		/*model.addAttribute("meetingroomid", meetingroomid);
		model.addAttribute("meetingroom", meetingroom);*/
		model.addAttribute("id", id);
		Meeting meeting=meetingService.meeting(id);
		MeetingParam param=meetingService.meetingparam(id);
		model.addAttribute("meeting", meeting);
		model.addAttribute("param", param);
		return "meeting/nopassedit";
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpSession session,Model model) {
		List<MeetingRoom> meetingroomlist=meetingroomService.meetingroomlist();
		User user=(User)session.getAttribute("users");
		List<User> userlist=userService.userlists(user);
		List<Depart> departlist=departService.list();
		model.addAttribute("meetingroomlist", meetingroomlist);
		model.addAttribute("userlist", userlist);
		model.addAttribute("departlist", departlist);
		return "meeting/add";
	}
	
	
	@RequestMapping(value = "/notifys", method = RequestMethod.GET)
	public String notifys(HttpServletRequest request, HttpServletResponse response,Model model) {
		return "meeting/notifydetail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/notify")
	public int notify(int ids) {
		int result=meetingService.notifys(ids);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/meetingstate")
	public int meetingstate(int id,int fg) {
		int result=meetingService.meetingstate(id, fg);
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/meetinglist")
	public Object meetinglist(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10")int limit,
			@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		Integer start = (page - 1) * limit;
		//session.getAttribute("users");
		Object pageResult = meetingService.meetingQuery(start,starttime,endtime, limit);
		return pageResult;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/attmeetinglist")
	public Object attmeetinglist(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "5")int limit,Model model, HttpSession session) {
		Integer start = (page - 1) * limit;
		int meetingid=(int)session.getAttribute("singmeetingid");
		//session.getAttribute("users");
		Object pageResult =attendmeetingService.attendmeetinglist(meetingid, start, limit);
		return pageResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/perdetaillist")
	public Object perdetaillist(@RequestParam(value = "username", defaultValue = "")String attendperson,
			Model model, HttpSession session,int id) {
		Object pageResult=attendmeetingService.perdetail(id,attendperson);
		return pageResult;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/del")
	public int del(String ids,Integer meetingroomid){
		String[] idsarray=ids.split(",");
		int result=1;
		List<Integer> idarray=new ArrayList<Integer>();
		for(int i=0;i<idsarray.length;i++) {
			idarray.add(Integer.parseInt(idsarray[i]));
		}
		result=meetingService.del(idarray,meetingroomid);
		return result;
		
	}
	@ResponseBody
	@RequestMapping(value = "/detail")
	public Object detail(int id) {
		List<Meeting> meeting=meetingService.detail(id);
		return meeting.get(0);
	}
	
	@ResponseBody
	@RequestMapping(value = "/notifydetail")
	public Object notifydetail(HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.notifydetail(user);
		return pageResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/published")
	public Object published(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.published(user,starttime,endtime);
		return pageResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/attended")
	public Object attended(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.attended(user,starttime,endtime);
		return pageResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/notifydel")
	public int notifydel(String ids) {
		int result = meetingService.notifydel(ids);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/read")
	public int read(HttpSession session,String ids) {
		String id[]=ids.split(",");
		long count=(long) session.getAttribute("notifys");
		session.setAttribute("notifys", count-id.length);
        return 0;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addmeeting")
	public int addmeeting(AddMeeting paramlist,HttpSession session) {
		String username=(String)session.getAttribute("currentUser");
		int result=meetingService.add(paramlist, username);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updatemeetings")
	public int updatemeetings(Meeting paramlist,HttpSession session) {
		int result=meetingService.updatemeetings(paramlist);
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/pass")
	public int pass(Model model, HttpSession session,int ids) {
		int result=meetingService.pass(ids);
		return result;
	}
	
	
	
	

	
	
	@ResponseBody
	@RequestMapping(value = "/checkednopass")
	public Object checkednopass(Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.checkednopass(user);
		return pageResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/noattend")
	public Object noattend(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.noattend(user,starttime,endtime);
		return pageResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/nopass")
	public Object nopass(int id, String reason) {
		int result= meetingService.nopass(id, reason);
		return result;
	}
	
	//请假的会议，主要查询请了假的会议
	@ResponseBody
	@RequestMapping(value = "/absent")
	public Object absent(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.absent(user,starttime,endtime);
		return pageResult;
	}
	
	//请假是否成功，主要修改参会信息
	
	@ResponseBody
	@RequestMapping(value = "/absents")
	public Object absent(int id, String reason, HttpSession session) {
		User user=(User)session.getAttribute("users");
		int result= meetingService.absent(id, reason,user);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/listen")
	public Object listen(Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.listen(user);
		return pageResult;
	}

	
	@ResponseBody
	@RequestMapping(value = "/hasattend")
	public Object hasattend(@RequestParam(value = "starttime", defaultValue = "")String starttime,
			@RequestParam(value = "endtime", defaultValue = "")String endtime,Model model, HttpSession session) {
		User user=(User)session.getAttribute("users");
		Object pageResult = meetingService.hasattended(user, starttime, endtime);
		return pageResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendMail")
	public int sendMail(HttpSession session,HttpServletResponse response,int id,int flag) throws WriterException, IOException, MessagingException {
		User user=(User)session.getAttribute("users");
		String subject=user.getUsername()+"，您好!";
		String toMail=user.getEmail();
		String bottom="会议签到系统";
		String content=id+","+user.getId()+","+flag;
		String path="D:/img";
		File file1 = new File(path,"sign"+user.getUsername()+user.getId()+".jpg");
		 Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		 hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		 hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		 hints.put(EncodeHintType.MARGIN,2);
			int width=250;
			int height=250;
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, height,width,hints);
			Zxing.writeToFile(bitMatrix, "jpg", file1,bottom);
		String picturePath=path+"/"+"sign"+user.getUsername()+user.getId()+".jpg";
		//int result=maliService.sendSimpleMail(subject, picturePath, toMail);
		int result=maliService.sendPictureMail(subject, "", toMail, picturePath);
		return result;
	}
	
	
	//报名成功后改变出席人员状态
	@ResponseBody
	@RequestMapping(value = "/changestate")
	public int changestate(int id,HttpSession session) {
		User user=(User)session.getAttribute("users");
		int result=meetingService.changeState(id, user);
		return result;
	}
	

	 @RequestMapping("/sign")
	 @ResponseBody
	 public String decodeEwm(MultipartFile ewmImg){
	 String parse = null;
	 try {
	  parse = MeetingController.parse(ewmImg.getInputStream());
	 } catch (Exception e) {
	  //e.printStackTrace();
	 }
	 String msg = "no";
	 if(null!=parse){
	  return parse;
	 }
	 return msg;
	 }
	
	    @RequestMapping(value="/export", produces = "text/html;charset=UTF-8")
		public void export(Model model,int id,HttpServletResponse response, HttpServletRequest request) throws IOException
		{
			Meeting meeting=meetingService.meeting(id);
			//JsonMsgBean jsonMsg = null;
			int rowIndex=1;
			String fileName=meeting.getName();
		    HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(fileName);
			//产生表格标题行
			HSSFCell cell=null;
			HSSFRow row=sheet.createRow(0);
		 //   String []heads= {"id","项目名称","创建人","创建时间","备注"};
		    cell = row.createCell((short) 0);
		    cell.setCellValue("id");
		    cell = row.createCell((short) 1);
		    cell.setCellValue("参会人");
		    cell = row.createCell((short) 2);
		    cell.setCellValue("签到时间");
		    cell=row.createCell((short)3);
		    cell.setCellValue("参会状态");
		    cell=row.createCell((short)4);
		    cell.setCellValue("缺席原因");
		    List<AttendMetting> list=null;
		 
		   list=attendmeetingService.attendlist(id);
		   
		    for(AttendMetting attendmeeting:list)
		    {
		    	HSSFRow rows=sheet.createRow(rowIndex);
		    	cell=rows.createCell((short) 0);  //第1列
		    	Long ids=attendmeeting.getId();
		    	if(ids!=null)
		    	cell.setCellValue(id);
		    	cell=rows.createCell((short) 1);  //第2列
		    	String name=attendmeeting.getAttendperson();
		    	if(name!=null)
		    		cell.setCellValue(name);
		    	cell=rows.createCell((short) 2);  //第3列
		    	String signtime=attendmeeting.getSigntime();
		    	if(signtime!=null) {
		    		cell.setCellValue(signtime);
	    		//设置日期格式
	    		HSSFCellStyle cellStyle = workbook.createCellStyle();
	            HSSFDataFormat format= workbook.createDataFormat();
	            cellStyle.setDataFormat(format.getFormat("yyyy年m月d日 hh:mm:ss"));
	            cell.setCellStyle(cellStyle);
	    	    }
		    	cell=rows.createCell((short) 3);  //第4列
		    	String state=attendmeeting.getState();
		    	if(state!=null) {
		    		cell.setCellValue(state);
		    	}
		    	cell=rows.createCell((short) 4);  //第5列
		    	String remark=attendmeeting.getAbsencereason();
		    	if(remark!=null)
		    		cell.setCellValue(remark);
		    	rowIndex++;
		    }
		    response.reset();
		    response.setContentType("application/ms-txt;charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        response.setHeader("Content-disposition", "attachment;filename="+ new String(fileName.getBytes("GBK"),"ISO8859_1")+".xls");
	        //调取response对象中的OutputStream对象
	        OutputStream os = null;
	        try {
	            os = response.getOutputStream();
	            workbook.write(os);
	            os.flush();
	            os.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
	 
	 @ResponseBody
	 @RequestMapping(value = "/signstate")
	 public int signstate(HttpSession session,String data,Model model) {
		    int result=0;
		    String datas[]=data.split(",");
		    if(datas.length==3) {
		    	int id=(int)session.getAttribute("singmeetingid");
				if(id==Integer.parseInt(datas[0])) {                       //是否存在此会议
					User user=userService.detail(Integer.parseInt(datas[1]));   //查询是否存在此用户
					if(Integer.parseInt(datas[2])==0) {                         //非旁听
						AttendMetting meeting=attendmeetingService.detail(Integer.parseInt(datas[0]), Integer.parseInt(datas[1]));
						if(null!=meeting) {
							
							if(null!=user) {
								if(meeting.getState().equals("已签到")) {
								      result=5;
								}
								else {
									result=attendmeetingService.update(Integer.parseInt(datas[0]), Integer.parseInt(datas[1]));
								}
							  
							}
							else
								result=1;
						}
						else {
							result=2;
						}
			    	}
					else if(Integer.parseInt(datas[2])==1) {                    //旁听
						if(null!=user) {
							  result=attendmeetingService.add(Integer.parseInt(datas[0]), Integer.parseInt(datas[1]),user);
							}
							else
								result=1;
						result=4;
					}
					else {
						result=3;
					}
					
				}
				else {
					result=2;
				}
		    }
		    else {
		    	result=3;
		    }
			return result;
		}
	 
	public static final String parse(InputStream input) throws Exception {
		   Reader reader = null;
		   BufferedImage image;
		   try {
		     image = ImageIO.read(input);
		     if (image == null) {
		       throw new Exception("cannot read image from inputstream.");
		     }
		     
		     final LuminanceSource source = new BufferedImageLuminanceSource(image);
		     final BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		     final Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		     hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		     // 解码设置编码方式为：utf-8，
		     reader = new MultiFormatReader();
		     return reader.decode(bitmap, hints).getText();
		   } catch (IOException e) {
		     e.printStackTrace();
		     throw new Exception("parse QR code error: ", e);
		   } catch (ReaderException e) {
		     e.printStackTrace();
		     throw new Exception("parse QR code error: ", e);
		     }
	 }
	
}
