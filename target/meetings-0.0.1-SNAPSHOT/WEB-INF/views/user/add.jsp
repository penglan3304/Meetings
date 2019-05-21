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
  <form class="layui-form" action=""  onsubmit="return validator(this)" >
  <div style="margin-left:150px;margin-top:30px">
  <div class="layui-form-item">
    <label class="layui-form-label">用户名</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text"  id="name"  autocomplete="off"
        placeholder="请输入用户名" class="layui-input">
    </div>
  </div>
  
<!--   <div class="layui-form-item">
    <label class="layui-form-label">密码</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text"  id="pass"  autocomplete="off"
        placeholder="请输入密码" class="layui-input">
    </div>
  </div> -->
  
  <div class="layui-form-item">
    <label class="layui-form-label">邮箱</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text"  id="mail"  autocomplete="off"
        placeholder="请输入邮箱" class="layui-input">
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">手机号</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text"  id="phone"  autocomplete="off"
        placeholder="请输入手机号" class="layui-input">
    </div>
  </div>
  
    <div class="layui-form-item">
      <label class="layui-form-label">所属部门</label>
      <div class="layui-input-inline" style="width:200px">
        <select id="depart"  lay-filter="depart" lay-search="">
           <option value="">直接选择或搜索选择</option>
          <c:forEach var="list" items="${departlist }">
              <option value='${list.id}'>${list.name}</option>
           </c:forEach>
        </select>
      </div>
    </div>
  
     <div class="layui-form-item">
      <label class="layui-form-label">用户角色</label>
      <div class="layui-input-inline" style="width:200px">
        <select id="roles"  lay-filter="roles" lay-search="">
           <option value="">直接选择或搜索选择</option>
          <c:forEach var="list" items="${rolelist}">
              <option value='${list.id}'>${list.name}</option>
           </c:forEach>
        </select>
      </div>
    </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" type="button" id="submit">立即提交</button>
      <button class="layui-btn layui-btn-primary"  type="button" id="close">关闭</button>
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
  var another="是";
  var placeid="";
  var departid="";
  var roleid="";
  var checkperson="";
  //日期时间选择器
  laydate.render({
    elem: '#starttime'
    ,type: 'datetime'
  });
  
  //日期范围
  laydate.render({
    elem: '#endtime'
    ,type: 'datetime'
  });
  
  form.on('switch(switchTest)', function(data){
        if(this.checked){
        	another="是";
        }
        else{
        	another="否";
        }
  });
  
  form.on('select(depart)', function (data) {
      departid = data.value;
  });
  
  form.on('select(roles)', function (data) {
      roleid = data.value;
  });
  $("#submit").on('click',function(){
	  if ($("#name").val().length == 0) {
			alert("请输入用户名");
			$("#name").focus();

			return false;
		}
		
		/* if ($("#pass").val().length==0) {
			alert("请输入密码");
			$("#pass").focus();

			return false;
		} */
		if ($("#mail").val().length == 0) {
			alert("请输入邮箱");
			$("#mail").focus();

			return false;
		}
		if($("#mail").val().length != 0){
			var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
			if(!reg.test($("#mail").val())){
				alert("请输入正确的邮箱");
				$("#mail").focus();

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
			//$("#title").focus();

			return false;
		}
		
		if (roleid.length==0) {
			alert("请选择所属角色");
			//$("#title").focus();

			return false;
		}
	   var paramlist={};
	   paramlist.username=$("#name").val();
	   paramlist.departid=departid;
	   /* paramlist.password=$("#pass").val(); */
	   paramlist.email=$("#mail").val();
	   paramlist.phone=$("#phone").val();
	   $.ajax({
		   url:"${ctx}/user/adduser.do?roleid="+roleid,
		   type:"post",
		   data:paramlist,
		   async:true,
		   success:function(data){
			   if(data==0){
				   layer.alert('添加成功',function(index){
					   window.parent.location.reload();
					   layer.closeAll();
				   });
			   }
		   },
		   error:function(){
			   layer.alert('添加出错',function(index){
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