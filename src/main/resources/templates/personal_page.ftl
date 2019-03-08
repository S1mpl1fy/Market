<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>个人主页</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body style="background-color: #f2f2f2">
<div>
    <ul class="layui-nav">
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/goods">在售商品</a></li>
        <li class="layui-nav-item"><a href="">个人中心</a></li>
        <li class="layui-nav-item"><a href="/market/publish">发布商品</a></li>
        <li class="layui-nav-item">
            <#if user??>
            <a href="/market/homepage"><img src="${user.headUrl}"" class="layui-nav-img">${user.mail}</a>
            <#else >
            <a href="/market/homepage"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">未登录</a>
        </#if>
        <dl class="layui-nav-child">
            <dd><a href="javascript:;">修改信息</a></dd>
            <dd><a href="/market/msg/list">站内信</a></dd>
            <dd><a href="javascript:void(0)" onclick="logout()">退出登录</a></dd>
        </dl>
        </li>
    </ul>
</div>
<#if target??>
<div style="margin-left: 20%; margin-right: 20%;">
    <div class="layui-card" style="margin-top: 20px;">
        <div class="layui-card-header">用户名</div>
        <div class="layui-card-body">
            ${target.name}
        </div>
    </div>
    <div class="layui-card">
        <div class="layui-card-header">邮箱</div>
        <div class="layui-card-body">
            ${target.mail}
        </div>
    </div>
    <div class="layui-card">
        <div class="layui-card-header">性别</div>
        <div class="layui-card-body">
            <#if target.sex== 1 >
            男
            <#else>
            女
            </#if>
        </div>
    </div>
    <button class="layui-btn">私信他</button>
</div>
</#if>

<script src="/static/layui/layui.js"></script>
<script>
    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
    function logout() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET','http://localhost:8080/market/user/logout',true);
        xhr.send(null);
        xhr.onreadystatechange = function () {
            if(xhr.readyState === 4 && xhr.status === 200){
                window.location.reload(true);
            }
        }
    }
</script>
</body>
</html>