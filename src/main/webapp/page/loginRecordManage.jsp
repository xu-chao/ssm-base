<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录日志管理</title>
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
        var id;
        var startDate;
        var endDate;

        function searchOperationRecord() {
            id = $("#s_id").val();
            startDate = $('#s_startDate').datebox('getValue');
            endDate = $('#s_endDate').datebox('getValue');
            if ((startDate == '') && (endDate == '')) {
                $("#dg").datagrid('load', {
                    "userID": id
                });
            } else if ((startDate == '') || (endDate == '')) {
                $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
            } else if(startDate > endDate){
                $.messager.alert('警告', '结束时间不能早于开始时间!');
            } else {
                $("#dg").datagrid('load', {
                    "userID": id,
                    "startDate": startDate,
                    "endDate": endDate,
                });
            }
        }

        function resetSearchOperationRecord(){
            $("#s_id").val("");
            $("#s_startDate").combo('setText','');
            $("#s_startDate").combo('setValue','');
            $("#s_endDate").combo('setText','');
            $("#s_endDate").combo('setValue','');
            searchOperationRecord();
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
                url: '${pageContext.request.contextPath}/operationRecord/operationRecordPage.action',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'record_ID', title: '记录ID', width: 100, align: 'center',sortable:true},
                    {field: 'user_ID', title: '用户ID', width: 100, align: 'center',sortable:true},
                    {field: 'user_Name', title: '用户名', width: 100, align: 'center'},
                    {field: 'operation_Name', title: '操作记录', width: 100, align: 'center'},
                    {
                        field: 'operation_Time', title: '操作时间', width: 100, align: 'center',sortable:true,
                        formatter: function (val, row, index) {
                            var now = new Date(val);
                            return now.format("Y-m-d H:i:s");
                        }
                    },
                    {field: 'operation_Result', title: '操作结果', width: 100, align: 'center'}
                ]],
                // queryParams:{},
                pageSize:20,
                sortName:'operation_Time',
                sortOrder:'desc',
                // view: detailview,
                // detailFormatter: function(rowIndex, rowData){
                //     return '<table><tr>' +
                //         '<td rowspan=2 style="border:0"><img src="images/' + rowData.itemid + '.png" style="height:50px;"></td>' +
                //         '<td style="border:0">' +
                //         '<p>Attribute: ' + rowData.attr1 + '</p>' +
                //         '<p>Status: ' + rowData.status + '</p>' +
                //         '</td>' +
                //         '</tr></table>';
                // }
            });
        })
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="系统日志管理" fit="true" fitColumns="true" pagination="true" rownumbers="true"
       toolbar="#tb"></table>
<div id="tb">
    <div>
        &nbsp;用户ID&nbsp;<input type="text" id="s_id" size="10"
                               onkeydown="if(event.keyCode==13) searchOperationRecord()"/>
        &nbsp;操作时间&nbsp;<input type="text" id="s_startDate" size="20"
                               onkeydown="if(event.keyCode==13) searchOperationRecord()"/>--
        <input type="text" id="s_endDate" size="20" onkeydown="if(event.keyCode==13) searchOperationRecord()"/>
        <a href="javascript:searchOperationRecord()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        <a href="javascript:resetSearchOperationRecord()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a>
    </div>
</div>

</body>
</html>