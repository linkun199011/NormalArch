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
    <title>意见反馈</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
	<script type="text/javascript" src="./js_lib/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="./js_lib/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="./css/jquery.autocomplete.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	


	// 公共校验方法
	function check(id) {

		var obj = document.getElementById(id);
		var objErr = document.getElementById(id + "Err");

		console.log(id + ":" + obj.value);

		if (null == obj.value || 0 == obj.value || '' == obj.value.trim()) {
			objErr.style.color = "red";
			return false;
		} else {
			objErr.style.color= "black";
			return true;
		}

	}

	// 提交前校验
	function addService() {

		var errorMsg = '';

		if (null == $("#project").val() || '' == $("#project").val()) {
			errorMsg += '*请选择项目<br>';

			var span = document.getElementById("projectErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("projectErr");
			span.style.color = "black";
		}
		
		
		if (null == $("#currentAppIdAndName").val() || '' == $("#currentAppIdAndName").val()) {
			errorMsg += '*请选择系统<br>';

			var span = document.getElementById("currentAppIdAndNameErr");
			span.style.color = "red";
		} else {
			var span = document.getElementById("currentAppIdAndNameErr");
			span.style.color = "black";
		}
		
		if (!check("serviceType")) {
			errorMsg += '*请输入服务发起类型<br>';
		}

		if (!check("serviceName")) {
			errorMsg += "*请输入服务名称<br>";
		}

		if (!check("serviceDesc")) {
			errorMsg += "*请输入服务描述<br>";
		}

		if (!check("modifyDesc")) {
			errorMsg += "*请输入修改内容，如为新增服务请填新增<br>";
		}

		var t_obj = document.getElementById("stepTable");
		var rows = t_obj.rows;
		for (var i = 2; i < rows.length; i++) {
			var cells = rows[i].cells;

			if(null != cells[0].childNodes[1].value){
				cells[0].childNodes[1].name = "appServiceDetailTmpList[" + (i - 2)
				+ "].appServiceDetailId";
			}
			
			if (null == cells[1].childNodes[0].value
					|| '' == cells[1].childNodes[0].value) {
				errorMsg += "步骤" + (i-1) + ":请输入步骤描述<br>";
				cells[1].childNodes[1].style.color = "red";
			} else {
				cells[1].childNodes[0].name = "appServiceDetailTmpList[" + (i - 2)
						+ "].stepDesc";
				cells[1].childNodes[1].style.color = "black";
			}

			if (null == cells[2].childNodes[0].value
					|| '' == cells[2].childNodes[0].value) {
				errorMsg += "步骤" + (i-1) + ":请选择调用系统<br>";
				cells[2].childNodes[1].style.color = "red";
			} else {
				cells[2].childNodes[0].value = cells[2].childNodes[0].value
						.split('-')[0];
				cells[2].childNodes[0].name = "appServiceDetailTmpList[" + (i - 2)
						+ "].calledAppId";
				cells[2].childNodes[1].style.color = "black";
			}

			cells[3].childNodes[0].name = "appServiceDetailTmpList[" + (i - 2)
						+ "].calledAppIntf";

		}

		if ('' == errorMsg) {

			document.form.action = "updateAppCoop";
			
			document.getElementById("removeIndex").value = delIndex;
			
			document.form.submit();

		} else {
			document.getElementById("errorMsg").innerHTML = errorMsg;
			document.getElementById("errorMsg").style.color = "red";
			return false;
		}

	}

	function goback(){

		document.form.action = "queryAppCoop";
		document.form.submit();
		
	}

</script>
</head>
<body>
  
	<s:form id="form" action="userResponse">
	
  		<div class="panel-head"><strong>意见反馈</strong></div>
		<br><br>
		<div class="form-group">
       		<div class="field">
          		<label>意见分类：</label>
         		<select name="responseCategory" id="responseCategory" class="input" style="width:200px;line-height:17px;display:inline-block;" >	
         			<c:if test="${0 eq responseCategory || null eq responseCategory}">
           			 	<option value=0 selected>bug</option>	
           			 	<option value=1>服务缺失</option>  
           			 	<option value=2>关联关系错误</option>   
           			 	<option value=3>其他</option>     
         			</c:if>	
         			<c:if test="${1 eq responseCategory}">
           			 	<option value=0>bug</option>	
           			 	<option value=1 selected>服务缺失</option>  
           			 	<option value=2>关联关系错误</option>   
           			 	<option value=3>其他</option>     
         			</c:if>	
         			<c:if test="${2 eq responseCategory}">
           			 	<option value=0>bug</option>	
           			 	<option value=1>服务缺失</option>  
           			 	<option value=2 selected>关联关系错误</option>   
           			 	<option value=3>其他</option>     
         			</c:if>	
         			<c:if test="${3 eq responseCategory}">
           			 	<option value=0 selected>bug</option>	
           			 	<option value=1>服务缺失</option>  
           			 	<option value=2>关联关系错误</option>   
           			 	<option value=3>其他</option>     
         			</c:if>	
        		</select>
       		<div class="field">
		</div>
		<br>
     	<div class="form-group">
     	 	<div class="field">
     	 		<label>意见：</label>
     	 		<br>
     	 		<textarea id="responseDetail" class="input" style="resize:none;width:1200px;height:200px;line-height:17px;display:inline-block" name="">${response}</textarea>
     	 		<span id="responseDetailErr">*</span>
     	 	</div>
     	</div>

		<div id="buttonDIV">
			&nbsp;&nbsp;&nbsp;&nbsp; 
			
		<br>
		<br>
		<br>


        <div class="field">
        	<input type="button" class="button bg-main" style="padding:5px 15px; margin:0 10px;" value="保存" onclick="addService();">
       		<input type="button" class="button bg-red" style="padding:5px 15px; margin:0 10px;" value="返回" onclick="goback();">
       	</div>

		<br>
		<br>
		<br>
		
		<span class="text-big" style="color:#CC3300" id="errorMsg">${resultHint}</span>

		</div>
	</s:form>

</body>
</html>