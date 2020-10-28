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
    <title>送货管理</title>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>

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
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '采购单号';
        var _value = 'nId';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/needGoods/";
        var pageName = 'needGoodsThrough';
        var name = 'NeedGoods';
        var title = '最新送货列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的送货信息将被清除！";
        var flag = "purchase";
        var updateRoot = '${pageContext.request.contextPath}/purchase/';
        var updateName = 'Purchase';
        var updateId = 'purchaseId';
        var updateParam = '?' + updateId + '=';
        var columns = [[
            // {
            //     field: 'purchasedId',
            //     title: '送货流水号',
            //     width: 50,
            //     align: 'center',
            //     formatter: function (value, row, index) {
            //         if (row.purchase == null) {
            //             return "";
            //         } else {
            //             return row.purchase.purchasedId;
            //         }
            //     }
            //     // hidden: true//列表默认隐藏
            // },
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
            },
            {
                field: 'purchaseId',
                title: '送货流水号',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.purchaseId;
                }
            },
            {
                field: 'SUPPLIER_NAME',
                title: '供应商',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.purchase == null) {
                        return "";
                    } else {
                        return row.purchase.supplier.SUPPLIER_NAME;
                    }
                },
                hidden: true//列表默认隐藏
            }, {
                field: 'purchaseMount',
                title: '收货数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.purchase == null) {
                        return "";
                    } else {
                        return row.purchase.purchaseMount;
                    }
                }
            }, {
                field: 'purchasePerson',
                title: '采购人员',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.purchase == null) {
                        return "";
                    } else {
                        return row.purchase.purchasePerson;
                    }
                }
            }, {
                field: 'purchaseGoodsPerson',
                title: '收货人员',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.purchase == null) {
                        return "";
                    } else {
                        return row.purchase.user.allName;
                    }
                }
                // hidden: true//列表默认隐藏
            }, {
                field: 'REPO_NAME',
                title: '货位号（仓库）',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.purchase == null) {
                        return "";
                    } else {
                        return row.purchase.respository.REPO_ADDRESS;
                    }
                }
            }, {
                field: 'purchaseNum',
                title: '送货次数',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.purchase == null) {
                        return "";
                    } else {
                        return row.purchase.purchaseNum;
                    }
                },
                hidden: true//列表默认隐藏
            }, {
                field: 'action',
                title: '操作',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.purchaseId == null){
                        return "暂无送货记录";
                    }else {
                        return "<a href=\"javascript:openPurchaseList('" + row.purchaseId +"')\" class=\"easyui-linkbutton\">编辑</a>";
                    }
                },
            }
        ]];
        var h = 280;
        var w = 400;
        var _title = '送货管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].purchaseId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].purchaseId);
                }
                return strIds;
            }
        }

        $(document).ready(function () {

            $('#endDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

            $('#SUPPLIER_NAME').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/supplier/searchAllSpuplier',
                    valueField: 'supplier_ID',
                    textField: 'supplier_NAME',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    editable: true
                });

            $('#REPO_ADDRESS').combobox(
                {
                    prompt: '输入关键字后自动搜索',
                    // mode: 'remote',
                    url: '${pageContext.request.contextPath}/respository/searchAllRespository',
                    valueField: 'repo_ID',
                    textField: 'repo_ADDRESS',
                    panelHeight: 'auto',
                    panelMaxHeight: 150,
                    editable: true
                });

            $('#dg').datagrid({
                rowStyler: function (index, row) {
                    if (typeof row.purchase == 'undefined') {
                        return;
                    } else if (row.purchase == null) {
                        return 'background-color:#808080;color:#fff;';
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

            $('#purchaseDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

        })

        <!-- 送货历史 -->
        function openPurchaseList(purchaseId) {
            if (purchaseId != null) {
                openPurchaseListDialog1(purchaseId);
            } else {
                openPurchaseListDialog2();
            }
        }
        function openPurchaseListDialog1(purchaseId) {
            debugger
            $("#dlgPurchaseList1").dialog("open").dialog("setTitle", "（" + purchaseId + "）送货历史");
            $('#dgPurchaseList').datagrid({
                url: '${pageContext.request.contextPath}/purchaseList/purchaseListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'purchaseListId', title: '送货记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'purchaseDate', title: '送货日期', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseDate;
                        }
                    },
                    {
                        field: 'code', title: '供应商', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.purchase.supplier.SUPPLIER_NAME;
                        }
                    },
                    {
                        field: 'purchasedId', title: '送货单号', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchasedId;
                        }
                    },
                    {
                        field: 'count', title: '送货数量', width: 20, align: 'center', tooltip: true, calcable: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseMount;
                        }
                    },
                    {
                        field: 'purchasePerson', title: '采购人员', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchasePerson;
                        }
                    },
                    {
                        field: 'purchaseGoodsPerson', title: '收货人员', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            if(row.purchase == null){
                                return "";
                            }else {
                                return row.purchase.user.allName;
                            }
                        }
                    },
                    {
                        field: 'REPO_NAME', title: '仓库', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.respository.REPO_NAME;
                        }
                    },
                    {
                        field: 'purchaseLeave', title: '剩余数量', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.purchase.purchaseLeave;
                        }
                    },
                    {
                        field: 'total', title: '收货次数', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return "第" + row.purchase.purchaseNum + "次";
                        }
                    },
                ]],
                fit: true,
                fitColumns: true,
                iconCls: 'icon-tip',
                pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
                singleSelect: true,// 如果为true，则只允许选择一行。
                striped: true,// 是否显示斑马线效果。
                collapsible: true,	//定义是否显示可折叠按钮。
                rownumbers: true,//如果为true，则显示一个行号列。
                nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
                // sortName: 'goodsId',//定义哪些列可以进行排序。
                // sortOrder: 'desc',//定义列的排序顺序，只能是'asc'或'desc'。
                remoteSort: false,//定义从服务器对数据进行排序。
                loading: true,//显示载入状态。
                loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
                pageNumber: 1,// 在设置分页属性的时候初始化页码。
                pageSize: 50,// 在设置分页属性的时候初始化页面大小。
                pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
                queryParams: {purchaseId: purchaseId},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                // showFooter: true,
            })
        }
        function openPurchaseListDialog2() {
            $("#dlgPurchaseList2").dialog("open").dialog("setTitle", "暂无返修日报表");
        }
        <!-- 送货历史 -->
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
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'nId',iconCls:'icon-ok'">采购单号</div>
                        <div data-options="name:'goodsId'">存货编码</div>
                        <div data-options="name:'purchaseId'">送货流水号</div>
                        <div data-options="name:'purchasePerson'">采购人员</div>
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
                <td>收货日期
                </td>
                <td>
                    <input id="purchaseDate" name="purchaseDate" type="text" class="easyui-validatebox"
                           style="width:200px;"/>
                    <input id="NID_t" type="hidden" name="NID"/>
                    <input id="goodsId_t" type="hidden" name="goodsId"/>
                    <input id="storageId_t" type="hidden" name="storageId"/>
                    <%--<input id="purchaseId_t" type="hidden" name="purchaseId"/>--%>
                    <input type="hidden" id="flag" name="flag"/>
                    <input type="hidden" id="purchaseFlag" name="purchaseFlag"/>
                    <input type="hidden" id="mountStorage" name="mountStorage"/>
                </td>
            </tr>
            <tr>
                <td>供应商
                </td>
                <td>
                    <input id="SUPPLIER_NAME" name="SUPPLIER_ID" class="easyui-combobox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>收货数量
                </td>
                <td>
                    <input id="purchaseMount" name="purchaseMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td>采购人员
                </td>
                <td>
                    <input id="purchasePerson" name="purchasePerson" type="text"
                           class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>仓库
                </td>
                <td>
                    <input id="REPO_ADDRESS" name="REPO_ID" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnSave" class="easyui-linkbutton" type="button">保存</button>
    </form>
</div>
<!-- 送货历史 -->
<div id="dlgPurchaseList1" class="easyui-window" title="送货历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgPurchaseList">
    </table>
</div>

<div id="dlgPurchaseList2" class="easyui-window" title="送货历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无送货记录</span>
</div>
<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
    </form>
</div>
</body>
</html>