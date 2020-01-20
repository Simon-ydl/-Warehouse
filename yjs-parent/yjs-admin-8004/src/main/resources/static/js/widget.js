/**
 * 加载数据
 * @type {{getTown}} 获取镇街
 */
var Widget = function () { // url参数
    var urlConfig
        , param
        , async = false
        , contentType = false
        , widgetMask = false
    ;
    (function init() {
        urlConfig = {
            org: {
                findByCode: WEB_ROOT + '/affairThird/group/code/get'
                , findById: WEB_ROOT + '/affairThird/group/id/get'
            },
            hall: {
                list: WEB_ROOT + '/affairServiceBusHall/ajaxList'
            },
            busGroup: {
              list:   WEB_ROOT + '/affairServiceBusGroup/ajaxList'
            },
            dict: {
                list: WEB_ROOT + '/sysDict/ajaxDict'
            }
        };
        param = {};

    })();

    var connector = {
        post: function (url, param, async, contentType, successCallback) {
            widgetMask = layui.layer.load(2);
            globalPost(url, param, async, contentType, function(response){
                layui.layer.close(widgetMask);
                successCallback(response);
            });
        }

    }

    var org = {
        getByCode: function (code, lx) {
            var findCode = code || 'zjyq'
                , type = lx || '2'
                , param = {parentCode: findCode, extDeptLx: type}
                , data = false;
            connector.post(urlConfig.org.findByCode, param, async, contentType, function (response) {
                if (response.code === 1) {
                    data = response.data;
                }
            });
            return data;
        },
        getById: function (id, lx) {
            var findId = id || 'zjyq'
                , type = lx || '1'
                , param = {id: findId, extDeptLx: type}
                , data = false;
            connector.post(urlConfig.org.findById, param, async, contentType, function (response) {
                if (response.code === 1) {
                    data = response.data;
                }
            });
            return data;
        },
        town: function () {
            var findCode = 'DGS924'
                , type = '2'
                , param = {parentCode: findCode, extDeptLx: type}
                , data = [];
            var dgs = {};
            dgs.extDeptJgdz = "";
            dgs.extDeptLx = "2";
            dgs.groupCode = "441900";
            dgs.id = "0";
            dgs.groupType = "zzjg";
            dgs.state = "1";
            dgs.groupName = "东莞市";
            dgs.nodeName = "东莞市";
            dgs.nodeId = "0";
            dgs.parentId = "0";
            data.push(dgs)

            connector.post(urlConfig.org.findByCode, param, async, contentType, function (response) {
                if (response.code === 1) {
                    data = data.concat(response.data);
                }
            });

            var findCode = 'DGSZJJCF'
                , param2 = {parentCode: findCode, extDeptLx: type}
            connector.post(urlConfig.org.findByCode, param2, async, contentType, function (response) {
                if (response.code === 1) {
                    data = data.concat(response.data);
                }
            });
            return data;
        }
    }

    var hall = {
        list: function (townGroupId) {
            if (townGroupId) {
                var param = {townGroupId: townGroupId}
                    , data = false
                ;
                connector.post(urlConfig.hall.list, param, async, contentType, function (response) {
                    if (response.code === 1) {
                        data = response.data;
                    }
                });
                return data;
            }
        }
    }

    var busGroup = {
        list: function (hallId) {
            if (hallId) {
                var param = {hallId: hallId}
                    , data = false
                ;
                connector.post(urlConfig.busGroup.list, param, async, contentType, function (response) {
                    if (response.code === 1) {
                        data = response.data;
                    }
                });
                return data;
            }
        }
    }

    var dict = {

        list: function (code) {
            if (code) {
                var param = {dictRootName: code, sel: true}
                    , data = false
                ;
                connector.post(urlConfig.dict.list, param, async, contentType, function (response) {
                    if (response) {
                        data = response;
                    }
                });
                return data;
            }
        },
        get: function (code) {
            if (code) {
                var param = {dictRootName: code, sel: true}
                    , data = false
                ;
                connector.post(urlConfig.dict.list, param, async, contentType, function (response) {
                    if (response) {
                        data = {};
                        for (var dt in response) {
                            var obj = response[dt];
                            data[obj.dictValue] = obj.dictName;
                        }
                    }
                });
                return data;
            }
        },
        getDetail: function (code) {
            if (code) {
                var param = {dictRootName: code, sel: true}
                    , data = false
                ;
                connector.post(urlConfig.dict.list, param, async, contentType, function (response) {
                    if (response) {
                        data = {};
                        for (var dt in response) {
                            var obj = response[dt];
                            data[obj.dictValue] = obj;
                        }
                    }
                });
                return data;
            }
        }

    }

    var fn_initSelect = function (valueField, textField, selectDoms, arrayData, selectValue) {

        if(selectDoms  && selectDoms.length > 0){
            $.each(selectDoms, function (index, dom) {
                var $selectDom = $(dom).empty().append('<option value="">请选择</option>')
                if (arrayData && arrayData.length > 0) {
                    for (var td in arrayData) {
                        var data = arrayData[td];
                        var $option = $('<option></option>', {value: data[valueField]}).text(data[textField]);
                        if (selectValue && selectValue === data[valueField]) {
                            $option.prop('selected', true);
                        }
                        $selectDom.append($option);
                    }
                }
            });
            layui.form.render();
        }
    }

    var fn = {
        initSelect: function (doms, arrayData, selectValue, valueField, textField) {
            if(doms && doms.length > 0){
                $.each(doms, function (index, dom) {
                    var $dom = $(dom).empty().append('<option value="">请选择</option>')
                    if (arrayData && arrayData.length > 0) {
                        for (var td in arrayData) {
                            var data = arrayData[td];
                            var $option = $('<option></option>', {value: data[valueField]}).text(data[textField]);
                            if (selectValue && selectValue == data[valueField]) {
                                $option.prop('selected', true);
                            }
                            $dom.append($option);
                        }
                    }
                });
                layui.form.render();
            }
        },
        initRadio: function(doms, arrayData, checkValue, inputName, valueField, textField){
            if (doms && doms.length > 0) {
                var valField = valueField || 'dictValue'
                    ,txtField = textField || 'dictName';
                $.each(doms, function (index, dom) {
                    var $dom = $(dom).empty();
                    for (var i in arrayData) {
                        var obj = arrayData[i];
                        var $childEle = $('<input type="radio" />').val(obj[valField]).attr({
                            name: inputName,
                            'lay-filter': inputName,
                            'title': obj[txtField]
                        });
                        if (checkValue && checkValue == obj[valField]) {
                            $childEle.prop('checked', true);
                        }
                        $dom.append($childEle);
                    }
                });
                layui.form.render();
            }
        },
        initCheckbox: function(doms, arrayData, checkValue, inputName, valueField, textField){
            if (doms && doms.length > 0) {
                var valField = valueField || 'dictValue'
                    ,txtField = textField || 'dictName';
                $.each(doms, function (index, dom) {
                    var $dom = $(dom).empty();
                    for (var i in arrayData) {
                        var obj = arrayData[i];
                        var $childEle = $('<input type="checkbox" lay-skin="primary"/>').val(obj[valField]).attr({
                            name: inputName,
                            'lay-filter': inputName,
                            'title': obj[txtField]
                        });
                        if (checkValue && checkValue.indexOf(obj[valField]) > -1) {
                            $childEle.prop('checked', true);
                        }
                        $dom.append($childEle);
                    }
                });
                layui.form.render();
            }
        }
    }

    return {
        data: {
            org: org
            , hall: hall
            , dict: dict
        },
        fn: {
            /**
             * 初始化镇街select
             * @param selectValue 默认选中的值
             * @param ele 初始的父级jquery元素
             */
            initTownSelect: function (selectValue, ele) {
                var $ele = ele || $('[widget=townSelect]');
                if($ele && $ele.length > 0){
                    var arrayData = org.town();
                    fn.initSelect($ele, arrayData, selectValue, 'id', 'groupName');
                    return $ele;
                }
            },
            /**
             * 初始化镇街select
             * @param townGroupId 所属上级ID
             * @param selectValue 默认选中的值
             * @param ele 初始的父级jquery元素
             */
            initDeptSelect: function(townGroupId, selectValue, ele){
                var $ele = ele || $('[widget=deptSelect]');
                if($ele && $ele.length > 0){
                    var arrayData = org.getById(townGroupId);
                    fn.initSelect($ele, arrayData, selectValue, 'id', 'groupName');
                    return $ele;
                }
            },
            /**
             * 初始化大厅select
             * @param townGroupId 所属镇街ID
             * @param selectValue 默认选中的值
             * @param ele 初始的父级jquery元素
             */
            initHallSelect: function (townGroupId, selectValue, ele) {
                var $ele = ele || $('[widget=hallSelect]');
                if($ele && $ele.length > 0){
                    var arrayData = hall.list(townGroupId);
                    fn.initSelect($ele, arrayData, selectValue, 'id', 'dtmc');
                    return $ele;
                }
            },
            /**
             * 初始化窗口组select
             * @param hallId 大厅ID
             * @param selectValue 默认选中的值
             * @param ele 初始的父级jquery元素
             */
            initBusGroupSelect: function (hallId, selectValue, ele) {
                var $ele = ele || $('[widget=busGroupSelect]');
                if($ele && $ele.length > 0){
                    var arrayData = busGroup.list(hallId);
                    fn.initSelect($ele, arrayData, selectValue, 'id', 'ckzmc');
                    return $ele;
                }
            },
            /**
             * 初始化状态radio
             * @param selectValue 默认选中的值
             * @param ele 初始的父级jquery元素
             */
            initStatusRadio: function (checkValue, ele, inputName) {
                var $ele = ele || $('[widget=dict-status]');
                if($ele && $ele.length > 0){
                    var arrayData = dict.list('STATUS')
                        ,name = inputName || 'status'
                    ;
                    fn.initRadio($ele, arrayData, checkValue, name);
                    return $ele;
                }
            },
            /**
             * 初始化承接业务类型checkbox
             * @param selectValue 默认选中的值
             * @param ele 初始的父级jquery元素
             */
            initCjywlxRadio: function (checkValue, ele, inputName) {
                var $ele = ele || $('[widget=dict-checkbox-cjywlx]')
                if($ele && $ele.length > 0){
                    var arrayData = dict.list('CJYWLX')
                        ,name = inputName || 'cjywlx'
                    ;
                    fn.initRadio($ele, arrayData, checkValue, name);
                    return $ele;
                }
            },
            dict: {
                initRadio: function(dictCode, checkValue, ele, inputName){
                    var $ele = ele || $('[dict-widget='+dictCode+']');
                    if($ele && $ele.length > 0){
                        var mrName = $ele.attr('name');
                        if($ele.length > 1){
                            mrName = $ele.get(0).attr('name');
                        }
                        var arrayData = dict.list(dictCode);
                        var name = inputName || mrName;
                        fn.initRadio($ele, arrayData, checkValue, name);
                        return $ele;
                    }
                },
                initCheckbox: function(dictCode, checkValue, ele, inputName){
                    var $ele = ele || $('[dict-widget='+dictCode+']');
                    if($ele && $ele.length > 0){
                        var mrName = $ele.attr('name');
                        if($ele.length > 1){
                            mrName = $ele.get(0).attr('name');
                        }
                        var arrayData = dict.list(dictCode);
                        var name = inputName || mrName;
                        fn.initCheckbox($ele, arrayData, checkValue, name);
                        return $ele;
                    }
                },
                initSelect: function(dictCode, selectValue, ele, inputName){
                    var $ele = ele || $('[dict-widget='+dictCode+']');
                    if($ele && $ele.length > 0){
                        var mrName = $ele.attr('name');
                        if($ele.length > 1){
                            mrName = $ele.get(0).attr('name');
                        }
                        var arrayData = dict.list(dictCode);
                        var name = inputName || mrName;
                        fn.initSelect($ele, arrayData, selectValue, 'dictValue', 'dictName');
                        return $ele;
                    }
                }
            }
        }
    }
}();

    /**
     * 公共控件js，如有需要，请引用
     * 目前含有控件：部门下拉框（可搜索）
     */
    $(function () {

        Widget.fn.initTownSelect();
        Widget.fn.initStatusRadio();
        Widget.fn.initCjywlxRadio();
    })