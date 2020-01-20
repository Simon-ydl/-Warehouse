/**
 * 密码强度显示插件，依赖jquery
 */
var strongRegex = new RegExp('^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$', 'g');
var mediumRegex = new RegExp('^(?=.{8,})(?=.*[a-zA-Z])(?=.*[0-9]).*$', 'g');
var enoughRegex = new RegExp('(?=.{8,}).*', 'g');
var pstrengthLastInputTime = 0;
(function($){
    $.pstrength = function(input,pstrengthHolderId){
        var holder = $('#' + pstrengthHolderId);
        var pwDiv = $('<div class="pw-strength" style="top:-4px;" title="灰：密码小于8位&#10;弱：密码不符合中强密码规则&#10;中：密码为8位及以上并且含有字母和数字&#10;强：密码为8位及以上并且含有大写字母、小写字母、数字、特殊字符">'
            + '<div class="pw-bar"></div>'
            + '<div class="pw-bar-on"></div>'
            + '<div class="pw-txt">'
                + '<span>弱</span>'
                + '<span>中</span>'
                + '<span>强</span>'
            + '</div></div>');

        holder.empty();
        holder.append(pwDiv);

        input.keyup(function(e) {
            var field = $(this);
            pstrengthLastInputTime = e.timeStamp;
            setTimeout(function(){//不输入0.5秒后进行验证
                if(!(pstrengthLastInputTime - e.timeStamp === 0)) return;

                var value = $('#' + field.attr('id')).val();
                var pw = holder;
                if (eval(enoughRegex.toString()).test(value) == false) { //密码小于8位的时候，密码强度图片都为灰色
                    pw.removeClass('pw-weak');
                    pw.removeClass('pw-medium');
                    pw.removeClass('pw-strong');
                    pw.addClass('pw-default');
                }
                else if (eval(strongRegex.toString()).test(value)) {//密码为8位及以上并且含有大写字母、小写字母、数字、特殊字符，强度最强
                    pw.removeClass('pw-weak');
                    pw.removeClass('pw-medium');
                    pw.removeClass('pw-strong');
                    pw.addClass('pw-strong');
                }
                else if (eval(mediumRegex.toString()).test(value)) {//密码为8位及以上并且含有字母和数字，强度中等[注：等保二级要求]
                    pw.removeClass('pw-weak');
                    pw.removeClass('pw-medium');
                    pw.removeClass('pw-strong');
                    pw.addClass('pw-medium');
                }
                else {//如果密码为8位及以下，就算大写字母、小写字母、数字、特殊字符四项都包括，强度也是弱的
                    pw.removeClass('pw-weak');
                    pw.removeClass('pw-medium');
                    pw.removeClass('pw-strong');
                    pw.addClass('pw-weak');
                }
            },500);
            return true;
        });
    }
    $.fn.pstrength = function(){
        this.each(function(){
            new $.pstrength($(this),$(this).attr('pstrengthHolderId'));
        });
        return this;
    }
})(jQuery)