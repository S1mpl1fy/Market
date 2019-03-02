<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>在售商品</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body style="background-color: #f2f2f2">
<div>
    <ul class="layui-nav">
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/index">在售商品</a></li>
        <li class="layui-nav-item"><a href="/market/homepage">个人中心</a></li>
        <li class="layui-nav-item"><a href="">发布商品</a></li>
        <li class="layui-nav-item">
            <#if logout?? && logout == "1">
                <a href="/market/homepage"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">未登录</a>
            <#elseif user??>
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
<div style="margin-left: 20%; margin-right: 20%;">
    <div class="layui-card" style="margin-top: 20px;">
        <div class="layui-card-header">卡片面板1</div>
        <div class="layui-card-body">
            卡片式面板面板通常用于非白色背景色的主体内<br>
            从而映衬出边框投影
        </div>
    </div>
    <div class="layui-card">
        <div class="layui-card-header">卡片面板2</div>
        <div class="layui-card-body">
            反正就是一些商品信息<br>
            你懂得
        </div>
    </div>
</div>


<script src="/static/layui/layui.all.js"></script>

</body>
</html>