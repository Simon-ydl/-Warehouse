/**
 * 加载前置条件数据
 */
;(function($){
    "use strict";

    /**
     * 内部使用常量
     */
    var constants = {
        layFilter: 'perfixSituation',
        cardPrefix: 'card_perfix_',
      /*  delChoiceText: '清除选择',
        delChoiceBtnClassName: 'delChoiceBtn',*/
        bodyDivClassName: 'situation-body',
        footerDivClassName: 'situation-footer',
        requirdStr: 'required="true"'
        ,toggleBtnIdName: 'perfixSituation-toggle-btn'
        ,hightLinght: '置顶'
    };

    var wrapperDom = false;
    var toggleWrapperBtn = false;

    /**
     * 内部函数定义
     */
    $.extend(prefixSituationWrapper.prototype,{
        /**
         * @private
         * @desc 参数使用默认值进行初始化与合并
         * @param {string|Object} source - 数组源（URL或JSON格式数据源）
         * @param {Object} option - 初始化参数
         * @return {Object} - 合并后的参数
         */
        _setOption1st: function(option) {
            return $.extend({
                /**
                 * 类型 1：根据url获取数据 2.直接传入相匹配的json数据
                 */
                type: 1,
                /**
                 * 数据来源 url获取json数据
                 */
                source: false,
                /**
                 * 通过url获取数据时，同时发送的参数
                 */
                param: false,
                /**
                 * 回显的情形选择ID数组
                 */
                echo: false,
                /**
                 * 是否显示标题，默认显示
                 */
                showTitle: true,
                /**
                 * 是否高亮显示
                 */
                isHightLinght: false,
                /**
                 * 展示的title值，不传入默认事项名称
                 */
                title: false,
                /**
                 * 标题文字旁边的图标
                 */
                titleIcon: '<i class="iconfont layui-extend-preview"></i>',
                before: false,
                /**
                 * 默认所有的选择 radio checkbox的name值
                 */
                inputName: 'situationIds',
                /**
                 * 展开情形面板时的事件
                 */
                unfold: false,
                /**
                 * 点击按钮抛出情形ID集合时的事件
                 */
                end: false,
                /**
                 * 是否禁用checkbox
                 */
                inputDisabled: false,
                /**
                 * 是否禁用checkbox
                 */
                afterInit: false,
                /**
                 * 投屏的一个标识状态
                 */
                widgetClickCallBack : false

            },option);
        },
        /**
         * @private
         * @desc 参数初始化
         * @param {string|Object} source - 数据源
         * @param {Object} option - 参数集
         */
        _setOption: function(option){
            option = this._setOption1st(option);
            this.option = option;
        },
        /**
         * 初始化前的操作
         * @param option
         * @private
         */
        _setBefore: function(option, callback){
            if (typeof callback == 'function') {
                callback();
            }
        },
        _initNodeElem : function (nodeInfos,pqid,isRoot){
            var option = this.option;
            var outPutElems=[];

            for(var index in nodeInfos)
            {
                var echoDataArray = []; //定义回显的array
                var nodeInfo = nodeInfos[index];
                var $nodeElme = $('<div></div>').attr('id', constants.cardPrefix + nodeInfo.id).attr("pqid",pqid);

                //问题
                var $choiceQuestionWrapper = $('<div></div>').addClass('layui-card-header')
                    .css({'height': 'auto','min-height': '22px', 'line-height': '20px', 'padding-top': '10px', 'padding-bottom': '10px'});

                //判断是否是必须的问题
                $choiceQuestionWrapper.append(
                    $('<i>&nbsp;</i>').addClass('iconfont layui-extend-bixu').css({color: 'red'})
                )
                $choiceQuestionWrapper.append(nodeInfo.name);

                //高亮显示按钮
                if(option.isHightLinght)
                {
                    var $btn = $('<input />', {type: 'button'}).addClass('layui-btn layui-btn-sm layui-btn-primary _top').attr("value",constants.hightLinght)
                        .css({'margin-left': '20px','visibility':'visible'}).on('click',function () {
                            $("._gl").each(function(i,v){
                                $(v).removeClass("_gl");
                            });
                            $(this).addClass("_gl");
                        })
                    $choiceQuestionWrapper.append($btn);
                }

                //备注
                if(nodeInfo.bz){
                    var $bz = $('<i></i>').addClass('iconfont layui-extend-beizhu situation-bz').data('content', nodeInfo.bz);
                    $choiceQuestionWrapper.append('&nbsp;').append($bz);
                }

                //答案
                var $choiceAnswerWrapper = $('<div></div>').addClass('layui-card-body');
                $choiceAnswerWrapper.css('border-bottom', '1px solid #f6f6f6');
                var answers = nodeInfo.childs;

                for(var j in answers){
                    var answer = answers[j];
                    var pid = nodeInfo.id;
                    var name = option.inputName + '_' + pid;
                    var sfxz = this.option.echo && this.option.echo.indexOf(answer.id) > -1;


                    var sfjy = this.option.inputDisabled;
                    if(answer.sfbx == '1')
                    {
                        sfxz = true;
                        sfjy = true;
                    }

                    var reg = new RegExp('"',"g")
                    //var endBz = (answer.bz ? "("+answer.bz+")" : "").replace(reg,'&quot;');
                    var answerName = answer.name.replace(reg,'&quot;');
                    var title = answer.sfzz == '1' ? "<span style='color: red!important;padding-left: 0px'>"+answerName+"</span>" : answerName;

                    //备注
                    if(answer.bz && answer.sfzz == '0'){
                        title += "<ic class='iconfont layui-extend-beizhu situation-bz' data-content='"+answer.bz+"'></ic>";
                    }

                    var $input = '<input type="radio" name="{0}" value="{1}" title="{2}" endFlag="{7}" {8} unSid="{9}"  lay-filter="{3}" {4} {5} {6}>'
                        .signMix(name, answer.id, title, constants.layFilter, constants.requirdStr, sfxz ? 'checked': '', sfjy ? 'disabled="disabled"': ''
                        ,answer.sfzz,answer.bz ? "endBz='"+answer.bz+"'" : "",answer.unSid);

                    //判断是单选问题还是多选问题
                    if(nodeInfo.muiltSelect){
                        $input = '<input type="checkbox" minCheck="{9}" name="{0}"  value="{1}" title="{2}" endFlag="{7}" {8} unSid="{10}"  lay-skin="primary" lay-filter="{3}" {4} {5} {6}>'
                            .signMix(name, answer.id, title, constants.layFilter, constants.requirdStr, sfxz ? 'checked': '', sfjy ? 'disabled="disabled"': ''
                                ,answer.sfzz,answer.bz ? "endBz='"+answer.bz+"'" : "" ,nodeInfo.muiltSelect,answer.unSid);
                        //如果是多选的时候，则需要特别处理
                        pid = answer.id;
                    }

                    if(option.isView)
                    {
                        $input.attr("disabled",'disabled');
                    }

                    //继承问题中的绑定材料
                    if(answer.childs){
                        $input = $($input).data('pid',pid).data('content', answer.childs).data('valid', true);
                        if(sfxz){
                            echoDataArray.push({
                                childs:answer.childs,
                                pid:pid
                            });
                        }
                    }
                    $choiceAnswerWrapper.append($input);

                }

                $nodeElme.append($choiceQuestionWrapper).append($choiceAnswerWrapper);

                var elemList = [];
                if(echoDataArray.length > 0){
                    for(var k in echoDataArray){
                        var echoData = echoDataArray[k];
                        var tmp = this._initNodeElem(echoData.childs,echoData.pid,false);
                        Array.prototype.push.apply(elemList, tmp);
                    }
                }

                if(isRoot)
                {
                    var $nodeElmeRoot = $('<div></div>').addClass('layui-card');
                    $nodeElmeRoot.append($nodeElme)
                    if(elemList.length > 0){
                        for(var i in elemList)
                        {
                            $nodeElmeRoot.append(elemList[i]);
                        }
                    }
                    outPutElems.push($nodeElmeRoot);
                }
                else {
                    outPutElems.push($nodeElme);
                    if(elemList.length > 0){
                        for(var i in elemList)
                        {
                            outPutElems.push($(elemList[i]));
                        }
                    }
                }

            }
            return outPutElems;
        },
        /**
         * @private
         * @desc 插件HTML结构生成
         * @param {Object} combo_input - 输入框源对象
         */
        _setElem: function(self, data, wrapper) {
            //渲染内部总的div
            var $formWrapper = $('<form></form>').addClass('layui-form')
                .css({'background': '#F2F2F2', 'padding': '20px'});

            var option = self.option;
            //问题与答案的div
            var $bodyWrapper = $('<div></div>').addClass(constants.bodyDivClassName)
            //情形选择
            var childs = data.childs;

            //初始化第一层问题
            var elemList = self._initNodeElem(childs,"root",true);
            for(var i in elemList)
            {
                $bodyWrapper.append(elemList[i]);
            }

            $formWrapper.append($bodyWrapper);
            wrapper.empty();

            //渲染标题部分
            if(option.showTitle){
                var $toggleWrapperBtn = $('<button></button>', {type: 'button'}).addClass('layui-btn layui-btn-primary').attr('id', constants.toggleBtnIdName)
                    .css({position: 'absolute', right: '15px', top: '8px'})
                    .append('展开情形');
                var $titleDom = $('<blockquote></blockquote>').addClass('layui-elem-quote layui-quote-nm').css({background: '#ffffff', margin: '0px'})
                    .append(option.titleIcon)
                    .append('&nbsp;')
                    .append(option.title || data.name)
                    .append(
                        $toggleWrapperBtn.hide()
                    );
                wrapper.append($titleDom);
                toggleWrapperBtn = $toggleWrapperBtn;
            }

            wrapper.append($formWrapper);
            layui.form.render();
            wrapperDom = $formWrapper;
        },
        /**
         * 隐藏问题与答案面板
         * @private
         */
        _fold: function(){
            wrapperDom.slideUp();
            toggleWrapperBtn.show();
        },
        _unfold: function(){
            wrapperDom.slideDown();
            toggleWrapperBtn.hide();
            var unfoldFn = this.option.unfold;
            if(unfoldFn && typeof unfoldFn == 'function'){
                unfoldFn();
            }
        },
        /**
         * 抛出情形选择ID集合
         * @private
         */
        _end: function(){
            var siutationUnSidArray = [];
            var prefixIdsArray = [];
            var inputNameArray = [];
            var validNameArray = [];
            var isError = false;
            var isFinish = false;
            var errorMsg ="";

            wrapperDom.find('input:radio:checked,input:checkbox:checked').each(function(index, dom){
                var endFlag = $(dom).attr("endFlag");
                if(endFlag == '1')
                {
                    errorMsg ="";
                    var endBz =  $(dom).attr("endBz");
                    if(endBz && endBz.length > 0)
                    {
                        errorMsg = endBz;
                    }
                    isError = true;
                    $(dom).focus();
                    layui.form.render();
                    return;
                }
            });

            wrapperDom.find('input:radio,input:checkbox').each(function(index, dom){
                var name = $(dom).attr('name');
                inputNameArray.push(name);
                if($(dom).attr('required')){
                    validNameArray.push(name);
                }
            });

            var uniqueNameArray = this._setUnquieArray(inputNameArray);
            var uniqueValidNameArray = this._setUnquieArray(validNameArray);

            if(!isError)
            {
                //验证必选的是否选择
                var isNotValid = uniqueValidNameArray.length > 0;
                for(var i in uniqueValidNameArray){
                    var name = uniqueValidNameArray[i];
                    var $inputs = wrapperDom.find('input[name='+name+']');
                    $inputs.each(function(index, dom){
                        if($(dom)[0].checked){
                            isNotValid = false;
                        }
                    });
                    //如果都本次选中，则值重置为true
                    if(!isNotValid && i < uniqueValidNameArray.length - 1){
                        isNotValid = true;
                    }else{
                        break;
                    }
                }

                if(!isNotValid){
                    //多选几的情况

                    var checkboxList = wrapperDom.find('input:checkbox:checked');
                    if(checkboxList.length > 0)
                    {
                        isNotValid = true;
                        checkboxList.each(function(index, dom){
                            var $this = $(this);
                            var name = $this.attr("name");
                            var minCheck = $this.attr("minCheck") * 1;
                            var sameNameCheckbox =  $("input[name='"+name+"']:checked");
                            if(minCheck > sameNameCheckbox.length)
                            {
                                isNotValid = true;
                                return false;
                            }
                            else {
                                isNotValid = false;
                            }
                        });
                    }

                    if(!isNotValid){
                        isFinish = true;
                    }
                }
            }

            //遍历所有radio,checkbox
            for(var i in uniqueNameArray){
                var name = uniqueNameArray[i];
                var $inputs = wrapperDom.find('input[name='+name+']');
                $inputs.each(function(index, dom){
                    if($(dom)[0].checked){
                        prefixIdsArray.push($(dom).val());
                        siutationUnSidArray.push($(dom).attr("unSid"));
                    }
                });
            }

            var result = { prefixIds: prefixIdsArray,unSid: siutationUnSidArray,isError:isError,errorMsg:errorMsg,isFinish:isFinish};
            var endFn = this.option.end;
            if(endFn && typeof endFn == 'function'){
                endFn(result);
            }
            return result;
        },
        /**
         * @private
         * @desc 初始化后的处理
         * @param {Object} self - 插件的内部对象
         * @param {Object} data - 列表数据
         */
        _afterInit: function(self, data, wrapper) {
            this._setElem(self, data, wrapper);
            this._setBefore(this.option, function () {
                self._end();

                if(self.option.afterInit)
                {
                    self.option.afterInit();
                }
            });
        },
        /**
         * @private
         * @desc 为插件设置初始化的选中值（若有指定的话）
         */
        _setInitRecord: function(wrapper) {
            var self = this;
            //将初始值放入控件
            if (self.option.type == '2') {
                var data = JSON.parse(self.option.source);
                self._afterInit(self, data, wrapper);
            } else {
                var param = $.extend(constants.sourceParam, self.option.param);
                globalAsyncPost(self.option.source, param, function (response) {
                    if(response.code === 1){
                        if(response.data && response.data.length > 0){
                            self._afterInit(self, response.data[0], wrapper);
                        }else{
                            self._ajaxErrorNotify('请求返回数据为空');
                        }
                    }else{
                        self._ajaxErrorNotify(response.msg);
                    }
                })

            }
        },
        _setUnquieArray: function(array){
            var newArray = [];
            for(var i=0;i<array.length;i++) {
                var items=array[i];
                //判断元素是否存在于new_arr中，如果不存在则插入到new_arr的最后
                if($.inArray(items,newArray)==-1) {
                    newArray.push(items);
                }
            }
            return newArray;
        },
        /**
         * 克隆对象
         * @param obj
         * @private
         */
        _deepCopy: function(obj){
            if(typeof obj != 'object'){
                return obj;
            }
            // TODO 临时处理list
            if($.isArray(obj)) {
                var newList = [];
                for(var attr in obj) {
                    newList.push(this._deepCopy(obj[attr]));
                }
                return newList;
            } else {
                var newobj = {};
                for ( var attr in obj) {
                    newobj[attr] = this._deepCopy(obj[attr]);
                }
                return newobj;
            }
        },
        _cancleSelet : function (pid) {
            var self = this;
            var childs = $("[pqid='"+pid+"']");

            childs.each(function () {
                var checkBox = $(this).find("input[type='checkbox']");
                if(checkBox.length > 0)
                {
                    var checkboxList = $(this).find("input[type='checkbox']");
                    checkboxList.each(function () {
                        self._cancleSelet($(this).val());
                    });
                }
                else {
                    if($(this).attr("id"))
                    {
                        self._cancleSelet($(this).attr("id").replace(constants.cardPrefix,""));
                    }
                }
                $(this).remove();
            });
        },
        /**
         * 初始化相关绑定函数
         */
        _setInitFun : function(wrapper){
            var self = this;
            //点击展开情形面板的事件
            $(document).delegate('#' + constants.toggleBtnIdName, 'click', function(){
                self._unfold();
            });


            //备注显示绑定
            $(document).delegate(' .layui-extend-beizhu.situation-bz', 'mouseover', function(){
                var that = this;
                var title = $(this).data('content');
                layui.layer.tips(title, that);
            });

            /*$(document).delegate('.' + constants.delChoiceBtnClassName, 'click', function(){
                var name = $(this).data('name');
                var pid = $(this).data('pid');
                $(this).remove();
                wrapper.find('input:radio[name='+name+']').each(function(index, dom){
                    $(dom)[0].checked = false;
                })

                self._cancleSelet(pid);
                layui.form.render('radio');
                self._end();
            })*/

            //选择radio或者checkbox事件绑定
            layui.form.on('radio('+constants.layFilter+')', function(res){
                if(self.option.widgetClickCallBack)
                {
                    self.option.widgetClickCallBack();
                }
                var $elem = $(res.elem)
                var childs = $elem.data('content');
                var pid = $elem.data('pid');
                var inputName = $elem.attr('name');
                var $header = $elem.parents('.layui-card-body:first').prev();
                $header.addClass("_cTop")

                self._cancleSelet(pid);
                $("[pqid=\'"+pid+"\']").remove();
                var elemList = self._initNodeElem(childs,pid,false).reverse();
                for(var i in elemList)
                {
                    $("#"+constants.cardPrefix+pid).after(elemList[i]);
                }

                //添加清除选择按钮
                /*var isValid = $elem.data('valid');
                var $header = $elem.parents('.layui-card-body:first').prev();
                if(!isValid && $header.find('.' + constants.delChoiceBtnClassName).length == 0){
                    var $btn = $('<button></button>', {type: 'button'}).addClass('layui-btn layui-btn-sm layui-btn-primary').addClass(constants.delChoiceBtnClassName)
                        .append(constants.delChoiceText)
                        .data('pid', pid)
                        .data('name', inputName)
                        .css({'margin-left': '20px'})
                    ;
                    $header.append($btn);
                }*/
                layui.form.render();
                self._end();
            });
            layui.form.on('checkbox('+constants.layFilter+')', function(res){
                if(self.option.widgetClickCallBack)
                {
                    self.option.widgetClickCallBack();
                }
                var $elem = $(res.elem)
                var childs = $elem.data('content');
                var pid = $elem.data('pid');
                var inputName = $elem.attr('name');
                var $header = $elem.parents('.layui-card-body:first').prev();
                $header.addClass("_cTop")

                self._cancleSelet(pid);
                $("[pqid=\'"+pid+"\']").remove();
                if(res.elem.checked)
                {
                    var elemList = self._initNodeElem(childs,pid,false).reverse();
                    for(var i in elemList)
                    {
                        $("#"+constants.cardPrefix+inputName.replace(self.option.inputName+"_","")).after(elemList[i]);
                    }
                }
                layui.form.render();
                self._end();
            });

        },
        /**
         * @private
         * @desc Ajax请求失败的处理
         * @param {Object} self - 插件内部对象
         * @errorThrom {string} errorThrown - Ajax的错误输出内容
         */
        _ajaxErrorNotify: function(msg) {
            alert(msg || '请求失败');
        },
    })

    /**
     * 初始化方法
     * @param wrapper
     * @param source
     * @param option
     */
    function prefixSituationWrapper(wrapper,option)
    {
        this._setOption(option);
        this._setInitRecord(wrapper);
        this._setInitFun(wrapper);
    }

    function isNumber(val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }

    }

    $.fn.prefixSituationWrapper = function(option)
    {
        if(typeof option == 'object')
        {
            this.each(function()
            {
                this.wrapper = new prefixSituationWrapper($(this),option);
            });
        }
        else if(typeof option ==  'string' && option == 'clear')
        {
            this.each(function()
            {
                if(this.wrapper)
                {
                    this.wrapper.clear();
                    delete this.wrapper;
                }
            });
        }

        return this;
    }

    String.prototype.signMix= function() {
        if(arguments.length === 0) return this;
        var param = arguments[0], str= this;
        if(typeof(param) === 'object') {
            for(var key in param)
                str = str.replace(new RegExp("\\{" + key + "\\}", "g"), param[key]);
            return str;
        } else {
            for(var i = 0; i < arguments.length; i++)
                str = str.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
            return str;
        }
    }
})(window.jQuery);
