//检验身份证号码
var verifyCertId = function(value) {
    var pattern = /^(11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\d{4}(18|19|20)\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
    if (pattern.test(value)) {
        var code = value.split('')
        var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
        var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2]
        var sum = 0
        var ai = 0
        var wi = 0
        for (var i = 0; i < 17; i++) {
            ai = code[i]
            wi = factor[i]
            sum += ai * wi
        }
        var last = parity[sum % 11]
        if (last != code[17]) {
            return false
        }
    } else {
        return false
    }

    return true
}

// 从身份证获取生日
var getBirthdate = function(idCard) {
    var birthday = ''
    idCard = idCard + ''
    if (idCard != null && idCard != '') {
        if (idCard.length == 15) {
            birthday = '19' + idCard.substr(6, 6)
        } else if (idCard.length == 18) {
            birthday = idCard.substr(6, 8)
        }
        birthday = birthday.replace(/(.{4})(.{2})/, '$1-$2-')
    }
    return birthday
}

//获取当前日期（2020-01-01）
var getNowFormatDate = function() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}



var nation = {
    '汉族': 'AE01',
    '蒙古族': 'AE02',
    '回族': 'AE03',
    '藏族': 'AE04',
    '维吾尔族': 'AE05',
    '苗族': 'AE06',
    '彝族': 'AE07',
    '壮族': 'AE08',
    '布依族': 'AE09',
    '朝鲜族': 'AE10',
    '满族': 'AE11',
    '侗族': 'AE12',
    '瑶族': 'AE13',
    '白族': 'AE14',
    '土家族': 'AE15',
    '哈尼族': 'AE16',
    '哈萨克族': 'AE17',
    '傣族': 'AE18',
    '黎族': 'AE19',
    '傈僳族': 'AE20',
    '佤族': 'AE21',
    '畲族': 'AE22',
    '高山族': 'AE23',
    '拉祜族': 'AE24',
    '水族': 'AE25',
    '东乡族': 'AE26',
    '纳西族': 'AE27',
    '景颇族': 'AE28',
    '柯尔克孜族': 'AE29',
    '土族': 'AE30',
    '达斡尔族': 'AE31',
    '仫佬族': 'AE32',
    '羌族': 'AE33',
    '布朗族': 'AE34',
    '撒拉族': 'AE35',
    '毛南族': 'AE36',
    '仡佬族': 'AE37',
    '锡伯族': 'AE38',
    '阿昌族': 'AE39',
    '普米族': 'AE40',
    '塔吉克族': 'AE41',
    '怒族': 'AE42',
    '乌孜别克族': 'AE43',
    '俄罗斯族': 'AE44',
    '鄂温克族': 'AE45',
    '德昂族': 'AE46',
    '保安族': 'AE47',
    '裕固族': 'AE48',
    '京族': 'AE49',
    '塔塔尔族': 'AE50',
    '独龙族': 'AE51',
    '鄂伦春族': 'AE52',
    '赫哲族': 'AE53',
    '门巴族': 'AE54',
    '珞巴族': 'AE55',
    '基诺族': 'AE56',
    '其他': 'AE97',
    '外国血统中国籍人士': 'AE98'
};