package com.yjs.service.loginuser;


import com.yjs.bean.UserBean;
import com.yjs.dao.mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	Usermapper usermapper;


	@Override
	public void selectUser(UserBean userBean) {
		// TODO Auto-generated method stub
		String useridcode=userBean.getUseridcode();
		int num=usermapper.selectUser(useridcode);
		if(num==1) {
			int updanum=usermapper.updateUser(userBean);
			System.out.println("修改"+num+updanum); 
		}else {
			int insertnum=usermapper.insertUser(userBean);
			System.out.println("增加"+ num+insertnum);
		}
	}
}
