<%--
  Created by IntelliJ IDEA.
  User: LIUHD
  Date: 2020/01/10
  Time: 14:17
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>news</title>

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
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jdirk.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/autoPinyin.js"></script>
    <script type="text/javascript">
        $(function () {

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


        })

    </script>
</head>
<body>
<table class="TailTable" border="0" align="center" cellpadding="0" cellspacing="2">
    <tr>
        <td height="490" align="center" valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="5"></td>
                </tr>
            </table>
            <form id="formDevEdit" name="formDevEdit">
                <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
<%--                    <tr>--%>
<%--                        <td>--%>
<%--                            <table width="100%" border="1" cellspacing="0" cellpadding="0">--%>
<%--                                <tr>--%>
<%--                                    <td align="center" valign="top" class="H2">设备信息详情</td>--%>
<%--                                </tr>--%>
<%--                            </table>--%>
<%--                        </td>--%>
<%--                    </tr>--%>
                    <tr>
                        <td>
                            <table class="TailTable"  border="1" align="center" cellpadding="0" cellspacing="0">
                                <tr align="center">
                                    <td class="TailLabelc" colspan="7">设备基本信息</td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc" >乐园简称:</td>
                                    <td  width="180" align="center" valign="middle"><label></label></td>
                                    <td class="TailLabelc">项目名称:</td>
                                    <td  align="center" valign="middle"></td>
                                    <td class="TailLabelc">设备序号:</td>
                                    <td  width="120" align="center" valign="middle"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">设备名称：</td>
                                    <td width="180" align="center" valign="middle"><label></label></td>
                                    <td class="TailLabelc">设备型号: </td>
                                    <td width="180" align="center" valign="middle"></td>
                                    <td class="TailLabelc">设备类型:</td>
                                    <td width="180" align="center" valign="middle"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">包装类型：</td>
                                    <td width="180" align="center" valign="middle"><label></label></td>
                                    <td class="TailLabelc">沿用情况: </td>
                                    <td width="180" align="center" valign="middle"></td>
                                    <td class="TailLabelc">检验级别:</td>
                                    <td width="180" align="center" valign="middle"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">供应厂家：</td>
                                    <td width="180" align="center" valign="middle"></td>
                                    <td class="TailLabelc">安装厂家:</td>
                                    <td width="180" align="center" valign="middle"></td>
                                    <td class="TailLabelc">型式试验:</td>
                                    <td width="180" align="center" valign="middle"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">设备数量：</td>
                                    <td width="180" align="center" valign="middle"></td>
                                    <td class="TailLabelc">负责人: </td>
                                    <td width="180" align="center" valign="middle"></td>
                                    <td class="TailLabelc">&nbsp;</td>
                                    <td width="180" align="center" valign="middle">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc"><p>总工办计划基</p>
                                        <p>础开始日期：</p></td>
                                    <td align="center" valign="middle"></td>
                                    <td class="TailLabelc"><p>总工办计划基</p>
                                        <p>础完成日期：</p></td>
                                    <td align="center" valign="middle"></td>
                                    <td class="TailLabelc"><p>总工办计划设</p>
                                        <p>备进场日期：</p></td>
                                    <td align="center" valign="middle"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table class="TailTable" border="1" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="TailLabelc" colspan="7" >设备总体情况</td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">设备状态:&nbsp; </td>
                                    <td colspan="6" align="left"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">备注情况:&nbsp;</td>
                                    <td colspan="6" align="left"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">问题反馈:&nbsp;</td>
                                    <td colspan="6" align="left"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">预警说明:</td>
                                    <td width="500" colspan="6" align="left"><span style="color: #F00"></span></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table class="TailTable" border="1" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="TailLabelc" colspan="7">设备进度详细情况</td>
                                </tr>
                                <tr class="table_td_Left">
                                    <td class="TailLabelc">图审状态</td>
                                    <td class="TailLabelc">安装基础实际开始日期</td>
                                    <td class="TailLabelc">安装基础实际完成日期</td>
                                    <td class="TailLabelc">安装基础状态</td>
                                </tr>
                                <tr>
                                    <td align="center" width="180"><input type="text" class="TailInput" name="sbzt"
                                               readonly="true" data-options="required:true"></td>
                                    <td align="center" width="180"><input type="text" class="TailInput" name="sbzt"
                                               readonly="true" data-options="required:true"></td>
                                    <td align="center" width="180"><input type="text" class="TailInput" name="sbzt"
                                               readonly="true" data-options="required:true"></td>
                                    <td align="center" width="180"><input type="text" class="TailInput" name="sbzt"
                                               readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td height="5" colspan="4">&nbsp;</td>
                                </tr>
                            </table>

                            <table class="TailTable">
                                <tr>
                                    <td class="TailLabelc">环节/状态</td>
                                    <td class="TailLabelc">状态</td>
                                    <td class="TailLabelc">计划开始日期</td>
                                    <td class="TailLabelc">实际开始日期</td>
                                    <td class="TailLabelc"计划完成日期</td>
                                    <td class="TailLabelc">实际完成日期</td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">选型方案:</td>
                                    <td align="center"><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                    <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                    <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                    <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                    <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">提单状态:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">采购状态:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">深化方案:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">主结构生产:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">造型生产:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">电气生产:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">主结构到货:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">造型到货:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">电气到货:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">主结构安装:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">造型安装: </td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">电气安装:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">设备调试:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">型式试验:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">国/省检:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                                <tr>
                                    <td class="TailLabelc">竣工验收:</td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                     <td align="center" ><input type="text" class="TailInput" name="sbzt"
                                                                              readonly="true" data-options="required:true"></td>
                                </tr>
                            </table>

                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>