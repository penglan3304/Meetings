<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>
<link rel="stylesheet" href="/meetings/layui/css/layui.css" media="all">
 <div class="layui-carousel" id="test1" >
      <div carousel-item >
           <div style="height:400px;width:1300px;margin-left:280px;margin-top:50px" class="layui-bg-green demo-carousel">
                  <p style="text-align:center;margin-top:150px;font-size:30px">会议实时查询</p>
                  <p style="text-align:center;margin-top:10px;font-size:20px">了解会议的实时信息，合理安排时间</p>
          </div>
          <div style="height:400px;width:1300px;margin-left:280px;background-color: #7CCD7C;margin-top:50px">
                  <p style="text-align:center;margin-top:150px;font-size:30px;color:#FFFFFF">二维码获取</p>
                  <p style="text-align:center;margin-top:10px;font-size:20px;color:#FFFFFF">会议报名成功后会通过QQ邮箱发送二维码，请注意查收</p>
          </div>
          <div style="height:400px;width:1300px;margin-left:280px;background-color:#9ACD32;margin-top:50px" >
                  <p style="text-align:center;margin-top:150px;font-size:30px;color:#FFFFFF">创建会议</p>
                  <p style="text-align:center;margin-top:10px;font-size:20px;color:#FFFFFF">创建会议时注意是否允许旁听，知识交流可以让更多人来捧场哦</p>
          </div>
          <div style="height:400px;width:1300px;margin-left:280px;background-color:#8E8E38;margin-top:50px">
                 <p style="text-align:center;margin-top:150px;font-size:30px;color:#FFFFFF">报表获取</p>
                  <p style="text-align:center;margin-top:10px;font-size:20px;color:#FFFFFF">获取参会人员信息情况，请在会议结束后在会议管理中导出与会人信息</p>
         </div>
         <!--  <div style="height:400px;width:1300px;margin-left:280px" class="layui-bg-cyan demo-carousel">
                <p style="text-align:center;margin-top:150px;font-size:30px">会议实时查询</p>
                  <p style="text-align:center;margin-top:10px;font-size:20px">了解会议的实时信息，合理安排时间</p>
        </div> -->
      </div>
</div>
<div style="margin-left:280px;margin-top:20px;height:200px">
<div style="float:left">
   <i class="layui-icon layui-icon-flag" style="font-size: 120px;color: #1E9FFF;float:left"></i>
   <p style="margin-top:20px;margin-left:150px;font-size:20px"><strong>会议创建须知</strong></p>
   <p style="margin-top:5px">
     1.在会议管理中创建会议，创建时注意选好参会部门<br/>
     2.创建时默认会议可旁听，意味着所有人都可参会
   </p>
</div> 
<div style="float:left;margin-left:140px"> 
   <i class="layui-icon layui-icon-template" style="font-size: 120px; color: #1E9FFF;float:left"></i> 
</div>
<div style="float:left;width:240px">
<p style="margin-top:20px;margin-left:50px;font-size:20px"><strong>会议进行须知</strong></p>
   <p style="margin-top:5px">
      1.签到时需到已审核会议中点击开始签到进行人员签到<br/>
      2.签到完成后点击开始会议，会议正式开始
   </p>
</div>
<div style="float:left;margin-left:120px">
   <i class="layui-icon layui-icon-read" style="font-size: 120px; color: #1E9FFF;float:left"></i> 
</div>
<div style="float:left;width:240px;margin-left:10px">
 <p style="margin-top:20px;margin-left:50px;font-size:20px"><strong>会议结束须知</strong></p>
   <p style="margin-top:5px">
        1.会议结束后点击结束会议，会议结束<br/>
        2.会议管理-->查看人员信息中导出参会人员签到信息
   </p>
</div>
</div>


<div style="border:1px solid #E0E0E0;width:640px;margin-left:280px;float:left;background-color:#54b5ff;height:500px">    
     <div style="margin-bottom: 16px;text-align:center">
        <div class="layui-inline" >
        <p style="font-size:30px"><strong>实时会议信息</strong></p>
          <div id="show" style="font-size:40px"></div>
  	    </div>
    </div>
 <div class="layui-carousel" id="test2" >
      <div carousel-item >
          <c:forEach items="${informations_ }" var="infos">
          <div class="layui-inline" style="float:left;background-color:#54b5ff;width:100%;height:100%"> 
             <div style="text-align:center;font-size:30px"> <label >${infos.name}</label></div>
              <div style="margin-top:10px;margin-left:20%">
              <table border="1" cellpadding="10">
				  <tr>
				    <th>会议名称</th>
				    <th>会议状态</th>
				    <th>主讲人</th>
				    <th>操作</th>
				  </tr>
				   <c:forEach items="${information_ }" var="info" varStatus="s">
		            <c:choose>
		               <c:when test="${infos.id eq info.meetingroomid}">
		                    <tr>
							    <td>${info.name}</td>
							    <%-- <td>
							      <c:choose>
		                             <c:when test="${info.state=='审核通过' }">
		                                 <input type="hidden" id="${s.count }" value="${info.starttime },${info.id}">
		                                                          已开始：<span id="_d${info.id }">00</span>  
										        <span id="_h${info.id }">00</span>  
										        <span id="_m${info.id }">00</span>  
										        <span id="_s${info.id }">00</span> 
		                             </c:when>
		                             <c:otherwise>
		                               ${info.state}
			          			    </c:otherwise>
		                           </c:choose> 
		                         </td> --%>
		                         <td>${info.state}</td>
		                         <td>${info.createname}</td> 
		                         <td><a href="javascript:void(0);" onclick="detail(${info.id})" >查看详情</a></td> 
							</tr>
		               </c:when>
		             </c:choose>
		            </c:forEach>
			</table>
			</div>
          </div>
        </c:forEach>
      </div>
</div>
</div> 
<div style="border:1px solid #E0E0E0;width:640px;margin-left:20px;float:left;background-color:#54b5ff;height:500px">
    <div style="margin-bottom: 16px;text-align:center">
        <div class="layui-inline" >
        <p style="font-size:30px"><strong>实时会议室信息</strong></p>
        <table class="layui-hide"  id="meetingroomlist" lay-filter="meetingroomlist"></table>
  	    </div>
    </div>
</div>    
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['tree','table','layer','form','upload'], function(){
    var $ = layui.$;
    var table = layui.table;
    var layer = layui.layer;
    table.render({
        elem: '#meetingroomlist'
        ,url:'/meetings/meetingroom/meetingroomlist.do'
        ,title: '会议列表'
        ,width:600
        ,method:'POST'
        ,cols: [
            [
            	 {field:'name', title:'会议室名称',height:38}
                ,{field:'state', title:'会议室状态',height:38}
                ,{field:'createname', title:'创建人',height:38}
                ,{field:'createtime', title:'创建时间',height:38}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo',height:38}
            ]
        ]
        ,page: true
    });

function countTime() {  
	 var date = new Date();  
    var now = date.getTime();
	         for(var i=1;i<=${leng_};i++){
	        	 var value=$("#"+i).val();
	        	 if(value){
	        		var strs= value.split(",");
	        	
	        	 var endDate = new Date(strs[0]); 
	             var end = endDate.getTime();
	           //时间差  
	             var leftTime = now-end; 
	             //定义变量 d,h,m,s保存倒计时的时间  
	             var d,h,m,s;  
	             if (leftTime>=0) {  
	                 d = Math.floor(leftTime/1000/60/60/24);  
	                 h = Math.floor(leftTime/1000/60/60%24);  
	                 m = Math.floor(leftTime/1000/60%60);  
	                 s = Math.floor(leftTime/1000%60);                     
	             }  
	             //将倒计时赋值到div中  
	             document.getElementById("_d"+strs[1]).innerHTML = d+"天";  
	             document.getElementById("_h"+strs[1]).innerHTML = h+"时";  
	             document.getElementById("_m"+strs[1]).innerHTML = m+"分";  
	             document.getElementById("_s"+strs[1]).innerHTML = s+"秒";  
	        	 }
	         }
	         //递归每秒调用countTime方法，显示动态时间效果  
            setTimeout(countTime,1000);  
        }  
window.onload = function() {
countTime();
var show = document.getElementById("show");  
setInterval(function() {   
var time = new Date();   // 程序计时的月从0开始取值后+1   
var m = time.getMonth() + 1;   
var t = time.getFullYear() + "-" + m + "-"     
+ time.getDate() + " " + time.getHours() + ":"     
+ time.getMinutes() + ":" + time.getSeconds();   
show.innerHTML = t;  
}, 1000); 
};
function detail(id){
	 $.ajax({
   		url: "${ctx}/meeting/detail.do?id="+id,
   		type: "POST",
   		dataType: "json",
  		success: function(data){ 
  				 layer.open({
        			  type : 2,
        		      title : "会议详情",
        		      area: ['770px', '550px'],
        		      btn: ['关闭'],
        		      content : '${ctx}/meeting/detailshow.do',
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
        		    	    body.find('#checkperson').val(data.checkperson);
        		    	    body.find('#departname').val(data.departname);
        		    	  }
        		  });
  			}
		  });
}

window.onload = function() {
	setInterval(function() { 
		table.reload('meetingroomlist', {
			 where: {
				 
	            }
	   	 	,page: {
	        	//curr: 1 //重新从第 1 页开始
	    	}
	    }); 
	},6000);
};
});
</script>
<script>
layui.use('carousel', function(){
    var carousel = layui.carousel;
    //建造实例
    carousel.render({
      elem: '#test1'
      ,width: '100%' //设置容器宽度
      ,height: '500'
      ,arrow: 'none' //始终显示箭头
      ,interval:5000//,anim: 'fade' //切换动画方式
    });
    
    carousel.render({
        elem: '#test2'
        ,width: '640' //设置容器宽度
        ,height: '300'
        ,arrow: 'none' //始终显示箭头
        ,anim:'fade'
        ,indicator:'none'
        ,interval:6000//,anim: 'fade' //切换动画方式
      });
  });
</script>
