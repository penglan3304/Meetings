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
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all">
 <div class="layui-card" style="background-color:#54b5ff;">
  <div class="layui-card-header" style="font-size:20px;color:#ffffff">实时会议信息</div>
  <div class="layui-card-body">
      <div style="margin-bottom: 16px;text-align:center">
        <div class="layui-inline" >
          <div id="show" style="font-size:40px"></div>
  	    </div>
	  </div>
     
        <c:forEach items="${informations }" var="infos">
          <div class="layui-inline" style="float:left;width:33%;height:40%;margin-top:20px"> 
             <div style="text-align:center;margin-top:20px;font-size:30px"> <label >${infos.name}</label></div>
              <div style="margin-top:10px;margin-left:10%">
              <table border="1" cellpadding="10">
				  <tr>
				    <th>会议名称</th>
				    <th>会议状态</th>
				    <th>主讲人</th>
				    <th>操作</th>
				  </tr>
				   <c:forEach items="${information }" var="info" varStatus="s">
		            <c:choose>
		               <c:when test="${infos.id eq info.meetingroomid}">
		                    <tr>
							    <td>${info.name}</td>
							    <td>
							      <c:choose>
		                             <c:when test="${info.state=='正进行' }">
		                                 <input type="hidden" id="${s.count }" value="${info.endtime },${info.id}">
		                                        <span id="infoshow_${info.id }"></span>
										        <span id="_h${info.id }">00</span>  
										        <span id="_m${info.id }">00</span>  
										        <span id="_s${info.id }">00</span> 
		                             </c:when>
		                             <c:otherwise>
		                               ${info.state}
			          			    </c:otherwise>
		                           </c:choose> 
		                         </td>
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
    
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/layui/layui.js"></script>
<script>
layui.use(['tree','table','layer','form','upload'], function(){
    var $ = layui.$;
    var table = layui.table;
    var layer = layui.layer;
});
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
</script>
<script type="text/javascript"> 

function countTime() {  
	 var date = new Date();  
     var now = date.getTime();
	         for(var i=1;i<=${leng};i++){
	        	 var value=$("#"+i).val();
	        	 if(value){
	        	 var strs=value.split(",");
	        	 var endDate = new Date(strs[0]); 
	             var end = endDate.getTime();
	           //时间差 
	           if(end>now){
	        	   var leftTime = end-now;
	        	   document.getElementById("infoshow_"+strs[1]).innerHTML ="距结束:";  
	        	   
	           }
	           else{
	        	   var leftTime =now-end; 
	        	   document.getElementById("infoshow_"+strs[1]).innerHTML ="已超时:";
	        	   document.getElementById("infoshow_"+strs[1]).style.color="red"
	           }
	             //定义变量 d,h,m,s保存倒计时的时间  
	             var d,h,m,s;  
	             if (leftTime>=0) {  
	               //  d = Math.floor(leftTime/1000/60/60/24);  
	                 h = Math.floor(leftTime/1000/60/60%24);  
	                 m = Math.floor(leftTime/1000/60%60);  
	                 s = Math.floor(leftTime/1000%60);                     
	             }  
	             //将倒计时赋值到div中  
	            // document.getElementById("_d"+strs[1]).innerHTML = d+"天";  
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
</script>