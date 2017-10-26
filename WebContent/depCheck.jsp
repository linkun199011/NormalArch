<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://hi.csdn.net/tjcyjd/tags" prefix="pageTag" %>


<!DOCTYPE html>

<html>

<s:action id="iac" executeResult="false" name="initAppCoopAction"/>

<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>应用协作关系管理</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
<script type="text/javascript" src="./js_lib/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="./js_lib/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="./css/jquery.autocomplete.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	
	
	function submitView(projectId){
		
		document.getElementById("project").value = projectId;
		document.getElementById("flowStatusInfo").value = "所有";

		document.form.action = "queryAppCoop";
		document.form.submit();
	}


</script>

<title>QA审查项目是否维护完成界面</title>
</head>
<body>

	<s:form id="form">

		<span class="text-big" style="color:#CC3300" id="errorMsg">${resultHint}</span>

		<br>
		<c:if test="${0 ne fn:length(projectForDepCheckList)}">
		
			<table id="resultTable" class="table table-hover text-center">
		      	<tr>
			        <th>项目名</th>
			        <th>已提交</th>
			        <th>编辑中</th>
			        <th>待项目组确认</th>
			        <th>待架构部确认</th>
			        <th>确认待发布</th>
			        <th>操作</th>
			    </tr>	 	 	
  				<c:forEach items="${projectForDepCheckList}" var="projectForDepCheck"
					varStatus="ind">
					<tr align="center">
						<td>${projectForDepCheck.projectId}</td>
						<td>${projectForDepCheck.totalCount}</td>
						<td>${projectForDepCheck.numOfEdit}</td>					
						<td>${projectForDepCheck.numOfGroupCheck}</td>			
						<td>${projectForDepCheck.numOfDepCheck}</td>			
						<td>${projectForDepCheck.numOfPassCheck}</td>
						<td>
							<a class="button border-red" href="javascript:void(0)" onclick="submitView('${projectForDepCheck.projectId}')">查看</a>
						</td>
					</tr>	
				</c:forEach>				   
		    </table>
	 	</c:if>
	 	
	 	<input type="hidden" id="project" name="projectName"/>
	 	<input type="hidden" id="flowStatusInfo" name="flowStatusInfo"/>
	 	
	</s:form>

</body>
</html>