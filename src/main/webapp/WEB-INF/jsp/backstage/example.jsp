<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>案例管理</title>
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
        <label class="layui-form-label">案例名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" placeholder="请输入案例名称" maxlength="20" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">案例标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" lay-verify="required" placeholder="请输入案例标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">案例简介</label>
        <div class="layui-input-block">
            <textarea name="introduction" lay-verify="required" placeholder="请输入案例简介" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">封面图</label>
        <button type="button" class="layui-btn" id="uploadFile">
            <i class="layui-icon">&#xe67c;</i>上传图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" autocomplete="off" class="layui-input" id="img1">
        </div>
    </div>

    <div class="layui-form-item" style="display: none;" id="show1">
        <label class="layui-form-label">图片显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg1" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">案例内容</label>
        <div id="contentEditor1" class="layui-input-block">
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
        <label class="layui-form-label">案例名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入案例名称" maxlength="20"
                   autocomplete="off" class="layui-input">

        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">案例标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" id="title" lay-verify="required" placeholder="请输入案例标题" autocomplete="off"
                   class="layui-input">

        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">案例简介</label>
        <div class="layui-input-block">
            <textarea name="introduction" id="introduction" lay-verify="required" placeholder="请输入案例简介"
                      class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">修改</label>
        <button type="button" class="layui-btn" id="uploadFile2">
            <i class="layui-icon">&#xe67c;</i>修改图片
        </button>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" id="coverImg" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">案例封面图</label>
        <div class="layui-input-inline">
            <img src="" id="showImg2" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">案例内容</label>
        <div id="contentEditor2" class="layui-input-block">
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
<script src="${pageContext.request.contextPath}/static/layuiadmin/wangEditor.min.js"></script>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
            class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
            class="layui-icon layui-icon-delete"></i></a>
</script>
<script>
    //上传图片到服务器的地址
    var url = 'https://pc.leanmarketing.cn/upload/ueditorUpload';
    //var url = 'http://localhost:10000/upload/ueditorUpload';
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

        // 富文本编辑器（1是新增，2是修改）
        var E = window.wangEditor;
        var editor1 = new E('#contentEditor1');

        var xhr = new XMLHttpRequest();
        // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
        editor1.customConfig.uploadImgServer = url;  // 上传图片到服务器
        //editor1.customConfig.uploadImgServer = 'http://security.yipage.cn/upload/ueditorUpload';
        // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        // 将图片大小限制为 10M
        editor1.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        // 上传图片时刻自定义设置 header
        editor1.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        // 可使用监听函数在上传图片的不同阶段做相应处理
        editor1.customConfig.uploadImgHooks = {
            before: function (xhr, editor1, files) {
                // 图片上传之前触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

                // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
                // return {
                //     prevent: true,
                //     msg: '放弃上传！'
                // }
            },
            success: function (xhr, editor1, result) {
                // 图片上传并返回结果，图片插入成功之后触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果

                //editor1.txt.append('<img src="' + result.data + '" style="max-width:100%;"/>');
                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor1, result) {
                // 图片上传并返回结果，但图片插入错误时触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor1) {
                // 图片上传出错时触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor1) {
                // 图片上传超时时触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };

        var editor2 = new E('#contentEditor2');

        editor2.customConfig.uploadImgServer = url;  // 上传图片到服务器
        //editor2.customConfig.uploadImgServer = 'http://security.yipage.cn/upload/ueditorUpload'
        // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        // 将图片大小限制为 10M
        editor2.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        // 上传图片时刻自定义设置 header
        editor2.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        // 可使用监听函数在上传图片的不同阶段做相应处理
        editor2.customConfig.uploadImgHooks = {
            before: function (xhr, editor2, files) {
                // 图片上传之前触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

                // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
                // return {
                //     prevent: true,
                //     msg: '放弃上传！'
                // }
            },
            success: function (xhr, editor2, result) {

                //editor1.txt.append('<img src="' + result.data + '" style="max-width:100%;"/>');
                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor2, result) {
                // 图片上传并返回结果，但图片插入错误时触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor2) {
                // 图片上传出错时触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor2) {
                // 图片上传超时时触发
                // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };
        editor1.create();
        editor2.create();


        table.render({
            elem: '#table',
            url: '/example/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'id', title: 'ID', minWidth: 200},
                {field: 'title', title: '案例名称', minWidth: 200},
                {field: 'title', title: '案例标题', minWidth: 200},
                {field: 'introduction', title: '案例简介', minWidth: 200},
                {
                    field: 'headPortrait', title: '案例封面图',
                    templet: function (d) {
                        if (d.coverImg != '') {
                            return '<div><img src=' + d.coverImg + ' height="100%" width="100%"></div>'
                        } else {
                            return "";
                        }
                    }
                    , minWidth: 200
                },
                {field: 'content', title: '案例内容', minWidth: 200},
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

        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
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
                    $("#coverImg").val(res.data);
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
                title: '添加案例信息',
                content: $('#addForm'),
                area: ['800px', '600px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            data.field.content = editor1.txt.html();
            $.ajax({
                url: '/example/add',
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
                    url: '/example/deletes/' + ids,
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
                        url: '/example/deletes/' + data.id,
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
                var index2 = 0;
                index2 = layer.open({
                    type: 1,
                    title: '编辑案例信息',
                    content: $('#updateForm'),
                    area: ['800px', '600px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#introduction").val(data.introduction);
                editor2.txt.html(data.content);
                $("#name").val(data.name);
                $("#title").val(data.title);
                $("#content").val(data.content);
                $("#coverImg").val(data.coverImg);
                $("#showImg2").attr("src", data.coverImg);

                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    data.field.content = editor2.txt.html();
                    $.ajax({
                        url: '/example/update',
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
