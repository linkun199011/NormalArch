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
    <title>应用协作关系展示</title>  
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

			document.form.action = "queryServRel";
			document.form.submit();

		} else {
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}

	}
	
	function up(appServiceId,operationType){
		
		document.getElementById("selectedAppServiceId").value = appServiceId;
		document.getElementById("operationType").value = operationType;
		
		document.form.action = "upShowServRel";
		
		document.form.submit();
	}
	
	function down(appServiceId,operationType){
		console.log(appServiceId+","+operationType);
		
		document.getElementById("selectedAppServiceId").value = appServiceId;
		document.getElementById("operationType").value = operationType;
		
		document.form.action = "downShowServRel";
		document.form.submit();
	}
	

</script>

<title>应用协作维护界面</title>
</head>
<body>

	<s:form id="form" action="queryServRel">

		<div class="form-group">
       		<div class="field">
          		<label>系统名称：</label>
         		<input type="text" class="input" id="currentAppIdAndName" name="currentAppIdAndName"
					placeHolder="请输入系统关键字"  style="width:200px; line-height:17px;display:inline-block;" value="${currentAppIdAndName}"/>
				<span id="currentAppIdAndNameErr">*</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>服务名称：</label>
         		<input type="text" class="input" id="serviceName" name="serviceName"
					style="width:200px; line-height:17px;display:inline-block;" value="${serviceName}"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>服务码：</label>
         		<input type="text" class="input" id="serviceCode" name="serviceCode"
					style="width:200px; line-height:17px;display:inline-block;" value="${serviceCode}"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>内部转换码：</label>
         		<input type="text" class="input" id="appInnerCode" name="appInnerCode"
					style="width:200px; line-height:17px;display:inline-block;" value="${appInnerCode}"/>
				<br><br>
				<label>服务状态：</label>
         		<select name="operationTypeInfo" id="operationTypeInfo" class="input" style="width:200px;line-height:17px;display:inline-block;">	
         			<c:if test="${0 eq operationTypeInfo}">
           			 	<option value=0 selected>仅修改中</option>	
           			 	<option value=1>仅基线</option>         
         			</c:if>	
         			<c:if test="${1 eq operationTypeInfo  || null eq operationTypeInfo}">
         				<option value=0>仅修改中</option>	
           			 	<option value=1 selected>仅基线</option>   
         			</c:if>
        		</select>
        		&nbsp;&nbsp;&nbsp;&nbsp;
				<label>流程状态：</label>
				<select name="flowStatusInfo" id="flowStatusInfo" class="input" style="width:200px;line-height:17px;display:inline-block;" >
					<c:forEach items="${iac.dicFlowStatusList}" var="dicFlowStatus" varStatus="ind">
						<c:choose>
							<c:when test="${null eq dicFlowStatus.flowStatus || '所有' eq dicFlowStatus.flowStatus }">
								<option value="${dicFlowStatus.flowStatus}" selected>${dicFlowStatus.flowStatus}</option>
							</c:when>
							<c:otherwise>
								<option value="${dicFlowStatus.flowStatus}">${dicFlowStatus.flowStatus}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
        		
       		 </div>
       	</div>
       	
       <div class="field">
        	<input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="查询" onclick="submitQry();">
        </div>
        	
       	
		<br>
		<br>
		
		<span class="text-big" style="color:#CC3300" id="errorMsg">${resultHint}</span>
		
		<c:if test="${0 ne fn:length(appRelList)}">
		
			<table id="resultTable" class="table table-hover text-center">
		      	<tr>
			        <th>系统名</th>
			        <th>服务发起方式</th>
			        <th>服务码</th>
			        <th>内部转化码</th>
			        <th>服务名称</th>
			        <th>请求接收文件名</th>
			        <th>返回响应文件名</th>
			        <th>服务状态</th>
			        <th>流程状态</th>
			        <th>修改人</th>
			        <th>操作</th>
			    </tr>
			    <c:forEach items="${appRelList}" var="appRel"
					varStatus="ind">
					<tr align="center">
						<td>${appRel.appName}</td>
						<td>${appRel.serviceType}</td>
						<td>${appRel.serviceCode}</td>
						<td>${appRel.appInnerCode}</td>					
						<td>${appRel.serviceName}</td>
						<td>${appRel.receiveFile}</td>
						<td>${appRel.getbackFile}</td>
						<td>${appRel.operationType}</td>
						<td>${appRel.flowStatus}</td>
						<td>${appRel.modifyUserName}</td>
						<td>
							<div class="button-group"> 
								<a class="button border-main" href="javascript:void(0)" onclick="up('${appRel.appServiceId}','${appRel.operationType}')">上游</a>
								<a class="button border-red" href="javascript:void(0)" onclick="down('${appRel.appServiceId}','${appRel.operationType}')">下游</a> 
							</div>
						</td>
					</tr>
				</c:forEach> 
		    </table>
		</c:if>
	    	
		<input type="hidden" name="serviceId" id="selectedAppServiceId">
		<input type="hidden" name="operationType" id="operationType">
	
	</s:form>
	
		
	<br>
	<br>
	
    <!--一引入分页标签 -->
    <pageTag:pager pageSize="${pageSize}" pageNo="${pageNo}" url="queryServRel.action" recordCount="${recordCount}"/>
    <!--分页标签结束 -->
	


</body>
</html>