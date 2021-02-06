<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>拼团规则</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">

        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body">
                    <form class="layui-form" action="" lay-filter="component-form-element">

                        <div class="layui-form-item" style="display: none">
                            <label class="layui-form-label">id</label>
                            <div class="layui-input-block">
                                <input type="text" name="id" placeholder="公司 id" id="id" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">规则</label>
                            <div id="contentEditor" class="layui-input-block">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/wangEditor.min.js"></script>

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

        var E = window.wangEditor;
        var editor = new E('#contentEditor');

        var xhr = new XMLHttpRequest();
        // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
        editor.customConfig.uploadImgServer = url;  // 上传图片到服务器
        //editor1.customConfig.uploadImgServer = 'http://security.yipage.cn/upload/ueditorUpload';
        // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        // 将图片大小限制为 10M
        editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024;
        // 上传图片时刻自定义设置 header
        editor.customConfig.uploadImgHeaders = {
            'Accept': 'application/json;charset=UTF-8',
            'X-Requested-With': 'XMLHttpRequest'
        };
        // 可使用监听函数在上传图片的不同阶段做相应处理
        editor.customConfig.uploadImgHooks = {
            before: function (xhr, editor1, files) {
            },
            success: function (xhr, editor1, result) {
                return {
                    prevent: true,
                    msg: '上传成功~'
                }
            },
            fail: function (xhr, editor1, result) {

                return {
                    prevent: true,
                    msg: '图片插入错误，请重新上传图片！'
                }
            },
            error: function (xhr, editor1) {

                return {
                    prevent: true,
                    msg: '上传失败，请重新上传图片！'
                }
            },
            timeout: function (xhr, editor1) {

                return {
                    prevent: true,
                    msg: '上传超时，请重新上传图片！'
                }
            }
        };
        editor.create();

        $.ajax({
            url: '/lesson/collage/rule/1',
            type: "get",
            success: function (result) {
                //赋值
                $("#id").val(result.data.id);
                editor.txt.html(result.data.content);
            }
        });

        //监听提交
        form.on('submit(updateSubmit)', function (data) {
            var field = data.field;
            data.field.content = editor.txt.html();
            $.ajax({
                url: '/lesson/collage/rule/update',
                type: "post",
                data: field,
                dataType: "json",
                success: function (result) {
                    if (result.code == 200) {
                        layer.msg('更新成功');
                    } else {
                        layer.msg(result.message);
                    }
                }
            });
            return false;
        });


    });

</script>


</body>
</html>
