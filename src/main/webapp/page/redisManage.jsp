<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Redis管理</title>
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/Redis/css/content-base.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/static/Redis/js/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/static/Redis/js/index.js"></script>
<script>
    var _url_getMemeryInfo = "${pageContext.request.contextPath}/redis/getMemeryInfo";
    var _url_getKeysSize = "${pageContext.request.contextPath}/redis/getKeysSize";
    var _url_logEmpty = "${pageContext.request.contextPath}/redis/logEmpty";
</script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis 内存实时占用情况：</h5>
                </div>
                <div class="ibox-content">
                    <div id="container"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis key的实时数量：</h5>
                </div>
                <div class="ibox-content">
                    <div id="keysChart"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-5">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis INFO：</h5>
                </div>
                <div class="ibox-content">
                    <table class="table table-condensed table-hover"
                           style="word-break: break-all;">
                        <tbody>
                        <c:if test="${empty infoList}">
                            <td>暂无数据</td>
                        </c:if>
                        <c:if test="${not empty infoList}">
                            <c:forEach var="info" items="${infoList}">
                                <tr>
                                    <td title="${info.key}:${info.desctiption }">${info.key}</td>
                                    <td>${info.desctiption }</td>
                                    <td>${info.value}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-sm-7">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis实时日志(共
                        <c:if test="${empty logLen}">
                        0
                        </c:if>
                        <c:if test="${not empty logLen}">
                            ${logLen}
                        </c:if>
                    条)：</h5>
                    <div class="ibox-tools">
                        <button type="button" onclick="empty();"
                                class="btn btn-warning btn-xs">清空日志</button>
                    </div>
                </div>
                <div class="ibox-content">
                    <table class="table table-condensed table-hover">
                        <tbody>
                        <c:if test="${empty logList}">
                            <td><p style="width: 200px;">暂无数据</p></td>
                        </c:if>
                        <c:if test="${not empty logList}">
                            <c:forEach var="log" items="${logList }">
                                <tr>
                                    <td>${log.id }</td>
                                    <td>${log.executeTime }</td>
                                    <td>${log.usedTime }</td>
                                    <td><p style="width: 600px; word-wrap: break-word; word-break: normal;">${log.args }</p></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>