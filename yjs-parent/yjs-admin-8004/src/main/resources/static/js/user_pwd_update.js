var loginDivContent;
function toUpdateUserPwd() {
    loginDivContent = $('#loginbox').children().clone();
    var username = $('#username').val();
    if(!username || username.length == 0) {
        alert('请先输入用户名');
        return;
    }

    $('#username').val('');
    $('#authcode').val('');

    $('#username').attr('title','请输入旧密码').attr('placeholder','请输入旧密码');
    $('#username').prev().attr('src','images/login/pm.png');
    $('#username').attr('userCode',username);
    $('#username').attr('id','oldUserPwd');

    $('#password').attr('title','请输入新密码').attr('placeholder','请输入新密码');
    $('#password').attr('pstrengthHolderId','pstrengthHolder');
    $('#password').attr('id','newUserPwd');

    $('.main_input_box:eq(3) > a:eq(0)').attr('onclick','updateUserPwd()').text('修改密码');
    $('.main_input_box:eq(3) > a:eq(1)').attr('onclick','toLoginForm()').text('返  回');

    $('.main_input_box:last').html('<div id="status" class="errors" style="display:table-cell;vertical-align: middle;color:#c30f1a;font-size:14px;height:40px;">'
        + '<table style="width: 100%"><tr><td style="text-align: left;padding-left:30px;">新密码强度：<p style="width:145px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;" title="' + username + '">当前用户：'
        + username + '</p></td><td id="pstrengthHolder"></td></tr></table></div>')

    $('#newUserPwd').pstrength();
}

function updateUserPwd() {
    var userCode =  $.trim($('#oldUserPwd').attr('userCode')),
        oldUserPwd = $.trim($('#oldUserPwd').val()),
        newUserPwd = $.trim($('#newUserPwd').val()),
        authCode = $.trim($('#authcode').val());

    if(!userCode) {alert('请填写用户名');return;}
    if(!oldUserPwd) {alert('请填写旧密码');return;}
    if(!newUserPwd) {alert('请填写新密码');return;}
    if(!authCode) {alert('请填写验证码');return;}

    if(newUserPwd && !eval(mediumRegex.toString()).test(newUserPwd) && !eval(strongRegex.toString()).test(newUserPwd)) {
        alert('密码强度要求至少达到中级：密码为8位及以上并且含有字母和数字');
        $('#newUserPwd').focus();
        return;
    }

    $.post('/cas/updateUserPwd',
        {
            'userCode'  : userCode,
            'oldUserPwd': oldUserPwd,
            'newUserPwd': newUserPwd,
            'authCode'  : authCode
        },function(result) {
        if(result) {
            var data = eval('(' + result + ')');
            if(data && data.code == 1) {
                alert('密码修改成功');
                toLoginForm();
            }
            else if(data && data.msg) alert(data.msg);
            else
                alert('密码修改失败，请稍后重试或联系您的账号管理员重置密码');
            $('#validcodeimg').trigger('click');
        }
    })
}

function toLoginForm() {
    if(loginDivContent) {
        $('#loginbox').empty();
        $('#loginbox').append(loginDivContent);
        $('#authcode').val('');
        $('.main_input_box:last').html('');
    }
}