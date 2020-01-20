package com.yjs.service.business.Impl;

import java.util.List;

import com.yjs.bean.business.Business;
import com.yjs.dao.mapper.BusinessMapper;
import com.yjs.service.business.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	BusinessMapper businessMapper;

	@Override
	public void save(Business business) {
		// TODO Auto-generated method stub
		businessMapper.save(business);
	}

	@Override
	public List<Business> findAllByUseridcode(String useridcode) {
		List<Business> business = businessMapper.findAllByUseridcode(useridcode);
		return business;
	}

	@Override
	public Business findAllByBusinessCode(String businessCode) {
		// TODO Auto-generated method stub
		Business business = businessMapper.findAllByBusinessCode(businessCode);
		return business;
	}

	@Override
	public void updateBusinessState(String business_code, String state) {
		businessMapper.updateBusinessState(business_code,state);
	}

	@Override
	public void updata(Business business) {
		businessMapper.update(business);
	}

	@Override
	public Business findAllByUseridcodeAndMatterId(String userIdCode, int matterId) {
		Business business = businessMapper.findAllByUseridcodeAndMatterId(userIdCode, matterId);
		return business;
	}

}
