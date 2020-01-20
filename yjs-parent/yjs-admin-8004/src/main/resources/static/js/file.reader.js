// 添加全局站点信息
;(function($){
    "use strict";

    /**
     * 内部函数定义
     */
    $.extend(fileReader.prototype,{

        /**
         * @private
         * @desc 参数使用默认值进行初始化与合并
         * @param {string|Object} source - 数组源（URL或JSON格式数据源）
         * @param {Object} option - 初始化参数
         * @return {Object} - 合并后的参数
         */
        _setOption1st: function(option) {
            return $.extend({
                candel: false,
                itemClass : "",
                baseViewUrl: WEB_ROOT + '/sysAttach/fileDownload?token='+window.localStorage.getItem('tokenCode')+'&fileId='
            },option);
        },
        _setOption: function(option){
            option = this._setOption1st(option);
            this.option = option;
        },
        _initReader :function (obj,dataJson) {
            var pictures = ['gif','jpg','jpeg','bmp','png'];
            //绿建公司插件支持
            var lvJianSupports = ['dwg'];
            var option = this.option;
            for(var data in dataJson)
            {
                var $li;
                var $img;

                var fileData = dataJson[data];
                //工程文件dwg跳转新html页面打开
                if($.inArray(fileData.nameExt,lvJianSupports)>=0){
                    $li = $(
                        '<div id="' + fileData.idNum + '" class="file-item thumbnail '+option.itemClass+'" data-id="' + fileData.idNum + '"  title="'+fileData.name+'">' +
                        '<div class="info">' + fileData.name + '</div>' +
                        '<a target="_blank" href="'+ HOST_PATH +'/html/business/dwg_preview.html?idNum='+ fileData.idNum +'"><img></a>' +
                        '<span class="success"></span>' +
                        '</div>'
                        ),
                        $img = $li.find('img');
                }else if (option.isLicense){
                    $li = $(
                        '<div id="' + fileData.idNum + '" class="file-item thumbnail '+option.itemClass+'" data-id="' + fileData.idNum + '"  title="'+fileData.name+'">' +
                        '<div class="info">' + fileData.name + '</div>' +
                        '<a target="_blank" href="'+option.baseViewUrl+fileData.idNum+'"><img></a>' +
                        '<div class="licenseInfo">' + '<a target="_blank" class="licenseView" href="javascript:void(0);" data-sblsh="'+fileData.sblsh+'" data-licenseCode="'+fileData.licenseViewContent+'">联网查看电子证照</a>' + '</div>' +
                        '<span class="success"></span>' +
                        '</div>'
                    ),
                        $img = $li.find('img');
                }else{
                    $li = $(
                        '<div id="' + fileData.idNum + '" class="file-item thumbnail '+option.itemClass+'" data-id="' + fileData.idNum + '"  title="'+fileData.name+'">' +
                        '<div class="info">' + fileData.name + '</div>' +
                        '<a target="_blank" href="'+option.baseViewUrl+fileData.idNum+'"><img></a>' +
                        '<span class="success"></span>' +
                        '</div>'
                        ),
                        $img = $li.find('img');
                }

                if(option.candel)
                {
                    var $btns = $('<div class="file-panel">' +
                        '<span data-id="'+fileData.idNum+'" class="cancel">删除</span>' +
                        '</div>').appendTo($li);
                }

                if($.inArray(fileData.nameExt,pictures)>=0){
                    $img.attr( 'src', option.baseViewUrl+fileData.idNum ).addClass("noImgThumb");
                }else{
                    $img.attr( 'src', "/resource/images/affair/file-common.png" ).addClass("noImgThumb");
                }

                obj.find(".uploader-list").append( $li );
            }

            $(document).delegate('.cancel', 'click', function(){
                 $(this).parents('.file-item').remove();

            });

            $(document).delegate('.file-item', 'mouseenter', function(){
                var $btns = $(this).find('.file-panel');
                $btns.stop().animate({height: 30});
            });

            $(document).delegate('.file-item', 'mouseleave', function(){
                var $btns = $(this).find('.file-panel');
                $btns.stop().animate({height: 0});
            });

            $(document).undelegate('.licenseView', 'click');
            $(document).delegate('.licenseView', 'click', function(){
                var sblsh = $(this).attr('data-sblsh');
                var licenseCode = $(this).attr('data-licenseCode');
                var mark = top.layer.msg('处理中，请稍候...', {
                    icon: 16,
                    shade: [0.3, '#f5f5f5'],
                    scrollbar: false,
                    time: false
                });
                setTimeout(function () {
                    globalSyncPost(WEB_ROOT + "/license/getLicenseView",{sblsh : sblsh,licenseCode:licenseCode},
                        function (result) {
                            top.layer.close(mark);
                            if (result.code == 1) {
                                window.open(result.data,'_blank');
                            }else {
                                top.layer.msg('获取电子证照地址失败', {icon: 5});
                            }
                        });
                },1);
            });

        },
        _msg:function (msg) {
            alert(msg);
        }
    })



    /**
     * 初始化方法
     * @param wrapper
     * @param source
     * @param option
     */
    function fileReader(obj,dataJson,option)
    {
        this._setOption(option);
        this._initReader(obj,dataJson);
    }

    $.fn.fileReader = function(dataJson,option)
    {
        if(typeof option == 'object')
        {
            this.each(function()
            {
                this.obj = new fileReader($(this),dataJson,option);
            });
        }
        else if(typeof option ==  'string' && option == 'clear')
        {
            this.each(function()
            {
                if(this.obj)
                {
                    this.obj.clear();
                    delete this.obj;
                }
            });
        }
        return this;
    }

})(window.jQuery);