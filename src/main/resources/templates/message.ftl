<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>站内信列表</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body style="background-color: #f2f2f2">
<div>
    <ul class="layui-nav">
        <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/index">在售商品</a></li>
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

<#if conversations??>
    <#list conversations as cv>
        <div class="layui-container">
            <div class="layui-card" style="margin-top: 20px">
                <div class="layui-card-body" onclick="window.open('http://localhost:8080/market/msg/detail/${cv["conversation"].conversationId}','_self')" >
                    <div class="layui-row layui-col-space1">
                        <div class="layui-col-md1">
                            <div class="layui-row grid-demo">
                                <img src="${cv["user"].headUrl}" height="60px" >
                                <#if cv["unread"] ??>
                                    <span class="layui-badge-rim">${cv["unread"]}</span>
                                </#if>
                            </div>
                        </div>
                        <div class="layui-col-md11">
                            <div class="layui-row grid-demo grid-demo-bg1">
                                <div class="layui-col-md2" style="color:  #7a7a52;">
                                    ${cv["user"].name}
                                </div>
                                <div class="layui-col-md9" style="color:  #7a7a52;">
                                    ${cv["conversation"].createdDate?string('yyyy-MM-dd HH:mm:ss')}
                                </div>
                                <div class="layui-col-md11" style="padding-top: 12px">
                                    ${cv["conversation"].content}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#list>

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