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
  <form class="layui-form" action=""   onsubmit="return validator(this)" >
  <div style="margin-left:150px;margin-top:30px">
  <div class="layui-form-item">
    <label class="layui-form-label">会议室名称</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text" name="title" id="title" lay-verify="title" autocomplete="off"
        placeholder="请输入会议名称" class="layui-input">
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
  $("#submit").on('click',function(){
	  if ($("#title").val().length == 0) {
			alert("请输入会议名称");
			$("#title").focus();

			return false;
		}
	   var paramlist={};
	   paramlist.name=$("#title").val();
	   $.ajax({
		   url:"${ctx}/meetingroom/addmeetingroom.do",
		   type:"post",
		   data:paramlist,
		   async:true,
		   success:function(data){
			   if(data==0){
				   layer.alert('添加成功',function(index){
					   window.parent.location.reload();
					   layer.close(index);
				   });
			   }
		   },
		   error:function(){
			   layer.alert('添加出错',function(index){
				   layer.close(index);
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