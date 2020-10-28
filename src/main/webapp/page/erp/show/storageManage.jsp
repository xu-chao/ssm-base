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
    <title>需求管理</title>
    <link href="${pageContext.request.contextPath}/static/css/Site.css" rel="stylesheet"/>
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

    <link href="http://www.easyui-extlib.com/Content/icons/icon-standard.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jeasyui-extensions-datagrid.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-menu.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-getColumnInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui-extensions-datagrid-columnToggle.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getDom.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.getRowData.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jeasyui.extensions.datagrid.tooltip.js"></script>

    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";
        var g_searchType ='';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '采购单号';
        var _value = 'nId';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/needGoods/";
        var pageName = 'needGoodsThrough';
        var name = 'NeedGoods';
        var title = '库存列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的库存信息将被清除！";
        var flag = "storage";
        var updateRoot = '${pageContext.request.contextPath}/storage/';
        var updateName = 'Storage';
        var updateId = 'storageId';
        var updateParam = '?' + updateId + '=';
        var columns = [[
            {
                field: 'storageId',
                title: '库存号',
                width: 50,
                align: 'center',
                hidden: true//列表默认隐藏
            }, {
                field: 'EApplicantData',
                title: '申请日期',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    return row.need.EApplicantData;
                }
            },{
                field: 'goodsId',
                title: '存货编码',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    if(value.substr(3,3) == 'NEW'){
                        return "<label style='color: red'>" + value + "</label>";
                    }else {
                        return value;
                    }
                }
            }, {
                field: 'mount',
                title: '工艺数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.mount;
                    }
                }
            }, {
                field: 'mountIn',
                title: '实购数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.mountIn;
                    }
                }
            }, {
                field: 'mountBack',
                title: '备用数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.mountBack;
                    }
                }
            }, {
                field: 'mountStorage',
                title: '申购数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.mountStorage;
                    }
                }
                // hidden: true//列表默认隐藏
            }, {
                field: 'ETemp',
                title: '临时备注',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.ETemp;
                    }
                }
            }, {
                field: 'EPlan',
                title: '计划备注',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.EPlan;
                    }
                }
            },
            {
                field: 'EIsRun',
                title: '发货标识',
                width: 50,
                align: 'center',
                hidden: true,
                tooltip: true,
                formatter: function (value, row, index) {
                    if (row.storage == null) {
                        return "";
                    } else {
                        return row.storage.EIsRun;
                    }
                }
            }
        ]];
        var h = 220;
        var w = 400;
        var _title = '库存管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].storageId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].storageId);
                }
                return strIds;
            }
        }

        $(document).ready(function(){

            $('#endDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#dg').datagrid({
                rowStyler:function(index,row){
                    if(typeof row.storage == 'undefined'){
                        return ;
                    }else if(row.storage == null) {
                        return 'background-color:#0eb83a;color:#fff;';
                    }
                },
                fitColumns: true,
                toolbar: [
                    {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: function () {
                            edit(flag);
                        }
                    },
                    '-',
                    {
                        text: '删除',
                        iconCls: 'icon-cut',
                        handler: function () {
                            var temp = '';
                            var strIds = getID(temp);
                            del(strIds);
                        }
                    },
                    '-',
                    {
                        text: '导出',
                        iconCls: 'icon-excel',
                        handler: function () {
                            var formData = $('#searchForm').serializeJSON();
                            // 下载文件
                            $.download(root + pageName + "Export" + listParam + '?flag=' + flag, formData);
                        }
                    },
                ],
            });

        })
    </script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/crud2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/download2.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'查询'"
     style="padding: 4px; background-color: #eee; height: 90px">
    <form id="searchForm">
        <table cellpadding="5">
            <tr>
                <td> &nbsp;
                    操作时间:&nbsp;<input type="text" id="s_startDate" name="startDate" size="20"/> --
                    <input type="text" id="s_endDate" name="endDate" size="20"/>
                    <input type="text" id="flag" name="flag" hidden="true" value="storage"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'nId',iconCls:'icon-ok'">采购单号</div>
                        <div data-options="name:'goodsId'">存货编码</div>
                        <div data-options="name:'ETemp'">临时备注</div>
                        <div data-options="name:'EPlan'">计划备注</div>
                        <div data-options="name:'mountStorage'">申购数量</div>
                    </div>
                </td>
                <td>
                    <a class="easyui-linkbutton"
                       data-options="iconCls:'icon-reload'" id="btnReset">重置</a>
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
<!-- 添加、修改 -->
<div id="editDlg" class="easyui-window" data-options="closed:true" align="center">
    <form id="editForm">
        <table>
            <tr>
                <td>需求
                </td>
                <td>
                    <input id="mount" name="mount" class="easyui-numberspinner"
                           data-options="required:false,increment:2" style="width:60px;"/>
                    备用
                    <input id="mountBack" name="mountBack" class="easyui-numberspinner"
                           data-options="required:false,increment:2" style="width:60px;"/>
                    库存
                    <input id="mountIn" name="mountIn" class="easyui-numberspinner"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>变更</td>
                <td>
                    <input type="text" id="ETemp" name="ETemp" size="30" maxlength="40"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>计划备注</td>
                <td>
                    <input type="text" id="EPlan" name="EPlan" size="30" maxlength="40"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                </td>
            </tr>
            <tr>
                <td>发货标识</td>
                <td>
                    <span>
                        <input class="EIsRun" type="radio" name="EIsRun" value="×" checked="true">×</input>
                        <input class="EIsRun" type="radio" name="EIsRun" value="√">√</input>
                    </span>
                </td>
            </tr>
        </table>
        <br/>
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
</html>