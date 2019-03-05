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
    <div class="layui-card" style="margin-top: 20px">
        <div class="layui-card-header">
            <p>评论</p>
        </div>
        <div class="layui-card-body">
            <textarea name="description" placeholder="请输入评论内容" class="layui-textarea" id="comment"></textarea>
        </div>
        <div class="layui-card-body" align="right">
            <button class="layui-btn" onclick="submit()">提交评论</button>
        </div>
    </div>

    <#list cus as cu>
        <#if cu??>
            <div class="layui-card" style="margin-top: 20px">
            <div class="layui-card-body">
            <div class="layui-row layui-col-space1">
            <div class="layui-col-md1">
                <div class="layui-row grid-demo">
                    <img src="${cu.userHeadUrl}" height="60px">
                </div>
            </div>
            <div class="layui-col-md11">
            <div class="layui-row grid-demo grid-demo-bg1">
            <div class="layui-col-md2" style="color:  #7a7a52;">
            ${cu.userName}
            </div>
            <div class="layui-col-md9" style="color:  #7a7a52;">
                ${cu.date?string('yyyy-MM-dd HH:mm:ss')}
            </div>
            <div class="layui-col-md11" style="padding-top: 12px">
                ${cu.content}
            </div>
            </div>
            </div>
            </div>
            </div>
            </div>

        </#if>
    </#list>
</div>

<div style="margin-bottom: 60px">
</div>


<script src="/static/layui/layui.js"></script>
<script>
    //注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
    var userId = ${user.id},  goodsId = ${gu.goods.id};
    layui.use('element', function(){
        var element = layui.element;

        //…
    });

    function submit() {
        var content  = document.getElementById('comment').value;
        var temp = new FormData();
        temp.append("userId",userId);
        temp.append("goodsId",goodsId);
        temp.append("content", content);
        console.log(temp);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/market/comment/submit', true);
        xhr.send(temp);
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4 && xhr.status == 200){
                window.location.reload(true);
            }
        }
        //window.open('http://localhost:8080/market/goods/detail/' + goodsId, '_self');
        return false;
    }
</script>
</body>
</html>