var vm = new Vue({
    el: '#dealt',
    data: {
        fileList: {},
        workFlow:{},
        byBusinessResultSatue:{},
        applicantDepartment:{},
        contentByItemsid:{},
        look:'查看'
    },
    methods: {
        list: function () {
            axios({
                url: '/datum/findDealt'
            }).then(response => {
                this.data = response.data;
                this.contentByItemsid = response.data.data.contentByItemsid;
                this.fileList = response.data.data.fileList;
                this.workFlow = response.data.data.workFlow.data;
                this.byBusinessResultSatue = response.data.data.byBusinessResultSatue;
                this.applicantDepartment = response.data.data.applicantDepartment[0];
                console.log(response.data);
            })
        },
        file: function(name,url,id) {
            console.log(url)
            console.log(url.split(".")[1])
            var suf = url.split(".")[1];
            console.log(url)
            console.log(suf);
            if (suf=="png"  || suf=="jpg"){
                //多窗口模式，层叠置顶
                layer.open({
                    type: 2 //此处以iframe举例
                    ,title: '点击查看详情'
                    ,area: ['50%', '50%']
                    ,shade: 0
                    ,maxmin: true
                    ,content: '/toPng?id='+id
                    ,yes: function(){
                        $(that).click();
                    }
                    ,btn2: function(){
                        layer.closeAll();
                    }

                    ,zIndex: layer.zIndex //重点1
                    ,success: function(layero){
                        console.log("dad")
                        console.log(layero)
                        layer.setTop(layero); //重点2
                    }
                });
            }else {
                //多窗口模式，层叠置顶
             window.open("http://19.104.51.80/"+url);
            }
        },
        _mouseover(index,dept,time) {

            var tim = dateFormat("YYYY-mm-dd HH:MM",new Date(time));
            this.a = layer.tips('审核人 : '+dept + '<br/>'+'审核时候 : '+tim, `#mo${index}`, {
                time: 999999
            });
        },
        _mouseout(index){
            layer.close(this.a);
        }
    },
    mounted: function () {
        this.list();
    }
})


function dateFormat(fmt, date) {
    let ret;
    let opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
    };

    return fmt;
}