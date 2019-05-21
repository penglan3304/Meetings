<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
 <div style="width:100%;height:46px;background-color: #f2f2f2;">
<div style="margin-bottom: 16px;">
	<button class='layui-btn layui-btn-ks'  id="del">删除</button>
	<button class='layui-btn layui-btn-ks'  id="read">标记为已读</button>
 </div>
</div>
<div class="" style="width:100%;margin-left:5px;float: left;">
	<table class="layui-hide" style="margin:0px 0" id="notifylist"></table>
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
       {{#  if(d.state==='审核不通过'){ }}
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
       {{#  }  }}
         {{#  if(d.state==='审核不通过'||d.state==='已结束'||d.state==='未开始'){ }}
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">取消会议</a>
 

</script>
<script>
    layui.use(['tree','table','layer','form','upload'], function(){
        var $ = layui.$;
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;
        var upload = layui.upload;

       table.render({
            elem: '#notifylist'
            ,url:'/meetings/meeting/notifydetail.do'
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
                    ,{field:'isanother', title:'是否允许旁听',width:150}
                ]
            ]
        }); 
  
       
    $("#del").on('click',function(){
    	  var checkStatus=table.checkStatus('notifylist');
    	  var data=checkStatus.data;
    	  var id="";
    	  for(var i=0;i<data.length;i++){
    		id+=data[i].id;
    		if(i<data.length-1){
    			id+=",";
    		}
    	   }
    	  if(data.length!=0){
    		  layer.confirm('是否删除该条会议记录', function(index){
      	    	$.ajax({
              		url: "${ctx}/meeting/notifydel.do?ids="+id,
              		type: "POST",
              		dataType: "json",
             			success: function(data){            
                  		if(data==0){
                 			layer.close(layer.index);
                 			window.parent.location.reload();
                  		}else{    
                  			layer.msg("删除失败", {icon: 5}); 
                 			 }
              		},
              		error:function(){
              			layer.msg("删除失败", {icon: 5}); 
              		}

          		});
      	      
      	    });
    	  }
    	  else{
    		 layer.alert("请选中一项消息记录进行操作");
    	  }
        });

    $("#read").on('click',function(){
  	  var checkStatus=table.checkStatus('notifylist');
  	  var data=checkStatus.data;
  	  var id="";
  	  for(var i=0;i<data.length;i++){
  		id+=data[i].id;
  		if(i<data.length-1){
  			id+=",";
  		}
  	   }
  	  if(data.length!=0){
  		  layer.confirm('是否标记为已读', function(index){
    	    	$.ajax({
            		url: "${ctx}/meeting/read.do?ids="+id,
            		type: "POST",
            		dataType: "json",
           			success: function(data){            
                		if(data==0){
                			layer.close(layer.index);
                 			window.parent.location.reload();
                		}else{    
                			layer.msg("标记失败", {icon: 5}); 
               			 }
            		},
            		error:function(){
            			layer.msg("标记失败", {icon: 5}); 
            		}

        		});
    	      
    	    });
  	  }
  	  else{
  		  
  		 layer.alert("请选中一项消息记录进行操作");
  	  }
      });

    });
 </script>