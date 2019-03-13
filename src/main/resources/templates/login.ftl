<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css" media="all">
</head>
<body style="background-color: #f2f2f2">
    <div>
        <ul class="layui-nav">
            <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/goods">在售商品</a></li>
            <li class="layui-nav-item"><a href="">个人中心</a></li>
            <li class="layui-nav-item"><a href="/market/publish">发布商品</a></li>
            <li class="layui-nav-item">
                <a href="/market/login"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">未登录</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">修改信息</a></dd>
                    <dd><a href="/market/msg/list">站内信</a></dd>
                    <dd><a href="javascript:void(0)" onclick="logout()">退出登录</a></dd>
                </dl>
            </li>
        </ul>
    </div>
    <#if user??>
        <div style="margin-left: 20%; margin-right: 20%;">
        <div class="layui-card" style="margin-top: 20px;">
        <div class="layui-card-header">用户名</div>
        <div class="layui-card-body">
        ${user.name}
        </div>
        </div>
        <div class="layui-card">
    <div class="layui-card-header">邮箱</div>
        <div class="layui-card-body">
        ${user.mail}
        </div>
        </div>
        <div class="layui-card">
    <div class="layui-card-header">性别</div>
        <div class="layui-card-body">
        <#if user.sex== 1 >
            男
        <#else>
            女
        </#if>
        </div>
        </div>
        </div>
    <#else>
        <div  class="layui-container">
        <form class="layui-form" method="post" style="margin-left: 20%; margin-right: 30%; margin-top: 12%" action="/market/homepage.jspy">
        <div class="layui-form-item">
            <label class="layui-form-label" >邮箱</label>
            <div class="layui-input-block">
                <input type="text" name="mail" placeholder="请输入邮箱" lay-verify="required|mail" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
        <label class="layui-form-label" >密码</label>
        <div class="layui-input-block">
        <input type="password" name="pass" placeholder="请输入密码" lay-verify="required|pass" autocomplete="off" class="layui-input">
        </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn"  lay-filter="*" type="submit">登录</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                <button class="layui-btn"><a href="user/register">没有账号？</button>
            </div>
        </div>
        </form>
        </div>
    </#if>

<script src="/static/layui/layui.js"></script>
<script>
    layui.use('form', function(){
        var form = layui.form;

        form.verify({
            username: function (value) {
                if(value.length < 4)
                    return '用户名长度至少为4'
            },
            pass:[
                /^[\S]{6,12}$/, '密码必须6到18位，且不能出现空格'
            ],
            mail:[
                /^[a-zA-z0-9]{2,}@[[a-zA-z0-9]{2,}\.[[a-zA-z0-9]{2,}$/, '不合法的邮箱'
            ]

        });

        form.on('submit', function (data) {
            console.log(data.elem);
            var temp = new FormData(data.elem);
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/market/login.jspy', true);
            xhr.send(temp);
            xhr.onload = function() {
                //window.location.reload();
                window.open('http://localhost:8080/market/goods','_self');
            };
            return false;
        });
        //各种基于事件的操作，下面会有进一步介绍
    });
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