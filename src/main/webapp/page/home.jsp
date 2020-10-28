<%--
  Created by IntelliJ IDEA.
  User: liuhd
  Date: 2020/1/6
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home.css">

</head>
<body>
<div class="allBox">
    <div class="homeLeft01">
        <a href="#" class="homeUpaBox">
            <div class="aLeft"><img src="${pageContext.request.contextPath}/static/images/daiban.png"/></div>
            <div class="aRight">
                <p class="fontSizeBig" style="margin-top: 10px">14<span class="fontSizeMin">个</span></p>
                <p class="fontSizeMin">待办未处理</p>
            </div>
        </a>
        <a href="#" class="homeUpaBule">
            <div class="aLeft"><img src="${pageContext.request.contextPath}/static/images/daiban.png"/></div>
            <div class="aRight">
                <p class="fontSizeBig" style="margin-top: 10px">14<span class="fontSizeMin">元</span></p>
                <p class="fontSizeMin">我的询价金额</p>
            </div>
        </a>
    </div>
    <div class="homeLeft01">
        <a href="#" class="homeUpaBule03">
            <div class="aLeft"><img src="${pageContext.request.contextPath}/static/images/daiban.png"/></div>
            <div class="aRight">
                <p class="fontSizeBig" style="margin-top: 10px">14<span class="fontSizeMin">个</span></p>
                <p class="fontSizeMin">待办未处理</p>
            </div>
        </a>
        <a href="#" class="homeUpaBule04">
            <div class="aLeft"><img src="${pageContext.request.contextPath}/static/images/daiban.png"/></div>
            <div class="aRight">
                <p class="fontSizeBig" style="margin-top: 10px">14<span class="fontSizeMin">元</span></p>
                <p class="fontSizeMin">我的询价金额</p>
            </div>
        </a>
    </div>
    <div class="homeLeft01">
        <a href="#" class="homeUpaBule01">
            <div class="aLeft"><img src="${pageContext.request.contextPath}/static/images/daiban.png"/></div>
            <div class="aRight">
                <p class="fontSizeBig" style="margin-top: 10px">14<span class="fontSizeMin">个</span></p>
                <p class="fontSizeMin">待办未处理</p>
            </div>
        </a>
        <a href="#" class="homeUpaBule02">
            <div class="aLeft"><img src="${pageContext.request.contextPath}/static/images/daiban.png"/></div>
            <div class="aRight">
                <p class="fontSizeBig" style="margin-top: 10px">14<span class="fontSizeMin">元</span></p>
                <p class="fontSizeMin">我的询价金额</p>
            </div>
        </a>
    </div>
    <div id="calder" style="width: 15.2%;" class="homeLeft02"></div>
    <div class="homeLeft03">
        <h5 class="hStyle"><span>通知公告</span><a href="#">更多</a></h5>
        <ul class="orderUl" id="notic">
            <li><a href="#">综合管理中心-试运行期间故障或改进信息的落实与优化</a><span>03-15</span></li>
            <li><a href="#">各相关中心-组织年度信息管理平台的版本更新维护评审</a><span>05-18</span></li>
            <li><a href="#">机电调试中心-试运行期间故障或改进信息的收集与反馈</a><span>07-30</span></li>
            <li><a href="#">综合管理中心-体系文件查阅及发布管理年提出全面推广</a><span>09-01</span></li>
            <li><a href="#">各相关中心-落实《信息管理平台评审会议纪要》的内容</a><span>10-21</span></li>
            <li><a href="#">综合管理中心-正式运行版本的维护与监测集中进行统一</a><span>11-25</span></li>
        </ul>
    </div>
    <div class="clear"></div>
    <div class="homeLeft04">
        <h5 class="hStyle"><span>风险分析</span></h5>
        <p class="stataAny" id="chart01"></p>
    </div>
    <div class="homeLeft04">
        <h5 class="hStyle"><span>天气分析</span></h5>
        <p class="stataAny" id="chart02"></p>
    </div>
    <div class="homeLeft03" style="margin-top: 5px">
        <h5 class="hStyle"><span>综合计划</span><a href="#">更多</a></h5>
        <ul class="orderUl">
            <li><a href="#">综合管理中心-试运行期间故障或改进信息的落实与优化</a><span>03-15</span></li>
            <li><a href="#">各相关中心-组织年度信息管理平台的版本更新维护评审</a><span>05-18</span></li>
            <li><a href="#">机电调试中心-试运行期间故障或改进信息的收集与反馈</a><span>07-30</span></li>
            <li><a href="#">综合管理中心-体系文件查阅及发布管理年提出全面推广</a><span>09-01</span></li>
            <li><a href="#">各相关中心-落实《信息管理平台评审会议纪要》的内容</a><span>10-21</span></li>
            <li><a href="#">综合管理中心-正式运行版本的维护与监测集中进行统一</a><span>11-25</span></li>
        </ul>
    </div>
    <div class="clear"></div>
    <div class="homeLeft05">
        <h5 class="hStyle"><span>交货分析</span></h5>
        <p class="stataAny" style="height: 220px" id="chart03"></p>
    </div>
    <div class="homeLeft06" id="rightTab">
        <div title="待办信息" style="display:none;">
            <ul class="orderUl">
                <li><a href="#">需求管理:安阳一期-历史文化科技圆(WX1912-A0001H)     </a><span>03-15</span></li>
                <li><a href="#">问题单管理:绵阳1-牛郎织女-机电：(201912050000003)   </a><span>05-18</span></li>
                <li><a href="#">库存管理:机械生产-辅助：(SX2913-A0005H)             </a><span>07-30</span></li>
                <li><a href="#">派单管理:投影机日检(201912311656182)                </a><span>09-01</span></li>
                <li><a href="#">计划需求管理:九州神韵-灯光系统(201912311652183)     </a><span>10-21</span></li>
                <li><a href="#">采购流程:GB/T709 Q235B T1：(SX2913-A0005H)          </a><span>11-25</span></li>
            </ul>
            <p class="tabConP"><a href="#">更多</a></p>
        </div>
        <div title="已办信息" style="overflow:auto;display:none;">
            <ul class="orderUl">
                <li><a href="#">综合管理中心-试运行期间故障或改进信息的落实与优化</a><span>03-15</span></li>
                <li><a href="#">各相关中心-组织年度信息管理平台的版本更新维护评审</a><span>05-18</span></li>
                <li><a href="#">机电调试中心-试运行期间故障或改进信息的收集与反馈</a><span>07-30</span></li>
                <li><a href="#">综合管理中心-体系文件查阅及发布管理年提出全面推广</a><span>09-01</span></li>
                <li><a href="#">各相关中心-落实《信息管理平台评审会议纪要》的内容</a><span>10-21</span></li>
                <li><a href="#">综合管理中心-正式运行版本的维护与监测集中进行统一</a><span>11-25</span></li>
            </ul>
            <p class="tabConP"><a href="#">更多</a></p>
        </div>
    </div>
    <div class="clear"></div>

</div>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/jquery-easyui-1.7.0/locale/easyui-lang-zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/static/echarts/echarts-all.js"></script>
<script src="${pageContext.request.contextPath}/static/js/home.js"></script>

</body>
</html>
