var vm = new Vue({
    el: '#app',
    data: {
        content: {},
        fileList: [],
        activiti:{},
        applicantDepartment:{},
        data2:{},
        look:'查看',
        pass:{
            fileUpload:'',/*上传附件*/
            remark:'',/*审批意见*/
            state:'',/*状态*/
            approver:'',/*审批人*/
            dept:'',/*部门*/
            businessCode:'',//流水号
            id:''//错误材料的id
        },
        upload_err: '',
        upload_tips: "",
        reUpload: null,
        allIds: [], // 全部的文件id
        selectIds: [], // 已选的文件id
        correction: {}, //材料补正原因,
        hasFile: false // 是否有选择文件
    },
    methods: {
        list() {//待办点击办理先查询数据库
            axios({
                url: '/datum/findBybusinessCode',
                method:'post',
                data:this.pass
            }).then(rsp => {
                if (rsp.data && rsp.data.data && rsp.data.data.fileList) {
                    const fileList = rsp.data.data.fileList
                    const ids = fileList.map(file => {
                        this.correction[file.id] = '';
                        return file.id
                    });
                    this.fileList = rsp.data.data.fileList;
                    this.allIds = ids;
                }
                this.data = rsp.data;
                this.content = rsp.data.data.content;
                this.applicantDepartment = rsp.data.data.applicantDepartment;
                // this.activiti = rsp.data.data.activiti.data;
                this.data2 = rsp.data.data;
                console.log("dept::"+rsp.data)

            })
        },
        list2() {//待办点击办理 工作流接口
            axios({
                url: '/datum/activitiTaskNext',
                method:'post',
            }).then(respond=>{
                this.activiti = respond.data.data.data;
                console.log("activiti:"+this.activiti)
            })
        },
        viewFile(name,url,id) {
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
        aaa(bid,iid) {
            //示范一个公告层
            // console.log("iid:"+iid);
            layer.open({
                type: 2//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层)
                , title: false //不显示标题栏
                , closeBtn: 1
                , area: ['70%', '97%']//定义宽高
                , shade: 0//遮幕
                , content: '/datum/application.pdf?businessCode='+bid+"&itemsId="+iid,//跳转到想要的界面，这里是我自己项目的跳转界面
                success: function (layero) {
                    console.log(layero.data)
                    var btn = layero.find('.layui-layer-btn');
                    btn.find('.layui-layer-btn0').attr({
                        href: '/toIndex',
                        target: '_blank',
                    });
                }
            })
        },
        through(businessCode) {//受理通过
            this.pass.businessCode = businessCode;
            this.pass.state=2;
            this.pass.approver = this.data2.userName;
            this.pass.dept  = this.activiti.actName;
        },
        fileUpload(business_code, userName, actName, state) { //审批接口
            let correction = this.selectIds.reduce((map, sId) => {
                map.push({
                    id: sId,
                    correction: this.correction[sId]
                });
                return map
            }, []);
            debugger
            correction = JSON.stringify({correction})
            const postData = {
                business_code,
                state,
                userName,
                actName,
                remark: this.pass.remark,
                correction,
            };
            if (!this.hasFile) {
                console.log(qs(postData, false))
                axios({
                    url: '/datum/Pass',
                    data: qs(postData, false),
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    }
                }).then(rsp => {
                    console.log('rsp ====>', rsp)
                    this.activitiProceeds();
                    console.log('parent', parent, parent.layui.layer)
                    parent.window.location.href='/toIndex'
                    parent.layui.layer.closeAll()
                });
                return
            }
            // 使用reUpload重新上传
            if (this.reUpload instanceof Function) {
                this.reUpload();
            } else {
                this.uploader.config.data = postData
                this.uploader.upload()
            }
        },

        activitiProceeds(){//审批接口
            console.log("审批工作流接口")
            axios({
                url:'/datum/activitiProceeds',
                methods: 'POST'
            })
        },
        checkAll() {
            if (this.allIds.length === this.selectIds.length) {
                this.selectIds = []
            } else {
                this.selectIds = this.allIds
            }
        },
        check(event) {
            console.log('event ====>', event);
            const id = event.target.value
            if (this.selectIds.includes(id)) {
                this.selectIds = this.selectIds.filter(sid => sid !== id)
            } else {
                this.selectIds.push(id)
            }
        }
    },
    created() {
        this.list();
        this.list2();
    },
    mounted: function () {
        const _this = this;
        this.uploader = layui.upload.render({
            elem: '#_fileUpload',
            url: '/datum/Pass',
            auto: false,    //选择文件后不自动上传
            multiple: false,//是否允许多文件上传。设置 true即可开启。不支持ie8/9
            number:1,       //设置同时可上传的文件数量，一般配合 multiple 参数出现。注意：该参数为 layui 2.2.3 开始新增
            accept:'file',//指定允许上传时校验的文件类型，可选值有：images（图片）、file（所有文件）、video（视频）、audio（音频）
            choose: function (obj, index) {
                console.log('ob =====>', obj, index);
                const files = obj.pushFile();
                const fileKeys = Object.keys(files);
                const fileNames = [];
                obj.preview(function(i) {
                    console.log('files ===>', files[i]);
                    fileKeys.map(key => {
                        let keyLastModified = files[key].lastModified;
                        let keyName = files[key].name;
                        let iLastModified = files[i].LastModified;
                        let iName = files[i].name;
                        if (keyLastModified !== iLastModified && keyName !== iName) {
                            delete files[key]
                        }
                    });

                    Object.keys(files).map(key => fileNames.push(files[key].name));
                    _this.upload_tips = fileNames.join('')
                    _this.hasFile = true;
                });
            },
            done: function (index, reUpload) {
                console.log('done ===>', index, reUpload)
                // _this.reUpload = reUpload
                parent.window.location.href='/toIndex'
                _this.activitiProceeds();
            },
            error: function (index, reUpload) {
                _this.reUpload = reUpload // 将重新上传的方法设置为layui中返回的upload
            },
        });
        console.log('uploader', this.uploader)
    }
})