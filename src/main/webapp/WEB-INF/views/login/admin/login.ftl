<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>互联网工作 - 后台管理登录页</title>
<link rel="stylesheet" type="text/css" href="${path}/css/public.css">
    <link rel="stylesheet" type="text/css" href="${path}/css/manage.css">
<script type="text/javascript">
<!--
function reloadVerifyCode(){
	document.getElementById('verifyCodeImage').setAttribute('src', '${path}/login/getVerifyCodeImage');
}

//登录
function login(){
	document.getElementById("loginForm").submit();
}
//-->
</script>
</head>
<body>

<div class="x_body">
    <div class="x_cont" id="x_cont">
        <div class="x_bj bj1 show"></div>


        <div class="x_Sign">
            <h1>欢迎登录</h1>
            <form id="loginForm" name="loginForm" action="${path}/login/admin/submit" method="post">
                <input type="hidden" name=su_passwordd"/>
                <div style="color:red; font-size:22px;">${message_login!}</div>
                <ul class="x_Sin_deng  clearFix">
                    <li><i class="x_Sin_ico sin_01 fl"></i><input type="text" id="username" name="username"
                                                                  value="${su_codee!}" placeholder="请输入您的用户名" class=" fl">
                    </li>
                    <li><i class="x_Sin_ico sin_02 fl"></i><input type="password" id="password" name="password" autocomplete="off"
                                                                   placeholder="请输入您的密码"
                                                                  class=" fl"></li>
                </ul>
                 验证：<input type="text" name="verifyCode"/>
		 &nbsp;&nbsp;
		 <img id="verifyCodeImage" onclick="reloadVerifyCode()" src="${path}/login/getVerifyCodeImage"/><br/>
            
                <div class="x_wjposs clearFix tb_p">
                    <label class="fl labte"><input type="checkbox" id="ck_rmbUser" class="inpt">&nbsp;记住密码</label>
                    <a href="javascript:;" class="fr">找回密码</a><br/>
                    <div class="cont">为了您的信息安全，请不要在公用电脑上使用此功能！</div>
                </div>
                <div class="x_submit">
                    <a href="javascript:login();">登&nbsp;录</a>
                </div>
                <!--
                <div class="x_zc_po fr">
                   <a href="javascript:;">立即注册 </a>&nbsp;|
                   <a href="javascript:;">反馈意见 </a>
                </div>-->
            </form>
        </div>
    </div>

<#--<div class="x_hread_pt x_fotter">-->
<#--<div class="x_hear_p ">-->
<#--<p>京ICP备07504877号-2 | 互联网药品交易服务资格证编号：国A20150007 | 经营许可证编号：京ICP证151141号 | 互联网药品信息服务资格证编号：(京)-经营性-2012-0007-->
<#--Copyright©2004-2016 Mypharma.com 我的医药网 版权所有</p>-->
<#--</div>-->
<#--</div>-->
</div>

</body>
</html>