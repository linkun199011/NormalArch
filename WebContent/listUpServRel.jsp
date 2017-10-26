<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@ taglib uri="http://hi.csdn.net/tjcyjd/tags" prefix="pageTag" %>
	
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
    
    <script type="text/javascript">
    
    	function submitView(checkNoId, seqNoId,operationType){
    		
    		document.getElementById("checkNoId").value = checkNoId;
    		document.getElementById("seqNoId").value = seqNoId;
    		document.getElementById("operationType").value = operationType;
    		
    		console.log(checkNoId);
    		console.log(seqNoId);
    		console.log(operationType);
    		
    		document.form.action = "showUpServRel";
    		document.form.submit();
    		
    	}
    	
    	function qryForSelected(){

    		var selectedAppName = "";
    		
    		var radio = document.getElementsByName("check1");
    		console.log(radio.length);
    		for (i = 0; i < radio.length; i++) {
    		//	console.log("a:"+document.getElementById("appName"+i).innerHTML);
    			if (radio[i].checked) {
    				selectedAppName = document.getElementById("appName"+i).innerHTML;
    				console.log("selectedAppName:"+selectedAppName);
    			}
    		}
    		
    		console.log("selectedAppName:"+selectedAppName);
    		
    		if("" == selectedAppName){

    			document.getElementById("errorMsg").innerHTML = "请选择需查询的系统！";
    			
    		}else{

        		document.getElementById("selectedAppName").value = selectedAppName;
        		
        		document.form.action = "upShowServRel";
        		document.form.submit();
        		
    		}
    		
    	}
    	
    	function returnToPrev(){

			document.form.action = "queryServRel";
			document.form.submit();
    	}
    	
    </script>
    
    
</head>
<body>

 <s:form id="form" action="showUpServRel">
    
    <h1 align="center">上游关联系统展示</h1>
    <div class="field">
    	<span class="text-big" style="color:#CC3300">${fn:length(upAppNameList)}</span>
    	<span class="text-big" >个关联系统：</span>
    	&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="check1" value="所有"><span class="text-big" id="appName0">所有</span>
    	<br>
    	<br>
		<c:forEach items="${upAppNameList}" var="appName" varStatus="ind">
			<c:if test="${ind.index>0 && ind.index%8 eq 0}">
				<br>
				<br>
			</c:if>
			<input type="radio" name="check1" value="${appName}"><span class="text-big" id="appName${ind.index+1}">${appName}</span><span>&nbsp;&nbsp;&nbsp;</span>
		</c:forEach>
	</div>
	<br>
	<br>
	<div class="field">
        <input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="提交" onclick="qryForSelected();">
       	<input type="button" class="button bg-red" style="padding:5px 15px; margin:0 10px;" value="返回" onclick="history.go(-1);">
	</div>
	
		<span class="text-big" style="color:#CC3300" id="errorMsg"></span>
	
    	<br><br>
		<div class="field">		
	   			<table id="resultTable" class="table table-hover text-center">
				      	<tr>
					        <th>系统名</th>
					        <th>服务发起方式</th>
					        <th>服务码</th>
					        <th>内部转化码</th>
					        <th>服务名称</th>
					        <th>操作</th>
					    </tr>
					    <c:forEach items="${upAppRelResultList}" var="appRel"
							varStatus="ind">
							<tr height="60px">
								<td>${appRel.appName}</td>
								<td>${appRel.serviceType}</td>
								<td>${appRel.serviceCode}</td>
								<td>${appRel.appInnerCode}</td>					
								<td>${appRel.serviceName}</td>
								<td  align="center" >
									<c:if test="${'源头' eq appRel.sourceType}">
										<div class="button-group"> 
											<a class="button border-yellow" href="javascript:void(0)" onclick="submitView('${appRel.checkNoId}','${appRel.seqNoId}','${appRel.operationType}')">查看</a>
										</div>
									</c:if>
								</td>
							</tr>
						</c:forEach> 
				    </table>
			</div>
			
	    
		 	
			<input type="hidden" name="checkNoId" id="checkNoId">
			<input type="hidden" name="seqNoId" id="seqNoId">
			<input type="hidden" name="operationType" id="operationType">
			<input type="hidden" name="showCode" id="sequenceDiagramCode" value="<%= request.getAttribute("showCode") %>">
			<input type="hidden" name="selectedAppName" id = "selectedAppName">
			
		</s:form>   
		
				
		<br>
		<br>
		
	    <!--一引入分页标签 -->
		<pageTag:pager pageSize="${pageSize}" pageNo="${pageNo}" url="upShowServRel.action" recordCount="${recordCount}"/>
	
    
</body>