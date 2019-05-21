<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
  <form class="layui-form" action=""  onsubmit="return validator(this)" >
  <div style="margin-left:150px;margin-top:30px">
	  <div class="layui-form-item">
      <label class="layui-form-label">姓名</label>
      <div class="layui-input-inline" style="width:200px">
        <select id="userlist"  lay-filter="userlist" lay-search="">
           <option value="">直接选择或搜索选择</option>
          <c:forEach var="list" items="${userlist }">
              <option value='${list.id}'>${list.username}</option>
           </c:forEach>
        </select>
      </div>
    </div>
    
    <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">收消息</label>
    <div class="layui-input-block" style="width:200px">
      <textarea name="desc" id="chatinfo_" placeholder="请输入内容" class="layui-textarea"></textarea>
    </div>
  </div>
  
   <div class="layui-form-item">
    <label class="layui-form-label">发送消息</label>
    <div class="layui-input-block" style="width:200px">
      <input type="text" name="title" id="msgs" required  lay-verify="required"  autocomplete="off" class="layui-input">
       
    </div>
  </div>
  
  
    </div>
  	</form>
<button class="layui-btn" type="button" id="submit">发送</button>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['laydate','form','layer'], function(){
	  var laydate = layui.laydate;
	  var form = layui.form;
	  var userid="";
	  
	  form.on('select(userlist)', function (data) {
		  userid = data.value;
	  });
	  $("#submit").on('click',function(){
		  var msg=$("#msgs").val();
			if(userid.length==0){
				alert("请选择接收方");
				return false;
			}
			if($("#msgs").val().length == 0){
				alert("请输入信息");
				return false;
			}
			 $.ajax({
				   url:"${ctx}/user/chatptp.do?topic="+userid+"&msg="+msg,
				   type:"post",
				   async:true,
				   success:function(data){
						  $("#chatinfo_").val(msg+data);
				   },
				   error:function(){
					   layer.alert('添加出错',function(index){
						   layer.closeAll();
					   });
				   }
			   })
	  });
});

</script>