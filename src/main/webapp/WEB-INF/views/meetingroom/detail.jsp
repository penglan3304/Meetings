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
<style>
   .layui-input-block input{
     border:none;
     color:#999999 !important;
   }
</style>
</head>
<body>
<form class="layui-form" action="" style="margin-top:20px">
  <div class="layui-form-item">
    <label class="layui-form-label">会议室名称</label>
    <div class="layui-input-block">
      <input type="text"  id="meetingroomname" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">会议室状态</label>
    <div class="layui-input-block">
      <input type="text"  id="state" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
   <div class="layui-form-item">
    <label class="layui-form-label">创建时间</label>
    <div class="layui-input-block">
      <input type="text" id="time" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">创建人</label>
    <div class="layui-input-block">
      <input type="text"  id="createname" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>

</form>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
  
</script>
</body>
</html>