<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>计划任务流程</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.html4.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.html5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.flash.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plupload/queue/jquery.plupload.queue.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/myplupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";

        function openUpFile() {
            $("#uploader").dialog("open").dialog("setTitle", "上传文件");
        }

        var deptValue_session = ${sessionDept.dept.deptID };
        var sessionPid = ${sessionDept.dept.pid};

        $(function () {
            $('#dept_1').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    value: deptValue_session,
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    // mode: 'remote',
                    "state":"closed",
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
                    // valueField: 'deptID',
                    // textField: 'deptName',
                    // panelHeight: 'auto',
                    // panelMaxHeight: 150,
                    // editable: true,
                    // hasDownArrow: false,
                    // onBeforeLoad: function (param) {
                    //     if (param == null || param.q == null
                    //         || param.q.replace(/ /g, '') == '') {
                    //         var value = $(this).combobox('getValue');
                    //         if (value) {// 修改的时候才会出现q为空而value不为空
                    //             param.id = value;
                    //             return true;
                    //         }
                    //         return false;
                    //     }
                    // },
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#PTScene_1').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'allName',
                            // formatter: function(row){
                            //     row.id = row.firstName;
                            //     return row.id;
                            // },
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=PTScene',
                            columns: [[
                                {field: 'cb', checkbox: true, align: 'center'},
                                {field: 'id', title: '用户名/工号', width: 100, align: 'center'},
                                {field: 'firstName', title: '姓', width: 100, align: 'center'},
                                {field: 'lastName', title: '名', width: 100, align: 'center'},
                                {field: 'email', title: '邮箱', width: 100, align: 'center'}
                            ]],
                        });
                    }
                });

            $('#dept_2').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    value: deptValue_session,
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    url: '${pageContext.request.contextPath}/dept/deptList',
                    onLoadSuccess:function(data){
                        $("#dept_2").combotree('tree').tree("collapseAll");
                    },
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#PTScene_2').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'allName',
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=PTScene',
                            columns: [[
                                {field: 'cb', checkbox: true, align: 'center'},
                                {field: 'id', title: '用户名/工号', width: 100, align: 'center'},
                                {field: 'firstName', title: '姓', width: 100, align: 'center'},
                                {field: 'lastName', title: '名', width: 100, align: 'center'},
                                {field: 'email', title: '邮箱', width: 100, align: 'center'}
                            ]],
                        });
                    }
                });
        });

        function formatAction(val, row) {
            if (row.state == '未提交') {
                return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlg3').dialog('open')\">提交申请</a>";
            } else if (row.state == '审核通过' || row.state == '审核未通过') {
                return "<a href='javascript:openListCommentDialog(" + row.processInstanceId + ")'>查看历史批注</a>";
            }
        }

        function openListCommentDialog(processInstanceId) {
            $("#dg2").datagrid("load", {
                processInstanceId: processInstanceId
            });
            $("#dlg2").dialog("open").dialog("setTitle", "查看历史批注");
        }

        function openPlanTaskAddDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "申请计划任务单");
        }

        function addPlanTask() {
            var formData = $('#fm').serializeJSON();
            $.ajax({
                url: '${pageContext.request.contextPath}/planTask/addPlanTask',
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    message = "新增成功";
                    $.messager.alert("提示", message, 'info', function () {
                        if (rtn.success) {
                            // 刷新表格数据
                            closePlanTaskDialog();

                        }
                    });
                }
            });
        }

        function resetValue() {
            // $('#repairDate').combo('setText', '');
            // $('#repairLevel').combobox('clear');
            // $("#repairPlace").val("");
            // $('#repairType').combobox('clear');
            // $("#repairContext").val("");
            // $('#recordDate').combo('setText', '');
            // $("#fileUUID").val("");
        }

        function closePlanTaskDialog() {
            $("#dlg").dialog("close");
            resetValue();
            setTimeout(function () {
                document.location.reload();//重载当前页面
            }, 500);

        }

        function closePDialog() {
            $("#dlg4").dialog("close");
            resetValue();
        }

        function startRedictApply() {
            var formData = $('#fm').serializeJSON();
            var isSend = $('input[name=isSend]:checked').val();
            $.ajax({
                url: "${pageContext.request.contextPath}/planTask/startRedictApply",
                data: formData,
                dataType: 'json',
                type: 'post',
                beforeSend: function () {
                    if (isSend == "true") {
                        // 禁用按钮防止重复提交
                        // $("#submit").attr("disabled",true);
                        $("#dlg4").dialog("open").dialog("setTitle", "邮件发送中...");
                        var value = 5;
                        $('#p').progressbar('setValue', value);
                        if (value < 100) {
                            ptime = window.setInterval(function () {
                                value += Math.floor(Math.random() * 10);
                                $('#p').progressbar('setValue', value);
                                if (value > 95) {
                                    window.clearInterval(ptime);
                                    $('#p').progressbar('setValue', 99);
                                }
                            }, 5000);
                        } else {
                            window.clearInterval(ptime);
                            $('#p').progressbar('setValue', 99);
                        }
                    } else {
                        return;
                    }
                },
                success: function (result) {
                    if (isSend == "true") {
                        if (ptime != null) {
                            console.log("error");
                            window.clearInterval(ptime);
                        }
                        if (result.success) {
                            if ($("#dlg4").parent().is(":hidden")) {
                                var audio = document.createElement("audio");
                                if (audio.canPlayType("audio/mp3")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                } else if (audio.canPlayType("audio/ogg")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                }
                                audio.play();
                                $("#dlg4").dialog("open").dialog("setTitle", "邮件发送成功！");
                                $('#p').progressbar('setValue', 100);
                                $("#dp").text("运维申请提交成功，目前审核中，请耐心等待！");
                            } else {
                                var audio = document.createElement("audio");
                                if (audio.canPlayType("audio/mp3")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                } else if (audio.canPlayType("audio/ogg")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                }
                                audio.play();
                                $("#dlg4").dialog("setTitle", "邮件发送成功！");
                                $('#p').progressbar('setValue', 100);
                                $("#dp").text("运维申请提交成功，目前审核中，请耐心等待！");
                            }
                            closePlanTaskDialog();
                            // $("#dg").datagrid("reload");
                        } else {
                            $("#dlg4").dialog("setTitle", "邮件发送失败！");
                            $('#p').progressbar('setValue', 0);
                            $("#dp").text("运维申请提交失败，请联系管理员！");
                            closePlanTaskDialog();
                            // $("#dg").datagrid("reload");
                        }
                    } else {
                        if (result.success) {
                            $.messager.alert("系统提示", "运维申请提交成功，目前审核中，请耐心等待！");
                            // $("#dg").datagrid("reload");
                            closePlanTaskDialog();
                        } else {
                            $.messager.alert("系统提示", "运维申请提交失败，请联系管理员！");
                        }
                    }
                }
            });
        }

        //提交运维流程申请
        function startApply() {
            var selectRows = $("#dg").datagrid("getSelections");
            var taskID = selectRows[0].ID;
            var userID = $('#PTScene_2').combobox('getValue');
            var userTask = {
                taskID: taskID,
                userID: userID
            }
            $.ajax({
                url: "${pageContext.request.contextPath}/planTask/startApply",
                data: userTask,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    if (rtn.success) {
                        $.messager.alert("系统提示", "运维申请提交成功，目前审核中，请耐心等待！");
                        $("#dg").datagrid("reload");
                        closePlanTaskDialog();
                    } else {
                        $.messager.alert("系统提示", "运维申请提交失败，请联系管理员！");
                    }
                }
            });
        }

        function closeImportClick() {
            document.getElementById('fileImport').value = null;
            document.getElementById('fileName').innerHTML = "";
            document.getElementById('uploadInfo').innerHTML = "";
            $('#import-excel-template').window('close')
        }

        $(document).ready(function () {

            $('#ptxitong').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=ptxitong',
                valueField: 'typeID',
                textField: 'typeName',
                onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                    var val = $(this).combobox("getData");
                    for (var item in val[0]) {
                        if (item == "typeID") {
                            $(this).combobox("select", val[0][item]);
                        }
                    }
                }
            }).combobox("clear");
            $('#ptdanwei').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=ptdanwei',
                valueField: 'typeID',
                textField: 'typeName',
                onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                    var val = $(this).combobox("getData");
                    for (var item in val[0]) {
                        if (item == "typeID") {
                            $(this).combobox("select", val[0][item]);
                        }
                    }
                }
            }).combobox("clear");
            $('#ptlx').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=ptlx',
                valueField: 'typeID',
                textField: 'typeName',
                onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                    var val = $(this).combobox("getData");
                    for (var item in val[0]) {
                        if (item == "typeID") {
                            $(this).combobox("select", val[0][item]);
                        }
                    }
                }
            }).combobox("clear");
            //设置公园选择，如果是管理员则可以选择所有，其他只能选择自己所属公园


            $('#parkIDSelect').combobox({
                disabled: false,
                url: pageContext + '/park/searchAllPark',
                valueField: 'parkID',
                textField: 'parkName',
                onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                    var val = $(this).combobox("getData");
                    for (var item in val[0]) {
                        if (item == "parkName") {
                            $(this).combobox("select", val[0][item]);
                        }
                    }
                },
                onSelect: function (record) {
                    var parkID = record.parkID;
                    $('#projectIDSelect').combobox({
                        disabled: false,
                        url: pageContext + '/project/findProjectByParkID?parkID=' + parkID,
                        valueField: 'projectID',
                        textField: 'projectName',
                        onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                            var val = $(this).combobox("getData");
                            for (var item in val[0]) {
                                if (item == "parkID") {
                                    $(this).combobox("select", val[0][item]);

                                }
                            }
                        }
                    }).combobox("clear");
                }
            }).combobox("clear");


            $('#dg').datagrid({
                remoteSort: false,
                singleSelect: true,
                striped: true,
                nowrap: true,
                url: '${pageContext.request.contextPath}/planTask/planTaskPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'ptID', title: '计划任务单号', width: 100, align: 'center'},
                    {
                        field: 'ProjectID', title: '项目名', width: 100, align: 'center',
                        formatter: function (val, row, index) {
                            if (row.project == null) {
                                return "";
                            } else {
                                return row.project.projectName;
                            }
                        }
                    },
                    {
                        field: 'ptxitong', title: '改造类型', width: 100, align: 'center',
                        formatter: function (val, row, index) {
                            if (row.ptxitong == null) {
                                return "";
                            } else {
                                return row.ptxitong.typeName;
                            }
                        }
                    },
                    {field: 'ptleimu', title: '类目', width: 100, align: 'center'},
                    {field: 'ptrwmc', title: '任务名称', width: 100, align: 'center'},
                    {field: 'pttuhao', title: '图号', width: 100, align: 'center'},
                    {field: 'ptsbmc', title: '设备名称', width: 100, align: 'center'},
                    {field: 'ptxinghao', title: '任务名称', width: 100, align: 'center'},
                    {field: 'pttuhao', title: '型号', width: 100, align: 'center'},
                    {
                        field: 'ptdanwei', title: '单位', width: 100, align: 'center',
                        formatter: function (val, row, index) {
                            if (row.ptdanwei == null) {
                                return "";
                            } else {
                                return row.ptdanwei.typeName;
                            }
                        }
                    },
                    {field: 'ptsuliang', title: '数量', width: 100, align: 'center'},
                    {field: 'ptgz', title: '工种', width: 100, align: 'center'},
                    {
                        field: 'ptlx', title: '改造类型', width: 100, align: 'center',
                        formatter: function (val, row, index) {
                            if (row.ptlx == null) {
                                return "";
                            } else {
                                return row.ptlx.typeName;
                            }
                        }
                    },
                    {field: 'ptbz', title: '备注', width: 100, align: 'center'},
                    {field: 'ptdate', title: '日期', width: 100, align: 'center'},
                    {field: 'state', title: '审核状态', width: 100, align: 'center'},
                    {field: 'processInstanceId', title: '流程实例Id', width: 100, align: 'center'},
                    {field: 'action', title: '操作', width: 100, align: 'center', formatter: formatAction}
                ]],
                // queryParams:{},
                pageSize: 20,
                sortName: 'ptdate',
                sortOrder: 'desc',
            });

            $('#PTScene_1').combogrid({
                title: '计划任务现场负责人',
                panelWidth: 500,
                remoteSort: false,
                nowrap: false,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                striped: true,
            });

            $('#PTScene_2').combogrid({
                title: '计划任务现场负责人',
                panelWidth: 500,
                remoteSort: false,
                nowrap: false,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                striped: true,
            });

            $('#p').progressbar({
                text: '{value}%'
            });
            $("#dp").text("邮件服务启动，请不要重复操作!");
        })

    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="计划任务申请管理"
       fitColumns="true" pagination="true" rownumbers="true" fit="true" toolbar="#tb">
</table>
<div id="tb">
    <div>
        <a href="javascript:openPlanTaskAddDialog()" class="easyui-linkbutton" iconCls="icon-add"
           plain="true">申请计划任务</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 400px;height: 480px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">

    <%-- 	<form id="fm" method="post" enctype="multipart/form-data" >--%>
    <form id="fm" method="post">
        <table style="margin:5px;height:70px;">


            <tr>
                <td>工程名称：</td>
                <td>
                    <input type="text" id="parkIDSelect" style="width:200px;" name="park.parkID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td>项目名称：</td>
                <td>
                    <input type="text" id="projectIDSelect" style="width:200px;" data-options="panelHeight:'auto'"
                           name="project.projectID" class="easyui-combobox" required="true" />
                </td>
            </tr>
            <tr>
                <td>改造类型：</td>
                <td>
                    <input type="text" id="ptxitong" style="width:200px;" name="ptxitong.typeID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            </tr>
            <tr>
                <td>类目：</td>
                <td>
                    <input id="ptleimu" maxlength="50" style="width:200px;" name="ptleimu"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>任务名称：</td>
                <td>
                    <input id="ptrwmc" maxlength="50" style="width:200px;" name="ptrwmc"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>图号：</td>
                <td>
                    <input id="pttuhao" maxlength="50" style="width:200px;" name="pttuhao"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>设备名称：</td>
                <td>
                    <input id="ptsbmc" maxlength="50" style="width:200px;" name="ptsbmc"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>型号：</td>
                <td>
                    <input id="ptxinghao" maxlength="50" style="width:200px;" name="ptxinghao"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>单位：</td>
                <td>
                    <input type="text" id="ptdanwei" style="width:200px;" name="ptdanwei.typeID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            <tr>
                <td>数量：</td>
                <td>
                    <input id="ptsuliang" maxlength="50" style="width:200px;" name="ptsuliang"
                           class="easyui-numberbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>工种：</td>
                <td>
                    <input id="ptgz" maxlength="50" style="width:200px;" name="ptgz"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>类型：</td>
                <td>
                    <input type="text" id="ptlx" style="width:200px;" name="ptlx.typeID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            <tr>
            <tr>
                <td>备注：</td>
                <td>
                    <input type="hidden" name="user.id" value="${sessionInfo.userId }"/>
                    <input id="ptbz" maxlength="50" style="width:200px;" name="ptbz"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>


<%--            <tr>--%>
<%--                <td>部门：</td>--%>
<%--                <td>--%>
<%--                    <input id="dept_1" class="easyui-combotree" style="width:200px;">--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>提交计划任务现场负责人：</td>--%>
<%--                <td>--%>
<%--                    <select id="PTScene_1" type="text" name="PTScene"--%>
<%--                            style="width: 200px;">--%>
<%--                    </select>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>是否Email通知：</td>--%>
<%--                <td>--%>
<%--                    <span>--%>
<%--                        <input class="isSend" type="radio" name="isSend" value=true>是</input>--%>
<%--                        <input class="isSend" type="radio" name="isSend" value=false checked="true">否</input>--%>
<%--                    </span>--%>
<%--                </td>--%>
<%--            </tr>--%>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:addPlanTask()" class="easyui-linkbutton" iconCls="icon-save">暂存</a>
<%--    <a id="submit" href="javascript:startRedictApply()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>--%>
    <a href="javascript:closePlanTaskDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>操作
</div>

<div id="dlg3" class="easyui-window" title="选择提交人" style="width: 360px;height: 180px;" data-options="closed:true"
     align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_2" class="easyui-combotree" style="width:200px;"
                           data-options="editable:false,panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <div class="datagrid-toolbar"></div>
            </tr>
            <tr>
                <td>提交计划任务现场负责人：</td>
                <td>
                    <select id="PTScene_2" type="text" name="PTScene" data-options="editable:false,panelHeight:'auto'"
                            style="width: 200px;">
                    </select>
                </td>
            </tr>
        </table>
        <br/>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:startApply()">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg3').dialog('close')">关闭</a>
    </form>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 750px;height: 250px;padding: 10px 20px" closed="true">
    <table id="dg2" title="批注列表" class="easyui-datagrid" fitColumns="true"
           url="${pageContext.request.contextPath}/task/listHistoryCommentWithProcessInstanceId"
           style="width: 700px;height: 200px;">
        <thead>
        <tr>
            <th field="time" width="120" align="center">批注时间</th>
            <th field="userId" width="100" align="center">批注人</th>
            <th field="message" width="200" align="center">批注信息</th>
        </tr>
        </thead>
    </table>
</div>

<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>

<div id="dlg4" class="easyui-dialog" style="width: 260px;height: 165px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons4">
    <div id="p" style="width:200px;"></div>
    <div><span id="dp"></span></div>
</div>

<div id="dlg-buttons4">
    <a href="javascript:closePDialog()" class="easyui-linkbutton" iconCls="icon-ok">确认并关闭</a>
    <audio id="audio"></audio>
</div>
</body>
</html>