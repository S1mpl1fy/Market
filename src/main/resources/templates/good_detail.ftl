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
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/goods">在售商品</a></li>
        <li class="layui-nav-item"><a href="/market/user/#{user.id}">个人中心</a></li>
        <li class="layui-nav-item"><a href="/market/publish">发布商品</a></li>
        <li class="layui-nav-item">
            <#if user??>
                <a href="/market/user/#{user.id}"><img src="${user.headUrl}" class="layui-nav-img">${user.mail}</a>
            <#else>
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
        <div class="layui-card-body" style=" font-size: 24px">
            <hr/>
            <p>Price: ${gu.goods.price}</p><br>
            <p>${gu.goods.description}
        </div>
        <div class="layui-card-body" style="height:30px">
            <#if gu.status == 1>
                <i class="layui-icon layui-icon-praise" style="color: #3399ff; font-size: 24px"  id='${gu.goods.id}'  onclick="like('${gu.goods.id}')"><span class="layui-badge-rim" id="likeCount${gu.goods.id}">${gu.goods.likeCount}</span></i>
            <#else>
                <i class="layui-icon layui-icon-praise" style=" font-size: 24px" id='${gu.goods.id}'  onclick="like('${gu.goods.id}')"><span class="layui-badge-rim" id="likeCount${gu.goods.id}">${gu.goods.likeCount}</span></i>
            </#if>
            <#if gu.status == -1>
                <i class="layui-icon layui-icon-tread" style="color: #3399ff; font-size: 24px"  id='dislike${gu.goods.id}' onclick="dislike('${gu.goods.id}')"></i>
            <#else>
                <i class="layui-icon layui-icon-tread" style="margin-left: 20px; font-size: 24px"  id='dislike${gu.goods.id}' onclick="dislike('${gu.goods.id}')"></i>
            </#if>
            <i class="layui-icon layui-icon-reply-fill" style="margin-left: 20px;font-size: 24px"><span class="layui-badge-rim">${gu.goods.commentCount}</span></i>
            <#if user.id != gu.user.id>
                <button class="layui-btn" style="margin-left: 20px;" onclick="deal('${gu.goods.id}')">我要拍下</button>
            </#if>
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
                                <img src="${cu.userHeadUrl}" height="60px" >
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
            if(xhr.readyState === 4 && xhr.status === 200){
                window.location.reload(true);
            }
        };
        //window.open('http://localhost:8080/market/goods/detail/' + goodsId, '_self');
        return false;
    }

    function logout() {
        var xhr = new XMLHttpRequest();
        xhr.open('GET','http://localhost:8080/market/user/logout',true);
        xhr.send(null);
        xhr.onreadystatechange = function () {
            if(xhr.readyState === 4 && xhr.status === 200){
                window.open('http://locahost:8080/market/login','_self');
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

    function deal(goodsId) {
        var temp = new FormData();
        temp.append("userId",userId);
        temp.append("goodsId",goodsId);
        console.log(temp);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/market/goods/deal', true);
        xhr.send(temp);
        xhr.onreadystatechange = function(){
            if(xhr.readyState === 4 && xhr.status === 200){
                var json = JSON.parse(xhr.responseText);
                window.open('http://localhost:8080/market/msg/detail/'+json.msg, '_self');
            }
        };
        //window.open('http://localhost:8080/market/goods/detail/' + goodsId, '_self');
    }
</script>
</body>
</html>