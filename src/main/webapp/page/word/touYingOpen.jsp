<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>公园管理</title>
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
    <script type="text/javascript">
        var touYing;
        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/touYing/getTouYingById",
                type: "post",
                data: {"id": '${param.id}'},//返回给客户端的json数据
                async: false,
                dataType: 'json',
                success: function (data) {
                    touYing = data.touYing;
                    $("#allName").text(touYing.user.allName);
                    $("#touYing_date").text(touYing.touYing_date);
                    for(let key  in touYing){
                        if(key.substr(key.length-4, key.length) == "flag"){
                            if (touYing[key]==1){
                                $("input[name="+ key +"]").eq(0).attr("checked","checked");//合格
                            }else {
                                $("input[name="+ key +"]").eq(1).attr("checked","checked");//不合格
                            }
                        }else if(key=="touYing_date"){
                            var times = new Date(touYing[key].time);
                            $("#"+key).text(times.format("Y-m-d"));
                        }else if(key=="user") {
                            $("#"+key).text(touYing[key].allName)
                        }else{
                            $("#"+key).text(touYing[key]);
                        }

                    }
                }
            });
        });
        function closeDialog() {
            window.parent.refreshTabData("查看投影设备安装条件检查表", window.top.reload_touYing);
        }


    </script>
</head>
<body style="tab-interval:36pt;"><!--StartFragment-->
<form id="yinxiang">
    <div class="Section0" style="layout-grid:18.0000pt;">
        <div align=center>
            <table class=MsoNormalTable border=1 cellspacing=0
                   style="border-collapse:collapse;width:687.6000pt;mso-table-layout-alt:fixed;border:none;mso-border-left-alt:0.5000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);mso-border-insideh:0.5000pt solid rgb(0,0,0);mso-border-insidev:0.5000pt solid rgb(0,0,0);mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;">
                <tr style="height:19.8500pt;page-break-inside:avoid;">
                    <td width=95 valign=center colspan=2 rowspan=3
                        style="width:71.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid rgb(0,0,0);mso-border-left-alt:0.5000pt solid rgb(0,0,0);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal>
                        <span
                                style="font-family:'Times New Roman';mso-fareast-font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">
                            <img width="76" height="76" style="margin:0 auto;"
                                 src="${pageContext.request.contextPath}/static/images/fangte2.png">
                        </span>
                            <span
                                    style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p>
                        </span>
                        </p>
                    </td>
                    <td width=413 valign=center colspan=5 rowspan=3
                        style="width:309.9000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><b><span
                                style="mso-spacerun:'yes';font-family:楷体;mso-bidi-font-family:宋体;font-weight:bold;font-size:18.0000pt;">华强方特（深圳）智能技术有限公司</span></b><b><span
                                style="font-family:楷体;mso-bidi-font-family:宋体;font-weight:bold;font-size:18.0000pt;"><o:p></o:p></span></b>
                        </p></td>
                    <td width=141 valign=center
                        style="width:106.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">质量记录</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                </tr>
                <tr style="height:15.5500pt;page-break-inside:avoid;">
                    <td width=141 valign=center rowspan=3
                        style="width:106.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:none;border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><b><span
                                style="mso-spacerun:'yes';font-family:楷体;font-weight:bold;font-size:15.0000pt;mso-font-kerning:1.0000pt;">投影设备安装</span></b><b><span
                                style="mso-spacerun:'yes';font-family:楷体;font-weight:bold;font-size:15.0000pt;mso-font-kerning:1.0000pt;">条件检查表</span></b><b><span
                                style="font-family:楷体;mso-bidi-font-family:宋体;font-weight:bold;font-size:15.0000pt;"><o:p></o:p></span></b>
                        </p></td>
                </tr>
                <tr style="height:26.8500pt;page-break-inside:avoid;"></tr>
                <tr style="height:19.8500pt;page-break-inside:avoid;">
                    <td width=211 valign=center colspan=4
                        style="width:158.9000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid rgb(0,0,0);mso-border-left-alt:0.5000pt solid rgb(0,0,0);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">编码：Q</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">R-03-04-05</span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=296 valign=center colspan=3
                        style="width:222.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">流水号：</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">
<%--流水号：id--%>
                            <span id="id"></span>

                        </span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                </tr>
                <tr style="height:14.2000pt;page-break-inside:avoid;">
                    <td width=650 valign=center colspan=8
                        style="width:487.6000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:none;mso-border-right-alt:none;border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p>&nbsp;</o:p></span></p></td>
                </tr>
                <tr style="height:21.7500pt;page-break-inside:avoid;">
                    <td width=110 valign=center colspan=3
                        style="width:83.0500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid rgb(0,0,0);mso-border-left-alt:0.5000pt solid rgb(0,0,0);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">工程</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">名称</span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=312 valign=center colspan=3
                        style="width:234.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">
<%----------------------------------------------工程名称--%>
                        <span id="project_name"></span>
                    </span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=84 valign=center
                        style="width:63.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">检查</span><span
                                style="font-family:宋体;font-size:12.0000pt;">人</span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=141 valign=center
                        style="width:106.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal style="text-align: center;margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">

                     </span><span><o:p>
<%----------------------------------------------检查人 allName --%>
                            <span id="allName"></span>
                        </o:p></span></p></td>
                </tr>
                <tr style="height:19.8500pt;page-break-inside:avoid;">
                    <td width=110 valign=center colspan=3
                        style="width:83.0500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid rgb(0,0,0);mso-border-left-alt:0.5000pt solid rgb(0,0,0);border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">项目</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">名称</span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=312 valign=center colspan=3
                        style="width:234.6500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">
<%----------------------------------------------项目名称--%>
                              <span id="entry_name"></span>
                        </span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=84 valign=center
                        style="width:63.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">日 期</span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                    <td width=141 valign=center
                        style="width:106.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal style="text-align: center;margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:12.0000pt;">
 <%---------------------------------------------- 时间 --%>
                            <span id="touYing_date"></span>
                         </span><span
                                style="font-family:宋体;font-size:12.0000pt;"><o:p></o:p></span></p></td>
                </tr>
                <tr style="height:19.8500pt;page-break-inside:avoid;">
                    <td width=53 valign=center
                        style="width:58.3000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid rgb(0,0,0);mso-border-left-alt:0.5000pt solid rgb(0,0,0);border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">序号</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=57 valign=center colspan=2
                        style="width:32.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">检查</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">项目</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=237 valign=center colspan=2
                        style="width:100.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">检查内容</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">及要求</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=75 valign=center
                        style="width:82.8500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:none;mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">检验</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">方法</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=84 valign=center
                        style="width:93.5500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid rgb(0,0,0);mso-border-right-alt:0.5000pt solid rgb(0,0,0);border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">检验</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">结果</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=141 valign=center
                        style="width:146.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid rgb(0,0,0);mso-border-top-alt:0.5000pt solid rgb(0,0,0);border-bottom:1.0000pt solid rgb(0,0,0);mso-border-bottom-alt:0.5000pt solid rgb(0,0,0);">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">情况</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">说明</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                </tr>
            </table>
        </div>
        <p class=MsoNormal><span
                style="mso-spacerun:'yes';font-family:Tahoma;font-size:11.0000pt;"></span>
        </p></div>
    <div class="Section0" style="layout-grid:18.0000pt;">
        <div align=center>
            <table class=MsoNormalTable border=1 cellspacing=0
                   style="border-collapse:collapse;width:689.1000pt;mso-table-layout-alt:fixed;border:none;mso-border-left-alt:0.5000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;mso-border-insideh:0.5000pt solid windowtext;mso-border-insidev:0.5000pt solid windowtext;mso-padding-alt:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;">
                <tr style="height:49.2500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">1</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center rowspan=3
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">安</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">装</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">基</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">础</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">投影机</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">吊柱</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">、</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">吊架</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">及</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">安装马道</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">施工</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">完成，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">材料</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">、</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">制作及</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">安装定位符合施工图纸要求</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">，安装</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">固定牢靠、安全</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">核对</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">图纸</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;">
                        <span style="font-family:宋体;font-size:10.5000pt;">
<%----------------------------------------------合格1--%>
                         <input class="easyui-validatebox" type="radio" name="n1_flag" value="1" disabled="disabled"
                                validType="radio['frm','n1_flag']"/>
                        </span>
                            <span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span>
<%----------------------------------------------不合格1--%>
                        <input class="easyui-validatebox" type="radio" name="n1_flag" value="0" disabled="disabled">
                    </span>
                            <span style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">不合格</span><span
                                    style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:1.0000pt solid windowtext;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;">
                            <span style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                                <%---------------------------------------------- n1_text --%>
                                <span id="n1_text"></span>
                            </o:p></span>
                        </p>
                    </td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">2</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">所有金属</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">焊接</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">由专业焊工（具有</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">与</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">焊接</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">项目相符的焊工合格证书，持证上</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">岗</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">）</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">完成，焊接工艺符合质量要求，</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">无</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">漏焊</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">、气孔</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">和</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">夹渣等现象</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
<%---------------------------------------------- 合格2 --%>
                         <input type="radio" disabled="disabled" name="n2_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n2_flag']">合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
<%---------------------------------------------- 不合格2 --%>
                         <input type="radio" disabled="disabled" class="easyui-validatebox" name="n2_flag" value="0">不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">
                            <o:p>
                                <%---------------------------------------------- n2_text --%>
                                <span id="n2_text"></span>
                            </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:23.9000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">3</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">投影机吊柱、</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">吊架</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">及</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">安装马道表面均做吸光</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">和</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">防腐防锈处理</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="font-family:宋体;font-size:10.5000pt;">
                            <%---------------------------------------------- 合格3 --%>
                         <input type="radio" disabled="disabled" name="n3_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n3_flag']">
                        </span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="font-family:宋体;font-size:10.5000pt;">
                            <%---------------------------------------------- 不合格3 --%>
                         <input type="radio" disabled="disabled" name="n3_flag" value="0" class="easyui-validatebox"></span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n3_text --%>
                            <span id="n3_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">4</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center rowspan=5
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:none;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">幕</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">架</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银幕架的</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">材料、制作及安装</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">定位</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">符合施工图纸要求</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">，安装</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">固定牢靠、安全</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">核对</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">图纸</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
              <%---------------------------------------------- 合格4 --%>
                         <input type="radio" disabled="disabled" name="n4_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n4_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
              <%---------------------------------------------- 不合格4 --%>
                         <input type="radio" disabled="disabled" name="n4_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n4_text --%>
                            <span id="n4_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">5</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银幕架</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">焊接</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">由</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">专业焊工完成，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">焊接</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">工艺符合质量要求，无</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">漏焊</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">、气孔</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">和</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">夹渣等现象</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
    <%---------------------------------------------- 合格5 --%>
                         <input type="radio" disabled="disabled" name="n5_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n5_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                            <%---------------------------------------------- 不合格5 --%>
                         <input type="radio" disabled="disabled" name="n5_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n5_text --%>
                            <span id="n5_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:30.5000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">6</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">架表面</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">均</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">做吸光和</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">防腐</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">防锈处理</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                       <%---------------------------------------------- 合格6 --%>
                         <input type="radio" disabled="disabled" name="n6_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n6_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                <%---------------------------------------------- 不合格6 --%>
                         <input type="radio" disabled="disabled" name="n6_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n6_text --%>
                            <span id="n6_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">7</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">平幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">架内框</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">尺寸须</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">大于银幕相应尺寸，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">金属</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银幕的平幕架的上、下、左</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">、</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">右</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">四边</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">的尺寸均比银幕的相应尺寸大</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">10cm，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">其他银幕</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">的</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">平幕架的上、下、左</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">、</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">右</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">四边</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">的尺寸均比银幕的相应尺寸大</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">5cm。银幕架</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">安装</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">完成</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">后</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">，超出银幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">的部分需用装饰或造型遮挡来遮住银幕画面以外多余部分与放映光线的虚边</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">核对</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">图纸</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                             <%---------------------------------------------- 合格7 --%>
                         <input type="radio" disabled="disabled" name="n7_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n7_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
<%---------------------------------------------- 不合格7 --%>
                         <input type="radio" disabled="disabled" name="n7_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n7_text --%>
                            <span id="n7_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">8</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">框范围内不允许有装饰遮挡，否则影响银幕的安装</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                     <%---------------------------------------------- 合格8 --%>
                         <input type="radio" disabled="disabled" name="n8_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n8_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                   <%---------------------------------------------- 不合格8 --%>
                         <input type="radio" disabled="disabled" name="n8_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n8_text --%>
                            <span id="n8_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:28.2000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">9</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center rowspan=4
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:none;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">幕</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银幕自然</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">下垂展平，四周张紧</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">适度</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                          <%---------------------------------------------- 合格9 --%>
                         <input type="radio" disabled="disabled" name="n9_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n9_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                  <%---------------------------------------------- 不合格9 --%>
                         <input type="radio" disabled="disabled" name="n9_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n9_text --%>
                            <span id="n9_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">10</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">漫反射</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">幕和背投幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">两面</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">分别为粗糙面和光滑面，确保银幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">光滑面</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">朝向</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">观影</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">区</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p>
                            <%---------------------------------------------- 合格10 --%>
                            <input type="radio" disabled="disabled" name="n10_flag" value="1" class="easyui-validatebox"
                                   validType="radio['frm','n10_flag']">
                            合格 </o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                  <%---------------------------------------------- 不合格10 --%>
                         <input type="radio" disabled="disabled" name="n10_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    </td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n10_text --%>
                            <span id="n10_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:25.7000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">11</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">表面</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">无污染、</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">损坏，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">平整无明显折痕</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                            <%---------------------------------------------- 合格11 --%>
                         <input type="radio" disabled="disabled" name="n11_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n11_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                    <%---------------------------------------------- 不合格10 --%>
                         <input type="radio" disabled="disabled" name="n11_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n11_text --%>
                            <span id="n11_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:28.0000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">12</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">360环形银幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">接缝</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">处</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">弧度与其他位置弧度一致</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">调节螺杆调整到位</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                           <%---------------------------------------------- 合格12 --%>
                         <input type="radio" disabled="disabled" name="n12_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n12_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                     <%---------------------------------------------- 不合格12 --%>
                         <input type="radio" disabled="disabled" name="n12_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n12_text --%>
                            <span id="n12_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:24.8500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">13</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center rowspan=2
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:none;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">银</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">幕</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">镀铝膜</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">安装角度正确，张紧适度</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                 <%---------------------------------------------- 合格13 --%>
                         <input type="radio" disabled="disabled" name="n13_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n13_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                <%---------------------------------------------- 不合格13 --%>
                         <input type="radio" disabled="disabled" name="n13_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n13_text --%>
                            <span id="n13_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:21.1000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">14</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">球幕</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">铝板压接方式正确</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                              <%---------------------------------------------- 合格14 --%>
                         <input type="radio" disabled="disabled" name="n14_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n14_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                  <%---------------------------------------------- 不合格14 --%>
                         <input type="radio" disabled="disabled" name="n14_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n14_text --%>
                            <span id="n14_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">15</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center rowspan=9
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:none;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">环</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">境</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">检</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">查</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">投影机安装位置有足够空间满足安装。设备工程师应根据施工图纸对现场设备安装位置情况进行核对，确保足够的设备安装空间</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">核对</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">图纸</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                <%---------------------------------------------- 合格15 --%>
                         <input type="radio" disabled="disabled" name="n15_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n15_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                               <%---------------------------------------------- 不合格15 --%>
                         <input type="radio" disabled="disabled" name="n15_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n15_text --%>
                            <span id="n15_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">16</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">线槽线管敷设完成，布放点位与施工图纸相符，满足设备安装位置需求。注意面客区域的线管，已经做好隐藏安装。无造型隐藏布管区域，需涂抹哑光漆，或者开槽暗藏并将槽面还原</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">核对</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">图纸</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                              <%---------------------------------------------- 合格16 --%>
                         <input type="radio" disabled="disabled" name="n16_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n16_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                               <%---------------------------------------------- 不合格16 --%>
                         <input type="radio" disabled="disabled" name="n16_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n16_text --%>
                            <span id="n16_text"></span>

                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">17</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText
                           style="margin-right:-5.4000pt;mso-para-margin-right:-0.4900gd;text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">强电线缆和</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">弱电控制线缆</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">敷设完成，布放点位与施工图纸相符，满足设备安装位置需求。注意线缆两端应预留适度余量</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">核对</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">图纸</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                   <%---------------------------------------------- 合格17 --%>
                         <input type="radio" disabled="disabled" name="n17_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n17_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                 <%---------------------------------------------- 不合格17 --%>
                         <input type="radio" disabled="disabled" name="n17_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n17_text --%>
                            <span id="n17_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">18</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText
                           style="margin-right:-5.4000pt;mso-para-margin-right:-0.4900gd;text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">等电位</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">接地箱</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">和设备</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">配电箱均安装完成</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">，</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">电箱的电压及接地均符合设计要求</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">仪器</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">测量</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                                <%---------------------------------------------- 合格18 --%>
                         <input type="radio" disabled="disabled" name="n18_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n18_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                       <%---------------------------------------------- 不合格18 --%>
                         <input type="radio" disabled="disabled" name="n18_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n18_text --%>
                            <span id="n18_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:25.6000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">19</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText
                           style="margin-right:-5.4000pt;mso-para-margin-right:-0.4900gd;text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">设备间地板铺装完成，墙面刷涂完成</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                      <%---------------------------------------------- 合格19 --%>
                         <input type="radio" disabled="disabled" name="n19_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n19_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                 <%---------------------------------------------- 不合格19 --%>
                         <input type="radio" disabled="disabled" name="n19_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n19_text --%>
                            <span id="n19_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.1500pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">20</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText
                           style="margin-right:-5.4000pt;mso-para-margin-right:-0.4900gd;text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">投影机安装位置周边各类管道安装、焊接，装饰、土建抹灰及上色喷涂等施工作业已完成</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                             <%---------------------------------------------- 合格20 --%>
                         <input type="radio" disabled="disabled" name="n20_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n20_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                       <%---------------------------------------------- 不合格20 --%>
                         <input type="radio" disabled="disabled" name="n20_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n20_text --%>
                            <span id="n20_text"></span>
                        </o:p></span>
                        </p>
                    </td>
                </tr>
                <tr style="height:27.3000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">21</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText
                           style="margin-right:-5.4000pt;mso-para-margin-right:-0.4900gd;text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">投影机安装位置及附近无渗水，无漏水</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                      <%---------------------------------------------- 合格21 --%>
                         <input type="radio" disabled="disabled" name="n21_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n21_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                   <%---------------------------------------------- 不合格21 --%>
                         <input type="radio" disabled="disabled" name="n21_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n21_text --%>

                            <span id="n21_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:23.5000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">22</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">建议在总控室及设备间监控系统正常使用后设备进场</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                        <%---------------------------------------------- 合格22 --%>
                         <input type="radio" disabled="disabled" name="n22_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n22_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                        <%---------------------------------------------- 不合格22 --%>
                         <input type="radio" disabled="disabled" name="n22_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n22_text --%>
                            <span id="n22_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:25.4000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">23</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=235 valign=center
                        style="width:176.3500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">投影机安装环境良好，不潮湿，保持通风和散热</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=75 valign=center
                        style="width:56.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">目测</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal align=center style="margin-bottom:0.0000pt;text-align:center;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">感观</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">判断</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=85 valign=center
                        style="width:63.8000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                             <%---------------------------------------------- 合格23 --%>
                         <input type="radio" disabled="disabled" name="n23_flag" value="1" class="easyui-validatebox"
                                validType="radio['frm','n23_flag']">
                            合格 </span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p>
                        <p class=MsoNormal style="margin-bottom:0.0000pt;"><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;">
                        <%---------------------------------------------- 不合格23 --%>
                         <input type="radio" disabled="disabled" name="n23_flag" value="0" class="easyui-validatebox">
                            不合格</span><span
                                style="font-family:宋体;font-size:10.5000pt;"><o:p></o:p></span></p></td>
                    <td width=142 valign=center
                        style="width:106.9500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- n23_text --%>
                            <span id="n23_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:31.5000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">24</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">结</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">论</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=538 valign=center colspan=4
                        style="width:403.8500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- 结论 --%>
                            <span id="jieLun_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:34.8000pt;">
                    <td width=56 valign=center
                        style="width:42.5000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">25</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=57 valign=center
                        style="width:42.7500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">其</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                        <p class=MsoPlainText><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">他</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                    <td width=538 valign=center colspan=4
                        style="width:403.8500pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:none;mso-border-left-alt:none;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p>
                            <%---------------------------------------------- 其他 --%>
                            <span id="other_text"></span>
                        </o:p></span>
                        </p></td>
                </tr>
                <tr style="height:20.9000pt;">
                    <td width=652 valign=center colspan=6
                        style="width:489.1000pt;padding:0.0000pt 5.4000pt 0.0000pt 5.4000pt ;border-left:1.0000pt solid windowtext;mso-border-left-alt:0.5000pt solid windowtext;border-right:1.0000pt solid windowtext;mso-border-right-alt:0.5000pt solid windowtext;border-top:none;mso-border-top-alt:0.5000pt solid windowtext;border-bottom:1.0000pt solid windowtext;mso-border-bottom-alt:0.5000pt solid windowtext;">
                        <p class=MsoPlainText style="text-align:left;"><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">注</span><span
                                style="mso-spacerun:'yes';font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;">：检查异常情况需附照片并作详细说明，及时反馈给相关部门。</span><span
                                style="font-family:宋体;font-size:10.5000pt;mso-font-kerning:1.0000pt;"><o:p></o:p></span>
                        </p>
                    </td>
                </tr>
            </table>
        </div>
        <p class=MsoNormal style="mso-line-height-alt:12pt;"><span
                style="mso-spacerun:'yes';font-family:Tahoma;font-size:10.5000pt;"><o:p>&nbsp;</o:p></span></p></div>
</form>
<div id="dlg-buttons" style=" position: relative;top: -1px;padding: 5px;text-align: right;">
    <a href="javascript:startRedictApply()" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
    <a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<!--EndFragment--></body>
</html>