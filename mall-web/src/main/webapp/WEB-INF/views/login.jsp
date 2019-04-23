<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="user">jsp作业</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="login.jsp">登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-4">

        </div>
        <div class="col-md-4">
            <form class="form-signin" method="post" action="/user/login">
                <h2 class="form-signin-heading">登录到jsp作业</h2>
                <label for="userName">用户名</label>
                <input type="text" name="userName" id="userName" class="form-control" placeholder="请输入用户名" required autofocus><br>
                <label for="password">密码</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="请输入密码" required>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me" checked="checked"> 记住密码
                    </label>
                </div>
                <button type="submit" class="btn btn-primary" id="btn-login">登录</button>
                <a href="/user/toRegister" class="btn btn-default">注册</a>
            </form>
        </div>
        <div class="col-md-4">
        </div>
    </div>
</body>
</html>