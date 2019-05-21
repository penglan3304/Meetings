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
    <label class="layui-form-label">会议名称</label>
    <div class="layui-input-block">
      <input type="text" name="meetingname" id="meetingname" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">会议地点</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="meetingroom" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">参会部门</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="departname" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">开始时间</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="starttime" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">结束时间</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="endtime" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">会议状态</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="state" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">不通过原因</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="reason" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <label class="layui-form-label">会议审核人</label>
    <div class="layui-input-block">
      <input type="text" name="title" id="checkperson" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
</form>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
  
</script>
</body>
</html>