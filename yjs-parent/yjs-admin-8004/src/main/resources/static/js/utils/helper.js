const qs = (params, qmask = true) => {
    let temp = '';
    const paramKeys = Object.keys(params)
    if (paramKeys.length > 0) {
        temp = paramKeys.reduce((map, key) => {
            // 开始拼接
            if (!map) {
                map = `${key}=${params[key]}`
                return map
            }
            map = `${map}&${key}=${params[key]}`
            return map
        }, '')
    }
    if (qmask && temp) {
        temp = `?${temp}`
    }
    return temp
};