<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-cn">

<s:action id="ul" executeResult="false" name="userinit"/>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>架构资产管理工具</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>  
    
    <script type="text/javascript">
	    $().ready(function() {
	    	
	    	if("" == "${name}"){
	    		location.href = "userlogout.action";
	    	}
	    });
    
    </script> 
</head>
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
  <div class="logo margin-big-left">
    <h1><img src="images/logo.jpg" height="50" alt="" />架构资产管理工具</h1>
  </div>
  <div class="head-l">
  <!-- <a class="button button-little bg-blue" href="userResponse.jsp" target="right"><span></span>提出意见</a> -->
  	<a class="button button-little bg-red" href="userlogout.action"><span></span>退出登录</a> 
  </div>
</div>
<div class="leftnav">
  <div class="leftnav-title"><strong><span></span>功能菜单</strong></div> 
  <h2><span>血缘关系管理</span></h2>
  <ul style="display:block">
  	<li><a href="initAppCoop.action" target="right"><span></span>血缘关系维护</a></li>
  	<li><a href="QACheck.jsp" target="right"><span></span>维护情况查询</a></li> 
  	<c:if test="${username eq adminMap[username]}">
  		<li><a href="depCheckAppCoop.action" target="right"><span></span>架构部审查</a></li>
  	</c:if>
  </ul> 
  <h2><span>关联关系展示</span></h2>
  <ul style="display:block">
    <li><a href="initServRel.action" target="right"><span></span>服务关联展示</a></li>   
    <li><a href="initSysRel.action" target="right"><span></span>系统关联展示</a></li>   
  </ul> 
  <h2><a href="appMap.jsp" target="right"><span>应用地图展示</span></a></h2>
   
</div>
<script type="text/javascript">
$(function(){
  $(".leftnav ul li a").click(function(){
	    $("#a_leader_txt").text($(this).text());
  })
  $(".leftnav h2 a").click(function(){
	    $("#a_leader_txt").text($(this).text());
  })
});
</script>
<ul class="bread">
  <li><a id="a_leader_txt">首页</a></li>
  <li><b>当前用户：</b><span style="color:red;">${name}</span>
</ul>
<div class="admin">
  <iframe src="manageAppCoop.jsp" name="right" width="100%" height="100%"></iframe>
</div>

<input type ="hidden" name="modifyUser" value="${name}">
</body>
</html>