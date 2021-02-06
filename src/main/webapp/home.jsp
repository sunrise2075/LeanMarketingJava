<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>精益营销管理后台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/admin.css" media="all">
</head>
<body class="layui-layout-body">
<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <!-- 头部区域 -->
            <ul class="layui-nav layui-layout-left">
                <li class="layui-nav-item layadmin-flexible lay-unselect">
                    <a href="javascript:" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:" layadmin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right" lay-filter="layadmin-layout-right">
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:" layadmin-event="fullscreen">
                        <i class="layui-icon layui-icon-screen-full"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:">
                        <img class="layui-nav-img" src="${login_admin.headPortrait}">
                        <cite id="showName">${login_admin.username}</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd layadmin-event="" style="text-align: center;"><a style="cursor: pointer;"
                                                                             onclick="outLogin()">退出</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:" layadmin-event=""><i class="layui-icon layui-icon-more-vertical"></i></a>
                </li>
            </ul>
        </div>
        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div class="layui-logo" lay-href="../manager/datastatistics.jsp">
                    <span>后台管理系统</span>
                </div>
                <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu"
                    lay-filter="layadmin-system-side-menu">

                    <li data-name="home" class="layui-nav-item " id="id1" style="display: none">
                        <a href="javascript:" lay-href="page/admininfo" lay-tips="个人信息管理" lay-direction="2">
                            <i class="layui-icon layui-icon-app"></i>
                            <cite>个人信息管理</cite>
                        </a>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id15" style="display: none">
                        <a href="javascript:" lay-tips="公司信息管理" lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>公司信息管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/lean_marketing">Lean Marketing 管理</a></dd>
                            <dd><a lay-href="page/marketing_consultant">营销顾问管理</a></dd>
                            <dd><a lay-href="page/example">案例管理</a></dd>
                            <dd><a lay-href="page/companyinfo">公司信息管理</a></dd>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id2" style="display: none">
                        <a href="javascript:" lay-href="page/agent" lay-tips="代理商管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>代理商管理</cite>
                        </a>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id16" style="display: none">
                        <a href="javascript:" lay-href="page/dealer" lay-tips="经销商管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>经销商管理</cite>
                        </a>
                    </li>

                    <li data-name="template" class="layui-nav-item" id="id3" style="display: none">
                        <a href="javascript:" lay-tips="权限管理" lay-direction="2">
                            <i class="layui-icon layui-icon-template"></i>
                            <cite>权限管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/administrator">管理员管理</a></dd>
                            <dd><a lay-href="page/role">角色组管理</a></dd>
                            <dd><a lay-href="page/administrator_log">管理员日志</a></dd>
                        </dl>
                    </li>

                    <li data-name="app" class="layui-nav-item" id="id4" style="display: none">
                        <a href="javascript:" lay-tips="会员管理" lay-direction="2">
                            <i class="layui-icon layui-icon-template"></i>
                            <cite>会员管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/user">会员管理</a></dd>
                            <c:if test="${login_admin.roleCode==1}">
                                <dd><a lay-href="page/user_benefit">会员权益管理</a></dd>
                                <%--<dd><a lay-href="page/user_collection_product">收藏商品管理</a></dd>--%>
                                <%--<dd><a lay-href="page/user_collection_video">收藏视频管理</a></dd>--%>
                                <%--<dd><a lay-href="page/user_recharge_order">会员充值管理</a></dd>--%>
                            </c:if>
                        </dl>
                    </li>


                    <li data-name="user" class="layui-nav-item" id="id5" style="display: none">
                        <a href="javascript:" lay-tips="视频管理" lay-direction="2">
                            <i class="layui-icon layui-icon-user"></i>
                            <cite>视频管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/video_category">视频分类管理</a></dd>
                            <dd><a lay-href="page/video">视频管理</a></dd>
                            <dd><a lay-href="page/video_chapter">视频章节管理</a></dd>
                            <dd><a lay-href="page/video_contents">视频内容管理</a></dd>
                            <%--<dd><a lay-href="page/watch_record">观看记录</a></dd>--%>
                            <%--<dd><a lay-href="page/video_evaluate">视频评价管理</a></dd>--%>
                            <%--<dd><a lay-href="page/video_order">视频订单管理</a></dd>--%>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id6" style="display: none">
                        <a href="javascript:" lay-tips="评估管理" lay-direction="2">
                            <i class="layui-icon layui-icon-template"></i>
                            <cite>评估管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/exam_category">试题分类管理</a></dd>
                            <dd><a lay-href="page/exam_subject">评估管理</a></dd>
                            <dd><a lay-href="page/exam_questions">评估答案管理</a></dd>
                            <dd><a lay-href="page/exam_score">评估结果管理</a></dd>
                            <dd><a lay-href="page/exam_ability">评估能力管理</a></dd>
                            <%--<dd><a lay-href="page/exam_order">试题订单管理</a></dd>--%>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id7" style="display: none">
                        <a href="javascript:" lay-tips="文库管理" lay-direction="2">
                            <i class="layui-icon layui-icon-template"></i>
                            <cite>文库管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/library_category">文库分类管理</a></dd>
                            <dd><a lay-href="page/library">文库管理</a></dd>
                            <%--<dd><a lay-href="page/library_download_record">下载记录管理</a></dd>--%>
                            <%--<dd><a lay-href="page/library_order">文库订单管理</a></dd>--%>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id8" style="display: none">
                        <a href="javascript:" lay-tips="商品管理" lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>商品管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/product_category">分类管理</a></dd>
                            <dd><a lay-href="page/product">商品管理</a></dd>
                            <%--<dd><a lay-href="page/product_evaluate">商品评价管理</a></dd>--%>
                        </dl>
                    </li>

                    <li data-name="app" class="layui-nav-item" id="id9" style="display: none">
                        <a href="javascript:" lay-href="page/product_order" lay-tips="订单管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-app"></i>
                            <cite>订单管理</cite>
                        </a>
                    </li>

                    <li data-name="user" class="layui-nav-item" id="id10" style="display: none">
                        <a href="javascript:" lay-href="page/index" lay-tips="财务管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-user"></i>
                            <cite>财务管理</cite>
                        </a>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id11" style="display: none">
                        <a href="javascript:" lay-tips="轮播图管理" lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>轮播图管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/banner">商品轮播图管理</a></dd>
                            <dd><a lay-href="page/video_banner">视频轮播图管理</a></dd>
                            <dd><a lay-href="page/library_banner">文库轮播图管理</a></dd>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id12" style="display: none">
                        <a href="javascript:" lay-tips="员工管理" lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>员工管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/employee">子账号管理</a></dd>

                            <dd><a lay-href="page/watch_record">观看记录管理</a></dd>
                            <c:if test="${login_admin.roleCode == 3}">
                                <dd><a lay-href="page/exam_score2">评估结果管理</a></dd>
                            </c:if>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id13" style="display: none">
                        <a href="javascript:" lay-href="page/news" lay-tips="新闻管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>新闻管理</cite>
                        </a>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id14" style="display: none">
                        <a href="javascript:" lay-href="page/black_list" lay-tips="黑名单管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>黑名单管理</cite>
                        </a>
                    </li>

                    <li data-name="user" class="layui-nav-item" id="id17" style="display: none">
                        <a href="javascript:" lay-tips="拼课管理" lay-direction="2">
                            <i class="layui-icon layui-icon-user"></i>
                            <cite>拼课管理</cite>
                        </a>
                        <dl class="layui-nav-child">
                            <dd><a lay-href="page/lesson_collage">课程管理</a></dd>
                            <dd><a lay-href="page/lesson_collage_rule">拼课规则管理</a></dd>
                            <dd><a lay-href="page/lesson_collage_record">拼课记录管理</a></dd>
                        </dl>
                    </li>

                    <li data-name="get" class="layui-nav-item" id="id18" style="display: none">
                        <a href="javascript:" lay-href="page/label" lay-tips="标签管理"
                           lay-direction="2">
                            <i class="layui-icon layui-icon-auz"></i>
                            <cite>标签管理</cite>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <!-- 页面标签 -->
        <div class="layadmin-pagetabs" id="LAY_app_tabs">

            <div class="layui-icon layadmin-tabs-control layui-icon-down">
                <ul class="layui-nav layadmin-tabs-select" lay-filter="layadmin-pagetabs-nav">
                    <li class="layui-nav-item" lay-unselect>
                        <a href="javascript:"></a>
                        <dl class="layui-nav-child layui-anim-fadein">
                            <dd layadmin-event="closeThisTabs"><a href="javascript:">关闭当前标签页</a></dd>
                            <dd layadmin-event="closeOtherTabs"><a href="javascript:">关闭其它标签页</a></dd>
                            <dd layadmin-event="closeAllTabs"><a href="javascript:">关闭全部标签页</a></dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <div class="layui-tab" lay-unauto lay-allowClose="true" lay-filter="layadmin-layout-tabs">
                <ul class="layui-tab-title" id="LAY_app_tabsheader">
                    <li lay-id="home/console.html" lay-attr="home/console.html" class="layui-this"><i
                            class="layui-icon layui-icon-home"></i></li>
                </ul>
            </div>
        </div>


        <!-- 主体内容 -->
        <div class="layui-body" id="LAY_app_body">
            <div class="layadmin-tabsbody-item layui-show">
                <iframe src="page/admininfo" frameborder="0" class="layadmin-iframe"></iframe>
            </div>
        </div>

        <!-- 辅助元素，一般用于移动设备下遮罩 -->
        <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/layuiadmin/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script>

    layui.config({
        base: '${pageContext.request.contextPath}/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index'], function () {

        var $ = layui.jquery,
            layer = layui.layer;

        /**
         * 获取用户权限
         */
        $.ajax({
            url: '/administrator/isHaveAuthority',
            type: "post",
            success: function (result) {

                if (result.code == 200) {
                    var codes = result.data.codes.split(",");
                    for (var j = 0; j < codes.length; j++) {
                        document.getElementById("id" + codes[j]).style.display = "block";
                    }
                } else {
                    layer.msg("没有权限,3秒之后调回登录页面");
                    setTimeout(function () {//等待2s跳转
                        top.location.href = "login.jsp";
                    }, 3000);
                }
            }
        });
        return false;
    });

    function outLogin() {

        $.ajax({
            url: 'administrator/login_out',
            type: "get",
            success: function (data) {
                if (data) {//需要登录
                    top.location.href = "login.jsp";
                }
            }
        });
    }
</script>
</body>
</html>

