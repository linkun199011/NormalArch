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
	//实现自动输入框
	var data = ${iac.appBaseInfoJson};
	var appBaseInfoList = data.appBaseInfo;
	
	var projectData = ${iac.projectInfoJson};
	
	console.log(projectData);
	var projectList = projectData.projectList;
	console.log(projectList);

	var editListSize = ${iac.appServiceDetailTmpList.size()};

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
			width:250,
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
			width:250,
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
		
		for(i=0; i<editListSize; i++){
			
			$("#calledSysId"+i).autocomplete(appBaseInfoList, {
				minChars : 0,
				delay : 500,
				max : 500,
				mustMatch : false,
				matchContains : true,
				selectFirst : true,
				width:250,
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
		}

		autoIndex = editListSize+1;

	});

	// 插入行
	function insertinto() {

		var c1 = document.getElementsByName("check1");
		var checked = new Array();
		for (i = 0; i < c1.length; i++) {
			if (c1[i].checked) {
				checked.push(i + 2);
			}
		}
		for (i = 0; i < checked.length; i++) {

			var index = document.getElementById('stepTable').insertRow(
					checked[i]);

			var checkBoxTd = document.createElement("td");
			var stepDescTd = document.createElement("td");
			var calledAppIdTd = document.createElement("td");
			var calledAppIntf = document.createElement("td");

			checkBoxTd.innerHTML = "<input type='checkbox' name='check1'><input type='hidden' value='${appServiceDetailTmp.appServiceDetailId}'/>";
			stepDescTd.innerHTML = "<input type='text' id='stepDesc' style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;'><span>*</span>";
			calledAppIdTd.innerHTML = "<input type='text' id='calledSysId"+autoIndex+"' placeHolder='请输入系统关键字' style='width:250px;  text-align:center; border:1px solid #a8a8a8; padding:7px 0;'/><span>*</span>"
			calledAppIntf.innerHTML = "<input type='text' id='calledAppIntf' style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;'>";


			index.appendChild(checkBoxTd);
			index.appendChild(stepDescTd);
			index.appendChild(calledAppIdTd);
			index.appendChild(calledAppIntf);


			$("#calledSysId" + autoIndex).autocomplete(appBaseInfoList, {
				minChars : 0,
				max : 500,
				mustMatch : false,
				matchContains : true,
				selectFirst : true,
				width:250,
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

			autoIndex += 1;
		}

	}

	//使表格行上移，接收参数为链接对象 
	function moveUp() {
		//按复选框上移
		var _table = document.getElementById("stepTable");
		var c1 = document.getElementsByName("check1");
		var checked = new Array();
		for (i = 1; i < c1.length; i++) {
			if (c1[i].checked) {
				checked.push(i + 2);
			}
		}

		for (i = 0; i < checked.length; i++) {

			var _row = _table.rows[checked[i]];
			
			var _node = _row.previousSibling;
			while(_node && _node.nodeType != 1){
				_node = _node.previousSibling;
			}

			if(_node){
				swapNode(_row,_node);
			}
			
		}
	}

	//使表格行下移，接收参数为链接对象 
	function moveDown() {
		//按复选框下移
		var _table = document.getElementById("stepTable");
		var c1 = document.getElementsByName("check1");
		var checked = new Array();
		for (i = 0; i < c1.length; i++) {
			if (c1[i].checked) {
				checked.push(i + 2);
			}
		}
	
		for (i = checked.length - 1; i >= 0; i--) {

			var _row = _table.rows[checked[i]];

			var _node = _row.nextSibling;
			while(_node && _node.nodeType != 1){
				_node = _node.nextSibling;
			}

			if(_node){
				swapNode(_row,_node);
			}
			
		}
	}

	//定义通用的函数交换两个结点的位置 
	function swapNode(node1, node2) {
		//获取父结点 
		var _parent = node1.parentNode;
		//获取两个结点的相对位置 
		var _t1 = node1.nextSibling;
		var _t2 = node2.nextSibling;
		//将node2插入到原来node1的位置 
		if (_t1) {
			_parent.insertBefore(node2, _t1);
		} else {
			_parent.appendChild(node2);
		}
		//将node1插入到原来node2的位置 
		if (_t2) {
			_parent.insertBefore(node1, _t2);
		} else {
			_parent.appendChild(node1);
		}
	}
	//增加行
	function add() {

		var t_obj = document.getElementById("stepTable");
		//alert("stepTable size:" + t_obj.rows.length);    	
		var stepValue = t_obj.rows.length - 1;

		var index = t_obj.insertRow(-1);

		var checkBoxTd = document.createElement("td");
		var stepDescTd = document.createElement("td");
		var calledAppIdTd = document.createElement("td");
		var calledAppIntf = document.createElement("td");
		
		checkBoxTd.innerHTML = "<input type='checkbox' name='check1'><input type='hidden' value='${appServiceDetailTmp.appServiceDetailId}'/>";
		stepDescTd.innerHTML = "<input type='text' id='stepDesc' style='width:250px;  text-align:center; border:1px solid #a8a8a8; padding:7px 0;'><span>*</span>";
		calledAppIdTd.innerHTML = "<input type='text' id='calledSysId"+autoIndex+"' placeHolder='请输入系统关键字' style='width:250px;  text-align:center; border:1px solid #a8a8a8; padding:7px 0;'/><span>*</span>"
		calledAppIntf.innerHTML = "<input type='text' id='calledAppIntf' style='width:250px;  text-align:center; border:1px solid #a8a8a8; padding:7px 0;'>";

		index.appendChild(checkBoxTd);
		index.appendChild(stepDescTd);
		index.appendChild(calledAppIdTd);
		index.appendChild(calledAppIntf);

		// 新建autocomplete的方法
		$("#calledSysId" + autoIndex).autocomplete(appBaseInfoList, {
			minChars : 0,
			delay : 300,
			max : 500,
			mustMatch : false,
			matchContains : true,
			selectFirst : true,
			width:250,
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

		autoIndex += 1;

	}

	var delIndex="";
	// 删除行
	function del() {

		var c = document.getElementsByName("check1");
		var checked = new Array();
		// alert(c.length);   
		for (i = 0; i < c.length; i++) {
			if (c[i].checked) {
				checked.push(i + 2);
			}
		}
		for (i = 0; i < checked.length; i++) {
			
			var index = checked[i];
			
			var t_obj = document.getElementById("stepTable");
			var rows = t_obj.rows;
			var cells = rows[index].cells;
			if("" != cells[0].childNodes[1].value){
				delIndex += cells[0].childNodes[1].value+",";
			}
			
			console.log(delIndex);
			
			//alert(checked[i]);   
			document.getElementById("stepTable").deleteRow(checked[i] - i);
		}
		
		if(checked.length == 0){
			alert("请选择要删除的步骤！");
		}

	}

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

			console.log(delIndex);
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
  
	<s:form id="form" action="addAppCoop">
	
  		<div class="panel-head"><strong>维护应用协作关系</strong></div>
		<br><br>
		<div class="form-group">
       		 <div class="field">
          		<label>项目名称：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" class="input" id="project" name="projectName"
					placeHolder="请输入项目关键字"  style="width:250px; line-height:17px;display:inline-block;" value="${projectName}"/>
				<span id="projectErr">*</span>
       			&nbsp;&nbsp;&nbsp;&nbsp;
          		<label>&nbsp;&nbsp;&nbsp;&nbsp;系统名称：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" class="input" id="currentAppIdAndName" name="currentAppIdAndName"
					placeHolder="请输入系统关键字"  style="width:250px; line-height:17px;display:inline-block;" value="${currentAppIdAndName}" />
				<span id="currentAppIdAndNameErr">*</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>&nbsp;&nbsp;&nbsp;&nbsp;发起类型：&nbsp;&nbsp;&nbsp;</label>
         		<select name="appServiceTmp.serviceTypeId" id="serviceType" class="input" style="width:250px;line-height:17px;display:inline-block;">
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
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.serviceCode" value="${appServiceTmp.serviceCode}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>&nbsp;&nbsp;&nbsp;&nbsp;服务名称：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" id="serviceName" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.serviceName" value="${appServiceTmp.serviceName}">
				<span id="serviceNameErr">*</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label>&nbsp;&nbsp;&nbsp;&nbsp;服务说明：&nbsp;&nbsp;&nbsp;</label>
         		<input type="text" id="serviceDesc" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.serviceDesc" value="${appServiceTmp.serviceDesc}">
       		 	<span id="serviceDescErr">*</span>
       		 </div>
      	</div>
      	<div class="form-group">
       		 <div class="field">
				<label>内部转换码：</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.appInnerCode" value="${appServiceTmp.appInnerCode}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>接收请求文件：</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.receiveFile" value="${appServiceTmp.receiveFile}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>返回响应文件：</label>
         		<input type="text" class="input" style="width:250px; line-height:17px;display:inline-block" name="appServiceTmp.getbackFile" value="${appServiceTmp.getbackFile}">
       		 </div>
     	 </div>
     	 
     	 <div class="form-group">
     	 	<div class="field">
     	 		<label>修改内容描述：</label>
     	 		<br>
     	 		<textarea id="modifyDesc" class="input" style="resize:none;width:1200px;height:200px;line-height:17px;display:inline-block" name="appServiceTmp.modifyDesc">${appServiceTmp.modifyDesc}</textarea>
     	 		<span id="modifyDescErr">*</span>
     	 	</div>
     	 </div>
      
      	<table id="stepTable" class="table table-hover text-center">
	      	<tr>
		        <th>服务步骤</th>
		        <th>步骤描述*</th>
		        <th>调用系统*</th>
		        <th>调用服务*</th>
		    </tr>
		    <tr>
		    	<td>示例</td>
		    	<td><input type='text' id='eg_stepDesc' value="账户信息查询" style='width:250px;  text-align:center; border:1px solid #a8a8a8; padding:7px 0;' readonly><span>*</span></td>
		    	<td><input type='text' id='eg_calledApp'  value="94-企业服务总线" style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;' readonly><span>*</span></td>
		    	<td><input type='text' id='eg_calledIntf' value="0100300000101" style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;' readonly></td>
		    </tr>
		    <c:forEach items="${appServiceDetailTmpList}" var="appServiceDetailTmp"
				varStatus="ind">
				<tr align="center">
					<td><input type="checkbox" name="check1" /><input type="hidden" value="${appServiceDetailTmp.appServiceDetailId}"/></td>
					<td><input type='text' id='stepDesc' style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;'
						value="${appServiceDetailTmp.stepDesc}"><span>*</span></td>
					<td><input type='text' id='calledSysId${ind.index}'
						placeHolder='请输入系统关键字' style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;'
						value="${editAppServiceIdAndName[ind.index]}"><span>*</span></td>
					<td><input type='text' id='calledAppIntf'
						style='width:250px; text-align:center; border:1px solid #a8a8a8; padding:7px 0;' value="${appServiceDetailTmp.calledAppIntf}"></td>
				</tr>
			</c:forEach> 
	    </table>

		<a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;" class="button border-blue" onclick="add()">添加步骤</a>
		<a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;" class="button border-blue" onclick="del()">删除步骤</a>
		<a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;" class="button border-blue" onclick="insertinto()">插入步骤</a>
		<a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;" class="button border-blue" onclick="moveUp()">步骤上移</a>
		<a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;" class="button border-blue" onclick="moveDown()">步骤下移</a>

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
		<input type="hidden" name="serviceName" value="${serviceName}">
		<input type="hidden" name="removeIndex" id="removeIndex">
		<input type="hidden" name="appServiceTmp.appServiceId" value="${appServiceTmp.appServiceId}">
		<input type="hidden" name="appServiceTmp.pubAppServiceId" value="${appServiceTmp.pubAppServiceId}">
		
		<span class="text-big" style="color:#CC3300" id="errorMsg">${resultHint}</span>

		</div>
	</s:form>

</body>
</html>