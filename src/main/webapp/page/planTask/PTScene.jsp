<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>计划任务现场负责人受理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<%--    <style type="text/css">--%>
<%--        .table th {--%>
<%--            font-weight: bold;--%>
<%--        }--%>

<%--        .table th,--%>
<%--        .table td {--%>
<%--            padding: 8px;--%>
<%--            line-height: 20px;--%>
<%--        }--%>

<%--        .table td {--%>
<%--            text-align: center;--%>
<%--        }--%>

<%--        .table-border {--%>
<%--            border-top: 1px solid #ddd;--%>
<%--        }--%>

<%--        .table-border th,--%>
<%--        .table-border td {--%>
<%--            border-bottom: 1px solid #ddd;--%>
<%--        }--%>

<%--        .table-bordered {--%>
<%--            border: 1px solid #ddd;--%>
<%--            border-collapse: separate;--%>
<%--            *border-collapse: collapse;--%>
<%--            border-left: 0;--%>
<%--        }--%>

<%--        .table-bordered th,--%>
<%--        .table-bordered td {--%>
<%--            border-left: 1px solid #ddd;--%>
<%--        }--%>

<%--        .table-border.table-bordered {--%>
<%--            border-bottom: 0;--%>
<%--        }--%>

<%--        .table-striped tbody > tr:nth-child(odd) > td,--%>
<%--        .table-striped tbody > tr:nth-child(odd) > th {--%>
<%--            background-color: #f9f9f9;--%>
<%--        }--%>

<%--        /* 文本超长class */--%>
<%--        .textEllipsis{--%>
<%--            /*color:red;*/--%>
<%--            overflow: hidden;/*不允许滚动条*/--%>
<%--            white-space: nowrap;/*不允许文本换行*/--%>
<%--            text-overflow: ellipsis;/*文本超长显示省略号*/--%>
<%--        }--%>

<%--        /* 鼠标移上,显示全文class */--%>
<%--        .textEllipsis:hover {--%>
<%--            height: auto;--%>
<%--            word-break:break-all;--%>
<%--            white-space: pre-wrap;--%>
<%--            text-decoration: none;--%>
<%--        }--%>
<%--    </style>--%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
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
        var str;
        var assign;
        var pageContext = "${pageContext.request.contextPath}";
        var fileBeforeID;
        var group_session = '${sessionInfo.groupId }';
        var deptValue_session = ${sessionDept.dept.deptID };
            assign = "PTEngineer";
            str = '受理';
        var sessionPid = ${sessionDept.dept.pid};


        $(function () {

            $("#btnSubmit").click(function () {
                $("#dlgSubmit").dialog("open");
            });

            $('#dept').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#PTEngineer').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'id',
                            // formatter: function(row){
                            //     row.id = row.firstName;
                            //     return row.id;
                            // },
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID='+assign,
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
            if ($("#comment").val() == "") {
                $.messager.alert('', '批注不能为空!', 'error');
                return;
            }
            var formData = $('#fm').serializeJSON();
            $.ajax({
                url: '${pageContext.request.contextPath}/planTaskTask/auditPTEngineer?state=' + state,
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (result) {
                    if (result.success) {
                        $.messager.confirm("系统提示", "提交成功！");
                        window.parent.refreshTabData(str, window.top.reload_factory);
                    } else {
                        $.messager.confirm("系统提示", "提交失败，请联系管理员！");
                        return;
                    }
                }
            });

        }

        // function closeTodo(){
        //     var currentTab = $('#tabs').tabs('getSelected');
        //     var currtab_title = currentTab.panel('options').title;
        //     $('#tabs').tabs('close', currtab_title);
        // }

        $(function () {
            var ss = [];

            $('#pg').propertygrid({
                // width: 100,
                // height: 'auto',
                showGroup: true,
                scrollbarSize: 10,
                fit: true,
                // fitColumns: true,
                // multiple: true,
                columns: [[
                    { field: 'name', title: '属性', width: 80, resizable: true },
                    { field: 'value', title: '值', width: 200, resizable: true }
                ]]
            });

            var ptID;
            $.post("${pageContext.request.contextPath}/planTask/getPlanTaskByTaskId", {taskId: '${param.taskId}'}, function (result) {
                planTask = result.planTask;
                var planTaskDate = new Date(planTask.ptdate.time)
                ss.push({"name":"姓名","value":"<label style='color: orange'>" + planTask.user.allName + "</label>","group":"提交人"});
                ss.push({"name":"申请日期","value":planTaskDate.format("Y-m-d H:i:s"),"group":"提交日期"});
                ss.push({"name":"计划单号","value":"<label style='color: red'>" + planTask.ptID + "</label>","group":"需求单"});
                ss.push({"name":"项目","value":planTask.project.projectName,"group":"需求单"});
                ss.push({"name":"系统","value":planTask.ptxitong.typeName,"group":"需求单"});
                ss.push({"name":"类目","value":planTask.ptleimu,"group":"需求单"});
                ss.push({"name":"任务名称","value":planTask.ptrwmc,"group":"需求单"});
                ss.push({"name":"图号","value":planTask.pttuhao,"group":"需求单"});
                ss.push({"name":"设备名称","value":planTask.ptsbmc,"group":"需求单"});
                ss.push({"name":"型号","value":planTask.ptxinghao,"group":"需求单"});
                ss.push({"name":"单位","value":planTask.ptdanwei.typeName,"group":"需求单"});
                ss.push({"name":"数量","value":planTask.ptsuliang,"group":"需求单"});
                ss.push({"name":"工种","value":planTask.ptgz,"group":"需求单"});
                ss.push({"name":"类型","value":planTask.ptlx.typeName,"group":"需求单"});
                ss.push({"name":"备注","value":planTask.ptbz,"group":"需求单"});
                $('#pg').propertygrid('loadData', ss);
                ptID = planTask.ptID;
            }, "json");

            $('#PTEngineer').combogrid({
                title:'计划任务设备工程师',
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

    </script>
</head>
<body class="easyui-layout" style="margin: 5px">
<div data-options="region:'east',title:'计划任务单信息',split:true"
     style="width: 400px;padding: 1px; background-color: #eee">
    <table id="pg"></table>
</div>

<div id="dlgSubmit" class="easyui-window" title="流程提交" style="width: 450px;height: 240px;" data-options="closed:true"
     align="center">
    <form id="fm">
        <table>
            <tr>
                <td>批注：</td>
                <td colspan="4">
                        <textarea id="comment" name="comment" rows="5" cols="25" class="easyui-validatebox"
                                  required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td class="datagrid-toolbar"></td>
                <td class="datagrid-toolbar"></td>
            </tr>
            <tr>
                <td>指定部门：</td>
                <td colspan="4">
                    <input id="dept" class="easyui-combotree" style="width:200px;"
                           data-options="editable:false,panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>指定审批人员：</td>
                <td colspan="4">
                    <select id="PTEngineer" type="text" name="userId"
                            data-options="editable:false,panelHeight:'auto'"
                            style="width: 200px;">
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" name="taskId" value="${param.taskId}"/>
                </td>
                <td colspan="4">
                    <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">批准</a>
                    &nbsp;&nbsp;
                    <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div data-options="region:'center',title:'处理/批注列表',split:true">
    <div id="editStorage">
        <a id="btnSubmit" class="easyui-linkbutton" iconCls="icon-save"
           plain="true">提交</a>
    </div>
    <div style="padding-top: 5px">
        <table id="dg" class="easyui-datagrid"
               fitColumns="true"
               url="${pageContext.request.contextPath}/task/listHistoryComment?taskId=${param.taskId}"
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
