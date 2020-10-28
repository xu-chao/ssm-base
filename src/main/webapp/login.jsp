<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运管维系统-登录界面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/login/css/slide1.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var pageContext = "${pageContext.request.contextPath}";
	function submitData(){
		$("#fm").form("submit",{
			url:"${pageContext.request.contextPath}/user/userLogin.action",
			onSubmit:function(){
				if($("#groupId").combobox("getValue")=="请选择"){
					$.messager.alert("系统提示","请选择用户角色！");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					window.location.href="${pageContext.request.contextPath}/main.jsp";
				}else{
					$.messager.alert("系统提示",result.errorInfo);
					return;
				}
			}
		});
	}

	function resetValue(){
		$("#userName").val("");
		$("#password").val("");
		$("#groupId").combobox("setValue","");
	}
</script>
</head>
<body>
<div style="position: absolute;width: 100%;height: 100%;z-index: -1;left: 0;top: 0">
	<img src="${pageContext.request.contextPath}/static/images/login_bg.jpg" width="100%" height="100%" style="left: 0;top: 0;">
</div>
<div class="easyui-window" title="运管维流程" data-options="modal:true,closable:false,collapsible:false,minimizable:false,maximizable:false" style="width: 400px;height: 280px;padding: 10px">

	<div class="comImageValidate rightValidate">
		<div class="hkinnerWrap" style="height: 33px;position: relative">
			<span  class="v_rightBtn "><em class="notSel">→</em></span>
			<span class="huakuai"  style="font-size: 12px;line-height: 33px;color: #A9A9A9;">向右滑动滑块填充拼图</span>
			<input type = "hidden" name="validX"/>
		</div>
		<div class="imgBg">
			<div class="imgBtn">
				<img />
			</div>
			<span class="refresh">刷新</span>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/login/js/jquery.lgyslide1.js"></script>

	<form id="fm" action="" method="post">
		<table cellpadding="6px" align="center">
			<tr align="center">
				<th colspan="2" style="padding-bottom: 10px"><big>用户登录</big></th>
			</tr>
			<tr>
				<td>用户名：</td>
				<td>
					<input type="text" id="userName" name="userName" class="easyui-validatebox" required="true" style="width: 200px"/>
				</td>
			</tr>
			<tr>
				<td>密码：</td>
				<td>
					<input type="password" id="password" name="password" class="easyui-validatebox" required="true" style="width: 200px"/>
				</td>
			</tr>
			<tr>
				<td>角色：</td>
				<td>
					<input  id="groupId" name="groupId" class="easyui-combobox" data-options="panelHeight:'auto',valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/group/findGroup.action'" value="请选择"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<a href="javascript:submitData()" class="easyui-linkbutton" iconCls="icon-submit">登录</a>&nbsp;
					<a href="javascript:resetValue()" class="easyui-linkbutton" iconCls="icon-reset">重置</a>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>