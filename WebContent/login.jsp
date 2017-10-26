<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>架构资产管理工具</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
</head>
<body>
<div class="bg"></div>
<div class="container">
  <!-- 登录框抖动  <div class="line bouncein"> -->
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height:150px;"></div>
            <div class="media media-y margin-big-bottom">           
            </div>         
            <s:form id="formId" action="userlogin">
            <s:token></s:token>
            <div class="panel loginbox">
                <div class="text-center margin-big padding-big-top"><h1>架构资产管理工具</h1></div>
                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                    <div class="form-group">
                        <div class="field">
                            <input type="text" class="input input-big" name="username" placeholder="登录账号" data-validate="required:请填写账号" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field">
                            <input type="password" class="input input-big" name="passowrd" placeholder="登录密码" data-validate="required:请填写密码" />
                        </div>
                    </div>
                    

                </div>
                <div style="padding:30px;"><input type="submit" class="button button-block bg-main text-big input-big" value="登录"></div>
   				<div style="padding-bottom:20px;">
   					<span class="text-big" style="padding:50px;color:#CC3300">${loginResp}</span>
   				</div>
            </div>
            </s:form>          
        </div>
    </div>
</body>
</html>