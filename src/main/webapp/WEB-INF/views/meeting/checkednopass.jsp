<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
 
 <div class="layui-card">
  <div class="layui-card-header" style="font-size:20px;color:#54b5ff">审核不通过</div>
  <div class="layui-card-body">
      <div style="margin-bottom: 16px;">
     <div class="layui-inline" >
    <form class="layui-form" lay-filter="attendanceform">
	  <div class="layui-inline" >
      	<label class="layui-form-label" style="padding:8px 15px;line-height:30px;">开始时间</label>
      	<div class="layui-input-inline"style="margin-top: 8px">
        	<input type="text" style="height: 30px;line-height: 30px;" name="startdate" id="startdate" lay-verify="required" class="layui-input">
      	</div>
      </div>
      <div class="layui-inline" >
      	<label class="layui-form-label" style="padding:8px 15px;line-height:30px;">结束时间</label>
      	<div class="layui-input-inline"style="margin-top: 8px">
        	<input type="text" style="height: 30px;line-height: 30px;" name="enddate" id="enddate" lay-verify="required" class="layui-input">
      	</div>
      </div>
  	</form>
  	</div>
	  <button class='layui-btn layui-btn-ks'  id="doSearch">搜索</button>
	</div>

	   <table  style="margin:0px 0" id="checknopasslist" lay-filter="checknopasslist"></table>


  </div>
</div>

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['laydate','form'], function(){
	  var laydate = layui.laydate;
	  var form = layui.form;
	  
	  
	//执行一个laydate实例
    laydate.render({
      elem: '#startdate' //指定元素
      ,type: 'datetime'
    });
    laydate.render({
        elem: '#enddate' //指定元素
        ,type: 'datetime'
    });
	  
  
	  
	});
</script>
<script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
        <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
 </script>
<script>
    layui.use(['tree','table','layer','form','upload'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var upload = layui.upload;
       
      //获取系统当前时间
        function getNowFormatDate(num) {
            var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = "";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            var hour = date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            if (hour >= 0 && hour <= 9) {
            	hour = "0" + hour;
            }
            if (minutes >= 0 && minutes <= 9) {
            	minutes = "0" + minutes;
            }
            if (seconds >= 0 && seconds <= 9) {
            	seconds = "0" + seconds;
            }
            
            var currentdate ="";
            if(num!=null){
            	//获取起始日期
            	currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + "00" + seperator2 + "00" + seperator2 + "00";
            }else{
            	currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + hour + seperator2 + minutes
                + seperator2 + seconds;
            }
            
            return currentdate;
        }
        var endTime = getNowFormatDate();
        var startTime =getNowFormatDate("start");
        
      	//表单初始赋值
        form.val('attendanceform', {
          "startdate": startTime
          ,"enddate": endTime 
        })
        
       table.render({
            elem: '#checknopasslist'
            ,url:'/meetings/meeting/checkednopass.do'
            ,title: '会议列表'
            ,height: 550
            ,method:'POST'
            ,cols: [
                [
                	{checkbox: true}
                    ,{field:'name', title:'会议名称',height:38}
                    ,{field:'meetingroom', title:'会议室',height:38}
                    ,{field:'starttime', title:'开始时间',height:38}
                    ,{field:'endtime', title:'结束时间',height:38}
                    ,{field:'createtime', title:'创建时间',height:38}
                    ,{field:'modifytime', title:'修改时间',height:38}
                    ,{field:'state', title:'状态 ',height:38}
                    ,{field:'createname', title:'创建人',height:38}
                    ,{field:'reason', title:'未通过原因',height:38}
                    ,{field:'isanother', title:'是否允许旁听',height:38}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo',width:120}
                ]
            ]
        });   
        table.on('tool(checknopasslist)', function(obj){ 
        	  var data = obj.data; 
        	  var layEvent = obj.event; 
        	  if(layEvent === 'detail'){ 
        		  $.ajax({
              		url: "${ctx}/meeting/detail.do?id="+data.id,
              		type: "POST",
              		dataType: "json",
             		success: function(data){ 
             				 layer.open({
                   			  type : 2,
                   		      title : "会议详情",
                   		      area: ['600px', '400px'],
                   		      btn: ['关闭'],
                   		      content : '${ctx}/meeting/nopassshow.do',
                   		      yes: function(){   
                   		    	  layer.closeAll();
                   		      },
                   		      success: function(layero, index){
                   		    	    var body = layer.getChildFrame('body', index);
                   		    	    body.find('#meetingname').val(data.name);
                   		    	    body.find('#meetingroom').val(data.meetingroom);
                   		    	    body.find('#starttime').val(data.starttime);
                   		    	    body.find('#endtime').val(data.endtime);
                   		    	    body.find('#state').val(data.state);
                   		    	    body.find('#reason').val(data.reason);
                   		    	    body.find('#checkperson').val(data.checkperson);
                   		    	    body.find('#departname').val(data.departname);
                   		    	  }
                   		  });
             			}
        		  });
        		 
        	  } 
        else if(layEvent === 'edit'){ //修改未通过会议
        	    	$.ajax({
                		url: "${ctx}/meeting/detail.do?id="+data.id,
                		type: "POST",
                		dataType: "json",
                		success: function(data){ 
            				 layer.open({
                  			  type : 2,
                  		      title : "会议详情",
                  		      area: ['800px', '600px'],
                  		      content : '${ctx}/meeting/nopassedit.do?meetingroomid='+data.meetingroomid+'&meetingroom='+data.meetingroom
                  		    		                     +'&id='+data.id,
                  		      success: function(layero, index){
                  		    	    var body = layer.getChildFrame('body', index);
                  		    	    body.find('#title').val(data.name);
                  		    	    body.find('#starttime').val(data.starttime);
                  		    	    body.find('#endtime').val(data.endtime);
                  		    	    body.find('#checkperson').val(data.checkperson);
                  		    	   if(data.isanother=="是"){
                  		    		 body.find('#isanother').prop("checked", true);
                  		    	   }else{
                  		    		 body.find('#isanother').prop("checked", false);
                  		    	   }
                  		    	  
                  		    	    
                  		    	    body.find('#departdiv').find("[lay-value='" +data.departs + "']").click();
                  		    	    body.find('#placediv').find("[lay-value='" +data.meetingroomid + "']").click();
                  		    	  
                  		    	  }
                  		  });
            			}
 
            		});
        	  } 
     
        });
        
        $('#doSearch').on('click',function(){
        	var starttime=$('#startdate').val();
        	var endtime=$('#enddate').val();
        	if(starttime.length==0){
        		alert("请选择开始时间");
        		return false;
        	}
        	if(endtime.length==0){
        		alert("请选择结束时间");
        		return false;
        	}
        	if(new Date(starttime)>new Date(endtime)){
        		alert("开始时间不得晚于结束时间");
        		return false;
        	}
        	table.reload('checknopasslist', {
         		 where: {
         			starttime: starttime,
         			endtime: endtime,
                  }
         	 	, page: {
                  	curr: 1 //重新从第 1 页开始
              	}
              });
        });
    });
 
 </script>