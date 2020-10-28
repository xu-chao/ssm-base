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
    <title>质检管理</title>
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
        var g_searchType = '';
        var g_searchValue = '';
        //用于search.js自动补全
        var _idName = '采购单号';
        var _value = 'nId';
        //用于crud.js的esayui初始参数
        var root = "${pageContext.request.contextPath}/needGoods/";
        var pageName = 'needGoodsThrough';
        var name = 'NeedGoods';
        var title = '最新质检列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的质检信息将被清除！";
        var flag = "checkout";
        var updateRoot = '${pageContext.request.contextPath}/checkout/';
        var updateName = 'Checkout';
        var updateId = 'checkoutId';
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
                field: 'checkoutId',
                title: '质检流水号',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.checkoutId;
                }
            },
            {
                field: 'checkoutDate',
                title: '送检日期',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutDate;
                    }
                },
                hidden: true//列表默认隐藏
            },
            {
                field: 'SUPPLIER_NAME',
                title: '供应商',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.supplier.SUPPLIER_NAME;
                    }
                }
            }, {
                field: 'checkoutMount',
                title: '合格数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutMount;
                    }
                }
            }, {
                field: 'checkoutNotQuaMount',
                title: '不合格数量',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutNotQuaMount;
                    }
                }
            }, {
                field: 'checkoutType',
                title: '送检类型',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutType;
                    }
                }
                // hidden: true//列表默认隐藏
            }, {
                field: 'checkoutMaterial',
                title: '送检资料',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutMaterial;
                    }
                }
            }, {
                field: 'checkoutStatus',
                title: '首次检验状态',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutStatus;
                    }
                },
                // hidden: true//列表默认隐藏
            },
            {
                field: 'checkoutDataStatus',
                title: '单据状态',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutDataStatus;
                    }
                },
                hidden: true//列表默认隐藏
            }, {
                field: 'checkoutLabelStatus',
                title: '标签状态',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutLabelStatus;
                    }
                },
                hidden: true//列表默认隐藏
            }, {
                field: 'checkoutNum',
                title: '质检次数',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else {
                        return row.checkout.checkoutNum;
                    }
                },
                hidden: true//列表默认隐藏
            }, {
                field: 'action',
                title: '操作',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkoutId == null) {
                        return "暂无质检记录";
                    } else {
                        return "<a href=\"javascript:openCheckoutList('" + row.checkoutId + "')\" class=\"easyui-linkbutton\">编辑</a>";
                    }
                },
            }
        ]];
        var h = 400;
        var w = 400;
        var _title = '质检管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].checkoutId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].checkoutId);
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
                }
            );

            $('#dg').datagrid({
                rowStyler: function (index, row) {
                    if (typeof row.checkout == 'undefined') {
                        return;
                    } else if (row.checkout == null) {
                        return 'background-color:#bce672;color:#fff;';
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

            $('#checkoutDate').datetimebox({
                required: true,
                showSeconds: true,
                editable: false,
            });

        })

        <!-- 质检历史 -->
        function openCheckoutList(checkoutId) {
            debugger
            // var row = selectRows[0];
            if (checkoutId != null) {
                openCheckoutListDialog1(checkoutId);
            } else {
                openCheckoutListDialog2();
            }
        }

        function openCheckoutListDialog1(checkoutId) {
            debugger
            $("#dlgCheckoutList1").dialog("open").dialog("setTitle", "（" + checkoutId + "）质检记录表");
            $('#dgCheckoutList').datagrid({
                url: '${pageContext.request.contextPath}/checkoutList/checkoutListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'checkoutListId', title: '质检记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'checkoutDate', title: '送检日期', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutDate;
                        }
                    },
                    {
                        field: 'SUPPLIER_NAME', title: '供应商', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.checkout.supplier.SUPPLIER_NAME;
                        }
                    },
                    {
                        field: 'checkoutMount', title: '合格数量', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutMount;
                        }
                    },
                    {
                        field: 'checkoutNotQuaMount',
                        title: '不合格数量',
                        width: 20,
                        align: 'center',
                        tooltip: true,
                        calcable: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutNotQuaMount;
                        }
                    },
                    {
                        field: 'checkoutType', title: '送检类型', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutType;
                        }
                    },
                    {
                        field: 'checkoutMaterial', title: '送检资料', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutMaterial;
                        }
                    },
                    {
                        field: 'checkoutPerson', title: '送检人员', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutPerson;
                        }
                    },
                    {
                        field: 'checkoutStatus',
                        title: '首次检验状态',
                        width: 20,
                        align: 'center',
                        tooltip: true,
                        hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutStatus;
                        }
                    },
                    {
                        field: 'checkoutDataStatus',
                        title: '单据状态',
                        width: 20,
                        align: 'center',
                        tooltip: true,
                        hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutDataStatus;
                        }
                    },
                    {
                        field: 'checkoutLabelStatus',
                        title: '标签状态',
                        width: 20,
                        align: 'center',
                        tooltip: true,
                        hidden: true,
                        formatter: function (value, row, index) {
                            return row.checkout.checkoutLabelStatus;
                        }
                    },
                    {
                        field: 'checkoutNum', title: '质检次数', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return "第" + row.checkout.checkoutNum + "次";
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
                queryParams: {checkoutId: checkoutId},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
                // showFooter: true,
            })
        }

        function openCheckoutListDialog2() {
            $("#dlgCheckoutList2").dialog("open").dialog("setTitle", "暂无质检记录");
        }

        <!-- 质检历史 -->
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
                        <div data-options="name:'checkoutId'">质检流水号</div>
                        <div data-options="name:'checkoutType'">送检类型</div>
                        <div data-options="name:'checkoutMaterial'">送检资料</div>
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
                <td>送检日期
                </td>
                <td>
                    <input id="checkoutDate" name="checkoutDate" type="text" class="easyui-validatebox"
                           style="width:200px;"/>
                    <input id="NID_t" type="hidden" name="NID"/>
                    <input id="goodsId_t" type="hidden" name="goodsId"/>
                    <input id="storageId_t" type="hidden" name="storageId"/>
                    <%--<input id="checkoutId_t" type="hidden" name="checkoutId"/>--%>
                    <input type="hidden" id="flag" name="flag"/>
                    <input type="hidden" id="checkoutFlag" name="checkoutFlag"/>
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
                <td>合格数量
                </td>
                <td>
                    <input id="checkoutMount" name="checkoutMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>不合格数量
                </td>
                <td>
                    <input id="checkoutNotQuaMount" name="checkoutNotQuaMount" class="easyui-numberspinner" value="0"
                           data-options="required:false,increment:2" style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>送检类型
                </td>
                <td>
                    <input id="checkoutType" name="checkoutType" type="text" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>送检材料
                </td>
                <td>
                    <input id="checkoutMaterial" name="checkoutMaterial" class="easyui-textbox easyui-validatebox"
                           style="width:200px;"/>
                </td>
            </tr>
            <tr>
                <td>首次送检状态
                </td>
                <td>
                    <span>
                        <input class="checkoutStatus" type="radio" name="checkoutStatus" value="1"
                               checked="true">合格</input>
                        <input class="checkoutStatus" type="radio" name="checkoutStatus" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>单据状态
                </td>
                <td>
                    <span>
                        <input class="checkoutDataStatus" type="radio" name="checkoutDataStatus" value="1"
                               checked="true">合格</input>
                        <input class="checkoutDataStatus" type="radio" name="checkoutDataStatus" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>标签状态
                </td>
                <td>
                    <span>
                        <input class="checkoutLabelStatus" type="radio" name="checkoutLabelStatus" value="1"
                               checked="true">合格</input>
                        <input class="checkoutLabelStatus" type="radio" name="checkoutLabelStatus" value="2">不合格</input>
                    </span>
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnSave" class="easyui-linkbutton" type="button">保存</button>
    </form>
</div>
<!-- 质检历史记录 -->
<div id="dlgCheckoutList1" class="easyui-window" title="质检历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgCheckoutList">
    </table>
</div>

<div id="dlgCheckoutList2" class="easyui-window" title="质检历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无质检记录</span>
</div>
<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
    </form>
</div>
</body>
</html>