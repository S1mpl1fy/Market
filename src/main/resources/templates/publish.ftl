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
        <li class="layui-nav-item"><a href="/market/user/#{user.id}">个人中心</a></li>
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
<div class="layui-container">
    <form class="layui-form" style="margin-left: 20%; margin-right: 20%; margin-top: 12% " action="/market/publish.jspy">
        <div class="layui-form-item">
            <label class="layui-form-label">商品标题</label>
            <div class="layui-input-block">
                <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">交易方式</label>
            <div class="layui-input-block">
                <input type="checkbox" name="like[write]" title="当面交易">
                <input type="checkbox" name="like[read]" title="邮寄交易" checked>
                <input type="checkbox" name="like[dai]" title="其他">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">出售方式</label>
            <div class="layui-input-block">
                <input type="radio" name="method" value="1" title="一口价" checked>
                <input type="radio" name="method" value="0" title="拍卖">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">价格/起拍价</label>
            <div class="layui-input-block">
                <input type="number" name="price" required  lay-verify="required" placeholder="￥" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">详细介绍</label>
            <div class="layui-input-block">
                <textarea name="description" placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                <button type="button" class="layui-btn" id="test1">
                    <i class="layui-icon">&#xe67c;</i>上传图片
                </button>
            </div>
        </div>
    </form>

</div>


<script src="/static/layui/layui.js"></script>
<script>
    var img = null;
    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
    var form = layui.form;

    //监听提交
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit', function (data) {
            var temp = new FormData(data.form);
            temp.append("image",img);
            console.log(temp);
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/market/publish.jspy', true);
            xhr.send(temp);
            xhr.onload = function() {
                //window.location.reload();
                window.open('http://127.0.0.1:8080/market/goods','_self');
            };
            return false;
        })
    });
    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: '/uploadImage/' //上传接口
            ,done: function(res){
                console.log(res.msg);
                img = res.msg;
                //上传完毕回调
            }
            ,error: function(){
                //请求异常回调
            }
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
</script>
</body>
</html>