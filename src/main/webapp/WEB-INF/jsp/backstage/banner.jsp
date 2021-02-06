<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>轮播图管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary], .layui-table-cell .layui-form-radio[lay-skin=primary] {
        top: 0px;
        vertical-align: middle;
    }

    .loadingImg {
        width: 21px;
        height: 21px;
        animation: an 1s steps(12) infinite;
    }

    .loading {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 1000;
    }

    @keyframes an {
        0% {
            -webkit-transform: rotate(0deg);
            transform: rotate(0deg);
        }
        to {
            -webkit-transform: rotate(1turn);
            transform: rotate(1turn);
        }
    }

    .myLabel {
        width: 100px;
    }
</style>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <%--<div class="layui-form layui-card-header layuiadmin-card-header-auto">--%>
                <%--<div class="layui-form-item">--%>
                <%--<div class="layui-inline">--%>
                <%--<div class="layui-input-inline">--%>
                <%--<input type="text" name="companyName" placeholder="请输入商品名称" autocomplete="off"--%>
                <%--class="layui-input">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="layui-inline">--%>
                <%--<button class="layui-btn layuiadmin-btn-useradmin" data-type="reload" lay-submit--%>
                <%--lay-filter="search">--%>
                <%--<i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>--%>
                <%--</button>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
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
<div class="layui-form" lay-filter="layuiadmin-form-useradmin" id="addForm"
     style="padding: 10px 0 0 0;display: none;position: relative">

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">商品分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId1" lay-filter="categoryId1" lay-verify="required">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">商品名称</label>
        <div class="layui-input-inline">
            <select name="typeId" id="typeId1" lay-verify="required">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">轮播图标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" lay-verify="required" placeholder="请输入轮播图标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">商品id</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="productId" lay-verify="required" placeholder="请输入商品id" autocomplete="off"--%>
    <%--class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">小程序轮播图</label>
        <button type="button" class="layui-btn" id="uploadFile1">
            <i class="layui-icon">&#xe67c;</i>上传图片
        </button>
        <span style="color: red">请上传格式为 750*288 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="img" id="img1" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showDiv1">
        <label class="layui-form-label myLabel">图片显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg1" width="100%" height="60%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">PC轮播图</label>
        <button type="button" class="layui-btn" id="uploadFile3">
            <i class="layui-icon">&#xe67c;</i>上传图片
        </button>
        <span style="color: red">请上传格式为 1920 x 500 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="imgpc" id="img3" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showDiv3">
        <label class="layui-form-label myLabel">图片显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg3" width="100%" height="60%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="addSubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>

    <div id="loading" class="loading" style="display: none">
        <img class="loadingImg" src="${pageContext.request.contextPath}/static/img/loading.png"/>
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
        <label class="layui-form-label myLabel">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId2" lay-filter="categoryId2" lay-verify="required">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">商品名称</label>
        <div class="layui-input-inline">
            <select name="typeId" id="typeId2" lay-verify="required">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">轮播图标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" id="title" lay-verify="required" placeholder="请输入轮播图标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">小程序轮播图</label>
        <button type="button" class="layui-btn" id="uploadFile2">
            <i class="layui-icon">&#xe67c;</i>修改图片
        </button>
        <span style="color: red">请上传格式为 750*288 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="img" id="img2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">轮播图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg2" width="100%" height="60%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label myLabel">PC轮播图</label>
        <button type="button" class="layui-btn" id="uploadFile4">
            <i class="layui-icon">&#xe67c;</i>修改图片
        </button>
        <span style="color: red">请上传格式为 1920 x 500 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="imgpc" id="img4" autocomplete="off" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label myLabel">轮播图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg4" width="100%" height="60%"/>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label myLabel">状态</label>
        <div class="layui-input-inline">
            <input type="radio" lay-filter="switch" name="state" title="启用" value="1">
            <input type="radio" lay-filter="switch" name="state" title="禁用" value="2">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
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
<script type="text/html" id="state">
    {{# if(d.state == '1') {d.state = '启用'}   }}
    {{# if(d.state == '2') {d.state = '禁用'}   }}
    {{# if(d.state == null) {d.state = ''}   }}
    <span>{{d.state}}</span>
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
            upload = layui.upload;

        table.render({
            elem: '#table',
            url: '/banner/list?type=1',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'id', title: 'ID'},
                {field: 'title', title: '轮播图标题', minWidth: 200},
                {field: 'typeName', title: '商品名称', minWidth: 200},
                {
                    field: 'img', title: '小程序轮播图',
                    templet: function (d) {
                        if (d.img != '') {
                            return '<div><img src=' + d.img + ' height="100%" width="100%"></div>'
                        } else {
                            return "";
                        }
                    }

                    , minWidth: 200
                },
                {
                    field: 'imgpc', title: 'PC轮播图',
                    templet: function (d) {
                        if (d.img != '') {
                            return '<div><img src=' + d.imgpc + ' height="100%" width="100%"></div>'
                        } else {
                            return "";
                        }
                    }

                    , minWidth: 200
                },
                {field: 'state', title: '状态', templet: '#state'},
                {
                    field: 'createTime',
                    title: '创建时间',
                    templet: '<div>{{layui.util.toDateString(d.createTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'

                    , minWidth: 200
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    templet: '<div>{{layui.util.toDateString(d.updateTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'

                    , minWidth: 200
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
            url: '/product/category/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                data = result.data;
                var html = '<option value="">请选择商品分类</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.name + '</option>';
                });
                $("#categoryId1").html(html);
                $("#categoryId2").html(html);
                form.render('select');
            }
        });

        //监听select改变
        form.on('select(categoryId1)', function (data) {
            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/product/select?categoryId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;
                    var html = '<option value="">请选择商品名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.name + '</option>';
                    });
                    $("#typeId1").html(html);
                    form.render('select');
                }
            });
        });

        form.on('select(categoryId2)', function (data) {
            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/product/select?categoryId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;
                    var html = '<option value="">请选择商品名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.name + '</option>';
                    });
                    $("#typeId2").html(html);
                    form.render('select');
                }
            });
        });

        upload.render({
            elem: '#uploadFile1',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#showDiv1").show();
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
                    $("#img2").val(res.data);
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
                    $("#showDiv3").show();
                    $("#showImg3").attr("src", res.data);
                    $("#img3").val(res.data);
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
                    $("#showImg4").attr("src", res.data);
                    $("#img4").val(res.data);
                }
                layer.closeAll("loading");
            }
        });


        //触发增加事件
        $('#add').on('click', function () {
            //打开编辑窗口
            layer.open({
                type: 1,
                title: '添加轮播图信息',
                content: $('#addForm'),
                area: ['520px', '420px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            data.field.type = 1;
            $.ajax({
                url: '/banner/add',
                type: "post",
                data: field,
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code = 200) {
                        //提示信息
                        layer.msg('添加成功');
                        //刷新数据
                        table.reload('table'); //数据刷新 table id
                        //关闭弹窗
                        layer.closeAll();

                    } else {
                        layer.msg(result.message);
                        //刷新数据
                        table.reload('table'); //数据刷新 table id
                        //关闭弹窗
                        layer.closeAll();
                    }
                }
            });
            return false;
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
                    url: '/banner/deletes/' + ids,
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
                        url: '/banner/deletes/' + data.id,
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
                //打开编辑窗口
                layer.open({
                    type: 1,
                    title: '编辑轮播图信息',
                    content: $('#updateForm'),
                    area: ['520px', '420px'],
                    closeBtn: 1
                });

                $.ajax({
                    url: '/product/detail?id=' + data.typeId,
                    type: "get",
                    success: function (result) {
                        var productCategory = result.data.productCategory;
                        var product = result.data.product;
                        //赋值
                        $("#typeId2").html('<option value="' + product.id + '">' + product.name + '</option>');
                        $("#categoryId2").find("option[value = '" + productCategory.id + "']").attr("selected", "selected");
                        form.render();
                    }
                });

                //赋值
                $("#id").val(data.id);
                $("#title").val(data.title);
                $("#typeId").val(data.typeId);
                $("#img2").val(data.img);
                $("#showImg2").attr("src", data.img);
                $("#img4").val(data.imgpc);
                $("#showImg4").attr("src", data.imgpc);
                $("input:radio[value='" + data.state + "']").attr('checked', 'true');
                form.render('radio');
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/banner/update',
                        type: "post",
                        data: field,
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            if (result.code == 200) {
                                layer.msg('更新成功');
                                //刷新数据
                                table.reload('table');
                                //关闭弹窗
                                layer.closeAll();
                            } else {
                                layer.msg(result.message);
                                //关闭弹窗
                                layer.closeAll();
                            }
                        }
                    });
                    return false;
                });
            }
        });
    });

</script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/qiniu.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/jquery-2.1.1.min.js"></script>

</html>
