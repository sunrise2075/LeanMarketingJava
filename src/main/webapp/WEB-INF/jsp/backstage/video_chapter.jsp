<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>视频章节管理</title>
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
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="videoName" placeholder="请输入视频名称" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="videoChapterName" placeholder="视频章节名称" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <select name="isHide">
                                <option value="">请选择是否隐藏</option>
                                <option value="1">显示</option>
                                <option value="2">隐藏</option>
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
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId1" lay-filter="categoryId1">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">视频名称</label>
        <div class="layui-input-inline">
            <select name="videoId" id="videoId1">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">章节名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" lay-verify="name" placeholder="请输入视频分类名称"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-inline">
            <input type="text" name="sort" lay-verify="required" placeholder="数字越小排越前" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">简介</label>
        <div class="layui-input-block">
            <textarea name="introduction" placeholder="最多可输入100字" maxlength="100" class="layui-textarea"></textarea>
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
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId2" lay-filter="categoryId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">视频名称</label>
        <div class="layui-input-inline">
            <select name="videoId" id="videoId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">章节名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入视频章节名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-inline">
            <input type="text" name="sort" id="sort" lay-verify="required" placeholder="数字越小排越前" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">简介</label>
        <div class="layui-input-block">
            <textarea name="introduction" id="introduction" placeholder="最多可输入100字" maxlength="100"
                      class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="radio" name="isHide" value="1" title="显示">
            <input type="radio" name="isHide" value="2" title="隐藏">
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
<script type="text/html" id="isHide">
    {{# if(d.isHide == '1') {d.isHide = '显示'}   }}
    {{# if(d.isHide == '2') {d.isHide = '隐藏'}   }}
    <span>{{d.isHide}}</span>
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
            url: '/video/chapter/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'id', title: 'ID'},
                {field: 'videoName', title: '视频名称', minWidth: 200},
                {field: 'name', title: '视频章节名称', minWidth: 200},
                {field: 'sort', title: '排序', minWidth: 200},
                {field: 'introduction', title: '简介', minWidth: 200},
                {field: 'isHide', title: '是否隐藏', templet: '#isHide', minWidth: 200},
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
            , height: 'full-220'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });
        //动态加载下拉框值
        $.ajax({
            url: '/video/category/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                data = result.data;
                var html = '<option value="">请选择视频分类</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.name + '</option>';
                });
                $("#categoryId1").html(html);
                $("#categoryId2").html(html);
                form.render('select');
            }
        });
        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });

        //监听select改变
        form.on('select(categoryId1)', function (data) {
            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/video/select?categoryId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;
                    var html = '<option value="">请选择视频名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.title + '</option>';
                    });
                    $("#videoId1").html(html);
                    form.render('select');
                }
            });
        });

        form.on('select(categoryId2)', function (data) {
            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/video/select?categoryId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;

                    var html = '<option value="">请选择视频名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.title + '</option>';
                    });
                    $("#videoId2").html(html);
                    form.render('select');
                }
            });
        });

        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {

            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加视频分类信息',
                content: $('#addForm'),
                area: ['420px', '450px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            $.ajax({
                url: '/video/chapter/add',
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
                    url: '/video/chapter/deletes/' + ids,
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
                        url: '/video/chapter/deletes/' + data.id,
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
                    title: '编辑视频分类信息',
                    content: $('#updateForm'),
                    area: ['420px', '450px'],
                    closeBtn: 1,
                    end: function () {
                        table.reload('table');
                    }
                });
                $.ajax({
                    url: '/video/chapter/' + data.id,
                    type: "get",
                    success: function (result) {
                        var videoCategory = result.data.videoCategory;
                        var videoChapter = result.data.videoChapter;
                        var video = result.data.video;
                        //赋值
                        $("#id").val(videoChapter.id);
                        $("#sort").val(videoChapter.sort);
                        $("#name").val(videoChapter.name);
                        $("#introduction").val(data.introduction);
                        $("#videoId2").html('<option value="' + videoChapter.videoId + '">' + video.title + '</option>');
                        $("input:radio[value='" + data.isHide + "']").attr('checked', 'true');
                        $("#categoryId2").find("option[value = '" + videoCategory.id + "']").attr("selected", "selected");
                        form.render();
                    }
                });
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/video/chapter/update',
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


