<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会议室管理</title>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <style>
        /*未被访问的样式*/
        .editcls:link {
            color: #1111ba;
        }

        /*已被点击后的样式*/
        .editcls:visited {
            color: black;
        }

        /*是鼠标悬停的样式*/
        .editcls:hover {
            color: pink;
        }

        /*已被激活的样式*/
        .editcls:active {
            color: #0ac9cc;
        }
    </style>
    <script type="text/javascript">
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '会议室单号';
        var _value = 'projectName';
        //用于crud.js的esayui初始参数
        var pageContext = "${pageContext.request.contextPath}";
        var root = "${pageContext.request.contextPath}/huiyishi/";
        var sessionFile = "${sessionFile}";
        var pageName = 'huiyishiThrough';
        var name = 'Huiyishi';
        var title = '会议室列表';
        var idField = 'hysID';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[ {
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
        },{field: 'hysText', title: '会议室描述', width: 200, align: '批注信息',
            formatter: function (value, row, index) {
                if(value){
                    return "<div  class='textEllipsis'>"+value+"</div>";
                }else{
                    return '';
                }
            }
        },
            {field: 'remark1', title: '提交备注', width: 100, align: 'center'},
            {field: 'remark2', title: '审批备注', width: 100, align: 'center'},
            {field: 'hysDate', title: '提交日期', width: 100, align: 'center', sortable: true},
            {
            field: 'operation',
            title: '操作',
            width: 100,
            align: 'center',
            formatter: function (value, row, index ) {
                var btn = '<a class="editcls" onclick="openFiles(\'' + row.fileID + '\')" href="javascript:void(0)">反馈文件</a>&nbsp;&nbsp;'
                return btn;
            }
        }]];
        // '<a class="editcls" onclick="openFiles(\'' + row.fileAfterID + '\')" href="javascript:void(0)">反馈后文件</a>&nbsp;&nbsp;'+
        var h = 180;
        var w = 280;
        var _title = '项目管理';

        //提交的方法名称
        var method = "";
        var saveParam = "";
        var message = "";

        function openListCommentDialog(processInstanceId) {
            $("#dg2").datagrid("load", {
                processInstanceId: processInstanceId
            });
            $("#dlg2").dialog("open").dialog("setTitle", "查看历史批注");
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

        $(document).ready(function () {
            // 加载表格数据
            $('#dg').datagrid({
                url: root + pageName + 'Page',
                idField: idField,//指明哪一个字段是标识字段。
                title: title,
                columns: columns,
                frozenColumns: [[{
                    field: 'ck',
                    align: 'center',
                    checkbox: true
                }, {
                    title: _idName,
                    field: idField,
                    align: 'center',
                    sortable: true,
                    width: 150
                }]],
                fit: true,
                fitColumns: true,
                iconCls: 'icon-tip',
                singleSelect: false,// 如果为true，则只允许选择一行。
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: false,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                sortName: idField,//定义哪些列可以进行排序。
                sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                detailFormatter: function (index, row) {
                    return '<div class="ddv" style="border:1px solid  #000"><span style="font-weight:bold;">会议室描述：</span>' + row.problemText + '</div>';
                },
                toolbar: [{
                    text: '导出',
                    iconCls: 'icon-excel',
                    handler: function () {
                        var startDate = $('#s_startDate').datebox('getValue');
                        var endDate = $('#s_endDate').datebox('getValue');
                        var formData = {
                            "searchType": g_searchType,
                            "searchValue": g_searchValue,
                            "startDate": startDate,
                            "endDate": endDate
                        };
                        // 下载文件
                        $.download(root + pageName + "Export", formData);
                    }
                }]
            });
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

            $('#ss').searchbox({
                searcher: function (value, name) {
                    g_searchType = name;
                    g_searchValue = value;
                    var stateID = $('#stateID option:selected').val();
                    var startDate = $('#s_startDate').datebox('getValue');
                    var endDate = $('#s_endDate').datebox('getValue');
                    if ((startDate == '' && endDate != '') || (startDate != '' && endDate == '')) {
                        $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
                    } else if (startDate > endDate) {
                        $.messager.alert('警告', '结束时间不能早于开始时间!');
                    } else {
                        $('#dg').datagrid('load', {
                            "searchType": name,
                            "searchValue": value,
                            "stateID": stateID,
                            "startDate": startDate,
                            "endDate": endDate
                        });
                    }

                },
                // class: "easyui-searchbox",
                menu: '#searchType',
                prompt: '请输入搜索值'
            });
            // 点击重置按钮
            $('#btnReset').bind('click', function () {
                $('#searchForm').form('clear');
            });

            $('#dg2').datagrid({
                fitColumns: true,
                url: "${pageContext.request.contextPath}/huiyishiTask/listHistoryCommentWithProcessInstanceId",
                columns: [[
                    {field: 'time', title: '批注时间', width: 120, align: 'center'},
                    {field: 'userId', title: '批注人', width: 100, align: 'center'},
                    {field: 'message', title: '批注', width: 200, align: '批注信息',
                        formatter: function (value, row, index) {
                            if(value){
                                return "<div  class='textEllipsis'>"+value+"</div>";
                            }else{
                                return '';
                            }
                        }
                        },
                ]],

            });
        })

    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/openFiles.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td> &nbsp;
                    操作时间:&nbsp;<input type="text" id="s_startDate" size="20"/> --
                    <input type="text" id="s_endDate" size="20"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'hysID',iconCls:'icon-ok'">会议室单号</div>
                        <div data-options="name:'ALLNAME_'">提交人</div>
                        <div data-options="name:'ParkName'">工程名称</div>
                    </div>
                </td>
                <td>
                    状态:&nbsp;
                    <select class="easyui-combobox" name="stateID" id="stateID"
                            data-options="editable:false,panelHeight:'auto'" style="width: 80px;">
                        <option value="">全部</option>
                        <option value="1">进行中</option>
                        <option value="2" selected="selected">通过</option>
                    </select>
                </td>
                <td>
                    <a class="easyui-linkbutton"
                       data-options="iconCls:'icon-remove'" id="btnReset">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true"
     style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <table id="dg"></table>
</div>
<div id="uploader" class="easyui-dialog" style="width: 800px;height: 360px" closed="true">
    <table id="findFiles"
           fitColumns="true" fit="true" toolbar="#tb">
    </table>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 750px;height: 250px;padding: 10px 20px" closed="true">
    <table id="dg2">
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