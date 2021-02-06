<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>视频内容管理</title>
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


    <%--<div class="layui-form-item layui-form-text">--%>
    <%--<label class="layui-form-label">视频章节id</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="chapterId" placeholder="请输入视频章节id" autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

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
            <select id="videoId1" lay-filter="videoId1">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">图片</label>
        <button type="button" class="layui-btn" id="uploadFile3">
            <i class="layui-icon">&#xe67c;</i>上传
        </button>
        <span style="color: red">请上传格式为 282*150 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="img" id="img1" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showImgDiv1">
        <label class="layui-form-label">封面图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg1" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">章节名称</label>
        <div class="layui-input-inline">
            <select name="chapterId" id="chapterId1">

            </select>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">内容名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" placeholder="请输入视频内容名称" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-inline">
            <input type="text" name="sort" lay-verify="required" placeholder="数字越小排越前" autocomplete="off"
                   class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">视频上传</label>
        <button style="float: left;width: 150px;position: relative" type="button" class="layui-btn">
            <input type="file" id="inputSelect" onchange="qiniuUpload()"
                   style="opacity: 0;width: 100%;height: 100%;position: absolute;top: 0;left: 0">
            上传
        </button>

        <span style="padding: 9px 0px; color: red;display: block;">请上传格式为mp4的文件</span>
    </div>

    <div class="layui-form-item" style="display: none" id="showFileUrl">
        <label class="layui-form-label">视频链接</label>
        <div class="layui-input-inline">
            <input type="text" name="url" id="url1" autocomplete="off" class="layui-input" style="width:300px">
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
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId2" lay-filter="categoryId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">视频名称</label>
        <div class="layui-input-inline">
            <select id="videoId2" lay-filter="videoId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">图片</label>
        <button type="button" class="layui-btn" id="uploadFile4">
            <i class="layui-icon">&#xe67c;</i>修改
        </button>
        <span style="color: red">请上传格式为 282*150 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="img" id="img2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">封面图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showImg2" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">章节名称</label>
        <div class="layui-input-inline">
            <select name="chapterId" id="chapterId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">内容标题</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" placeholder="请输入视频内容标题" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-inline">
            <input type="text" name="sort" id="sort" lay-verify="required" placeholder="数字越小排越前" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">文件上传</label>--%>
    <%--<button type="button" class="layui-btn" id="uploadFile2">--%>
    <%--<i class="layui-icon">&#xe67c;</i>修改--%>
    <%--</button>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">视频上传</label>
        <button style="float: left;width: 150px;position: relative" type="button" class="layui-btn">
            <input type="file" id="inputSelect2" onchange="qiniuUpload2()"
                   style="opacity: 0;width: 100%;height: 100%;position: absolute;top: 0;left: 0">
            修改
        </button>
        <span style="padding: 9px 0px; color: red;display: block;">请上传格式为mp4的文件</span>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">文件链接</label>
        <div class="layui-input-inline">
            <input type="text" name="url" id="url2" autocomplete="off" class="layui-input" style="width:300px">
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
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="updateSubmit">立即提交</button>
        </div>
    </div>

    <div id="loading2" class="loading" style="display: none">
        <img class="loadingImg" src="${pageContext.request.contextPath}/static/img/loading.png"/>
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
<script type="text/html" id="getImg">
    <img src="{{d.img}}" height="100%" width="100%">
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

        table.render({
            elem: '#table',
            url: '/video/contents/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'videoTitle', title: '视频名称', minWidth: 200},
                {field: 'img', title: '图片', minWidth: 200, template: '#getImg'},
                {field: 'videoChapterName', title: '视频章节名称', minWidth: 200},
                {field: 'name', title: '视频内容名称', minWidth: 200},
                {field: 'url', title: '视频链接', minWidth: 200},
                {field: 'sort', title: '排序', minWidth: 200},
                {field: 'introduction', title: '简介', minWidth: 200},
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

        upload.render({
            elem: '#uploadFile3',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {
                    $("#showImgDiv1").show();
                    $("#img1").val(res.data);
                    $("#showImg1").attr("src", res.data);
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

                    $("#img2").val(res.data);
                    $("#showImg2").attr("src", res.data);
                }
                layer.closeAll("loading");
            }
        });

        //监听select(categoryId1)改变
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
                    $("#chapterId1").html("");
                    form.render('select');
                }
            });
        });
        //监听select(categoryId2)改变
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
                    $("#chapterId2").html("");
                    form.render('select');
                }
            });
        });

        //监听select(videoId1)改变
        form.on('select(videoId1)', function (data) {
            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/video/chapter/select?videoId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;

                    var html = '<option value="">请选择视频名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.name + '</option>';
                    });
                    $("#chapterId1").html(html);
                    form.render('select');
                }
            });
        });
        //监听select(videoId2)改变
        form.on('select(videoId2)', function (data) {
            var value = data.value;
            //动态加载下拉框值
            $.ajax({
                url: '/video/chapter/select?videoId=' + value,
                type: "get",
                dataType: "json",
                success: function (result) {
                    data = result.data;
                    var html = '<option value="">请选择视频章节名称</option>';
                    $.each(data, function (index, value) {
                        html += '<option value="' + value.id + '">' + value.name + '</option>';
                    });
                    $("#chapterId2").html(html);
                    form.render('select');
                }
            });
        });

//        upload.render({
//            elem: '#uploadFile',
//            url: '/upload/uploadFile',
//            accept: 'file',
//            before: function (obj) {
//                layer.load(2, {shade: false});
//            },
//            done: function (res) {
//                if (res.msg = 'ok') {
//                    $("#showFileUrl").show();
//                    $("#url1").val(res.data);
//                }
//                layer.closeAll("loading");
//            }
//        });
//
//        upload.render({
//            elem: '#uploadFile2',
//            url: '/upload/uploadFile',
//            accept: 'file',
//            before: function (obj) {
//                layer.load(2, {shade: false});
//            },
//            done: function (res) {
//                if (res.msg = 'ok') {
//                    $("#url2").val(res.data);
//                }
//                layer.closeAll("loading");
//            }
//
//        });

        var index = 0;
        //触发增加事件
        $('#add').on('click', function () {
            //打开编辑窗口
            index = layer.open({
                type: 1,
                title: '添加视频目录信息',
                content: $('#addForm'),
                area: ['420px', '600px'],
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
                url: '/video/contents/add',
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
                    url: '/video/contents/deletes/' + ids,
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
                        url: '/video/contents/deletes/' + data.id,
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
                var index2 = layer.open({
                    type: 1,
                    title: '编辑视频内容信息',
                    content: $('#updateForm'),
                    area: ['450px', '620px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });
                $.ajax({
                    url: '/video/contents/' + data.id,
                    type: "get",
                    success: function (result) {

                        var videoContents = result.data.videoContents;
                        var videoChapter = result.data.chapter;
                        var video = result.data.video;
                        var videoCategory = result.data.videoCategory;

                        //赋值
                        $("#categoryId2").find("option[value = '" + videoCategory.id + "']").attr("selected", "selected");
                        $("#videoId2").html('<option value="' + video.id + '">' + video.title + '</option>');
                        $("#chapterId2").html('<option value="' + videoChapter.id + '">' + videoChapter.name + '</option>');
                        //$("input:radio[value='"+data.isHide+"']").attr('checked','true');
                        form.render("select");
                    }
                });

                $("#id").val(data.id);
                $("#sort").val(data.sort);
                $("#url2").val(data.url);
                $("#name").val(data.name);
                $("#chapterId").val(data.chapterId);
                $("#introduction").val(data.introduction);
                $("#showImg2").attr("src", data.img);
                $("#img2").val(data.img);

                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    $.ajax({
                        url: '/video/contents/update',
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

<script src="${pageContext.request.contextPath}/static/layuiadmin/qiniu.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layuiadmin/jquery-2.1.1.min.js"></script>
<script>
    var UploadToken = "";

    function getUploadToken() {

        $.ajax({
            url: '/upload/getToken',
            type: "get",
            success: function (result) {
                document.getElementById("loading").style.display = "block";
                UploadToken = result.token;
                var file = document.getElementById("inputSelect").files[0];
                var fileName = file.name;

                var fileNameSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length);
                var baseUrl = "https://qiniu.leanmarketing.cn/";//显示图片的最根七牛云地址，到时候会换掉
                var key = new Date().getTime() + fileNameSuffix;
                var putExtra = {
                    fname: "",
                    params: {},
                    mimeType: null
                };
                var config = {
                    useCdnDomain: true,
                    region: qiniu.region.z2
                };
                var observable = qiniu.upload(file, key, UploadToken, putExtra, config);
                var observer = {
                    next(res) {

                    },
                    error(err) {
                    },
                    complete(res) {

                        //拼凑地址
                        var url = baseUrl + key;
                        document.getElementById("loading").style.display = "none";
                        $("#showFileUrl").show();
                        $("#url1").val(baseUrl + key);
                    }
                };
                var subscription = observable.subscribe(observer); // 上传开始
            }
        });
    }

    function getUploadToken2() {

        $.ajax({
            url: '/upload/getToken',
            type: "get",
            success: function (result) {
                document.getElementById("loading2").style.display = "block";
                UploadToken = result.token;
                var file = document.getElementById("inputSelect2").files[0];
                var fileName = file.name;

                var fileNameSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length);
                if (fileNameSuffix != (".mp4")) {
                    layer.msg("只能上传文件名以.mp4的结尾的文件");
                    document.getElementById("loading2").style.display = "none";
                    //layer.closeAll();
                    return false;
                }
                var baseUrl = "https://qiniu.leanmarketing.cn/";//显示图片的最根七牛云地址，到时候会换掉
                var key = new Date().getTime() + fileNameSuffix;
                var putExtra = {
                    fname: "",
                    params: {},
                    mimeType: null
                };
                var config = {
                    useCdnDomain: true,
                    region: qiniu.region.z2
                };
                var observable = qiniu.upload(file, key, UploadToken, putExtra, config);
                var observer = {
                    next(res) {

                    },
                    error(err) {
                    },
                    complete(res) {
                        //拼凑地址
                        var url = baseUrl + key;

                        document.getElementById("loading2").style.display = "none";
                        $("#url2").val(baseUrl + key);
                    }
                };
                var subscription = observable.subscribe(observer); // 上传开始
            }
        });
    }

    //开始上传图片
    function qiniuUpload() {
        getUploadToken();
    }

    function qiniuUpload2() {
        getUploadToken2();
    }
</script>

</html>
