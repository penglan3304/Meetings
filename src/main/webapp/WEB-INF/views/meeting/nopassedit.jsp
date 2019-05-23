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
    <label class="layui-form-label">会议名称</label>
    <div class="layui-input-inline" style="width:200px">
      <input type="text" name="title" id="title" lay-verify="title" autocomplete="off"
        placeholder="请输入会议名称" class="layui-input">
    </div>
  </div>
  
 
    
    <div class="layui-form-item">
      <label class="layui-form-label">参会部门</label>
      <div class="layui-input-inline" style="width:200px" id="departdiv">
        <select id="depart" lay-verify="required" lay-filter="depart" lay-search="">
           <option value="">直接选择或搜索选择</option>
          <c:forEach var="list" items="${departlist }">
              <c:choose>
				   <c:when test="${list.id == param.departs }">
				     <option value='${list.id}' selected>${list.name}</option>
				   </c:when>
				   
				   <c:otherwise> 
				      <option value='${list.id}'>${list.name}</option>
				   </c:otherwise>
				</c:choose>
              
           </c:forEach>
        </select>
      </div>
    </div>
   
   <div class="layui-form-item">
    <label class="layui-form-label">是否允许旁听</label>
    <div class="layui-input-inline" style="width:200px">
      <c:choose>
		<c:when test="${meeting.isanother=='是' }">
			<input type="checkbox" id="isanother"  lay-skin="switch" lay-filter="switchTest" lay-text="是|否" checked>
		</c:when>
				   
		<c:otherwise> 
			<input type="checkbox" id="isanother"  lay-skin="switch" lay-filter="switchTest" lay-text="是|否">
		</c:otherwise>
	  </c:choose>
      
    </div>
  </div>
  
 
  
  <div class="layui-form-item">
    <label class="layui-form-label">会议时间</label>
      <div class="layui-input-inline" style="width:200px">
         <input type="text" class="layui-input" id="starttime" >
      </div>
      
      <div class="layui-input-inline" style="width:5px;line-height:36px">-</div>
 
      <div class="layui-input-inline" style="width:200px">
        <input type="text" class="layui-input" id="endtime" style="margin-right:560px">
      </div>
 </div>
 
 
  <div class="layui-form-item">
      <label class="layui-form-label">会议地点</label>
      <div class="layui-input-inline" style="width:200px" id="placediv">
        <select  id="place"  lay-verify="required" lay-filter="place" lay-search="">
          <option value="">直接选择或搜索选择</option>
           <c:forEach var="list" items="${meetingroomlist }">
                <c:if test="${list.id == meeting.meetingroomid }">
				     <option value='${list.id}' selected>${list.name}</option>
				</c:if>
				   
              
           </c:forEach>
        </select>
      </div>
    </div>
  
 <div class="layui-form-item">
    <label class="layui-form-label">会议审核人</label>
    <div class="layui-input-block" style="width:200px">
      <input type="text" name="title" id="checkperson" lay-verify="title" autocomplete="off"  class="layui-input" readonly>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" type="button" id="submit">立即提交</button>
      <button class="layui-btn layui-btn-primary" id="close">关闭</button>
    </div>
  </div>
 </div> 
</form>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['laydate','form','layer'], function(){
	var meetingroomid=${meeting.meetingroomid};
  var laydate = layui.laydate;
  var form = layui.form;
  var layer = layui.layer;
  var another="是";
  var placeid=meetingroomid;
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
    	,done: function(value, date, endDate){
        	var starttime=$("#starttime").val();
        	var endtime=value;
        	 $.ajax({
                 url: "${ctx}/meetingroom/selectmeeting.do?starttime="+starttime+"&endtime="+endtime,
                 type: "post",
                 async: false,
                 success: function(data) {
                     var jsonData=data;
                     var count = jsonData.count;
                     if (count != 0) {
                         var empList = jsonData.data;
                         var selectEmpHTML = "";
                         $.each(empList,function(index,value){
                             var name = value.name;
                             var optionHTML = "<option value='" + value.id + "'>" + name + "</option>";
                             selectEmpHTML = selectEmpHTML + optionHTML;
                         });
                         $("#place").append(selectEmpHTML);
                     } 
                     else{
                    	 layer.alert("无可用会议室"); 
                     } 
                     form.render('select');   
                     }
             });
        	  
        }
  });
  

  form.on('switch(switchTest)', function(data){
        if(this.checked){
        	another="是";
        }
        else{
        	another="否";
        }
  });
  
  form.on('select(place)', function (data) {
		  placeid = data.value;
	 
  });
  form.on('select(depart)', function (data) {
      departid = data.value;
  });
  form.on('select(checkperson)', function (data) {
      checkperson = data.value;
  });
  $("#submit").on('click',function(){
	  if ($("#title").val().length == 0) {
			alert("请输入会议标题");
			$("#title").focus();

			return false;
		}
		
		if (placeid.length==0) {
			alert("请选择会议地点");
			$("#title").focus();

			return false;
		}
		
		if (departid.length==0) {
			alert("请选择参会部门");
			$("#title").focus();

			return false;
		}
		
		
		if ($("#starttime").val().length == 0) {
			alert("请选择开始时间");
			$("#starttime").focus();

			return false;
		}
		
		if ($("#endtime").val().length == 0) {
			alert("请选择结束时间");
			$("#endtime").focus();

			return false;
		}
		if(new Date($("#starttime").val())<new Date()){
			alert("开始时间不能早于当前时间")
			return false;
		}
		
		if(new Date($("#starttime").val())>=new Date($("#endtime").val())){
			alert("开始时间不能晚于结束时间")
			return false;
		}
	   var paramlist={};
	   paramlist.name=$("#title").val();
	   paramlist.meetingroomid=placeid;
	   paramlist.departs=departid;
	   paramlist.isanother=another;
	   paramlist.starttime=$("#starttime").val();
	   paramlist.endtime=$("#endtime").val();
	   paramlist.state="待审核";
	   paramlist.reason="";
	   paramlist.id=${id};
	   $.ajax({
		   url:"${ctx}/meeting/updatemeetings.do",
		   type:"post",
		   data:paramlist,
		   async:true,
		   success:function(data){
			   if(data==0){
				   parent.layer.alert('修改成功',function(index){
					   location.reload();
					   parent.layer.closeAll();
				   });
			   }
			   else{
				   parent.layer.alert('修改出错',function(index){
					   location.reload();
					   parent.layer.closeAll();
				   }); 
			   }
		   },
		   error:function(){
			   parent.layer.alert('修改出错',function(index){
				   location.reload();
				   parent.layer.closeAll();
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