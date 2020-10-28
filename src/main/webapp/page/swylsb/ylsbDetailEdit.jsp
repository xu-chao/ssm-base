<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: LIUHD
  Date: 2020/01/10
  Time: 14:17
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>设备修改</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/icon.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date.format/date.format.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.serializejson.min.js"></script>
    <script type="text/javascript">
        <%--data: {"id": '${param.taskId}'},//返回给客户端的json数据--%>
        var arrayJd = []; //定义一个对象数据 存放每一个input的键值对（input中“name”为key,“value”为value）
        var arrayJh = []; //定义一个对象数据 存放每一个input的键值对（input中“name”为key,“value”为value）
        var swID;
        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/swylsb/findById",
                type: "post",
                data: {"id": 3},//返回给客户端的json数据
                async: false,
                dataType: 'json',
                success: function (data) {
                    var ylsb = data.ylsb;
                    $("#parkName").text(ylsb.project.park.parkAb);
                    $("#projectName").text(ylsb.project.projectName);
                    $("#sbID").text(ylsb.sbID);
                    $("#sbName").text(ylsb.sbName);
                    $("#sbName").text(ylsb.sbName)
                    $("#sbClass").text(ylsb.sbClass);
                    $("#sbStatus").text(ylsb.sblx.typeName);
                    $("#bzlx").text(ylsb.bzlx);
                    $("#yyqk").text(ylsb.yyqk);
                    $("#jyjbStatus").text(ylsb.jyjb.typeName);
                    $("#gydw").text(ylsb.gydw);
                    $("#azdw").text(ylsb.azdw);
                    $("#xssyStatus").text(ylsb.xuyao.typeName);
                    $("#sbsl").text(ylsb.sbsl);
                    $("#fzrUser").text(ylsb.fzrUser);
                    var sbDate = new Date(ylsb.sbDate.time);
                    $("#sbDate").text(sbDate.format("Y-m-d H:i:s"));
                    $("#startDate").text(new Date(ylsb.startDate.time).format("Y-m-d"));
                    $("#completeDate").text(new Date(ylsb.completeDate.time).format("Y-m-d"));
                    $("#approachDate").text(new Date(ylsb.approachDate.time).format("Y-m-d"));
                    //设备总体情况
                    $('#swylsbXqID').val(ylsb.ID);
                    $('#ztbz').val(ylsb.swylsbXq.ztbz);
                    $('#wtfk').val(ylsb.swylsbXq.wtfk);
                    //进度表
                    swID = ylsb.ID;
                    $('#1ID').val(swID);
                    $('#2ID').val(swID);
                    $('#1tsStatus').combobox('setValue', ylsb.swylsbJd.tsStatusType.typeID);
                    if (ylsb.swylsbJd.azjcsjStartDate != null) {
                        // $('#1azjcsjStartDate').val(new Date(ylsb.swylsbJd.azjcsjStartDate.time).format("Y-m-d"));
                        $('#1azjcsjStartDate').datebox('setValue', new Date(ylsb.swylsbJd.azjcsjStartDate.time).format("Y-m-d"));
                    }
                    if (ylsb.swylsbJd.azjcsjEndDate != null) {
                        // $('#1azjcsjEndDate').val(new Date(ylsb.swylsbJd.azjcsjEndDate.time).format("Y-m-d"));
                        $('#1azjcsjEndDate').datebox('setValue', new Date(ylsb.swylsbJd.azjcsjEndDate.time).format("Y-m-d"));
                    }
                    $('#1azStatus').combobox('setValue', ylsb.swylsbJd.azStatusType.typeID);
                    $('#1xxfaStatus').combobox('setValue', ylsb.swylsbJd.xxfaStatusType.typeID);
                    $('#1tdztStatus').combobox('setValue', ylsb.swylsbJd.tdztStatusType.typeID);
                    $('#1cgztStatus').combobox('setValue', ylsb.swylsbJd.cgztStatusType.typeID);
                    $('#1shfaStatus').combobox('setValue', ylsb.swylsbJd.shfaStatusType.typeID);
                    $('#1ztjgscStatus').combobox('setValue', ylsb.swylsbJd.ztjgscStatusType.typeID);
                    $('#1zxscStatus').combobox('setValue', ylsb.swylsbJd.zxscStatusType.typeID);
                    $('#1dqscStatus').combobox('setValue', ylsb.swylsbJd.dqscStatusType.typeID);
                    $('#1ztjgdhStatus').combobox('setValue', ylsb.swylsbJd.ztjgdhStatusType.typeID);
                    $('#1zxdhStatus').combobox('setValue', ylsb.swylsbJd.zxdhStatusType.typeID);
                    $('#1dqdhStatus').combobox('setValue', ylsb.swylsbJd.dqdhStatusType.typeID);
                    $('#1zjganStatus').combobox('setValue', ylsb.swylsbJd.zjganStatusType.typeID);
                    $('#1zxazStatus').combobox('setValue', ylsb.swylsbJd.zxazStatusType.typeID);
                    $('#1dqazStatus').combobox('setValue', ylsb.swylsbJd.dqazStatusType.typeID);
                    $('#1sbtsStatus').combobox('setValue', ylsb.swylsbJd.sbtsStatusType.typeID);
                    $('#1xssy1Status').combobox('setValue', ylsb.swylsbJd.xssy1StatusType.typeID);
                    $('#1gsjStatus').combobox('setValue', ylsb.swylsbJd.gsjStatusType.typeID);
                    $('#1jgysStatus').combobox('setValue', ylsb.swylsbJd.jgysStatusType.typeID);
                }
            });

            $('#parkIDSelect').combobox({
                disabled: false,
                url: '${pageContext.request.contextPath}/park/searchAllPark',
                valueField: 'parkID',
                textField: 'parkAb',
                onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                    $(this).combobox('setValue', rec[0].parkID);
                },
                onSelect: function (record) {
                    var parkID = record.parkID;
                    $('#projectIDSelect').combobox({
                        disabled: false,
                        url: '${pageContext.request.contextPath}/project/findProjectByParkID?parkID=' + parkID,
                        valueField: 'projectID',
                        textField: 'projectName',
                        onLoadSuccess: function (rec) {//加载完成后,val[0]写死设置选中第一项
                            if (rec.length > 0) {
                                $(this).combobox('setValue', rec[0].projectID);
                            }
                        }
                    }).combobox("clear");
                }
            }).combobox("clear");
            sbztFunc();

        })

        function sbztFunc() {

            var zongti = "选型方案未下发";   //置初始状态值
            var xxfaStatus = $('#1xxfaStatus').combobox('getValue');
            var tdztStatus = $('#1tdztStatus').combobox('getValue');
            var cgztStatus = $('#1cgztStatus').combobox('getValue');
            var shfaStatus = $('#1shfaStatus').combobox('getValue');
            var ztjgscStatus = $('#1ztjgscStatus').combobox('getValue');
            var zxscStatus = $('#1zxscStatus').combobox('getValue');
            var dqscStatus = $('#1dqscStatus').combobox('getValue');
            var zjganStatus = $('#1zjganStatus').combobox('getValue');
            var ztjgdhStatus = $('#1ztjgdhStatus').combobox('getValue');
            var zxazStatus = $('#1zxazStatus').combobox('getValue');
            var zxdhStatus = $('#1zxdhStatus').combobox('getValue');
            var dqazStatus = $('#1dqazStatus').combobox('getValue');
            var dqdhStatus = $('#1dqdhStatus').combobox('getValue');
            var sbtsStatus = $('#1sbtsStatus').combobox('getValue');
            var gsjStatus  = $('#1gsjStatus').combobox('getValue');
            var jgysStatus   = $('#1jgysStatus').combobox('getValue');


            //对选型方案及提单状态进行判断  已完成  82	xxfa	已完成  83	xxfa	会签中  84	xxfa	设计中
            if (xxfaStatus == 82) {
                //对提单状态进行判断，如果提单 已完成    提单状态 78	tzzt	已完成 79	tzzt	未提单
                if (tdztStatus == 78) {
                    zongti = "提单已完成";
                } else {
                    zongti = "提单" + $("#1tdztStatus").combobox("getText");
                    ;
                }
            } else {
                zongti = "选型方案" + $("#1xxfaStatus").combobox('getText');
            }

            //提单已完成，对采购过程状态进行判断 已完成
            if (tdztStatus == 78) {
                //对采购状态进行判断 已完成  5	cgzt	合同会签中 6	cgzt	询价中 7	cgzt	厂家已定 8	cgzt	审批中 9	cgzt	已完成 10	cgzt	未开始
                if (cgztStatus == 9) {
                    zongti = "采购完成";
                } else if (cgztStatus == 10) //未开始
                {
                    zongti = "提单完成，采购未开始";
                } else {
                    zongti = "采购" + $("#1xxfaStatus").combobox('getText');
                }

            }


            //对采购状态进行判断
            if (cgztStatus == 9) {
                //如果采购已完成，对深化方案状态进行判断  已完成
                if (shfaStatus == 2) {
                    zongti = "深化完成，采购完成";
                } else if (shfaStatus == 1)//未开始
                {
                    zongti = "深化未开始，采购完成";
                } else {
                    zongti = "深化方案" + $("#1shfaStatus").combobox('getText') + "，采购完成";
                }
            } else {
                //如果采购未完成，对深化方案状态进行判断
                if ((shfaStatus == 2) && (tdztStatus == 78)) {
                    zongti = "深化完成，采购" + $("#1cgztStatus").combobox('getText');     //"提单完成，深化完成，采购" & cgztStatus
                } else if ((shfaStatus == 1) && (tdztStatus == 78)) {
                    zongti = "深化未开始，采购" + $("#1cgztStatus").combobox('getText');   //"提单完成，深化未开始，采购" & cgztStatus
                } else if (tdztStatus == 78) {
                    zongti = "深化" + $("#1shfaStatus").combobox('getText') + "，采购" + $("#1cgztStatus").combobox('getText');    //"提单已完成，深化" & shfaStatus & "，采购" & cgztStatus
                }
            }


            //对采购状态进行判断，并判断生产状态
            if (cgztStatus != 9) {
                //对生产过程状态进行判断
                if (((ztjgscStatus != 69) && (ztjgscStatus != 85)) || ((zxscStatus != 69) && (zxscStatus != 85)) || ((dqscStatus != 69) && (dqscStatus != 85))) {
                    //若机械、造型、电气有一个在生产中
                    zongti = "采购" + $("#1cgztStatus").combobox('getText') + "，设备预投产";
                }
            }


            ////////*******************************************以下代码用于判断机构生产、发货及安装状态******************************


            //特别说明
            //“进行中”是指“进行中”、“进行中(1%)、“进行中(10%)、“进行中(30%)、“进行中(50%)、“进行中(70%)、“进行中(90%)”
            //“部分到货”是指“部分到货”、“部分到货(10%)、“部分到货(30%)、“部分到货(50%)、“部分到货(70%)、“部分到货(90%)”
            var MechSta;
            if (zjganStatus == 85) {
                //若机构安装状态为已完成
                MechSta = "机构安装完成";
            } else if ((zjganStatus != 69) && (zjganStatus != 85))//进行中
            {
                //若机构安装状态为进行中
                // if (ztjgdhStatus == "已完成")
                if (ztjgdhStatus == 19) {
                    //若机构发货状态为已完成
                    MechSta = "机构在安装";    //"机构全到货在安装"
                }
                //机构全到货在安装
                else if ((ztjgdhStatus == 14) || (ztjgdhStatus == 15) || (ztjgdhStatus == 16) || (ztjgdhStatus == 17) || (ztjgdhStatus == 18)) {
                    // if (ztjgscStatus == "已完成")
                    if (ztjgscStatus == 85) {
                        //若机构生产状态为已完成
                        MechSta = "机构待发货";    //"机构一部分到货在安装，其余待发货"
                    } else {
                        //若机构生产状态为进行中
                        MechSta = "机构在生产";    //"机构一部分到货在安装，其余在生产"
                    }
                } else if (ztjgdhStatus == 13)//"运输中"
                {
                    // if (ztjgscStatus == "已完成")
                    if (ztjgscStatus == 85) {
                        //若机构生产状态为已完成
                        MechSta = "机构运输中";    //"机构一部分到货在安装，其余运输中"
                    } else {
                        //若机构生产状态为进行中
                        MechSta = "机构在生产";    //"机构一部分到货在安装，其余在生产"
                    }
                } else {
                    // if (ztjgscStatus == "已完成")
                    if (ztjgscStatus == 85) {
                        //若机构生产状态为已完成
                        MechSta = "机构待发货";    //"机构一部分到货在安装，其余待发货"
                    } else {
                        //若机构生产状态为进行中
                        MechSta = "机构在生产";    //"机构一部分到货在安装，其余在生产"
                    }
                }
            } else if (zjganStatus == 69)//"未开始"
            {
                //若机构安装状态为未开始
                if (ztjgdhStatus == 19)//"已完成"
                {
                    //若机构发货状态为已完成
                    MechSta = "机构待安装";    // "机构全到货待安装"
                } else if ((ztjgdhStatus == 14) || (ztjgdhStatus == 15) || (ztjgdhStatus == 16) || (ztjgdhStatus == 17) || (ztjgdhStatus == 18))//"部分到货"
                {
                    // if (ztjgscStatus == "已完成")
                    if (ztjgscStatus == 85) {
                        //若机构生产状态为已完成
                        MechSta = "机构待发货";    //"机构一部分到货待安装，其余待发货"
                    } else {
                        //若机构生产状态为进行中
                        MechSta = "机构在生产";    //"机构一部分到货待安装，其余在生产"
                    }
                } else if (ztjgdhStatus == 13)//"运输中"
                {
                    // if (ztjgscStatus == "已完成")
                    if (ztjgscStatus == 85) {
                        //若机构生产状态为已完成
                        MechSta = "机构运输中";    //"机构运输中"
                    } else {
                        //若机构生产状态为进行中
                        MechSta = "机构在生产";    //"机构一部分在运输中，其余在生产"
                    }
                } else {
                    // if (ztjgscStatus == "已完成")
                    if (ztjgscStatus == 85) {
                        //若机构生产状态为已完成
                        MechSta = "机构待发货";   //"机构生产完成待发货"
                    } else if ((ztjgscStatus != 69) && (ztjgscStatus != 85)) {
                        //若机构生产状态为进行中
                        MechSta = "机构在生产";
                    } else {
                        //若机构生产状态为未开始
                        MechSta = "机构未生产";
                    }
                }
            }
            ////////*******************************************以上代码用于判断机构生产、发货及安装状态******************************

            var ModelSta;
            ////////*******************************************以下代码用于判断造型生产、发货及安装状态******************************
            if (zxazStatus == 85) {
                //若造型安装状态为已完成
                ModelSta = "造型安装完成";
            } else if ((zxazStatus != 69) && (zxazStatus != 85))  //"进行中"
            {
                //若造型安装状态为进行中
                if (zxdhStatus == 19)//"已完成"
                {
                    //若造型发货状态为已完成
                    ModelSta = "造型在安装";   //"造型全到货在安装"
                }
                // else if (zxdhStatus == "部分到货")
                else if ((zxdhStatus == 14) || (zxdhStatus == 15) || (zxdhStatus == 16) || (zxdhStatus == 17) || (zxdhStatus == 18)) {
                    // if (zxscStatus == "已完成")
                    if (zxscStatus == 85) {
                        //若造型生产状态为已完成
                        ModelSta = "造型待发货";   // "造型一部分到货在安装，其余待发货"
                    } else {
                        //若造型生产状态为进行中
                        ModelSta = "造型在生产";   //"造型一部分到货在安装，其余在生产"
                    }
                }
                // else if (zxdhStatus == "运输中")
                else if (zxdhStatus == 13) {
                    // if (zxscStatus == "已完成")
                    if (zxscStatus == 85) {
                        //若造型生产状态为已完成
                        ModelSta = "造型运输中";   // "造型一部分到货在安装，其余运输中"
                    } else {
                        //若造型生产状态为进行中
                        ModelSta = "造型在生产";   //"造型一部分到货在安装，一部分在运输，其余在生产"
                    }
                } else {
                    // if (zxscStatus == "已完成")
                    if (zxscStatus == 85) {
                        //若造型生产状态为已完成
                        ModelSta = "造型待发货";   // "造型一部分到货在安装，其余生产已完成"
                    } else {
                        //若造型生产状态为进行中
                        ModelSta = "造型在生产";   //"造型一部分到货在安装，其余在生产"
                    }
                }
            } else if (zxazStatus == 69)//"未开始"
            {
                //若造型安装状态为未开始
                // if (zxdhStatus == "已完成")
                if (zxdhStatus == 19) {
                    //若造型发货状态为已完成
                    ModelSta = "造型待安装";   // "造型全到货待安装"
                }
                // else if (zxdhStatus == "部分到货")
                else if ((zxdhStatus == 14) || (zxdhStatus == 15) || (zxdhStatus == 16) || (zxdhStatus == 17) || (zxdhStatus == 18)) {
                    // if (zxscStatus == "已完成")
                    if (zxscStatus == 85) {
                        //若造型生产状态为已完成
                        ModelSta = "造型待发货";   //"造型一部分到货待安装，其余待发货"
                    } else {
                        //若造型生产状态为进行中
                        ModelSta = "造型在生产";  // "造型一部分到货待安装，其余在生产"
                    }
                }
                // else if (zxdhStatus == "运输中")
                else if (zxdhStatus == 13) {
                    // if (zxscStatus == "已完成")
                    if (zxscStatus == 85) {
                        //若造型生产状态为已完成
                        ModelSta = "造型运输中";   // "造型运输中"
                    } else {
                        //若造型生产状态为进行中
                        ModelSta = "造型在生产";   //"造型一部分在运输，其余在生产"
                    }
                } else {
                    // if (zxscStatus == "已完成")
                    if (zxscStatus == 85) {
                        //若造型生产状态为已完成
                        ModelSta = "造型待发货";     //"造型生产完成待发货"
                    }
                    // else if (zxscStatus == "进行中")
                    else if ((zxscStatus != 69) && (zxscStatus != 85)) {
                        //若造型生产状态为进行中
                        ModelSta = "造型在生产";
                    } else {
                        //若造型生产状态为未开始
                        ModelSta = "造型未生产";
                    }
                }
            }

            ////////*******************************************以上代码用于判断造型生产、发货及安装状态******************************

            var ElecSta;
            ////////*******************************************以下代码用于判断电气生产、发货及安装状态******************************
            // if (dqazStatus == "已完成")
            if (dqazStatus == 85) {
                //若电气安装状态为已完成
                ElecSta = "电气安装完成";
            }
            // else if (dqazStatus == "进行中")
            else if ((dqazStatus != 69) && (dqazStatus != 85)) {
                //若电气安装状态为进行中
                // if (dqdhStatus == "已完成")
                if (dqdhStatus == 19) {
                    //若电气发货状态为已完成
                    ElecSta = "电气在安装";    //"电气全到货在安装"
                }
                // else if (dqdhStatus == "部分到货")
                else if ((zxdhStatus == 14) || (zxdhStatus == 15) || (zxdhStatus == 16) || (zxdhStatus == 17) || (zxdhStatus == 18)) {
                    // if (dqscStatus == "已完成")
                    if (dqscStatus == 85) {
                        //若电气生产状态为已完成
                        ElecSta = "电气待发货";    //"电气一部分到货在安装，其余待发货"
                    } else {
                        //若电气生产状态为进行中
                        ElecSta = "电气在生产";    //"电气一部分到货在安装，其余在生产"
                    }
                }
                // else if (dqdhStatus == "运输中")
                else if (dqdhStatus == 13) {
                    // if (dqscStatus == "已完成")
                    if (dqscStatus == 85) {
                        //若电气生产状态为已完成
                        ElecSta = "电气运输中";    //"电气一部分到货在安装，其余运输中"
                    }
                    // else if (dqscStatus == "进行中")
                    else if ((dqscStatus != 69) && (dqscStatus != 85)) {
                        //若电气生产状态为进行中
                        ElecSta = "电气在生产";    //"电气一部分运输中，其余在生产"
                    }
                } else {
                    // if (dqscStatus == "已完成")
                    if (dqscStatus == 85) {
                        //若电气生产状态为已完成
                        ElecSta = "电气待发货";      //"电气生产完成待发货"
                    } else {
                        //若电气生产状态为进行中
                        ElecSta = "电气在生产";
                    }
                }
            }
            // else if (dqazStatus == "未开始")
            else if (dqazStatus == 69) {
                //若电气安装状态为未开始
                // if (dqdhStatus == "已完成")
                if (dqdhStatus == 19) {
                    //若电气发货状态为已完成
                    ElecSta = "电气待安装";    //"电气全到货待安装"
                }
                // else if (dqdhStatus == "部分到货")
                else if ((zxdhStatus == 14) || (zxdhStatus == 15) || (zxdhStatus == 16) || (zxdhStatus == 17) || (zxdhStatus == 18)) {
                    // if (dqscStatus == "已完成")
                    if (dqscStatus == 85)
                    //若电气生产状态为已完成
                        ElecSta = "电气待发货";    //"电气一部分到货待安装，其余待发货"
                } else {
                    //若电气生产状态为进行中
                    ElecSta = "电气在生产";    //"电气一部分到货待安装，其余在生产"
                }
            }
            // else if (dqdhStatus == "运输中")
            else if (dqdhStatus == 13) {
                // if (dqscStatus == "已完成")
                if (dqscStatus == 85) {
                    //若电气生产状态为已完成
                    ElecSta = "电气运输中";    //"电气运输中"
                } else {
                    //若电气生产状态为进行中
                    ElecSta = "电气在生产";    //"电气一部分运输中，其余在生产"
                }
            } else {
                // if (dqscStatus == "已完成")
                if (dqscStatus == 85) {
                    //若电气生产状态为已完成
                    ElecSta = "电气待发货";      //"电气生产完成待发货"
                }
                // else if (dqscStatus == "进行中")
                else if ((dqscStatus != 69) && (dqscStatus != 85)) {
                    //若电气生产状态为进行中
                    ElecSta = "电气在生产";
                } else {
                    //若电气生产状态为未开始
                    ElecSta = "电气未生产";
                }
            }
            ////////*******************************************以上代码用于判断电气生产、发货及安装状态******************************


            //连接机械状态
            var zongtiSta = MechSta;

            //连接造型状态
            if ((zongtiSta.length != 0) && (ModelSta.length != 0)) {
                zongtiSta = zongtiSta + "；" + ModelSta;
            } else if (MechSta.length != 0) {
                zongtiSta = ModelSta;
            }

            //连接电气状态
            if ((zongtiSta.length != 0) && (ElecSta.length != 0)) {
                zongtiSta = zongtiSta + "；" + ElecSta;
            } else if (ElecSta.length != 0) {
                zongtiSta = ElecSta;
            }


            //设备安装已完成，对设备调试状态进行判断
            // if ((zjganStatus == "已完成") && (zxazStatus == "已完成") && (dqazStatus == "已完成"))
            if ((zjganStatus == 85) && (zxazStatus == 85) && (dqazStatus == 85)) {
                //对调试状态进行判断
                // if (sbtsStatus == "已完成")
                if (sbtsStatus == 85) {
                    //对国省检状态进行判断
                    if (gsjStatus == "已完成") {
                        zongti = "待竣工验收";
                    } else if (gsjStatus == "进行中") {
                        zongti = "国省检进行中";
                    } else if (gsjStatus == "未开始") {
                        zongti = "待国省检";
                    } else if (gsjStatus == "不需要") {
                        zongti = "待竣工验收";
                    }
                }
                // else if (sbtsStatus == "进行中")
                else if ((sbtsStatus != 69) && (sbtsStatus != 85)) {
                    zongti = "调试进行中";
                }
            }


            //设备国省检已完成或不需要，对竣工验收状态进行判断
            // if ((sbtsStatus == "已完成") && ((gsjStatus == "已完成") || (gsjStatus == "不需要")))
            if ((sbtsStatus == 85) && ((gsjStatus == 2) || (gsjStatus == 3))) {
                //对竣工验收状态进行判断
                // if (jgysStatus == "已完成")
                if (jgysStatus == 88) {
                    zongti = "竣工验收完成";
                }
                // else if (jgysStatus == "进行中")
                else if (jgysStatus == 87) {
                    zongti = "竣工验收进行中";
                }
            }


            ////////*******************************************以下代码用于判断电气生产、发货及安装状态精简格式化输出******************************

            if (zongtiSta == "机构未生产；造型未生产；电气未生产") {
                zongtiSta = "合同完成，未生产";
            } else if (zongtiSta == "机构未生产；造型在生产；电气未生产") {
                zongtiSta = "机构、电气未生产；造型在生产";
            } else if (zongtiSta == "机构在生产；造型未生产；电气未生产") {
                zongtiSta = "机构在生产；造型、电气未生产";
            } else if (zongtiSta == "机构在生产；造型在生产；电气在生产") {
                zongtiSta = "机构、造型、电气在生产";
            } else if (zongtiSta == "机构在生产；造型在生产；电气未生产") {
                zongtiSta = "机构、造型在生产；电气未生产";
            } else if (zongtiSta == "机构待发货；造型待发货；电气未生产") {
                zongtiSta = "机构与造型待发货；电气未生产";
            } else if (zongtiSta == "机构待发货；造型未生产；电气待发货") {
                zongtiSta = "机构、电气待发货；造型未生产";
            } else if (zongtiSta == "机构待发货；造型在生产；电气在生产") {
                zongtiSta = "机构待发货；造型、电气在生产";
            } else if (zongtiSta == "机构未生产；造型待发货；电气未生产") {
                zongtiSta = "机构、电气未生产；造型待发货";
            } else if (zongtiSta == "机构在生产；造型待发货；电气在生产") {
                zongtiSta = "机构、电气在生产；造型待发货";
            } else if (zongtiSta == "机构待发货；造型在生产；电气待发货") {
                zongtiSta = "机构、电气待发货；造型在生产";
            } else if (zongtiSta == "机构待发货；造型待发货；电气待发货") {
                zongtiSta = "机构、造型、电气待发货";
            } else if (zongtiSta == "机构待安装；造型待发货；电气待发货") {
                zongtiSta = "机构待安装；造型、电气待发货";
            } else if (zongtiSta == "机构待安装；造型待安装；电气待安装") {
                zongtiSta = "机构、造型、电气待安装";
            } else if (zongtiSta == "机构安装完成；造型未生产；电气未生产") {
                zongtiSta = "机构安装完成；造型、电气未生产";
            } else if (zongtiSta == "机构安装完成；造型在生产；电气在生产") {
                zongtiSta = "机构安装完成；造型、电气在生产";
            } else if (zongtiSta == "机构安装完成；造型待发货；电气待发货") {
                zongtiSta = "机构安装完成；造型、电气待发货";
            } else if (zongtiSta == "机构安装完成；造型待安装；电气待安装") {
                zongtiSta = "机构安装完成；造型、电气待安装";
            } else if (zongtiSta == "机构安装完成；造型在安装；电气在安装") {
                zongtiSta = "机构安装完成；造型、电气在安装";
            } else if (zongtiSta == "机构安装完成；造型在生产；电气安装完成") {
                zongtiSta = "机构、电气安装完成；造型在生产";
            } else if (zongtiSta == "机构安装完成；造型待发货；电气安装完成") {
                zongtiSta = "机构、电气安装完成；造型待发货";
            } else if (zongtiSta == "机构安装完成；造型在安装；电气安装完成") {
                zongtiSta = "机构、电气安装完成；造型在安装";
            } else if (zongtiSta == "机构安装完成；造型安装完成；电气未生产") {
                zongtiSta = "机构、造型安装完成；电气未生产";
            } else if (zongtiSta == "机构安装完成；造型安装完成；电气待发货") {
                zongtiSta = "机构、造型安装完成；电气待发货";
            } else if (zongtiSta == "机构安装完成；造型安装完成；电气待安装") {
                zongtiSta = "机构、造型安装完成；电气待安装";
            } else if (zongtiSta == "机构安装完成；造型安装完成；电气在安装") {
                zongtiSta = "机构、造型安装完成；电气在安装";
            } else if (zongtiSta == "机构安装完成；造型安装完成；电气安装完成") {
                zongtiSta = "设备待调试";   //代码调试专用
            }
            $("#sbzt").text(zongtiSta);
        }

        // 定义一个键值对对象
        function ObjData(key, value) {
            this.Key = key;
            this.Value = value;
        }

        function saveFuc() {
            var swylsbXq = $('#swylsbXq').serializeJSON();
            $.ajax({
                url: "${pageContext.request.contextPath}/swylsbXq/updateSwylsbXq",
                data: swylsbXq,
                dataType: 'json',
                type: 'post',
                success: function (rtn) {
                    if (!rtn.success) {
                        $.messager.alert("系统提示", "提交失败！");
                    } else {
                        getJdData();
                        // 把数组转换成json字符串
                        var postDataJd = JSON.stringify(arrayJd);
                        $.ajax({
                            url: "${pageContext.request.contextPath}/swylsbJd/updateSwylsbJd",
                            data: {postDataJd: postDataJd},
                            dataType: 'json',
                            type: 'post',
                            success: function (rtn) {
                                if (rtn.success) {
                                    var postDataJh = JSON.stringify(arrayJh);
                                    $.ajax({
                                        url: "${pageContext.request.contextPath}/swylsbJh/updateSwylsbJh?ID="+swID,
                                        data: {postDataJh: postDataJh},
                                        dataType: 'json',
                                        type: 'post',
                                        success: function (rtn) {
                                            if (rtn.success) {
                                                $.messager.confirm("系统提示", "提交成功！");
                                                window.parent.refreshTabData(str, window.top.reload_ylsbDetail);

                                            } else {
                                                message = "新增失败";
                                            }

                                        }
                                    });
                                } else {
                                    message = "新增失败";
                                }

                            }
                        });
                    }
                }
            });

        }

         function getJdData() {
            var list = document.getElementById("swylsbJh");//查询form下的所有input标签
             arrayJd = [];
             arrayJh = [];
             for (var i = 0; i < list.length && list[i]; i++) //对表单中所有的input进行遍历
            {
                var ss = list[i].id;
                var ss1 = list[i].value;
                if (list[i].id.substring(0, 1) == '1') {
                    //判断不是空的 input,进行表单提交 
                    if (list[i].value != "" && list[i].value != null) {
                        var key = list[i].id.substring(1);
                        var value = list[i].value;
                        var s = new ObjData(key, value); //创建键值对象
                        arrayJd.push(s); //把对象放入对象数组中
                    }
                }
                if (list[i].id.substring(0, 1) == '2') {
                    //判断不是空的 input,进行表单提交 
                    if (list[i].value != "" && list[i].value != null) {
                        var key1 = list[i].id.substring(1);
                        var value1 = list[i].value;
                        var s1 = new ObjData(key1, value1); //创建键值对象
                        arrayJh.push(s1); //把对象放入对象数组中
                    }
                }
            }

        }
    </script>
</head>
<body>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="2">
    <tr>
        <td height="490" align="center" valign="top">
            <table width="61.8%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="5"></td>
                </tr>
            </table>
            <table width="61.8%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table class="TailTable">
                            <tr align="center">
                                <td class="TailLabelc" colspan="7">设备基本信息</td>
                            </tr>
                            <tr>
                                <td class="TailLabelc">乐园简称:</td>
                                <td width="180" align="center" valign="middle"><label id="parkName"></label></td>
                                <td class="TailLabelc">项目名称:</td>
                                <td align="center" valign="middle"><label id="projectName"></label></td>
                                <td class="TailLabelc">设备序号:</td>
                                <td width="120" align="center" valign="middle"><label id="sbID"></label></td>
                            </tr>
                            <tr>
                                <td class="TailLabelc">设备名称:</td>
                                <td width="180" align="center" valign="middle"><label id="sbName"></label></td>
                                <td class="TailLabelc">设备型号:</td>
                                <td width="180" align="center" valign="middle"><label id="sbClass"></label></td>
                                <td class="TailLabelc">设备类型:</td>
                                <td width="180" align="center" valign="middle"><label id="sbStatus"></label></td>
                            </tr>
                            <tr>
                                <td class="TailLabelc">包装类型:</td>
                                <td width="180" align="center" valign="middle"><label id="bzlx"></label></td>
                                <td class="TailLabelc">沿用情况:</td>
                                <td width="180" align="center" valign="middle"><label id="yyqk"></label></td>
                                <td class="TailLabelc">检验级别:</td>
                                <td width="180" align="center" valign="middle"><label id="jyjbStatus"></label></td>
                            </tr>
                            <tr>
                                <td class="TailLabelc">供应厂家:</td>
                                <td width="180" align="center" valign="middle"><label id="gydw"></label></td>
                                <td class="TailLabelc">安装厂家:</td>
                                <td width="180" align="center" valign="middle"><label id="azdw"></label></td>
                                <td class="TailLabelc">型式试验:</td>
                                <td width="180" align="center" valign="middle"><label id="xssyStatus"></label></td>
                            </tr>
                            <tr>
                                <td class="TailLabelc">设备数量:</td>
                                <td width="180" align="center" valign="middle"><label id="sbsl"></label></td>
                                <td class="TailLabelc">负责人:</td>
                                <td width="180" align="center" valign="middle"><label id="fzrUser"></label></td>
                                <td class="TailLabelc">填写时间:</td>
                                <td width="180" align="center" valign="middle"><label id="sbDate"></label></td>
                            </tr>
                            <tr>
                                <td class="TailLabelc">总工办计划基础开始日期:</td>
                                <td align="center" valign="middle"><label id="startDate"></label></td>
                                <td class="TailLabelc">总工办计划基础完成日期:</td>
                                <td align="center" valign="middle"><label id="completeDate"></label></td>
                                <td class="TailLabelc">总工办计划设备进场日期:</td>
                                <td align="center" valign="middle"><label id="approachDate"></label></td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <form id="swylsbXq">
                    <tr>
                        <td>
                            <table class="TailTable">
                                <tr>
                                    <td class="TailLabelc" colspan="7">设备总体情况</td>
                                    <input id="swylsbXqID" name="ID" type="hidden"/>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">设备状态:&nbsp;</td>
                                    <td colspan="6" align="left"><label id="sbzt"></label></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">状态备注:&nbsp;</td>
                                    <td colspan="6" align="left"><input type="text" style="width: 90%;"
                                                                        class="TailInput" id="ztbz" name="ztbz"
                                                                        data-options="required:true">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">问题反馈:&nbsp;</td>
                                    <td colspan="6" align="left"><input type="text" style="width: 90%;"
                                                                        class="TailInput" id="wtfk" name="wtfk"
                                                                        data-options="required:true">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">预警说明:</td>
                                    <td width="500" colspan="6" align="left"><span style="color: #F00"></span></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </form>

                <form id="swylsbJh" name="swylsbJh" method="post">
                    <tr>
                        <td>
                            <table class="TailTable">
                                <tr>
                                    <td class="TailLabelc" colspan="7">设备进度详细情况</td>
                                    <input  id="1ID" name="1ID" type="hidden"/>
                                    <input  id="2ID" name="2ID" type="hidden"/>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">图审状态</td>
                                    <td class="TailLabelc">安装基础实际开始日期</td>
                                    <td class="TailLabelc">安装基础实际完成日期</td>
                                    <td class="TailLabelc">安装基础状态</td>
                                </tr>
                                <tr>
                                    <td align="center"><input id="1tsStatus" name="1tsStatus" type="text"
                                                              class="easyui-combobox TailInput"
                                                              editable="false"
                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=azjczt',
                onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center"><input type="text" class="easyui-datebox TailInput"
                                                              id="1azjcsjStartDate"
                                                              name="1azjcsjStartDate"></td>
                                    <td align="center"><input type="text" class="easyui-datebox TailInput"
                                                              id="1azjcsjEndDate"
                                                              name="1azjcsjEndDate"></td>
                                    <td align="center"><input type="text" id="1azStatus"
                                                              name="1azStatus"
                                                              class="easyui-combobox TailInput1"
                                                              editable="false"
                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=azjczt    ',
                 onSelect:function(record){
                    sbztFunc()
                    }"></td>
                                </tr>
                                <tr>
                                    <td height="5" colspan="4">&nbsp;</td>
                                </tr>
                            </table>
                            <table class="TailTable">
                                <tr>
                                    <td class="TailLabelc">
                                        <div style="width: 120px">环节/状态</div>
                                    </td>
                                    <td class="TailLabelc">状态</td>
                                    <td class="TailLabelc">计划开始日期</td>
                                    <td class="TailLabelc">实际开始日期</td>
                                    <td class="TailLabelc">计划完成日期</td>
                                    <td class="TailLabelc">实际完成日期</td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">选型方案:</td>
                                    <td align="center" valign="middle"><input type="text" id="1xxfaStatus"
                                                                              name="1xxfaStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=xxfa',
                 onSelect:function(record){
                    sbztFunc()
                    }
"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2xxfaStatus-jhStartDate"
                                                                              name="2xxfaStatus"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2xxfaStatus-jhEndDate"
                                                                              name="2xxfaStatus"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2xxfaStatus-sjStartDate"
                                                                              name="2xxfaStatus-sjStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2xxfaStatus-sjEndDate"
                                                                              name="2xxfaStatus-sjEndDate"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">提单状态:</td>
                                    <td align="center" valign="middle"><input type="text" id="1tdztStatus"
                                                                              name="1tdztStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=tzzt',
                 onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2tdztStatus-jhStartDate"
                                                                              name="2tdztStatus-jhStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2tdztStatus-jhEndDate"
                                                                              name="2tdztStatus-jhEndDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2tdztStatus-sjStartDate"
                                                                              name="2tdztStatus-sjStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2tdztStatus-sjEndDate"
                                                                              name="2tdztStatus-sjEndDate"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">采购状态:</td>
                                    <td align="center" valign="middle"><input type="text" id="1cgztStatus"
                                                                              name="1cgztStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=cgzt',
              onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2cgztStatus-jhStartDate"
                                                                              name="2cgztStatus-jhStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2cgztStatus-jhEndDate"
                                                                              name="2cgztStatus-jhEndDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2cgztStatus-sjStartDate"
                                                                              name="2cgztStatus-sjStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2cgztStatus-sjEndDate"
                                                                              name="2cgztStatus-sjEndDate"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">深化方案:</td>
                                    <td align="center" valign="middle"><input type="text" id="1shfaStatus"
                                                                              name="1shfaStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=azjczt',
                onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2shfaStatus-jhStartDate"
                                                                              name="2shfaStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2shfaStatus-jhEndDate"
                                                                              name="2shfaStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2shfaStatus-sjStartDate"
                                                                              name="2shfaStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2shfaStatus-sjEndDate"
                                                                              name="2shfaStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">主结构生产:</td>
                                    <td align="center" valign="middle"><input type="text" id="1ztjgscStatus"
                                                                              name="1ztjgscStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                  onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgscStatus-jhStartDate"
                                                                              name="2ztjgscStatus-jhStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgscStatus-jhEndDate"
                                                                              name="2ztjgscStatus-jhEndDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgscStatus-sjStartDate"
                                                                              name="2ztjgscStatus-sjStartDate"></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgscStatus-sjEndDate"
                                                                              name="2ztjgscStatus-sjEndDate"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">造型生产:</td>
                                    <td align="center" valign="middle"><input type="text" id="1zxscStatus"
                                                                              name="1zxscStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2zxscStatus-jhStartDate"
                                                                              name="2zxscStatus-jhStartDate"
                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2zxscStatus-jhEndDate"
                                                                              name="2zxscStatus-jhEndDate"
                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2zxscStatus-sjStartDate"
                                                                              name="2zxscStatus-sjStartDate"
                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2zxscStatus-sjEndDate"
                                                                              name="2zxscStatus-sjEndDate"
                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">电气生产:</td>
                                    <td align="center" valign="middle"><input type="text" id="1dqscStatus"
                                                                              name="1dqscStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                 onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2dqscStatus-jhStartDate"
                                                                              name="2dqscStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2dqscStatus-jhEndDate"
                                                                              name="2dqscStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2dqscStatus-sjStartDate"
                                                                              name="2dqscStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2dqscStatus-sjEndDate"
                                                                              name="2dqscStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">主结构到货:</td>
                                    <td align="center" valign="middle"><input type="text" id="1ztjgdhStatus"
                                                                              name="1ztjgdhStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=dhjd',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgdhStatus-jhStartDate"
                                                                              name="2ztjgdhStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgdhStatus-jhEndDate"
                                                                              name="2ztjgdhStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgdhStatus-sjStartDate"
                                                                              name="2ztjgdhStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2ztjgdhStatus-sjEndDate"
                                                                              name="2ztjgdhStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">造型到货:</td>
                                    <td align="center" valign="middle"><input type="text" id="1zxdhStatus"
                                                                              name="1zxdhStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=dhjd',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxdhStatus-jhStartDate"
                                                                              name="2zxdhStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxdhStatus-jhEndDate"
                                                                              name="2zxdhStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxdhStatus-sjStartDate"
                                                                              name="2zxdhStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxdhStatus-sjEndDate"
                                                                              name="2zxdhStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">电气到货:</td>
                                    <td align="center" valign="middle"><input type="text" id="1dqdhStatus"
                                                                              name="1dqdhStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=dhjd',
                  onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqdhStatus-jhStartDate"
                                                                              name="2dqdhStatus-jhStartDate"
                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqdhStatus-jhEndDate"
                                                                              name="2dqdhStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqdhStatus-sjStartDate"
                                                                              name="2dqdhStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqdhStatus-sjEndDate"
                                                                              name="2dqdhStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">主结构安装:</td>
                                    <td align="center" valign="middle"><input type="text" id="1zjganStatus"
                                                                              name="1zjganStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zjganStatus-jhStartDate"
                                                                              name="2zjganStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zjganStatus-jhEndDate"
                                                                              name="2zjganStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zjganStatus-sjStartDate"
                                                                              name="2zjganStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zjganStatus-sjEndDate"
                                                                              name="2zjganStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">造型安装:</td>
                                    <td align="center" valign="middle"><input type="text" id="1zxazStatus"
                                                                              name="1zxazStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                    onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxazStatus-jhStartDate"
                                                                              name="2zxazStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxazStatus-jhEndDate"
                                                                              name="2zxazStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxazStatus-sjStartDate"
                                                                              name="2zxazStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2zxazStatus-sjEndDate"
                                                                              name="2zxazStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">电气安装:</td>
                                    <td align="center" valign="middle"><input type="text" id="1dqazStatus"
                                                                              name="1dqazStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                     onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqazStatus-jhStartDate"
                                                                              name="2dqazStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqazStatus-jhEndDate"
                                                                              name="2dqazStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqazStatus-sjStartDate"
                                                                              name="2dqazStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2dqazStatus-sjEndDate"
                                                                              name="2dqazStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">设备调试:</td>
                                    <td align="center" valign="middle"><input type="text" id="1sbtsStatus"
                                                                              name="1sbtsStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=scjd',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2sbtsStatus-jhStartDate"
                                                                              name="2sbtsStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2sbtsStatus-jhEndDate"
                                                                              name="2sbtsStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2sbtsStatus-sjStartDate"
                                                                              name="2sbtsStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2sbtsStatus-sjEndDate"
                                                                              name="2sbtsStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">型式试验:</td>
                                    <td align="center" valign="middle"><input type="text" id="1xssy1Status"
                                                                              name="1xssy1Status"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=azjczt',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2xssy1Status-jhStartDate"
                                                                              name="2xssy1Status-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2xssy1Status-jhEndDate"
                                                                              name="2xssy1Status-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2xssy1Status-sjStartDate"
                                                                              name="2xssy1Status-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2xssy1Status-sjEndDate"
                                                                              name="2xssy1Status-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">国/省检:</td>
                                    <td align="center" valign="middle"><input type="text" id="1gsjStatus"
                                                                              name="1gsjStatus"
                                                                              editable="false"
                                                                              class="easyui-combobox TailInput1"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=azjczt',
                     onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2gsjStatus-jhStartDate"
                                                                              name="2gsjStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2gsjStatus-jhEndDate"
                                                                              name="2gsjStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2gsjStatus-sjStartDate"
                                                                              name="2gsjStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2gsjStatus-sjEndDate"
                                                                              name="2gsjStatus-sjEndDate"

                                    ></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">竣工验收:</td>
                                    <td align="center" valign="middle"><input type="text" id="1jgysStatus"
                                                                              name="1jgysStatus"
                                                                              class="easyui-combobox TailInput1"
                                                                              editable="false"
                                                                              data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findMyTypeByTypeCode?typeCode=jindu',
                   onSelect:function(record){
                    sbztFunc()
                    }"
                                    /></td>
                                    </td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2jgysStatus-jhStartDate"
                                                                              name="2jgysStatus-jhStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2jgysStatus-jhEndDate"
                                                                              name="2jgysStatus-jhEndDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"

                                                                              id="2jgysStatus-sjStartDate"
                                                                              name="2jgysStatus-sjStartDate"

                                    ></td>
                                    <td align="center" valign="middle"><input type="text"
                                                                              class="easyui-datebox TailInput1"
                                                                              id="2jgysStatus-sjEndDate"
                                                                              name="2jgysStatus-sjEndDate"

                                    ></td>
                                </tr>
                            </table>

                        </td>
                    </tr>
                </form>
                <tr>
                    <td>
                        <table class="TailTable">
                            <tr>
                                <td valign="middle" align="center">
                                    <a href="#" onclick="saveFuc()" class="easyui-linkbutton" iconCls="icon-save">保存</a>
                                    &emsp;&emsp;&emsp;&emsp;
                                    <a href="#" class="easyui-linkbutton" iconCls="icon-redo">重置</a>
                                </td>
                            </tr>

                        </table>
                    </td>
                </tr>

            </table>

</body>
</html>