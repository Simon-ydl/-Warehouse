package com.yjs.service.user.service;

import com.yjs.bean.system.RoUser;
import com.yjs.bean.test.Model;
import com.yjs.service.base.service.IBaseApp;

import java.util.List;

/**
 * @ClassName: RoUserService
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/13 15:14
 * @Version: 1.0.0
 **/

public interface IRoUserService  extends IBaseApp<RoUser> {

    int update(RoUser roUser);
}
