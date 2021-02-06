<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单管理</title>
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
        <div class="layui-col-md2">

            <div class="layui-card">
                <div class="layui-card-header">
                    全部订单
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">总</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font" id="totalNum"></p>
                </div>
            </div>
        </div>
        <div class="layui-col-md2 ">
            <div class="layui-card">
                <div class="layui-card-header">
                    待付款
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">总</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font" id="needPayNum"></p>
                </div>
            </div>
        </div>
        <div class="layui-col-md2">
            <div class="layui-card">
                <div class="layui-card-header">
                    待发货
                    <span class="layui-badge layui-bg-green layuiadmin-badge">总</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font" id="needSendNum"></p>
                </div>
            </div>
        </div>
        <div class="layui-col-md2">
            <div class="layui-card">
                <div class="layui-card-header">
                    待评价
                    <span class="layui-badge layui-bg-green layuiadmin-badge">总</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font" id="needEvaluateNum"></p>
                </div>
            </div>
        </div>
        <div class="layui-col-md2">
            <div class="layui-card">
                <div class="layui-card-header">
                    已完成
                    <span class="layui-badge layui-bg-orange layuiadmin-badge">总</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font" id="successNum"></p>
                </div>
            </div>
        </div>
        <div class="layui-col-md2">
            <div class="layui-card">
                <div class="layui-card-header">
                    已取消订单
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">总</span>
                </div>
                <div class="layui-card-body layuiadmin-card-list">
                    <p class="layuiadmin-big-font" id="cancelNum"></p>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='startTime' id="selectStartTime" class="layui-input"

                                       placeholder="开始时间">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='endTime' id="selectEndTime" class="layui-input"

                                       placeholder="结束时间">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='payNumber' id="selectPayNumber" class="layui-input"
                                       placeholder="请输入支付单号">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='userName' id="selectUserName" class="layui-input"
                                       placeholder="请输入用户名">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name='productName' id="selectProductName" class="layui-input"
                                       placeholder="请输入商品名称">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <select name="payState" id="selectPayState" lay-filter="LAY-user-adminrole-type">
                                <option value="">请选择订单类型</option>
                                <option value="">全部订单</option>
                                <option value="1">待付款订单</option>
                                <option value="2">待发货订单</option>
                                <option value="4">待评价订单</option>
                                <option value="5">已成功订单</option>
                                <option value="6">已取消订单</option>
                            </select>
                        </div>
                        <div class="layui-inline">
                            <button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit
                                    lay-filter="search">
                                <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-card">

                <div class="layui-card-body">
                    <table class="layui-hide" id="table" lay-filter="table">
                        <div style="padding-bottom: 10px;">
                            <button class="layui-btn" id="export">导出订单信息</button>
                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="layui-form" id="sendForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" id="id" name="id" autocomplete="off" class="layui-input" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">快递公司名称</label>
        <div class="layui-input-inline">
            <input type="text" name="logisticsCompany" autocomplete="off" placeholder="请输入快递公司名称"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">快递单号</label>
        <div class="layui-input-inline">
            <input type="text" name="courierNumber" autocomplete="off" placeholder="请输入快递单号" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="sendSubmit">确定</button>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="payState">
    {{# if(d.payState == '1') {d.payState = '待付款'}   }}
    {{# if(d.payState == '2') {d.payState = '待发货 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="send"><i class="layui-icon ">发货</i></a>'}   }}
    {{# if(d.payState == '3') {d.payState = '待收货'}   }}
    {{# if(d.payState == '4') {d.payState = '待评价'}   }}
    {{# if(d.payState == '5') {d.payState = '已成功'}   }}
    {{# if(d.payState == '6') {d.payState = '已取消'}   }}
    {{# if(d.payState == null) {d.payState = ''}   }}
    <span>{{d.payState}}</span>
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
            laydate = layui.laydate,

            util = layui.util,
            upload = layui.upload;

        //执行一个laydate实例
        laydate.render({
            elem: '#selectStartTime' //指定元素
        });
        laydate.render({
            elem: '#selectEndTime' //指定元素
        });

        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });

        table.render({

            elem: '#table',
            url: '/product/order/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'payNumber', title: '订单号', minWidth: 200},
                {field: 'userName', title: '用户名称', minWidth: 200},
                {field: 'productName', title: '商品名称', width: 200},
                {
                    field: 'productImg', title: '商品图片', templet: function (d) {
                        if (d.productImg != '') {
                            return '<div><img src=' + d.productImg + ' height="100%" width="100%"></div>'
                        } else {
                            return "";
                        }
                    }
                    , minWidth: 200
                },
                {field: 'productPrice', title: '商品单价', minWidth: 200},
                {field: 'productNum', title: '商品数量', minWidth: 200},

                {field: 'receiver', title: '收货人', minWidth: 200},
                {field: 'receiverPhone', title: '收货人电话', minWidth: 200},
                {field: 'receiverAddress', title: '收货人地址', minWidth: 200},

                {field: 'payment', title: '支付方式', minWidth: 200},
                {field: 'payState', title: '支付状态', templet: '#payState', minWidth: 200},
                {field: 'payMoney', title: '支付金额', minWidth: 200},
                {
                    field: 'payTime', title: '支付时间', templet: function (d) {

                        if (d.payTime != null) {
                            return util.toDateString(d.payTime);
                        } else {
                            return "";
                        }
                    }, minWidth: 200
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
            , height: 'full-330'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });

        $.ajax({
            url: '/product/order/selectAllOrderStateNum',
            type: "get",
            success: function (result) {
                data = result.data;
                //赋值
                $("#totalNum").text(data.totalNum);
                $("#cancelNum").text(data.cancelNum);
                $("#needEvaluateNum").text(data.needEvaluateNum);
                $("#needPayNum").text(data.needPayNum);
                $("#needSendNum").text(data.needSendNum);
                $("#successNum").text(data.successNum);
            }
        });

        $('#export').on('click', function () {

            //获取搜索from表单里面的数据
            var startTime = $("#selectStartTime").val();
            var endTime = $("#selectEndTime").val();
            var payNumber = $("#selectPayNumber").val();
            var userName = $("#selectUserName").val();
            var productName = $("#selectProductName").val();

            var payState = $("#selectPayState").val();

            location.href = '/product/order/exportExcel?startTime=' + startTime
                + '&endTime=' + endTime + '&payNumber=' + payNumber + '&userName=' + userName
                + '&productName=' + productName + '&payState=' + payState;
        });

        table.on('tool(table)', function (obj) {
            var data = obj.data;

            var layEvent = obj.event;
            if (layEvent === 'send') {  //发货
                //打开编辑窗口
                layer.open({
                    type: 1,
                    title: '编辑发货信息',
                    content: $('#sendForm'),
                    area: ['400px', '250px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });

                $("#id").val(data.id);

                //监听更新提交
                form.on('submit(sendSubmit)', function (data) {
                    var field = data.field;

                    $.ajax({
                        url: '/product/order/send',
                        type: "post",
                        data: field,
                        dataType: "json",
                        success: function (result) {
                            layer.msg(result.message);
                            layer.closeAll();
                        }
                    });
                });
            }
        });
    });
</script>
</html>
