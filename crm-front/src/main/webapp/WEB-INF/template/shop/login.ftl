<!DOCTYPE HTML>
<html>
<head>
    [#include "/shop/common/include.ftl" /]
    <script type="text/javascript">
        $(function () {
            $("#registBtn").click(function () {
                window.location = "${base}/register";
            });
            $("#username").focus();

            $(".jcaptcha-btn").click(function () {
                var img = $(".jcaptcha-img");
                var imageSrc = img.attr("src");
                if (imageSrc.indexOf("?") > 0) {
                    imageSrc = imageSrc.substr(0, imageSrc.indexOf("?"));
                }
                imageSrc = imageSrc + "?" + new Date().getTime();
                img.attr("src", imageSrc);
            });
            $.validationEngineLanguage.allRules.ajaxJcaptchaCall = {
                "url": "${base}/jcaptcha-validate",
                "alertTextLoad": "* 正在验证，请稍等。。。"
            };
            $("#loginForm").validationEngine({scroll: false});
        });

        function login() {
            var code;
            var username = $.trim($("#username").val());
            var password = $.trim($("#password").val());
            if (username == '') {
                $("#username").addClass("wrong");
                $("#username").focus();
                return;
            }
            if (password == '') {
                $("#password").addClass("wrong");
                $("#password").focus();
                return;
            }
            if ($("#captchaCode").length == 1) {
                code = $.trim($("#captchaCode").val());
                if (code == '') {
                    $("#captchaCode").addClass("wrong");
                    $("#captchaCode").focus();
                    return;
                }
            }
            $.ajax({
                url: "${base}/common/publicKey",
                type: "GET",
                dataType: "json",
                cache: false,
                success: function (data) {
                    var rsaKey = new RSAKey();
                    rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
                    password = hex2b64(rsaKey.encrypt(password));
                    $.ajax({
                        url: "${base}/login",
                        type: "POST",
                        dataType: "json",
                        data: {
                            username: username,
                            password: password,
                            captcha: code,
                            captchaId: "${captchaId}"
                        },
                        cache: false,
                        success: function (data) {
                            if (data.error == undefined || data.error == '') {
                                window.location = "${(redirectUrl)!''}";
                            } else {
                                $.message('WARN', data.error);
                            }
                        }
                    });
                }
            });
        }
    </script>
</head>
<body class="login-bg">
[#include "/shop/common/head.ftl"/]
<div class="container toppadding bottompadding">
    <div class="col-sm-6">
        <!--为广告所在容器指定-->
        <div class="ad">
            [@shop_advert_list position_code="login"]
            [#list tag_list as tag]
            <a href="${tag.url}">
                <img src="${base}${tag.realPath}" class="img-responsive">
            </a>
            [/#list]
            [/@shop_advert_list]
        </div>
    </div>
    <div class="col-sm-4">
        <form id="loginForm" method="post" class="form-signin" role="form">
            <label for="username" class="sr-only">username</label>
            <input type="text" id="username" class="form-control" placeholder="手机号" required="" autofocus=""
                   value="${(param.username)!''}">
            <label for="Password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="密码" required="">

            <div class="col-xs-6 padding-no">
                <input type="text" id="jcaptchaCode" name="jcaptchaCode"
                       class="form-control validate[required,ajax[ajaxJcaptchaCall]]" placeholder="请输入验证码">
            </div>
            <div class="col-xs-6 padding-no">
                <div class="col-xs-6 padding-no"><img class="jcaptcha-btn jcaptcha-img" style="margin-left: 10px;"
                                                      src="${base}/jcaptcha.jpg" title="点击更换验证码"></div>
                <div class="col-xs-6 padding-no"><a class="jcaptcha-btn btn btn-link float-right">换一张</a></div>
            </div>
            <div class="col-xs-12">
                <div class="checkbox">
                    <label> <input type="checkbox" value="remember-me">下次自动登录</label>
                </div>
            </div>
            <div class="col-xs-6">
                <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
            </div>
            <div class="col-xs-6">
                <a href="register">
                    <div class="btn btn-lg btn-success btn-block">注册</div>
                </a>
            </div>
        </form>
    </div>
</div>

[#include "/shop/common/foot.ftl"/]
</body>
</html>