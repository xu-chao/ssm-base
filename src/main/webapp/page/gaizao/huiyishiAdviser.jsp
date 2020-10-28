<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>负责人审批</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript">
        var str = "审批";
        var sessionFile = "${sessionFile}";
        var pageContext = "${pageContext.request.contextPath}";
        function submit(state) {
            var formData = $('#fm').serializeJSON();
            $.ajax({
                url: '${pageContext.request.contextPath}/huiyishiTask/auditOperators',
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (result) {
                    if (result.success) {
                        $.messager.confirm("系统提示", "提交成功！");
                        window.parent.refreshTabData(str, window.top.reload_huiyishi);
                    } else {
                        $.messager.confirm("系统提示", "提交失败，请联系管理员！");
                        return;
                    }
                }
            });
        }
        function openUpExists() {
            $("#uploaderExists").dialog("open").dialog("setTitle", "上传文件");
        }
        $(function () {
            $('#parkIDSelect').combobox({
                disabled: false,
                url: pageContext + '/park/searchAllPark',
                valueField: 'parkID',
                textField: 'parkName',
                readonly:true,
            }).combobox("clear");

            $.ajax({
                url: "${pageContext.request.contextPath}/huiyishi/getHuiyishiByTaskId",
                type: "post",
                data: {"taskId": '${param.taskId}'},//返回给客户端的json数据
                async: false,
                dataType: 'json',
                success: function (data) {
                    var huiyishi = data.huiyishi;
                    $('#parkIDSelect').combobox('setValue', huiyishi.park.parkID);
                    $("#hysText").val(huiyishi.hysText);
                    $('#remark1').textbox('setValue',huiyishi.remark1);
                    $('#hysID').val(huiyishi.hysID);
                    $("#fileIDEdit").val(huiyishi.fileID);
                }
            });
//上传 已有的文件id   用于重新修改问题单，重新上传文件  -----------二次上传文件
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
                        if (fileBefore == "" || fileBefore == null) {
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

        });

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
<body class="easyui-layout" style="margin: 5px">
<!-- 添加、修改 -->
<div style="text-align: center;">
    <form action="" id="fm" method="post">
        <table style="margin: auto ">
            <tr>
                <td>公园名称:</td>
                <td colspan="8">
                    <input type="hidden" id="hysID"name="hysID"/>
                    <input type="text" id="parkIDSelect" name="park.parkID" style="width:300px;"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                <p></p>
                </td>
            </tr>
            <tr>
                <td>会议描述:</td>
                <input type="hidden" style="width:300px;" name="userAdviser.id" value="${sessionInfo.userId }"/>
                <td colspan="8">
            <textarea type="text" id="hysText" name="hysText" rows="5"
                      cols="42"
                      class="easyui-validatebox" required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td>提交备注:</td>
                <td colspan="8">
                    <input id="remark1" name="remark1"  style="width:300px;" type="text" class="easyui-textbox"
                           required="true" readonly="readonly">
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td>审批备注:</td>
                <td colspan="8">
                    <input id="remark2"  style="width:300px;" data-options="validType:'length[5,50]'"
                           name="remark2"
                           class="easyui-textbox easyui-validatebox" required="true">
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td>文件上传:</td>
                <td colspan="8">
                    <input type="button" onclick="openFiles()" value="管理文件">
                    &emsp;&emsp;&emsp;&emsp;
                    <input type="button" onclick="openUpExists()" value="上传文件">
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <input id="fileIDEdit" type="hidden" name="fileID"/>
                    <label id="uploadInfoEdit"/>
                    <input type="hidden" name="taskId" value="${param.taskId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <p></p>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <a href="javascript:submit(1)" class="easyui-linkbutton"
                       iconCls="icon-ok">通过
                    </a>
                </td>
            </tr>
        </table>
    </form>
</div>
<%--上传文件，用   已有的文件id--%>
<div id="uploaderExists" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>
<div id="fileUploader"  class="easyui-window" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>
</body>
</html>
