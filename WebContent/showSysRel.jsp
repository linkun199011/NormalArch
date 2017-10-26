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
    <title>系统关联关系展示</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
	<script type="text/javascript" src="./js_lib/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="./js_lib/jquery.autocomplete.js"></script>
	<link rel="stylesheet" href="./css/jquery.autocomplete.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
	// 实现自动输入框
	var data = ${iac.appBaseInfoJson};
	var appBaseInfoList = data.appBaseInfo;
	
	//新增AutoComplete时使用
	var autoIndex = 0;

	//开始
	$().ready(function() {
		$("#currentAppIdAndName").autocomplete(appBaseInfoList, {
			minChars : 0,
			delay : 500,
			max : 500,
			mustMatch : false,
			matchContains : true,
			selectFirst : true,
			width: 200,
			formatItem : function(data, i, total) {
				return "<I>" + data.appId + "-" + data.appName + "</I>";
			},
			formatMatch : function(data, i, total) {
				return data.appId + "-" + data.appName;
			},
			formatResult : function(data) {
				return data.appId + "-" + data.appName;
			}
		});


	});
	
	
	function submitQry(){
		
		var errorMsg = '';
	
		if (null == $("#currentAppIdAndName").val() || '' == $("#currentAppIdAndName").val()) {
			errorMsg += '*请选择系统<br>';

			var span = document.getElementById("currentAppIdAndNameErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("currentAppIdAndNameErr");
			span.style.color = "black";
		}
		
		if ('' == errorMsg) {

			document.getElementById("currentAppIdAndName").value = $("#currentAppIdAndName")
					.val();

			document.form.action = "querySysRel";
			document.form.submit();

		} else {
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}

	}

	function submitDrawRel(){
		
		var errorMsg = '';
		
		if (null == $("#currentAppIdAndName").val() || '' == $("#currentAppIdAndName").val()) {
			errorMsg += '*请选择系统<br>';

			var span = document.getElementById("currentAppIdAndNameErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("currentAppIdAndNameErr");
			span.style.color = "black";
		}
		
		if ('' == errorMsg) {

			document.getElementById("currentAppIdAndName").value = $("#currentAppIdAndName")
					.val();

			document.form.action = "drawSysRel";
			document.form.submit();

		} else {
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
		
	}

</script>

<title>应用协作维护界面</title>
</head>
<body>

	<s:form id="form" action="querySysRel">

		<s:token></s:token>
		<div class="form-group">
       		<div class="field">
          		<label>系统名称：</label>
         		<input type="text" class="input" id="currentAppIdAndName" name="currentAppIdAndName"
					placeHolder="请输入系统关键字"  style="width:200px; line-height:17px;display:inline-block;" value="${currentAppIdAndName}"/>
				<span id="currentAppIdAndNameErr">*</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>服务状态：</label>
         		<select name="operationTypeInfo" id="operationTypeInfo" class="input" style="width:200px;line-height:17px;display:inline-block;" >		
            		<option value=1 selected>仅基线</option>         				
        		</select>
       		 </div>
       	</div>
       	
       	<div class="field">
        	<input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="查询" onclick="submitQry();">
        </div>
       	
		<br>
		<br>
		
		<span class="text-big" style="color:#CC3300" id="errorMsg">${qryHint}</span>
		
		<c:if test="${0 ne fn:length(appInfoList)}">
			
			<div class="field">		
				<table id="resultTable" class="table table-hover text-center">
			      	<tr>
				        <th>系统名</th>
				        <th>系统缩写</th>
				        <th>系统编号</th>
				        <th>服务级别</th>
				        <th>所属应用分类</th>
				        <th>所属应用分层</th>
				        <th>系统等级</th>
				        <th>等级保护</th>
				        <th>服务时段</th>
				    </tr>
				    <c:forEach items="${appInfoList}" var="appInfo"
						varStatus="ind">
						<tr align="center">
							<td>${appInfo.appName}</td>
							<td>${appInfo.appShortName}</td>
							<td>${appInfo.appCode}</td>
							<td>${appInfo.serviceLevel}</td>
							<td>${appInfo.category_lv1}</td>
							<td>${appInfo.category_lv2}</td>
							<td>${appInfo.systemLevel}</td>
							<td>${appInfo.securityLevel}</td>					
							<td>${appInfo.serviceTime}</td>		
						</tr>
					</c:forEach> 
					
			    </table>
			</div>
	
		    
		    <br>
		    <br>
		    <input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="查询关联图" onclick="submitDrawRel();">
		
		</c:if>
		
	</s:form>
	
		
	<br>
	<br>

	<!--一引入分页标签 -->
	<pageTag:pager pageSize="${pageSize}" pageNo="${pageNo}" url="querySysRel.action" recordCount="${recordCount}"/>
	

</body>
</html>