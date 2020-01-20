var curIframeIndexs2 = [];
function hideData()
{

    if(layer && curIframeIndexs2.length > 0)
    {
        var lastIframeIndex = curIframeIndexs2.pop();
        layer.close(lastIframeIndex);
    }
}

function showData(url,title,width,height,endCallback)
{
	if(layer)
	{
		title = (title || '');
		title = '<i class="layui-icon" style="margin-right:10px;">&#xe63c;</i>' + title;
        var prevIframeIndex = null;
        if(curIframeIndexs2.length > 0) prevIframeIndex = curIframeIndexs2[curIframeIndexs2.length - 1];
        var curIframeIndex = layer.open
		({
        	type	: 2, //2:iframe
            title	: [title,'color:#FFFFFF;background-color:#1e9fff;font-size:16px;'],
        	area	: initArea(width,height),
        	content	: url,
       		zIndex	: 10,
       		success	: function(layero, index)
       		{
       			if(top.$('#layui-layer' + index).find('iframe').length > 0 && top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex){
                    top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex = prevIframeIndex;
                    top.$('#layui-layer' + index).find('iframe')[0].contentWindow.iframeIndex = index;
				}
			},end: function(){
                if(typeof(endCallback) == 'function'){
                    endCallback();
                }
                if(typeof searchList == 'function'){
                    searchList();
				}
			}
      	});
        curIframeIndexs2.push(curIframeIndex);
        return curIframeIndex;
    }
}

function showDialog(url,title,successFun,endFun,cfg, cancelFun)
{
    if(!cfg) cfg = {};
    cfg = $.extend({
        type	: 2,
        title	: ['<i class="layui-icon" style="margin-right:10px;">&#xe63c;</i>' + title,'color:#FFFFFF;background-color:#1e9fff;font-size:16px;'],
        area	:  initArea(null,null),
        content	: url,
        zIndex	: 10,
        success	: function(layero, index)
        {
            if(top.$('#layui-layer' + index).find('iframe').length > 0 && top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex){
                top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex = prevIframeIndex;
                top.$('#layui-layer' + index).find('iframe')[0].contentWindow.iframeIndex = index;
            }
            if(typeof successFun == 'function'){
                successFun(layero,index);
            }
        },
        cancel	: function(index, layero)
        {
            if(typeof cancelFun == 'function'){
                cancelFun(index, layero);
            }

            hideData();
            return false;
        },
        end: function(){
            if(typeof endFun == 'function'){
                endFun();
            }
        }
    }, cfg);



    if(layer)
    {
        var prevIframeIndex = null;
        if(curIframeIndexs2.length > 0) prevIframeIndex = curIframeIndexs2[curIframeIndexs2.length - 1];
        var curIframeIndex = layer.open(cfg);
        curIframeIndexs2.push(curIframeIndex);
        return curIframeIndex;
    }
}

function openDialog4Call(url,title,width,height,openCallback,endCallback)
{
    if(layer)
    {
        title = (title || '');
        title = '<i class="layui-icon" style="margin-right:10px;">&#xe63c;</i>' + title;
        var prevIframeIndex = null;
        if(curIframeIndexs2.length > 0) prevIframeIndex = curIframeIndexs2[curIframeIndexs2.length - 1];
        var curIframeIndex = layer.open
        ({
            type	: 2, //2:iframe
            title	: [title,'color:#FFFFFF;background-color:#1e9fff;font-size:16px;'],
            area	: initArea(width,height),
            content	: url,
            zIndex	: 10,
            btnAlign: 'c',
            success	: function(layero, index)
            {
                if(top.$('#layui-layer' + index).find('iframe').length > 0 && top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex){
                    top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex = prevIframeIndex;
                    top.$('#layui-layer' + index).find('iframe')[0].contentWindow.iframeIndex = index;
                }
                openCallback(layero, index);
            },yes: function(layero, index){
                if(typeof(endCallback) == 'function'){
                    endCallback(layero, index);
                }
                if(typeof searchList == 'function'){
                    searchList();
                }
            }
        });
        curIframeIndexs2.push(curIframeIndex);
        return curIframeIndex;
    }
}

function openDialog4Qxpz(url,title,width,height,openCallback,successCallback,successCallback2)
{
    if(layer)
    {
        title = (title || '');
        title = '<i class="layui-icon" style="margin-right:10px;">&#xe63c;</i>' + title;
        var prevIframeIndex = null;
        if(curIframeIndexs2.length > 0) prevIframeIndex = curIframeIndexs2[curIframeIndexs2.length - 1];
        var curIframeIndex = layer.open
        ({
            type	: 2, //2:iframe
            title	: [title,'color:#FFFFFF;background-color:#1e9fff;font-size:16px;'],
            area	: initArea(width,height),
            content	: url,
            zIndex	: 10,
            btn: ['确定','重置','取消'],
            success	: function(layero, index)
            {
                if(top.$('#layui-layer' + index).find('iframe').length > 0 && top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex){
                    top.$('#layui-layer' + index).find('iframe')[0].contentWindow.prevIframeIndex = prevIframeIndex;
                    top.$('#layui-layer' + index).find('iframe')[0].contentWindow.iframeIndex = index;
                }
                openCallback(layero, index);
            },
            yes: function (index, layero) {
                successCallback(layero,index);
            },
            btn2: function(index, layero){
                successCallback2(layero,index);
            }
        });
        curIframeIndexs2.push(curIframeIndex);
        return curIframeIndex;
    }
}

function initArea(width,height){
	var documentWidth = $(window).width();
	if(!width || documentWidth < width){
		width = documentWidth;
	}
    var documentHeight = $(window).height();
    if(!height || documentHeight < height){
        height = documentHeight;
    }
    return [width + 'px', height + 'px'];
}
