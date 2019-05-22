<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html lang="zh-CN">
<head>
    <c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
    <meta charset="utf-8">
    <meta name="description" content="这是页面描述">
    <meta name="keywords" content="key,key1,key2">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <%--<meta http-equiv="refresh" content="1;url=/pageController/toBaidu"/>--%>
    <title>css3手机移动端登录界面样式代码演示_大头网</title>

    <%--<link rel="stylesheet" href="${ path } /css/login.css" />--%>
</head>
<body background="${path}/images/111.jpg">
<!-- 代码 开始 -->
<div class="login">
    nihao<p/>xiongdi<br/>
    aaaaa<br/>
    bbbbb<hr/>
    <%--<img src="${path}/images/111.jpg" alt="正在加载。。。">--%>
    <%--<iframe name = "myIframe" id = "idIframe" src="${path} /WEB-INF/views/home.jsp" frameborder="0" align="left" width="200" height="200" scrolling="no">--%>
        <%--<p>你的浏览器不支持iframe标签</p>--%>
    <%--</iframe>--%>
    <%--<div class="welcome"><img src="${ path }/images/welcome.png"></div>--%>
    <div class="login-form">
        <%--<div class="login-inp"><label>登录</label><input type="text" placeholder=""></div>--%>
        <%--<div class="login-inp"><label>密码</label><input type="password" placeholder=""></div>--%>
        <%--<div class="login-inp"><a href="#" >立即登录</a></div>--%>

        <div class="login-inp">
        <form class="form-signin" method="post" action="user/login">
        <label for="userName">登录</label>
        <input type="text" name="userName" id="userName" class="form-control" placeholder="请输入用户名" required autofocus><br>
        <label for="password">密码</label>
        <input type="password" name="password" id="password" class="form-control" placeholder="请输入密码" required>
        <div class="checkbox">
        <label>
        <input type="checkbox" value="remember-me" checked="checked"> 记住密码
        </label>
        </div>
        <button type="submit" class="btn btn-primary" id="btn-login">立即登录</button>
        <a href="/pageController/toRegister" class="btn btn-default">注册</a>
        </form>
        </div>

    </div>
    <div class="login-txt"><a href="#">立即注册</a>|<a href="#">忘记密码？</a></div>
</div>
<!-- 代码 结束 -->

<%--<nav class="navbar navbar-default">--%>
    <%--<div class="container-fluid">--%>
        <%--<div class="navbar-header">--%>
            <%--<a class="navbar-brand" href="user">jsp作业</a>--%>
        <%--</div>--%>
        <%--<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">--%>
            <%--<ul class="nav navbar-nav navbar-right">--%>
                <%--<li><a href="login.jsp">登录</a></li>--%>
            <%--</ul>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</nav>--%>
<%--<div class="container">--%>
    <%--<div class="row">--%>
        <%--<div class="col-md-4">--%>

        <%--</div>--%>
        <%--<div class="col-md-4">--%>
            <%--<form class="form-signin" method="post" action="user/login">--%>
                <%--<h2 class="form-signin-heading">登录到jsp作业</h2>--%>
                <%--<label for="userName">用户名</label>--%>
                <%--<input type="text" name="userName" id="userName" class="form-control" placeholder="请输入用户名" required autofocus><br>--%>
                <%--<label for="password">密码</label>--%>
                <%--<input type="password" name="password" id="password" class="form-control" placeholder="请输入密码" required>--%>
                <%--<div class="checkbox">--%>
                    <%--<label>--%>
                        <%--<input type="checkbox" value="remember-me" checked="checked"> 记住密码--%>
                    <%--</label>--%>
                <%--</div>--%>
                <%--<button type="submit" class="btn btn-primary" id="btn-login">登录</button>--%>
                <%--<a href="/pageController/toRegister" class="btn btn-default">注册</a>--%>
            <%--</form>--%>
        <%--</div>--%>
        <%--<div class="col-md-4">--%>
        <%--</div>--%>
    <%--</div>--%>
</body>
</html>