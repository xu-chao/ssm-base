<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
<script type="text/javascript">
	var sessionPid = ${sessionDept.dept.pid};
	function searchUser(){
		$("#dg").datagrid('load',{
			"userID":$("#s_id").val(),
			"deptPid":sessionPid
		});
	}

	function openAuthDiglog(){
		var selectRows=$("#dg").datagrid("getChecked");
		if(selectRows.length!=1){
			$.messager.alert("系统提示","请选择一条要设置的用户！");
			return;
		}
		var row=selectRows[0];
		$("#dlg").dialog("open").dialog("setTitle","设置用户权限");
		loadAllGroups(); // 加载所有角色
		// setRoles(selectRows[0].id);
		url="${pageContext.request.contextPath}/userPark/updateUserPark?userID="+selectRows[0].userID;
	}
	function loadAllGroups(){
		// 自动补全
		$('#inputtable').combobox(
				{
					prompt : '输入关键字后自动搜索',
					url : '${pageContext.request.contextPath}/park/searchAllPark',// _url,_value已经在各自的js文件中定义
					valueField : 'parkID',
					textField : 'parkName',
					panelHeight : 'auto',
					panelMaxHeight : 150,
					editable : true,
				});
	}


	function closeAuthDialog(){
		$("#dlg").dialog("close");
	}

	function saveAuth(){
        var parkID =  $('#inputtable').combobox('getValue');
		$.post(url,{parkID:parkID},function(result){
			if(result.success){
				$.messager.alert("系统提示","提交成功！");
				closeAuthDialog();
				$("#dg").datagrid("reload");
			}else{
				// $.messager.alert("系统提示","提交失败，请联系管理员！");
				 $.messager.alert("系统提示",result.msg);
			}
		},"json");
	}

	$(document).ready(function () {
		$('#dg').datagrid({
			remoteSort:false,
			nowrap:false,
			singleSelect:true,
			url: '${pageContext.request.contextPath}/userPark/findAllUser',
			columns: [[
				{field: 'cb', checkbox: true, align: 'center'},
				{field: 'id', title: '用户名', width: 80, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.user) {
                            return row.user.id;
                        } else {
                            return "获取不到该权限名！";
                        }
                    }},
				{field: 'password', title: '密码', width: 80, align: 'center',
					formatter : function(value) {
						return "*********";
					}},
				{field: 'allName', title: '姓名', width: 80, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.user) {
                            return row.user.allName;
                        } else {
                            return "获取不到该姓名！";
                        }}},
				{field: 'phone', title: '手机号', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.user) {
                            return row.user.phone;
                        } else {
                            return "获取不到该名！";
                        }}},
				{field: 'email', title: '邮箱', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.user) {
                            return row.user.email;
                        } else {
                            return "获取不到该邮箱！";
                        }}},
				{field: 'groups', title: '拥有公园权限', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.park) {
                            return row.park.parkName;
                        } else {
                            return "没有分配公园！";
                        }}}
			]],
			queryParams:{deptPid:sessionPid},
			pageSize:20,
			sortName:'operation_Time',
			sortOrder:'desc',
		});
	})

</script>
</head>
<body style="margin: 1px">
<table id="dg" title="权限管理"
  fitColumns="true" pagination="true" rownumbers="true"
  fit="true" toolbar="#tb">
</table>
<div id="tb">
 <div>
	<a href="javascript:openAuthDiglog()" class="easyui-linkbutton" iconCls="icon-power" plain="true">公园分配</a>
 </div>
 <div>
 	&nbsp;用户名&nbsp;<input type="text" id="s_id" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
 	<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
 </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 450px;height: 200px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">

 	<div id="groupsList" style="padding: 10px">
		<input class="easyui-combobox" style="width: 300px;"  id="inputtable" editable="false"/>
	</div>

</div>

<div id="dlg-buttons">
	<a href="javascript:saveAuth()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeAuthDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>