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
	// 实现自动输入框
	var data = ${iac.appBaseInfoJson};
	var appBaseInfoList = data.appBaseInfo;
	
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
		$("#currentAppIdAndName").autocomplete(appBaseInfoList, {
			minChars : 0,
			delay : 500,
			max : 500,
			mustMatch : false,
			matchContains : true,
			selectFirst : true,
			width:200,
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
		
		$("input[type='checkbox']").die("click").live("click",(function(){
			
			$("input[type='checkbox']").removeAttr("checked");
			$(this).attr("checked", true);
			
		}));

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

			document.getElementById("currentAppIdAndName").value = $("#currentAppIdAndName")
					.val();
			
			document.getElementById("project").value = $("#project")
			.val();

			document.form.action = "queryAppCoop";
			document.form.submit();

		} else {
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}

	}
	
	function submitEdit(appServiceId,operationType){
		
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
				
			document.getElementById("selectedAppServiceId").value = appServiceId;
			document.getElementById("selectedAppServiceOperationType").value = operationType;
			
			
			document.form.action = "editAppCoop";
				
			document.form.submit();
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}

	}
	
	function submitDel(appServiceId,operationType){
		
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
			
			if(confirm("您确定要删除吗?")){
				
				document.getElementById("selectedAppServiceId").value = appServiceId;
				document.getElementById("selectedAppServiceOperationType").value = operationType;
		
				document.form.action = "deleteAppCoop";
				document.form.submit();
					
			}
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
				
	}
	
	function submitView(appServiceId, operationType, flowStatus){
		
		document.getElementById("selectedAppServiceId").value = appServiceId;
		document.getElementById("selectedAppServiceOperationType").value = operationType;
		document.getElementById("selectedAppServiceFlowStatus").value = flowStatus;
				
		document.form.action = "viewAppCoop";
			
		document.form.submit();
		
	}
	
	function submitAdd(){
		
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
			document.form.action = "fwdUpdateAppCoop";
		
			document.form.submit();
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
		
	}
	
	function submitForCheck(type){
		
		var errorMsg = '';

		if (null == $("#project").val() || '' == $("#project").val()) {
			errorMsg += '*请选择项目<br>';

			var span = document.getElementById("projectErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("projectErr");
			span.style.color = "black";
		}
		
		if('' == errorMsg){
			document.getElementById("project").value = $("#project")
			.val();
			
			if('group' == type){
				document.form.action = "submitForGroupCheckAppCoop";
			}else{
				document.form.action = "submitForDepCheckAppCoop";	
			}
			
			document.form.submit();
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
		
	}
	
	function submitReturn(appServiceId, type){
		
		document.getElementById("selectedAppServiceId").value = appServiceId;
		
		if('group' == type){
			document.form.action = "returnToDeveloperAppCoop";
		}else{
			document.form.action = "returnToTechManagerAppCoop";	
		}
	
		document.form.submit();
	}
	
	
	function submitConfirm(appServiceId){
		
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
			
			document.getElementById("selectedAppServiceId").value = appServiceId;
			
			document.form.action = "confirmAppCoop";
					
				
			document.form.submit();
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
	}
	
	function submitPublishByServiceId(appServiceId){
		
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
			
			document.getElementById("selectedAppServiceId").value = appServiceId;
			
			document.form.action = "publishAppCoop";

			document.form.submit();
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
	}	
	
	function publishAll(){
		var errorMsg = "";
		
		if (null == $("#project").val() || '' == $("#project").val()) {
			errorMsg += '*请选择项目<br>';

			var span = document.getElementById("projectErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("projectErr");
			span.style.color = "black";
		}
		
		if('' == errorMsg){
			document.getElementById("project").value = $("#project")
			.val();
			
			document.form.action = "publishAllAppCoop";
			
			document.form.submit();
		}else{
			document.getElementById("errorMsg").innerHTML = errorMsg;
			return false;
		}
		
	}
	
	
	function exportExcel(){
		console.log("exportExcel success...");

        var errorMsg = "";

        if (null == $("#project").val() || '' == $("#project").val()) {
            errorMsg += '*请选择项目<br>';

            var span = document.getElementById("projectErr");
            span.style.color = "red";
        } else {
            var span = document.getElementById("projectErr");
            span.style.color = "black";
        }

        if('' == errorMsg){
            document.getElementById("project").value = $("#project")
                .val();

            document.form.action = "exportExcelAppCoop";

            document.form.submit();
        }else{
            document.getElementById("errorMsg").innerHTML = errorMsg;
            return false;
        }

		
	}

</script>

<title>应用协作维护界面</title>
</head>
<body>


	<% HttpSession s = request.getSession();%>

	<s:form id="form" action="queryAppCoop">

		<div class="form-group">
       		<div class="field">
          		<label>项目名称：</label>
         		<input type="text" class="input" id="project" name="projectName"
					placeHolder="请输入项目关键字"  style="width:200px; line-height:17px;display:inline-block;" value="${projectName}"/>
				<span id="projectErr">*</span>
       			&nbsp;&nbsp;&nbsp;&nbsp;
          		<label>系统名称：</label>
         		<input type="text" class="input" id="currentAppIdAndName" name="currentAppIdAndName"
					placeHolder="请输入系统关键字"  style="width:200px; line-height:17px;display:inline-block;" value="${currentAppIdAndName}"/>
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
         		<select name="operationTypeInfo" id="operationTypeInfo" class="input" style="width:200px;line-height:17px;display:inline-block;" >	
         			<c:if test="${0 eq operationTypeInfo || null eq operationTypeInfo}">
           			 	<option value=0 selected>仅修改中</option>	
           			 	<option value=1>仅基线</option>         				
           			 	<option value=2>基线+修改中</option>	
         			</c:if>	
         			<c:if test="${1 eq operationTypeInfo }">
         				<option value=0>仅修改中</option>	
           			 	<option value=1 selected>仅基线</option>         				
           			 	<option value=2>基线+修改中</option>	
         			</c:if>	
         			<c:if test="${2 eq operationTypeInfo }">
           			 	<option value=0>仅修改中</option>	
           			 	<option value=1>仅基线</option>         				
           			 	<option value=2 selected>基线+修改中</option>	
         			</c:if>	
        		</select>
        		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
        	
        	<c:if test="${'testAdmin' ne username}">
       			<input type="button" class="button bg-red" style="padding:5px 15px; margin:0 10px;" value="新增" onclick="submitAdd();">
       			<c:if test="${0 ne fn:length(appRelList)}">	
			       	<input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="提交项目组确认" onclick="submitForCheck('group');">
			       	<c:if test="${name eq projectToTechManagerMap[projectName]}">
			       		<input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="提交架构部确认" onclick="submitForCheck('dep');">
			       	</c:if>
			       	<c:if test="${username eq adminMap[username]}">
			       		<input type="button" class="button bg-black" style="padding:5px 15px; margin:0 10px;" value="服务发布" onclick="publishAll();">
			       		<input type="button" class="button bg-black" style="padding:5px 15px; margin:0 10px;" value="导出excel" onclick="exportExcel();">
			       	</c:if>
			    </c:if>
		    </c:if>
       	</div>   	

		<br>
		
		<span class="text-big" style="color:#CC3300" id="errorMsg">${resultHint}</span>
		
		<br>
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
								<a class="button border-red" href="javascript:void(0)" onclick="submitView('${appRel.appServiceId}','${appRel.operationType}','${appRel.flowStatus}')">查看</a>
								<c:if test="${'testAdmin' ne username}">
									<c:choose>
										<c:when test="${'待项目组确认' ne appRel.flowStatus && '待架构部确认' ne appRel.flowStatus }">
											<c:if test="${'编辑中' eq appRel.flowStatus || '已发布' eq appRel.flowStatus}">
												<a class="button border-main" href="javascript:void(0)" onclick="submitEdit('${appRel.appServiceId}','${appRel.operationType}')">修改</a>
												<c:if test="${'编辑中' eq appRel.flowStatus}">
													<a class="button border-red" href="javascript:void(0)" onclick="submitDel('${appRel.appServiceId}','${appRel.operationType}')">删除</a> 
												</c:if>
											</c:if>
											<c:if test="${'确认待发布' eq appRel.flowStatus}">
												<c:if test="${username eq adminMap[username]}">
													<a class="button border-black" href="javascript:void(0)" onclick="submitReturn('${appRel.appServiceId}','dep');">退回项目组</a>
													<a class="button border-red" href="javascript:void(0)" onclick="submitPublishByServiceId('${appRel.appServiceId}');">发布</a>
												</c:if>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${'待项目组确认' eq appRel.flowStatus}">
												<c:if test="${name eq projectToTechManagerMap[projectName]}">
													<a class="button border-yellow" href="javascript:void(0)" onclick="submitReturn('${appRel.appServiceId}','group');">退回开发人员</a>
												</c:if>
											</c:if>
											<c:if test="${'待架构部确认' eq appRel.flowStatus}">
												<c:if test="${username eq adminMap[username]}">
													<a class="button border-black" href="javascript:void(0)" onclick="submitReturn('${appRel.appServiceId}','dep');">退回项目组</a>
													<a class="button border-red" href="javascript:void(0)" onclick="submitConfirm('${appRel.appServiceId}');">审核通过</a>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach> 
		    </table>
		 	
			<input type="hidden" name="selectedAppServiceId" id="selectedAppServiceId">
			<input type="hidden" name="selectedAppServiceOperationType" id="selectedAppServiceOperationType">
			<input type="hidden" name="selectedAppServiceFlowStatus" id="selectedAppServiceFlowStatus">
		</c:if>
	
	</s:form>
	
		
	<br>
	<br>
	
    <!--一引入分页标签 -->
    <pageTag:pager pageSize="${pageSize}" pageNo="${pageNo}" url="queryAppCoop.action" recordCount="${recordCount}"/>
    <!--分页标签结束 -->

</body>
</html>