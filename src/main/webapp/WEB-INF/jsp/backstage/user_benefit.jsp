<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员权益管理</title>
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
                <div class="layui-card-body">
                    <table class="layui-hide" id="table" lay-filter="table">
                        <div style="padding-bottom: 10px;">
                            <button class="layui-btn" id="deletes">批量删除</button>
                            <button class="layui-btn" id="add">添加</button>
                        </div>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%--添加表单--%>
<div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="addForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <label class="layui-form-label">权益名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" placeholder="请输入会员权益名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权益类型</label>
        <div class="layui-input-inline">
            <select name="type" lay-verify="required" id="type1" lay-filter="type1">
                <option value="">请选择会员权益类型</option>
                <option value="1">个人会员</option>
                <option value="2">企业会员</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="halfYearPrice1">
        <label class="layui-form-label">半年价格</label>
        <div class="layui-input-inline">
            <input type="text" name="halfYearPrice" placeholder="请输入半年的价格" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="yearPrice1">
        <label class="layui-form-label">一年价格</label>
        <div class="layui-input-inline">
            <input type="text" name="yearPrice" lay-verify="required" placeholder="请输入一年的价格" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showDirectorNum1">
        <label class="layui-form-label">总监数量</label>
        <div class="layui-input-inline">
            <input type="text" name="directorNum" id="directorNum1" placeholder="请输入总监数量" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showClerkNum1">
        <label class="layui-form-label">职员数量</label>
        <div class="layui-input-inline">
            <input type="text" name="clerkNum" id="clerkNum1" placeholder="请输入职员数量" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">权益说明</label>
        <div class="layui-input-block">
            <textarea name="introduction" lay-verify="required" placeholder="请输入会员权益说明"
                      class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addSubmit">立即提交</button>
        </div>
    </div>
</div>
<%--更新表单--%>
<div class="layui-form" id="updateForm" style="padding: 10px 0 0 0;display: none">

    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" id="id" name="id" autocomplete="off" class="layui-input" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权益名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入会员权益名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权益类型</label>
        <div class="layui-input-inline">
            <select name="type" lay-verify="required" id="type2" lay-filter="type2">
                <option value="">请选择会员权益类型</option>
                <option value="1">个人会员</option>
                <option value="2">企业会员</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="halfYearPrice2">
        <label class="layui-form-label">半年价格</label>
        <div class="layui-input-inline">
            <input type="text" name="halfYearPrice" id="halfYearPrice" placeholder="请输入半年的价格" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="yearPrice2">
        <label class="layui-form-label">一年价格</label>
        <div class="layui-input-inline">
            <input type="text" name="yearPrice" id="yearPrice" lay-verify="required" placeholder="请输入一年的价格"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showDirectorNum2">
        <label class="layui-form-label">总监数量</label>
        <div class="layui-input-inline">
            <input type="text" name="directorNum" id="directorNum2" placeholder="请输入总监数量" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showClerkNum2">
        <label class="layui-form-label">职员数量</label>
        <div class="layui-input-inline">
            <input type="text" name="clerkNum" id="clerkNum2" placeholder="请输入职员数量" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">权益说明</label>
        <div class="layui-input-block">
            <textarea name="introduction" id="introduction" placeholder="请输入会员权益说明" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
            class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
            class="layui-icon layui-icon-delete"></i></a>
</script>
<script type="text/html" id="type">
    {{# if(d.type == 1) {d.type = '个人会员'}   }}
    {{# if(d.type == 2) {d.type = '企业会员'}   }}
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
            form = layui.form;

        table.render({
            elem: '#table',
            url: '/user/benefit/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'name', title: '会员权益名称', minWidth: 200},
                {field: 'type', title: '会员权益类型', templet: '#type', minWidth: 200},
                {field: 'halfYearPrice', title: '半年价格', minWidth: 200},
                {field: 'yearPrice', title: '一年价格', minWidth: 200},
                {field: 'directorNum', title: '总监数量', minWidth: 200},
                {field: 'clerkNum', title: '职员数量', minWidth: 200},
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
                {title: '操作', align: 'center', fixed: 'right', toolbar: '#toolbar'}
            ]]
            , page: true
            , limit: 10
            , limits: [10, 15, 20, 25, 30]
            , height: 'full-220',
            text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });

        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {

            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加会员权益信息',
                content: $('#addForm'),
                area: ['450px', '320px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });
        //监听下拉框的选择
        form.on('select(type1)', function (data) {
            var value = data.value;
            if (value == 1) {  //个人会员
                $("#halfYearPrice1").show();
                $("#yearPrice1").show();
                $("#showDirectorNum1").hide();
                $("#showClerkNum1").hide();
                $("#directorNum1").val(null);
                $("#clerkNum1").val(null);
            }
            if (value == 2) { //企业会员
                $("#yearPrice1").show();
                $("#halfYearPrice1").hide();
                $("#showDirectorNum1").show();
                $("#showClerkNum1").show();

            }
        }),

            //监听添加提交
            form.on('submit(addSubmit)', function (data) {
                var field = data.field;
                if (field.type == 2) {
                    field.halfYearPrice = null;
                } else {
                    field.directorNum = null;
                    field.clerkNum = null;
                }

                $.ajax({
                    url: '/user/benefit/add',
                    type: "post",
                    data: field,
                    dataType: "json",
                    async: false,
                    success: function (result) {

                        layer.msg(result.message, {
                            icon: 1,
                            time: 1000,
                            end: function () {
                                //关闭弹窗
                                layer.close(index);
                            }
                        });
                    }
                });
            });

        //触发批量删除事件
        $('#deletes').on('click', function () {
            var checkStatus = table.checkStatus('table');
            var checkData = checkStatus.data; //得到选中的数据
            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            layer.confirm('确定要删除信息', function (index) {
                var ids = '';
                for (var i = 0; i < checkData.length; i++) {
                    ids += checkData[i].id + ",";
                }
                ids = ids.substring(0, ids.length - 1);
                $.ajax({
                    url: '/user/benefit/deletes/' + ids,
                    type: "get",
                    success: function (result) {
                        if (result.code == 200) {
                            layer.msg('已删除', {time: 1000});
                            table.reload('table');
                        } else {
                            layer.msg('删除失败', {time: 1000});
                        }
                    }
                });
                layer.close(index);
            });
        });

        //监听工具条
        table.on('tool(table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'del') {  //删除
                layer.confirm('真的删除行么', function (index) {
                    //向服务端发送删除指令
                    $.ajax({
                        url: '/user/benefit/deletes/' + data.id,
                        type: "get",
                        success: function (result) {
                            if (result.code == 200) {
                                layer.msg('已删除');
                                table.reload('table');
                            } else {
                                layer.msg('删除失败');
                            }
                        }
                    });
                    layer.close(index);
                });
            } else if (layEvent === 'edit') { //编辑
                var index2 = 0;                //打开编辑窗口
                index2 = layer.open({
                    type: 1,
                    title: '编辑会员权益信息',
                    content: $('#updateForm'),
                    area: ['450px', '320px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });

                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#introduction").val(data.introduction);
                $("#halfYearPrice").val(data.halfYearPrice);
                $("#yearPrice").val(data.yearPrice);
                if (data.type == 1) {
                    $("#halfYearPrice2").show();
                    $("#yearPrice2").show();
                } else {
                    $("#yearPrice2").show();
                    $("#showClerkNum2").show();
                    $("#showDirectorNum2").show();
                    $("#clerkNum2").val(data.clerkNum);
                    $("#directorNum2").val(data.directorNum);
                }
                $("#type2").find("option[value = '" + data.type + "']").attr("selected", "selected");
                form.render('select');
                //监听下拉框的选择
                form.on('select(type2)', function (data) {
                    var value = data.value;
                    if (value == 1) {  //个人会员
                        $("#halfYearPrice2").show();
                        $("#yearPrice2").show();
                    }
                    if (value == 2) { //企业会员
                        $("#yearPrice2").show();
                        $("#halfYearPrice2").hide();
                    }
                    if (value == 1) {  //个人会员
                        $("#halfYearPrice2").show();
                        $("#yearPrice2").show();
                        $("#showDirectorNum2").hide();
                        $("#showClerkNum2").hide();
                    }
                    if (value == 2) { //企业会员
                        $("#yearPrice2").show();
                        $("#halfYearPrice2").hide();
                        $("#showDirectorNum2").show();
                        $("#showClerkNum2").show();
                    }
                }),
                    //监听更新提交
                    form.on('submit(updateSubmit)', function (data) {
                        var field = data.field;
                        if (field.type == 2) {
                            field.halfYearPrice = null;
                        } else {
                            field.directorNum = null;
                            field.clerkNum = null;
                        }
                        $.ajax({
                            url: '/user/benefit/update',
                            type: "post",
                            data: field,
                            dataType: "json",
                            async: false,
                            success: function (result) {
                                layer.msg(result.message, {
                                    icon: 1,
                                    time: 1000,
                                    end: function () {
                                        //关闭弹窗
                                        layer.close(index2);
                                    }
                                });
                            }
                        });
                        return false;
                    });
            }
        });
    });
</script>


</html>

