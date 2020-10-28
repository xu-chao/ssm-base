<%--
  Created by IntelliJ IDEA.
  User: xuchao
  Date: 2019/8/21
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>子项目管理</title>
    <link href="${pageContext.request.contextPath}/static/css/Site.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plupload/queue/css/jquery.plupload.queue.css"
          type="text/css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <link href="${pageContext.request.contextPath}/static/css/icon-standard.css" rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
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
        //用于search.js自动补全
        //用于crud.js的esayui初始参数
        var pageContext = "${pageContext.request.contextPath}";
        var root = "${pageContext.request.contextPath}/gaizao/";
        var sessionPid = ${sessionDept.dept.pid};
        var pageName = 'gaizao';
        var editFileID = null;//编辑中的文件ID
        var g_projectID;
        var g_projectName;
        var g_parkName;
        var selectRows;
        var columns = [[
            {
                field: 'gzID',
                title: 'ID',
                width: 50,
                align: 'center',
                sortable: true,
            }, {
                field: 'projectName',
                title: '项目名',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.project) {
                        return row.project.projectName;
                    } else {
                        return "获取不到该项目！";
                    }
                }
            }, {
                field: 'gzUser',
                title: '改造负责人',
                width: 50,
                align: 'center',
                // formatter: function (value, row, index) {
                //     if (row.gzUser) {
                //         return row.gzUser.allName;
                //     } else {
                //         return "获取不到该负责人！";
                //     }
                // }
            },
            {
                field: 'gzcs',
                title: '已改造次数',
                width: 50,
                align: 'center',
                sortable: true
            }, {
                field: 'gzDate',
                title: '上一次(日期)',
                width: 50,
                align: 'center',
                sortable: true
            },
            {
                field: 'gzxq',
                title: '改造详情',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {   //控制字段显示  datagrid
                    if (value.length > 20) {
                        return "<div title='" + value + "'>" + value.substring(0, 20) + "...</div>";
                    } else {
                        return "<div title='" + value + "'>" + value + "</div>";
                    }
                }


            }, {
                field: 'operation',
                title: '功能',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<a href="javascript:void(0)" ' +
                        'onclick="openFiles(\'' + row.file_id + '\')">反馈文件</a>' +
                        '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                        '<a href="javascript:void(0)" onclick="openGaiZaoDetailUpFile(\'' + row.project.projectID + '\',\''+ row.project.projectName +'\')">改造细节</a>';
                }
            }
        ]];

        var h = 180;
        var w = 280;
        var _title = '项目管理';

        //提交的方法名称
        var method = "";
        var saveParam = "";
        var message = "";

        //-------------------------文件系统--------------------------
        function openFiles(filesID) {
            $("#fileShow").dialog("open").dialog("setTitle", "下载文件");
            $('#findFiles').datagrid({
                remoteSort: false,
                singleSelect: false,
                striped: true,
                nowrap: false,
                url: '${pageContext.request.contextPath}/question/findFiles?fileID=' + filesID,
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'remark', title: '文件名', width: 100, align: 'center'},
                    {
                        field: 'path', title: '图片显示', width: 200, align: 'center',
                        formatter: function (value, row, index) {
                            return '<img src="${pageContext.request.contextPath}' + value + '"  alt = "非图片" height="200" width="300" class="slide-image">';
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

        function editRow(path) {
            var form = $("<form>");    // 定义一个form表单
            form.attr("style", "display:none");
            form.attr("target", "_blank");
            form.attr("method", "post");
            form.attr("action", "${pageContext.request.contextPath}/question/downloadFile");    // 此处填写文件下载提交路径
            var input1 = $("<input>");
            input1.attr("type", "hidden");
            input1.attr("name", "path");    // 后台接收参数名
            input1.attr("value", path);
            $("body").append(form);    // 将表单放置在web中
            form.append(input1);
            form.submit();    // 表单提交

        }

        //-------------------------文件系统--------------------------
        $(document).ready(function () {
            $('#gzDate').datetimebox({
                showSeconds: true,
                editable: false,
            });

            // $('#QuestionAdviser_1').combogrid({
            //     title: '改造人员',
            //     panelWidth: 500,
            //     remoteSort: false,
            //     nowrap: false,
            //     fitColumns: true,
            //     pagination: true,
            //     rownumbers: true,
            //     fitColumns: true,
            //     striped: true,
            // });

            // 加载表格数据
            $('#dg').datagrid({
                url: "",
                idField: "gzID",//指明哪一个字段是标识字段。
                // title : "改造记录",
                columns: columns,
                fit: true,
                fitColumns: true,
                emptyMsg: '<h2  style="text-align:center;color:red" >无数据</h2>',
                singleSelect: false,// 如果为true，则只允许选择一行。
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                sortName: "gzDate",//定义哪些列可以进行排序。
                sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                toolbar: [
                    {
                        text: '添加',
                        iconCls: 'icon-add',
                        handler: function () {
                            $("#dlg").dialog("open").dialog("setTitle", "申请改造单");
                        }
                    }
                ],
                // onLoadSuccess: function (data) {
                //     if (data.total == 0) {
                //         var body = $(this).data().datagrid.dc.body2;
                //         body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height:35px; text-align:center;"><h1>暂无数据</h1></td></tr>');
                //     }
                // }
            });

        })

        //ajax下载word文件
        function downWord(processId, questionID) {
            $.ajax({
                url: "${pageContext.request.contextPath}/word/getQuestionWord",
                type: 'post',
                data: {"processId": processId},//返回给客户端的json数据
                async: false,
                success: function (data, filename) {
                    var blob = new Blob([data], {type: 'application/msword'});
                    var link = document.createElement("a");
                    var body = document.querySelector("body");
                    link.href = window.URL.createObjectURL(blob);
                    link.download = questionID;//文件名
                    link.style.display = "none";
                    body.appendChild(link);
                    link.click();
                    body.removeChild(link);
                    window.URL.revokeObjectURL(link.href);
                }
            });
        }

        $(function () {
            <%--$('#dept_1').combotree(--%>
            <%--    {--%>
            <%--        // prompt: '输入关键字后自动搜索',--%>
            <%--        // value: deptValue_session,--%>
            <%--        required: true,--%>
            <%--        missingMessage: '部门名称不能为空!',--%>
            <%--        // mode: 'remote',--%>
            <%--        url: '${pageContext.request.contextPath}/dept/selectChild?deptID=' + sessionPid,--%>
            <%--        onSelect: function (record) {--%>
            <%--            var deptID = record.id;--%>
            <%--            $('#QuestionAdviser_1').combogrid({--%>
            <%--                method: 'POST',--%>
            <%--                idField: 'id',--%>
            <%--                textField: 'allName',--%>
            <%--                url: '${pageContext.request.contextPath}/user/userPage?deptID=' + deptID,--%>
            <%--                columns: [[--%>
            <%--                    {field: 'cb', checkbox: true, align: 'center'},--%>
            <%--                    {field: 'id', title: '用户名/工号', width: 100, align: 'center'},--%>
            <%--                    {field: 'firstName', title: '姓', width: 100, align: 'center'},--%>
            <%--                    {field: 'lastName', title: '名', width: 100, align: 'center'},--%>
            <%--                    {field: 'email', title: '邮箱', width: 100, align: 'center'}--%>
            <%--                ]],--%>
            <%--            });--%>
            <%--        }--%>
            <%--    });--%>


            $('#cityIDSelect1').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/city/searchCity',
                    valueField: 'cityID',
                    textField: 'cityName',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    // hasDownArrow: false,
                    multiple: false,
                    editable: true,
                    onSelect: function (record) {
                        var cit = record.cityID;
                        $('#parkIDSelect1').combobox({
                            disabled: false,
                            url: '${pageContext.request.contextPath}/project/findParkByCityID?cityID=' + cit,
                            valueField: 'parkID',
                            textField: 'parkName',
                            onLoadSuccess: function () { //加载完成后,val[0]写死设置选中第一项
                                var val = $(this).combobox("getData");
                                for (var item in val[0]) {
                                    if (item == "parkID") {
                                        $(this).combobox("select", val[0][item]);
                                    }
                                }
                            },
                            onSelect: function (record) {
                                var parkID = record.parkID;
                                g_parkName = record.parkName;//赋值  公园名称
                                $('#dg').datagrid('options').url = "${pageContext.request.contextPath}/gaizao/gaizaoPage",
                                    $('#dg').datagrid('load', {
                                        parkID: parkID,
                                    });
                            }
                        }).combobox("clear");
                    }
                });

            $('#gzlx').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=gzlx',
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

        })


        //提交 改造单
        function addGaiZao() {
            if ($('#fileUUID').val() == '') {//防止文件id 为空
                $('#fileUUID').val(uuidFile);
            }
            var checkValue = $("#projectIDSelect").combobox("getValue");//取值  //获取Select选择的Value
            if (checkValue == "") {
                $.messager.alert('', '项目名称 不能为空!', 'error');
                return;
            }
            var validate = $('#fm').form('validate')
            if (!validate) {
                $.messager.alert("系统提示", "请填写完必要字段！");
                return;
            }
            var formData = $('#fm').serializeJSON();
            $.ajax({
                url: '${pageContext.request.contextPath}/gaizao/addGaiZao',
                data: formData,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    message = "新增成功";
                    $.messager.alert("提示", message, 'info', function () {
                        if (rtn.success) {
                            closeGaiZao();
                        }
                    });
                }
            });
        }

        function closeGaiZao() {
            $("#dlg").dialog("close");
            resetValue();
            // setTimeout(function () {
            //     document.location.reload();//重载当前页面
            // }, 10);
        }

        function resetValue() {
            document.getElementById('uploadInfo').innerHTML = "";
            $('#gzDate').combobox('setText', '');
            $('#cityIDSelect').combobox('clear');
            $('#parkIDSelect').combobox('clear');
            $('#projectIDSelect').combobox('clear');
            // $('#dept_1').combotree('clear');
            // $('#QuestionAdviser_1').combogrid('clear');
            $('#gzlx').combobox('clear');
            $("#gzxq").val("");
            $("#beizu").val("");
            flag = true;//设置文件id重新生成
            // 刷新表格数据
            $('#dg').datagrid('reload');
        }

        function openUpFile() {
            $("#uploader").dialog("open").dialog("setTitle", "上传文件");
        }

        //-----------------------------------改造详情----------------------------------------
        function openGaiZaoDetailUpFile(projectID,projectName) {
            g_projectID = projectID;
            // g_projectName = project.projectName;
            $("#gaiZaoDetail").dialog("open").dialog("setTitle", "(公园)："+g_parkName+"  (项目)："+projectName);
            $('#gaiZaoTable').datagrid('options').url = "${pageContext.request.contextPath}/gaizao/gaiZaoDetail",
                $('#gaiZaoTable').datagrid('load', {
                    projectID: projectID,
                });
        }

        /**
         *
         */
        $(function () {
            $('#gaiZaoTable').datagrid({
                url: '',
                rownumbers: true,
                pagination: true,
                fitColumns: true,
                pageList: [5, 10, 15, 20],
                idField: 'gzID',
                columns: [[{
                    field: 'gzID',
                    title: 'ID',
                    width: 30,
                    align: 'center',
                }, {
                    field: 'gzDate',
                    title: '日期',
                    width: 100,
                    align: 'center',
                    sortable: true,
                    editor: {
                        type: "datetimebox",
                        options: {
                            required: true,
                            validType: "gzDate"
                        }
                    }
                }, {
                    field: 'gzlx',
                    title: '改造类型',
                    width: 50,
                    align: 'center',
                    formatter:
                        function (value, row, index) {
                            if (row.gzlx) {
                                return row.gzlx.typeName;
                            } else {
                                return "获取不到该数据！";
                            }
                        },
                    editor: {
                        type: 'combobox',
                        options: {
                            required: true,
                            url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=gzlx',
                            valueField: 'typeID',
                            textField: 'typeName',
                            editable: false,
                            disabled: false,
                        }
                    }
                }, {
                    field: 'gzxq',
                    title: '改造详情',
                    width: 100,
                    align: 'center',
                    editor: {
                        type: "validatebox",
                        options: {
                            required: true,
                            validType: "gzxq"
                        }
                    }
                },
                    <%--}, {--%>
                    <%--    field: 'userID',--%>
                    <%--    title: '负责人',--%>
                    <%--    width: 50,--%>
                    <%--    align: 'center',--%>
                    <%--    formatter:--%>
                    <%--        function (value, row, index) {--%>
                    <%--            if (row.userID) {--%>
                    <%--                return row.userID.allName;--%>
                    <%--            } else {--%>
                    <%--                return "获取不到该数据！";--%>
                    <%--            }--%>
                    <%--        },--%>
                    <%--    editor: {--%>
                    <%--        // type : "validatebox"--%>
                    <%--        // type:'combobox',--%>
                    <%--        type: 'combogrid',--%>
                    <%--        options: {--%>
                    <%--            required: true,--%>
                    <%--            method: 'POST',--%>
                    <%--            idField: 'id',--%>
                    <%--            textField: 'allName',--%>
                    <%--            title: '改造人员',--%>
                    <%--            panelWidth: 500,--%>
                    <%--            remoteSort: false,--%>
                    <%--            nowrap: false,--%>
                    <%--            fitColumns: true,--%>
                    <%--            pagination: true,--%>
                    <%--            rownumbers: true,--%>
                    <%--            fitColumns: true,--%>
                    <%--            striped: true,--%>
                    <%--            url: '${pageContext.request.contextPath}/user/userPage?deptPid=' + sessionPid,--%>
                    <%--            columns: [[--%>
                    <%--                {field: 'cb', checkbox: true, align: 'center'},--%>
                    <%--                {field: 'id', title: '用户名/工号', width: 100, align: 'center'},--%>
                    <%--                {field: 'firstName', title: '姓', width: 100, align: 'center'},--%>
                    <%--                {field: 'lastName', title: '名', width: 100, align: 'center'},--%>
                    <%--                {field: 'email', title: '邮箱', width: 100, align: 'center'}--%>
                    <%--            ]],--%>
                    <%--        }--%>
                    <%--    }--%>
                    <%--},--%>

                    {
                        field: 'gzUser',
                        title: '改造负责人',
                        width: 30,
                        align: 'center',
                        editor: {
                            type: "validatebox",
                            options: {
                                validType: "gzUser"
                            }
                        }
                    }, {
                        field: 'beizu',
                        title: '备注',
                        width: 30,
                        editor: {
                            type: "validatebox",
                            options: {
                                validType: "beizu"
                            }
                        }
                    }, {
                        field: 'operation',
                        title: '附件',
                        width: 100,
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a href="javascript:void(0)" ' +
                                'onclick="openFiles(\'' + row.file_id + '\')">管理文件</a>' +
                                '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                                '<a href="javascript:void(0)" onclick="openUpExists(\'' + row.file_id + '\')">上传文件</a>';
                        }
                    }
                ]],
                loadFilter: function (data) {
                    var data_ = {
                        // "rows" : data.attributes.rows,
                        "rows": data.rows,
                        "total": data.rows.length,
                    };
                    return data_;

                }
            });
            var editBoxing = undefined;
            $('#gaiZaoTable').datagrid({
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-edit',
                    handler: function () {
                        if (editBoxing == undefined) {
                            editBoxing = 0;
                            $('#gaiZaoTable').datagrid("insertRow", {
                                index: editBoxing, // 索引从0开始
                                row: {
                                    "file_id": uuidOut(18, 20),//新建文件ID
                                    "projectID": g_projectID,//项目名
                                }
                            });
                            $('#gaiZaoTable').datagrid('beginEdit', editBoxing);
                        } else {
                            $.messager.alert('警告', '尚有未编辑完成单元，请继续编辑', 'info');
                        }

                    }
                }, '-', {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        $('#gaiZaoTable').datagrid('endEdit', editBoxing);
                        editBoxing = undefined;
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        var array = $('#gaiZaoTable').datagrid('getSelections');
                        if (array.length >= 1) {
                            var str = '';
                            var fileID = '';//文件id
                            for (var i = 0; i < array.length; i++) {
                                str += array[i].gzID + ",";
                                fileID += array[i].file_id + ",";
                            }
                            $.post('${pageContext.request.contextPath}/gaizao/gaizaoDelete', {
                                str: str,
                                fileID: fileID,
                            }, function (data) {
                                var data = eval('(' + data + ')');
                                if (data.success == true) {
                                    $('#gaiZaoTable').datagrid('reload');
                                    $('#dg').datagrid('reload');
                                    $.messager.show({
                                        title: '更新消息',
                                        msg: '删除成功',
                                        timeout: 3000
                                    });
                                }
                                $('#gaiZaoTable').datagrid('reload');
                            });
                        } else {
                            $.messager.alert('警告', '请选择一条记录');
                        }
                    }
                }, '-', {
                    text: '取消编辑',
                    iconCls: 'icon-redo',
                    handler: function () {
                        if (editBoxing == 0) {
                            $('#gaiZaoTable').datagrid('deleteRow', editBoxing);
                            editBoxing = undefined;
                            $('#gaiZaoTable').datagrid('reload');
                        } else {
                            editBoxing = undefined;
                            $('#gaiZaoTable').datagrid('reload');

                        }

                    }
                }, '-',],
                onAfterEdit: function (index, row, changes) {
                    if (row.gzID == undefined) {
                        $.post('${pageContext.request.contextPath}/gaizao/addGaiZao', {
                            "project.projectID": row.projectID,//项目名
                            "userID.id": ${sessionInfo.userId },//负责人
                            "gzUser": row.gzUser,//改造人员
                            "gzDate": row.gzDate,//改造日期
                            "gzlx.typeID": row.gzlx,//改造类型
                            "gzxq": row.gzxq,//改造详情
                            "beizu": row.beizu,//备注
                            "file_id": row.file_id//备注
                        }, function (data) {
                            var data = eval('(' + data + ')');
                            if (data.success == true) {
                                $.messager.show({
                                    title: '更新消息',
                                    msg: '新增成功',
                                    timeout: 3000,
                                });
                                $('#gaiZaoTable').datagrid('reload');
                                $('#dg').datagrid('reload');
                            } else {
                                $.messager.alert('警告', '未完成新增');
                            }
                            ;

                        });
                        $('#gaiZaoTable').datagrid('load');
                    } else {
                        $.post(
                            '${pageContext.request.contextPath}/gaizao/updateGaiZao',
                            {
                                "gzID": row.gzID,//ID
                                "userID.id": ${sessionInfo.userId },//负责人
                                // "gzUser.id": row.gzUser,//改造人员
                                "gzUser": row.gzUser,//改造人员
                                "gzDate": row.gzDate,//改造日期
                                "gzlx.typeID": row.gzlx,//改造类型
                                "gzxq": row.gzxq,//改造详情
                                "beizu": row.beizu//备注
                            }, function (data) {
                                var data = eval('(' + data + ')');
                                if (data.success == true) {
                                    $.messager.show({
                                        title: '更新消息',
                                        msg: '修改成功',
                                        timeout: 3000
                                    });
                                    $('#gaiZaoTable').datagrid('reload');
                                    $('#dg').datagrid('reload');
                                } else {
                                    $.messager.alert('警告', '修改失败');
                                }
                            });
                    }

                },
                onDblClickCell: function (index, field, value) {
                    if (editBoxing == undefined) {
                        editBoxing = index;
                        $('#gaiZaoTable').datagrid('beginEdit', index);
                        var ed = $(this).datagrid('getEditor', {
                            index: index,
                            field: field
                        });
                        $(ed.target).focus();
                    } else {
                        $('#gaiZaoTable').datagrid('endEdit', editBoxing);
                        editBoxing = index;
                        $('#gaiZaoTable').datagrid('beginEdit', index);
                        var ed = $(this).datagrid('getEditor', {
                            index: index,
                            field: field
                        });
                        $(ed.target).focus();
                    }
                }
            });
//-------------------------------------------------------------------------编辑的 文件上传功能
            //上传 已有的文件id   用于重新修改问题单，重新上传文件
            var filters = {title: "文档", extensions: "zip,doc,docx,xls,xlsx,pdf,ppt,pptx,jpg,png,gif,bmp,tif"};
            var uploaderExists = $("#uploaderExists").pluploadQueue($.extend({
                runtimes: 'flash,html5,html4',
                url: pageContext + '/question/uploadFile',//远程上传地址
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
                        if (editFileID == "" || editFileID == null) {
                            $.messager.confirm("系统提示", "文件id为空，请联系管理员！");
                            return;
                        }
                        var urlStr = pageContext + "/question/uploadFile?fileID=" + editFileID;
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
                            document.getElementById('uploadInfo').innerHTML = e;
                        } else {
                            document.getElementById('uploadInfo').innerHTML = s;
                        }

                    }
                }
            }, (false ? {chunk_size: '1mb'} : {})));
//-------------------------------------------------------------------------编辑的 文件上传功能

        });
        $.extend($.fn.validatebox.defaults.rules, {
            loginName: {
                validator: function (value, param) {
                    return /^[\u0391-\uFFE5\w]+$/.test(value);
                },
                message: '登录名称只允许汉字、英文字母、数字及下划线。'
            }
        });

        //------------编辑中的 上传文件
        function openUpExists(id) {
            editFileID = id;
            $("#uploaderExists").dialog("open").dialog("setTitle", "上传文件");
        }


    </script>
    <%--    <script type="text/javascript"--%>
    <%--            src="${pageContext.request.contextPath}/static/js/download.js"></script>--%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/CityParkProject.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'"
     style="padding: 4px; background-color: #eee; height: 140px">
    <form id="searchForm">
        <table cellpadding="5">
            <h1 style="text-align: center;">公园维护改造记录</h1>
            <HR align=center width="100%" color=#987cb9 SIZE=1>
            <tr>
                <td>城市 ：</td>
                <td><input class="easyui-combobox" id="cityIDSelect1"/></td>
                <td>公园 ：</td>
                <td><input class="easyui-combobox" id="parkIDSelect1"/></td>
            </tr>
            <%--                <td>类型:</td>--%>
            <%--                <td><input name="goodsType" class="easyui-combobox" id="searchType"/></td>--%>
            <%--                <td><a class="easyui-linkbutton"--%>
            <%--                       data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>--%>
            <%--                <td><a class="easyui-linkbutton"--%>
            <%--                       data-options="iconCls:'icon-remove'" id="btnReset">重置</a></td>--%>
        </table>
    </form>
</div>
<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true"
     style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <table id="dg"></table>
</div>
<%--添加改造 表单--%>
<div id="dlg" class="easyui-dialog" style="width: 500px;height: 480px;padding: 10px 20px" closed="true"
     buttons="#dlg-buttons">
    <%-- 	<form id="fm" method="post" enctype="multipart/form-data" >--%>
    <form id="fm" method="post">
        <table style="margin:5px;height:100px;">
            <tr>
                <td>所属城市：</td>
                <td>
                    <input type="text" id="cityIDSelect" style="width:300px;"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td>公园名称：</td>
                <td>
                    <input type="text" id="parkIDSelect" style="width:300px;"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td>项目名称：</td>
                <td>
                    <input type="text" id="projectIDSelect" style="width:300px;" data-options="panelHeight:'auto'"
                           name="project.projectID" class="easyui-combobox"/>
                </td>
            </tr>
            <tr>
                <td>改造类型：</td>
                <td>
                    <input type="text" id="gzlx" style="width:300px;" name="gzlx.typeID"
                           class="easyui-combobox" data-options="panelHeight:'auto'"
                           required="true"/>
                    <%--                    <input id="questionDeptID" style="width:300px;" name="dept.deptID" class="easyui-combobox" editable="false"--%>
                    <%--                           required="true">--%>
                </td>
            </tr>
            <tr>
                <td>改造时间：</td>
                <td>
                    <input type="text" id="gzDate" data-options="panelHeight:'auto'" name="gzDate"
                           class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td valign="top">改造详情：</td>
                <td>
                    <input type="hidden" name="userID.id" value="${sessionInfo.userId }"/>
                    <textarea type="text" id="gzxq" style="height:100px;width:300px;" name="gzxq" rows="5"
                              cols="50"
                              class="easyui-validatebox" required="true"></textarea>
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td>部门：</td>--%>
            <%--                <td>--%>
            <%--                    <input id="dept_1" class="easyui-combotree" style="width:300px;" required="true"--%>
            <%--                           data-options="panelHeight:'auto'">--%>
            <%--                </td>--%>
            <%--            </tr>--%>
            <tr>
                <td>改造负责人：</td>
                <td>
                    <%--                    <select id="QuestionAdviser_1" type="text" name="gzUser.id" data-options="panelHeight:'auto'"--%>
                    <%--                            style="width: 300px;" required="true">--%>
                    <%--                    </select>--%>
                    <input id="beizu" name="gzUser" type="text" class="easyui-textbox" style="width:300px;"
                           required="true"
                           data-options="panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>备注：</td>
                <td>
                    <input id="beizu" name="beizu" type="text" class="easyui-textbox" style="width:300px;"
                           required="true"
                           data-options="panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>附件：</td>
                <td><input type="button" onclick="openUpFile()" value="上传文件"></td>
            </tr>
            <tr>
                <td colspan="4">
                    <input id="fileUUID" type="hidden" name="file_id"/>
                    <label id="uploadInfo"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:addGaiZao()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
    <a href="javascript:closeGaiZao()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<%--上传文件，生成 新的文件id--%>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>
<%--显示文件--%>
<div id="fileShow" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>
<%--上传文件，用   已有的文件id--%>
<div id="uploaderExists" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">&nbsp;</div>

<%--显示改造详情--%>
<div id="gaiZaoDetail" class="easyui-dialog" resizable="true" style="width: 900px;height: 400px" closed="true">
    <table id="gaiZaoTable"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>
</body>
</html>