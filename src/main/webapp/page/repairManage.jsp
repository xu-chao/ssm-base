<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>申故流程</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.html4.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.html5.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/plupload.flash.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/plupload/zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plupload/queue/jquery.plupload.queue.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/myplupload.js"></script>
    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";

        function openUpFile() {
            $("#uploader").dialog("open").dialog("setTitle", "上传文件");
        }

        var deptValue_session = ${sessionDept.dept.deptID };
        var sessionPid = ${sessionDept.dept.pid};

        $(function(){
            $('#dept_1').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    value:deptValue_session,
                    required:true,
                    missingMessage:'部门名称不能为空!',
                    // mode: 'remote',
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
                    onSelect:function(record) {
                        var deptID= record.id;
                        $('#projectSupervisor_1').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField:'id',
                            // formatter: function(row){
                            //     row.id = row.firstName;
                            //     return row.id;
                            // },
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=ProjectSupervisor',
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
                    value:deptValue_session,
                    required:true,
                    missingMessage:'部门名称不能为空!',
                    url: '${pageContext.request.contextPath}/dept/deptList',
                    onSelect:function(record) {
                        var deptID= record.id;
                        $('#projectSupervisor_2').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField:'id',
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=ProjectSupervisor',
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

        function formatAction(val,row){
            if(row.state=='未提交'){
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
            $("#dlg").dialog("open").dialog("setTitle","申请故障单");
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
                            // 刷新表格数据
                            closeRepairDialog();

                        }
                    });
                }
            });
        }

        function resetValue(){
            $('#repairDate').combo('setText','');
            $('#repairLevel').combobox('clear');
            $("#repairPlace").val("");
            $('#repairType').combobox('clear');
            $("#repairContext").val("");
            $('#recordDate').combo('setText','');
            $("#fileUUID").val("");
        }

        function closeRepairDialog(){
            $("#dlg").dialog("close");
            resetValue();
            setTimeout (function(){
                document.location.reload();//重载当前页面
            },2000);

        }

        function closePDialog(){
            $("#dlg4").dialog("close");
            resetValue();
        }

        function startRedictApply(){
            var formData = $('#fm').serializeJSON();
            var isSend = $('input[name=isSend]:checked').val();
            $.ajax({
                url : "${pageContext.request.contextPath}/repair/startRedictApply",
                data : formData,
                dataType : 'json',
                type : 'post',
                beforeSend: function(){
                    if(isSend == "true"){
                        // 禁用按钮防止重复提交
                        // $("#submit").attr("disabled",true);
                        $("#dlg4").dialog("open").dialog("setTitle","邮件发送中...");
                        var value = 5;
                        $('#p').progressbar('setValue',value);
                        if(value < 100){
                            ptime = window.setInterval(function(){
                                value += Math.floor(Math.random() * 10);
                                $('#p').progressbar('setValue', value);
                                if(value > 95){
                                    window.clearInterval(ptime);
                                    $('#p').progressbar('setValue', 99);
                                }
                            },5000);
                        }else {
                            window.clearInterval(ptime);
                            $('#p').progressbar('setValue', 99);
                        }
                    }else {
                        return;
                    }
                },
                success : function(result) {
                    if(isSend == "true"){
                        if(ptime != null){
                            console.log("error");
                            window.clearInterval(ptime);
                        }
                        if (result.success) {
                            if($("#dlg4").parent().is(":hidden")){
                                var audio = document.createElement("audio");
                                if (audio.canPlayType("audio/mp3")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                }else if(audio.canPlayType("audio/ogg")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                }
                                audio.play();
                                $("#dlg4").dialog("open").dialog("setTitle","邮件发送成功！");
                                $('#p').progressbar('setValue', 100);
                                $("#dp" ).text("运维申请提交成功，目前审核中，请耐心等待！");
                            }else {
                                var audio = document.createElement("audio");
                                if (audio.canPlayType("audio/mp3")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                }else if(audio.canPlayType("audio/ogg")) {
                                    audio.src = "${pageContext.request.contextPath}/static/ogg/email.wav";
                                }
                                audio.play();
                                $("#dlg4").dialog("setTitle","邮件发送成功！");
                                $('#p').progressbar('setValue', 100);
                                $("#dp" ).text("运维申请提交成功，目前审核中，请耐心等待！");
                            }
                            closeRepairDialog();
                            // $("#dg").datagrid("reload");
                        } else {
                            $("#dlg4").dialog("setTitle","邮件发送失败！");
                            $('#p').progressbar('setValue', 0);
                            $("#dp" ).text("运维申请提交失败，请联系管理员！");
                            closeRepairDialog();
                            // $("#dg").datagrid("reload");
                        }
                    }else {
                        if (result.success) {
                            $.messager.alert("系统提示","运维申请提交成功，目前审核中，请耐心等待！");
                            // $("#dg").datagrid("reload");
                            closeRepairDialog();
                        }else{
                            $.messager.alert("系统提示","运维申请提交失败，请联系管理员！");
                        }
                    }
                }
            });
        }

        //提交运维流程申请
        function startApply(){
            var selectRows = $("#dg").datagrid("getSelections");
            var taskID = selectRows[0].id;
            var userID = $('#projectSupervisor_2').combobox('getValue');
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
                        $.messager.alert("系统提示","运维申请提交成功，目前审核中，请耐心等待！");
                        $("#dg").datagrid("reload");
                        closeRepairDialog();
                    }else{
                        $.messager.alert("系统提示","运维申请提交失败，请联系管理员！");
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
                striped:true,
                nowrap:false,
                url: '${pageContext.request.contextPath}/repair/repairPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'id', title: '单号', width: 100, align: 'center',sortable:true},
                    {field: 'repairDate', title: '维修日期', width: 100, align: 'center',sortable:true},
                    {field: 'repairLevel', title: '严重程度', width: 100, align: 'center',
                        // formatter: function (val, row, index) {
                        //     if(val == 0){
                        //         return "一般修理";
                        //     }else if(val == 1){
                        //         return "复杂修理";
                        //     }
                        // }
                    },
                    {field: 'repairType', title: '故障类型', width: 100, align: 'center',
                        // formatter: function (val, row, index) {
                        //     if(val == 0){
                        //         return "投影";
                        //     }else if(val == 1){
                        //         return "音响";
                        //     }else if(val == 2){
                        //         return "灯光";
                        //     }else if(val == 3){
                        //         return "机械";
                        //     }else if(val == 4){
                        //         return "电气";
                        //     }else if(val == 5){
                        //         return "软件";
                        //     }else if(val == 6){
                        //         return "液压";
                        //     }else if(val == 7){
                        //         return "效果";
                        //     }else if(val == 7){
                        //         return "电脑";
                        //     }else if(val == 8){
                        //         return "其他";
                        //     }
                        // }
                    },
                    {field: 'repairContext', title: '维修内容', width: 100, align: 'center'},
                    {field: 'recordDate', title: '记录时间', width: 100, align: 'center',sortable:true},
                    {field: 'state', title: '审核状态', width: 100, align: 'center'},
                    {field: 'processInstanceId', title: '流程实例Id', width: 100, align: 'center'},
                    {field: 'action', title: '操作', width: 100, align: 'center', formatter: formatAction}
                ]],
                // queryParams:{},
                pageSize:20,
                sortName:'repairDate',
                sortOrder:'desc',
            });

            $('#projectSupervisor_1').combogrid({
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

            $('#projectSupervisor_2').combogrid({
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

            $('#p').progressbar({
                text:'{value}%'
            });
            $("#dp" ).text("邮件服务启动，请不要重复操作!");
        })

    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="故障申请管理"
       fitColumns="true" pagination="true" rownumbers="true" fit="true" toolbar="#tb">
</table>
<div id="tb">
    <div>
        <a href="javascript:openRepairAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">申请故障单</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 400px;height: 480px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">

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
                <td>故障填报时间：</td>
                <td>
                    <a href="#" title="不填写，默认Now" class="easyui-tooltip">
                        <input type="text" id="recordDate" style="width:200px;"
                               name="recordDate" class="easyui-validatebox"/></a>
                </td>
            </tr>
            <tr>
                <td>故障发生地点：</td>
                <td>
                    <input type="text" id="repairPlace" style="width:200px;" name="repairPlace" class="easyui-textbox easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>故障程度：</td>
                <td>
                    <select id="repairLevel" class="easyui-combobox" style="width:200px;" name="repairLevel" panelMaxHeight="200px" data-options="editable:false,panelHeight:'auto'">
                        <option value="一般修理">一般修理</option>
                        <option value="复杂修理">复杂修理</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>故障类别：</td>
                <td>
                    <select id="repairType" class="easyui-combobox" style="width:200px;" name="repairType" panelMaxHeight="200px" data-options="editable:false,panelHeight:'auto'">
                        <option value="投影">投影</option>
                        <option value="音响">音响</option>
                        <option value="灯光">灯光</option>
                        <option value="机械">机械</option>
                        <option value="电气">电气</option>
                        <option value="软件">软件</option>
                        <option value="液压">液压</option>
                        <option value="效果">效果</option>
                        <option value="电脑">电脑</option>
                        <option value="其他">其他</option>
                    </select>
                </td>
            </tr>
<%--            <tr>--%>
<%--                <td>现场图片：</td>--%>
<%--                <td><input type="text" style="width:200px;"><input type="button" onclick="openUpFile()" value="选择图片"></td>--%>
<%--            </tr>--%>
            <tr>
                <td valign="top">故障描述：</td>
                <td>
                    <input type="hidden" name="userId" value="${sessionInfo.userId }"/>
                    <input type="hidden" name="state" value="未提交"/>
                    <textarea type="text" id="repairContext" style="width:200px;" name="repairContext"  rows="5" cols="50" class="easyui-validatebox" required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td>请选择文件：</td>
                <td><input type="button" onclick="openUpFile()" value="上传文件"></td>
            </tr>
            <tr>
                <td colspan="4">
                    <input id="fileUUID" type="hidden" name="repairFileID"/>
                    <label id="uploadInfo" />
                </td>
            </tr>
            <tr>
                <td class="datagrid-toolbar"></td>
                <td class="datagrid-toolbar"></td>
            </tr>
            <tr></tr>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_1" class="easyui-combotree" style="width:200px;">
                </td>
            </tr>
            <tr>
                <td>提交项目负责人：</td>
                <td>
                    <select id="projectSupervisor_1"  type="text" name="projectSupervisor"
                            style="width: 200px;">
                    </select>
                </td>
            </tr>
            <tr>
                <td>是否Email通知：</td>
                <td>
                    <span>
                        <input class="isSend" type="radio" name="isSend" value=true>是</input>
                        <input class="isSend" type="radio" name="isSend" value=false checked="true">否</input>
                    </span>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:addRepair()" class="easyui-linkbutton" iconCls="icon-save">暂存</a>
    <a id="submit" href="javascript:startRedictApply()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
    <a href="javascript:closeRepairDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div id="dlg3" class="easyui-window" title="选择提交人" style="width: 360px;height: 180px;" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_2" class="easyui-combotree" style="width:200px;" data-options="editable:false,panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <div class="datagrid-toolbar"></div>
            </tr>
            <tr>
                <td>提交项目负责人：</td>
                <td>
                    <select id="projectSupervisor_2"  type="text" name="projectSupervisor" data-options="editable:false,panelHeight:'auto'"
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

<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>

<div id="dlg4" class="easyui-dialog" style="width: 260px;height: 165px;padding: 10px 20px" closed="true" buttons="#dlg-buttons4">
    <div id="p" style="width:200px;"></div>
    <div><span id="dp"></span></div>
</div>

<div id="dlg-buttons4">
    <a href="javascript:closePDialog()" class="easyui-linkbutton" iconCls="icon-ok">确认并关闭</a>
    <audio id="audio"></audio>
</div>
</body>
</html>