<!DOCTYPE html>
<html>
<head>
<title>sharmoon</title>
[#include "/shop/common/include.ftl" /]
</head>
<body >
[#include "/shop/common/head.ftl" /]
<div class="container">
    <div class="toppadding">
      	<div class="col-sm-6 center-block"> <img src="${base}/theme/shop/image/u90.jpg" class="img-responsive"></div>
        <div class="col-sm-6 ">
            <form class="form-signin" action="${base}/login" method="post">
                <h2 class="form-signin-heading">请登录</h2>
                <label for="inputEmail" class="sr-only">Email address</label>
                <input type="text" name="username" id="username" class="form-control" placeholder="账户名">
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="密码" required="">
                <div class="checkbox">
                    <label> <input type="checkbox" value="remember-me"> Remember me</label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>