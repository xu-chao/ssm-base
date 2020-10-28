<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>主管项目负责人审批</title>
	<link rel="stylesheet" type="text/css"
		  href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css"
		  href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <style type="text/css">
        .table th {
            font-weight: bold;
        }

        .table th,
        .table td {
            padding: 8px;
            line-height: 20px;
        }

        .table td {
            text-align: center;
        }

        .table-border {
            border-top: 1px solid #ddd;
        }

        .table-border th,
        .table-border td {
            border-bottom: 1px solid #ddd;
        }

        .table-bordered {
            border: 1px solid #ddd;
            border-collapse: separate;
            *border-collapse: collapse;
            border-left: 0;
        }

        .table-bordered th,
        .table-bordered td {
            border-left: 1px solid #ddd;
        }

        .table-border.table-bordered {
            border-bottom: 0;
        }

        .table-striped tbody > tr:nth-child(odd) > td,
        .table-striped tbody > tr:nth-child(odd) > th {
            background-color: #f9f9f9;
        }
    </style>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
	<script type="text/javascript">
		var str = '';
		var repairFileID;
		var state;
		var group_session = '${sessionInfo.groupId }';
		var deptValue_session = ${sessionDept.dept.deptID };
		if(group_session == 'ProjectSupervisor'){
			str = '审核并派单';
		}else if(group_session == 'Maintainer'){
			str = '执行工单';
		}else if(group_session == 'OperationalManagers'){
			str = '查看信息';
		}
		$(function () {
			$('#dept').combotree(
					{
						// prompt: '输入关键字后自动搜索',
						value: deptValue_session,
						required: true,
						missingMessage: '部门名称不能为空!',
						// mode: 'remote',
						url: '${pageContext.request.contextPath}/dept/deptList',
						onSelect: function (record) {
							var deptID = record.id;
							$('#Maintainer').combogrid({
								method: 'POST',
								idField: 'id',
								textField: 'id',
								// formatter: function(row){
								//     row.id = row.firstName;
								//     return row.id;
								// },
								url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=Maintainer',
								columns: [[
									{field: 'cb', checkbox: true, align: 'center'},
									{field: 'id', title: '用户名/工号', width: 100, align: 'center'},
									{field: 'firstName', title: '姓', width: 100, align: 'center'},
									{field: 'lastName', title: '名', width: 100, align: 'center'},
									{field: 'email', title: '邮箱', width: 100, align: 'center'}
								]]
							});
						}
					});
		});

		function submit(state) {
			$("#fm").form("submit", {
				url: '${pageContext.request.contextPath}/repairTask/auditOperators?state=' + state,
				onSubmit: function () {
					return $(this).form("validate");
				},
				success: function (result) {
					var result = eval('(' + result + ')');
					if (result.success) {
						$.messager.alert("系统提示", "提交成功！");
						window.parent.refreshTabData(str, window.top.reload_taskTab);
                        // closeTodo();
					} else {
						$.messager.alert("系统提示", "提交失败，请联系管理员！");
						return;
					}
				}
			});
		}

        // function closeTodo(){
		//     debugger
        //     var currentTab = $('#tabs').tabs('getSelected');
        //     var currtab_title = currentTab.panel('options').title;
        //     $('#tabs').tabs('close', currtab_title);
        // }

		$(function () {
			$.post("${pageContext.request.contextPath}/repair/getRepairByTaskId", {taskId: '${param.taskId}'}, function (result) {
				var repair = result.repair;
				var dept = result.dept;
				state = repair.state;
				if(state == "维修人员未完成维修"){
					var temp = $('#body').layout('panel','center');
					temp.panel('setTitle','主管项目负责人审批（维修人员未完成维修返单）');
				}
				repairFileID = repair.repairFileID;
				$("#userName").text(repair.user.firstName + repair.user.lastName);
				var recordDate = new Date(repair.recordDate.time);
                $("#deptment").text(dept.deptName);
				$("#position").text(repair.user.position);
				$("#phone").text(repair.user.phone);
				$("#email").text(repair.user.email);
				$("#recordDate").text(recordDate.format("Y-m-d H:i:s"));
				var repairDate = new Date(repair.repairDate.time);
				$("#repairDate").text(repairDate.format("Y-m-d H:i:s"));
				$("#repairPlace").text(repair.repairPlace);
				$("#repairLevel").text(repair.repairLevel);
				$("#repairType").text(repair.repairType);
				$("#repairContext").text(repair.repairContext);
			}, "json");

			$('#Maintainer').combogrid({
				title:'项目主管负责人',
				panelWidth: 500,
				remoteSort: false,
				nowrap: false,
				fitColumns: true,
				pagination: true,
				rownumbers:true,
				fitColumns: true,
				striped:true,
			});
		});

		function openUpFile() {
			$("#uploader").dialog("open").dialog("setTitle", "下载文件");
			$('#findFiles').datagrid({
				remoteSort: false,
				singleSelect: false,
				striped: true,
				nowrap: false,
				url: '${pageContext.request.contextPath}/repair/findFiles?repairFileID=' + repairFileID,
				columns: [[
					{field: 'cb', checkbox: true, align: 'center'},
					{field: 'remark', title: '文件名', width: 100, align: 'center'},
					{
						field: 'path', title: '图片显示', width: 200, align: 'center',
						formatter: function (value, row, index) {
							return '<img src="${pageContext.request.contextPath}'+value+'"  alt = "非图片" height="200" width="300" class="slide-image">';
						},
					},
					{
						field: 'create_time', title: '文件日期', width: 100, align: 'center',
						formatter: function (value, row, index) {
							var time_form = new Date(value.time);
							return time_form.format("Y-m-d H:i:s");
						},
					},
					{
						field: 'ed', title: '操作', width: 80, align: 'center',
						formatter: function (value, rowData, rowIndex) {
							return  '<a onclick=editRow(\''+rowData.path+'\') class=\'btn btn-info btn-sm pull-right\'>'+ '<img src="${pageContext.request.contextPath}/static/images/download.png">'+ '</ a>';
						}
					}
				]],
				sortName: 'remark',
				sortOrder: 'desc',
                toolbar: [{
                    text: '全部下载',
                    iconCls: 'icon-ok',
                    handler: function () {
                        var rows = $("#findFiles").datagrid("getRows");
                        //这段代码是获取当前页的所有行。
                        for (var i = 0; i < rows.length; i++) {
                            //获取每一行的数据
                            editRow(rows[i].path);
                        }
                    }
                }]
			});
		}

		function editRow(path) {
			var form=$("<form>");    // 定义一个form表单
			form.attr("style","display:none");
			form.attr("target","_blank");
			form.attr("method","post");
			form.attr("action","${pageContext.request.contextPath}/repair/downloadFile");    // 此处填写文件下载提交路径
			var input1=$("<input>");
			input1.attr("type","hidden");
			input1.attr("name","path");    // 后台接收参数名
			input1.attr("value",path);
			$("body").append(form);    // 将表单放置在web中
			form.append(input1);
			form.submit();    // 表单提交

		}
	</script>
</head>
<body id="body" class="easyui-layout" style="margin: 5px">
<div data-options="region:'center',title:'主管项目负责人审批'"
	 style="width: 600px;padding: 4px; background-color: #eee">
	<div id="p">
		<form id="fm" method="post">
			<table class="table table-border table-bordered table-striped" style="width: 100%">
				<tr>
					<td>申请人：</td>
					<td>
						<span id="userName"></span>
					</td>
					<td>申请时间：</td>
					<td>
						<span id="recordDate"></span>
					</td>
				</tr>
                <tr>
                    <td>部门：</td>
                    <td>
                        <span id="deptment"></span>
                    </td>
                    <td>职位：</td>
                    <td>
                        <span id="position"></span>
                    </td>
                </tr>
                <tr>
                    <td>手机号码：</td>
                    <td>
                        <span id="phone"></span>
                    </td>
                    <td>Email：</td>
                    <td>
                        <span id="email"></span>
                    </td>
                </tr>
				<tr>
					<td>故障发生时间：</td>
					<td>
                        <span id="repairDate"></span>
					</td>
					<td>故障发生地点：</td>
					<td>
						<span id="repairPlace"></span>
					</td>
				</tr>
				<tr>
					<td>故障程度：</td>
					<td>
                        <span id="repairLevel"></span>
					</td>
					<td>故障类别：</td>
					<td>
                        <span id="repairType"></span>
					</td>
				</tr>
				<tr>
					<td>故障描述：</td>
					<td colspan="4">
                        <span id="repairContext" rows="2" cols="50"></span>
					</td>
				</tr>
				<tr>
					<td>批注：</td>
					<td colspan="4">
                        <textarea id="comment" name="comment" rows="2" cols="49" class="easyui-validatebox"
								  required="true"></textarea>
					</td>
				</tr>
				<tr id="tr1">
					<td>指定部门：</td>
					<td colspan="4">
						<input id="dept" class="easyui-combotree" style="width:200px;">
					</td>
				</tr>
				<tr id="tr2">
					<td>指定维修人员：</td>
					<td colspan="4">
						<select id="Maintainer" type="text" name="userId"
								style="width: 200px;">
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" name="taskId" value="${param.taskId}"/>
					</td>
					<td colspan="4">
						<a id="submit" href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">批准并派单
<%--                           <c:if test="${state == '维修人员未完成维修'} || ${state == '审核中'}">--%>
<%--                               批准并派单--%>
<%--                           </c:if>--%>
<%--                           <c:if test="${state == '维修人员已完成维修'}">--%>
<%--                                批准并结单--%>
<%--                           </c:if>--%>
<%--							<c:if test="empty ${state }">--%>
<%--								0--%>
<%--							</c:if>--%>
<%--							<c:if test="not empty ${state }">--%>
<%--								${state }--%>
<%--							</c:if>--%>
                        </a>
                        &nbsp;&nbsp;
						<a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>
                        &nbsp;&nbsp;
						<a class="easyui-linkbutton" onclick="openUpFile();">文件下载</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
	<table id="findFiles"
		   fitColumns="true" fit="true" toolbar="#tb">
	</table>
</div>
<div data-options="region:'east',title:'批注列表',split:true"
	 style="width: 500px;">
	<div style="padding-top: 5px">
		<table id="dg" class="easyui-datagrid"
			   fitColumns="true"
			   rownumbers="true"
			   url="${pageContext.request.contextPath}/repairTask/listHistoryComment?taskId=${param.taskId}"
			   style="height: 200px;">
			<thead>
			<tr>
				<th field="time" width="40" align="center">批注时间</th>
				<th field="userId" width="20" align="center">批注人</th>
				<th field="message" width="40" align="center">批注信息</th>
			</tr>
			</thead>
		</table>
	</div>
</div>
</body>
</html>
