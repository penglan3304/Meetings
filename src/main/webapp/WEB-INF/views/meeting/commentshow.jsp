<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
<div class="" style="width:100%;margin-left:5px;float: left;">
	<table class="layui-hide" style="margin:0px 0" id="meetinglist" lay-filter="meeting"></table>
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
        <a class="layui-btn layui-btn-xs" lay-event="detail">评论详情</a>
      </script>
<script>
    layui.use(['tree','table','layer','form','upload'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var upload = layui.upload;
       table.render({
            elem: '#meetinglist'
            ,url:'/meetings/meeting/comment.do'
            ,title: '会议列表'
            ,height: 550
            ,method:'POST'
            ,cols: [
                [
                	{field:'name', title:'会议名称',height:38}
                    ,{field:'meetingroom', title:'会议室',height:38}
                    ,{field:'starttime', title:'开始时间',height:38}
                    ,{field:'endtime', title:'结束时间',height:38}
                    ,{field:'createtime', title:'创建时间',height:38}
                    ,{field:'modifytime', title:'修改时间',height:38}
                    ,{field:'state', title:'状态 ',height:38}
                    ,{field:'createname', title:'创建人',height:38}
                    ,{field:'isanother', title:'是否允许旁听',height:38}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo',width:230}
                ]
            ]
            ,page: true
        });   
        table.on('tool(meeting)', function(obj){ 
        	  var data = obj.data; 
        	  var layEvent = obj.event; 
        	  if(layEvent === 'detail'){ 
        		  layer.open({
           			  type : 2,
           		      title : "评论详情",
           		      area: ['800px', '600px'],
           		      btn: ['关闭'],
           		      content : "${ctx}/meeting/commentdetail.do?id="+data.id,
	           		    success: function(layero, index){
		   		    	    var body = layer.getChildFrame('body', index);
		   		    	    body.find('#meetingname').val(data.name);
		   		    	    body.find('#createname').val(data.createname);
		   		    	 
		   		    	    /* body.find('#meetingroom').val(data.meetingroom);
		   		    	    body.find('#starttime').val(data.starttime);
		   		    	    body.find('#endtime').val(data.endtime);
		   		    	    body.find('#state').val(data.state);
		   		    	    body.find('#checkperson').val(data.checkperson);
		   		    	    body.find('#departname').val(data.departname); */
	   		    	  }
           		  });
        	  } 
       
        });
    });
 </script>