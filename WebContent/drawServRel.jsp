<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title></title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <link rel="stylesheet" href="css/mermaid.css"/>
    <script src="js_lib/mermaid.min.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
</head>
<body>
	<a class="button border-main" href="javascript:void(0)" onclick="history.go(-1)">返回</a>
	<br>
    <span class="text-big" style="color:#CC3300" id="showErrMsg">${qryHint}</span>
    <br>
   	<c:if test="${null ne showCode && '' ne showCode }">
		<input type="hidden" name="showCode" id="sequenceDiagramCode" value="<%= request.getAttribute("showCode") %>">
	    <br/>
	    <div class="mermaid">
	    	sequenceDiagram
				<%= request.getAttribute("showCode") %>
	    </div>
	</c:if>
</body>