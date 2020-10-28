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
    <title>存货管理</title>
    <link href="${pageContext.request.contextPath}/static/css/Site.css" rel="stylesheet"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <style>
        .datagrid-body {
            overflow: auto;
        }

        .datagrid-header-row > td {
            border-bottom: 1px solid #DFDFDF !important;
        }
    </style>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>

    <link href="${pageContext.request.contextPath}/static/css/icon-standard.css" rel="stylesheet"/>
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
        var title = '汇总列表';
        var idField = 'nId';
        var addParam = '?' + idField + '=';
        var selectRows;
        var tooltip = "此条采购单号的所有信息将被清除！";
        //一般为空
        var flag = "needGoods";
        var columns = [[
            {
                title: '计划部', colspan: 26,
                // rowStyler: function (index, row) {
                //     return 'background-color:#1685a9;color:#fff;';
                // },
            },
            {title: '仓储部', colspan: 7},
            {title: '质检部', colspan: 11}
        ], [
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
                        return row.purchase.purchaseGoodsPerson;
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
            },
        ]];
        var h = 240;
        var w = 400;
        var _title = '汇总管理';

        function getID(info) {
            if (info == 'update') {
                var selectRows = $("#dg").datagrid("getSelections");
                var row = selectRows[0].nId;
                return row;
            } else {
                var selectRows = $("#dg").datagrid("getSelections");
                if (selectRows.length == 0) {
                    $.messager.alert("系统提示", "请选择要删除的数据！");
                    return;
                }
                var strIds = [];
                for (var i = 0; i < selectRows.length; i++) {
                    strIds.push(selectRows[i].nId);
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

            $('#dg').datagrid({
                rowStyler: function (index, row) {
                    if (typeof row.goods == 'undefined') {
                        return;
                    } else if (row.goods == null) {
                        return 'background-color:#1685a9;color:#fff;';
                    }
                },
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
                    <input type="text" id="flag" name="flag" hidden="true" value="goods"/>
                </td>
                <td>
                    <input id="ss" style="width:300px">
                    <div id="searchType" style="width:120px">
                        <div data-options="name:'nId',iconCls:'icon-ok'">采购单号</div>
                        <div data-options="name:'goodsId'">存货编码</div>
                        <div data-options="name:'goodsName'">物料名称</div>
                        <div data-options="name:'goodsCode'">图号</div>
                        <div data-options="name:'goodsUnit'">单位</div>
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
                <td>存货编码：</td>
                <td>
                    <input id="goodsId" name="goodsId" class="easyui-textbox easyui-validatebox"
                           data-options="required:true,missingMessage:'存货编码不能为空!'">
                </td>
            </tr>
            <tr>
                <td>物料名称：</td>
                <td>
                    <input id="goodsName" name="goodsName" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>图号：</td>
                <td>
                    <input id="goodsType" name="goodsType" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
            <tr>
                <td>单位：</td>
                <td>
                    <input id="goodsUnit" name="goodsUnit" class="easyui-textbox easyui-validatebox" type="text">
                </td>
            </tr>
        </table>
        <br/>
        <button id="btnSave" class="easyui-linkbutton" class="easyui-textbox easyui-validatebox" type="button">保存
        </button>
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