<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>派单流程</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
<script type="text/javascript">

	function formatAction(val,row){
		if(row.state=='未提交'){
			// return "<a href='javascript:startApply("+row.id+")'>提交申请</a>";
            return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlg3').dialog('open')\">提交申请</a>";
		}else if(row.state=='审核通过' || row.state=='审核未通过'){
			return "<a href='javascript:openListCommentDialog("+row.processInstanceId+")'>查看历史批注</a>";
		}
	}
	
	function openListCommentDialog(processInstanceId){
		$("#dg2").datagrid("load",{
			processInstanceId:processInstanceId
		});
		$("#dlg2").dialog("open").dialog("setTitle","查看历史批注");
	}
	
	function openRepairAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","申请派工单");
	}

    function addRepair(){
        var formData = $('#fm').serializeJSON();
        $.ajax({
            url : '${pageContext.request.contextPath}/repair/addRepair',
            data : formData,
            dataType : 'json',
            type : 'post',
            success : function(rtn) {
                message = "新增成功";
                $.messager.alert("提示", message , 'info', function() {
                    if (rtn.success) {
                        // 成功的话，我们要关闭窗口
                        $('#editDlg').dialog('close');
                        // 刷新表格数据
                        $('#dg').datagrid('reload');
                    }
                });
            }
        });
    }
	
	<%--function saveLeave(){--%>
	<%--	//获取上传文件控件内容--%>
	<%--	var file = document.getElementById('fileImport').files[0];--%>
	<%--	//判断控件中是否存在文件内容，如果不存在，弹出提示信息，阻止进一步操作--%>
	<%--	if (file == null) { alert('错误，请选择文件'); return; }--%>
	<%--	//获取文件名称--%>
	<%--	var fileName = file.name;--%>
	<%--	//获取文件类型名称--%>
	<%--	var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);--%>
	<%--	//这里限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息--%>
	<%--	if (file_typename == '.xlsx'||file_typename =='.PDF'||file_typename =='.pdf'||file_typename=='.xls')--%>
	<%--	{--%>
	<%--		//计算文件大小--%>
	<%--		var fileSize = 0;--%>
	<%--		//如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB--%>
	<%--		if (file.size > 1024 * 1024) {--%>
	<%--			fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;--%>
	<%--			if (fileSize > 10) {--%>
	<%--				alert('错误，文件超过10MB，禁止上传！'); return;--%>
	<%--			}--%>
	<%--			fileSize = fileSize.toString() + 'MB';--%>
	<%--		}--%>
	<%--		else {--%>
	<%--			fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';--%>
	<%--		}--%>
	<%--		//将文件名和文件大小显示在前端label文本中--%>
	<%--		document.getElementById('fileName').innerHTML = "<span style='color:Blue'>文件名: " + file.name + ',大小: ' + fileSize + "</span>";--%>
	<%--		//获取form数据--%>
	<%--		$("#fm").form("submit",{--%>
	<%--			url:'${pageContext.request.contextPath}/leave/save.action',--%>
	<%--			onSubmit:function(){--%>
	<%--				return $(this).form("validate");--%>
	<%--			},--%>
	<%--			success:function(result){--%>
	<%--				var result=eval('('+result+')');--%>
	<%--				if(result.success){--%>
	<%--					$.messager.alert("系统系统","保存成功！");--%>
	<%--					resetValue();--%>
	<%--					$("#dlg").dialog("close");--%>
	<%--					$("#dg").datagrid("reload");--%>
	<%--				}else{--%>
	<%--					$.messager.alert("系统系统","保存失败！");--%>
	<%--					return;--%>
	<%--				}--%>
	<%--			}--%>
	<%--		});--%>
	<%--	}--%>
	<%--	else {--%>
	<%--		alert("文件类型错误");--%>
	<%--		//将错误信息显示在前端label文本中--%>
	<%--		document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示:上传文件应该是.xlsx.或者PDF后缀而不应该是" + file_typename + ",请重新选择文件</span>"--%>
	<%--	}--%>

	<%--}--%>

	
	function resetValue(){
		$("#leaveDays").val("");
		$("#leaveReason").val("");
	}
	
	function closeLeaveDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}

	//提交运维流程申请
	function startApply(){
        var selectRows = $("#dg").datagrid("getSelections");
        var taskID = selectRows[0].id;
        var userID = $('#projectSupervisor').combobox('getValue');
        var userTask ={
            taskID:taskID,
            userID:userID
        }
        $.ajax({
            url : "${pageContext.request.contextPath}/repair/startApply",
            data : userTask,
            dataType : 'json',
            type : 'post',
            success : function(rtn) {
                if (rtn.success) {
                    $.messager.alert("系统系统","运维申请提交成功，目前审核中，请耐心等待！");
                    $("#dg").datagrid("reload");
                }else{
                    $.messager.alert("系统系统","运维申请提交失败，请联系管理员！");
                }
            }
        });
	}
	//导入事件，显示导入弹出窗口
	// this.importClick = function ()
	//  function importFileClick()
	// {
	// 	debugger;
	// 	$('#import-excel-template').window('open')
	// 	document.getElementById("importFileForm").style.display = "block";
	// }
	//关闭导入弹出窗口
	// this.closeImportClick = function () {
	 function closeImportClick() {
		document.getElementById('fileImport').value = null;
		document.getElementById('fileName').innerHTML = "";
		document.getElementById('uploadInfo').innerHTML = "";
		$('#import-excel-template').window('close')
	}

	$(document).ready(function () {

		$('#repairDate').datetimebox({
			required: true,
			showSeconds: true,
			editable: false,
		});

        $('#recordDate').datetimebox({
            required: false,
            showSeconds: true,
            editable: false,
            // prompt:'不填写，默认Now',
        });

		$('#dg').datagrid({
			remoteSort:false,
			singleSelect:true,
			nowrap:false,
			url: '${pageContext.request.contextPath}/workOrder/workOrderPage',
			columns: [[
				{field: 'cb', checkbox: true, align: 'center'},
				{field: 'id', title: '编号', width: 100, align: 'center',sortable:true},
				{field: 'woStartDate', title: '工单开始时间', width: 100, align: 'center',sortable:true},
				{field: 'woType', title: '工单类别', width: 100, align: 'center'},
				{field: 'materielID', title: '物料型号', width: 100, align: 'center'},
				{field: 'materielType', title: '物料类型', width: 100, align: 'center'},
                {field: 'repairedPerson', title: '维修负责人', width: 100, align: 'center'},
                {field: 'repairedHelper', title: '维修协助人', width: 100, align: 'center'},
                {field: 'repairedProcess', title: '维修过程', width: 100, align: 'center'},
                {field: 'woEndDate', title: '工单结束时间', width: 100, align: 'center'},
				{field: 'state', title: '审核状态', width: 100, align: 'center'},
				{field: 'processInstanceId', title: '流程实例Id', width: 100, align: 'center'},
				{field: 'action', title: '操作', width: 100, align: 'center', formatter: formatAction}
			]],
			// queryParams:{},
			pageSize:20,
			sortName:'woStartDate',
			sortOrder:'desc',
		});
	})

</script>
</head>
<body style="margin: 1px">
<table id="dg" title="运维管理"
  fitColumns="true" pagination="true" rownumbers="true" fit="true" toolbar="#tb">
</table>
<%--<div id="tb">--%>
<%-- <div>--%>
<%--	<a href="javascript:openRepairAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">申请派工单</a>--%>
<%-- </div>--%>
<%--</div>--%>

<div id="dlg" class="easyui-dialog" style="width: 620px;height: 480px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">
 
<%-- 	<form id="fm" method="post" enctype="multipart/form-data" >--%>
    <form id="fm" method="post">
 		<table  style="margin:5px;height:70px;">
 			<tr>
 				<td>故障发生时间：</td>
 				<td>
 					<input type="text" id="repairDate" style="width:200px;" name="repairDate" class="easyui-validatebox" required="true"/>
 				</td>
 			</tr>
			<tr>
				<td>故障发生地点：</td>
				<td>
					<input type="text" id="repairPlace" style="width:200px;" name="repairPlace" class="easyui-validatebox" required="true"/>
				</td>
			</tr>
            <tr>
                <td>故障程度：</td>
                <td>
                    <select id="repairLevel" class="easyui-combobox" style="width:200px;" name="repairLevel" panelMaxHeight="200px" data-options="editable:false,panelHeight:'auto'">
                        <option value="0">一般修理</option>
                        <option value="1">复杂修理</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>故障类别：</td>
                <td>
                    <select id="repairType" class="easyui-combobox" style="width:200px;" name="repairType" panelMaxHeight="200px" data-options="editable:false,panelHeight:'auto'">
                        <option value="0">电气</option>
                        <option value="1">软件</option>
                    </select>
                </td>
            </tr>
 			<tr>
 				<td valign="top">故障描述：</td>
 				<td>
                    <input type="hidden" name="userId" value="${sessionInfo.userId }"/>
                    <input type="hidden" name="state" value="未提交"/>
 					<textarea type="text" id="repairContext" style="width:200px;" name="repairContext"  rows="5" cols="50" class="easyui-validatebox" required="true"></textarea>
 				</td>
 			</tr>
<%--            <tr>--%>
<%--                <td>上传现场图片：</td>--%>
<%--                <td><input type="file" id="repairImageID" name="repairImageID" style="width:200px;" class="easyui-validatebox"></td>--%>
<%--            </tr>--%>
<%--			<tr>--%>
<%--				<td>请选择文件：</td>--%>
<%--				<td><input type="file" id="repairFileID" name="repairFileID" style="width:200px;" class="easyui-validatebox"></td>--%>
<%--            </tr>--%>
            <tr>
                <td>上传现场图片：</td>
                <td><input type="text" id="repairImageID" name="repairImageID" style="width:200px;" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>请选择文件：</td>
                <td><input type="text" id="repairFileID" name="repairFileID" style="width:200px;" class="easyui-validatebox"></td>
            </tr>
            <tr>
                <td>故障填报时间：</td>
                <td>
                    <a href="#" title="不填写，默认Now" class="easyui-tooltip">
                        <input type="text" id="recordDate" style="width:200px;"
                               name="recordDate" class="easyui-validatebox"/></a>
                </td>
            </tr>
		</table>
 	</form>
</div>
<div id="dlg-buttons">
	<a href="javascript:addRepair()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeLeaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div id="dlg3" class="easyui-dialog" title="选择提交人员" closed="true" style="width:400px;height:200px;padding:10px"
     data-options="iconCls: 'icon-save',toolbar: '#dlg3-toolbar',buttons: '#dlg3-buttons'">
    <td>提交人：</td>
    <td>
        <select id="projectSupervisor" class="easyui-combobox" style="width:200px;" name="projectSupervisor" panelMaxHeight="200px"
                data-options="
                    editable:false,
                    panelHeight:'auto',
                    value:'---请选择---',
                    url:'${pageContext.request.contextPath}/repair/findProjectSupervisorList',
					valueField:'id',
					textField:'firstName',">
        </select>
    </td>
</div>
<div id="dlg3-toolbar" style="padding:2px 0">
    <table cellpadding="0" cellspacing="0" style="width:100%">
        <tr>
            <td style="padding-left:2px">
<%--                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>--%>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">帮助</a>
            </td>
<%--            <td style="text-align:right;padding-right:2px">--%>
<%--                <input class="easyui-searchbox" data-options="prompt:'请输入...'" style="width:150px"></input>--%>
<%--            </td>--%>
        </tr>
    </table>
</div>
<div id="dlg3-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:startApply()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg3').dialog('close')">关闭</a>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 750px;height: 250px;padding: 10px 20px" closed="true" >
 	<table id="dg2" title="批注列表" class="easyui-datagrid" fitColumns="true"
           url="${pageContext.request.contextPath}/task/listHistoryCommentWithProcessInstanceId" style="width: 700px;height: 200px;">
        <thead>
        <tr>
            <th field="time" width="120" align="center">批注时间</th>
            <th field="userId" width="100" align="center">批注人</th>
            <th field="message" width="200" align="center">批注信息</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>