<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会议室流程</title>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";

        function openUpFile() {
            $("#uploader").dialog("open").dialog("setTitle", "上传文件");
        }

        function openUpExists() {
            $("#uploaderExists").dialog("open").dialog("setTitle", "上传文件");
        }

        var deptValue_session = ${sessionDept.dept.deptID };
        var sessionParkID = ${sessionPark.park.parkID};
        var sessionParkName = "${sessionPark.park.parkName}";
        var sessionPid = 0;
        var sessionFile = "${sessionFile}";
        // var deptfirst = deptValue_session.toString().substr(0,1);//根据用户部门获取用户的中心
        // if (deptfirst!="0") {//等于0为可浏览全部部门
        //     deptfirst = deptfirst+"00";
        // }

        $(function () {
            $('#p').progressbar({
                text: '{value}%'
            });
            $("#dp").text("邮件服务启动，请不要重复操作!");

            $('#dept_1').combotree(
                {
                    // prompt: '输入关键字后自动搜索',
                    // value: deptValue_session,
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    panelHeight : '250',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
                    onSelect: function (record) {
                        var deptID = record.id
                        $('#HuiyishiAdviser_1').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'allName',
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=HYSexaminer',
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
                    // value: deptValue_session,
                    required: true,
                    missingMessage: '部门名称不能为空!',
                    panelHeight:"250",
                    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,
                    onSelect: function (record) {
                        var deptID = record.id;
                        $('#HuiyishiAdviser_2').combogrid({
                            method: 'POST',
                            idField: 'id',
                            textField: 'id',
                            url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID + '&groupID=HYSexaminer',
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

            <%--$('#huiyishiDeptID').combobox({--%>
            <%--    disabled: false,--%>
            <%--    url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + 200,--%>
            <%--    valueField: 'id',--%>
            <%--    textField: 'text',--%>
            <%--    onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项--%>
            <%--        var val = $(this).combobox("getData");--%>
            <%--        for (var item in val[0]) {--%>
            <%--            if (item == "id") {--%>
            <%--                $(this).combobox("select", val[0][item]);--%>
            <%--            }--%>
            <%--        }--%>
            <%--    }--%>
            <%--}).combobox("clear");--%>

        });

        function formatAction(val, row) {
            if (row.state == '未提交') {
                return "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"huiyishiEdit()\">编辑</a>&nbsp;&nbsp;" +
                    "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" onclick=\"$('#dlg3').dialog('open')\">提交申请</a>";
            } else if (row.state == '审核通过' || row.state == '审核未通过') {
                return "<a href='javascript:openListCommentDialog(" + row.processInstanceId + ")'>查看历史批注</a>";
            }
        }

        //会议室单 编辑
        function huiyishiEdit() {
            // 清空表单内容
            $('#editHuiyishi').form('clear');

            // 获取被选中行的数据
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            } else {
                var row = selectRows[0];
                // 加载数据

                $('#dlgEdit').dialog('open');
                $('#editHuiyishi').form('load', row);
                for (let key  in row) {
                    if (key == "park") {
                        $("#parkIDEdit").combobox('setValue', row[key].parkID);
                    } else if (key == "project") {
                        $("#projectIDEdit").combobox('setValue', row[key].projectID);
                    }

                }


            }
        }

        function openListCommentDialog(processInstanceId) {
            $("#dg2").datagrid("load", {
                processInstanceId: processInstanceId
            });
            $("#dlg2").dialog("open").dialog("setTitle", "查看历史批注");
        }

        function openHuiyishiAddDialog() {

            $("#dlg").dialog("open").dialog("setTitle", "申请会议室反馈");
        }

        function addHuiyishi() {

            // var validate = $('#problemTitle').validatebox("isValid");
            // if (!validate) {
            //     $.messager.alert('', '会议室描述填写不正确!', 'error');
            //     return;
            // }
            if($('#fileUUID').val()==''){
                $('#fileUUID').val(uuidFile) ;
            }
            var formData = $('#fm').serializeJSON();
            $.ajax({
                url: '${pageContext.request.contextPath}/huiyishi/addHuiyishi',
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    message = "新增成功";
                    $.messager.alert("提示", message, 'info', function () {
                        if (rtn.success) {
                            flag = true;
                            closeHuiyishiDialog();
                            // 刷新表格数据
                            $('#dg').datagrid('reload');

                        }
                    });
                }
            });
        }


        function resetValue() {
            document.getElementById('uploadInfo').innerHTML = "";
            document.getElementById('uploadInfoEdit').innerHTML = "";
            $('#repairDate').combo('setText', '');
            $('#cityIDSelect').combobox('clear');
            $('#projectIDSelect').combobox('clear');
            // $('#huiyishiDeptID').combobox('clear');
            $('#subjectID').combobox('clear');
            $("#problemText").val("");
        }

        function closeHuiyishiDialog() {
            $("#dlg").dialog("close");
            $("#dlg3").dialog("close");
            resetValue();
            setTimeout(function () {
                document.location.reload();//重载当前页面
            }, 10);

        }

        function startRedictApply() {
            // 做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。
            var isValid = $('#fm').form('validate');
            if (isValid == false) {
                $.messager.alert("系统提示", "请填写必要字段！");
                return;
            }
            var checkValue = $("#HuiyishiAdviser_1").combobox("getValue");//取值  //获取Select选择的Value
            if (checkValue == "") {
                $.messager.alert('', '提交会议室审批人不能为空!', 'error');
                return;
            }
            if($('#fileUUID').val()==''){
                $('#fileUUID').val(uuidFile) ;
            }
            var formData = $('#fm').serializeJSON();
            // var isSend = $('input[name=isSend]:checked').val();
            $.ajax({
                url: "${pageContext.request.contextPath}/huiyishi/startRedictApply",
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "会议室申请提交成功，目前审核中，请耐心等待！");
                            flag = true;
                            closeHuiyishiDialog();
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "会议室申请提交失败，请联系管理员！");
                        }
                    }
            });
        }

        //提交会议室流程申请
        function startApply() {
            var selectRows = $("#dg").datagrid("getSelections");
            var taskID = selectRows[0].hysID;
            var userID = $('#HuiyishiAdviser_2').combobox('getValue');
            var userTask = {
                taskID: taskID,
                userID: userID
            }
            console.log(userTask);
            $.ajax({
                url: "${pageContext.request.contextPath}/huiyishi/startApply",
                data: userTask,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    if (rtn.success) {
                        $.messager.alert("系统提示", "会议室申请提交成功，目前审核中，请耐心等待！");
                        $("#dg").datagrid("reload");
                        closeHuiyishiDialog();
                    } else {
                        $.messager.alert("系统提示", "会议室申请提交失败，请联系管理员！");
                    }
                }
            });
        }

        //保存 修改的 会议室单
        function updateHuiyishi() {
            // 做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。
            var isValid = $('#editHuiyishi').form('validate');
            if (isValid == false) {
                $.messager.alert("系统提示", "请填写必要字段！");
                return;
            }
            var formData = $('#editHuiyishi').serializeJSON();

            $.ajax({
                url: "${pageContext.request.contextPath}/huiyishi/updateHuiyishi",
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    if (rtn.success) {
                        $.messager.alert("系统提示", "会议室单提交成功！");
                        $("#dg").datagrid("reload");
                        closeHuiyishiDialog();
                    } else {
                        $.messager.alert("系统提示", "会议室单提交失败，请联系管理员！");
                    }
                }
            });
        }

        $(document).ready(function () {
            $('#huiyishiDate').datetimebox({
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
                remoteSort: false,
                singleSelect: true,
                striped: true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/huiyishi/huiyishiPage',
                detailFormatter: function (index, row) {
                    return '<div class="ddv" style="border:1px solid  #000"><span style="font-weight:bold;">会议室描述：</span>' + row.problemText + '</div>';
                },
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'hysID', title: '单号', width: 100, align: 'center', sortable: true},
                    {
                        field: 'park',
                        title: '公园名称',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (value.parkName) {
                                return value.parkName;
                            } else {
                                return value;
                            }
                        }
                    },{
                        field: 'userID',
                        title: '提交人',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (value) {
                                return value.allName;
                            } else {
                                return value;
                            }
                        }
                    },{
                        field: 'userAdviser',
                        title: '审批人',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (value) {
                                return value.allName;
                            } else {
                                return value;
                            }
                        }
                    },
                    {field: 'hysText', title: '会议室描述', width: 200, align: '批注信息',
                        formatter: function (value, row, index) {
                            if(value){
                                return "<div  class='textEllipsis'>"+value+"</div>";
                            }else{
                                return '';
                            }
                        }
                    },
                    {field: 'remark1', title: '提交人备注', width: 100, align: 'center'},
                    {field: 'hysDate', title: '提交日期', width: 100, align: 'center', sortable: true},
                    {field: 'state', title: '审核状态', width: 100, align: 'center'},
                    {field: 'processInstanceId', title: '流程ID', width: 100, align: 'center'},
                    {field: 'action', title: '操作', width: 150, align: 'center', formatter: formatAction}
                ]],
                // queryParams:{},
                pageSize: 20,
                sortName: 'hysDate',
                sortOrder: 'desc',
            });

            $('#HuiyishiAdviser_1').combogrid({
                title: '审批人',
                panelWidth: 500,
                remoteSort: false,
                nowrap: false,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                striped: true,
            });

            $('#HuiyishiAdviser_2').combogrid({
                title: '审批人',
                panelWidth: 500,
                remoteSort: false,
                nowrap: false,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                striped: true,
            });

            //设置公园选择，如果是管理员则可以选择所有，其他只能选择自己所属公园

            if (sessionParkID != 0) {
                $("#parkIDSelect").combobox({
                    // enable:false,
                    editable: false,
                    data: [{'parkID': sessionParkID, 'parkName': sessionParkName}],
                    valueField: 'parkID',
                    textField: 'parkName',
                    panelHeight: 'auto',
                    onLoadSuccess: function (data) {
                        var data = $("#parkIDSelect").combobox("getData");
                        if (data && data.length > 0) {
                            $("#parkIDSelect").combobox("setValue", data[0].parkID);
                        }
                    }
                });
                // 编辑
                $("#parkIDEdit").combobox({
                    // enable:false,
                    editable: false,
                    data: [{'parkID': sessionParkID, 'parkName': sessionParkName}],
                    valueField: 'parkID',
                    textField: 'parkName',
                    panelHeight: 'auto',
                    onLoadSuccess: function (data) {
                        var data = $("#parkIDEdit").combobox("getData");
                        if (data && data.length > 0) {
                            $("#parkIDEdit").combobox("setValue", data[0].parkID);
                        }
                    }
                });




            } else {
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
                    }
                }).combobox("clear");
                // 编辑
                $('#parkIDEdit').combobox({
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
                    }
                }).combobox("clear");
            }


//上传 已有的文件id   用于重新修改会议室单，重新上传文件  -----------二次上传文件
            var filters = {title: "文档", extensions: "zip,doc,docx,xls,xlsx,pdf,ppt,pptx,jpg,png,gif,bmp,tif"};
            var uploaderExists = $("#uploaderExists").pluploadQueue($.extend({
                runtimes: 'flash,html5,html4',
                url: pageContext + '/fileInfo/uploadFile',//远程上传地址
                max_file_size: '10mb',
                file_data_name: 'file',
                unique_names: true,
                multiple_queues: true,
                // resize: { width: 320, height: 240, quality: 90 },
                filters: [filters],
                flash_swf_url: pageContext + '/static/plupload/plupload.flash.swf',//flash文件地址
                init: {
                    //重新修改url
                    BeforeUpload: function (uploaderExists, file) {
                        var fileBefore = $('#fileIDEdit').val();
                        if (fileBefore==""||fileBefore==null){
                            $.messager.confirm("系统提示", "文件id为空，请联系管理员！");
                            return;
                        }
                        var urlStr = pageContext + "/fileInfo/uploadFile?fileID=" + $('#fileIDEdit').val();
                        uploaderExists.settings.url = urlStr;//重新修改url
                    },
                    FileUploaded: function (uploaderExists, file, response) {
                        if (response.response) {
                            var rs = $.parseJSON(response.response);
                            if (rs.success) {
                                files.push(file.name);
                            } else {
                                errors.push(file.name);
                            }
                        }
                    },
                    UploadComplete: function (uploaderExists, fs) {
                        var e = errors.length ? "失败" + errors.length + "个(" + errors.join("、") + ")。" : "。";
                        var s = "上传完成！共" + fs.length + "个，成功" + files.length + e;
                        if (errors.length > 0) {
                            document.getElementById('uploadInfoEdit').innerHTML = e;
                        } else {
                            document.getElementById('uploadInfoEdit').innerHTML = s;
                        }

                    }
                }
            }, (false ? {chunk_size: '1mb'} : {})));

        })

        function closePDialog() {
            $("#dlg4").dialog("close");
            resetValue();


        }

        function openFiles() {
            var filesID = $('#fileIDEdit').val()//编辑的会议室单 文件id
            $("#fileUploader").dialog("open").dialog("setTitle", "历史文件");
            $('#findFiles').datagrid({
                remoteSort: false,
                singleSelect: false,
                striped: true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/fileInfo/findFiles?fileID=' + filesID,
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'remark', title: '文件名', width: 100, align: 'center'},
                    {
                        field: 'path', title: '图片显示', width: 200, align: 'center',
                        formatter: function (value, row, index) {
                            return  '<img src="'+sessionFile + value + '" alt = "非图片" height="200" width="300" class="slide-image">';
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
                            return '<a onclick=deleteFile(\'' + rowData.fileID + '\',\'' + rowData.path + '\') class=\'btn btn-info btn-sm pull-right\' title="删除">' + '<img src="${pageContext.request.contextPath}/static/images/delete.png">' + '</ a>' +
                                '&emsp;&nbsp;<a  title="下载" onclick=editRow(\'' + rowData.path + '\') class=\'btn btn-info btn-sm pull-right\'>' + '<img src="${pageContext.request.contextPath}/static/images/download.png">' + '</ a>';
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
            var form = $("<form>");    // 定义一个form表单
            form.attr("style", "display:none");
            form.attr("target", "_blank");
            form.attr("method", "post");
            form.attr("action", "${pageContext.request.contextPath}/fileInfo/downloadFile");    // 此处填写文件下载提交路径
            var input1 = $("<input>");
            input1.attr("type", "hidden");
            input1.attr("name", "path");    // 后台接收参数名
            input1.attr("value", path);
            $("body").append(form);    // 将表单放置在web中
            form.append(input1);
            form.submit();    // 表单提交
        }

        function deleteFile(fileID, fileName) {
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/fileInfo/deleteFile",
                dataType: "json",
                data: {fileID: fileID, filePath: fileName},
                success: function (result) {
                    if (result.success) {
                        $.messager.alert("系统", "删除成功！");
                        $("#findFiles").datagrid("reload");
                    } else {
                        $.messager.alert("系统提示", "删除失败！");
                        return;
                    }
                }
            });
        }
    </script>
</head>
<body style="margin: 1px">
<table id="dg" title="会议室反馈申请管理"
       fitColumns="true" pagination="true" rownumbers="true" fit="true" toolbar="#tb">
</table>
<div id="tb">
    <div>
        <a href="javascript:openHuiyishiAddDialog()" class="easyui-linkbutton" iconCls="icon-add"
           plain="true">申请会议室反馈</a>
    </div>
</div>
<%--添加会议室单 表单--%>
<div id="dlg" class="easyui-dialog" style="width: 500px;height: 480px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">

    <%-- 	<form id="fm" method="post" enctype="multipart/form-data" >--%>
    <form id="fm" method="post">
        <table style="margin:5px;height:100px;">
            <tr>
                <td>公园名称：</td>
                <td>
                    <input type="text" id="parkIDSelect" style="width:300px;" name="park.parkID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td valign="top">会议室描述：</td>
                <td>
                    <input type="hidden" name="userID.id" value="${sessionInfo.userId }"/>
                    <input type="hidden" name="state" value="未提交"/>
                    <textarea type="text" id="hysText" style="height:200px;width:300px;" name="hysText" rows="5"
                              cols="50"
                              class="easyui-validatebox" required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td>提交人备注：</td>
                <td>
                    <input  data-options="validType:'length[5,50]'" style="width:300px;" name="remark1"
                            class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>附属图片或文件：</td>
                <td><input type="button" onclick="openUpFile()" value="上传文件"></td>
            </tr>
            <tr>
                <td colspan="4">
                    <input id="fileUUID" type="hidden" name="fileID"/>
                    <label id="uploadInfo"/>
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
                    <input id="dept_1" class="easyui-combotree" style="width:300px;" required="true"
                           data-options="panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>提交审批人：</td>
                <td>
                    <select id="HuiyishiAdviser_1" type="text" name="HuiyishiAdviser" data-options="panelHeight:'auto'"
                            style="width: 300px;" required="true">
                    </select>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>是否Email通知：</td>--%>
            <%--                <td>--%>
            <%--                    <span>--%>
            <%--                        <input class="isSend" type="radio" name="isSend" value=true checked="true">是</input>--%>
            <%--                        <input class="isSend" type="radio" name="isSend" value=false >否</input>--%>
            <%--                    </span>--%>
            <%--                </td>--%>
            <%--            </tr>--%>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:addHuiyishi()" class="easyui-linkbutton" iconCls="icon-save">暂存</a>
    <a href="javascript:startRedictApply()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
    <a href="javascript:closeHuiyishiDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div id="dlg3" class="easyui-window" title="选择提交人" style="width: 360px;height: 180px;" data-options="closed:true"
     align="center" >
    <form id="editForm">
        <table>
            <tr>
                <td>部门：</td>
                <td>
                    <input id="dept_2" class="easyui-combotree" style="width:200px;">
                </td>
            </tr>
            <tr>
                <div class="datagrid-toolbar"></div>
            </tr>
            <tr>
                <td>提交审批人：</td>
                <td>
                    <select id="HuiyishiAdviser_2" type="text" name="HuiyishiAdviser"
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
<%--编辑会议室单--%>
<div id="dlgEdit" class="easyui-dialog" title="编辑会议室单"  data-options="closed:true"
     align="center" buttons="#dlgEdit-buttons">
    <form id="editHuiyishi">
        <table>
            <tr>
                <td>公园名称：</td>
                <td>
                    <input type="text" id="parkIDEdit" style="width:300px;" name="park.parkID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td valign="top">会议室描述：</td>
                <td>
                    <%--                    <input type="hidden" name="userID" value="${sessionInfo.userID }"/>--%>
                    <input type="hidden" name="state" value="未提交"/>
                    <textarea type="text" id="hysText" style="height:200px;width:300px;" name="hysText" rows="5"
                              cols="50"
                              class="easyui-validatebox" required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td>提交人备注：</td>
                <td>
                    <input  data-options="validType:'length[5,50]'" style="width:300px;" name="remark1"
                            class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td>附属图片或文件：</td>
                <td colspan="1">
                    <input type="button" onclick="openFiles()" value="管理文件">
                    &emsp;&emsp;&emsp;&emsp;
                    <input type="button" onclick="openUpExists()" value="上传文件">
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <input  type="hidden" name="hysID"/>
                    <input id="fileIDEdit" type="hidden" name="fileID"/>
                    <label id="uploadInfoEdit"/>
                </td>
            </tr>
            <tr>
                <td class="datagrid-toolbar"></td>
                <td class="datagrid-toolbar"></td>
            </tr>
            <tr></tr>
        </table>
        <br/>

    </form>
</div>
<div id="dlgEdit-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:updateHuiyishi()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgEdit').dialog('close')">关闭</a>
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
<div id="dlg4" class="easyui-dialog" style="width: 260px;height: 165px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons4">
    <div id="p" style="width:200px;"></div>
    <div><span id="dp"></span></div>
</div>
<div id="dlg-buttons4">
    <a href="javascript:closePDialog()" class="easyui-linkbutton" iconCls="icon-ok">确认并关闭</a>
    <audio id="audio"></audio>
</div>
<%--上传文件，生成 新的文件id--%>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>
<%--上传文件，用   已有的文件id--%>
<div id="uploaderExists" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>

<div id="fileUploader"  class="easyui-window" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>
<style type="text/css">
    /* 文本超长class */
    .textEllipsis{
        color:black;
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
</body>
</html>