/**
 * 用于回显普通的json数据，不适用于jsonArray
 */
;(function($){
    /**
     * 内部函数定义
     */
    $.extend(showThirdData.prototype,{
        /**
         * @private
         * @desc 参数使用默认值进行初始化与合并
         * @param {Object} option - 初始化参数
         * @return {Object} - 合并后的参数
         */
        _setOption: function(options) {
            //判断是否属于json对象
            if(options.dataJson && typeof options.dataJson != 'object'){
                options.dataJson = JSON.parse(options.dataJson);
            }
            if(options.resultParamJson && typeof options.resultParamJson != 'object'){
                options.resultParamJson = JSON.parse(options.resultParamJson);
            }
            this._settings = $.extend({
                'dataJson': {},//源数据
                'resultParamJson': {},//结果字段名称
                'matMainId': false//材料要素ID
            }, options);
        },
        /**
         * 获取返回结果字段
         * @param 材料要素Id
         * @private
         */
        _getResultParamJson: function(matMainId) {
            var $this = this;
            var resultParamUrl = WEB_ROOT + '/dirInterface/api/comm/resultParamRead';
            globalSyncPost(resultParamUrl, {matMainId:matMainId}, function (response) {
                if(response.code == 1){
                    $this._settings.resultParamJson = response.data;
                }
            })
        },
        /**
         * 初始化table
         * @param wrapper
         * @private
         */
        _initTable: function($wrapper) {
            var $this = this;
            if($this._settings.matMainId){
                $this._getResultParamJson($this._settings.matMainId);
            }
            //遍历源数据
            var $div = $('<div class="matters-content"></div>');
            var $table = $('<table class="layui-table"></table>');
            var $tbody = $('<tbody></tbody>');
            for (var key in $this._settings.dataJson){
                if(typeof $this._settings.dataJson[key] == 'object'){
                    //如果是obj对象的继续遍历
                    if(!$.isArray($this._settings.dataJson[key])) {
                        var obj = $this._settings.dataJson[key];
                        for (var key2 in obj){
                            var $tr = $('<tr></tr>');
                            var $th = $('<th></th>');
                            if($this._settings.resultParamJson && $this._settings.resultParamJson[key2]){
                                $th.html($this._settings.resultParamJson[key2]);
                            }else {
                                $th.html(key2);
                            }
                            var $td = $('<td></td>');
                            if(obj[key2] && $this._checkUrl(obj[key2]) ){
                                var $a = $('<a></a>',{href:obj[key2],target:'_blank'}).html(obj[key2])
                                $td.append($a);
                            }else {
                                $td.html(obj[key2]);
                            }
                            $tr.append($th).append($td);
                            $tbody.append($tr);
                            $table.append($tbody);
                            $div.append($table);
                        }
                    }else {
                        var $tr = $('<tr></tr>');
                        var $th = $('<th></th>');
                        if($this._settings.resultParamJson && $this._settings.resultParamJson[key]){
                            $th.html($this._settings.resultParamJson[key])
                        }else {
                            $th.html(key);
                        }
                        var $td = $('<td></td>');
                        if($this._settings.dataJson[key] && $this._checkUrl($this._settings.dataJson[key]) ){
                            var $a = $('<a></a>',{href:$this._settings.dataJson[key],target:'_blank'}).html($this._settings.dataJson[key])
                            $td.append($a);
                        }else {
                            $td.html($this._settings.dataJson[key]);
                        }
                        $tr.append($th).append($td);
                        $tbody.append($tr);
                        $table.append($tbody);
                        $div.append($table);
                    }
                }else {
                    var $tr = $('<tr></tr>');
                    var $th = $('<th></th>');
                    if($this._settings.resultParamJson && $this._settings.resultParamJson[key]){
                        $th.html($this._settings.resultParamJson[key])
                    }else {
                        $th.html(key);
                    }
                    var $td = $('<td></td>');
                    //$this._settings.dataJson[key].indexOf('http') != -1
                    if($this._settings.dataJson[key] && $this._valiImgType($this._settings.dataJson[key]) )
                    {
                        var $a = $('<img/>',{src:$this._settings.dataJson[key],target:'_blank'})
                        $td.append($a);
                    }
                    else if($this._settings.dataJson[key] && $this._checkUrl($this._settings.dataJson[key]) ){
                        var $a = $('<a></a>',{href:$this._settings.dataJson[key],target:'_blank'}).html($this._settings.dataJson[key])
                        $td.append($a);
                    }
                    else {
                        $td.html($this._settings.dataJson[key]);
                    }
                    $tr.append($th).append($td);
                    $tbody.append($tr);
                    $table.append($tbody);
                    $div.append($table);
                }
            }
            $wrapper.append($div);
        },
        /**
         * 校验字符串是否为url地址
         * @param str
         * @private
         */
        _checkUrl:function (str) {
            var reg = /(ht|f)tp(s?)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-\.\?\,\'\/\\\+&amp;%\$#_]*)?/;
            return reg.test(str);
        },
        _valiImgType : function(str){
            return /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(str);
        }
    });
    /**
     * 构造函数
     * @param $wrapper
     * @param option
     */
    function showThirdData($wrapper, options) {
        this._setOption(options);
        this._initTable($wrapper);
        layui.form.render();
    }
    /**
     * 扩展jquery方法
     * @param option
     * @returns {Window.jQuery}
     */
    $.fn.showThirdData = function(options) {
        if(typeof options == 'object') {
            this.each(function() {
                this.obj = new showThirdData($(this), options);
            });
        } else if(typeof options ==  'string' && options == 'clear') {
            this.each(function() {
                if(this.obj) {
                    this.obj.clear();
                    delete this.obj;
                }
            });
        }
        return this;
    }
})(window.jQuery);