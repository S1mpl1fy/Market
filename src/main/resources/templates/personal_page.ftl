<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人主页</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body style="background-color: #f2f2f2">
<div>
    <ul class="layui-nav">
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/goods">在售商品</a></li>
        <li class="layui-nav-item"><a href="/market/user/#{user.id}">个人中心</a></li>
        <li class="layui-nav-item"><a href="/market/publish">发布商品</a></li>
        <li class="layui-nav-item">
            <#if user??>
            <a href="/market/user/#{user.id}"><img src="${user.headUrl}"" class="layui-nav-img">${user.mail}</a>
            <#else >
            <a href="/market/user/#{user.id}"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">未登录</a>
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
        <div class="layui-card-header">个人资料</div>
        <div class="layui-card-body">
            用户名：${target.name}
        </div>
        <div class="layui-card-body">
            邮箱：${target.mail}
        </div>
        <div class="layui-card-body">
            性别：
        <#if target.sex== 1 >
            男
        <#else>
            女
        </#if>
        </div>
    </div>

    <#list goodsList as goods>
        <div class="layui-card">
            <div class="layui-card-header">${goods.getcreatedDate()?string('yyyy-MM-dd HH:mm:ss')}</div>
            <div class="layui-card-body" onclick="window.open('http://localhost:8080/market/goods/detail/'+${goods.id},'_self')">
                ${goods.getDescription()}
                <#if goods.status == 3>
                    <p style="color: darkred;">已售出</p>
                <#else >
                    <button class="layui-btn" onclick="deleteGoods('${goods.id}')">删除</button>
                </#if>
            </div>
        </div>
    </#list>
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
                window.open('http://localhost:8080/market/login','_self');
            }
        }
    }

    function deleteGoods(goodsId) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8080/market/goods/delete/'+goodsId, true);
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