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
        <li class="layui-nav-item"><a href="/market/homepage">个人中心</a></li>
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

<div class="layui-container">
    <div class="layui-card" style="margin-top: 20px">
        <div class="layui-card-header">
            <p>私信</p>
        </div>
        <div class="layui-card-body">
            <textarea name="description" placeholder="请输入私信内容" class="layui-textarea" id="msg"></textarea>
        </div>
        <div class="layui-card-body" align="right">
            <button class="layui-btn" onclick="submit()">发送</button>
        </div>
    </div>
</div>

<#if messages??>
    <#list messages as vo>
        <#if true>
            <div class="layui-container">
            <div class="layui-card" style="margin-top: 20px">
            <div class="layui-card-body" >
            <div class="layui-row layui-col-space1">
            <div class="layui-col-md1">
            <div class="layui-row grid-demo">
                <img src="${vo["headUrl"]}" height="60px" >
            </div>
            </div>
            <div class="layui-col-md11">
            <div class="layui-row grid-demo grid-demo-bg1">
            <div class="layui-col-md2" style="color:  #7a7a52;">
            ${vo["userName"]}
            </div>
            <div class="layui-col-md9" style="color:  #7a7a52;">
            ${vo["message"].createdDate?string('yyyy-MM-dd HH:mm:ss')}
            </div>
            <div class="layui-col-md11" style="padding-top: 12px">
            ${vo["message"].content}
            </div>
            </div>
            </div>
            </div>
            </div>
            </div>
            </div>
        <#else >

        </#if>
    </#list>
</#if>

<script src="/static/layui/layui.js"></script>
<script>
    var userId = ${user.id}, toId = ${targetId};
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
    function submit() {
        var content  = document.getElementById('msg').value;
        var temp = new FormData();
        temp.append("fromId",userId);
        temp.append("toId",toId);
        temp.append("content", content);
        console.log(temp);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/market/msg/list.add', true);
        xhr.send(temp);
        xhr.onreadystatechange = function(){
            if(xhr.readyState === 4 && xhr.status === 200){
                window.location.reload(true);
            }
        };
        return false;
    }
</script>
</body>