(window.webpackJsonp=window.webpackJsonp||[]).push([[7],{"+eJb":function(e,t,n){"use strict";n.d(t,"a",function(){return l}),n.d(t,"b",function(){return s});var a=n("o75L"),o=n("+s51"),i=n("yWgo"),r={allow:[a.a.getAllOnethingList,a.a.findAllByUseridcode,a.a.findUserInfoByToken],login:[a.a.fileDelete,a.a.fileload,a.a.getFiles,a.a.infomsg,a.a.getResult,a.a.addHousehold,a.a.addDatum,a.a.addSelf]},u={allow:[o.a.index],login:[o.a.user,o.a.precondition,o.a.situation,o.a.upmaterial,o.a.infowrite,o.a.benkeruhu,o.a.feedback]},l=(o.a.index,o.a.user,o.a.procedure,o.a.upmaterial,o.a.infowrite,o.a.benkeruhu,o.a.feedback,function(e,t){if(r.allow.includes(e))return!0;if(r.login.includes(e)){var n=Object(i.b)();if(n&&n.tokenCode)return!0;var a=t.params.tokenCode;return a?(Object(i.f)({tokenCode:a}),!0):(t.to(o.a.gdGovLogin,{},!1),!1)}return!0}),s=function(e,t){if(console.dev("cgi",e),u.allow.includes(e))return!0;if(console.dev("页面是否需要登录",u.login.includes(e)),u.login.includes(e)){var n=Object(i.b)();if(n&&n.tokenCode)return!0;var a=t.params.tokenCode;return a?(Object(i.f)({tokenCode:a}),!0):(console.dev("跳转登录页面"),t.to(o.a.gdGovLogin,{},!1),!1)}return!0}},"+s51":function(e,t,n){"use strict";var a=n("Ub41").a?"http://127.0.0.1:8080/local":window.location.origin,o=window.location.origin+window.location.pathname;function i(e,t){return e.endsWith("/")?e+t:e+"/"+t}var r=i(a,"index.html"),u=i(a,"procedure.html"),l=i(a,"situation.html"),s=i(a,"upmaterial.html"),c=i(a,"infowrite.html"),d=i(a,"benkeruhu.html"),f=i(a,"feedback.html"),m=i(a,"user.html"),v=i(a,"precondition.html"),g="http://tyrztest.gd.gov.cn/tif/sso/connect/page/oauth2/authorize?client_secret=123qwe&client_id=gdbs_66&service=initService&scope=all&response_type=code&redirect_uri=http%3A%2F%2Ftry.dg.gov.cn%2Fhtml%2Ftyrz_redirect_web.html?redirectUrl="+encodeURIComponent(encodeURIComponent("".concat(r,"?isTransfer=1")));t.a={localUrl:a,currentUrl:o,gdGovLogin:g,index:r,procedure:u,situation:l,upmaterial:s,infowrite:c,benkeruhu:d,feedback:f,user:m,precondition:v}},4:function(e,t,n){e.exports=n("SB2p")},"4eBD":function(e,t,n){"use strict";var a=n("CrG7");n.n(a).a},BJmK:function(e,t,n){"use strict";var a=n("CJXb");n.n(a).a},CJXb:function(e,t,n){},CeRN:function(e,t,n){"use strict";var a=n("DWNM"),o=n("+s51"),i=(n("hlQx"),n("yWgo")),r=n("o6Wo"),u={mixins:[a.a],props:{subtitle:{type:String,default:"东莞市一件事主题式申办"},userPage:Boolean,isLogin:Boolean},data:function(){return{tokenCode:"",userName:"",certId:"",phoneNum:"",OnlyUserId:""}},created:function(){this.getUserInfo()},methods:{handleGdGov:function(){window.open("http://www.gd.gov.cn/","_blank")},handleLogin:function(){this.$dgRouter.to(o.a.gdGovLogin,{},!1)},handleTitle:function(){this.userPage?this.$dgRouter.to("index"):this.$dgRouter.to("user")},getUserInfo:function(){var e=this,t=Object(i.b)();t?(console.dev("userInfo ==>",t),this.tokenCode=t.tokenCode,this.userName=t.userName,this.certId=t.certId,this.phoneNum=t.phoneNum,this.OnlyUserId=t.OnlyUserId):r.a.onSetUserInfo(function(){console.dev("接收到通知事件，将获取本地的UserInfo"),e.getUserInfo()})}},computed:{userTitle:function(){return this.userPage?"返回首页":"用户中心 (个人)"}},beforeDestroy:function(){Bus.$off("getUserInfo")}},l=(n("4eBD"),n("KHd+")),s=Object(l.a)(u,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("gd-biz-layout",{staticClass:"dg-layout"},[n("div",{attrs:{slot:"header"},slot:"header"},[n("gd-biz-header",{attrs:{subtitle:e.subtitle,"search-hidden":!0,"nav-data":[]}},[e.tokenCode?n("div",{attrs:{slot:"userinfo"},slot:"userinfo"},[n("span",{staticClass:"dg-layout-title",on:{click:e.handleTitle}},[e._v(e._s(e.userTitle))]),n("span",{staticClass:"dg-layout-line"}),n("span",{staticClass:"dg-layout-info"},[e._v(e._s(e.userName))])]):n("div",{attrs:{slot:"userinfo"},slot:"userinfo"},[n("span",{staticClass:"dg-layout-title",on:{click:e.handleGdGov}},[e._v("广东省人民政府")]),n("span",{staticClass:"dg-layout-line"}),n("span",{staticClass:"dg-layout-info",on:{click:e.handleLogin}},[e._v("登录")])])])],1),e._t("default")],2)},[],!1,null,null,null);t.a=s.exports},CrG7:function(e,t,n){},DWNM:function(e,t,n){"use strict";var a=n("lwsE"),o=n.n(a),i=n("W8MJ"),r=n.n(i),u=function(){function e(t){o()(this,e),this.ctx=t,this.info={title:"",timeoutMsg:"网络请求超时",btnOkText:"我知道了",btnCancelText:"",withMask:!0,onOkClick:function(){},onCancelClick:function(){}}}return r()(e,[{key:"showToast",value:function(e,t,n){this.ctx.$toast[e]({msg:t,timeout:n||4e3})}},{key:"Success",value:function(e,t){this.showToast("success",e,t)}},{key:"Error",value:function(e,t){this.showToast("error",e,t)}},{key:"Warning",value:function(e,t){this.showToast("warning",e,t)}},{key:"showLoading",value:function(e){var t=this,n=e.msg,a=e.openTimeout,o=void 0===a||a,i=e.timeout,r=void 0===i?3e4:i,u=e.timeoutMsg,l=void 0===u?"网络请求超时":u,s=e.title,c=void 0===s?"":s,d=e.btnOkText,f=void 0===d?"我知道了":d,m=e.onOkClick,v=void 0===m?function(){}:m,g=e.btnCancelText,h=void 0===g?"":g,p=e.onCancelClick,b=void 0===p?function(){}:p;(r=r||3e4)<3e4&&console.warn("不推荐将超时时间设为30秒以下"),this.ctx.$loading.show(n||""),this.info={title:c,timeoutMsg:l,btnOkText:f,withMask:!0,onOkClick:v,btnCancelText:h,onCancelClick:b},o&&(this.timer=setTimeout(function(){t.ctx.$loading.hide(),t.timeoutEnd()},r))}},{key:"timeoutEnd",value:function(){var e=this.info,t=e.title,n=e.timeoutMsg,a=e.btnOkText,o=e.btnCancelText,i=e.withMask,r=e.onOkClick,u=e.onCancelClick;this.clearTimer(),o?this.ctx.$confirm({head:t,msg:n,btnOkText:a,btnCancelText:o,withMask:i,onOkClick:r,onCancelClick:u}):this.ctx.$alert({head:t,msg:n,btnOkText:a,withMask:i,onOkClick:r})}},{key:"clearTimer",value:function(){this.timer&&(clearTimeout(this.timer),this.ctx.$loading.hide())}}]),e}(),l=n("Ub41"),s=n("yWgo"),c=n("+eJb");t.a={beforeCreate:function(){Object(s.g)(),console.dev=function(e){if(l.a){for(var t,n=arguments.length,a=new Array(n>1?n-1:0),o=1;o<n;o++)a[o-1]=arguments[o];(t=console).log.apply(t,["开发环境(dev):",e].concat(a))}},this.$dgRouter=Object(s.d)(),this.hint=new u(this);var e=window.location.origin+window.location.pathname;Object(c.b)(e,this.$dgRouter),this._beforeCreate&&this._beforeCreate()}}},JT98:function(e,t,n){"use strict";n.d(t,"f",function(){return a}),n.d(t,"h",function(){return o}),n.d(t,"g",function(){return i}),n.d(t,"c",function(){return r}),n.d(t,"b",function(){return u}),n.d(t,"d",function(){return l}),n.d(t,"e",function(){return s}),n.d(t,"a",function(){return c});var a=function(e){if(!/^(11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\d{4}(18|19|20)\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(e))return!1;for(var t=e.split(""),n=[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2],a=0,o=0;o<17;o++)a+=t[o]*n[o];return[1,0,"X",9,8,7,6,5,4,3,2][a%11]==t[17]},o=function(e){return!!/^[1][3,4,5,7,8][0-9]{9}$/.test(e)},i=function(e){if(!a(e))return null;var t=e.substring(6,10),n=e.substring(10,12),o=e.substring(12,14),i="".concat(t,"-").concat(n,"-").concat(o),r=parseInt(e.substr(16,1))%2==1?"1":"0",u=new Date,l=u.getMonth()+1,s=u.getDate(),c=u.getFullYear()-t-1;return(n<l||n==l&&o<=s)&&c++,{birth:i,sex:r,age:c}},r=[{name:"汉族",value:"汉族"},{name:"蒙古族",value:"蒙古族"},{name:"回族",value:"回族"},{name:"藏族",value:"藏族"},{name:"维吾尔族",value:"维吾尔族"},{name:"苗族",value:"苗族"},{name:"彝族",value:"彝族"},{name:"壮族",value:"壮族"},{name:"布依族",value:"布依族"},{name:"朝鲜族",value:"朝鲜族"},{name:"满族",value:"满族"},{name:"侗族",value:"侗族"},{name:"瑶族",value:"瑶族"},{name:"白族",value:"白族"},{name:"土家族",value:"土家族"},{name:"哈尼族",value:"哈尼族"},{name:"哈萨克族",value:"哈萨克族"},{name:"傣族",value:"傣族"},{name:"黎族",value:"黎族"},{name:"傈僳族",value:"傈僳族"},{name:"佤族",value:"佤族"},{name:"畲族",value:"畲族"},{name:"高山族",value:"高山族"},{name:"拉祜族",value:"拉祜族"},{name:"水族",value:"水族"},{name:"东乡族",value:"东乡族"},{name:"纳西族",value:"纳西族"},{name:"景颇族",value:"景颇族"},{name:"柯尔克孜族",value:"柯尔克孜族"},{name:"土族",value:"土族"},{name:"达斡尔族",value:"达斡尔族"},{name:"仫佬族",value:"仫佬族"},{name:"羌族",value:"羌族"},{name:"布朗族",value:"布朗族"},{name:"撒拉族",value:"撒拉族"},{name:"毛南族",value:"毛南族"},{name:"仡佬族",value:"仡佬族"},{name:"锡伯族",value:"锡伯族"},{name:"阿昌族",value:"阿昌族"},{name:"普米族",value:"普米族"},{name:"塔吉克族",value:"塔吉克族"},{name:"怒族",value:"怒族"},{name:"乌孜别克族",value:"乌孜别克族"},{name:"俄罗斯族",value:"俄罗斯族"},{name:"鄂温克族",value:"鄂温克族"},{name:"德昂族",value:"德昂族"},{name:"保安族",value:"保安族"},{name:"裕固族",value:"裕固族"},{name:"京族",value:"京族"},{name:"塔塔尔族",value:"塔塔尔族"},{name:"独龙族",value:"独龙族"},{name:"鄂伦春族",value:"鄂伦春族"},{name:"赫哲族",value:"赫哲族"},{name:"门巴族",value:"门巴族"},{name:"珞巴族",value:"珞巴族"},{name:"基诺族",value:"基诺族"},{name:"其他",value:"其他"},{name:"外国血统中国籍人士",value:"外国血统中国籍人士"}],u=[{name:"统一社会信用代码",value:"001"},{name:"其他法人或其他组织有效证件",value:"099"},{name:"居民身份证",value:"111"},{name:"临时居民身份证",value:"112"},{name:"户口簿",value:"113"},{name:"中国人民解放军军官证",value:"114"},{name:"中国人民武装警察部队警官证",value:"115"},{name:"出生医学证明",value:"117"},{name:"中国人民解放军士兵证",value:"118"},{name:"中国人民武装警察部队士兵证",value:"119"},{name:"中国人民解放军文职人员证",value:"120"},{name:"中国人民武装警察部队文职人员证",value:"122"},{name:"居住证",value:"154"},{name:"外交护照",value:"411"},{name:"公务护照",value:"412"},{name:"公务普通护照",value:"413"},{name:"普通护照",value:"414"},{name:"旅行证",value:"415"},{name:"外国人旅行证",value:"418"},{name:"台湾居民来往大陆通行证",value:"511"},{name:"港澳居民来往内地通行证",value:"516"},{name:"外国人永久居留身份证",value:"553"},{name:"外国人临时居住证",value:"554"},{name:"外交人员身份证明",value:"791"},{name:"境外人员身份证明",value:"792"},{name:"驻华机构证明",value:"813"},{name:"营业执照",value:"821"},{name:"律师事务所执业许可证",value:"830"},{name:"其他",value:"999"}],l={1:"自然人",2:"企业法人",3:"事业法人",4:"社会组织法人",5:"非法人企业",6:"行政机关",7:"其他组织"},s={1:"政府部门核发",2:"其他",4:"申请人自备"},c={"东莞市公安局":10,"东莞市人力资源和社会保障局":11}},SB2p:function(e,t,n){"use strict";n.r(t);var a=n("Kw5r"),o=n("DWNM"),i=n("+s51"),r=n("CeRN"),u=n("JT98"),l=n("xdbn"),s={mixins:[o.a],components:{dglayout:r.a,essentialinfo:function(){return n.e(16).then(n.bind(null,"ngVT"))},gongan:function(){return Promise.all([n.e(0),n.e(14)]).then(n.bind(null,"adOW"))},dgshow:function(){return n.e(2).then(n.bind(null,"XuAl"))},benkeruhu:function(){return n.e(15).then(n.bind(null,"9SYB"))}},data:function(){return{benkeruhuSrc:i.a.benkeruhu,testTable:[],formIds:"",materialIds:"",tableArr:[],isOK:3,ids:"",nation:u.c,showTable:!1,showTest:!1,active:3,list:[{title:"业务选择",desc:""},{title:"信息自检",desc:""},{title:"信息填写",desc:""},{title:"上传材料",desc:""},{title:"完成反馈",desc:""}],allFromData:{}}},methods:{alertMsg:function(e){e.head&&e.msg&&this.$alert({head:e.head,msg:e.msg}),e.itemId&&this.testTable.map(function(t){t.itemId==e.itemId&&(t.code=1)}),console.log("全新的saveSuccess",this.testTable)},handleNext:function(){if(this.testTable.every(function(e){return 1===e.code})){var e=this.$dgRouter.params.businessCode;this.$dgRouter.to("upmaterial",{businessCode:e})}else this.$toast.error({msg:"请先填写并保存全部表单",timeout:2500})},handleGetPage:function(){var e=this,t=this;this.hint.showLoading({msg:"加载中...",timeout:3e4,timeoutMsg:"请求超时",btnOkText:"我知道了"});var n={};n.businessCode=this.$dgRouter.params.businessCode,l.a.getPage(n).then(function(n){n&&1==n.code?(console.log("resss",n),e.tableArr=n.data,console.log(e.tableArr,"888"),e.tableArr.map(function(t){if("table"==t.paType){var n={itemId:t.itemId,code:0};e.testTable.push(n)}}),console.log("测试数组",e.testTable),e.hint.clearTimer()):(e.hint.clearTimer(),e.$confirm({head:"温馨提示",msg:"获取数据失败",btnOkText:"重试",onOkClick:function(){t.handleGetPage()}}))}).catch(function(n){e.hint.clearTimer(),e.$confirm({head:"温馨提示",msg:"服务器异常",btnOkText:"重试",onOkClick:function(){t.handleGetPage()}})})}},created:function(){this.handleGetPage()},watch:{}},c=(n("BJmK"),n("KHd+")),d=Object(c.a)(s,function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("dglayout",[n("div",{staticClass:"container"},[n("div",{staticClass:"main"},[e.list.length>0?n("div",[n("gd-steps",{staticStyle:{width:"900px"},attrs:{active:e.active}},e._l(e.list,function(e,t){return n("gd-step",{key:t,attrs:{title:e.title,desc:e.desc}})}),1)],1):e._e()]),e._l(e.tableArr,function(t){return n("div",{key:t.id},[n("dgshow",{attrs:{title:t.pgName}},[n(t.pgInfo,{tag:"component",attrs:{itemId:t.itemId},on:{alertMsg:e.alertMsg}})],1)],1)}),n("div",{staticClass:"bottom-footer"},[n("gd-button",{attrs:{type:"primary",size:"small"},on:{click:e.handleNext}},[e._v("下一步")])],1)],2)])],1)},[],!1,null,null,null).exports,f=n("kzom"),m=n.n(f),v=(n("tElv"),n("E2g8"));n.n(v).a.polyfill(),a.default.use(m.a),a.default.config.productionTip=!1,d.el="#root",d.mpType="app",new a.default(d).$mount();t.default={config:{pages:["^pages/index"],window:{backgroundTextStyle:"light",navigationBarBackgroundColor:"#fff",navigationBarTitleText:"WeChat",navigationBarTextStyle:"black"}}}},Ub41:function(e,t,n){"use strict";n.d(t,"a",function(){return a});var a=!1},dRp0:function(e,t,n){"use strict";var a=n("lSNA"),o=n.n(a),i=n("lwsE"),r=n.n(i),u=n("W8MJ"),l=n.n(u),s=n("vDqi"),c=n.n(s),d=n("o75L"),f=n("Ub41"),m=n("+eJb"),v=n("yWgo"),g=n("FVIv"),h=n("+s51");var p={102:function(){h.a.index!==h.a.currentUrl&&Object(g.a)({head:"操作失败",msg:"您当前未登录或登录已过期",btnOkText:"去登录",onOkClick:function(){window.location.href=h.a.gdGovLogin}})},103:function(){Object(g.a)({head:"操作失败",msg:"您已办理过相关业务",btnOkText:"我知道了",onOkClick:function(){window.location.href=h.a.index}})}};function b(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),n.push.apply(n,a)}return n}function y(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?b(n,!0).forEach(function(t){o()(e,t,n[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):b(n).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))})}return e}f.a&&console.log("http baseUrl: ",d.a.baseUrl);var w=c.a.create({baseURL:d.a.baseUrl,method:"POST"}),k=w.interceptors.request.use(function(e){return f.a&&console.log("http request =>",e),e}),O=w.interceptors.response.use(function(e){f.a&&console.log("http response =>",e);var t=e.data;return t&&t.code&&p.hasOwnProperty(t.code)&&p[t.code](),e}),C=function(){function e(){r()(this,e)}return l()(e,null,[{key:"getMethodData",value:function(e){var t=(e.method||"POST").toUpperCase();return"GET"===t?{method:"GET",params:e.data||{}}:{method:t,data:e.data||{}}}},{key:"getHeader",value:function(e){var t=e.header||{};return"GET"===e.method.toUpperCase()?t:(t.hasOwnProperty("Content-Type")||e.hasOwnProperty("content-type")||(t["content-type"]="application/json"),t)}},{key:"request",value:function(t){var n=t.url,a=void 0===n?"":n,o=t.method,i=void 0===o?"POST":o,r=t.data,u=void 0===r?{}:r,l=t.header,s=void 0===l?{}:l,c=t.extranet,d=void 0!==c&&c,f=t.timeout,v=void 0===f?3e4:f,g=t.ctx,h=t.showLoading,p={url:a,method:i,data:u,header:s,extranet:d,timeout:v,ctx:g,showLoading:void 0===h||h};if(console.dev("request url ==>",a),!1===p.extranet){var b=g?g.$dgRouter:e.router;if(!1===Object(m.a)(p.url,b))return Promise.reject({err:"no login"})}var C=p.ctx||null;return p&&!p.url?(console.dev("url is must!"),Promise.reject({url:"is must"})):(w.defaults.timeout=p.timeout||3e4,p&&!0===p.extranet&&(w.defaults.baseURL="",w.interceptors.response.eject(O),w.interceptors.request.eject(k)),p=y({},p=y({},p,{},e.getMethodData(p)),{},e.getHeader(p)),console.dev("http request start!"),console.dev(C,p.showLoading),C&&p.showLoading&&C.$loading.show(),w(p).then(function(e){return C&&p.showLoading&&C.$loading.hide(),Promise.resolve(e.data)}).catch(function(e){return C&&p.showLoading&&C.$loading.hide(),Promise.reject(e)}))}}]),e}();C.router=Object(v.d)();t.a=C},hlQx:function(e,t,n){"use strict";n.d(t,"a",function(){return c});var a=n("lwsE"),o=n.n(a),i=n("W8MJ"),r=n.n(i),u=n("dRp0"),l=n("o75L"),s=n("yWgo"),c=function(){function e(){o()(this,e)}return r()(e,null,[{key:"findUserInfoByToken",value:function(e,t){console.dev("ctx",t);var n=Object(s.c)({tokenCode:e},!1);return u.a.request({url:l.a.findUserInfoByToken,data:n,header:{"Content-Type":"application/x-www-form-urlencoded;charset=UTF-8"},ctx:t})}},{key:"findAllByUseridcode",value:function(e){return u.a.request({url:l.a.findAllByUseridcode,data:e})}}]),e}()},o6Wo:function(e,t,n){"use strict";var a=n("lwsE"),o=n.n(a),i=n("W8MJ"),r=n.n(i),u=new(n("Kw5r").default),l=function(){function e(){o()(this,e)}return r()(e,null,[{key:"setUserInfo",value:function(){u.$emit("setUserInfo")}},{key:"onSetUserInfo",value:function(e){u.$on("setUserInfo",function(){e(),u.$off("setUserInfo")})}}]),e}();t.a=l},o75L:function(e,t,n){"use strict";var a=n("Ub41").a?"http://192.168.1.106:7005/":"http://127.0.0.1:7005/";t.a={getResult:"business/finishBusiness",getFiles:"inspection/getInspection",baseUrl:a,getAllOnethingList:"getAllOnethingList",findAllByUseridcode:"business/findAllByUseridcode",findUserInfoByToken:"findUserInfoByToken",addSelf:"datum/addSelf",addDatum:"datum/addDatum",backDatum:"datum/backDatum",getPage:"page/getPage",tpdownloadFile:"tpdownloadFile",downloadFile:"downloadFile",fileDelete:"file/delete",addHousehold:"household/addHousehold",getOnethingItem:"getOnethingItem",listLastSituationItemInfo:"listLastSituationItemInfo",fileload:"file/fileload",infomsg:"business/findDetailByBusinessCode"}},xdbn:function(e,t,n){"use strict";n.d(t,"a",function(){return s});var a=n("lwsE"),o=n.n(a),i=n("W8MJ"),r=n.n(i),u=n("dRp0"),l=n("o75L"),s=function(){function e(){o()(this,e)}return r()(e,null,[{key:"addDatum",value:function(e){return u.a.request({url:l.a.addDatum,data:e})}},{key:"backDatum",value:function(e){return u.a.request({url:l.a.backDatum,data:e})}},{key:"getPage",value:function(e){return u.a.request({url:l.a.getPage,data:e})}},{key:"getAllMsg",value:function(e){return u.a.request({url:l.a.infomsg,data:e})}},{key:"findAllByUseridcode",value:function(e){return u.a.request({url:l.a.findAllByUseridcode,data:e})}},{key:"getAllMatter",value:function(e){return u.a.request({url:l.a.findAllByUseridcode,data:e})}},{key:"handleGetFiles",value:function(e){return u.a.request({url:l.a.getFiles,data:e})}},{key:"fileDelete",value:function(e){return u.a.request({url:l.a.fileDelete,data:e})}},{key:"addHousehold",value:function(e){return u.a.request({url:l.a.addHousehold,data:e})}},{key:"handleGetResult",value:function(e){return u.a.request({url:l.a.getResult,data:e})}},{key:"handleBackFiles",value:function(e){return u.a.request({url:l.a.backFiles,data:e})}}]),e}()},yWgo:function(e,t,n){"use strict";n.d(t,"g",function(){return m}),n.d(t,"e",function(){return v}),n.d(t,"a",function(){return g}),n.d(t,"f",function(){return p}),n.d(t,"b",function(){return b}),n.d(t,"d",function(){return w}),n.d(t,"c",function(){return y}),n.d(t,"h",function(){return k});var a=n("lSNA"),o=n.n(a),i=n("cDf5"),r=n.n(i),u=n("wZgz"),l=n.n(u),s=n("+NVA"),c=n.n(s),d=n("Ub41");function f(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter(function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable})),n.push.apply(n,a)}return n}var m=function(){var e=localStorage.getItem("timingStoreKeys"),t=e=e?JSON.parse(e):{timingStoreKeys:[]};if(0!==t.timingStoreKeys.length){var n=(new Date).getTime(),a=t.timingStoreKeys;a.map(function(e){var t=localStorage.getItem(e)||"";if(t){try{t=JSON.parse(t)}catch(t){console.warn(t),localStorage.removeItem(e);var o=a.filter(function(t){return t!==e});return void localStorage.setItem("timingStoreKeys",JSON.stringify({timingStoreKeys:o}))}if(t.timeOut<n){localStorage.removeItem(e);var i=a.filter(function(t){return t!==e});localStorage.setItem("timingStoreKeys",JSON.stringify({timingStoreKeys:i}))}}})}},v=function(e,t,n){m();var a=(new Date).getTime();"object"===r()(t)&&(t={value:function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?f(n,!0).forEach(function(t){o()(e,t,n[t])}):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):f(n).forEach(function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))})}return e}({},t),timeOut:a+6e4*n}),"string"==typeof t&&(t={value:t,timeOut:a+6e4*n});var i=localStorage.getItem("timingStoreKeys"),u=i=i?JSON.parse(i):{timingStoreKeys:[]},l="timingStore".concat(e);return u.timingStoreKeys.includes(l)||(u.timingStoreKeys.push(l),localStorage.setItem("timingStoreKeys",JSON.stringify(u))),localStorage.setItem(l,JSON.stringify(t)),!0},g=function(e){m();var t=JSON.parse(localStorage.getItem("timingStore".concat(e)));return t?t.value:null},h="AES20191223USERINFO",p=function(e,t){return e=JSON.stringify(e),e=l.a.encrypt(e,h).toString(),v("dgyjs-user",e,t||120)},b=function(){var e=g("dgyjs-user");return e?(e=l.a.decrypt(e.toString(),h),e=JSON.parse(e.toString(c.a))):null},y=function(e){var t=!(arguments.length>1&&void 0!==arguments[1])||arguments[1],n="",a=Object.keys(e);if(a.length>0){var o=new RegExp(/[\u4e00-\u9fa5]/);n=a.reduce(function(t,n){return o.test(n)&&(n=encodeURI(n)),o.test(e[n])&&(e[n]=encodeURI(e[n])),t=t?"".concat(t,"&").concat(n,"=").concat(e[n]):"".concat(n,"=").concat(e[n])},"")}return t&&n&&(n="?".concat(n)),n},w=function(){var e=window.location.href,t=new RegExp(/^(?:[^?]*\?)?([\w\d\-\/=&%].+)/).exec(e);if(!t)return{};var n,a;return{params:(t=t.pop()).split("&").reduce(function(e,t){return t.includes("=")?(t=decodeURIComponent(t).split("="),n=t.shift(),void 0!=(a=t.join("="))&&(a=a.replace("+"," "))):(n=decodeURIComponent(t),a=void 0),e[n]=a,e},{}),to:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},n=!(arguments.length>2&&void 0!==arguments[2])||arguments[2],a=window.location.origin;return n?(e=d.a?"".concat(a,"/local/").concat(e,".html").concat(y(t)):"".concat(a,"/").concat(e,".html").concat(y(t)),void(window.location.href=e)):void(window.location.href=e)},back:function(){arguments.length>0&&void 0!==arguments[0]&&arguments[0]?window.history.back(-1):window.history.go(-1)},redirect:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},n=!(arguments.length>2&&void 0!==arguments[2])||arguments[2],a=window.location.origin;return n?(e=d.a?"".concat(a,"/local/").concat(e,".html").concat(y(t)):"".concat(a,"/").concat(e,".html").concat(y(t)),void window.location.replace(e)):void window.location.replace(e)},open:function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},n=!(arguments.length>2&&void 0!==arguments[2])||arguments[2],a=window.location.origin;return n?(e=d.a?"".concat(a,"/local/").concat(e,".html").concat(y(t)):"".concat(a,"/").concat(e,".html").concat(y(t)),void window.open(e)):void window.open(e)}}};function k(){for(var e=[],t=0;t<36;t++)e[t]="0123456789abcdefg".substr(Math.floor(16*Math.random()),1);return e[14]="4",e[19]="0123456789abcdefg".substr(3&e[19]|8,1),e[8]=e[13]=e[18]=e[23]="-",e.join("")}}},[[4,1,0]]]);