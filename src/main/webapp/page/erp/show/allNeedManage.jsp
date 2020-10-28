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
    <title>需求汇总管理</title>
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

    <%--翻页导航--%>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.navigating.js"></script>

    <%--右键菜单--%>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/menu/jeasyui.extensions.menu.js"></script>
    <script src="http://www.easyui-extlib.com/Scripts/jquery-easyui-extensions/datagrid/jeasyui.extensions.datagrid.rowContext.js"></script>

    <script type="text/javascript">
        var pageContext = "${pageContext.request.contextPath}";
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '采购单号';
        var _value = 'nId';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/needGoods/";
        var pageName = 'needGoodsThrough';
        var name = 'NeedGoods';
        var title = '需求汇总列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的所有信息将被清除！";
        //一般为空
        var flag = "need";
        var columns = [[
            {
                field: 'EApplicantData',
                title: '申请日期',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    return row.need.EApplicantData;
                }
            }, {
                field: 'EPType',
                title: '加工类型',
                width: 50,
                align: 'center',
                hidden: true,//列表默认隐藏,
                formatter: function (value, row, index) {
                    return row.need.EPType;
                }
            }, {
                field: 'EBillCompany',
                title: '提单公司',
                width: 50,
                align: 'center',
                hidden: true,//列表默认隐藏
                tooltip: true,
                formatter: function (value, row, index) {
                    return row.need.EBillCompany;
                }
            }, {
                field: 'EType',
                title: '专业/类型',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.need.EType == 0 || row.need.EType == "机械生产") {
                        return "机械生产";
                    } else if (row.need.EType == 4 || row.need.EType == "电气生产") {
                        return "电气生产";
                    } else if (row.need.EType == 6 || row.need.EType == "机械现场") {
                        return "机械现场";
                    } else if (row.need.EType == 7 || row.need.EType == "电气现场") {
                        return "电气现场";
                    } else if (row.need.EType == 8 || row.need.EType == "试制研发") {
                        return "试制研发";
                    }
                }
            }, {
                field: 'EZone',
                title: '区域',
                width: 50,
                align: 'center',
                hidden: true,//列表默认隐藏
                formatter: function (value, row, index) {
                    return row.need.EZone;
                }
            }, {
                field: 'EConsumer',
                title: '工厂耗材',
                width: 50,
                align: 'center',
                hidden: true,//列表默认隐藏
                formatter: function (value, row, index) {
                    return row.need.EConsumer;
                }
            }, {
                field: 'cityName',
                title: '工程名称',
                width: 50,
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.need.subProject == null) {
                        return "";
                    } else {
                        return row.need.subProject.city.cityName;
                    }
                }
            },
            {
                field: 'parkName',
                title: '公园名称',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    if (row.need.subProject == null) {
                        return "";
                    } else {
                        return row.need.subProject.park.parkName;
                    }
                }
            },
            {
                field: 'subProjectName',
                title: '项目名称',
                width: 50,
                align: 'center',
                tooltip: true,
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.need.subProject == null) {
                        return "";
                    } else {
                        return row.need.subProject.subProjectName;
                    }
                }
            }, {
                field: 'ESubProjectNameElse',
                title: '子项目名称',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    return row.need.ESubProjectNameElse;
                }
            }, {
                field: 'ESysName',
                title: '系统名称',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.need.ESysName;
                }
            }, {
                field: 'EApplicant',
                title: '申请人',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.need.EApplicant;
                }
            }, {
                field: 'COMPANY_NAME', title: '公司主体', width: 50, align: 'center', tooltip: true,
                formatter: function (value, row, index) {
                    if (row.need.company == null) {
                        return "";
                    } else {
                        return row.need.company.COMPANY_NAME;
                    }
                }
            }, {
                field: 'endDate',
                title: '结束日期',
                width: 50,
                align: 'center',
                hidden: true,//列表默认隐藏
                formatter: function (value, row, index) {
                    return row.need.endDate;
                }
            }, {
                field: 'goodsId',
                title: '存货编码',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    if (value.substr(3, 3) == 'NEW') {
                        return "<label style='color: red'>" + value + "</label>";
                    } else {
                        return value;
                    }
                }
            }, {
                field: 'goodsName',
                title: '物料名称',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsName;
                    }
                }
            }, {
                field: 'goodsType',
                title: '类别',
                width: 50,
                align: 'center',
                hidden: true, //列表默认隐藏
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsType;
                    }
                }
            }, {
                field: 'goodsCode',
                title: '图号',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsCode;
                    }
                }
            }, {
                field: 'goodsUnit',
                title: '单位',
                width: 20,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.goods == null) {
                        return "";
                    } else {
                        return row.goods.goodsUnit;
                    }
                }
                // hidden: true//列表默认隐藏
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
            },
        ]];
        var h = 340;
        var w = 580;
        var _title = '计划需求管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].id;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].id);
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
                // onDblClickRow: function () {
                //     edit(flag);
                // }
                navigatingWithKey: true,
                navigateHandler: {
                    up: function (targetIndex) {
                        console.log("上之后被selected的数据索引" + targetIndex);
                    },
                    down: function (targetIndex) {
                        console.log("下之后被selected的数据索引" + targetIndex);
                    },
                    enter: function (selectedData) {
                        alert("功能有待开发！");
                    }
                },
                enableRowContextMenu: true,
                selectOnRowContextMenu: true,
                refreshMenu: false,
                pagingMenu: false,
                rowContextMenu: [
                    {
                        text: "新增/修改", iconCls: "icon-ok", handler: function (e, item, menu, grid, rowIndex, row) {
                            // alert("触发菜单1,数据行索引[" + rowIndex + "],数据:[" + row.AreaName + "]");
                            alert("新增/修改");
                        }
                    }
                ],
                fitColumns: true,
                toolbar: [
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
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/CityParkProject.js"></script>
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
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'NID',iconCls:'icon-ok'">采购单号</div>
                        <div data-options="name:'EApplicant'">申请人</div>
                        <div data-options="name:'ParkName'">公园名称</div>
                        <div data-options="name:'ESubProjectNameElse'">子项目名称</div>
                        <div data-options="name:'ECompanyId'">公司主体</div>
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
                <td>加工类型：</td>
                <td>
                    <input id="EPType" name="EPType" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>专业/类型：</td>
                <td>
                    <input id="EType" name="EType" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>项目</td>
                <td>
                    <input type="text" id="cityIDSelect" name="EPName" size="10" maxlength="40" class="easyui-combobox"
                           data-options="required:false,panelHeight:'auto'"/>
                    <input type="text" id="parkIDSelect" name="EParkName" size="10" maxlength="40"
                           class="easyui-combobox" data-options="required:false,panelHeight:'auto'"/>
                    <input type="text" id="projectIDSelect" name="ESubID" size="10" maxlength="40"
                           class="easyui-combobox" data-options="required:false,panelHeight:'auto'"/>
                    <select id="ESysName" class="easyui-combobox" style="width:80px;" name="ESysName"
                            size="10" data-options="editable:false,panelHeight:'auto'">
                        <option value="辅助">辅助</option>
                        <option value="轨道">轨道</option>
                        <option value="平台">平台</option>
                        <option value="特技">特技</option>
                        <option value="投影">投影</option>
                        <option value="辅助">小车</option>
                        <option value="银幕架">银幕架</option>
                        <option value="空白">空白</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>子项目名称：</td>
                <td>
                    <input id="ESubProjectNameElse" name="ESubProjectNameElse" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>提单公司</td>
                <td>
                    <input type="text" id="EBillCompany" name="EBillCompany" size="10" maxlength="20"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                    区域
                    <input type="text" id="EZone" name="EZone" size="5" maxlength="10" value="工厂"
                           class="easyui-textbox easyui-validatebox" data-options="required:false"/>
                    工厂耗材
                    <select id="EConsumer" class="easyui-combobox" style="width:60px;" name="EConsumer"
                            panelMaxHeight="200px" data-options="editable:false,panelHeight:'auto'">
                        <option value="否">否</option>
                        <option value="是">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>公司主体：</td>
                <td>
                    <input id="COMPANY_NAME" name="ECompanyId" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>要求到货时间：</td>
                <td>
                    <input type="text" id="endDate" style="width:200px;" name="endDate" class="easyui-validatebox"
                           required="true"/>
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