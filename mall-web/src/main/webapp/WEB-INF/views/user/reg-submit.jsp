<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>结果</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="./">jsp作业</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="../login.jsp">登录</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <% String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
    %>
    <%
//        if(password.equals(password2)){
//            out.println("<div class='alert alert-success' role='alert'>注册成功</div>");
//            out.println("<ul class='list-group'>");
//            out.println("<li class='list-group-item'>用户名：" + username + "</li>");
//            out.println("<li class='list-group-item'>密码：" + password + "</li>");
//            out.println("</ul>");
//        }
//        else{
//            out.println("<div class='alert alert-danger' role='alert'>两次密码输入不一致，请重新输入</div>");
//        }
    %>
    <!-- <%=username %>
    <%=password %>
    <%=password2 %> -->
</div>