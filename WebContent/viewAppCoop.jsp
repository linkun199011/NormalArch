<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>

<s:action id="iac" executeResult="false" name="initAppCoopAction"/>

<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>查看应用协作关系</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
	<script type="text/javascript" src="./js_lib/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="./js_lib/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="./css/jquery.autocomplete.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

	function up(appServiceId,operationType){
		
		document.getElementById("selectedAppServiceId").value = parseInt(appServiceId);
		document.getElementById("selectedAppServiceOperationType").value = operationType;
		
		document.form.action = "upAppCoop";
		
		document.form.submit();
	}
	
	function down(appServiceId,operationType){
		console.log(appServiceId+","+operationType);
		
		document.getElementById("selectedAppServiceId").value = parseInt(appServiceId);
		document.getElementById("selectedAppServiceOperationType").value = operationType;
		
		document.form.action = "downAppCoop";
		document.form.submit();
	}
	
	function submitConfirm(appServiceId){
		
		document.getElementById("selectedAppServiceId").value = appServiceId;
		
		document.form.action = "confirmAppCoop";
				
			
		document.form.submit();
	}
	

</script>
</head>
<body>
  
	<s:form id="form">
	
  		<div class="panel-head"><strong>查看应用协作关系</strong></div>
	
		<div class="form-group">
       		 <div class="field">
          		<label>项目名称：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" class="input" id="project" name="projectName"
					placeHolder="请输入项目关键字"  style="width:250px; line-height:17px;display:inline-block;" value="${projectName}" readonly/>
				<span id="projectErr">*</span>
       			&nbsp;&nbsp;&nbsp;&nbsp;
          		<label>&nbsp;&nbsp;&nbsp;&nbsp;系统名称：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" class="input" id="currentAppIdAndName" name="currentAppIdAndName"
					placeHolder="请输入系统关键字"  style="width:250px; line-height:17px;display:inline-block;" value="${currentAppIdAndName}" readonly/>
				<span id="currentAppIdAndNameErr">*</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>&nbsp;&nbsp;&nbsp;&nbsp;发起类型：&nbsp;&nbsp;&nbsp;</label>
         		<select name="appServiceTmp.serviceTypeId" id="serviceType" class="input" style="width:250px;line-height:17px;display:inline-block;" disabled>
          			<c:if test="${null eq appServiceTmp.serviceTypeId}">
           			 	<option value=0 selected>---请选择服务发起类型---</option>
           			 </c:if>
           			 <c:forEach items='${iac.dicAppServiceTypeList}'
							var='dicAppServiceType' varStatus="ind">
							<c:if test="${(ind.index+1) eq appServiceTmp.serviceTypeId }">
								<option value='${dicAppServiceType.serviceTypeId}' selected>
									${dicAppServiceType.serviceTypeName}</option>
							</c:if>
							<c:if test="${ind.index ne appServiceTmp.serviceTypeId }">
								<option value='${dicAppServiceType.serviceTypeId}'>
									${dicAppServiceType.serviceTypeName}</option>
							</c:if>
					</c:forEach>
        		</select>
				<span id="serviceTypeErr">*</span>
       		 </div>
       	</div>
        <div class="form-group">
       		 <div class="field">
				<label>服务编号：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.serviceCode" value="${appServiceTmp.serviceCode}" readonly>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>&nbsp;&nbsp;&nbsp;&nbsp;服务名称：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" id="serviceName" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.serviceName" value="${appServiceTmp.serviceName}" readonly>
				<span id="serviceNameErr">*</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>&nbsp;&nbsp;&nbsp;&nbsp;服务说明：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" id="serviceDesc" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.serviceDesc" value="${appServiceTmp.serviceDesc}" readonly>
       		 	<span id="serviceDescErr">*</span>
       		 </div>
      	</div>
      	<div class="form-group">
       		 <div class="field">
				<label>内部转换码：</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.appInnerCode" value="${appServiceTmp.appInnerCode}" readonly>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>接收请求文件：</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.receiveFile" value="${appServiceTmp.receiveFile}" readonly>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>返回响应文件：</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.getbackFile" value="${appServiceTmp.getbackFile}" readonly>
       		 </div>
     	 </div>
     	 
     	 <c:if test="${'基线' ne appServiceTmp.operationType}">
	     	  <div class="form-group">
	     	 	<div class="field">
	     	 		<label>修改内容描述：</label>
	     	 		<br>
	     	 		<textarea id="modifyDesc" class="input" style="resize:none;width:1200px;height:200px;line-height:17px;display:inline-block" name="appServiceTmp.modifyDesc">${appServiceTmp.modifyDesc}</textarea>
	     	 		<span id="modifyDescErr">*</span>
	     	 	</div>
	     	 </div>
     	 </c:if>
      
      	<table id="stepTable" class="table table-hover text-center">
	      	<tr>
		        <th>服务步骤</th>
		        <th>步骤描述*</th>
		        <th>调用系统*</th>
		        <th>调用服务*</th>
		    </tr>
		    <c:forEach items="${appServiceDetailTmpList}" var="appServiceDetailTmp"
				varStatus="ind">
				<tr align="center">
					<td><input type="checkbox" name="check1" disabled/><input type="hidden" value="${appServiceDetailTmp.appServiceDetailId}"/></td>
					<td><input type='text' id='stepDesc' style='width:250px; text-align:center; border:1px solid #ddd; padding:7px 0;'
						value="${appServiceDetailTmp.stepDesc}" readonly><span>*</span></td>
					<td><input type='text' id='calledSysId${ind.index}'
						placeHolder='请输入系统关键字' style='width:250px; text-align:center; border:1px solid #ddd; padding:7px 0;'
						value="${editAppServiceIdAndName[ind.index]}" readonly><span>*</span></td>
					<td><input type='text' id='calledAppIntf'
						style='width:250px; text-align:center; border:1px solid #ddd; padding:7px 0;' value="${appServiceDetailTmp.calledAppIntf}" readonly></td>
				</tr>
			</c:forEach> 
	    </table>

		<br>
		<br>
		<br>

        <div class="field">
       		<input type="button" class="button bg-red" style="padding:5px 15px; margin:0 10px;" value="返回" onclick="history.go(-1)">
       		<input type="button" class="button bg-blue" style="padding:5px 15px; margin:0 10px;" value="上游" onclick="up('${appServiceTmp.appServiceId}','${appServiceTmp.operationType}');">
       		<input type="button" class="button bg-yellow" style="padding:5px 15px; margin:0 10px;" value="下游" onclick="down('${appServiceTmp.appServiceId}','${appServiceTmp.operationType}');">
       		
       		<c:if test="${'待架构部确认' eq selectedAppServiceFlowStatus}">
				<c:if test="${username eq adminMap[username]}">
					<input type="button" class="button bg-black" style="padding:5px 15px; margin:0 10px;" value="审核通过" onclick="submitConfirm('${appServiceTmp.appServiceId}');">
				</c:if>
			</c:if>
       		
       	</div>

		<input type="hidden" name="selectedAppServiceId" id="selectedAppServiceId">
		<input type="hidden" name="method" id="method">
		<input type="hidden" name="selectedAppServiceOperationType" id="selectedAppServiceOperationType">
		
	</s:form>

</body>
</html>