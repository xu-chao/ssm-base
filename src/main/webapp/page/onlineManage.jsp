<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统日志管理</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/datagrid-detailview.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript">

        var name;
        var startDate;
        var endDate;

        function searchOperationRecord() {
            name = $("#s_name").val();
            startDate = $('#s_startDate').datebox('getValue');
            endDate = $('#s_endDate').datebox('getValue');
            if ((startDate == '') && (endDate == '')) {
                $("#dg").datagrid('load', {
                    "userName": name
                });
            } else if ((startDate == '') || (endDate == '')) {
                $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
            } else if(startDate > endDate){
                $.messager.alert('警告', '结束时间不能早于开始时间!');
            } else {
                $("#dg").datagrid('load', {
                    "userName": name,
                    "startDate": startDate,
                    "endDate": endDate,
                });
            }
        }

        function resetSearchOperationRecord(){
            $("#s_name").val("");
            $("#s_startDate").combo('setText','');
            $("#s_startDate").combo('setValue','');
            $("#s_endDate").combo('setText','');
            $("#s_endDate").combo('setValue','');
            searchOperationRecord();
        }

        function formatAction(val,row){
            return "<a href='javascript:openOnlineDetail(\""+row.sessionID+"\")'>详情</a>&nbsp;<a href='#'>踢出</a>";
        }

        function openOnlineDetail(sessionID){
            $("#dlg").dialog("open").dialog("setTitle", "详细情况");
        }

        $(document).ready(function () {
            $('#s_startDate').datetimebox({
                required: false,
                showSeconds: true,
                editable: false,
                onSelect: function (date) {
                    $('#s_endDate').datetimebox('enable');	//启用结束日期控件
                }
            });

            $('#s_endDate').datetimebox({
                required: false,
                showSeconds: true,
                editable: false,
                disabled: true,
                validType: 'compareDate[\'#s_startDate\']'
            });

            $.extend($.fn.validatebox.defaults.rules, {
                compareDate: {
                    validator: function (value, param) {
                        var d1 = $(param[0]).datetimebox('getValue');  //获取开始时间
                        return value >= d1;  //有效范围为大于开始时间
                    },
                    message: '结束时间不能早于开始时间!'
                }
            });

            $('#dg').datagrid({
                remoteSort:false,
                //singleSelect:true,
                nowrap:false,
                url: '${pageContext.request.contextPath}/member/online',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'sessionID', title: 'SessionID', width: 100, align: 'center'},
                    {field: 'userName', title: '用户名', width: 100, align: 'center'},
                    {field: 'email', title: '邮箱', width: 100, align: 'center'},
                    {field: 'startTime', title: '创建时间', width: 100, align: 'center'},
                    {field: 'lastAccess', title: '会话最后活动时间', width: 100, align: 'center'},
                    {field: 'sessionStatus', title: '状态', width: 100, align: 'center'},
                    {field: 'operation', title: '操作', width: 100, align: 'center', formatter:formatAction}
                ]],
            });
        })
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="在线用户管理:这里是在线已经登录的有效Session，不能等同于当前在线用户，使用Redis技术控制。" fit="true" fitColumns="true" pagination="true" rownumbers="true"
       toolbar="#tb"></table>
<div id="tb">
    <div>
        &nbsp;用户名&nbsp;<input type="text" id="s_name" size="10"
                               onkeydown="if(event.keyCode==13) searchOperationRecord()"/>
        &nbsp;最后活动时间&nbsp;<input type="text" id="s_startDate" size="20"
                               onkeydown="if(event.keyCode==13) searchOperationRecord()"/>--
        <input type="text" id="s_endDate" size="20" onkeydown="if(event.keyCode==13) searchOperationRecord()"/>
        <a href="javascript:searchOperationRecord()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        <a href="javascript:resetSearchOperationRecord()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 400px;height: 250px;padding: 10px 20px" closed="true">
    <form id="fm" method="post">
        <table id="systemTable" class="table table-border table-bordered table-striped" style="width: 100%"
               cellpadding="8">
            <tr>
                <td width="200">SessionID</td>
                <td width="350">
                    <span>9a8efa38-4a19-4c6e-9e66-a4e422609728</span>
                </td>
            </tr>
            <tr>
                <td>Session创建时间</td>
                <td>
                    <span id="name">2019-07-26 05:45:59</span>
                </td>
            </tr>
            <tr>
                <td>Session最后交互时间</td>
                <td>
                    <span id="email">2019-07-26 05:45:59</span>
                </td>
            </tr>
            <tr>
                <td>Session状态</td>
                <td>
                    <span>有效</span>
                </td>
            </tr>
            <tr>
                <td>Session Host</td>
                <td>
                    <span>127.0.0.1</span>
                </td>
            </tr>
            <tr>
                <td>Session TimeOut</td>
                <td>
                    <span>1800000 (毫秒) = 1800(秒) = 30(分钟)</span>
                </td>
            </tr>
            <tr>
                <td>姓名</td>
                <td>
                    <span>admin</span>
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td>
                    <span>123@qq.com</span>
                </td>
            </tr>
            <tr>
                <td>创建时间</td>
                <td>
                    <span>2019-07-26 05:45:59</span>
                </td>
            </tr>
            <tr>
                <td>最后登录时间</td>
                <td>
                    <span>2019-07-26 05:45:59</span>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>