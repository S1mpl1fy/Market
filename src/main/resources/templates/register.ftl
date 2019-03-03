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
            <li class="layui-nav-item" style="margin-left: 20%"><a href="/market/index">在售商品</a></li>
            <li class="layui-nav-item"><a href="/market/homepage">个人中心</a></li>
            <li class="layui-nav-item">
                <#if name??>
                    <a href="/market/homepage"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">${name}</a>
                <#else >
                    <a href="/market/homepage"><img src="http://images.nowcoder.com/head/11t.png" class="layui-nav-img">未登录</a>
                </#if>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">修改信息</a></dd>
                    <dd><a href="javascript:;">安全管理</a></dd>
                    <dd><a href="javascript:;">退出登录</a></dd>
                </dl>
            </li>
        </ul>
    </div>
    <div  class="layui-container">
        <form class="layui-form"  style="margin-left: 20%; margin-right: 20%; margin-top: 12% " action="register.jspy">
            <div class="layui-form-item">
                <label class="layui-form-label" name="">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="name" placeholder="请输入" lay-verify="required|username" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" name="">邮箱</label>
                <div class="layui-input-block">
                    <input type="text" name="mail" placeholder="请输入" lay-verify="required|mail" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" name="">密码</label>
                <div class="layui-input-block">
                    <input type="password" name="pass" placeholder="请输入" lay-verify="required|pass" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" name="">重复密码</label>
                <div class="layui-input-block">
                    <input type="password" name="pass2" placeholder="请输入" lay-verify="required|pass2" autocomplete="off" class="layui-input">
                </div>
            </div>
            <!--
            <div class="layui-form-item">
                <label class="layui-form-label">下拉选择框</label>
                <div class="layui-input-block">
                    <select name="interest" >
                        <option value="0">本科</option>
                        <option value="1">研究生</option>
                    </select>
                </div>
            </div>
            -->
            <div class="layui-form-item">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <input type="radio" name="sex" value="1" title="男">
                    <input type="radio" name="sex" value="0" title="女" checked>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="*">注册</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>

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
                /^[\S]{6,18}$/, '密码必须6到18位，且不能出现空格'
            ],
            pass2: function (value) {
                alert("1"+"2");
                var pass_com = document.getElementById("pass").value;
                alert("1"+pass_com+"2");
                if(pass_com != value){
                    return '两次输入的密码不相同'
                }
            },
            mail:[
                /^[a-zA-z0-9]{2,}@[[a-zA-z0-9]{2,}\.[[a-zA-z0-9]{2,}$/, '不合法的邮箱'
            ]

        });
        //各种基于事件的操作，下面会有进一步介绍
    });
</script>
</body>
</html>