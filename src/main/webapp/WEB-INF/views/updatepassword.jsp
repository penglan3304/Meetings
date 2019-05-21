<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>会议签到系统</title>
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
</head>
<body>
<form class="layui-form" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">原密码</label>
    <div class="layui-input-block">
      <input type="password"  id="orign" lay-verify="title" autocomplete="off"  class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">新密码</label>
    <div class="layui-input-block">
      <input type="password" id="new" lay-verify="title" autocomplete="off"  class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">确认密码</label>
    <div class="layui-input-block">
      <input type="password"  id="ask" lay-verify="title" autocomplete="off"  class="layui-input">
    </div>
  </div>
   <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" type="button" id="submit">确定</button>
      <button class="layui-btn layui-btn-primary" type="button"  id="close">关闭</button>
    </div>
  </div>
</form>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['laydate','form','layer'], function(){
	  var laydate = layui.laydate;
	  var form = layui.form;
	  var layer = layui.layer;
$("#submit").on('click',function(){
	var password=${user.password};
	var id=${user.id};
	if ($("#orign").val().length == 0) {
		alert("请输入原密码");
		$("#orign").focus();
		return false;
	}
	if ($("#new").val().length == 0) {
		alert("请输入新密码");
		$("#new").focus();
		return false;
	}
	if ($("#ask").val().length == 0) {
		alert("请确认新密码");
		$("#ask").focus();
		return false;
	}
	if($("#orign").val()!=password){
		alert("原密码输入错误");
		$("#orign").focus();
		return false;
	}
	if($("#ask").val()!=$("#new").val()){
		alert("密码确认时错误");
		$("#new").focus();
		return false;
	}
	var paramlist={};
	paramlist.password=$("#new").val();
	$.ajax({
		   url:"${ctx}/user/updateusers.do",
		   type:"post",
		   data:paramlist,
		   async:true,
		    success:function(data){
			   if(data==0){
				   layer.alert('修改成功,请重新登录',function(index){
					   window.parent.location.reload();
					   layer.closeAll();
				   });
			   }
		   },
		   error:function(){
			   layer.alert('修改出错',function(index){
				   layer.closeAll();
			   });
		   } 
	   })
	   
});
});
</script>
</body>
</html>