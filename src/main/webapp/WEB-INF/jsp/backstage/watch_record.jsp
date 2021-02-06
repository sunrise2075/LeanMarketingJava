<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>观看记录管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary], .layui-table-cell .layui-form-radio[lay-skin=primary] {
        top: 0px;
        vertical-align: middle;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">

            <div class="layui-card">
                <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                    <%--<div class="layui-form-item">--%>
                    <%--<div class="layui-inline">--%>
                    <%--<select name="identity" lay-filter="identity" id="selectIdentity">--%>
                    <%--<option value="">请选择用户身份</option>--%>
                    <%--<option value="1">普通会员</option>--%>
                    <%--<option value="2">企业会员</option>--%>
                    <%--<option value="3">代理商</option>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <%--<div class="layui-inline">--%>
                    <%--<select name="memberLevel" lay-filter="memberLevel" id="selectMemberLevel">--%>
                    <%--<option value="">请选择会员等级</option>--%>
                    <%--<option value="1">普通会员</option>--%>
                    <%--<option value="2">初级会员</option>--%>
                    <%--<option value="3">中级会员</option>--%>
                    <%--<option value="4">高级会员</option>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <%--<c:if test="${login_admin.address==null}">--%>
                    <%--<div class="layui-inline">--%>
                    <%--<div class="layui-input-inline">--%>
                    <%--<input type="text" name="registeredAddress" id="selectAddress" placeholder="请输入用户注册地址" autocomplete="off" class="layui-input">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</c:if>--%>

                    <%--<div class="layui-inline">--%>
                    <%--<button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit--%>
                    <%--lay-filter="search">--%>
                    <%--<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>--%>
                    <%--</button>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                </div>

                <div class="layui-card-body">
                    <table class="layui-hide" id="table" lay-filter="table">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script>

    layui.config({
        base: '${pageContext.request.contextPath}/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table', 'laydate'], function () {
        var $ = layui.jquery,
            table = layui.table,
            layer = layui.layer,
            form = layui.form;

        var roleCode = "${login_admin.roleCode}";
        var type = "";
        if (roleCode == 1) {
            type = 1;
        } else {
            type = 2;
        }

        table.render({
            elem: '#table',
            url: '/watch/record/list2?type=' + type,
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'userName', title: '用户名称', minWidth: 200},
                {field: 'videoName', title: '视频名称', minWidth: 200},
                {
                    field: 'createTime',
                    minWidth: 200,
                    title: '创建时间',
                    templet: '<div>{{layui.util.toDateString(d.createTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
                {
                    field: 'updateTime',
                    minWidth: 200,
                    title: '更新时间',
                    templet: '<div>{{layui.util.toDateString(d.updateTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
            ]]
            , page: true
            , limit: 10
            , limits: [10, 15, 20, 25, 30]
            , height: 'full-220'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });
    });
</script>


</html>


