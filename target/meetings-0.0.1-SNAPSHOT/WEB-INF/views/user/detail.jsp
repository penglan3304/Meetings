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
<form class="layui-form" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">用户名</label>
    <div class="layui-input-block">
      <input type="text"  id="name" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">邮箱</label>
    <div class="layui-input-block">
      <input type="text" id="email" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">手机号</label>
    <div class="layui-input-block">
      <input type="text"  id="phone" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">所属部门</label>
    <div class="layui-input-block">
      <input type="text"  id="depart" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">职位</label>
    <div class="layui-input-block">
      <input type="text"  id="position" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
</form>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
  
</script>
</body>
</html>