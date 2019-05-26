<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<!DOCTYPE html>
<html>
<head>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<title>找回密码</title>
</head>
<body>
     <div style="background:#FFF;width:1920px;height:80px">
     <span style="letter-spacing:20px;font-style:oblique;padding-top:30px;font-size: 30px;"><center><strong>会议签到系统</strong></center></span>
     <span style="font-size:20px;"><center><strong>Conference attendance system</strong></center></span>
   </div>
    <div style="width: 400px;height: 300px;margin:0px auto;border:1px solid ">
          <div>
          <span style="letter-spacing:20px;font-style:oblique;padding-top:30px;font-size: 25px;">
          <center><strong>找回密码</strong></center>
          </span>
          </div>
     <form onsubmit="return validator(this)" commandName="user" method="post" action="${ctx}/findpwd.do">
           <div style="width: 353px;padding-left:38px;height: 40px; margin-top:40px; padding-right:0px">
               <label style="width:20px">邮箱：</label>
               <input type="email" name="email" id="email" placeholder="请输入邮箱"  style="padding:6px 12px;margin-left:18px;width:230px"  />
          </div>
          
           <div style="width: 353px;padding-left:38px;height: 40px; margin-top:10px; padding-right:0px">
               <label style="width:20px">验证码：</label>
               <input name="securitycode1" id="securitycode1" placeholder="请输入验证码"  style="padding:6px 12px;position: relative;margin-left:5px;width:140px"/>	
		    
		        <input style="border: none; color:#0da2f6;height: 40px; padding:4px 8px;top: -2px;position: relative; background-color: transparent"
		             type="button" value="获取验证码"  id="randomNum"  />  
          </div>
          
           <div style="margin-top:20px"> <a href="${ctx }/home.do"  style=" text-decoration:none; outline:none;margin-left:230px;height:30px">已有账号,直接登录</a>  </div>
          
           <div  style="width: 353px;margin-top: 20px;padding-left:35px;padding-right:0px;">
		      <button id="save" name="save"  type="submit" class="btn btn-info btn-lg " style="width: 100%;height:50px;color: white;font-family: 微软雅黑;font-size: 20px;;background-image: url('${ctx}/img/login_submit.png');background-repeat: no-repeat;">确定</button>
		  </div>
     </form> 
   </div>  
</body>
<script >


function validator() {     //登录验证 
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
			var registResult="${registResult}";
			if(registResult!=""){
				alert(registResult);
				}
		});
</script>
</html>