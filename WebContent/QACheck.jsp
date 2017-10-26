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
	
	var projectData = ${iac.projectInfoJson};
	
	console.log(projectData);
	var projectList = projectData.projectList;
	console.log(projectList);
	
	//项目管理平台对接前临时使用
//	var projectList = [
//		"prj-001 柜面三期",
//		"prj-002 数据仓库三期",
//		"prj-003 核心账户变动",
//		"prj-004 利率市场化项目",
//		"prj-005 智慧客服二期",
//		"prj-006 知识库与智能机器人",
//		"tsk-001 短信-修改-上行短信",
//		"tsk-002 电子银行服务平台-修改-交易明细查询",
//		"prj-007 互联网前端整合项目"
//	];

	//新增AutoComplete时使用
	var autoIndex = 0;

	//开始
	$().ready(function() {
		
		$("#project").autocomplete(projectList, {
			minChars : 0,
			delay : 500,
			max : 500,
			mustMatch : false,
			matchContains : true,
			selectFirst : true,
			width:200,
			formatItem : function(data, i, total) {
				return "<I>" + data.projectName +"</I>";
			},
			formatMatch : function(data, i, total) {
				return data.projectName;
			},
			formatResult : function(data) {
				return data.projectName;
			}
		});
		
	});
	
	
	function submitQry(){
		
		var errorMsg = '';

		if (null == $("#project").val() || '' == $("#project").val()) {
			errorMsg += '*请选择项目<br>';

			var span = document.getElementById("projectErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("projectErr");
			span.style.color = "black";
		}
		
		if ('' == errorMsg) {

			document.getElementById("project").value = $("#project")
			.val();

			document.form.action = "checkForQA";
			document.form.submit();

		} else {
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}

	}
	
	function updateWhiteList(){
		
		document.getElementById("project").value = $("#project")
		.val();

		document.form.action = "updateProjectWhiteList";
		document.form.submit();
		
	}
	
	function removeFromProjectWhiteList(){
		
		document.getElementById("project").value = $("#project")
		.val();

		document.form.action = "removeFromProjectWhiteList";
		document.form.submit();
		
	}

</script>

<title>QA审查项目是否维护完成界面</title>
</head>
<body>

	<s:form id="form">

		<div class="form-group">
       		<div class="field">
          		<label>项目名称：</label>
         		<input type="text" class="input" id="project" name="projectName"
					placeHolder="请输入项目关键字"  style="width:200px; line-height:17px;display:inline-block;" value="${projectName}"/>
				<span id="projectErr">*</span>
       		</div>
       	</div>
       	<div class="field">
        	<input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="查询" onclick="submitQry();">
       	</div>   	

		<br>
		
		<span class="text-big" style="color:#CC3300" id="errorMsg">${resultHint}</span>
		
		<br>
		<c:if test="${projectForCheck.projectId ne null}">
		
			<table id="resultTable" class="table table-hover text-center">
		      	<tr>
			        <th>项目名</th>
			        <th>是否需填写应用协作</th>
			        <th>已提交应用协作数</th>
			        <th>已审核完成数</th>
			        <th>待审核数</th>
			    </tr>	 	 	

				<tr align="center">
					<td>${projectForCheck.projectId}</td>
					<td>${projectForCheck.projectUnderCheck}</td>
					<td>${projectForCheck.numOfAppCoopForCheck}</td>
					<td>${projectForCheck.numOfAppCoopCompleteCheck}</td>					
					<td>${projectForCheck.numOfAppCoopUnderCheck}</td>
				</tr>					   
		    </table>
		    
		  <br>
		  <br>
		  

		  
		  <div class="field"> 
		  	<c:if test="${username eq adminMap[username]}">
				<c:if test="${projectForCheck.projectUnderCheck eq true}">
					<input type="button" class="button bg-green" style="padding:5px 15px; margin:0 10px;" value="添加白名单" onclick="updateWhiteList();">
				</c:if>
				<c:if test="${projectForCheck.projectUnderCheck eq false}">
					<input type="button" class="button bg-red" style="padding:5px 15px; margin:0 10px;" value="移除白名单" onclick="removeFromProjectWhiteList();">
				</c:if>
			</c:if>
			</div>	
		</c:if>		
	</s:form>

</body>
</html>