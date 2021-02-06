<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文库管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layuiadmin/style/template.css" media="all">
</head>
<style>
    .layui-form-checkbox span {
        padding: 0 10px;
        height: auto !important;
        font-size: 14px;
        border-radius: 2px 0 0 2px;
        background-color: #d2d2d2;
        color: #fff;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

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
                            <select name="categoryId" id="categoryId3">
                            </select>
                        </div>
                        <div class="layui-input-inline">
                            <select name="fileType">
                                <option value="">请选择文件类型</option>
                                <option value="PPT">PPT</option>
                                <option value="PDF">PDF</option>
                                <option value="WORD">WORD</option>
                                <option value="MP3">MP3</option>
                                <option value="MP4">MP4</option>
                            </select>
                        </div>
                        <div class="layui-inline">
                            <select name="isFree">
                                <option value="">请选择是否免费</option>
                                <option value="1">免费</option>
                                <option value="2">收费</option>
                            </select>
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
        <label class="layui-form-label">文库标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" lay-verify="required" placeholder="请输入文库标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">文件类型</label>
        <div class="layui-input-inline">
            <select name="fileType">
                <option value="">请选择文件类型</option>
                <option value="PPT">PPT</option>
                <option value="PDF">PDF</option>
                <option value="WORD">WORD</option>
                <option value="MP3">MP3</option>
                <option value="MP4">MP4</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId1" name="categoryId" lay-filter="categoryId1">

            </select>
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">文件上传</label>--%>
    <%--<button type="button" class="layui-btn" id="uploadFile">--%>
    <%--<i class="layui-icon">&#xe67c;</i>上传--%>
    <%--</button>--%>
    <%--<div class="layui-input-inline" style="display: none">--%>

    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">文件上传</label>
        <button style="float: left;width: 150px;position: relative" type="button" class="layui-btn">
            <input type="file" id="inputSelect" onchange="qiniuUpload()"
                   style="opacity: 0;width: 100%;height: 100%;position: absolute;top: 0;left: 0">
            上传
        </button>
    </div>

    <div class="layui-form-item" style="display: none">
        <div class="layui-input-inline">
            <input type="text" id="fileSize" name="fileSize" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showFileUrl">
        <label class="layui-form-label">文件链接</label>
        <div class="layui-input-block">
            <input type="text" name="url" id="url1" placeholder="文件链接" lay-verify="required" autocomplete="off"
                   class="layui-input" style="width:300px">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否分享</label>
        <div class="layui-input-inline">
            <select name="isShare" id="isShare1" lay-filter="isShare1">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">标签</label>
        <input type="hidden" name="labels" id="labels">
        <div class="layui-input-block" id="labelList">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showLibrarys">

    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否免费</label>
        <div class="layui-input-inline">
            <select name="isFree" id="isFree1" lay-filter="isFree1">
                <option value="">请选择</option>
                <option value="1">免费</option>
                <option value="2">收费</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showPrice1">
        <label class="layui-form-label">文库价格</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="price1" placeholder="请输入文库价格" autocomplete="off" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item" style="display: none" id="showCodes1">
        <label class="layui-form-label">是否免费</label>
        <input type="hidden" name="codes" id="codes1">
        <div class="layui-input-block">

            <input type='checkbox' name='code' lay-skin='primary' title="初级会员" value="2" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="中级会员" value="3" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="高级会员" value="4" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="初级企业会员" value="5" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="中级企业会员" value="6" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="高级企业会员" value="7" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="代理商" value="8" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="企业总监" value="9" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="企业职员" value="0" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' title="经销商" value="1" lay-filter='filter'/>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">简介</label>
        <div class="layui-input-block">
            <textarea name="introduce" placeholder="请输入简介" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">文库详情</label>
        <div id="libraryEditor1" class="layui-input-block">
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
        <label class="layui-form-label">文库标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" id="title" lay-verify="required" placeholder="请输入文库标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">文件类型</label>
        <div class="layui-input-inline">
            <select name="fileType" id="fileType">
                <option value="">请选择文件类型</option>
                <option value="PPT">PPT</option>
                <option value="PDF">PDF</option>
                <option value="WORD">WORD</option>
                <option value="MP3">MP3</option>
                <option value="MP4">MP4</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select id="categoryId2" name="categoryId" lay-filter="categoryId2">

            </select>
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">文件上传</label>--%>
    <%--<button type="button" class="layui-btn" id="uploadFile2">--%>
    <%--<i class="layui-icon">&#xe67c;</i>修改--%>
    <%--</button>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">文件上传</label>
        <button style="float: left;width: 150px;position: relative" type="button" class="layui-btn">
            <input type="file" id="inputSelect2" onchange="qiniuUpload2()"
                   style="opacity: 0;width: 100%;height: 100%;position: absolute;top: 0;left: 0">
            修改
        </button>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">文件链接</label>
        <div class="layui-input-inline">
            <input type="text" name="url" id="url2" autocomplete="off" class="layui-input" style="width:300px">
        </div>
    </div>

    <div class="layui-form-item" style="display: none">
        <div class="layui-input-inline">
            <input type="text" id="fileSize2" name="fileSize" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否分享</label>
        <div class="layui-input-inline">
            <select name="isShare" id="isShare2" lay-filter="isShare2">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">标签</label>
        <input type="hidden" name="labels" id="labels2">
        <div class="layui-input-block" id="labelList2">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showLibrarys2">

    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否免费</label>
        <div class="layui-input-inline">
            <select name="isFree" id="isFree2" lay-filter="isFree2">
                <option value="">请选择</option>
                <option value="1">免费</option>
                <option value="2">收费</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showPrice2">
        <label class="layui-form-label">文库价格</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="price2" placeholder="请输入文库价格" autocomplete="off" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item" style="display: none" id="showCodes2">
        <label class="layui-form-label">是否免费</label>
        <input type="hidden" name="codes" id="codes2">
        <div class="layui-input-block">

            <input type='checkbox' name='code' lay-skin='primary' id='code2' title="初级会员" value="2"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code3' title="中级会员" value="3"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code4' title="高级会员" value="4"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code5' title="初级企业会员" value="5"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code6' title="中级企业会员" value="6"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code7' title="高级企业会员" value="7"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code8' title="代理商" value="8" lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id="code9" title="企业总监" value="9"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id="code0" title="企业职员" value="0"
                   lay-filter='filter'/>
            <input type='checkbox' name='code' lay-skin='primary' id='code1' title="经销商" value="1" lay-filter='filter'/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否隐藏</label>
        <div class="layui-input-block">
            <input type="radio" name="isHide" value="1" title="显示">
            <input type="radio" name="isHide" value="2" title="隐藏">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">简介</label>
        <div class="layui-input-block">
            <textarea name="introduce" id="introduce" placeholder="请输入简介" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">文库详情</label>
        <div id="libraryEditor2" class="layui-input-block">
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
<script src="${pageContext.request.contextPath}/static/layuiadmin/wangEditor.min.js"></script>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
            class="layui-icon layui-icon-edit"></i></a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
            class="layui-icon layui-icon-delete"></i></a>
</script>
<script type="text/html" id="isFree">
    {{# if(d.isFree == '1') {d.isFree = '免费'}   }}
    {{# if(d.isFree == '2') {d.isFree = '收费'}   }}
    {{# if(d.isFree == null) {d.isFree = ''}   }}
    <span>{{d.isFree}}</span>
</script>
<script type="text/html" id="isShare">
    {{# if(d.isShare == '1') {d.isShare = '是'}   }}
    {{# if(d.isShare == '0') {d.isShare = '否'}   }}
    {{# if(d.isShare == null) {d.isShare = ''}   }}
    <span>{{d.isShare}}</span>
</script>
<script type="text/html" id="isHide">
    {{# if(d.isHide == '1') {d.isHide = '显示'}   }}
    {{# if(d.isHide == '2') {d.isHide = '隐藏'}   }}
    {{# if(d.isHide == null) {d.isHide = ''}   }}
    <span>{{d.isHide}}</span>
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
        var editor1 = new E('#libraryEditor1');

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

        var editor2 = new E('#libraryEditor2');

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
            url: '/library/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'title', title: '文库标题', minWidth: 200},
                {field: 'fileType', title: '文件类型', minWidth: 200},
//                {field: 'lables', title: '标签', minWidth:200},
                {field: 'categoryName', title: '文库分类名称', minWidth: 200},
                {field: 'url', title: '文件链接', minWidth: 200},
                {field: 'price', title: '价格', minWidth: 200},
                {field: 'isShare', title: '是否可以分享', templet: '#isShare', minWidth: 200},
                {field: 'isFree', title: '是否免费', templet: '#isFree', minWidth: 200},
                {field: 'isHide', title: '是否隐藏', templet: '#isHide', minWidth: 200},
                {field: 'introduce', title: '简介', minWidth: 200},
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

//        upload.render({
//            elem: '#uploadFile',
//            url: '/upload/uploadFile',
//            accept: 'file',
//            before:function(obj){
//                layer.load(2,{shade:false});
//            },
//            done: function (res) {
//                if (res.msg = 'ok') {
//                    $("#showFileUrl").show();
//                    $("#url1").val(res.data);
//                    $("#fileSize").val(res.fileSize);
//                }
//                layer.closeAll("loading");
//            }
//        });
//
//        upload.render({
//            elem: '#uploadFile2',
//            url: '/upload/uploadFile',
//            accept: 'file',
//            before:function(obj){
//                layer.load(2,{shade:false});
//            },
//            done: function (res) {
//                if (res.msg = 'ok') {
//                    $("#url2").val(res.data);
//                }
//                layer.closeAll("loading");
//            }
//        });

        //获取所有标签
        $.ajax({
            url: '/label/listAll',
            type: "get",
            success: function (result) {
                data = result.data;
                var html = "";
                var html2 = "";
                $.each(data, function (index, value) {//获取所有key值
                    html += "<input type='checkbox' name='label' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='label' id='label" + value.id + "'/>";
                });
                $.each(data, function (index, value) {//获取所有key值
                    html2 += "<input type='checkbox' name='label' lay-skin='primary'  title='" + value.name + "' value='" + value.id + "' lay-filter='label2' id='label2" + value.id + "'/>";
                });
                $('#labelList').html(html);
                $('#labelList2').html(html2);
                form.render();
            }
        });

        //监听复选框
        form.on('checkbox(label)', function (data) {
            $('#showLibrarys').html("");
            var str = "";
            $("input:checkbox[name='label']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            if (str.length > 0) {
                str = str.substring(0, str.length - 1);

                //发送一个ajax请求
                //获取条件筛选的视频
                $.ajax({
                    url: '/library/getByLabels?labels=' + str,
                    type: "get",
                    success: function (result) {
                        var html = "";
                        var labels = str.split(",")
                        for (var i = 0; i < labels.length; i++) {

                            var html2 = "";
                            var list = result['list' + i];
                            $.each(list, function (index, value) {//获取所有key值
                                html2 += "<input type='checkbox' name='library' lay-skin='primary'  title='" + value.title + "' value='" + value.id + "' lay-filter='library' id='library" + value.id + "'/>";
                            });
                            html = html + '<div class="layui-form-item">' +
                                '<label class="layui-form-label"></label>' +
                                '<div class="layui-input-block">' +
                                html2 +
                                '</div>' +
                                '</div>';
                        }


                        $('#showLibrarys').html(html);
                        form.render();
                    }
                });
                $("#showLibrarys").show();

            } else {
                $("#showLibrarys").hide();
            }
        })

        //监听复选框
        form.on('checkbox(label2)', function (data) {

            var str = "";
            $("input:checkbox[name='label']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            if (str.length > 0) {
                str = str.substring(0, str.length - 1);

                //发送一个ajax请求
                //获取条件筛选的视频
                $.ajax({
                    url: '/library/getByLabels?labels=' + str,
                    type: "get",
                    success: function (result) {
                        var html = "";
                        var labels = str.split(",")
                        for (var i = 0; i < labels.length; i++) {

                            var html2 = "";
                            var list = result['list' + i];
                            $.each(list, function (index, value) {//获取所有key值
                                html2 += "<input type='checkbox' name='library' lay-skin='primary'  title='" + value.title + "' value='" + value.id + "' lay-filter='library' id='library" + value.id + "'/>";
                            });
                            html = html + '<div class="layui-form-item">' +
                                '<label class="layui-form-label"></label>' +
                                '<div class="layui-input-block">' +
                                html2 +
                                '</div>' +
                                '</div>';
                        }

                        $('#showLibrarys2').html(html);
                        form.render();
                    }
                });
                $("#showLibrarys2").show();

            } else {
                $("#showLibrarys2").hide();
            }
        });

        //动态加载下拉框值
        $.ajax({
            url: '/library/category/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                data = result.data;
                var html = '<option value="">请选择文库分类</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.categoryName + '</option>';
                });
                $("#categoryId1").html(html);
                $("#categoryId2").html(html);
                $("#categoryId3").html(html);
                form.render('select');
            }
        });


        //触发增加事件
        $('#add').on('click', function () {
            //打开编辑窗口
            layer.open({
                type: 1,
                title: '添加文库信息',
                content: $('#addForm'),
                area: ['1000px', '600px'],
                closeBtn: 1,
                end: function () {
                    window.location.reload();
                }
            });
        });
        //监听select改变
        form.on('select(isFree1)', function (data) {
            var value = data.value;

            if (value == 1) {  //免费
                //隐藏
                $("#showPrice1").hide();
                $("#showCodes1").hide();
            }
            if (value == 2) { //收费
                //显示
                $("#showPrice1").show();
                $("#showCodes1").show();
                $("#price1").val("");
            }
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            data.field.content = editor1.txt.html();

            if (field.isFree == 1) {
                field.price = null;
            } else {
                var str = "[";
                $("input:checkbox[name='code']:checked").each(function () {

                    var val = $(this).val();
                    str = str + '"' + val + '",';

                });
                str = str.substring(0, str.length - 1);
                field.codes = str + "]";
            }

            var str = "";
            $("input:checkbox[name='label']:checked").each(function (i) {

                var val = $(this).val();
                str = str + val + ",";

            });
            if (str.length > 0) {
                field.labels = str.substring(0, str.length - 1);
            } else {
                field.labels = "";
            }

            var libraryStr = "";
            $("input:checkbox[name='library']:checked").each(function (i) {

                var val = $(this).val();
                libraryStr = libraryStr + val + ",";

            });
            if (libraryStr.length > 0) {
                field.librarys = libraryStr.substring(0, libraryStr.length - 1);
            } else {
                field.librarys = "";
            }

            $.ajax({
                url: '/library/add',
                type: "post",
                data: field,
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result.code = 200) {
                        //提示信息
                        layer.msg('添加成功');
                        //刷新数据
                        table.reload('table');
                        //关闭弹窗
                        layer.closeAll();

                    } else {
                        layer.msg(result.message);
                        //刷新数据
                        table.reload('table');
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
                    url: '/library/deletes/' + ids,
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
                        url: '/library/deletes/' + data.id,
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
                    title: '编辑文库信息',
                    content: $('#updateForm'),
                    area: ['1000px', '600px'],
                    closeBtn: 1,
                    end: function () {
                        window.location.reload();
                    }
                });
                //赋值
                $("#id").val(data.id);
                $("#fileSize2").val(data.fileSize);
                $("#title").val(data.title);
                $("#url2").val(data.url);
                editor2.txt.html(data.content);
                $("#introduce").val(data.introduce);
                $("input:radio[value='" + data.isHide + "']").attr('checked', 'true');
                $("#fileType").find("option[value = '" + data.fileType + "']").attr("selected", "selected");
                $("#isShare2").find("option[value = '" + data.isShare + "']").attr("selected", "selected");
                $("#isFree2").find("option[value = '" + data.isFree + "']").attr("selected", "selected");
                $("#categoryId2").find("option[value = '" + data.categoryId + "']").attr("selected", "selected");
                form.render();
                var labels = data.labels;
                if (labels != null) {
                    var label = labels.split(",");
                    for (var i = 0; i < label.length; i++) {

                        $("#label2" + label[i]).prop('checked', true);
                        $("#label2" + label[i]).addClass('layui-form-checked');
                    }
                }
                form.render();

                var librarys = data.librarys;

                if (librarys != null && librarys.length > 0) {
                    //发送一个ajax请求
                    //获取条件筛选的视频
                    $.ajax({
                        url: '/library/getByLabels?labels=' + labels,
                        type: "get",
                        success: function (result) {
                            var html = "";
                            //var labels = labels.split(",");
                            for (var i = 0; i < Object.keys(result).length; i++) {

                                var html2 = "";
                                var list = result['list' + i];
                                $.each(list, function (index, value) {//获取所有key值
                                    html2 += "<input type='checkbox' name='library' lay-skin='primary'  title='" + value.title + "' value='" + value.id + "' lay-filter='library2' id='library2" + value.id + "'/>";
                                });

                                html = html + '<div class="layui-form-item">' +
                                    '<label class="layui-form-label"></label>' +
                                    '<div class="layui-input-block">' +
                                    html2 +
                                    '</div>' +
                                    '</div>';
                            }

                            $('#libraryList2').html(html);

                            form.render();
                            var library = librarys.split(",");
                            for (var i = 0; i < library.length; i++) {

                                $("#library2" + library[i]).prop('checked', true);
                                $("#library2" + library[i]).addClass('layui-form-checked');
                            }
                            form.render();
                            $("#showLibrarys2").show();
                        }
                    });
                } else {
                    $("#showLibrarys2").hide();
                }

                if (data.isFree == 2) {
                    //显示
                    $("#showPrice2").show();
                    $("#showCodes2").show();
                    $("#price2").val(data.price);


                    var codes = data.codes;


                    if (codes != null) {
                        var code = JSON.parse(codes);
                        for (var i = 0; i < code.length; i++) {
                            $("#code" + code[i]).prop('checked', true);
                            $("#code" + code[i]).addClass('layui-form-checked');
                            form.render();
                        }
                    }
                } else {
                    //隐藏
                    $("#showPrice2").hide();
                    $("#showCodes2").hide();
                }

                //监听select改变
                form.on('select(isFree2)', function (data) {
                    var value = data.value;

                    if (value == 1) {  //免费
                        //隐藏
                        $("#showPrice2").hide();
                    }
                    if (value == 2) { //收费
                        //显示
                        $("#showPrice2").show();
                        $("#price2").val("");
                    }
                });
                //监听更新提交
                form.on('submit(updateSubmit)', function (data) {
                    var field = data.field;
                    data.field.content = editor2.txt.html();
                    if (field.isFree == 1) {
                        field.price = null;
                    } else {
                        var str = "[";
                        $("input:checkbox[name='code']:checked").each(function () {

                            var val = $(this).val();
                            str = str + '"' + val + '",';

                        });
                        str = str.substring(0, str.length - 1);
                        field.codes = str + "]";
                    }

                    var str = "";
                    $("input:checkbox[name='label']:checked").each(function (i) {

                        var val = $(this).val();
                        str = str + val + ",";

                    });
                    if (str.length > 0) {
                        field.labels = str.substring(0, str.length - 1);
                    } else {
                        field.labels = "";
                    }
                    var libraryStr = "";
                    $("input:checkbox[name='library']:checked").each(function (i) {

                        var val = $(this).val();
                        libraryStr = libraryStr + val + ",";

                    });
                    if (libraryStr.length > 0) {
                        field.librarys = libraryStr.substring(0, libraryStr.length - 1);
                    } else {
                        field.librarys = "";
                    }

                    $.ajax({
                        url: '/library/update',
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
                        $("#url1").val(url);
                        $("#fileSize").val(file.size);

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
                        $("#fileSize2").val(file.size);
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
