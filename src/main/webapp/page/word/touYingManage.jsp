<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>项目管理</title>
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
        .editcls:link{color: #1111ba;}
        /*已被点击后的样式*/
        .editcls:visited{color:black;}
        /*是鼠标悬停的样式*/
        .editcls:hover{color:pink;}
        /*已被激活的样式*/
        .editcls:active{color: #0ac9cc;}
    </style>
    <script type="text/javascript">
        var searchType ='';
        var searchValue = '';
        //用于search.js自动补全
        var _idName = '单 号';
        var _value = 'projectName';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/touYing/";
        var pageName = 'touYing';
        var name = 'TouYing';
        var title = '投影设备安装条件检查表';
        var idField = 'id';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[{
            field: 'user',
            title: '提交人',
            width: 50,
            align: 'center',
            formatter: function (value, row, index) {
                if (row.user) {
                    return row.user.allName;
                } else {
                    return "获取不到该提交人！";
                }
            }
        }, {
            field: 'project_name',
            title: '工程名称',
            width: 80,
            align: 'center',
        }, {
            field: 'entry_name',
            title: '项目名称',
            width: 80,
            align: 'center',
        }, {
            field: 'touYing_date',
            title: '日 期',
            width: 100,
            align: 'center',
            formatter: function (value, row, index) {
                return value;
            }
        }, {
            field: 'operation',
            title: '操作',
            width: 100,
            align: 'center',
            formatter: function (value, row, index) {
                var btn = '<a class="editcls" onclick="openWord(\''+row.id+'\')" href="javascript:void(0)">查看Word</a>&nbsp &nbsp &nbsp ' +
                    '&nbsp &nbsp &nbsp<a class="editcls" onclick="downWord(\''+row.id+'\')" href="javascript:void(0)">下载Word</a>';
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

        $(document).ready(function () {
            // 加载表格数据
            $('#dg').datagrid({
                url : root + pageName + 'Page',
                idField : idField,//指明哪一个字段是标识字段。
                title : title,
                columns : columns,
                frozenColumns : [[{
                    field : 'ck',
                    align: 'center',
                    checkbox : true
                }, {
                    title : _idName,
                    field : idField,
                    align: 'center',
                    sortable : true,
                    width:150
                }]],
                fit:true,
                fitColumns:true,
                iconCls : 'icon-tip',
                singleSelect : false,// 如果为true，则只允许选择一行。
                pagination : true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                striped : true,// 是否显示斑马线效果。
                collapsible : true,	//定义是否显示可折叠按钮。
                rownumbers : true,//如果为true，则显示一个行号列。
                nowrap : true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                sortName : idField,//定义哪些列可以进行排序。
                sortOrder : 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort : false,//定义从服务器对数据进行排序。
                loading : true,//显示载入状态。
                loadMsg : '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber : 1,// 在设置分页属性的时候初始化页码。
                pageSize : 50,// 在设置分页属性的时候初始化页面大小。
                pageList : [ 10, 20, 30, 40, 50 ],//在设置分页属性的时候 初始化页面大小选择列表。
                toolbar : [ {
                    text : '添加',
                    iconCls : 'icon-add',
                    handler : function() {
                        window.parent.openTab("填写投影设备安装条件检查表","/word/touYingAdd.jsp",'icon-touYing');
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
                    searchType = name;
                    searchValue = value;
                    var startDate = $('#s_startDate').datebox('getValue');
                    var endDate = $('#s_endDate').datebox('getValue');
                    if ((startDate == ''&&endDate!='') || (startDate!=''&&endDate == '')) {
                        $.messager.alert('温馨提示', '请准确输入操作开始/结束时间');
                    } else if (startDate > endDate) {
                        $.messager.alert('警告', '结束时间不能早于开始时间!');
                    } else {
                        $('#dg').datagrid('load', {
                            "searchType": name,
                            "searchValue": value,
                            "startDate": startDate,
                            "endDate": endDate
                        });
                    }

                },
                class: "easyui-searchbox",
                menu: '#searchType',
                prompt: '请输入搜索值'
            });
            // 点击重置按钮
            $('#btnReset').bind('click', function () {
                $('#searchForm').form('clear');
            });

        })

        //ajax下载word文件
        function downWord(id) {
            $.ajax({
                url: "${pageContext.request.contextPath}/touYing/getTouYingWord",
                type: 'post',
                data: {"id": id},//返回给客户端的json数据
                async: false,
                success: function (data, filename) {
                    debugger
                    var blob = new Blob([data], { type: 'application/msword' });
                    var link = document.createElement("a");
                    var body = document.querySelector("body");
                    link.href = window.URL.createObjectURL(blob);
                    link.download = id;//文件名
                    link.style.display = "none";
                    body.appendChild(link);
                    link.click();
                    body.removeChild(link);
                    window.URL.revokeObjectURL(link.href);
                }
            });
        }
        //打开word界面
        function openWord(id) {
     window.parent.openTab("查看投影设备安装条件检查表","/word/touYingOpen.jsp?id="+id,'icon-touYing');
        }

        window.top["reload_touYing"] = function () {
            $("#dg").datagrid("reload");
        }
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td> &nbsp;
                    时 间:&nbsp;<input type="text" id="s_startDate" size="20"/> --
                    <input type="text" id="s_endDate" size="20"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'id',iconCls:'icon-ok'">单号</div>
                        <div data-options="name:'ALLNAME_'">提交人</div>
                        <div data-options="name:'project_name'">工程名称</div>
                        <div data-options="name:'entry_name'">项目名称</div>
                    </div>
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
</body>
</html>