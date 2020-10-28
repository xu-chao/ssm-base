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
                            if (rec.length>0) {
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
<div class="easyui-layout" data-options="fit:true">
    <div id="tabs" style="margin:0 auto;width:60%;height: 100%; padding:3%;">
        <table class="TailTable">
            <tr align="center">
                <td colspan="6" style="text-align: center;font-weight: bold;font-size: 18px;">设备基本信息修改</td>
            </tr>
            <tr>
                <td class="TailLabel">乐园简称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-combobox TailInput"
                                                          name="name" id="parkIDSelect"
                                                          data-options="required:true"></td>
                <td class="TailLabel">项目名称：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-combobox TailInput" editable="false"
                                                          name="project.projectID" id="projectIDSelect"
                                                          data-options="required:true"></td>
                <td class="TailLabel">设备序号：</td>
                <td align="center" valign="middle"><input type="text" class="TailInput"
                                                          name="name"
                                                          data-options="required:true"></td>
            </tr>
            <tr>
                <td class="TailLabel">设备名称：</td>
                <td align="center" valign="middle"><input type="text" class="TailInput"
                                                          name="name"
                                                          data-options="required:true"></td>
                <td class="TailLabel">设备型号：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-validatebox TailInput"
                                                          id="dateBox"/></td>
                <td class="TailLabel">设备类型：</td>
                <td align="center" valign="middle"><input type="text" id="sbStatus" name="sbStatus"
                                                          class="easyui-combobox TailInput" editable="false"
                                                          data-options="
    valueField: 'typeID',
    textField: 'typeName',
    url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=sblx',
   onLoadSuccess: function(rec){
                    $(this).combobox('setValue',  rec[0].typeID);
                    }"
                /></td>

            </tr>
            <tr>
                <td class="TailLabel">包装类型：</td>
                <td align="center" valign="middle"><input type="text" class="easyui-validatebox TailInput"></td>
                <td class="TailLabel">沿用情况：</td>
                <td align="center" valign="middle"><input type="text" name="yyqk" class="easyui-validatebox TailInput">
                </td>
                <td class="TailLabel">检验级别：</td>
                <td align="center" valign="middle"><input type="text" name="jyjbStatus"
                                                          class="easyui-combobox TailInput" editable="false"
                                                          data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=jyjb',
                   onLoadSuccess: function(rec){
                    $(this).combobox('setValue',  rec[0].typeID);
                    }"
                ></td>

            </tr>
            <tr>
                <td class="TailLabel">供应单位：</td>
                <td align="center" valign="middle"><input type="text" name="gydw" class="easyui-validatebox TailInput">
                </td>
                <td class="TailLabel">安装单位：</td>
                <td align="center" valign="middle"><input type="text" name="azdw" class="easyui-validatebox TailInput">
                </td>
                <td class="TailLabel">型式试验：</td>
                <td align="center" valign="middle"><input type="text" name="xssyStatus"
                                                          class="easyui-combobox TailInput" editable="false"
                                                          data-options="
                                                          valueField: 'typeID',
                    textField: 'typeName',
                    url: '${pageContext.request.contextPath}/myType/findByTypeCode?typeCode=xuyao',
                onLoadSuccess: function(rec){
                    $(this).combobox('setValue',  rec[0].typeID);
                    }"
                >
                </td>

            </tr>
            <tr>
                <td class="TailLabel">设备数量：</td>
                <td align="center" valign="middle"><input type="text" name="sbsl" class="easyui-validatebox TailInput"
                /></td>
                <td class="TailLabel"> 负责人：</td>
                <td align="center" valign="middle"><input type="text" name="fzrUser"
                                                          class="easyui-validatebox TailInput"/></td>
                <td class="TailLabel"></td>
                <td align="center" valign="middle"></td>

            </tr>
             <tr>
                <td class="TailLabel">总工办计划基础开始日期：</td>
                <td align="center" valign="middle"><input type="text" name="startDate" class="easyui-datebox TailInput" /></td>
                <td class="TailLabel"> 总工办计划基础完成日期：</td>
                <td align="center" valign="middle"><input type="text" name="completeDate" class="easyui-datebox TailInput" /></td>
                <td class="TailLabel">总工办计划设备进场日期</td>
                <td align="center" valign="middle"><input type="text" name="approachDate" class="easyui-datebox TailInput" /></td>

            </tr>

        </table>
        <div class="forSubmint"><a href="#" class="easyui-linkbutton" iconCls="icon-save">保存</a><a href="#"
                                                                                                   class="easyui-linkbutton"
                                                                                                   iconCls="icon-redo">重置</a>
        </div>
    </div>
</div>
</div>
</body>
</html>