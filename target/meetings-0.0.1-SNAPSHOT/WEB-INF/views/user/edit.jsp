<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  <form class="layui-form" action="" lay-verify="addmeeting" >
  <div style="margin-left:150px;margin-top:30px">
  <div class="layui-form-item">
    <label class="layui-form-label">用户名称</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text"  id="username" lay-verify="title" autocomplete="off"
        class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">邮箱</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text" id="email" lay-verify="title" autocomplete="off"
        placeholder="请输入邮箱" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">手机号</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text"  id="phone" lay-verify="title" autocomplete="off"
        placeholder="请输入手机号" class="layui-input">
    </div>
  </div>
  
  
    
    <div class="layui-form-item">
      <label class="layui-form-label">所属部门</label>
      <div class="layui-input-inline" style="width:200px" id="departdiv">
        <select id="depart" lay-verify="required" lay-filter="depart" lay-search="">
           <option value="">直接选择或搜索选择</option>
          <c:forEach var="list" items="${departlist }">
              <option value='${list.id}'>${list.name}</option>
           </c:forEach>
        </select>
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label">所在职位</label>
      <div class="layui-input-inline" style="width:200px" id="positiondiv">
        <select id="position" lay-verify="required" lay-filter="position" lay-search="">
           <option value="">直接选择或搜索选择</option>
          <c:forEach var="list" items="${rolelist }">
              <option value='${list.id}'>${list.name}</option>
           </c:forEach>
        </select>
      </div>
    </div>
  
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" type="button" id="submit">立即提交</button>
      <button class="layui-btn layui-btn-primary" type="button"  id="close">关闭</button>
    </div>
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
  form.on('select(depart)', function (data) {
      departid = data.value;
  });
  
  form.on('select(position)', function (data) {
	  positionid = data.value;
  });
  $("#submit").on('click',function(){
		if ($("#email").val().length == 0) {
			alert("请输入邮箱");
			$("#email").focus();

			return false;
		}
		if($("#email").val().length != 0){
			var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
			if(!reg.test($("#email").val())){
				alert("请输入正确的邮箱");
				$("#email").focus();

				return false;
			}
		}
		if ($("#phone").val().length == 0) {
			alert("请输入手机号");
			$("#phone").focus();

			return false;
		}
		if($("#phone").val().length != 0){
			var myreg=/^[1][3,4,5,7,8,9][0-9]{9}$/;
            if (!myreg.test($("#phone").val())) {
            	alert("请输入正确的手机号");
				$("#phone").focus();
				return false;
            }
		}
		
		if (departid.length==0) {
			alert("请选择所属部门");

			return false;
		}
		
		if (positionid.length==0) {
			alert("请选择所在职位");
			//$("#title").focus();

			return false;
		}
	   var paramlist={};
	   paramlist.username=$("#username").val();
	   paramlist.departid=departid;
	   paramlist.email=$("#email").val();
	   paramlist.phone=$("#phone").val();
	   $.ajax({
		   url:"${ctx}/user/updateuser.do?roleid="+positionid,
		   type:"post",
		   data:paramlist,
		   async:true,
		   success:function(data){
			   if(data==0){
				   layer.alert('修改成功',function(index){
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
  $("#close").on('click',function(){
	  parent.layer.closeAll();
  });
});  
</script>
</body>
</html>