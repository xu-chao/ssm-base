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
    <title>返修管理</title>
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
        var title = '返修列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的返修信息将被清除！";
        var flag = "RCheckout";
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
            },
            {
                field: 'RCheckoutId',
                title: '不合格编号',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaId;
                    }
                }
            },
            {
                field: 'NotQuaLook',
                title: '外观',
                width: 50,
                align: 'center',
                tooltip: true,
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaLook;
                    }
                },
                hidden: true//列表默认隐藏
            },
            {
                field: 'NotQuaSize',
                title: '尺寸',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaSize;
                    }
                }
            }, {
                field: 'NotQuaYd',
                title: '硬度',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaYd;
                    }
                }
            }, {
                field: 'NotQuaType',
                title: '型号',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaType;
                    }
                }
            }, {
                field: 'NotQuaXn',
                title: '性能',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaXn;
                    }
                }
                // hidden: true//列表默认隐藏
            }, {
                field: 'NotQuaTs',
                title: '探伤',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaTs;
                    }
                }
            }, {
                field: 'NotQuaZl',
                title: '资料',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaZl;
                    }
                },
                // hidden: true//列表默认隐藏
            },
            {
                field: 'NotQuaQt',
                title: '其他',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaQt;
                    }
                },
                hidden: true//列表默认隐藏
            },{
                field: 'NotQuaDesc',
                title: '不合格描述',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.checkout == null) {
                        return "";
                    } else if(row.checkout.notQualified == null){
                        return "";
                    } else {
                        return row.checkout.notQualified.NotQuaDesc;
                    }
                },
                hidden: true//列表默认隐藏
            },{
                field: 'action',
                title: '操作',
                width: 50,
                align: 'center',
                formatter: function (value, row, index) {
                    debugger
                    if(row.checkout == null){
                        return "暂无返修记录";
                    }else if(row.checkout.notQualified == null){
                        return "暂无返修记录";
                    }else {
                        return "<a href=\"javascript:openRCheckoutList('" + row.checkout.notQualified.rCheckout.RCheckoutId +"')\" class=\"easyui-linkbutton\">编辑</a>";
                    }
                },
            }
        ]];
        var h = 320;
        var w = 300;
        var _title = '返修管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].RCheckoutId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].RCheckoutId);
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
                // rowStyler:function(index,row){
                //     if(typeof row.checkout == 'undefined'){
                //         return ;
                //     }else if(row.checkout == null) {
                //         return 'background-color:#bce672;color:#fff;';
                //     }
                // },
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

        <!-- 不合格原因 -->
        function openRCheckoutList(RCheckoutId) {
            debugger
            // var row = selectRows[0];
            if (RCheckoutId != null) {
                openRCheckoutListDialog1(RCheckoutId);
            } else {
                openrCheckoutListDialog2();
            }
        }

        function openRCheckoutListDialog1(rCheckoutId) {
            $("#dlgRCheckoutList1").dialog("open").dialog("setTitle", "（" + rCheckoutId + "）返修历史记录表");
            $('#dgRCheckoutList').datagrid({
                url: '${pageContext.request.contextPath}/rCheckoutList/rCheckoutListPage',
                columns: [[
                    {field: 'cb', checkbox: true, align: 'center'},
                    {field: 'RCheckoutListId', title: '返修记录Id', width: 20, align: 'center', hidden: true},
                    {
                        field: 'RCheckoutDate', title: '返修日期', width: 20, align: 'center',  tooltip: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutDate;
                        }
                    },
                    {
                        field: 'RCheckoutMount', title: '合格数量', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutMount;
                        }
                    },
                    {
                        field: 'RCheckoutNotQuaMount', title: '不合格数量', width: 20, align: 'center', hidden: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutNotQuaMount;
                        }
                    },
                    {
                        field: 'RCheckoutResult', title: '返修结果', width: 20, align: 'center', tooltip: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutResult;
                        }
                    },
                    {
                        field: 'RCheckoutDesc', title: '返修描述', width: 20, align: 'center', tooltip: true, hidden: true,
                        formatter: function (value, row, index) {
                            return row.rCheckout.RCheckoutDesc;
                        }
                    },
                    {
                        field: 'RCheckoutNum', title: '当前返修次数', width: 20, align: 'center',
                        formatter: function (value, row, index) {
                            return "第" + row.rCheckout.RCheckoutNum + "次";
                        }
                    }
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
                queryParams: {RCheckoutId: rCheckoutId},
                rowTooltip: false, //是否启用行数据的 tooltip 功能，若该属性为true，则设置在columns中的tooltip会自动失效
                border: false,
                autoHighlightColumn: true,
                enableHeaderContextMenu: true,
                enableHeaderClickMenu: true,
            })
        }

        function openRCheckoutListDialog2() {
            $("#dlgRCheckoutList2").dialog("open").dialog("setTitle", "暂无返修日报表");
        }

        <!-- 不合格原因 -->
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
                        <div data-options="name:'NotQuaId'">不合格流水号</div>
                        <div data-options="name:'NotQuaType'">不合格型号</div>
                        <div data-options="name:'NotQuaDesc'">不合格描述</div>
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
                <td>外观：
                </td>
                <td>
                    <span>
                        <input class="NotQuaLook" type="radio" name="NotQuaLook" value="1" checked="true">合格</input>
                        <input class="NotQuaLook" type="radio" name="NotQuaLook" value="2">不合格</input>
                        <input id="checkoutId_tt" type="hidden" name="checkoutId"/>
                        <input id="NotQuaId" type="hidden" name="NotQuaId"/>
                    </span>
                </td>
            </tr>
            <tr>
                <td>尺寸：
                </td>
                <td>
                    <span>
                        <input class="NotQuaSize" type="radio" name="NotQuaSize" value="1" checked="true">合格</input>
                        <input class="NotQuaSize" type="radio" name="NotQuaSize" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>硬度：
                </td>
                <td>
                    <span>
                        <input class="NotQuaYd" type="radio" name="NotQuaYd" value="1" checked="true">合格</input>
                        <input class="NotQuaYd" type="radio" name="NotQuaYd" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>型号：
                </td>
                <td>
                    <span>
                        <input class="NotQuaType" type="radio" name="NotQuaType" value="1" checked="true">合格</input>
                        <input class="NotQuaType" type="radio" name="NotQuaType" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>性能：
                </td>
                <td>
                    <span>
                        <input class="NotQuaXn" type="radio" name="NotQuaXn" value="1" checked="true">合格</input>
                        <input class="NotQuaXn" type="radio" name="NotQuaXn" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>探伤：
                </td>
                <td>
                    <span>
                        <input class="NotQuaTs" type="radio" name="NotQuaTs" value="1" checked="true">合格</input>
                        <input class="NotQuaTs" type="radio" name="NotQuaTs" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>资料：
                </td>
                <td>
                    <span>
                        <input class="NotQuaZl" type="radio" name="NotQuaZl" value="1" checked="true">合格</input>
                        <input class="NotQuaZl" type="radio" name="NotQuaZl" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>其他：
                </td>
                <td>
                    <span>
                        <input class="NotQuaQt" type="radio" name="NotQuaQt" value="1" checked="true">合格</input>
                        <input class="NotQuaQt" type="radio" name="NotQuaQt" value="2">不合格</input>
                    </span>
                </td>
            </tr>
            <tr>
                <td>描述：
                </td>
                <td>
                    <textarea type="text" id="NotQuaDesc" name="NotQuaDesc" rows="2" cols="15"
                              class="easyui-validatebox"></textarea>
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnSave" class="easyui-linkbutton" type="button">保存</button>
    </form>
</div>
<!-- 返修历史记录 -->
<div id="dlgRCheckoutList1" class="easyui-window" title="产品返修历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <table id="dgRCheckoutList">
    </table>
</div>
<div id="dlgRCheckoutList2" class="easyui-window" title="产品返修历史记录表" style="width: 500px;height: 300px;"
     data-options="closed:true"
     align="center">
    <span>暂无返修记录</span>
</div>
<!-- 导入窗口 -->
<div id="importDlg" style="padding: 2px;">
    <form id="importForm" enctype="multipart/form-data">
        导入文件:<input type="file" name="file">
    </form>
</div>
</body>
</html>