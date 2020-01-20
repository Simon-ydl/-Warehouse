$(function () {
    //popover
    $(document).delegate('[data-toggle="popover"]', 'mouseover', function () {
        var that = this;
        var title = $(this).data('title');
        layui.layer.tips(title, that);
    });
    //相册查看
    $(document).delegate('[data-toggle="photos"]', 'click', function () {

        var dataList = $(this).data('content');

        if (dataList && dataList.length > 0) {

            var title = $(this).data('title') || '图片预览';
            var id = $(this).data('id') || '';
            var start = $(this).data('start') || 0;
            var json = {title: title, id: id, start: start, data: []}
            for (var i in dataList) {
                var data = dataList[i];
                var src = WEB_ROOT + "/sysAttach/fileDownload?token="
                    + window.localStorage.getItem('tokenCode') + '&fileId=' + data.idNum; //原图地址
                var photo = {alt: data.name, pid: data.idNum, src: src};
                json.data.push(photo);
            }
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        } else {
            layui.layer.msg('没有相关数据');
        }
    })

    /**
     * 查看佐证信息
     */
    $(document).delegate('.showThirdData', 'click', function(){
        var $this = $(this);
        var matMainId = $this.data('matMainId') || $this.data('matmainid');
        var content = $this.data('content');
        var title = '查看佐证信息';
        var url = '/html/business/sldj/xcdj/thirdData_view.html?_=' + Math.random();
        top.openDialog4Call(url, title, 800, 600,
            function(layero, index) {
                // 初始化文件
                var $viewContent = $("#view-content", layero.find("iframe")[0].contentWindow.document);
                $viewContent.showThirdData({
                    "dataJson":content,
                    "matMainId":matMainId
                })
            },function (layero, index) {

            })
    })

    //上传的附件展示
    $(document).delegate('.showElectrics', 'click', function(){
        var electricList = $(this).data('content');
        var $content = $('<div></div>');
        $content.append('<div class="uploader-list"></div>');

        if(typeof electricList == 'string') {
            electricList = JSON.parse(electricList);
        }

        if($.isArray(electricList) && electricList.length > 0) {
            $content.fileReader(electricList, {});

            var layerConfig = {
                title: "查看电子件",
                content: $content.html(),
                btn: ["关闭"]
            }

            if(electricList.length <= 3) {
                layerConfig.area = '500px'; //加上auto会使部分界面高度过小
            } else {
                layerConfig.maxWidth = window.innerWidth * 0.8;
                layerConfig.maxHeight= window.innerHeight * 0.8;
            }
            layer.open(layerConfig);
        }
    })

    //上传的附件展示
    $(document).delegate('.showLicense', 'click', function(){
        var sblsh = UrlParam.param("sblsh");
        var electricList = $(this).data('content');
        var $content = $('<div></div>');
        $content.append('<div class="uploader-list"></div>');

        if(typeof electricList == 'string') {
            electricList = JSON.parse(electricList);
        }
        if(sblsh){
            for (var i = 0; i < electricList.length; i++) {
                var e = electricList[i];
                e.sblsh = sblsh;
            }
        }
        if($.isArray(electricList) && electricList.length > 0) {
            var width = window.innerWidth * 0.8;
            var height = window.innerHeight * 0.8;

            $content.fileReader(electricList, {isLicense:true});
            layer.open({
                title: "查看电子件",
                maxWidth: width,
                maxHeight: height,
                content: $content.html(),
                btn: ["关闭"]
            });
        }
    })

// 第三方表单 显示
    $(document).delegate('.btn-formTb', 'click', function(){
        var tbid = $(this).data('tbid')
            ,rowid = $(this).data('rowid')
            ,formType = $(this).data('formType')
            ,matMainId = $(this).data('matMainId')
            ,sblsh = $(this).data('sblsh')
            ,bsr = $(this).data('bsr')
        if(formType == '2'){
            if(typeof bsr == 'string') {
                bsr = JSON.parse(bsr);
            }
            //第三方表单
            globalSyncPost(WEB_ROOT + '/affairItemMaterialMain/read', {id: matMainId}, function(fdResponse){
                var itemCode =  fdResponse.data.itemcode
                if(fdResponse.code == '1'){
                    var url = fdResponse.data.dzbddz;
                    url += (url.indexOf("?") > 0 ? "&" : "?") + "sxbm=" +itemCode ;
                    url += "&sblsh=" + sblsh;
                    url += "&rowid=" + rowid;
                    url += "&readOnly=1";
                    if(bsr){
                        url += "&bsType=" + bsr.type;
                        url += "&bsCardType=" + bsr.cardType;
                        url += "&bsNum=" + bsr.cardNum;
                    }
                    url += "&winCommunicationType=1";
                    console.log(url);
                    top.showDialog(url, '查看表单详细',null,null,null,null);
                }
            })
        }else {
            //本地表单
            var url = '/html/form/render.html?tbid=' + tbid + '&rowid=' + rowid + '&readOnly=1';
            top.showData(url, '查看表单详细');
        }
    })


  /*  // 表单查看
    $(document).delegate('.btn-formTb', 'click', function(){
        var tbid = $(this).data('tbid')
            ,rowid = $(this).data('rowid')

        var url = '/html/form/render.html?tbid=' + tbid + '&rowid=' + rowid;
        top.showData(url, '查看表单详细');
    })*/





    $(document).delegate('.btn-formTb-local', 'click', function(){
        var tbid = $(this).data('formId')
            ,rowid = $(this).data('rowId')

        var url = '/html/form/render.html?tbid=' + tbid + '&rowid=' + rowid + '&readOnly=1';
        top.showData(url, '查看表单详细');
    });



    // 情形查看
    $(document).delegate('.btn-view-situation', 'click', function() {
        var $this = $(this);
        var situationId = $this.data('situationids') || '';
        var ywqxId = $this.data('ywqxid') || '';

        if(ywqxId && ywqxId.length > 0) {
            var $situationDiv = $("<div></div>");
            $situationDiv.situationWrapper({
                source: WEB_ROOT + '/affairItemSituation/v3/datajson/view/list'
                ,showTitle:false
                ,param: {ywqxId: ywqxId}
                ,echo: situationId.split(',') //回显
                ,inputDisabled : true
                ,afterInit : function () {
                    var cfg = {
                        btnAlign: 'c',
                        btn: ['关闭'],
                        yes: function(index, layero){
                            top.hideData();
                        }
                    }
                    top.showDialog("/html/business/ywsp/situation_view.html","情形查看",function (layero, index) {
                        var $viewDiv = $("#viewDiv", layero.find("iframe")[0].contentWindow.document);
                        $viewDiv.append($situationDiv);

                        layero.find("iframe")[0].contentWindow.render();
                    } , null , cfg);

                    /*top.openDialog4Call("/html/business/ywsp/situation_view.html","情形查看",null,null,
                        function (layero, index) {
                            var $viewDiv = $("#viewDiv", layero.find("iframe")[0].contentWindow.document);
                            $viewDiv.append($situationDiv);

                            layero.find("iframe")[0].contentWindow.render();
                        },
                        function (layero, index) {}
                    );*/
                }
            });
        }
    });

    // 新版前置条件查看
    $(document).delegate('.btn-view-precondition', 'click', function() {
        var $this = $(this);
        var prefixSelectIds = $this.data('prefixselectids');
        var prefixId = $this.data('prefixid');

        if(prefixId && prefixId.length > 0) {
            var $preconditionDiv = $("<div></div>");
            $preconditionDiv.prefixSituationWrapper({
                source: WEB_ROOT + '/affairItemPrefixCondition/v3/datajson/view/list'
                ,showTitle:false
                ,param: {prefixId: prefixId}
                ,echo: prefixSelectIds.split(',') //回显
                ,inputDisabled : true
                ,afterInit : function () {
                    var cfg = {
                        btnAlign: 'c',
                        btn: ['关闭'],
                        yes: function(index, layero){
                            top.hideData();
                        }
                    }
                    top.showDialog("/html/business/ywsp/situation_view.html","前置条件查看",function (layero, index) {
                        var $viewDiv = $("#viewDiv", layero.find("iframe")[0].contentWindow.document);
                        $viewDiv.append($preconditionDiv);

                        layero.find("iframe")[0].contentWindow.render();
                    } , null , cfg);

                    /*top.openDialog4Call("/html/business/ywsp/situation_view.html","前置条件查看",null,null,
                        function (layero, index) {
                            var $viewDiv = $("#viewDiv", layero.find("iframe")[0].contentWindow.document);
                            $viewDiv.append($preconditionDiv);

                            layero.find("iframe")[0].contentWindow.render();
                        },
                        function (layero, index) {}
                    );*/
                }
            });
        }
    });

    //查看缴费通知书
    $(document).delegate('.feePayView', 'click', function () {
        var $self = $(this);

        if ($self.hasClass('layui-btn-disabled')) {
            return;
        }

        var sblsh = $self.data('sblsh');
        var pnNo = $self.data('pnno');

        globalSyncPost(WEB_ROOT + '/applyReceipt/getPayFeeItemAttachId', { sblsh: sblsh, pnNo: pnNo }, function (response) {
            if (response.code != 1 || !response.data) {
                layer.alert('不能兼容旧数据, 无法查看');
                return;
            }

            var url = WEB_ROOT + '/sysAttach/fileDownload?fileId=' + response.data + "&token=" + window.localStorage.getItem('tokenCode');
            window.open(url)
        });
    })
});

// 初始化办事人
function initBsr(applyBase) {
    var bsr = {};
    if(applyBase){
        bsr.name = applyBase.bsName;
        bsr.cardType = applyBase.bsCardType;
        bsr.cardNum = applyBase.bsNum;
        bsr.type = applyBase.bsType;
    }
    return bsr;
}

/**
 * 初始化材料电子件材料按钮
 * @param data
 * @param extra
 * @returns {jQuery|HTMLElement|boolean}
 */
function initMaterialElectricBtn(data,extra){
    var bsr = false;
    if(extra){
        bsr = extra.bsr;
    }
    var electricList = (data
        && data.electrics && data.electrics.length > 0) ? data.electrics : false;
    var $oper = false;
    if (electricList) {
        if (data.source == 2) {
            //$oper = $('<a href="' + data.licenseUrl + '" target="_blank" data-title="已关联证照" data-toggle="popover" class="showLicense">查看</a>');
            // 附件显示签名后PDF文件，佐证材料 和 电子证照则显示原文件（不需要签名）
            $oper = $('<a href="javascript:void(0);" data-title="已提交了'
                + electricList.length + '个附件，点击查看详细" data-toggle="popover" class="layui-btn layui-btn-xs showLicense">查看('+electricList.length+')</a>')
                .data('content', !!data.signElectrics ? data.signElectrics : electricList);
        }else if(data.source == 4){
            $oper = $('<a href="javascript:void(0);" data-title="已提交了'
                + electricList.length + '个附件，点击查看详细" data-toggle="popover" class="layui-btn layui-btn-xs showElectrics">查看('+electricList.length+')</a>')
                .data('content', !!data.signElectrics ? data.signElectrics : electricList);
        } else {
            // 附件显示签名后PDF文件，佐证材料 和 电子证照则显示原文件（不需要签名）
            $oper = $('<a href="javascript:void(0);" data-title="已提交了'
                + electricList.length + '个附件，点击查看详细" data-toggle="popover" class="layui-btn layui-btn-xs showElectrics">查看('+electricList.length+')</a>')
                .data('content', !!data.signElectrics ? data.signElectrics : electricList);
        }
    }else if(data && data.source == 3){
        if(data.dataContent){
            $oper = $('<a href="javascript:void(0);" target="_blank" data-title="已联网校验" data-toggle="popover" class="layui-btn layui-btn-xs showThirdData">查看(1)</a>')
                .data('content',data.dataContent).data('matMainId',data.matMainId);
        }else{
            $oper = $('<a href="javascript:void(0);" data-title="该材料没有上传附件" data-toggle="popover" class="layui-btn layui-btn-xs layui-btn-disabled">查看</a>')
        }
    } else if(data && (data.formTbId||data.formRowId)){
        if(data.formRowId){
            $oper = $('<a href="javascript:void(0);" data-title="该材料已提交表单" data-toggle="popover" class="layui-btn layui-btn-xs">查看(1)</a>')
                .addClass('btn-formTb')
                .data('rowid', data.formRowId)
                .data('formType', data.formType)
                .data('matMainId', data.matMainId)
                .data('sblsh', data.sblsh)
                .data('bsr',bsr);
            if(data.formTbId){
                $oper.data('tbid', data.formTbId);
            }
        } else {
            $oper = $('<a href="javascript:void(0);" data-title="该材料没有提交表单" data-toggle="popover" class="layui-btn layui-btn-xs layui-btn-disabled">查看(0)</a>')
        }
    } else {
        $oper = $('<a href="javascript:void(0);" data-title="该材料没有上传附件" data-toggle="popover" class="layui-btn layui-btn-xs layui-btn-disabled">查看(0)</a>')
    }
    return $oper;
}

    /**
     * 跳转页面，并且将地址栏的参数全部传过去
     * @param href
     */
    function toHrefTakeParam(href) {
        var params = UrlParam.paramMap();
        if (!$.isEmptyObject(params)) {
            var paramSerial = '';
            if (href.lastIndexOf('?') > -1) {
                paramSerial = '&';
            } else {
                paramSerial = '?';
            }
            for (var name in params) {
                var value = params[name].join();
                paramSerial += name + '=' + encodeURIComponent(value)  + '&';
            }
            paramSerial = paramSerial.substr(0, paramSerial.length - 1);
            href += paramSerial;
        }
        location.href = href;
    }

    /**
     * 动态载入js
     * @param url
     * @param callback
     */
    function loadScript(url, callback) {
        var script = document.createElement("script");
        script.type = "text/javascript";
        if (typeof(callback) != "undefined") {
            if (script.readyState) {
                script.onreadystatechange = function () {
                    if (script.readyState == "loaded" || script.readyState == "complete") {
                        script.onreadystatechange = null;
                        callback();
                    }
                };
            } else {
                script.onload = function () {
                    callback();
                };
            }
        }
        ;
        script.src = url;
        document.body.appendChild(script);
    }

    function msg(msg, title, timeout, callback) {
        if (title) {
            layer.open
            ({
                type: 1,
                offset: 'rb',//右下
                title: (title ? title : '提示'),
                content: '<div style="padding:20px 20px;">' + msg + '</div>',
                shade: 0,
                time: (timeout ? timeout : 3000),
                anim: 2,//从最底部往上滑入
            });
        }
        else
            layer.msg(msg, {
                time: (timeout ? timeout : 3000), end: function () {
                    if (typeof callback == 'function') {
                        callback()
                    }
                }
            });
    }

    /**
     * 获取项目根路径
     * @returns
     */
    function getRootPath() {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht + projectName);
    }

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    }

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(),    //day
            "h+": this.getHours(),   //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }
