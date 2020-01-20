layui.use(['element', 'layer'], function () {
    var element = layui.element;
    var layer = layui.layer;
});
layui.use('form', function () {
    var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
    form.render();
    var $ = layui.$;
    var kd = $("#kd").val();
    if (kd == 0) {
        $("tr.lz").each(function () {
            $(this).css("display", "");
        });
        $("tr.kdxx").each(function () {
            $(this).css("display", "none");
        });
    } else if (kd == 1) {
        $("tr.lz").each(function () {
            $(this).css("display", "none");
        });
        $("tr.kdxx").each(function () {
            $(this).css("display", "");
        });
    }
    form.on('select(kd)', function (data) {
        var v = data.value;
        $("tr.kdxx").each(function () {
            if (v == 0) {
                $(this).css("display", "none");
            } else {
                $(this).css("display", "");
            }
        });
        $("tr.lz").each(function () {
            if (v == 1) {
                $(this).css("display", "none");
            } else {
                $(this).css("display", "");
            }
        });
    });
});




layui.use(['form'], function () {
    initSbhjData();
    initClData();
})


function setFormTime(value) {
    var retData = new Date();
    if (value) {
        retData = new Date(value);
    }
    return retData.format('yyyy-MM-dd hh:mm:ss');
}

//办理工作历史
function initSbhjData(dom) {
    var sblsh = UrlParam.param("sblsh");
    var ywlsh = UrlParam.param("ywlsh");
    var urlList = WEB_ROOT + '/applyApprovalProcess/all/listBySblshAndYwlsh';
    globalAsyncPost(urlList, {sblsh: sblsh,ywlsh:ywlsh}, function (response) {
        var strs = new Array();
        var num = 0;
        if (response.code === 1) {
            var $wrapper = dom || $('#blhjDataTable').find('tbody').empty();
            num = response.data.length;
            if (response.data && response.data.length > 0) {
                for (var d in response.data) {
                    var data = response.data[d];
                    data.index = parseInt(d) + 1;
                    var $tr = trData(response.data[d]);
                    $wrapper.append($tr);
                    strs.push(JSON.parse( '{"title":"' + data.sphjmc + '","content":"' + data.spPerName + '：' + new Date(data.spDate).format('yyyy年MM月dd日') + '"}'));
                }
                $(".ystep").loadStep({
                    size: "large",
                    color: "blue",
                    steps: strs
                });
                $(".ystep").setStep(num);
                $(".ystep li").removeAttr("data-toggle");
                layui.form.render();
            } else {
                $wrapper.append(
                    $('<tr></tr>').append(
                        $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                    )
                )
            }
        }
    });
    //$("#blhjDataDiv").templateLoadData('showApprovalProcesses');
    //$(".ystep").templateLoadData('showProcessesStep');
}

/*field_list.html*/
function trData(data) {
    var css = {'text-align': 'center'};
    var $tr = $('<tr></tr>');
    var $td0 = $('<td></td>').css(css).append(data.sphjmc);

    var spPerName;
    globalSyncPost(WEB_ROOT + '/authUser/readAuth?id=' + data.spPerId, {'tm': Math.random()}, function (result1) {
        spPerName = result1.data.userName;
    });

    var $td1 = $('<td></td>').css(css).append(spPerName);
    var $td2 = $('<td></td>').css(css).append(new Date(data.spDate).format('yyyy-MM-dd hh:mm:ss'));
    var yijianStr;
    if (data.yijian == '1') {
        yijianStr = '同意';
    }
    else if (data.yijian == '2') {
        yijianStr = '不同意';
    }
    else if (data.yijian == '3') {
        yijianStr = '回退';
    }
    else if (data.yijian == '9') {
        yijianStr = data.qitayijian;
    }
    else {
        yijianStr = '其他意见';
    }
    var $td4 = $('<td></td>').css(css);
    if(data.approvalAttachIds && data.approvalAttachIds.length > 0)
    {
        var $viewElectricBtn = $("<button class='layui-btn  layui-btn-xs'>查看</button>");
        var electIds = '';
        if(data.approvalAttachElectrics){
            var electIdsArray = data.approvalAttachElectrics;
            for (var index = 0; index < electIdsArray.length; index++) {
                electIds += electIdsArray[index].idNum + ",";
            }
        }
        $viewElectricBtn.data("electIds",electIds);

        $viewElectricBtn.on('click', function() {
            viewElectric(this);
        });
        $td4.append($viewElectricBtn);
    }
    else {
        $td4.append("未上传！");
    }

    var $td3 = $('<td></td>').css(css).append(yijianStr);
    var $td5 = $('<td></td>').css(css).append(data.bz);

    $tr.append($td0)
        .append($td1)
        .append($td2)
        .append($td3)
        .append($td4).append($td5)
    ;
    return $tr;
}

//所需材料
/*function initClData(dom) {
    var sblsh = UrlParam.param("sblsh");
    var urlList = WEB_ROOT + '/applyMaterialMain/all/list';//根据申报流水号获取受理登记材料信息列表
    globalSyncPost(urlList, {sblsh: sblsh}, function (response) {
        if (response.code === 1) {
            var $wrapper = dom || $('#dataClTable').find('tbody').empty()
            ;
            if (response.data && response.data.length > 0) {
                for (var d in response.data) {
                    var data = response.data[d];
                    data.index = parseInt(d) + 1;
                    var $tr = trData_cl(response.data[d]);
                    $wrapper.append($tr);
                }
                layui.form.render();
            } else {
                $wrapper.append(
                    $('<tr></tr>').append(
                        $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                    )
                )
            }
        }
    });
}*/

//所需材料
function initClData(dom) {

    var sblsh = UrlParam.param("sblsh");

    var urlList = WEB_ROOT + '/applyMaterialMain/all/list';

    var itemName = UrlParam.param("sbxmmc");//事项名称

    var resp;

    //全部事项材料
    globalSyncPost(urlList, {sblsh: sblsh},function (response) {
        resp = response;
    });
    var gobleDate;
    //获取事项信息
    globalSyncPost(WEB_ROOT + '/applyItemInfo/list/all?sblsh='+sblsh,{'tm': Math.random()},function (re) {
        gobleDate = re.data;
        //console.log(gobleDate);
    });

    var newarray=[];

    if(gobleDate.length>1){
        for (var i in gobleDate){//该部分只为构建新的数组，待优化
            var iteminfo = gobleDate[i];
            //console.log(iteminfo);
            //判断是不是并联事项，并联事项进行材料去重。。。。。
            var isMultiple = iteminfo.hasMultiple;
            if(isMultiple==1){//1为并联

                //console.log(itemName);
                //console.log(iteminfo.itemName);

                //取出当前事项需要的材料
                if(itemName==iteminfo.itemName){
                    var materialIds = iteminfo.materialIds;
                    var result = materialIds.split(",");//材料id集合
                    console.log(result);
                    for(var i = 0;i<result.length;i++){
                        //console.log(result[i]);//获取当前事项每个材料id

                        if(resp.code === 1){
                            var $wrapper = $('#dataClTable').find('tbody').empty();

                            if (resp.data && resp.data.length > 0) {
                                for (var d in resp.data) {
                                    var data = resp.data[d];
                                    data.index = parseInt(d) + 1;
                                    if(result[i]==data.matMainId){
                                        newarray.push(data);//构建新的材料数组
                                        //console.log(i);
                                    }
                                    $wrapper.append($tr);
                                }
                                layui.form.render();
                                //console.log(newarray);
                            }else{
                                $wrapper.append(
                                    $('<tr></tr>').append(
                                        $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        var $wrapper = dom || $('#dataClTable').find('tbody').empty()
        if (newarray.length> 0) {
            for (var d in newarray) {
                var data = newarray[d];
                data.index = parseInt(d) + 1;
                var $tr = trData_cl(newarray[d]);
                $wrapper.append($tr);
            }
            layui.form.render();
        }else{
            $wrapper.append(
                $('<tr></tr>').append(
                    $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                )
            )
        }



    }else{//单项

        globalSyncPost(urlList, {sblsh: sblsh}, function (response) {
            if (response.code === 1) {
                var $wrapper = dom || $('#dataClTable').find('tbody').empty()
                ;
                if (response.data && response.data.length > 0) {
                    for (var d in response.data) {
                        var data = response.data[d];
                        data.index = parseInt(d) + 1;
                        var $tr = trData_cl(response.data[d]);
                        $wrapper.append($tr);
                    }
                    layui.form.render();
                } else {
                    $wrapper.append(
                        $('<tr></tr>').append(
                            $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                        )
                    )
                }
            }
        });

    }
}




function viewElectric(obj) {
    var $content = $('<div></div>')
    $content.append('<div class="uploader-list"></div>');
    var list = [];
    var dirElectricList = $(obj).data("electIds").split(',');
    for (var i in dirElectricList) {
        globalSyncPost(WEB_ROOT + '/sysAttach/read', {id: dirElectricList[i]}, function (response) {
            if (response.code === 1) {
                list.push(response.data);
            }
        });
    }

    if ($.isArray(list) && list.length > 0) {
        $content.fileReader(list, {});
        layer.open({
            title: "查看电子件",
            area: ['500px', 'auto'],
            content: $content.html()
        });
    }
}

function trData_cl(data) {

    var css = {'text-align': 'center'};
    var $tr = $('<tr></tr>');
    var $td0 = $('<td></td>').css(css).append(data.clName);
    var clTypeName;
    if (data.clType == '1') {
        clTypeName = "申办材料";
    }
    else if (data.clType == '2') {
        clTypeName = "补正告知材料";
    }
    else if (data.clType == '3') {
        clTypeName = "补正受理材料";
    }
    var $td1 = $('<td></td>').css(css).append(clTypeName);
    var $td2 = $('<td></td>').css(css).append(data.originNum || 0);
    var $td3 = $('<td></td>').css(css).append(data.originPageNum || 0);
    var $td4 = $('<td></td>').css(css).append(data.copyNum || 0);
    var $td5 = $('<td></td>').css(css).append(data.copyPageNum || 0);
    var $viewElectricBtn = false;
    if(data.electrics && data.electrics.length > 0){
        if(data.source == 2){
            //$viewElectricBtn = $('<a class="layui-btn layui-btn-xs" href="' + data.licenseUrl + '" target="_blank">查看证照</a>');
            $viewElectricBtn = $('<a class="layui-btn layui-btn-xs showLicense" href="javascript:void(0);">查看电子件</a>')
                .data('content', !!data.signElectrics ? data.signElectrics : data.electrics);
        }else {
            // 附件显示签名后PDF文件，佐证材料 和 电子证照则显示原文件（不需要签名）
            $viewElectricBtn = $('<a class="layui-btn layui-btn-xs showElectrics" href="javascript:void(0);">查看电子件</a>')
                .data('content', !!data.signElectrics ? data.signElectrics : data.electrics);

        }
    }else if(data.source == 3 && data.dataContent){
        $viewElectricBtn = $('<a href="javascript:void(0);" class="layui-btn layui-btn-xs showThirdData">查看佐证</a>')
            .data('content', data.dataContent).data('matMainId',data.matMainId);
    }else if(data.formTbId||data.formRowId){
        if(data.formRowId){
            $viewElectricBtn = $('<button class="layui-btn  layui-btn-xs" type="button">查看表单</button>')
                .addClass('btn-formTb')
                .data('tbid', data.formTbId)
                .data('rowid', data.formRowId)
                .data('formType',data.formType)
                .data('matMainId', data.matMainId)
        }else{
            $viewElectricBtn = '未填写表单';
        }
    }else{
        $viewElectricBtn = "未上传";
    }
    var $td6 = $('<td></td>').css(css).append($viewElectricBtn);
    $tr.append($td0).append($td1).append($td2).append($td3).append($td4).append($td5).append($td6);
    return $tr;
}

