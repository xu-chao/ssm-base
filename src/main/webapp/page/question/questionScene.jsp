<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>负责人审批</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
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

        /* 文本超长class */
        .textEllipsis{
            /*color:red;*/
            overflow: hidden;/*不允许滚动条*/
            white-space: nowrap;/*不允许文本换行*/
            text-overflow: ellipsis;/*文本超长显示省略号*/
        }

        /* 鼠标移上,显示全文class */
        .textEllipsis:hover {
            height: auto;
            word-break:break-all;
            white-space: pre-wrap;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
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
            assign = "QuestionExaminer";
            str = '审核并派单';
        var sessionPid = ${sessionDept.dept.pid};
        var sessionFile = "${sessionFile}";

        $(function () {
            $('#dept').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#QuestionExaminer').combogrid({
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
            $("#fm").form("submit", {
                url: '${pageContext.request.contextPath}/questionTask/auditOperators?state=' + state,
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
        //     var currentTab = $('#tabs').tabs('getSelected');
        //     var currtab_title = currentTab.panel('options').title;
        //     $('#tabs').tabs('close', currtab_title);
        // }

        $(function () {
            $.post("${pageContext.request.contextPath}/question/getQuestionByTaskId", {taskId: '${param.taskId}'}, function (result) {
                var question = result.question;
                fileBeforeID = question.fileBeforeID;
                $("#userName").text(question.user.firstName + question.user.lastName);
                var questionDate = new Date(question.questionDate.time);
                $("#questionDate").text(questionDate.format("Y-m-d H:i:s"));
                $("#subjectName").text(question.dept.deptName);
                $("#subjectID").text(question.subjectID)
                $("#phone").text(question.user.phone);
                $("#email").text(question.user.email);;
                $("#position").text(question.user.position);
                $("#parkID").text(question.park.parkID);
                $("#projectID").text(question.project.projectID);
                $("#problemText").text(question.problemText);
                $("#questionID").text(question.questionID);
                $("#problemText").text(question.problemText);
                $("#problemTitle").text(question.problemTitle);
                $("#taskId").text(${param.taskId});
            }, "json");

            $('#QuestionExaminer').combogrid({
                title:'审批人',
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

        function openBeforeFile() {
            $("#uploaderOne").dialog("open").dialog("setTitle", "下载文件");
            $('#findFiles').datagrid({
                remoteSort: false,
                singleSelect: false,
                striped: true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/fileInfo/findFiles?fileID=' + fileBeforeID,
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'remark', title: '文件名', width: 100, align: 'center'},
                    {
                        field: 'path', title: '图片显示', width: 200, align: 'center',
                        formatter: function (value, row, index) {
                            return '<img src="'+sessionFile + value + '"   alt = "非图片" height="200" width="300" class="slide-image">';
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
            form.attr("action","${pageContext.request.contextPath}/fileInfo/downloadFile");    // 此处填写文件下载提交路径
            var input1=$("<input>");
            input1.attr("type","hidden");
            input1.attr("name","path");    // 后台接收参数名
            input1.attr("value",path);
            $("body").append(form);    // 将表单放置在web中
            form.append(input1);
            form.submit();    // 表单提交

        }
        function openUpFile() {
            $("#uploader").dialog("open").dialog("setTitle", "上传文件");
        }
    </script>
</head>
<body class="easyui-layout" style="margin: 5px">
<div data-options="region:'center',title:'现场人反馈后审批'"
     style="width: 600px;padding: 4px; background-color: #eee">
    <div id="p">
        <form id="fm" method="post">
            <table class="table table-border table-bordered table-striped" style="width: 100%">
                <tr>
                    <td>单号：</td>
                    <td colspan="4">
                        <span id="questionID"></span>
                    </td>
                    <td>流程ID：</td>
                    <td colspan="4">
                        <span id="taskId"></span>
                    </td>
                </tr>
                <tr>
                    <td>申请人：</td>
                    <td colspan="4">
                        <span id="userName"></span>
                    </td>
                    <td>申请时间：</td>
                    <td colspan="4">
                        <span id="questionDate"></span>
                    </td>
                </tr>
                <tr>
                    <td>部门：</td>
                    <td colspan="4">
                        <span id="subjectName"></span>
                    </td>
                    <td>职位：</td>
                    <td colspan="4">
                        <span id="position"></span>
                    </td>
                </tr>
                <tr>
                    <td>手机号码：</td>
                    <td colspan="4">
                        <span id="phone"></span>
                    </td>
                    <td>Email：</td>
                    <td colspan="4">
                        <span id="email"></span>
                    </td>
                </tr>
                <tr>
                    <td>工程名称：</td>
                    <td colspan="4">
                        <span id="parkID"></span>
                    </td>
                    <td>项目名称：</td>
                    <td colspan="4">
                        <span id="projectID"></span>
                    </td>
                </tr>
                <tr>
                    <td>专业名称：</td>
                    <td colspan="4">
                        <span id="subjectID"></span>
                    </td>
                    <td>问题标题：</td>
                    <td colspan="4">
                        <span id="problemTitle"></span>
                    </td>
                </tr>
                <tr>
                    <td valign="top">问题描述：</td>
                    <td colspan="9" class="textEllipsis">
                        <span id="problemText" rows="2" cols="110"></span>
                    </td>
                </tr>
                <tr>
                    <td>批注：</td>
                    <td colspan="9">
                        <textarea id="comment" name="comment" rows="2" cols="49" class="easyui-validatebox"
                                  required="true"></textarea>
                    </td>
                </tr>
                <tr>
                    <td valign="top">指定部门：</td>
                    <td colspan="9">
                        <input id="dept" class="easyui-combotree" style="width:200px;" required="true">
                    </td>
                </tr>
                <tr>
                    <td valign="top">指定审批人员：</td>
                    <td colspan="9">
                        <select id="QuestionExaminer" type="text" name="userId"
                                style="width: 200px;" required="true">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>附属图片或文件：</td>
                    <td colspan="9">
                        <a  class="easyui-linkbutton" onclick="openUpFile()">反馈后上传</a>
                        &nbsp;&nbsp;
                        <a class="easyui-linkbutton" onclick="openBeforeFile();">反馈前文件</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="10">
                        <input id="fileUUID" type="hidden" name="fileAfterID"/>
                        <label id="uploadInfo"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" name="taskId" value="${param.taskId}"/>
                    </td>
                    <td colspan="9">
                        <a href="javascript:submit(1)" class="easyui-linkbutton" iconCls="icon-ok">批准并派单</a>
                        &nbsp;&nbsp;
                        <a href="javascript:submit(2)" class="easyui-linkbutton" iconCls="icon-no">驳回</a>

                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="uploaderOne" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>
<div data-options="region:'east',title:'批注列表',split:true"
     style="width: 500px;">
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
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>
</body>
</html>
