<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品管理</title>
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
                            <select name="categoryId" id="categoryId3">
                            </select>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="authorName" id="selectAuthorName" placeholder="请输入作者名称"
                                       autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <%--<div class="layui-inline">--%>
                        <%--<select name="isFree">--%>
                        <%--<option value="">请选择是否免费</option>--%>
                        <%--<option value="1">免费</option>--%>
                        <%--<option value="2">收费</option>--%>
                        <%--</select>--%>
                        <%--</div>--%>
                        <div class="layui-inline">
                            <select name="state" id="selectState">
                                <option value="">商品状态</option>
                                <option value="1">上架</option>
                                <option value="2">下架</option>
                            </select>
                        </div>

                        <div class="layui-inline">
                            <select name="isHide" id="selectIsHide">
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
                            <button class="layui-btn" id="export">导出商品信息</button>
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
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" lay-verify="required" placeholder="请输入商品名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" lay-verify="required" placeholder="请输入商品标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select name="categoryId" id="categoryId1" lay-filter="categoryId">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品简介</label>
        <div class="layui-input-block">
            <input type="text" name="introduction" lay-verify="required" placeholder="请输入商品简介" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品封面图</label>
        <button type="button" class="layui-btn" id="uploadFile1">
            <i class="layui-icon">&#xe67c;</i>上传
        </button>
        <span style="color: red">请上传固定格式的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" id="coverImg1" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showCover1">
        <label class="layui-form-label">封面图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showCoverImg1" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">首页推荐图</label>
        <button type="button" class="layui-btn" id="uploadFile7">
            <i class="layui-icon">&#xe67c;</i>上传
        </button>
        <span style="color: red">请上传格式为300 *600 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="recommendImg" id="recommendImg1" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showRecommendImgDiv1">
        <label class="layui-form-label">推荐图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showRecommendImg1" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-upload">
        <label class="layui-form-label">产品详情图</label>
        <button type="button" class="layui-btn" id="test2">多图片上传</button>
        <span style="color: red">请上传格式为 1200 * 高不限 的图片</span>
        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;margin-left:120px">
            预览图：
            <div class="layui-upload-list" id="demo2"></div>
        </blockquote>
        <div class="layui-input-inline" style="display: none;" id="detailPicture">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" lay-verify="required" placeholder="请输入地址" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">作者名称</label>
        <div class="layui-input-inline">
            <input type="text" name="authorName" lay-verify="required" placeholder="请输入作者名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">作者头像</label>
        <button type="button" class="layui-btn" id="uploadFile3">
            <i class="layui-icon">&#xe67c;</i>上传
        </button>
        <span style="color: red">请上传格式为 130*130 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="authorImg" id="authorImg1" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" style="display: none" id="showAuthor1">
        <label class="layui-form-label">头像显示</label>
        <div class="layui-input-inline">
            <img src="" id="showAuthorImg1" width="100%" height="20%"/>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">作者简介</label>
        <div class="layui-input-block">
            <textarea name="authorIntroduce" placeholder="请输入作者简介" class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">出版社</label>
        <div class="layui-input-inline">
            <input type="text" name="publishingHouse" lay-verify="required" placeholder="请输入出版社" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否套装</label>
        <div class="layui-input-inline">
            <select name="suit" id="suit1" lay-filter="suit1">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">国际标准号</label>
        <div class="layui-input-inline">
            <input type="text" name="bookNumber" lay-verify="required" placeholder="请输入国际标准书号" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">出版时间</label>
        <div class="layui-input-inline">
            <input type="text" name="publishTime" id="publishTime1" lay-verify="required" placeholder="请输入出版时间"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">页数</label>
        <div class="layui-input-inline">
            <input type="text" name="pageNumber" lay-verify="required" placeholder="请输入页数" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">字数</label>
        <div class="layui-input-inline">
            <input type="text" name="wordNumber" lay-verify="required" placeholder="请输入字数" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">是否免费</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<select name="isFree" id="isFree1" lay-filter="isFree1">--%>
    <%--<option value="">请选择</option>--%>
    <%--<option value="1">免费</option>--%>
    <%--<option value="2">收费</option>--%>
    <%--</select>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="layui-form-item"  style="display: none" id="showPrice1">--%>
    <%--<label class="layui-form-label">文库价格</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="price" id="price1"  placeholder="请输入文库价格" autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">商品价格</label>
        <div class="layui-input-inline">
            <input type="text" name="price" placeholder="请输入商品价格" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品描述</label>
        <div id="descriptionEditor1" class="layui-input-block">
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
        <label class="layui-form-label">商品名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" lay-verify="required" placeholder="请输入商品名称" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品标题</label>
        <div class="layui-input-inline">
            <input type="text" name="title" id="title" lay-verify="required" placeholder="请输入商品标题" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">分类名称</label>
        <div class="layui-input-inline">
            <select name="categoryId" id="categoryId2" lay-filter="categoryId2">

            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品简介</label>
        <div class="layui-input-block">
            <input type="text" name="introduction" id="introduction" lay-verify="required" placeholder="请输入商品简介"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">商品封面图</label>
        <button type="button" class="layui-btn" id="uploadFile2">
            <i class="layui-icon">&#xe67c;</i>修改
        </button>
        <span style="color: red">请上传固定格式的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="coverImg" id="coverImg2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">封面图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showCoverImg2" width="100%" height="20%"/>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">首页推荐图</label>
        <button type="button" class="layui-btn" id="uploadFile8">
            <i class="layui-icon">&#xe67c;</i>修改
        </button>
        <span style="color: red">请上传格式为300*600的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="recommendImg" id="recommendImg2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">推荐图显示</label>
        <div class="layui-input-inline">
            <img src="" id="showRecommendImg2" width="100%" height="20%"/>
        </div>
    </div>


    <div class="layui-upload">
        <label class="layui-form-label">产品详情图</label>
        <button type="button" class="layui-btn" id="test22">多图片上传</button>
        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;margin-left:120px">
            预览图：
            <div class="layui-upload-list" style="display: flex;flex-wrap: wrap;" id="demo22"></div>
        </blockquote>
        <div class="layui-input-inline" style="display: none;" id="detailPicture2">
            <%-- <input type="text" name="detailPicture" placeholder="产品详情图" lay-verify="required"  autocomplete="off" class="layui-input">--%>
        </div>
        <div class="layui-input-inline" style="display: none;">
            <input type="text" name="detailPictures" placeholder="产品详情图" autocomplete="off" class="layui-input" id="dp">
        </div>
    </div>

    <div class="layui-form-item" style="display: none">
        <label class="layui-form-label"></label>
        <button type="button" class="layui-btn" id="uploadPicture3">
            <i class="layui-icon">&#xe67c;</i>修改多文件上传
        </button>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">地址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" id="address" lay-verify="required" placeholder="请输入地址" autocomplete="off"
                   class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">作者名称</label>
        <div class="layui-input-inline">
            <input type="text" name="authorName" id="authorName" lay-verify="required" placeholder="请输入作者名称"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">作者头像</label>
        <button type="button" class="layui-btn" id="uploadFile4">
            <i class="layui-icon">&#xe67c;</i>修改
        </button>
        <span style="color: red">请上传格式为 130*130 的图片</span>
        <div class="layui-input-inline" style="display: none">
            <input type="text" name="authorImg" id="authorImg2" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">头像显示</label>
        <div class="layui-input-inline">
            <img src="" id="showAuthorImg2" width="100%" height="20%"/>
        </div>
    </div>


    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">作者简介</label>
        <div class="layui-input-block">
            <textarea name="authorIntroduce" id="authorIntroduce" placeholder="请输入作者简介"
                      class="layui-textarea"></textarea>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">出版社</label>
        <div class="layui-input-inline">
            <input type="text" name="publishingHouse" id="publishingHouse" lay-verify="required" placeholder="请输入出版社"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">出版时间</label>
        <div class="layui-input-inline">
            <input type="text" name="publishTime" id="publishTime2" lay-verify="required" placeholder="请输入出版时间"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">是否套装</label>
        <div class="layui-input-inline">
            <select name="suit" id="suit2" lay-filter="suit2">
                <option value="">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">国际标准号</label>
        <div class="layui-input-inline">
            <input type="text" name="bookNumber" id="bookNumber" lay-verify="required" placeholder="请输入国际标准书号"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">页数</label>
        <div class="layui-input-inline">
            <input type="text" name="pageNumber" id="pageNumber" lay-verify="required" placeholder="请输入页数"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">字数</label>
        <div class="layui-input-inline">
            <input type="text" name="wordNumber" id="wordNumber" lay-verify="required" placeholder="请输入字数"
                   autocomplete="off" class="layui-input">
        </div>
    </div>


    <%--<div class="layui-form-item">--%>
    <%--<label class="layui-form-label">是否免费</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<select name="isFree" id="isFree2" lay-filter="isFree2">--%>
    <%--<option value="">请选择</option>--%>
    <%--<option value="1">免费</option>--%>
    <%--<option value="2">收费</option>--%>
    <%--</select>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--<div class="layui-form-item"  style="display: none" id="showPrice2">--%>
    <%--<label class="layui-form-label">视频价格</label>--%>
    <%--<div class="layui-input-inline">--%>
    <%--<input type="text" name="price" id="price2"  placeholder="请输入视频价格" autocomplete="off" class="layui-input">--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">商品价格</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="price" placeholder="请输入商品价格" autocomplete="off" class="layui-input">
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
        <label class="layui-form-label">商品描述</label>
        <div id="descriptionEditor2" class="layui-input-block">
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

<script type="text/html" id="isFree">
    {{# if(d.isFree == '1') {d.isFree = '免费'}   }}
    {{# if(d.isFree == '2') {d.isFree = '收费'}   }}
    {{# if(d.isFree == null) {d.isFree = ''}   }}
    <span>{{d.isFree}}</span>
</script>
<script type="text/html" id="isHide">
    {{# if(d.isHide == '1') {d.isHide = '显示'}   }}
    {{# if(d.isHide == '2') {d.isHide = '隐藏'}   }}
    {{# if(d.isHide == null) {d.isHide = ''}   }}
    <span>{{d.isHide}}</span>
</script>
<script type="text/html" id="state">
    {{# if(d.state == '1') {d.state = '上架 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="down"><i class="layui-icon ">下架</i></a>'}   }}
    {{# if(d.state == '2') {d.state = '下架 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="up"><i class="layui-icon ">上架</i></a>'}   }}
    {{# if(d.state == null) {d.state = ''}   }}
    <span>{{d.state}}</span>
</script>
<script type="text/html" id="suit">
    {{# if(d.isHide == '1') {d.isHide = '是'}   }}
    {{# if(d.isHide == '2') {d.isHide = '否'}   }}
    {{# if(d.isHide == null) {d.isHide = ''}   }}
    <span>{{d.isHide}}</span>
</script>

<script type="text/html" id="coverImg">
    <img src="{{d.coverImg}}" height="100%" width="100%">
</script>
<script type="text/html" id="authorImg">
    <img src="{{d.authorImg}}" height="100%" width="100%">
</script>

<script type="text/html" id="recommendImg">
    <img src="{{d.recommendImg}}" height="100%" width="100%">
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
            laydate = layui.laydate,
            upload = layui.upload;

        // 富文本编辑器（1是新增，2是修改）
        var E = window.wangEditor;
        var editor1 = new E('#descriptionEditor1');

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

        var editor2 = new E('#descriptionEditor2');

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
            url: '/product/list',
            method: "get",
            cols: [[
                {type: 'checkbox', fixed: 'left', align: 'center'},
                {field: 'name', title: '商品名称', minWidth: 200},
                {field: 'title', title: '商品标题', minWidth: 200},
                {field: 'categoryName', title: '商品分类名称', minWidth: 200},
                {field: 'introduction', title: '商品简介', minWidth: 200},
                {field: 'recommendImg', title: '首页推荐图', templet: '#recommendImg', minWidth: 200},
                {field: 'coverImg', title: '商品封面图', templet: '#coverImg', minWidth: 200},
//                {field: 'isFree', title: '是否免费', templet:'#isFree',width:100},
                {field: 'price', title: '价格', minWidth: 200},
                {field: 'isHide', title: '是否隐藏', templet: '#isHide', minWidth: 200},
                {field: 'suit', title: '是否套装', templet: '#suit', minWidth: 200},
                {field: 'state', title: '状态', templet: '#state', minWidth: 200},
                {field: 'address', title: '地址', minWidth: 200},
                {field: 'authorName', title: '作者名称', minWidth: 200},
                {field: 'authorImg', title: '作者头像', templet: '#authorImg', minWidth: 200},
                {field: 'authorIntroduce', title: '作者简介', minWidth: 200},
                {field: 'publishingHouse', title: '出版社', minWidth: 200},

                {field: 'bookNumber', title: '国际标准书号', minWidth: 200},
                {field: 'pageNumber', title: '页数', minWidth: 200},
                {field: 'wordNumber', title: '字数', minWidth: 200},
                {field: 'salesNum', title: '销售量', minWidth: 200},
                {field: 'description', title: '商品描述', minWidth: 200},
                {field: 'publishTime', title: '出版时间', minWidth: 200},
                {
                    field: 'createTime',
                    title: '创建时间',
                    minWidth: 200,
                    templet: '<div>{{layui.util.toDateString(d.createTime, \'yyyy-MM-dd HH:mm:ss\')}}</div>'
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    minWidth: 200,
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
        //监听搜索
        form.on('submit(search)', function (data) {
            var field = data.field;
            //执行重载
            table.reload('table', {
                where: field
            });
        });

        //修改多文件上传的时候
        var uploadInst3 = upload.render({
            elem: '#uploadPicture3',
            url: '/upload/uploadFile',
            accept: 'file',

            done: function (res) {
                if (res.msg = 'ok') {
                    $("#pictures" + a).attr("src", res.data);
                    $("#detailPictures" + a).val(res.data);

                    var detailPicture2 = document.getElementById("detailPicture2");//找到div
                    var inputs = detailPicture2.getElementsByTagName("input");
                    var val = "";
                    for (var i = 0; i < inputs.length; i++) {
                        val = val + inputs[i].value + ",";
                    }
                    if (val.length > 1) {
                        val = val.substring(0, val.length - 1);
                    }
                    $("#dp").val(val);
                }
            }
        });

        var j = 0;
        //多图片上传
        upload.render({
            elem: '#test2'
            , url: '/upload/uploadFile'
            , multiple: true
            , before: function (obj) {

                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {

                    $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img" height="50%" width="50%">')
                });
            }
            , done: function (res) {
                //上传完毕
                $("#detailPicture").append('<input type="text" name="detailPicture[' + j + ']" value="' + res.data + '" class="layui-input">');
                j++;
            }
        });

        //多图片上传
        upload.render({
            elem: '#test22'
            , url: '/upload/uploadFile'
            , multiple: true
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                var imgsLength = document.getElementById("demo22").getElementsByTagName("img").length;
                obj.preview(function (index, file, result) {
                    $('#demo22').append(
                        '<div id="child' + imgsLength + '" style="position: relative;width: 50%; height: 300px;" >' +
                        '<div onclick="delPicture(' + imgsLength + ')" style="position: absolute;top: 0;right: 0;background: bisque;z-index: 9999; width: 30px;height: 30px"  ><i class="layui-icon layui-icon-close" style="font-size: 30px; color: #1E9FFF;"></i></div>' +
                        '<div onclick="changePicture(' + imgsLength + ')" style="position: absolute;top: 0;right: 31px;background: bisque;z-index: 9999; width: 30px;height: 30px"  ><i class="layui-icon layui-icon-edit" style="font-size: 30px; color: #1E9FFF;"></i></div>' +
                        '<img  src="' + result + '" alt="' + file.name + '" id="pictures' + imgsLength + '"  onclick="changePicture(' + imgsLength + ')"  class="layui-upload-img" width="100%" height="100%"></div> ');
                });
                length++;
            }
            , done: function (res) {
                var imgsLength = document.getElementById("demo22").getElementsByTagName("img").length - 1;
                //上传完毕
                $("#detailPicture2").append('<input type="text" name="detailPicture[' + imgsLength + ']" id="detailPictures' + imgsLength + '" value="' + res.data + '" class="layui-input">');
                var detailPicture2 = document.getElementById("detailPicture2");//找到div
                var inputs = detailPicture2.getElementsByTagName("input");
                var val = "";
                for (var i = 0; i < inputs.length; i++) {
                    val = val + inputs[i].value + ",";
                }
                if (val.length > 1) {
                    val = val.substring(0, val.length - 1);
                }
                $("#dp").val(val);
            }
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#publishTime1',
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#publishTime2',
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
                    $("#showCover1").show();
                    $("#coverImg1").val(res.data);
                    $("#showCoverImg1").attr("src", res.data);
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

                    $("#coverImg2").val(res.data);
                    $("#showCoverImg2").attr("src", res.data);
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

                    $("#showAuthor1").show();
                    $("#authorImg1").val(res.data);
                    $("#showAuthorImg1").attr("src", res.data);
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
                    $("#authorImg2").val(res.data);
                    $("#showAuthorImg2").attr("src", res.data);
                }
                layer.closeAll("loading");
            }
        });

        upload.render({
            elem: '#uploadFile7',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {

                    $("#showRecommendImgDiv1").show();
                    $("#recommendImg1").val(res.data);
                    $("#showRecommendImg1").attr("src", res.data);
                }
                layer.closeAll("loading");
            }
        });

        upload.render({
            elem: '#uploadFile8',
            url: '/upload/uploadFile',
            accept: 'file',
            before: function (obj) {
                layer.load(2, {shade: false});
            },
            done: function (res) {
                if (res.msg = 'ok') {

                    $("#recommendImg2").val(res.data);
                    $("#showRecommendImg2").attr("src", res.data);
                }
                layer.closeAll("loading");
            }
        });
        //导出商品
        $('#export').on('click', function () {

            //获取搜索from表单里面的数据
            var state = $("#selectState").val();
            var authorName = $("#selectAuthorName").val();
            var isHide = $("#selectIsHide").val();
            var categoryId = $("#categoryId3").val();

            location.href = '/product/exportExcel?authorName=' + authorName + '&isHide=' + isHide + '&categoryId=' + categoryId + '&state=' + state;
        });

        //动态加载下拉框值
        $.ajax({
            url: '/product/category/listAll',
            type: "get",
            dataType: "json",
            success: function (result) {
                data = result.data;
                var html = '<option value="">请选择文库分类</option>';
                $.each(data, function (index, value) {
                    html += '<option value="' + value.id + '">' + value.name + '</option>';
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
            }
            if (value == 2) { //收费
                //显示
                $("#showPrice1").show();
                $("#price1").val("");
            }
        });

        //监听添加提交
        form.on('submit(addSubmit)', function (data) {
            var field = data.field;
            data.field.description = editor1.txt.html();
            var detailPicture = document.getElementById("detailPicture");//找到div
            var inputs = detailPicture.getElementsByTagName('input');//找到这个div里面所有的img
            var val = "";
            for (var i = 0; i < inputs.length; i++) {

                val = val + inputs[i].value + ',';
            }
            val = val.substring(0, val.length - 1);

            field.bannerImg = val;

            $.ajax({
                url: '/product/add',
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
                    url: '/product/deletes/' + ids,
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
                        url: '/product/deletes/' + data.id,
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
            } else if (layEvent === 'up') { //上架
                layer.confirm('您真的要上架这个商品吗', function (index) {
                    //向服务端发送删除指令
                    $.ajax({
                        url: '/product/update?id=' + data.id + '&state=1',
                        type: "post",
                        success: function (result) {
                            if (result.code == 200) {
                                layer.msg('上架成功');
                                table.reload('table');
                            } else {
                                layer.msg('上架失败');
                            }
                        }
                    });
                    layer.close(index);
                });

            } else if (layEvent === 'down') { //下架
                layer.confirm('您真的要下架这个商品吗', function (index) {
                    $.ajax({
                        url: '/product/update?id=' + data.id + '&state=2',
                        type: "post",
                        success: function (result) {
                            if (result.code == 200) {
                                layer.msg('下架成功');
                                table.reload('table');
                            } else {
                                layer.msg('下架失败');
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
                $("#price").val(data.price);
                $("#introduction").val(data.introduction);
                editor2.txt.html(data.description);
                $("#bookNumber").val(data.bookNumber);
                $("#title").val(data.title);
                $("#address").val(data.address);
                $("#authorName").val(data.authorName);
                $("#recommendImg2").val(data.recommendImg);
                $("#coverImg2").val(data.coverImg);
                $("#authorImg2").val(data.authorImg);
                $("#authorIntroduce").val(data.authorIntroduce);
                $("#publishingHouse").val(data.publishingHouse);
                $("#publishTime2").val(data.publishTime);
                $("#bookNumber").val(data.bookNumber);
                $("#pageNumber").val(data.pageNumber);
                $("#wordNumber").val(data.wordNumber);
                $("#description").val(data.description);
                $("#name").val(data.name);
                $("#showRecommendImg2").attr("src", data.recommendImg);
                $("#showAuthorImg2").attr("src", data.authorImg);
                $("#showCoverImg2").attr("src", data.coverImg);
                $("#dp").val(data.bannerImg);
                $("input:radio[value='" + data.isHide + "']").attr('checked', 'true');
                $("#suit2").find("option[value = '" + data.suit + "']").attr("selected", "selected");
                $("#isFree2").find("option[value = '" + data.isFree + "']").attr("selected", "selected");
                $("#categoryId2").find("option[value = '" + data.categoryId + "']").attr("selected", "selected");
                form.render('radio');
                form.render('select');
                if (data.isFree == 2) {
                    //显示
                    $("#showPrice2").show();
                    $("#price2").val(data.price);
                } else {
                    //隐藏
                    $("#showPrice2").hide();
                }

                if (data.bannerImg != '') {
                    var pictures = data.bannerImg.split(",");
                    for (var i = 0; i < pictures.length; i++) {
                        //
                        $('#demo22').append(
                            '<div id="child' + i + '" style="position: relative;width: 50%;height: 300px;" >' +
                            '<div onclick="delPicture(' + i + ')" style="position: absolute;top: 0;right: 0;background: bisque;z-index: 9999; width: 30px;height: 30px"  ><i class="layui-icon layui-icon-close" style="font-size: 30px; color: #1E9FFF;"></i> </div>' +
                            '<div onclick="changePicture(' + i + ')" style="position: absolute;top: 0;right: 31px;background: bisque;z-index: 9999; width: 30px;height: 30px"  ><i class="layui-icon layui-icon-edit" style="font-size: 30px; color: #1E9FFF;"></i> </div>' +
                            '<img  src="' + pictures[i] + '" alt="' + pictures[i] + '" id="pictures' + i + '" onclick="changePicture(' + i + ')"  class="layui-upload-img" width="100%" height="100%" >' +
                            '</div> ');

                        $("#detailPicture2").append('<input type="text" name="detailPicture[' + i + ']" id="detailPictures' + i + '"  value="' + pictures[i] + '" class="layui-input">');

                        length++;
                    }
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
                    data.field.description = editor2.txt.html();
                    var value = document.getElementById("dp").value;

                    field.bannerImg = value;

                    if (field.isFree == 1) {
                        field.price = null;
                    }
                    $.ajax({
                        url: '/product/update',
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

    function changePicture(i) {
        document.getElementById("uploadPicture3").click();
        a = i;
    }

    function delPicture(i) {

        var parent = document.getElementById("demo22");
        var child = document.getElementById("child" + i);
        parent.removeChild(child);

        var parent2 = document.getElementById("detailPicture2");
        var child2 = document.getElementById("detailPictures" + i);
        parent2.removeChild(child2);

        var pictureLength = document.getElementById("detailPicture2").getElementsByTagName("input").length;


        var detailPicture2 = document.getElementById("detailPicture2");//找到div
        var inputs = detailPicture2.getElementsByTagName("input");
        var val = "";
        for (var i = 0; i < inputs.length; i++) {
            val = val + inputs[i].value + ",";
        }
        if (val.length > 1) {
            val = val.substring(0, val.length - 1);
        }
        document.getElementById("dp").value = val;
    }


</script>

</html>
