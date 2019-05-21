<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>

<link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css"/>

<script src="${ctx}/js/jquery.min.js"></script>

<img id="bg" src="<%=request.getContextPath()%>/img/bg5.jpg"  style="position: absolute; z-index: -1" /> 
<div id="login" style="overflow:visible;">
<div style="background:#FFF;width:1920px;height:80px">
   <span style="letter-spacing:20px;font-style:oblique;padding-top:30px;font-size: 30px;"><center><strong>会议签到系统</strong></center></span>
   <span style="font-size:20px;"><center><strong>Conference attendance system</strong></center></span>
</div>

<div style="background:#FFFFFF;width:430px;height:440px;border:3px solid #FCC;margin-left: 1400px;margin-top:250px">
    <div style="background:#FCC;height:70px;">
            <span style="letter-spacing:10px;font-size:30px;"><center><i class="fa fa-home fa-2x" style="color:#0da2f0;margin-top:5px"></i><strong>管理平台</strong></center></span>
    </div>
         
          <div id="type1" class="container" style="width: 400px;height:200px;padding-left: 0px;padding-right: 0px;margin-top: 40px;margin-left: 0px">
	     	<form:form  cssClass="form-horizontal " role="form" method="post" action="${ctx}/login/authen.do"   onsubmit="return validator1(this)">
		  <span style="font-size: 18px;margin-left:35px"><strong>账号密码登录</strong></span>
		  <input type="hidden" value=1  name="way" style="width:10px"/>
		  <a href="javascript:void(0);" onclick ="switch2()" style=" text-decoration:none; outline:none;width:60px;margin-left:120px;"><span style="font-size: 15px"><strong>邮箱快捷登录></strong></span></a>
		  
		   <div  style="width: 353px;padding-left:38px; height: 51px;margin-top: 20px; padding-right:0px;">
		  	<span class="input-group-addon"><i class="fa fa-user fa-2x" style="color:#0da2f0;"></i></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<input id="phone" name="phone" placeholder="请输入手机号"  tabindex="1" style="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;width: 62%;"/>	
		  </div>
		  
		   <div  style="width: 353px;padding-left:38px;height: 51px; margin-top: 5px; padding-right:0px">
		  	<span class="input-group-addon"><i class="fa fa-lock fa-2x" style="color:#0da2f0;"></i></span>
  			<span style="font-size: 23px;margin-left: 14px;color: #0da2f6" >|</span>
  			<input type="password" id="password" name="password" placeholder="请输入密码"  tabindex="2" style="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;position: relative;width: 82%;"/>	
		  </div >
		  
		  
		   <div  style="width: 353px;padding-left:38px;height: 51px; margin-top:10px; padding-right:0px;">
  			<span class="input-group-addon" ><i class="fa fa-tag fa-2x" style="color:#0da2f0;"></i></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<input name="securitycode" id="securitycode" placeholder="请输入验证码"  tabindex="1" style="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;width: 160px;"/>	
		        <img style="height: 40px; padding:4px 8px;top: -2px;position: relative;width: 120px; float:right;"  src="${ctx}/graphics.do"  id="verifyCodeImage" onclick="changesecuritycode();"> 
		   </div>
		   
		   
		   <div style="width: 353px;padding-left:38px;height: 20px; margin-top:10px; padding-right:0px;"> 
		    
		    <div style="float:left">
		       <input type="checkbox" checked="checked" onclick="remember()" name="rememberMe" id="rememberMes">
		       <label>记住密码</label>
		    </div><%-- 
		    <div style="float:right">
		       <a href="${ctx}/regist.do"  style=" text-decoration:none; outline:none;">注册账号</a>
		    </div> --%>
		    
		   </div>
		   
		 <div  style="width: 353px;margin-top: 20px;padding-left:35px;padding-right:0px;">
		      <button  name="savebtn"  type="submit" class="btn btn-info btn-lg " style="width: 100%;height:50px;color: white;font-family: 微软雅黑;font-size: 20px;background-image: url('/meetings/img/login_submit.png');background-repeat: no-repeat;">登录</button>
		  </div>
		      
		</form:form>
	  </div>   
         
         <div id="type2" class="container" style="width: 400px;height:50px;padding-left: 0px;padding-right: 0px;margin-top: 40px;margin-left: 0px;display:none">
	     	<form:form commandName="user" cssClass="form-horizontal " role="form" method="post" action="${ctx}/login/authen.do"   onsubmit="return validator2(this)">
		  <span style="font-size: 18px;margin-left:35px"><strong>邮箱快捷登录</strong></span>
		  <input type="hidden" value=2  name="way" style="width:10px"/>
		  <a href="javascript:void(0);" onclick ="switch1()" style=" text-decoration:none; outline:none;width:60px;margin-left:120px;"><span style="font-size: 15px"><strong>账号密码登录></strong></span></a>
		  
		   <div  style="width: 353; padding-left:38px; height: 51px;margin-top: 20px; padding-right:0px;">
		  	<span class="input-group-addon"><i class="fa fa-user fa-2x" style="color:#0da2f0;"></i></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<input type="email" id="email" name="email" placeholder="请输入邮箱"  tabindex="1" style="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;width: 62%;"/>	
		  </div>
		  
		   <div  style="width: 353px;padding-left:38px;height: 51px; margin-top:10px; padding-right:0px;">
  			<span class="input-group-addon" ><i class="fa fa-tag fa-2x" style="color:#0da2f0;"></i></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<input name="securitycode1" id="securitycode1" placeholder="请输入验证码"  tabindex="1" style="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;width: 160px;"/>	
		    
		        <input style="border: none; color:#0da2f6;height: 52px; padding:4px 8px;top: -2px;position: relative;width: 120px; float:right;background-color: transparent"
		             type="button" value="获取验证码"  id="randomNum"  />  
		   </div>
		   
		   
		   <div style="width: 353px;padding-left:38px;height: 30px; margin-top:10px; padding-right:0px;"> 
		    
		    <div style="float:left">
		       <input type="checkbox" checked="checked" onclick="remember1()" id="rememberMe" name="rememberMe">
		       <label>记住邮箱</label>
		    </div>
		    <%-- <div style="float:right">
		       <a href="${ctx}/regist.do "  style=" text-decoration:none; outline:none;">注册账号</a>
		    </div> --%>
		    
		   </div>
		   
		 <div  style="width: 353px;margin-top: 20px;padding-left:35px;padding-right:0px;">
		      <button  name="savebtn"  type="submit" class="btn btn-info btn-lg " style="width: 100%;height:50px;color: white;font-family: 微软雅黑;font-size: 20px;;background-image: url('/meetings/img/login_submit.png');background-repeat: no-repeat;">登录</button>
		  </div>
		</form:form>
	  </div>   
	  
	         
     </div>
</div>
<div style="background:#FFF;width:1920px;height:300px;margin-top:80px">
   <br/><span style="letter-spacing:40px;font-style:oblique;font-size:30px;color:#4d6ea6;"><center><strong>会而必议，议而必决；决而必行，行而必果</strong></center></span>
</div>
</body>
<script type="text/javascript">

/* function remember()
{ 
	var remFlag = $("input[id='rememberMe']").is(':checked'); 
	if(remFlag==true)
	{ 
		//如果选中设置remFlag为true 
			$("#rememberMe").val("1");
	}
	else
	{ 
		//如果没选中设置remFlag为false 
		$("#rememberMe").val("0");
	} 
}

function remember1()
{ 
	var remFlag = $("input[id='rememberMes']").is(':checked'); 
	if(remFlag==true)
	{ 
		//如果选中设置remFlag为true 
			$("#rememberMes").val("1");
	}
	else
	{ 
		//如果没选中设置remFlag为false 
		$("#rememberMes").val("0");
	} 
} */
function validator1(){
	var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
	if ($("#phone").val().length == 0 || $("#phone").val().length != 11||!(myreg.test($("#phone").val()))) {
		alert("请输入合法的用户名");
		$("#phone").focus();

		return false;
	}
	if ($("#password").val().length == 0) {
		alert("请输入密码");
		$("#password").focus();

		return false;
	}

 	if ($("#securitycode").val().length == 0) {
		alert("请输入验证码");
		$("#securitycode").focus();

		return false;
	}

	return true;
}

function validator2() {     //登录验证 
	var reg=/^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
	if($("#email").val().length == 0
			/*||!reg.test($("#phonenum").val())*/ ){
		alert("请输入合法的邮箱 ");
		$("#email").focus();

		return false;
	}
 	if ($("#securitycode1").val().length == 0) {
		alert("请输入验证码");
		$("#securitycode1").focus();

		return false;
	}
 	return true;
}
function switch1()   //短信转密码 
{
	document.getElementById("type2").style.display = 'none';
	document.getElementById("type1").style.display = 'block';
}
function switch2()   //密码转短信
{
   document.getElementById("type1").style.display = 'none';
   document.getElementById("type2").style.display = 'block';
}


//获取图形验证码 
function getSecurityCode() {
	$("#verifyCodeImage").get(0).src = '${ctx}'+'/graphics.do?' + Math.random();
	if ($("#phone").val().length == 0)
		$("#phone").focus();
	else if ($("#password").val().length == 0)
		$("#password").focus();
	else
		$("#securitycode").focus();
}
 //刷新生成验证码 
$("#changesecuritycode").click(function() {
	getSecurityCode();
});

//点击图片生成验证码 
$("#securitycode ~ img").click(function() {
	getSecurityCode();
});
 
 
$("#randomNum").click(function() {
	var reg=/^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
	if($("#email").val().length == 0||!reg.test($("#email").val()) ){
		alert("请输入合法的邮箱 ");
		$("#email").focus();

		return false;
	}
	else{
		var email=$("#email").val();
		var flag=0;
		$.ajax({
			type : "POST",
			url : "${ctx }/randomNum.do",
			data :
			{
				email:email,
				flag:flag
			},
			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
			dataType : "json",
			success : function(info) {
				if (info.code == 1) {
					var obj = $("#randomNum");
    			    settime(obj);
    			    $("#securitycode1").focus();
				} else {
					alert(info.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status + textStatus);
			}

		});
	}
});
var countdown=60;
function settime(obj) { //发送验证码60秒倒计时 
    if (countdown == 0) { 
        obj.attr('disabled',false); 
        //obj.removeattr("disabled"); 
        obj.val("获取验证码");
        countdown = 60; 
        return;
    } else { 
        obj.attr('disabled',true);
        obj.val("重新发送(" + countdown + ")");
        countdown--; 
    } 
    setTimeout(function(){ 
    	settime(obj)
    },1000) 
  }

</script>
<script type="text/javascript">	    
		$(document).ready(function() {
			var registResult = "${empty registResult ? param.registResult : registResult}";
			//var registResult="${registResult}";
			if(registResult!=""){
				alert(registResult);
				}
			
		/*	var localStorge="${localStorge}";
			if(localStorge=="2")
			{
				document.getElementById("type1").style.display = 'none';
	        	document.getElementById("type2").style.display = 'block';
			}
			if(localStorge=="1")
			{
				
				document.getElementById("type2").style.display = 'none';
	        	document.getElementById("type1").style.display = 'block';
			}*/
			var str=getCookie("loginInfo");
			str = str.substring(1,str.length-1);
			var phone = str.split(",")[0]; 
			
			var password = str.split(",")[1]; 
			//自动填充账号和密码
			$("#phone").val(phone);
			$("#password").val(password);
			
			var strs=getCookie("loginInfos");
			strs = strs.substring(1,str.length);
			//自动填充手机号 
			$("#email").val(strs);
			if(str!=null && str!="")
			{ 
				$("input[type='checkbox']").attr("checked", true); 
			}
			
		});
		//获取cookie
		function getCookie(cname) {
		    var name = cname + "=";
		    var ca = document.cookie.split(';');
		    for(var i=0; i<ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0)==' ') c = c.substring(1);
		        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
		    }
		    return "";
		}
</script>
