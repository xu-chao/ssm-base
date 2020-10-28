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
    <title>定时计划管理</title>
    <link href="${pageContext.request.contextPath}/static/css/Site.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <link href="http://www.easyui-extlib.com/Content/icons/icon-standard.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>

    <script type="text/javascript">
        //用于search.js自动补全
        var _url = '${pageContext.request.contextPath}/scheduleJob/searchScheduleJob';
        var _idName = '任务ID';
        var _value = 'jobName';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/scheduleJob/";
        var pageName = 'scheduleJob';
        var name = 'ScheduleJob';
        var title = '任务列表';
        var idField = 'scheduleJobId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var columns = [[{
            field : 'jobName',
            title : '任务名称',
            width : 50,
            align: 'center'
        }, {
            field : 'aliasName',
            title : '任务别名',
            width : 50,
            align: 'center'
        }, {
            field : 'jobGroup',
            title : '任务分组',
            width : 50,
            align: 'center',
            hidden: true//列表默认隐藏
        }, {
            field : 'jobTrigger',
            title : '触发器',
            width : 50,
            align: 'center',
            hidden: true//列表默认隐藏
        }, {
            field : 'status',
            title : '任务状态',
            width : 50,
            align: 'center',
            formatter: function formatAction(val,row){
                if(val=='NORMAL'){
                    return "NORMAL"
                }else {
                    return "ABNORMAL"
                }
            }
        }, {
            field : 'cronExpression',
            title : '任务运行时间表达式',
            width : 100,
            align: 'center'
        }, {
            field : 'isSync',
            title : '是否异步',
            width : 50,
            align: 'center',
            formatter: function formatAction(val,row){
                if(val){
                    return "异步"
                }else {
                    return "同步"
                }
            }
        }, {
            field : 'description',
            title : '任务描述',
            width : 50,
            align: 'center'
        }, {
            field : 'gmtCreate',
            title : '创建时间',
            width : 50,
            align: 'center'
        }, {
            field : 'gmtModify',
            title : '修改时间',
            width : 50,
            align: 'center',
            hidden: true//列表默认隐藏
        }, {
            field : 'url',
            title : '任务执行url',
            width : 100,
            align: 'center',
            hidden: true//列表默认隐藏
        }, {
            field : 'action',
            title : '操作',
            width : 200,
            align: 'center',
            formatter: function formatAction(val,row){
                return "<a href='${pageContext.request.contextPath}/repairTask/showCurrentView?taskId="+row.id+"'>暂停</a>&nbsp;" +
                    "<a href='${pageContext.request.contextPath}/repairTask/showCurrentView?taskId="+row.id+"'>恢复</a>&nbsp;" +
                "<a href='${pageContext.request.contextPath}/repairTask/showCurrentView?taskId="+row.id+"'>运行一次</a>&nbsp;"
            }
        }]];
        var h = 180;
        var w = 280;
        var _title = '任务管理';
        $(function() {
            $('.combo-text').bind('keyup', function(event) {
                if (event.keyCode == "13") {
                    //回车执行查询
                    //$('#btnSearch').click();
                    reloadgrid();
                }
            });
            // 点击查询按钮
            $('#btnSearch').bind('click', function() {
                reloadgrid();
            });

            // 点击重置按钮
            $('#btnReset').bind('click', function() {
                $('#searchForm').form('clear');
                reloadgrid();
            });

            function reloadgrid() {
                // 把表单数据转换成json对象
                var formData = $('#searchForm').serializeJSON();
                $('#dg').datagrid('load', formData);
            }
        })

        function getID(info) {
            if(info == 'update'){
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].scheduleJobId;
                return row;
            }else {
                var selectRows = $("#dg").datagrid("getSelections");
                if(selectRows.length == 0){
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].scheduleJobId);
                }
                return strIds;
            }
        }
        $(document).ready(function () {
            $('#dg2').datagrid({
                remoteSort: false,
                nowrap: false,
                url: '${pageContext.request.contextPath}/scheduleJob/scheduleJobPage2',
                columns: [[
                    {
                        field : 'jobName',
                        title : '任务名称',
                        width : 50,
                        align: 'center'
                    }, {
                        field : 'aliasName',
                        title : '任务别名',
                        width : 50,
                        align: 'center'
                    }, {
                        field : 'jobGroup',
                        title : '任务分组',
                        width : 50,
                        align: 'center'
                    }, {
                        field : 'jobTrigger',
                        title : '触发器',
                        width : 50,
                        align: 'center'
                    }, {
                        field : 'status',
                        title : '任务状态',
                        width : 50,
                        align: 'center',
                        formatter: function formatAction(val,row){
                            if(val=='NORMAL'){
                                return "NORMAL"
                            }else {
                                return "ABNORMAL"
                            }
                        }
                    }, {
                        field : 'cronExpression',
                        title : '任务运行时间表达式',
                        width : 100,
                        align: 'center'
                    }, {
                        field : 'isSync',
                        title : '是否异步',
                        width : 50,
                        align: 'center',
                        formatter: function formatAction(val,row){
                            if(val){
                                return "异步"
                            }else {
                                return "同步"
                            }
                        }
                    }, {
                        field : 'description',
                        title : '任务描述',
                        width : 50,
                        align: 'center'
                    }, {
                        field : 'gmtCreate',
                        title : '创建时间',
                        width : 50,
                        align: 'center'
                    }, {
                        field : 'url',
                        title : '任务执行url',
                        width : 100,
                        align: 'center',
                        hidden: true//列表默认隐藏
                    }
                ]],
                // queryParams:{},
                pageSize: 20,
                sortName: 'gmtCreate',
                sortOrder: 'desc',
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
            });
        })
        /**
         * esayui通用增删改查以及导入导出
         */
        //提交的方法名称
        var method = "";
        var listParam = "";
        var saveParam = "";
        var message = "";
        $(function() {
            // 加载表格数据
            $('#dg').datagrid({
                url : root + pageName + 'Page' + listParam,
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
                    width:100
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
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                toolbar : [ {
                    text : '新增',
                    iconCls : 'icon-add',
                    handler : function() {
                        // 打开前清空表单
                        $('#editForm').form('clear');
                        // 设置保存按钮提交的方法为add
                        method = "add";
                        // 关闭编辑窗口
                        $('#editDlg').dialog('open');
                        $('#' + _value).attr("readonly", false);
                    }
                }, '-', {
                    text : '修改',
                    iconCls : 'icon-edit',
                    handler : function() {
                        edit();
                    }
                }, '-', {
                    text : '删除',
                    iconCls : 'icon-cut',
                    handler : function() {
                        var temp = '';
                        var strIds = getID(temp);
                        del(strIds);
                    }
                }, '-', {
                    text : '导出',
                    iconCls : 'icon-excel',
                    handler : function() {
                        var formData = $('#searchForm').serializeJSON();
                        // 下载文件
                        $.download(root + pageName + "Export" + listParam, formData);
                    }
                }, '-', {
                    text : '导入',
                    iconCls : 'icon-save',
                    handler : function() {
                        $('#importDlg').dialog('open');
                    }
                } ],
                onDblClickRow : function() {
                    edit();
                },
            });

            if (typeof (height) != "undefined") {
                h = height;
            }
            if (typeof (width) != "undefined") {
                w = width;
            }
            // 初始化编辑窗口
            $('#editDlg').dialog({
                title : _title,// 窗口标题
                width : w,// 窗口宽度
                height : h,// 窗口高度
                closed : true,// 窗口是是否为关闭状态, true：表示关闭
                modal : true
                // 模式窗口
            });

            // 点击保存按钮
            $('#btnSave').bind('click', function() {
                // 做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。
                var isValid = $('#editForm').form('validate');
                if (isValid == false) {
                    return;
                }
                var formData = $('#editForm').serializeJSON();
                if(method == 'add'){
                    $.ajax({
                        url : root + "exist" + name + saveParam,
                        data : formData,
                        dataType : 'json',
                        type : 'post',
                        success : function(rtn) {
                            if(rtn.success){
                                $.messager.alert("系统系统", "该记录已存在，请核对更换！");
                                $("#"+ _value).focus();
                            }else {
                                $.ajax({
                                    url : root + method + name + saveParam,
                                    data : formData,
                                    dataType : 'json',
                                    type : 'post',
                                    success : function(rtn) {
                                        if (rtn.success) {
                                            message = "新增成功";
                                        }else{
                                            message = "新增失败";
                                        }

                                        $.messager.alert("提示", message , 'info', function() {
                                            if (rtn.success) {
                                                // 成功的话，我们要关闭窗口
                                                $('#editDlg').dialog('close');
                                                // 刷新表格数据
                                                $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                                                $('#dg').datagrid('reload');
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }else if(method == 'update'){
                    var id = getID(method);
                    $.ajax({
                        url : root + method + name + addParam + id,
                        data : formData,
                        dataType : 'json',
                        type : 'post',
                        success : function(rtn) {
                            message = "修改成功";
                            $.messager.alert("提示", message , 'info', function() {
                                if (rtn.success) {
                                    // 成功的话，我们要关闭窗口
                                    $('#editDlg').dialog('close');
                                    // 刷新表格数据
                                    $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                                    $('#dg').datagrid('reload');
                                }
                            });
                        }
                    });
                }
            });

            // 判断是否有导入的功能
            var importForm = document.getElementById('importForm');
            if (importForm) {
                $('#importDlg').dialog(
                    {
                        title : '导入数据',
                        width : 330,
                        height : 130,
                        modal : true,
                        closed : true,
                        buttons : [ {
                            text : '导入',
                            handler : function() {
                                $.ajax({
                                    url :root + pageName + 'Import',
                                    data : new FormData($('#importForm')[0]),
                                    type : 'post',
                                    processData : false,
                                    contentType : false,
                                    dataType : 'json',
                                    success : function(data) {
                                        $.messager.alert('提示', data.msg,
                                            'info', function() {
                                                if (data.status==200) {
                                                    $('#importDlg').dialog('close');
                                                    $('#importForm').form('clear');
                                                    $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                                                    $('#dg').datagrid('reload');
                                                }
                                            });
                                    }
                                });
                            }
                        } ]
                    });
            }
            $('#dg').datagrid({
                toolbar: '#tb'
            });
        });

        /**
         * 删除
         */
        function del(strIds) {
            var ids = strIds.join(",");
            $.messager.confirm("系统提示", "您确定要删除这<font color=red>" + strIds.length + "</font>条数据吗?", function (r) {
                if (r) {
                    $.post(root+ pageName + 'Delete', {ids: ids}, function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "数据已经成功删除！");
                            $("#dg").datagrid("clearSelections");//解决方法：在删除数据成功后清空所有的已选择项
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "数据删除失败，请联系管理员！");
                        }
                    }, "json");
                }
            });
        }

        function edit() {
            // 清空表单内容
            $('#editForm').form('clear');
            // 设置保存按钮提交的方法为update
            method = "update";
            // 获取被选中行的数据
            var selectRows = $("#dg").datagrid("getSelections");
            if (selectRows.length != 1) {
                $.messager.alert("系统提示", "请选择一条要编辑的数据！");
                return;
            }else {
                var row = selectRows[0];
                // 弹出窗口
                $('#editDlg').dialog('open');
                // 加载数据
                $('#editForm').form('load', row);
                if(_value = "cityName"){
                    $('#' + _value ).attr("readonly", true);
                }else{
                    $('#' + _value ).attr("readonly", false);
                }

            }
        }

    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download.js"></script>
</head>
<body class="easyui-layout">
<!-- 数据表格区 -->
<div data-options="region:'center',collapsible:true,split:true"
     style="width: 600px;padding: 4px; background-color: #eee">
    <!--搜索区  -->
    <table id="dg" collapsed="true"></table>
    <table id="dg2" title="运行中的任务"
           fitColumns="true" pagination="true" rownumbers="true">
    </table>
</div>
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>任务名称：</td>
                <td>
                    <input id="jobName" name="jobName" class="easyui-validatebox" data-options="required:true,missingMessage:'任务名称不能为空!'">
                </td>
            </tr>
            <tr>
                <td>任务别名：</td>
                <td><input id="aliasName" type="text" name="aliasName"></td>
            </tr>
            <tr>
                <td>任务分组：</td>
                <td><input id="jobGroup" type="text" name="jobGroup"></td>
            </tr>
            <tr>
                <td>触发器：</td>
                <td><input id="jobTrigger" type="text" name="jobTrigger"></td>
            </tr>
            <tr>
                <td>任务状态：</td>
                <td><input id="status" type="text" name="status"></td>
            </tr>
            <tr>
                <td>任务运行时间表达式：</td>
                <td><input id="cronExpression" type="text" name="cronExpression"></td>
            </tr>
            <tr>
                <td>是否异步：</td>
                <td><input id="isSync" type="text" name="isSync"></td>
            </tr>
            <tr>
                <td>任务描述：</td>
                <td><input id="description" type="text" name="description"></td>
            </tr>
            <tr>
                <td>创建时间：</td>
                <td><input id="gmtCreate" type="text" name="gmtCreate"></td>
            </tr>
            <tr>
                <td>任务描述：</td>
                <td><input id="gmtModify" type="text" name="gmtModify"></td>
            </tr>
            <tr>
                <td>创建时间：</td>
                <td><input id="url" type="text" name="url"></td>
            </tr>
        </table>
        <br />
        <button id="btnSave" class="easyui-linkbutton" type="button">保存</button>
    </form>
</div>
<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
    </form>
</div>

</body>
<div id="tb">
        <form id="searchForm">
    <td>任务名称 ：</td>
    <td><input name="jobName" class="easyui-combobox" id="inputtable" /></td>
    <td><a href="#" class="easyui-linkbutton"
           data-options="iconCls:'icon-search'" id="btnSearch">查询</a></td>
    <td><a href="#" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove'" id="btnReset">重置</a></td>
        </form>
</div>
</html>