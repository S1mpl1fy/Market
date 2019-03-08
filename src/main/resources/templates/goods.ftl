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
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/goods">在售商品</a></li>
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
                <dd><a href="/market/msg/list">站内信</a></dd>
                <dd><a href="javascript:void(0)" onclick="logout()">退出登录</a></dd>
            </dl>
        </li>
    </ul>
</div>
<div style="margin-left: 20%; margin-right: 20%;margin-top: 20px;">
    <#if gvm??>
        <#list gvm as gu>
            <div class="layui-card">
            <div class="layui-card-header" style="font-size: 18px;">
            <img src="${gu["user"].headUrl}" style="height: 18px; margin-right: 10px">
            ${gu["goods"].title}
            </div>
            <div class="layui-card-body">
                <div class="layui-row grid-demo">
                    <div class="layui-col-md3" style="height: 80px;">
                        <div class="grid-demo grid-demo-bg1">
                            <img src="${gu["goods"].image}" style="height: 80px">
                        </div>
                    </div>
                    <div class="layui-col-md9" style="height: 80px;" onclick="window.open('http://localhost:8080/market/goods/detail/'+${gu["goods"].id},'_self')">
                        <div class="grid-demo grid-demo-bg2">
                            ${gu["goods"].description}<br>
                        </div>
                    </div>
                    <div class="layui-col-md12 ">
                        <div class="grid-demo grid-demo-bg3" style="margin-top: 20px">
                            <hr/>
                            <#if gu["like"] == 1>
                                <i class="layui-icon layui-icon-praise" style="color: #3399ff;"  id='${gu["goods"].id}'  onclick="like('${gu["goods"].id}')"><span class="layui-badge-rim" id="likeCount${gu["goods"].id}">${gu["goods"].likeCount}</span></i>
                            <#else>
                                <i class="layui-icon layui-icon-praise" id='${gu["goods"].id}'  onclick="like('${gu["goods"].id}')"><span class="layui-badge-rim" id="likeCount${gu["goods"].id}">${gu["goods"].likeCount}</span></i>
                            </#if>
                            <#if gu["like"] == -1>
                                <i class="layui-icon layui-icon-tread" style="color: #3399ff;"  id='dislike${gu["goods"].id}' onclick="dislike('${gu["goods"].id}')"></i>
                            <#else>
                                <i class="layui-icon layui-icon-tread" style="margin-left: 20px"  id='dislike${gu["goods"].id}' onclick="dislike('${gu["goods"].id}')"></i>
                            </#if>
                            <i class="layui-icon layui-icon-reply-fill" style="margin-left: 20px"><span class="layui-badge-rim">${gu["goods"].commentCount}</span></i>
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
    function like(goodsId) {
        var xhr = new XMLHttpRequest();
        var form = new FormData();
        var likeCount = document.getElementById('likeCount' + goodsId);
        var dislike = document.getElementById('dislike' + goodsId);
        var like = document.getElementById('' + goodsId);
        form.append("goodsId", goodsId);
        xhr.open('POST', 'http://localhost:8080/market/like', true);
        xhr.send(form);
        xhr.onreadystatechange = function () {
            if(xhr.readyState === 4 && xhr.status === 200){
                var json = JSON.parse(xhr.responseText);
                like.style.color = '#3399ff';
                dislike.style.color = 'black';
                likeCount.innerText = json.msg;
            }
        }
    }

    function dislike(goodsId) {
        var xhr = new XMLHttpRequest();
        var form = new FormData();
        var like = document.getElementById('' + goodsId);
        var dislike = document.getElementById('dislike' + goodsId);
        var likeCount = document.getElementById('likeCount' + goodsId);
        form.append("goodsId", goodsId);
        xhr.open('POST', 'http://localhost:8080/market/dislike', true);
        xhr.send(form);
        xhr.onreadystatechange = function () {
            if(xhr.readyState === 4 && xhr.status === 200){
                var json = JSON.parse(xhr.responseText);
                dislike.style.color = '#3399ff';
                like.style.color = 'black';
                likeCount.innerText = json.msg;
            }
        }
    }
</script>
</body>
</html>