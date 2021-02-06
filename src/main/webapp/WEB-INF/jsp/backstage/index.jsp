<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>财务管理</title>
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
                    <div class="layui-form-item">
                        <c:if test="${login_admin.roleCode == 1}">

                            <div class="layui-inline">
                                <select name="dealerId" id="dealerId">

                                </select>
                            </div>

                            <div class="layui-inline">
                                <select name="province" id="province">

                                </select>
                            </div>

                            <div class="layui-inline">
                                <button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit
                                        lay-filter="search">
                                    <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                                </button>
                            </div>
                        </c:if>
                    </div>
                </div>

                <div class="layui-card-body">
                    <table class="layui-hide" id="table" lay-filter="table">
                        <div style="padding-bottom: 10px;">
                            <button class="layui-btn" id="export">导出</button>
                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/area.js"></script>
<script>
    var html = "<option value=''>请选择代理省份</option>";
    for (var i = 0; i < provice.length; i++) {

        html = html + "<option value='" + provice[i]['name'] + "'>" + provice[i]['name'] + "</option>";

        document.getElementById("province").innerHTML = html;
    }
</script>
<script type="text/html" id="type">
    {{# if(d.type == '1') {d.type = '视频消费'}   }}
    {{# if(d.type == '2') {d.type = '文库消费'}   }}
    {{# if(d.type == '3') {d.type = '考试消费'}   }}
    {{# if(d.type == '4') {d.type = '会员充值'}   }}
    <span>{{d.type}}</span>
</script>
<script>
    layui.config({
        base: '${pageContext.request.contextPath}/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table', 'laydate'], function () {
        var $ = layui.jquery,
            table = layui.table,
            layer = layui.layer,
            form = layui.form,
            data = form.data,
            upload = layui.upload;

        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });

        //动态加载下拉框值
        $.ajax({
            url: '/dealer/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                var data = result.data;

                var html = '<option value="">请选择经销商</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.name + '</option>';
                });
                $("#dealerId").html(html);
                form.render('select');
            }
        });


        var roleCode = "${login_admin.roleCode}";
        var address = "";
        var superiorId = "";
        var type = "";
        if (roleCode == 1) {   //企业管理员

        } else if (roleCode == 2) { //代理商管理员

            address = "${login_admin.address}";

        } else if (roleCode == 3) { //企业管理员

        } else {                   //经销商管理员

            var userId = "${login_admin.userId}";
            type = 1;

            if (userId == null) {
                superiorId = -1;  //不存在
            } else {
                superiorId = userId;
            }
        }
        table.render({

            elem: '#table',
            url: '/consume/record/list?address=' + address + '&superiorId=' + superiorId + '&type=' + type,
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'orderNumber', title: '订单号', minWidth: 200},
                {field: 'userName', title: '用户名称', minWidth: 200},
                {field: 'type', title: '消费类型', templet: "#type", minWidth: 200},
//                {field: 'payment', title: '支付方式'},
                {field: 'payMoney', title: '支付金额', minWidth: 200},
                {field: 'address', title: '地址', minWidth: 200},
                {
                    field: 'payTime',
                    minWidth: 200,
                    title: '支付时间',
                    templet: '<div>{{layui.util.toDateString(d.payTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
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

        $('#export').on('click', function () {

            var province = "";
            var dealerId = "";
            if (roleCode == 1) {
                province = $('#province option:selected').val();
                dealerId = $('#dealerId option:selected').val();
            }
            address = province;

            location.href = '/consume/record/exportExcel?address=' + address + '&superiorId=' + superiorId + '&type=' + type + '&dealerId=' + dealerId;
        });


    });
</script>
</html>

