<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
  + path + "/resources/";
 String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
  + path + "/";
 request.setAttribute("path", path);
 request.setAttribute("basePath", basePath);
 request.setAttribute("urlPath", urlPath);
 
%>
<!DOCTYPE html>
<html>
<head>
<title>QRCODE</title>
</head>
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all">
<script src="${urlPath}/js/jquery.min.js"></script>
<script src="${urlPath}/js/jquery.qrcode.min.js"></script>
<script src="${urlPath}/layui/layui.js"></script>
<body>
<video id="video" autoplay style="display: block;margin:1em auto;width:400px;height:400px;"></video>
<div style="text-align:center">
<span id="msgs"></span>
应到：<span >${counts }</span>人
实到：<span id="msg">${count }</span>人</div>
 <table  style="margin:0px 0" id="attdlist" lay-filter="attdlist"></table>
<script>
  window.addEventListener("DOMContentLoaded", function () {
    var video = document.getElementById("video"), canvas, context;
    try {
      canvas = document.createElement("canvas");
      context = canvas.getContext("2d");
    } catch (e) { alert("not support canvas!"); return; }
     
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
  
    if (navigator.getUserMedia){
      navigator.getUserMedia(
        { "video": true },
        function (stream) {
          if (video.mozSrcObject !== undefined)video.mozSrcObject = stream;
          else video.src = ((window.URL || window.webkitURL || window.mozURL || window.msURL) && window.URL.createObjectURL(stream)) || stream;        
          video.play();
        },
        function (error) {
          if(error.PERMISSION_DENIED){
           alert("用户拒绝了浏览器请求媒体的权限");
          }
           //console.log("用户拒绝了浏览器请求媒体的权限",error.code);
          if(error.NOT_SUPPORTED_ERROR){
           alert("当前浏览器不支持拍照功能");
          }
           //console.log("当前浏览器不支持拍照功能",error.code);
          if(error.MANDATORY_UNSATISFIED_ERROR){
           alert("指定的媒体类型未接收到媒体流");
          }
           //console.log("指定的媒体类型未接收到媒体流",error.code);
          alert("Video capture error: " + error.code);
        }
      );
     //定时扫描
      setInterval(function () {
        context.drawImage(video, 0, 0, canvas.width = video.videoWidth, canvas.height = video.videoHeight);
    sumitImageFile(canvas.toDataURL());
      }, 1500);
    } else {
     alert("扫描出错，换种方式试试！");
    }
     
  
  }, false);
   
   
  /**
   * @param base64Codes
   *      图片的base64编码
   */
  function sumitImageFile(base64Codes){
   // var form=document.forms[0];
     
   // var formData = new FormData(form);  //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
    var formData = new FormData();  //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
     
    //convertBase64UrlToBlob函数是将base64编码转换为Blob
    formData.append("ewmImg",convertBase64UrlToBlob(base64Codes)); //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同
    var count=0; 
    //ajax 提交form
    $.ajax({
      url : '${urlPath}/meeting/sign.do',
      type : "POST",
      data : formData,
      dataType:"text",
      processData : false,     // 告诉jQuery不要去处理发送的数据
      contentType : false,    // 告诉jQuery不要去设置Content-Type请求头   
      success:function(data){
       if(data != "no"){
    	   $.ajax({
    		      url : '${urlPath}/meeting/signstate.do?data='+data,
    		      type : "POST",
    		      dataType:"json",
    		      success:function(data){
    		    	  if(data==0){
    		    		  var count=$("#msg").val();
    		    		  count++;
    		    		  $("#msgs").html("<div style='font-size:20px;color:red'>签到成功</div>");
    		    		  $("#msg").val(count);
    		    	  }
    		    	  if(data==1){
    		    		  $("#msgs").html("<div style='font-size:20px;color:red'>无此用户</div>");
    		    	  }
    		    	  if(data==2){
    		    		  $("#msgs").html("<div style='font-size:20px;color:red'>参会错误</div>");
    		    	  }
    		    	  if(data==3){
    		    		  $("#msgs").html("<div style='font-size:20px;color:red'>二维码错误</div>");
    		    	  }
    		    	  if(data==4){
    		    		  $("#msgs").html("<div style='font-size:20px;color:red'>欢迎旁听</div>");
    		    	  }
    		    	  if(data==5){
    		    		  $("#msgs").html("<div style='font-size:20px;color:red'>请勿重复签到</div>");
    		    	  }
    		      }
    	   });
       }
      },
      xhr:function(){      //在jquery函数中直接使用ajax的XMLHttpRequest对象
        var xhr = new XMLHttpRequest();
         
        xhr.upload.addEventListener("progress", function(evt){
          if (evt.lengthComputable) {
            var percentComplete = Math.round(evt.loaded * 100 / evt.total); 
            console.log("正在提交."+percentComplete.toString() + '%');    //在控制台打印上传进度
          }
        }, false);
         
        return xhr;
      }
       
    });
  }
  
  /**
   * 将以base64的图片url数据转换为Blob
   * @param urlData
   *      用url方式表示的base64图片数据
   */
  function convertBase64UrlToBlob(urlData){
     
    var bytes=window.atob(urlData.split(',')[1]);    //去掉url的头，并转换为byte
     
    //处理异常,将ascii码小于0的转换为大于0
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
      ia[i] = bytes.charCodeAt(i);
    }
  
    return new Blob( [ab] , {type : 'image/png'});
  }
   
 </script> 
 <!--  <script>
layui.use(['tree','table','layer','form','upload'], function(){
    var $ = layui.$;
    var table = layui.table;
    var layer = layui.layer;
    table.render({
        elem: '#attdlist'
        ,url:'${ctx}/meeting/attmeetinglist.do'
        ,title: '会议列表'
        ,width:600
        ,method:'POST'
        ,cols: [
            [
            	 {field:'attendperson', title:'参会人',height:38}
                ,{field:'signtime', title:'签到时间',height:38}
                ,{field:'state', title:'状态',height:38}
                ,{field:'absencereason', title:'缺席原因',height:38}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo',height:38}
            ]
        ]
        ,page: true
    });
    window.onload = function() {
    	setInterval(function() { 
    		table.reload('attdlist', {
    			 where: {
    				 
    	            }
    	   	 	,page: {
    	        	
    	    	}
    	    }); 
    	},1500);
    };
    });
    
    </script> -->
</body>
  
</html>

  