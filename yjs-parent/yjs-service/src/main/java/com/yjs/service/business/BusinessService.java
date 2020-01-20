package com.yjs.service.business;

import com.yjs.bean.business.Business;

import java.util.List;


public interface BusinessService {

	void save(Business business);

	List<Business> findAllByUseridcode(String useridcode);

	Business findAllByBusinessCode(String businessCode);

	void updateBusinessState(String business_code,String state);//传入流水号修改business的state值

    void updata(Business business);

    Business findAllByUseridcodeAndMatterId(String userIdCode, int matterId);
}
