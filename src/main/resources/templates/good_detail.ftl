<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>商品详情</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body style="background-color: #f2f2f2">
<div>
    <ul class="layui-nav">
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/index">在售商品</a></li>
        <li class="layui-nav-item"><a href="/market/homepage">个人中心</a></li>
        <li class="layui-nav-item"><a href="/market/publish">发布商品</a></li>
        <li class="layui-nav-item">
            <#if user??>
                <a href="/market/homepage"><img src="${user.headUrl}" class="layui-nav-img">${user.mail}</a>
            <#else>
                <a href="/market/homepage"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">未登录</a>
            </#if>
            <dl class="layui-nav-child">
                <dd><a href="javascript:;">修改信息</a></dd>
                <dd><a href="javascript:;">安全管理</a></dd>
                <dd><a href="/market/user/logout">退出登录</a></dd>
            </dl>
        </li>
    </ul>
</div>

<div class="layui-container" style="margin-left: 20%; margin-right: 20%; margin-top: 20px">
    <div class="layui-card">
        <div class="layui-card-header" style="height: 54px; font-size: 36px" align="center">
            <div style="padding-top: 5px;">
                <img src="${gu.user.headUrl}" style="width: 36px; margin-left: 10px">
                ${gu.goods.title}
            </div>
        </div>
        <div class="layui-card-body" align="center">
            <img src="${gu.goods.image}" style="width: 560px;">
        </div>
        <div class="layui-card-body">
            <hr/>
            <p style="padding: 15px;">${gu.goods.description}
        </div>
        <div class="layui-card-body" style="height:30px">
            <i class="layui-icon layui-icon-praise" style="font-size: 24px; margin-left: 15px"><span class="layui-badge-rim">${gu.goods.likeCount}</span></i>
            <i class="layui-icon layui-icon-tread" style="margin-left: 20px;font-size: 24px"><span class="layui-badge-rim">1</span></i>
            <i class="layui-icon layui-icon-reply-fill" style="margin-left: 20px;font-size: 24px"><span class="layui-badge-rim">${gu.goods.commentCount}</span></i>
        </div>
    </div>
    <div class="layui-card">
        <div class="layui-card-header" style="margin-top: 20px; height: 20px;">
            <img src="http://images.nowcoder.com/head/11t.png"  style="margin-top: 2px; height: 18px;padding-left: 8px">
        </div>
        <div class="layui-card-body" style="height: 24px;">
            <p style="padding-left: 8px; font-size: 16px; margin-top: 4px;">这个真的好吃！！
        </div>
    </div>
</div>


<script src="/static/layui/layui.js"></script>
<script>
    //注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;

        //…
    });
</script>
</body>
</html>