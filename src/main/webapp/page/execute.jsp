<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>维修人员执行派工单</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
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
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
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
    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";
        var repairFileID;
        var group_session = '${sessionInfo.groupId }';
        var deptValue_session = ${sessionDept.dept.deptID };
        var str = '';
        if(group_session == 'ProjectSupervisor'){
            str = '审核并派单';
        }else if(group_session == 'Maintainer'){
            str = '执行工单';
        }else if(group_session == 'OperationalManagers'){
            str = '查看信息';
        }

        function submit(state) {
            var data = $('#ff').serializeJSON();
            $.ajax({
                url: "${pageContext.request.contextPath}/repairTask/auditProjectSupervisor?state=" + state,
                type: "post",
                data: data,
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        $.messager.alert("系统提示", "提交成功！");
                        setTimeout (function(){
                            window.parent.refreshTabData(str, window.top.reload_taskTab);
                        },2000);
                    } else {
                        $.messager.alert("系统提示", "提交失败，请联系管理员！");
                        return;
                    }
                },
                error: function () {
                    $.messager.alert("系统提示", "系统异常！");
                    return;
                }
            });
        }

        $(function () {
            $('#woStartDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $.post("${pageContext.request.contextPath}/repair/getRepairByTaskId", {taskId: '${param.taskId}'}, function (result) {
                var repair = result.repair;
                var dept = result.dept;
                state = repair.state;
                if(state == "主管项目负责人根据返单重新派工"){
                    var temp = $('#body').layout('panel','center');
                    temp.panel('setTitle','维修人员执行派工单（主管项目负责人根据返单重新派工）');
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
        });

        function formatItem(row) {
            var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
                '<span style="color:#888">' + row.desc + '</span>';
            return s;
        }

        function openUpFunction() {
            $("#uploader").dialog("open").dialog("setTitle", "上传文件");
        }
        function openUpFile() {
            $("#uploaderFile").dialog("open").dialog("setTitle", "下载文件");
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
                        field: 'create_time', title: '文件日期', width: 100, align: 'center',
                        formatter: function (value, row, index) {
                            var time_form = new Date(value.time);
                            return time_form.format("Y-m-d H:i:s");
                        },
                    },
                    {
                        field: 'path', title: '图片显示', width: 200, align: 'center',
                        formatter: function (value, row, index) {
                            return '<img src="${pageContext.request.contextPath}'+value+'" alt="非图片" height="200" width="300" class="slide-image">';
                        },
                    },
                    {
                        field: 'ed', title: '操作', width: 80, align: 'center',
                        formatter: function (value, rowData, rowIndex) {
                            return  '<a href=\'#\' onclick=editRow(\''+rowData.path+'\') class=\'btn btn-info btn-sm pull-right\'>'+ '<img src="${pageContext.request.contextPath}/static/images/download.png">'+ '</ a>';
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
            form.attr("action","${pageContext.request.contextPath}/repair/downloadFile");// 文件下载提交路径
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
<div id="uploaderFile" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>
<div data-options="region:'center',title:'维修人员执行派工单'"
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
                    <td valign="top">故障描述：</td>
                    <td colspan="4">
                        <span id="repairContext" rows="2" cols="50"></span>
                    </td>
                </tr>
            </table>
        </form>
        <hr />
        <form id="ff" method="post">
            <table class="table table-border table-bordered table-striped" style="width: 100%" title="故障反馈单">
                <tr>
                    <td>维修开始时间：</td>
                    <td colspan="4">
                        <input type="text" id="woStartDate" name="woStartDate"
                               class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>工单类型：</td>
                    <td>
                        <input class="easyui-combobox" name="woType" data-options="
				               url: '${pageContext.request.contextPath}/static/mock/mock_repair.json',
				               valueField: 'id',
				               textField: 'text',
				               panelWidth: 180,
				               panelHeight: 'auto',
				               formatter: formatItem
			            ">
                    </td>
                    <td>物料名称：</td>
                    <td>
                        <input type="text" id="materielID" name="materielID"
                               class="easyui-validatebox"/>
                    </td>
                </tr>
                <tr>
                    <td>物料类型：</td>
                    <td>
                        <input type="text" id="materielType" name="materielType"
                               class="easyui-validatebox"/>
                    </td>
                    <td>需要维修协助人：</td>
                    <td>
                        <input type="text" id="repairedHelper" name="repairedHelper"
                               class="easyui-validatebox"/>
                    </td>
                </tr>
                <tr>
                    <td>维修过程：</td>
                    <td colspan="4">
                    <textarea id="repairedProcess" name="repairedProcess" rows="2" cols="50"
                              class="easyui-validatebox"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>反馈：</td>
                    <td colspan="4">
                        <textarea id="comment" name="comment" rows="2" cols="50" class="easyui-validatebox"
                                  required="true"></textarea>
                    </td>
                </tr>

                <tr>
                    <td>上传文件：</td>
                    <td colspan="4">
                        <a class="easyui-linkbutton" onclick="openUpFunction();">上传</a>
                        <input id="fileUUID" type="hidden" name="repairedFileID"/>
                        <label id="uploadInfo"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="taskId" value="${param.taskId}"/>
                    </td>
                    <td colspan="4">
                        <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">完成</a>&nbsp;&nbsp;
                        <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">未完成</a>&nbsp;&nbsp;
                        <a class="easyui-linkbutton" onclick="openUpFile();">附件-现场图片</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
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
                <th field="time" width="30" align="center">批注时间</th>
                <th field="userId" width="30" align="center">批注人</th>
                <th field="message" width="40" align="center">批注信息</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 337px" closed="true">&nbsp;</div>
</body>
</html>