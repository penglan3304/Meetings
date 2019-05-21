package com.meeting.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meeting.constant.CVal;
import com.meeting.pojo.AttendMetting;
import com.meeting.pojo.JsonMsgBean;
import com.meeting.pojo.Meeting;
import com.meeting.service.AttendMeetingService;
import com.meeting.service.DepartService;
import com.meeting.service.MeetingService;
import com.meeting.service.UserService;

@Controller
@RequestMapping("/attendmeeting")
public class AttendMeetingController {
	@Resource(name = "meetingServiceImpl")
	private MeetingService meetingService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "departServiceImpl")
	private DepartService departService;
	
	@Resource(name = "attendMeetingServiceImpl")
	private AttendMeetingService attendmeetingService;

	
	@RequestMapping(value="/export", method = RequestMethod.GET)
	public String export(Model model,int id,HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		Meeting meeting=meetingService.meeting(id);
		JsonMsgBean jsonMsg = null;
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
	    
	    response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=stuTemplateExcel.xlsx");
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
		 model.addAttribute("jsonMsg", jsonMsg);
		  return "common/jsonTextHtml";
	}
}
