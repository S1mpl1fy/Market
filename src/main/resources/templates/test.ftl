<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>test</title>
    <link rel="stylesheet" href="../static/layui/css/layui.css" media="all">
</head>
<body>
<div>

    <#list gvm as gu>
        ${gu.goodsTitle}<br/>
        ${gu.commentCount}<br/>
        ${gu.description}<br/>
    </#list>
</div>
<div id="pageshow">
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
</fieldset>

<script src="../static/layui/layui.all.js"></script>

</body>
</html>