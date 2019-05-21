package com.meeting.pojo;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;



/**
 * 
 * @author 
 *
 */
public class JsonMsgBean {
	private Integer id;
	private int code;
	private int flag;
	private String msg;
	private String result;
	private String exception;
	private String activityStatu;
	//新增对象
	private Object obj;
	private Object obj1;
	private Object obj2;
	


	public JsonMsgBean(Integer id, int code){
		this.setId(id);
		this.code = code;			
	}		
	
	public JsonMsgBean(int id, int code, String msg){
		this.setId(id);
		this.code = code;
		this.msg = msg;
	}
	
	public JsonMsgBean(int id, int code, String msg , String result){
		this.setId(id);
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	
	public JsonMsgBean(Integer id, int code, String msg, Exception e){
		this.setId(id);
		this.code = code;
		this.msg = msg;
		this.exception = getStackTraceString(e);						
	}
	
	
	public JsonMsgBean(Object obj,Object obj1) {
		// TODO Auto-generated constructor stub
		this.obj = obj;
		this.obj1 = obj1;
	}
	
	public JsonMsgBean(Object obj,Object obj1 ,int code) {
		// TODO Auto-generated constructor stub
		this.obj = obj;
		this.obj1 = obj1;
		this.code = code;
	}
	
	public JsonMsgBean(Object obj,Object obj1 ,int code , int flag) {
		// TODO Auto-generated constructor stub
		this.obj = obj;
		this.obj1 = obj1;
		this.code = code;
		this.flag = flag;
	}

	
	public JsonMsgBean(Object obj,Object obj1,Object obj2) {
		this.obj = obj;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	public JsonMsgBean(Object obj,Object obj1,Object obj2,int code,String activityStatu) {
		this.obj = obj;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.code = code;
		this.activityStatu = activityStatu;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public void setException(Exception exception) {
		this.exception = getStackTraceString(exception);
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public Object getObj1() {
		return obj1;
	}

	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}
	
	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}
	

	public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivityStatu() {
		return activityStatu;
	}

	public void setActivityStatu(String activityStatu) {
		this.activityStatu = activityStatu;
	}

}

