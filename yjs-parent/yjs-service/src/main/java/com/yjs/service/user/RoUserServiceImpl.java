package com.yjs.service.user;

import com.yjs.bean.system.RoUser;
import com.yjs.dao.mapper.RoUserMapper;
import com.yjs.service.user.service.IRoUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: RoUserServiceImpl
 * @Auther：admin
 * @Description:
 * @Date: 2019/12/13 15:14
 * @Version: 1.0.0
 **/

@Service
@Transactional
public class RoUserServiceImpl implements IRoUserService {


    @Resource
    RoUserMapper mapper;


    @Override
    public List<RoUser> query(RoUser roUser) {
        return mapper.select(roUser);
    }

    @Override
    public int insert(RoUser roUser) {
        return mapper.insert(roUser);
    }

    @Override
    public int update(RoUser roUser) {
        return mapper.updateByPrimaryKey(roUser);
    }
}
