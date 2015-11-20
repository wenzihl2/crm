LG.changepassword = function (){
    $(document).bind('keydown.changepassword', function (e){
        if (e.keyCode == 13){
            doChangePassword();
        }
    });

    if (!window.changePasswordWin) {
        var changePasswordPanle = $("<form></form>");
        changePasswordPanle.ligerForm({
            fields: [
                { display: '旧密码', name: 'OldPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '新密码', name: 'currentPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '确认密码', name: 'currentPassword2', type: 'password', validate: { maxlength: 50, required: true, equalTo: 'input[name=currentPassword]', messages: { required: '请输入密码', equalTo: '两次密码输入不一致'}} }
            ]
        });

        //验证
        jQuery.metadata.setType("attr", "validate");
        LG.validate(changePasswordPanle);

        window.changePasswordWin = $.ligerDialog.open({
            width: 400,
            height: 190, top: 200,
            isResize: true,
            title: '用户修改密码',
            target: changePasswordPanle,
            buttons: [
	            { text: '确定', onclick: function () {
	                doChangePassword();
	            }},
	            { text: '取消', onclick: function () {
	                window.changePasswordWin.hide();
	                $(document).unbind('keydown.changepassword');
	            }}
            ]
        });
    } else {
        window.changePasswordWin.show();
    }

    function doChangePassword()
    {
        var OldPassword = $("input[name=OldPassword]").val();
        var currentPassword = $("input[name=currentPassword]").val();
        if (changePasswordPanle.valid()) {
            LG.ajax({
                url: base + '/admin/sso/user/updateProfile.jhtml',
                data: { "oldPassword": OldPassword, currentPassword: currentPassword },
                success: function (result) {
                	if(result.type == "ERROR") {
                		LG.showError(result.content);
                	} else {
	                	LG.showSuccess('密码修改成功');
	                    window.changePasswordWin.hide();
	                    $(document).unbind('keydown.changepassword');
                	}
                },
                error: function (message) {
                    LG.showError(message);
                }
            });
        }
    }

};