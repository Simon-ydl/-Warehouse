(window.webpackJsonp=window.webpackJsonp||[]).push([[2],{"+4iG":function(t,i,s){"use strict";var e=s("LgNM");s.n(e).a},LgNM:function(t,i,s){},XuAl:function(t,i,s){"use strict";s.r(i);var e={name:"dgshow",data:function(){return{isShow:!0}},props:{title:{type:String},infotitle:{type:String},url:{type:String}},methods:{saveMsg:function(){this.$emit("saveMsg")},handleShow:function(){this.isShow=!this.isShow}}},a=(s("+4iG"),s("KHd+")),n=Object(a.a)(e,function(){var t=this,i=t.$createElement,s=t._self._c||i;return s("div",{staticClass:"table-info"},[s("div",{staticClass:"info-title"},[s("span",{staticClass:"info-title-left"},[t._v(t._s(t.title))]),s("a",{directives:[{name:"show",rawName:"v-show",value:this.url,expression:"this.url"}],staticClass:"info-title-right-save",on:{click:t.saveMsg}},[t._v("暂存")]),t.isShow?s("a",{staticClass:"info-title-right title-color-false",on:{click:function(i){t.isShow=!t.isShow}}},[s("i",{staticClass:"gd-icon icon-subtract pad-r"}),t._v("收起\n    ")]):s("a",{staticClass:"info-title-right title-color-true",on:{click:function(i){t.isShow=!t.isShow}}},[s("i",{staticClass:"gd-icon icon-plus pad-r"}),t._v("展开\n    ")])]),s("div",{directives:[{name:"show",rawName:"v-show",value:t.isShow,expression:"isShow"}],staticClass:"info"},[t.infotitle?s("div",{staticClass:"msg-title"},[t._v(t._s(t.infotitle))]):t._e(),s("div",{staticClass:"msg-form"},[t._t("default")],2)])])},[],!1,null,"6d7db764",null);i.default=n.exports}}]);