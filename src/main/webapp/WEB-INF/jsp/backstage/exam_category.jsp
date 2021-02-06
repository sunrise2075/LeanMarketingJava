<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>试题分类管理</title>
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
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" lay-verify="name" placeholder="请输入试题分类名称"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类描述</label>
        <div class="layui-input-inline">
            <input type="text" name="description" lay-verify="required" lay-verify="name" placeholder="请输入分类描述"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类图标志</label>
        <button type="button" class="layui-btn" id="uploadFile">
            <i class="layui-icon">&#xe67c;</i>上传图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="img" autocomplete="off" class="layui-input" id="img1">
        </div>
    </div>

    <div class="layui-form-item" style="display: none;" id="show1">
        <label class="layui-form-label">图片显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg1" width="100%" height="60%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类图标志2</label>
        <button type="button" class="layui-btn" id="uploadFile3">
            <i class="layui-icon">&#xe67c;</i>上传图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" autocomplete="off" class="layui-input" id="coverImg1">
        </div>
    </div>

    <div class="layui-form-item" style="display: none;" id="showCover1">
        <label class="layui-form-label">图片显示</label>
        <div class="layui-input-inline">
            <img src="" id="showCoverImg1" width="100%" height="60%"/>
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
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入试题分类名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类描述</label>
        <div class="layui-input-inline">
            <input type="text" name="description" id="description" lay-verify="required" lay-verify="name"
                   placeholder="请输入分类描述" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <button type="button" class="layui-btn" id="uploadFile2">
            <i class="layui-icon">&#xe67c;</i>修改图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="img" id="img" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类标志图</label>
        <div class="layui-input-inline">
            <img src="" id="showImg2" width="100%" height="60%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <button type="button" class="layui-btn" id="uploadFile4">
            <i class="layui-icon">&#xe67c;</i>修改图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" id="coverImg2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类标志图2</label>
        <div class="layui-input-inline">
            <img src="" id="showCoverImg2" width="100%" height="60%"/>
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
<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/lay/modules/layim.js"></script>
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
            upload = layui.upload,
            form = layui.form;

        table.render({
            elem: '#table',
            url: '/exam/category/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'name', title: '试题分类名称', minWidth: 200},
                {field: 'description', title: '视频分类描述', minWidth: 200},
                {
                    field: 'img', title: '分类图标志',
                    templet: function (d) {
                        if (d.img != '') {
                            return '<div><img src=' + d.img + ' height="100%" width="100%"></div>'
                        } else {
                            return "";
                        }
                    }
                },
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
                {title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#toolbar'}
            ]]
            , page: true
            , limit: 10
            , limits: [10, 15, 20, 25, 30]
            , height: 'full-220'
            , text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });

        upload.render({
            elem: '#uploadFile',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#show1").show();
                    $("#showImg1").attr("src", res.data);
                    $("#img1").val(res.data);
                }
                layer.closeAll("loading");
            }
        });

        upload.render({
            elem: '#uploadFile2',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {

                    $("#showImg2").attr("src", res.data);
                    $("#img").val(res.data);
                }
                layer.closeAll("loading");
            }
        });

        upload.render({
            elem: '#uploadFile3',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#showCover1").show();
                    $("#showCoverImg1").attr("src", res.data);
                    $("#coverImg1").val(res.data);
                }
                layer.closeAll("loading");
            }
        });

        upload.render({
            elem: '#uploadFile4',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {

                    $("#showCoverImg2").attr("src", res.data);
                    $("#coverImg2").val(res.data);
                }
                layer.closeAll("loading");
            }
        });

        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {

            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加试题分类信息',
                content: $('#addForm'),
                area: ['400px', '600px'],
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
                url: '/exam/category/add',
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
                    url: '/exam/category/deletes/' + ids,
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
                        url: '/exam/category/deletes/' + data.id,
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
                    title: '编辑试题分类信息',
                    content: $('#updateForm'),
                    area: ['400px', '600px'],
                    closeBtn: 1,
                    end: function () {
                        table.reload('table');
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#name").val(data.name);
                $("#description").val(data.description);
                $("#img").val(data.img);
                $("#showImg2").attr("src", data.img);
                $("#showCoverImg2").attr("src", data.coverImg);
                $("input:radio[value='" + data.isHide + "']").attr('checked', 'true');
                form.render('radio');
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/exam/category/update',
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


