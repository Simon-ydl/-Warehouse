/**
 * 通用页面加载js
 * 默认需要同时引入
 * /resource/js/common/util.js
 * /resource/js/common/dataDivSub.js
 * /resource/js/common/file.reader.js
 * /resource/js/webuploader/webuploader.min.js
 * /resource/js/common/initThirdDataTable.js
 * /resource/js/cache/affair/catalogues/issuesManagement/itemSituation/situation.init.v2.js
 * /resource/js/cache/affair/precondition/precodition.situation.js
 */
var sblsh = UrlParam.param('sblsh');
var $layer = layer;
;(function($){
    var com_template_data = false;

    $.extend(templateLoadData.prototype, {
        /**
         * 内部函数定义
         */
        /**
         * @private
         * @desc 参数使用默认值进行初始化与合并
         * @param {Object} option - 初始化参数
         * @return {Object} - 合并后的参数
         */
         _setOption:function(options) {
            //判断是否属于json对象
            this._settings = $.extend({

                /**
                 * 源数据类型，0、默认的源数据,1、传入的json数据，2、url地址
                 */
                'source': 0,

                /**
                 * 数据源为1时，需要传入的json数据{applyBase:{}, applyItemInfos/applyItemInfo:[]/{}, approvalProcesses:[], applyMaterialMains:[]}
                 */
                'data': {},

                /**
                 * 数据源为2时，需要传入的参数
                 */
                'url': false,

                /**
                 * 数据源为2是需要传入的参数，json数据
                 */
                'param': false,

                /**
                 * 成功后的回调函数，回调数据:渲染的dom对象，数据源com_template_data
                 */
                'success':false,

                /**
                 * 取消按钮：目前用于收费时查询使用,function
                 */
                'cancelBtnClick':false
            }, options);
        },
        _getData:function(methodName) {
            var sourceData = false;
            //目前/apply/detail/read存在的基本数据：applyBase applyItemInfos approvalProcesses applyMaterialMains receiveHall
            if (this._settings.source == 0) {
                sourceData = com_template_data;
            } else if (this._settings.source == 1) {
                com_template_data = this._settings.data;
                sourceData = this._settings.data;
            } else if (this._settings.source == 2) {
                var url = this._settings.url;
                var params = this._settings.param;
                globalSyncPost(url, params, function (response) {
                    if (response.code == 1) {
                        com_template_data = response.data;
                        sourceData = response.data;
                    }
                })
            }
            if(!sourceData){
                return false;
            }
            if(sourceData.applyItemInfos){
                sourceData.applyItemInfo = sourceData.applyItemInfos[0];
            }

            /*switch (methodName) {
                case "showBaseInfo":
                    data = sourceData.applyBase;
                    if(sourceData.applyItemInfos){
                        data.applyItemInfo = sourceData.applyItemInfos[0];
                    }else {
                        data.applyItemInfo = sourceData.applyItemInfo
                    }
                    data.basePhoto = sourceData.basePhoto;
                    if (data.createDate) {
                        data.createDateStr = new Date(data.createDate).format('yyyy年MM月dd日 hh时mm分');
                    }
                    data.isFee = sourceData.isFee;
                    break;
                case "showBsrInfo":
                    data = sourceData.applyBase;
                    break;
                case "showApprovalProcesses":
                    data = sourceData.approvalProcesses;
                    break;
                case "showApplyMaterialMains":
                    var bsr = {};
                    bsr.name = sourceData.applyBase.bsName;
                    bsr.cardType = sourceData.applyBase.bsCardType;
                    bsr.cardNum = sourceData.applyBase.bsNum;
                    bsr.type = sourceData.applyBase.bsType;
                    data = {
                        materials: sourceData.applyMaterialMains,
                        extData: { bsr: bsr }
                    };
                    break;
                case "showProcessesStep":
                    data = sourceData.approvalProcesses;
                    break;
                case 'showPayFeeTable': {
                    data = {
                        feeData: sourceData.feeData
                    };
                    if(sourceData.applyItemInfos){
                        data.applyItemInfo = sourceData.applyItemInfos[0];
                    }else {
                        data.applyItemInfo = sourceData.applyItemInfo
                    }
                    break;
                }
                case 'ShowObtainPaper': {
                    data = sourceData.applyBase;
                    data.receiveHall = sourceData.receiveHall;
                    break;
                }
                default:
                    break;
            }*/
            return sourceData;
        }
    });

    /**
     * 加载方法
     */
    var methods = {
        /**
         * 初始化方法，其他额外需要添加的方法继续添加
         * 使用promise 来连接后续的执行函数, 为了异步执行ajax, 又不用在形参里层层加入回调函数
         */
        initAsync: function (options){
            console.log('init promise');
            this._setOption(options);

            if(this._settings.source == '0' && !com_template_data){
                //如果没有初始化数据则去获取默认的源数据WEB_ROOT + '/apply/detail/read'
                var baseUrl = WEB_ROOT + '/apply/detail/read';

                return new Promise(function (resolve, reject) {
                    globalPost(baseUrl, { sblsh:sblsh }, true, null, function (response) {
                        if(response.code == 1) {
                            com_template_data = response.data;
                        } else {
                            console.log("获取默认数据源失败！");
                        }

                        resolve(response);
                    })
                });
            }

            return new Promise(function (resolve, reject) {
                resolve();
            })
        },

        /**
         * 显示办事对象信息
         * @param data
         */
        showBsrInfo: function ($wrapper) {
            var data = this._getData('showBsrInfo');
            var applyBase = data.applyBase;

            //初始化表格
            var tableStr = '<table class="layui-table">' +
                '<tbody>';

            if (applyBase.bsType == '1') {
                //个人
                tableStr += '<tr>' +
                    '<td width="20%" align="right" class="title">姓名</td>' +
                    '<td width="30%" data-name="bsName">' + (applyBase.bsName || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">证件号码</td>' +
                    '<td width="30%" data-name="bsNum">' + (applyBase.bsNum || '无') + '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td width="20%" align="right" class="title">手机号码</td>' +
                    '<td colspan="3" data-name="bsPhone">' + (applyBase.bsPhone || '无') + '</td>' +
                    '</tr>';
                //个人-经办人
                tableStr += '<tr>' +
                    '<td width="20%" align="right" class="title">经办人姓名</td>' +
                    '<td width="30%" data-name="jName">' + (applyBase.jName || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">经办人证件号码</td>' +
                    '<td width="30%" data-name="jNum">' + (applyBase.jNum || '无') + '</td>' +
                    '</tr>' +
                    '<tr><td width="20%" align="right" class="title">经办人手机号码</td>' +
                    '<td width="30%" data-name="jPhone">' + (applyBase.jPhone || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">经办人邮箱</td>' +
                    '<td width="30%" data-name="jMail">' + (applyBase.jMail || '无') + '</td></tr>'
            } else {
                //非个人
                tableStr += '<tr>' +
                    '<td width="20%" align="right" class="title">机构名称</td>' +
                    '<td width="30%" data-name="bsName">' + (applyBase.bsName || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">证件号码</td>' +
                    '<td width="30%" data-name="bsNum">' + (applyBase.bsNum || '无') + '</td>' +
                    '</tr>';

                //代办对象, 个人或机构
                var str = applyBase.jNum === applyBase.agentNum ?
                    //个人代办
                    ('<tr>' +
                    '<td width="20%" align="right" class="title">经办人姓名</td>' +
                    '<td width="30%" data-name="jName">' + (applyBase.jName || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">经办人证件号码</td>' +
                    '<td width="30%" data-name="jNum">' + (applyBase.jNum || '无') + '</td>' +
                    '</tr>' +
                    '<tr><td width="20%" align="right" class="title">经办人手机号码</td>' +
                    '<td width="30%" data-name="jPhone">' + (applyBase.jPhone || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">经办人邮箱</td>' +
                    '<td width="30%" data-name="jMail">' + (applyBase.jMail || '无') + '</td>' +
                    '</tr>')
                    :
                    //机构代办
                    ('<tr>' +
                    '<td width="20%" align="right" class="title">代办机构名称</td>' +
                    '<td width="30%" data-name="agentName">' + (applyBase.agentName || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">代办机构证件号码</td>' +
                    '<td width="30%" data-name="agentName">' + (applyBase.agentNum || '无') + '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td width="20%" align="right" class="title">经办人姓名</td>' +
                    '<td width="30%" data-name="jName">' + (applyBase.jName || '无') + '</td>' +
                    '<td width="20%" align="right" class="title">经办人证件号码</td>' +
                    '<td width="30%" data-name="jNum">' + (applyBase.jNum || '无') + '</td>' +
                    '</tr>' +
                    '<tr><td width="20%" align="right" class="title">经办人手机号码</td>' +
                    '<td colspan="3" data-name="jPhone">' + (applyBase.jPhone || '无') + '</td></tr>');

                tableStr += str;
            }

            tableStr += '</tbody></table>';

            $wrapper.append(tableStr);
        },

        /**
         * 显示基本信息表格
         * @param data
         */
        showBaseInfo:function ($wrapper) {
            //判断数据源
            var data = this._getData('showBaseInfo');
            if(!data) {
                return;
            }

            //处理情形相关数据
            var itemInfo = data.applyItemInfo;
            var applyBase = data.applyBase;
            if(applyBase.createDate){
                applyBase.createDateStr = new Date(applyBase.createDate).format('yyyy年MM月dd日 hh时mm分');
            }

            var isSituationItem = itemInfo.itemExSource === 'L1' && itemInfo.sitemVersionId;

            //初始化表格
            var tableStr = '<table class="layui-table">' +
                '<tbody>' +
                '<tr>' +
                '<td width="20%" align="right" class="title">申办流水号</td>' +
                '<td width="30%" data-name="sblsh"></td>' +
                '<td width="20%" align="right" class="title">申办时间</td>' +
                '<td width="30%" data-name="createDateStr"></td>' +
                '</tr>' +
                '<tr>' +
                '<td width="20%" align="right" class="title">事项名称</td>' +
                '<td width="30%" data-name="sbxmmc"></td>' +
                '<td width="20%" align="right" class="title">实施编码</td>' +
                '<td width="30%" data-name="itemCode"></td>' +
                '</tr>'+
                '<tr>' +
                '<td width="20%" align="right" class="title">申请单位（人）</td>' +
                '<td width="30%" data-name="bsName"></td>' +
                '<td width="20%" align="right" class="title">申请单位（人）证件号</td>' +
                '<td width="30%" data-name="bsNum"></td>' +
                '</tr>';

            if (isSituationItem) {
                tableStr += '<tr><td width="20%" align="right" class="title">情形项名称</td>' +
                    '<td width="30%" data-name="sitem-qxxmc" id="sitem-qxxmc"></td></tr>';
            }

            //现场登记拍照
            if (applyBase.photoId && data.basePhoto) {
                tableStr = tableStr + '<tr>' +
                '<td width="20%" align="right" class="title">现场照片</td>' +
                '<td colspan="3" ><button class="layui-btn layui-btn-normal layui-btn-sm showElectrics" data-content=\'' + JSON.stringify(data.basePhoto) + '\'>' +
                '查看' +
                '</button>' +
                '</td></tr>';
            }
            //基础表单
            if (applyBase.baseFormRowid) {
                tableStr = tableStr +
                    '<tr id="bsxxTr">' +
                    '<td align="right" class="title">办事信息</td>' +
                    '<td colspan="3" id="bsxxNr">' +
                    '<button class="layui-btn layui-btn-normal layui-btn-sm formBtn btn-formTb" type="button"' +
                    '       data-tbid="' + applyBase.baseFormId + '" data-rowid="' + applyBase.baseFormRowid + '">' +
                    '<i class="layui-icon layui-icon-template-1"></i>查看办事信息' +
                    '</button>' +
                    '</td>' +
                    '</tr>'
            }

            tableStr  = tableStr +
                '<tr id="bzTr">' +
                '<td width="20%" align="right" class="title">备注</td>'+
                '<td data-name="bz" colspan="3"></td>';
                '</tr>';

            tableStr = tableStr +
                '<tr id="qztj-tr">' +
                ' <td width="20%" align="right" class="title">前置条件</td>' +
                ' <td width="30%" id="qztj-str">无</td>' +
                ' <td align="right" class="title">申办情形</td>' +
                ' <td colspan="3" id="situationTd"></td>' +
                '</tr>';
            if(applyBase.yjhhm){
                tableStr = tableStr +'<tr>' +
                ' <td width="20%" align="right" class="title">叫号号码</td>' +
                ' <td width="30%" data-name="yjhhm"></td>' +
                '</tr>';
            }else {
                tableStr = tableStr +'</tbody>' +
                    '</table>';
            }
            var $table = $(tableStr);
            //填充数据
            var selector = '[data-name]',
                $controls = $table.find(selector);
            if ($table.length > 0) {
                $controls.each(function (index, ct) {
                    var $ct = $(ct);
                    var nm = $ct.data('name');
                    $ct.html(applyBase[nm] || '无');
                })
            }

            var $situationDiv = $("<div></div>");
            var sffqx = false;

            //判断是否为省的情形
            if(itemInfo.situationType == '1'){
                //省情形
                $situationDiv.css({'padding': '10px 15px'});
                $situationDiv.append(itemInfo.situations);
            }else {
                if (isSituationItem) {
                    //查询情形项版本数据
                    globalSyncPost(WEB_ROOT + '/applyItemInfo/findSituationItemInfoHist', { sitemVersionId: itemInfo.sitemVersionId, sitemRowId: itemInfo.sitemRowId }, function (response) {
                        if (response.code != 1 || !response.data) {
                            console.error('查询情形项版本数据失败：'+response.msg+' ，id: ' + itemInfo.sitemVersionId);
                            return;
                        }
                        var situationItemInfo = response.data;
                        sffqx = situationItemInfo.siQxpzType == '1';
                        $table.find("#sitem-qxxmc").html(situationItemInfo.siName);
                    });

                } else {
                    globalSyncPost(WEB_ROOT + '/affairItemSituation/version/info/read?ywqxId=' + itemInfo.ywqxId, {'tm': Math.random()},
                        function (result) {
                            sffqx = result.data.sffqx;
                        });
                }
                if (sffqx == '1') {
                    var $situationBtn = $("<button class='layui-btn layui-btn-sm layui-btn-normal'>查看情形</button>");
                    $situationBtn.data("situationids", itemInfo.situationIds);
                    $situationBtn.data("ywqxid", itemInfo.ywqxId);
                    $situationBtn.addClass('btn-view-situation');
                    $situationDiv.append($situationBtn);
                } else {
                    $situationDiv.append("该事项不分情形");
                }
            }
            $table.find("#situationTd").append($situationDiv);

            //处理前置条件
            if(itemInfo.preconditions){
                var preconditions = itemInfo.preconditions || "无";
                $table.find("#qztj-str").html(preconditions);
            }else {
                if (itemInfo.prefixId) {
                    var preconditionDiv = $("<div></div>");
                    var preconditionBtn = $("<button class='layui-btn layui-btn-sm layui-btn-normal btn-view-precondition'>查看前置条件</button>");
                    preconditionBtn.data('prefixid', itemInfo.prefixId);
                    preconditionBtn.data('prefixselectids', itemInfo.prefixSelectIds);
                    preconditionDiv.append(preconditionBtn);
                    $table.find("#qztj-str").empty();
                    $table.find("#qztj-str").append(preconditionDiv);
                }else {
                    $table.find("#qztj-str").html('无');
                }
            }
            $wrapper.append($table);
        },

        /**
         * 显示办理工作历史
         * @param $wrapper
         */
        showApprovalProcesses:function ($wrapper) {
            var $tableStr = '<table class="layui-table" id="blhjDataTable">' +
                '                            <thead>' +
                '                            <tr align="center">' +
                '                                <td class="title">办理环节</td>' +
                '                                <td class="title" width="20%">办理人</td>' +
                '                                <td class="title" width="20%">办理时间</td>' +
                '                                <td class="title">办理意见</td>' +
                '                                <td class="title">审批附件</td>' +
                '                                <td class="title">备注</td>' +
                '                            </tr>' +
                '                            </thead>' +
                '                            <tbody align="center" >' +
                '                            </tbody>' +
                '                        </table>';
            var $table = $($tableStr);
            var data = this._getData('showApprovalProcesses');
            var approvalProcesses = data.approvalProcesses;

            if (approvalProcesses && approvalProcesses.length > 0) {
                for (var d in approvalProcesses) {
                    var process = approvalProcesses[d];
                    var $tr = _processTrData(process);
                    $table.append($tr);
                }
                layui.form.render();
            } else {
                $table.append(
                    $('<tr></tr>').append(
                        $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                    )
                )
            }
            $wrapper.append($table);
        },

        /**
         * 加载流程节点图
         * @param $wrapper
         */
        showProcessesStep:function ($wrapper) {
            var strs = new Array();
            var num = 0;
            var data = this._getData("showProcessesStep");
            var processes = data.approvalProcesses;
            if(processes && processes.length > 0){
                num = processes.length;
                for (var d in processes) {
                    var process = processes[d];
                    process.index = parseInt(d) + 1;
                    var sperNmae = "";
                    if(process.spPer){
                        sperNmae = process.spPer.userName;
                    }

                    var sphjmc = process.sphjmc ;
                    if(sphjmc.length > 6){
                        sphjmc = sphjmc.substring(0 , 6) + "...";
                    }

                    var step = {'title':sphjmc, 'innerTitle':process.sphjmc , 'content':sperNmae + '：' + new Date(process.spDate).format('yyyy年MM月dd日')}
                    strs.push(step);
                }
                $wrapper.loadStep({
                    size: "large",
                    color: "blue",
                    steps: strs
                });
                $wrapper.setStep(num);
                $wrapper.find('li').removeAttr("data-toggle");
                layui.form.render();
            }
        },

        /**
         * 加载材料信息
         * @param $wrapper
         */
        showApplyMaterialMains:function ($wrapper) {
            var data = this._getData("showApplyMaterialMains");
            var $tableStr = '<table class="layui-table" id="dataClTable">' +
                '                            <thead>' +
                '                            <tr>' +
                '                                <th style="text-align: center;" class="title" rowspan="2">材料名称</th>' +
                '                                <th style="text-align: center;" class="title" rowspan="2" width="65px">文件类型</th>' +
                '                                <th style="text-align: center;" class="title" colspan="2" width="85px">收取数量</th>' +
                '                                <th style="text-align: center;" class="title" rowspan="2">电子件信息</th>' +
                '                            </tr>' +
                '                            <tr>' +
                '                                <th style="text-align: center;" class="title">总份数</th>' +
                '                                <th style="text-align: center;" class="title">总页数</th>' +
                '                            </tr>' +
                '                            </thead>' +
                '                            <tbody align="center">' +
                '                            </tbody>' +
                '                        </table>';
            var $table = $($tableStr);
            var materials = data.applyMaterialMains;
            var applyBase = data.applyBase;
            var bsr = {};
            bsr.name = applyBase.bsName;
            bsr.cardType = applyBase.bsCardType;
            bsr.cardNum = applyBase.bsNum;
            bsr.type = applyBase.bsType;
            var extData = {bsr:bsr};

            if (materials && materials.length> 0) {
                for (var d in materials) {
                    var material = materials[d];
                    data.index = parseInt(d) + 1;
                    material.ext = extData;
                    var $tr = _materialTrData(material);
                    $table.append($tr);
                }
                layui.form.render();
            }else{
                $table.append(
                    $('<tr></tr>').append(
                        $('<td></td>', {colspan: '9', 'align': 'center'}).append('无数据').css({'padding-bottom': '150px'})
                    )
                )
            }
            $wrapper.append($table);
        },

        /**
         * 加载非税缴费项目的table
         */
        showPayFeeTable: function ($wrapper) {
            var dataMain = this._getData('showPayFeeTable') || {};

            var cancelBtnClick = this._settings['cancelBtnClick'] || function () { console.log('blank func, seemed caller not passed cancelBtnClick') };

            var data = dataMain['feeData'] || [];
            var itemInfo = dataMain['applyItemInfo'];

            // var cancellable = itemInfo.payWay == '2';
            // var cancellable = true;

            var common_td_css = { 'text-align': 'center' };
            var thTemplet = [
                { tagName: 'th', clazz: '', css: common_td_css, content: '通知书编号' },
                { tagName: 'th', clazz: '', css: common_td_css, content: '缴费单位/人' },
                { tagName: 'th', clazz: '', css: common_td_css, content: '缴费项目' },
                { tagName: 'th', clazz: '', css: common_td_css, content: '缴费情况' },
                { tagName: 'th', clazz: '', css: common_td_css, content: '操作' }
            ];

            //表头
            var $thead = $('<thead></thead>');
            $thead.append(renderTr(thTemplet));

            //表格内容
            var $tbody = $('<tbody></tbody>');
            for (var i = 0; i < data.length; i++) {
                var payFeeItem = data[i];
                //不显示已取消的
                // if (payFeeItem['status'] == 0) {
                //     continue;
                // }

                var statusText = payFeeItem.status == 2? '已缴费' :
                                 payFeeItem.status == 4? '已退库' :
                                 payFeeItem.status == 0? '已取消' : '未缴费';

                var statusText_td_css = { 'text-align': 'center' };
                if ([2,0].indexOf(parseInt(payFeeItem.status)) === -1) {
                    statusText_td_css['color'] = 'red';
                }

                var tbTemplet = [
                    { tagName: 'td', clazz: '', css: common_td_css, content: payFeeItem.pnNo },
                    { tagName: 'td', clazz: '', css: common_td_css, content: payFeeItem.payerName },
                    { tagName: 'td', clazz: '', css: common_td_css, content: payFeeItem.itemNameList },
                    { tagName: 'td', clazz: '', css: statusText_td_css, content: statusText }
                ];

                var $div = $('<div></div>');
                //查看按钮
                $div.append('<button class="layui-btn layui-btn-xs feePayView" ' +
                    'data-sblsh="' + sblsh + '" data-pnno="' + payFeeItem.pnNo + '">查看</button>');

                var cancellable = itemInfo.payWay == '2'&&payFeeItem.status!= '2'&&payFeeItem.status!= '0';

                //取消按钮
                if (cancellable) {
                    var $cancelBtn = $('<button class="layui-btn layui-btn-danger layui-btn-xs feePayCancelBtn" ' +
                        'data-sblsh="' + sblsh + '" data-pnno="' + payFeeItem.pnNo + '">取消通知书</button>');
                    $cancelBtn.click(cancelBtnClick);

                    $div.append($cancelBtn);
                }

                tbTemplet.push({ tagName: 'td', clazz: '', css: statusText_td_css, content: $div });
                $tbody.append(renderTr(tbTemplet));
            }

            //缴费情况
            var isFeeStr = dataMain.isFee + '';
            if (!isFeeStr || isFeeStr === '3') {
                return;
            }

            var isFeeText = isFeeStr === '0' ? '未缴费' :
                isFeeStr === '1' ? '部分已缴费' :
                isFeeStr === '2' ? '已缴费' :
                isFeeStr === '4' ? '取消缴费' :
                isFeeStr === '5' ? '待开通知书' :
                isFeeStr === '6' ? '查询异常' : '无';
            var isFeeColor = [0, 1, 6, 5].indexOf(parseInt(isFeeStr)) !== -1 ? 'red' : '';

            var feeStatus = '<span style="color: ' + isFeeColor + '">' + isFeeText + '</span>';

            $wrapper.append('<fieldset class="layui-elem-field">' +
                '<legend>缴费情况 - ' + feeStatus + '</legend>\n' +
                '<div class="layui-field-box">' +
                '</div>' +
                '</fieldset>');

            if (isFeeStr === '5') {
                //待开通知书, 还没有缴费项的通知书
                $wrapper.find('.layui-field-box').append($('<table class="layui-table"></table>'))
                    .find('table')
                    .append($thead)
                    .append('<tbody><tr><td colspan="5" style="text-align: center">暂无缴费通知书</td></tr></tbody>');
            } else {
                $wrapper.find('.layui-field-box').append($('<table class="layui-table"></table>')
                    .append($thead)
                    .append($tbody));
            }
        },

        /**
         * 加载领取办理结果信息
         */
        ShowObtainPaper: function ($wrapper) {
            var data = this._getData('ShowObtainPaper');
            var applyBase = data.applyBase;

            var $table = $('<table class="layui-table"></table>');
            var obtainTypeText = applyBase.obtainPaperType == 1? '邮寄出证' : applyBase.obtainPaperType == 2? '自助区取证' : '现场取证';

            $table.append('<tr>' +
                '<td width="20%" align="right">取件方式</td>' +
                '<td colspan="3">' +
                obtainTypeText +
                '</td>' +
                '</tr>');

            if (applyBase.obtainPaperType == 1) {
                var fzApplyWebPickupInfo = {};
                if(data.fzApplyWebPickupInfo){
                    fzApplyWebPickupInfo = data.fzApplyWebPickupInfo;
                }else {
                    globalSyncPost(WEB_ROOT + '/apply/detail/getMailDetail',{sblsh:applyBase.sblsh,sendType:"2"},
                        function (response) {
                        if(response.code == 1 && response.data){
                            fzApplyWebPickupInfo = response.data;
                        }
                    })
                }
                $table.append('<tr>' +
                    '<td width="20%" align="right">邮递收件人</td>' +
                    '<td width="30%" >' + fzApplyWebPickupInfo.sendName + '</td>' +
                    '<td width="20%" align="right">邮递收件人电话</td>' +
                    '<td width="30%" >' + fzApplyWebPickupInfo.sendPhone + '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td width="20%" align="right">邮递收件人地址</td>' +
                    '<td colspan="3">' + fzApplyWebPickupInfo.sendProvince + fzApplyWebPickupInfo.sendCity + fzApplyWebPickupInfo.sendTown + fzApplyWebPickupInfo.sendAddress + '</td>' +
                    '</tr>');
                $wrapper.append($table);
            } else {
                var receiveHall = data.receiveHall;
                var $tr = $('<tr><td width="20%" align="right">取证大厅</td></tr>');
                if(!receiveHall){
                    var bsdtUrl = WEB_ROOT + '/bsdtGroup/findOneByGroupId';
                    globalPost(bsdtUrl, {groupId:applyBase.receiveHallId}, true, null, function (response) {
                        if(response.code == 1 && response.data){
                            receiveHall = response.data;
                            $tr.append('<td colspan="3">' + (receiveHall? receiveHall.name : '') + '</td>');
                            $table.append($tr);
                            $wrapper.append($table);
                        }
                    })
                }else {
                    $tr.append('<td colspan="3">' + (receiveHall? receiveHall.name : '') + '</td>');
                    $table.append($tr);
                    $wrapper.append($table);
                }
            }
        },

        /**
         * 加载出证取件信息
         */
        ShowLicenseObtainInfo: function ($wrapper) {
            var _get_data = this._getData('ShowLicenseObtainInfo');
            var data = _get_data.licenseInfo;

            //出证人,取证人信息
            var $content = $('<div></div>');
            var $infoTable = '';

            var certiTbody = "";   //动态拼接证件信息详情
            var certiNameValue = "";
            var certiNumValue = "";
            var $table = $('<table class="layui-table"></table>');
            //证件信息列表
            if (data && data.infos && data.infos.length > 0) {

                for(var i=0 ; i<data.infos.length ; i++) {
                    var dt = data.infos[i];

                    //证件类型 0：普通证件 1：奥格返回的证件 2.退件材料
                    var certiType = dt.type;

                    certiNameValue += dt.certiName+"；";
                    certiNumValue += dt.certiNum+"；";

                    var statusText = dt.status == '1'? ' 已领取 ' : '<span style="color: #ffb90d"> 未领取 </span>';
                    if(2 != certiType) {
                        var certiTr =
                            "<tr>" +
                            "<td align='left' class='title' colspan='4' style='color:#1E9FFF;'>" + dt.certiName + '-' + statusText + "</td> " +
                            "</tr>" +
                            "<tr>" +
                            "<td align='right' class='title' style='word-break: break-all ; width: 120px'>证件类型</td>" +
                            "<td width='30%'>" + (dt.certiType || "") + "</td>" +
                            "<td align='right' class='title'>证件名称</td>" +
                            "<td width='30%' class='certiName'>" + dt.certiName + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td align='right' class='title'>证件编号</td>" +
                            "<td width='30%' class='certiNum'>" + dt.certiNum + "</td>" +
                            "<td align='right' class='title'>发证机关</td>" +
                            "<td width='30%'>" + dt.certiOrgan + "</td>" +
                            "</tr>";

                        var certiExpireTypeStr = dt.certiExpireType;
                        if (certiExpireTypeStr == 0) {
                            certiTr += "<tr>" +
                                "<td align='right' class='title'>证件有效期</td>" +
                                "<td width='30%' colspan='3'>长期有效</td>" +
                                "</tr>";
                        } else {
                            if (certiExpireTypeStr == 1) {
                                certiTr += "<tr>" +
                                    "<td align='right' class='title'>证件有效期始</td>" +
                                    "<td width='30%'>" + (dt.certiStartValidate || "") + "</td>" +
                                    "<td align='right' class='title'>证件有效期至</td>" +
                                    "<td width='30%'>" + dt.certiEndValidate + "</td>" +
                                    "</tr>"
                            } else {
                                certiTr += "<tr>" +
                                    "<td align='right' class='title'>证件有效期</td>" +
                                    "<td width='30%' colspan='3'>长期有效</td>" +
                                    "</tr>";
                            }
                        }

                        if (dt.certiIds) {
                            var certiIds = dt.certiIds;
                            if (certiIds.split(",").length > 0) {
                                certiTr += "<tr>" +
                                    "<td align='right' class='title'>文件信息</td>" +
                                    "<td width='30%' colspan='3'>" +
                                    "<input class='fjInput-" + i + "' type='hidden' name='certiIds-" + i + "' id='certiIds-" + i + "' value='" + dt.certiJson + "'/>" +
                                    "<button type='button' class='layui-btn layui-btn-sm layui-btn-normal' onclick='viewFile(this)'>下载附件<label style='color: red' class='num'>" + "(" + certiIds.split(",").length + ")" + "</label></button></td>" +
                                    "</tr>";
                            }
                        } else {
                            $('#fileInfo-' + i + '').hide();
                        }
                    }else{
                        certiTr =
                            "<tr>" +
                            "<td align='left' class='title' colspan='4' style='color:#1E9FFF;'>" + (dt.materialName || dt.clName) + '-' + statusText + "</td> " +
                            "</tr>" +
                            "<tr>" +
                            "<td align='right' class='title' style='word-break: break-all ; width: 120px'>材料名称</td>" +
                            "<td colspan='3' class='certiName'>"+(dt.materialName || dt.clName || "")+"</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td align='right' class='title'>材料类型</td>" +
                            "<td colspan='3' class='certiNum'>"+ (dt.materialType || dt.clType || "" )+"</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td align='right' class='title'>已领取份数</td>" +
                            "<td width='30%'>"+dt.materialPerNum+"</td>" +
                            "<td align='right' class='title'>未领取份数</td>" +
                            "<td width='30%'>"+dt.matNums+"</td>" +
                            "</tr>";
                    }

                    certiTbody += certiTr;
                }

            } else {
                certiTbody = '<tbody><tr><td colspan="5" style="text-align: center">无出证信息</td></tr></tbody>';
            }

            $content.append($infoTable);
            $table.append(certiTbody);
            $content.append($table);

            $wrapper.append($content);
        },

        /**
         * 加载办事人叫号信息
         * @param $wrapper
         * @constructor
         */
        ShowYjhInfo: function ($wrapper) {
            var _get_data = this._getData('ShowYjhInfo');
            var data = _get_data.yjhInfoList;

            if (!data || data.length == 0) {
                return;
            }

            var $table = $('<table class="layui-table"></table>');
            var $thead = $('<thead><tr><td>类型</td><td>号码</td><td>操作人员</td><td>大厅</td><td>窗口</td><td>时间</td></tr></thead>');
            var $tbody = $('<tbody></tbody>');
            for (var i = 0; i < data.length; i++) {
                var $tr = $('<tr></tr>');
                var typeStr = data[i]['type'] == 1? '现场登记' :
                              data[i]['type'] == 2? '网上申报' :
                              data[i]['type'] == 3? '大厅出证' :
                              data[i]['type'] == 4? '预约办理' :
                              data[i]['type'] == 5? '补正受理' : '未知';

                $tr.append($('<td></td>').append(typeStr));
                $tr.append($('<td></td>').append(data[i]['num'] || '无'));
                $tr.append($('<td></td>').append(data[i]['opUserName'] || '无'));
                $tr.append($('<td></td>').append(data[i]['hallName'] || '无'));
                $tr.append($('<td></td>').append(data[i]['winName'] || '无'));
                $tr.append($('<td></td>').append(new Date(data[i]['createDate']).format('yyyy-MM-dd hh:mm:ss')));
                $tbody.append($tr);
            }

            $table.append($thead);
            $table.append($tbody);
            $wrapper.append($table);
        }
    };

    /**
     *材料的行数据
     * @param data
     */
    function _materialTrData(data) {
        var common_td_css = {'text-align': 'center'};
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

        var lineColTemplet = [
            { tagName: 'td', clazz: '', css: common_td_css, content: data.clName },
            { tagName: 'td', clazz: '', css: common_td_css, content: clTypeName }
        ];

        //应收数量
        data.originNum = data.originNum? parseInt(data.originNum) : 0;
        data.originPageNum = data.originPageNum? parseInt(data.originPageNum) : 0;
        data.copyNum = data.copyNum? parseInt(data.copyNum) : 0;
        data.copyPageNum = data.copyPageNum? parseInt(data.copyPageNum) : 0;
        //总分数
        var pieceNum = data.originNum? data.originNum : (data.copyNum? data.copyNum : '-');
        //总页数
        var pageNum = data.originPageNum? data.originPageNum : (data.copyPageNum? data.copyPageNum : '-');

        lineColTemplet.push({ tagName: 'td', clazz: '', css: common_td_css, content: pieceNum });
        lineColTemplet.push({ tagName: 'td', clazz: '', css: common_td_css, content: pageNum });

        lineColTemplet.push({ tagName: 'td', clazz: '', css: common_td_css, content: initMaterialElectricBtn(data, data.extData) });
        return renderTr(lineColTemplet);
    }

    /**
     * 审批历史的行数据
     * @param data
     */
    function _processTrData(data) {
        var css = {'text-align': 'center'};
        var $tr = $('<tr></tr>');
        var $td0 = $('<td></td>').css(css).append(data.sphjmc);
        var spPer = data.spPer;
        var spPerName = '无';
        if(spPer){
            spPerName = spPer.userName;
        }
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
            yijianStr = data.qitayijian || '其他意见';
        }
        var $td4 = $('<td></td>').css(css);
        if(data.approvalAttachIds && data.approvalAttachElectrics) {
            var $viewElectricBtn = $("<button class='layui-btn  layui-btn-xs'>查看</button>");
            $viewElectricBtn.addClass('showElectrics');
            $viewElectricBtn.data('content',data.approvalAttachElectrics);
            $td4.append($viewElectricBtn);
        }
        else {
            $td4.append("未上传！");
        }
        var $td3 = $('<td></td>').css(css).append(yijianStr);
        var $td5 = $('<td></td>').css(css).append(data.bz);

        $tr.append($td0).append($td1).append($td2).append($td3)
            .append($td4).append($td5);
        return $tr;
    }

    /**
     * 构造函数
     * @param  0：$wrapper dom对象
     * @param 1：method  调用的方法名称
     * @param 2：options  设置的参数
     */
    function templateLoadData($wrapper,method,options) {
        var self = this;
        // 方法调用
        if (methods[method]) {
            //$wrapper.empty();
            //初始化, 准备数据
            var promise = methods.initAsync.call(self, options);
            //promise的下一个任务
            var next = function () {
                //渲染数据到dom
                methods[method].call(self, $wrapper);
                //成功回调
                if (options && typeof options.success === 'function') {
                    var reData = com_template_data;
                    if(this._settings.source != 0){
                        //处理缓存数据
                        com_template_data = false;
                    }
                    options.success($wrapper, reData);
                }
            };
            promise.then(next.bind(self));

            return promise
        } else {
            $.error('Method-' + method + 'does not exist on jQuery.comTemplate.js');
        }
    }

    /**
     * 扩展jquery方法
     * @param option - selector: 'jq选择器', method: '表格对应的方法', cfg: '_setOption'
     * @returns {Window.jQuery}
     */
    $.commonTableLoad = function(option) {
        option = option || {};
        if (option.destroyData) {
            com_template_data = false;
        }

        addRenderTask(option);
        return this;
    };

    //+++++++++++++++++++++++++++++++++++++渲染任务, 按顺序执行, 并随有加载动画
    function execRenderTask() {
        if (_taskList.length === 0) {
            return;
        }

        var self = this;
        var allPromise = Promise.resolve();
        //顺序执行任务
        for (var j = 0; j < _taskList.length; j++) {
            (function () {
                var _option = _taskList[j];

                //开始加载数据并渲染ui
                var next = function () {
                    $(_option.selector).empty();
                    var $loading = $('<div class="template-loading" style="text-align: center;margin: 20px;height: 50px;">\n' +
                        '                    <i class=\'layui-icon layui-icon-loading\n' +
                        '                    layui-anim layui-anim-rotate layui-anim-loop\' style="font-size: 40px"></i>&nbsp;&nbsp;模板加载中...\n' +
                        '                </div>');
                    $(_option.selector).append($loading);
                    return new templateLoadData($(_option.selector), _option.method, _option.cfg).then(function () { $(_option.selector).find(".template-loading").remove() });
                };
                allPromise = allPromise.then(next.bind(self));
            })()
        }

        _taskList.length = 0;
    }

    //在300ms内, 没有再加入task的话, 自动执行渲染任务execRenderTask
    var _checkTaskOverAndAutoExec;
    var _taskList = [];
    function addRenderTask(option) {
        _taskList.push(option);
        if (_checkTaskOverAndAutoExec) {
            clearTimeout(_checkTaskOverAndAutoExec);
        }

        _checkTaskOverAndAutoExec = setTimeout(function () {
            //任务队列已经完成初始化, 开始按顺序执行任务
            execRenderTask();
        }, 300);
    }

    function renderTr(lineColTemplet) {
        var $tr = $('<tr></tr>');

        for (var i = 0; i < lineColTemplet.length; i++) {
            var tagName = lineColTemplet[i].tagName;
            var $dom = $('<' + tagName + '>' + '</' + tagName + '>');
            var _css = lineColTemplet[i].css || '';
            var _class = lineColTemplet[i].clazz || '';
            var _content = lineColTemplet[i].content || '';
            $dom.css(_css).addClass(_class).append(_content);

            var _dataProps = lineColTemplet[i].dataProps || [];
            if (Array.isArray(_dataProps)) {
                for (var j = 0; j < _dataProps.length; j ++) {
                    $dom.data(_dataProps[j].name, _dataProps[j].value);
                }
            }

            $tr.append($dom);
        }

        return $tr;
    }

})(window.jQuery);