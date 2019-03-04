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
        <li class="layui-nav-item"><a href="/market/publish">发布商品</a></li>
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
<div style="margin-left: 20%; margin-right: 20%;margin-top: 20px;">
    <!--
    <div class="layui-card" style="margin-top: 20px;">
        <div class="layui-card-header">商品</div>
        <div class="layui-card-body">
            卡片式面板面板通常用于非白色背景色的主体内<br>
            从而映衬出边框投影
        </div>
    </div>
    -->
    <#if gvm??>
        <#list gvm as gu>
            <div class="layui-card">
            <div class="layui-card-header" style="font-size: 18px;">
            <img src="${gu.user.headUrl}" style="height: 18px; margin-right: 10px">
            ${gu.goods.title}
            </div>
            <div class="layui-card-body" onclick="window.open('http://localhost:8080/market/goods/detail/'+${gu.goods.id},'_self')">
                <div class="layui-row grid-demo">
                    <div class="layui-col-md3" style="height: 80px;">
                        <div class="grid-demo grid-demo-bg1">
                            <img src="${gu.goods.image}" style="height: 80px">
                        </div>
                    </div>
                    <div class="layui-col-md9" style="height: 80px;">
                        <div class="grid-demo grid-demo-bg2">
                            ${gu.goods.description}<br>
                        </div>
                    </div>
                    <div class="layui-col-md12 ">
                        <div class="grid-demo grid-demo-bg3" style="margin-top: 20px">
                            <hr/>
                            <i class="layui-icon layui-icon-praise" ><span class="layui-badge-rim">${gu.goods.likeCount}</span></i>
                            <i class="layui-icon layui-icon-tread" style="margin-left: 20px"><span class="layui-badge-rim">1</span></i>
                            <i class="layui-icon layui-icon-reply-fill" style="margin-left: 20px"><span class="layui-badge-rim">${gu.goods.commentCount}</span></i>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </#list>
    </#if>
</div>




<script src="/static/layui/layui.all.js"></script>
<script>

</script>
</body>
</html>