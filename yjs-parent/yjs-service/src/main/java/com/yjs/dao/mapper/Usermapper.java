package com.yjs.dao.mapper;




import com.yjs.bean.UserBean;

import java.util.List;

public interface Usermapper {
public List<UserBean> selectAlluser();
public int selectUser(String useridcode);
public int insertUser(UserBean userBean);
public int updateUser(UserBean userBean);
}
